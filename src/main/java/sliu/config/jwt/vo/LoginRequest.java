package sliu.config.jwt.vo;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录请求参数
 */
@Data
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;

    private String code;
    private String uuid;

}
