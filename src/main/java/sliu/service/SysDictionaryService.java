package sliu.service;

import sliu.domain.SysDictionary;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface SysDictionaryService{

    int deleteByPrimaryKey(Long id);


    int insertSelective(SysDictionary record);

    SysDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDictionary record);


    PageInfo<SysDictionary> queryByPage(Map params);

    List<SysDictionary> selectAll();

    List<SysDictionary> getList(List<Long> ids);

    List<SysDictionary> getListByParentId(Long parentId);

    SysDictionary getTree(Long id);
}