package sliu.service;

import sliu.domain.SysTask;

import java.util.HashMap;
import java.util.List;

public interface SysTaskService {
    List<SysTask> selectAllAbledTask();

    Boolean isEnabled();

    public List<HashMap> queryAllSysParameter();
}
