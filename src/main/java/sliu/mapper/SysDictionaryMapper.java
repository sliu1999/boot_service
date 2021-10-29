package sliu.mapper;

import sliu.domain.SysDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SysDictionary record);

    SysDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDictionary record);

    List<SysDictionary> selectAll();

    List<SysDictionary> getList(@Param("ids") List<Long> ids);

    List<SysDictionary> getListByParentId(Long parentId);
}