package sliu.service;

import sliu.domain.MySysParameter;

import java.util.List;

public interface MySysParameterService {
    List<MySysParameter> selectAllList();

    Boolean isEnabled();
}
