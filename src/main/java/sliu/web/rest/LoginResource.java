package sliu.web.rest;



import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import org.springframework.http.ResponseEntity;
import sliu.domain.Menu;
import sliu.service.AuthUserService;
import sliu.unit.HttpServletUtils;
import sliu.unit.ResponseUtil;
import sliu.unit.VerifyCodeUtils;
import sliu.config.jwt.JWTUtil;
import sliu.config.jwt.vo.*;
import sliu.config.jwt.vo.JwtModel;
import sliu.unit.Md5;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api")
public class LoginResource {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper;
    public LoginResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

     @Value("${secretKey:123456}")
      private String secretKey;

     @Autowired
      private StringRedisTemplate stringRedisTemplate;



        @GetMapping("/getCode")
        @ApiOperation("获取验证码")
        public ResponseResult getCode() throws IOException {
            Map<String, Object> map=new HashMap<String, Object>();
            // 生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

            System.err.println(verifyCode);

            // 唯一标识
            String uuid = IdUtil.simpleUUID();
            String verifyKey = "sysUser_codes"+ uuid;
            //存入redis给予过期时间
            redisTemplate.opsForValue().set(verifyKey,verifyCode,2,TimeUnit.MINUTES);
            // 生成图片
            int w = 111, h = 36;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
            try
            {
                map.put("uuid", uuid);
                map.put("img", Base64.encode(stream.toByteArray()));
                return ResponseResult.success(map);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return ResponseResult.error(-1,"获取验证码失败");
            }
            finally
            {
                stream.close();
            }
        }

