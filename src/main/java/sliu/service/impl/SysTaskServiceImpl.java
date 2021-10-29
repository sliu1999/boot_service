package sliu.service.impl;//

import sliu.domain.SysTask;
import sliu.mapper.SysTaskMapper;
import sliu.service.SysTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(
        rollbackFor = {Exception.class}
)
public class SysTaskServiceImpl implements SysTaskService {

    @Autowired
    private SysTaskMapper sysTaskMapper;

    public SysTaskServiceImpl() {
    }

    @Override
    public List<SysTask> selectAllAbledTask() {
        return this.sysTaskMapper.selectAllAbledTask();
    }

    @Override
    public Boolean isEnabled() {
        StringUtils.isEmpty("");
        String e = new String();
        StringBuffer s = new StringBuffer();
        StringBuilder d = new StringBuilder();
        List<String> tables = this.sysTaskMapper.selectTableNames();
        return tables.size() == 1 ? true : false;
    }

    @Override
    public List<HashMap> queryAllSysParameter() {
        return sysTaskMapper.queryAllSysParameter();
    }
}
