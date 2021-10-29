package sliu.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import sliu.config.jwt.JWTUtil;
import sliu.config.jwt.vo.JwtModel;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpServletUtils {



    public static JwtModel getUserInfo(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            HttpServletRequest httpServletRequest = requestAttributes.getRequest();
            JwtModel jwtModel = null;
            String token = httpServletRequest.getHeader("token");
            try {
                jwtModel = JWTUtil.getUserInfo(token);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }

            return jwtModel;
        }
        return null;
    }



}
