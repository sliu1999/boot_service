package sliu.mapper;

import sliu.domain.MySysParameter;

import java.util.List;
import java.util.Map;

public interface MySysParameterMapper {
    List<MySysParameter> selectSysParameterListByCondition(Map var1);

    List<String> selectTableNames();
}
