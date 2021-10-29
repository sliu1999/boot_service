package sliu.unit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import sliu.config.jwt.vo.JwtModel;
import sliu.config.jwt.vo.ResponseCodeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JWTUtil {

     private static final String ISSUER = "admin";


     /**
       * 校验Token
       * @param token
       * @param secretKey
       * @return
       */
     public static void verifyToken(String token, String secretKey) throws Exception {
         try {
                 Algorithm algorithm = Algorithm.HMAC256(secretKey);
                 JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(ISSUER).build();
                 jwtVerifier.verify(token);
             } catch (JWTDecodeException jwtDecodeException) {
                    throw new JWTDecodeException(ResponseCodeEnum.TOKEN_INVALID.getMessage());
             } catch (TokenExpiredException tokenExpiredException) {
                    throw new TokenExpiredException(ResponseCodeEnum.TOKEN_INVALID.getMessage());
             } catch (Exception ex) {
                    throw new Exception(ResponseCodeEnum.UNKNOWN_ERROR.getMessage());
         }
     }

     /**
       * 从Token中提取用户信息
       * @param token
       * @return
       */
     public static String getUserId(String token) {
         DecodedJWT decodedJWT = JWT.decode(token);
         String userId = decodedJWT.getClaim("userId").asString();
         return userId;
     }

    public static JwtModel getUserInfo(String token, ObjectMapper objectMapper) throws JsonProcessingException {
        DecodedJWT decodedJWT = JWT.decode(token);
        String userId = decodedJWT.getClaim("userId").asString();
        String subject = decodedJWT.getSubject();
        JwtModel jwtModel = objectMapper.readValue(subject, JwtModel.class);
        return jwtModel;
    }
}