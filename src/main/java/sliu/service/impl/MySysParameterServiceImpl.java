package sliu.service.impl;

import sliu.domain.MySysParameter;
import sliu.mapper.MySysParameterMapper;
import sliu.service.MySysParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(
        rollbackFor = {Exception.class}
)
public class MySysParameterServiceImpl implements MySysParameterService {
    @Autowired
    private MySysParameterMapper sysParameterMapper;


    @Override
    public List<MySysParameter> selectAllList() {
        return this.sysParameterMapper.selectSysParameterListByCondition(new HashMap(16));
    }

    @Override
    public Boolean isEnabled() {
        List<String> tables = this.sysParameterMapper.selectTableNames();
        return tables.size() == 1 ? true : false;
    }
}

