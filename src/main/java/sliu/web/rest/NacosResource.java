package sliu.web.rest;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sliu.config.jwt.JWTUtil;
import sliu.config.jwt.vo.*;
import sliu.domain.Menu;
import sliu.service.AuthUserService;
import sliu.unit.HttpServletUtils;
import sliu.unit.Md5;
import sliu.unit.ResponseUtil;
import sliu.unit.VerifyCodeUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api")
public class NacosResource {

    @NacosValue(value = "${useLocalCache:'false'}", autoRefreshed = true)
    private boolean useLocalCache;


    @GetMapping("/getNacos")
    public boolean getNacosConfIcon(){
        return useLocalCache;
    }
}
