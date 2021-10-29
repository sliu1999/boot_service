package sliu.config.jwt.vo;

import lombok.Data;

/**
 * 登录成功返回参数
 */
@Data
public class LoginResponse {
    private String userId;
    private String username;
    private String token;
    private String refreshToken;


}
