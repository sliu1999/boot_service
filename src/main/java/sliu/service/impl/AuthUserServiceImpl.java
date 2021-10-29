package sliu.service.impl;

import sliu.mapper.AuthUserMapper;
import sliu.service.AuthUserService;
import sliu.config.jwt.vo.LoginRequest;
import sliu.config.jwt.vo.JwtModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Override
    public JwtModel isUser(LoginRequest loginRequest) {
        JwtModel jwtModel = authUserMapper.queryUserByPassword(loginRequest);
        return jwtModel;
    }
}