        @PostMapping("/login")
        @ApiOperation("系统用户登录")
        public ResponseResult login(@RequestBody @Validated LoginRequest request, BindingResult bindingResult) throws JsonProcessingException {
         if (bindingResult.hasErrors()) {
             //如果实体类的注解验证不通过
             return ResponseResult.error(ResponseCodeEnum.PARAMETER_ILLEGAL.getErrorCode(), ResponseCodeEnum.PARAMETER_ILLEGAL.getMessage());
         }
         //验证码校验
//         HashMap codeInfo = new HashMap();
//         codeInfo.put("uuid",request.getUuid());
//         codeInfo.put("code",request.getCode());
//            if(!this.checkLogin(codeInfo))
//            {
//                return ResponseResult.error (ResponseCodeEnum.LOGIN_CODE_ERROR.getErrorCode(),"验证码错误");
//            }

         String username = request.getUsername();
         String password = request.getPassword();

         //根据用户名和密码去数据库验证 获取到userId
        String md5PW;
        if (password != null && !"".equals(password)) {
            md5PW = Md5.md5Encode(password);
        } else {
            md5PW = Md5.md5Encode(username);
        }
        request.setPassword(md5PW);
        JwtModel jwtModel = authUserService.isUser(request);
        if(jwtModel == null){
            return ResponseResult.error (ResponseCodeEnum.LOGIN_ERROR.getErrorCode(),"用户名或密码错误");
        }

         String token = JWTUtil.generateTokenByUserInfo(jwtModel.getUserId().toString(),objectMapper.writeValueAsString(jwtModel), secretKey);
         //  生成Token
         //String token = JWTUtil.generateTokenByUserId(userId, secretKey);

         //  生成刷新Token
         String refreshToken = UUID.randomUUID().toString().replace("-", "");

         //放入缓存,并给缓存设置过期时间为token有效时间;
         stringRedisTemplate.opsForHash().put(jwtModel.getUserId().toString(), "token", token);
         stringRedisTemplate.expire(jwtModel.getUserId().toString(), JWTUtil.TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

         LoginResponse loginResponse = new LoginResponse();
         loginResponse.setToken(token);
         loginResponse.setRefreshToken(refreshToken);
         loginResponse.setUsername(username);
         loginResponse.setUserId(jwtModel.getUserId().toString());

         return ResponseResult.success(loginResponse);

     }
        /**
        * 退出
        */
      @GetMapping("/logout")
      public ResponseResult logout() {
          JwtModel jwtModel = HttpServletUtils.getUserInfo();
          System.out.println("退出"+jwtModel.getUserId());
         String key = jwtModel.getUserId().toString();
         //清除缓存
         stringRedisTemplate.delete(key);
         return ResponseResult.success();
      }
      //验证码校验
    private boolean checkLogin(HashMap codeInfo)
    {
        String verifyKey = "sysUser_codes" + codeInfo.get("uuid");
        // 判断验证码
        if (redisTemplate.opsForValue().get(verifyKey) == null) {
            return false;
        } else {
            String captcha = redisTemplate.opsForValue().get(verifyKey)
                    .toString();
            redisTemplate.delete(verifyKey);
            if (!codeInfo.get("code").toString().equalsIgnoreCase(captcha)) {
                return false;
            }
        }
        return true;
    }

    @GetMapping(value = "/current")
    @ApiOperation("获取当前用户信息")
    public ResponseEntity<Map> current() {
        JwtModel jwtModel = HttpServletUtils.getUserInfo();
        List<String> roleList = new ArrayList<>(1);
        roleList.add("2018113009");
        //User userInfo = this.userService.selectUserByLoginId(jwtModel.getUserId());
        HashMap result = new HashMap(4);
        result.put("roles",roleList.toArray());
        result.put("introduction","I am a super administrator");
        result.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"); //头像
        result.put("name","Super Admin");
        return ResponseUtil.success(result);
    }

    @ApiOperation(value = "动态路由",notes = "配置vue-element-admin 动态路由")
    @GetMapping(value = "/menus")
    public ResponseEntity<Map> menus() {
        try {
            JwtModel jwtModel = HttpServletUtils.getUserInfo();
            //获取当前用户绑定的所有一级菜单
            Menu firstMenus = new Menu();
            firstMenus.setId("1");
            firstMenus.setName("系统管理");
            firstMenus.setAction("system");
            firstMenus.setSort(1);
            firstMenus.setParentId(null);
            firstMenus.setStyle("documentation");
            firstMenus.setMenuType(0);
            firstMenus.setUseRoles("2018113009");
            List<Menu> stairMenuList = new ArrayList<>(1);
            stairMenuList.add(firstMenus);
            List<HashMap> result = new ArrayList<>(4);
            if(stairMenuList != null && stairMenuList.size()>0){
                for (Menu stairMenu : stairMenuList){
                    //组装一级菜单数据格式
                    HashMap firstMenu = new HashMap(5);
                    firstMenu.put("path","/"+stairMenu.getAction());
                    firstMenu.put("component","Layout");
                    firstMenu.put("alwaysShow",true);
                    firstMenu.put("name",stairMenu.getName());
                    HashMap firstMate = new HashMap(4);
                    firstMate.put("title",stairMenu.getName());
                    firstMate.put("icon",stairMenu.getStyle());
                    firstMate.put("roles",stairMenu.getUseRoles().split(","));
                    firstMenu.put("meta",firstMate);
                    if(stairMenu.getId() != null){
                        //获取当前一级菜单下所有二级菜单，子路由
                        List<Menu> childrenMenu = new ArrayList<>(8);
                        Menu one = new Menu();
                        one.setId("2");
                        one.setName("用户管理");
                        one.setAction("user/list");
                        one.setSort(1);
                        one.setParentId("1");
                        one.setStyle("user");
                        one.setMenuType(0);
                        one.setUseRoles("2018113009");
                        Menu two = new Menu();
                        two.setId("3");
                        two.setName("角色管理");
                        two.setAction("role/list");
                        two.setSort(5);
                        two.setParentId("1");
                        two.setStyle("peoples");
                        two.setMenuType(0);
                        two.setUseRoles("2018113009");
                        Menu three = new Menu();
                        three.setId("4");
                        three.setName("用户详情");
                        three.setAction("user/detail");
                        three.setSort(2);
                        three.setParentId("1");
                        three.setStyle("user");
                        three.setMenuType(1);
                        three.setUseRoles("2018113009");
                        Menu four = new Menu();
                        four.setId("5");
                        four.setName("角色详情");
                        four.setAction("role/detail");
                        four.setSort(1);
                        four.setParentId("1");
                        four.setStyle("peoples");
                        four.setMenuType(1);
                        four.setUseRoles("2018113009");
                        childrenMenu.add(one);
                        childrenMenu.add(two);
                        childrenMenu.add(three);
                        childrenMenu.add(four);


                        if(childrenMenu != null && childrenMenu.size()>0){
                            List<HashMap> children = new ArrayList<>(4);
                            for (Menu child : childrenMenu){
                                HashMap childMenu = new HashMap(4);
                                childMenu.put("path",child.getAction());
                                childMenu.put("component",stairMenu.getAction()+"/"+child.getAction());
                                childMenu.put("alwaysShow",false);
                                childMenu.put("name",child.getName());
                                HashMap childMate = new HashMap(4);
                                childMate.put("title",child.getName());
                                childMate.put("icon",child.getStyle()!=null?child.getStyle():null);
                                childMenu.put("meta",childMate);
                                if(child.getMenuType() == 1){
                                    //如果是子路由，则隐藏
                                    childMenu.put("hidden",true);
                                    childMate.put("roles",stairMenu.getUseRoles().split(","));
                                }else {
                                    childMate.put("roles",child.getUseRoles()!=null?child.getUseRoles().split(","):new ArrayList<>());
                                }
                                children.add(childMenu);
                            }
                            firstMenu.put("children",children);
                        }
                    }
                    result.add(firstMenu);

                }
            }
            return stairMenuList!=null ? ResponseUtil.success(result):ResponseUtil.error("未查询到结果！");
        }catch (Exception var){
            throw var;
        }
    }
}
