package sliu.config;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import sliu.config.jwt.JWTUtil;
import sliu.config.jwt.vo.ResponseResult;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/")
@Configuration
@Slf4j
public class TokenFilter implements Filter {

    private static Logger logger = LoggerFactory
            .getLogger(TokenFilter.class);

    @Value("${secretKey:123456}")
    private String secretKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String url = httpServletRequest.getRequestURI();
        boolean isFilter = false;
        if (url.indexOf("/api/login") >= 0 || url.indexOf("/api/getCode") >= 0
                || url.indexOf("/v2") >= 0  || url.indexOf("/swagger-resources") >= 0 || url.indexOf("/js") >= 0
                || url.indexOf("/doc.html") >= 0 || url.indexOf("/css") >= 0) {
            //白名单放行，登录，获取验证码
            chain.doFilter(request, response);
        }else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String token = httpServletRequest.getHeader("token");
            ResponseResult responseResult = new ResponseResult();
            if (StringUtils.isBlank(token)) {
                //如果token为null 返回401
                ((HttpServletResponse) response).setStatus(401);
                responseResult.setErrorCode(2005);
                responseResult.setMsg("token缺失");
            } else {
                //todo 检查Redis中是否有此Token 即token是否过期
                isFilter = true;
                try {
                    JWTUtil.verifyToken(token, secretKey);
                } catch (TokenExpiredException ex) {
                    responseResult.setErrorCode(2002);
                    responseResult.setMsg("无效的token");
                } catch (Exception ex) {
                    responseResult.setErrorCode(2000);
                    responseResult.setMsg("未知错误");
                }

            }
            if (responseResult.getErrorCode() > 2000) {// 验证失败
                PrintWriter writer = null;
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(response.getOutputStream(),
                            "UTF-8");
                    writer = new PrintWriter(osw, true);
                    String jsonStr = JSON.toJSONString(responseResult);
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                    osw.close();
                } catch (UnsupportedEncodingException e) {
                    logger.error("过滤器返回信息失败:" + e.getMessage(), e);
                } catch (IOException e) {
                    logger.error("过滤器返回信息失败:" + e.getMessage(), e);
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != osw) {
                        osw.close();
                    }
                }
                return;
            }

            if (isFilter) {
                logger.info("token filter过滤ok!");
                chain.doFilter(request, response);
            }
        }

    }


    private Map<String, String> getHeadKeyAndValue(HttpServletRequest httpRequest) {
        Map<String, String> header = new HashMap<>();
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String nextElement = headerNames.nextElement();
            header.put(nextElement, httpRequest.getHeader(nextElement));
        }
        return header;
    }
}
