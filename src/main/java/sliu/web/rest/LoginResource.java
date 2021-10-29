package sliu.web.rest;



import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import sliu.service.AuthUserService;
import sliu.unit.HttpServletUtils;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    public boolean checkLogin(HashMap codeInfo)
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
}