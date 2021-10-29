package sliu.web.rest;


import sliu.unit.HttpServletUtils;
import sliu.config.jwt.vo.ResponseResult;
import sliu.config.jwt.vo.JwtModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Sentinel限流
 * @author sliu
 * @date 2021/3/29
 */
@RestController
@RequestMapping("/api")
public class XianLiuResource {



    @GetMapping(value = "/current")
    public ResponseResult current() {
        HashMap result = new HashMap(4);
        String [] s = {"2021"};
        result.put("roles",s);
        result.put("introduction","I am a super administrator");
        result.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("name","Super Admin");
        JwtModel jwtModel = HttpServletUtils.getUserInfo();
        System.out.println(jwtModel.getUserId());
        System.out.println(jwtModel.getUsername());
        return ResponseResult.success(result);
    }

    @GetMapping(value = "/menus")
    public ResponseResult menus() {
        List<HashMap> result = new ArrayList<HashMap>(2);
        List<HashMap> children = new ArrayList<HashMap>(2);
        HashMap user = new HashMap(4);
        user.put("path","user");
        user.put("component","system/user");
        user.put("alwaysShow",false);
        user.put("name","用户管理");
        HashMap userMate = new HashMap(4);
        userMate.put("title","用户管理");
        userMate.put("icon","user");
        String [] userRoles = {"2021","test"};
        userMate.put("roles",userRoles);
        user.put("meta",userMate);
        children.add(user);

        HashMap role = new HashMap(4);
        role.put("path","role");
        role.put("component","system/role");
        role.put("alwaysShow",false);
        role.put("name","角色管理");
        HashMap roleMate = new HashMap(4);
        roleMate.put("title","角色管理");
        roleMate.put("icon","peoples");
        String [] roleRoles = {"test"};
        roleMate.put("roles",roleRoles);
        role.put("meta",roleMate);
        children.add(role);

        HashMap system = new HashMap(8);
        system.put("path","/system");
        system.put("component","Layout");
        system.put("alwaysShow",true);
        system.put("name","系统管理");

        HashMap sysMeta = new HashMap(4);
        sysMeta.put("title","系统管理");
        sysMeta.put("icon","documentation");
        String [] s = {"2021"};
        sysMeta.put("roles",s);
        system.put("meta",sysMeta);
        system.put("children",children);
        result.add(system);
        return ResponseResult.success(result);
    }


}