package sliu.service;

import sliu.config.jwt.vo.LoginRequest;
import sliu.config.jwt.vo.JwtModel;

public interface AuthUserService {

    JwtModel isUser(LoginRequest loginRequest);
}
