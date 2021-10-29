package sliu.mapper;

import sliu.domain.SysTask;

import java.util.HashMap;
import java.util.List;

public interface SysTaskMapper {
    List<SysTask> selectAllAbledTask();

    List<String> selectTableNames();

    List<HashMap> queryAllSysParameter();
}
