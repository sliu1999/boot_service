package sliu.mapper;

import sliu.config.jwt.vo.LoginRequest;
import sliu.config.jwt.vo.JwtModel;
import org.mapstruct.Mapper;

@Mapper
public interface AuthUserMapper {

    JwtModel queryUserByPassword(LoginRequest loginRequest);
}
