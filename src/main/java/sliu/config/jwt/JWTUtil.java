package sliu.config.jwt;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import sliu.config.jwt.vo.ResponseCodeEnum;
import sliu.config.jwt.vo.JwtModel;

import java.util.Date;


public class JWTUtil {
    public static final long TOKEN_EXPIRE_TIME = 3600 * 1000 * 8; //1h
     private static final String ISSUER = "admin";

     /**
       * 根据userId生成Token
       * @param userId 用户标识（不一定是用户名，有可能是用户ID或者手机号什么的）
       * @param secretKey
       * @return
       */
     public static String generateTokenByUserId(String userId, String secretKey) {
         Algorithm algorithm = Algorithm.HMAC256(secretKey);
         Date now = new Date();
         Date expireTime = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

         String token = JWT.create()
                 .withIssuer(ISSUER) // issuer：jwt签发人
                 .withIssuedAt(now) // iat: jwt的签发时间
                 .withJWTId(userId) // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                 .withExpiresAt(expireTime)  //设置过期时间
                 .withClaim("userId", userId) // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                 .sign(algorithm);

         return token;

     }

     //根据用户信息生成token
    public static String generateTokenByUserInfo(String userId, String subject,String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        String token = JWT.create()
                .withIssuer(ISSUER) // issuer：jwt签发人
                .withIssuedAt(now) // iat: jwt的签发时间
                .withJWTId(userId) // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .withExpiresAt(expireTime)  //设置过期时间
                .withClaim("userId", userId) // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .withSubject(subject) //// sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .sign(algorithm);

        return token;

    }


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

    //token转用户信息
    public static JwtModel getUserInfo(String token) throws JsonProcessingException {
        DecodedJWT decodedJWT = JWT.decode(token);
        String userId = decodedJWT.getClaim("userId").asString();
        String subject = decodedJWT.getSubject();
        JSONObject jsonObject = JSONObject.parseObject(subject);

        JwtModel jwtModel = new JwtModel();
        jwtModel.setUserId(jsonObject.getString("userId"));
        jwtModel.setUsername(jsonObject.getString("username"));
        return jwtModel;
    }

}