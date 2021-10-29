package sliu.service.impl;

import sliu.domain.SysDictionary;
import sliu.mapper.SysDictionaryMapper;
import sliu.service.SysDictionaryService;
import sliu.unit.ServiceUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictionaryServiceImpl implements SysDictionaryService {


    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysDictionaryMapper.deleteByPrimaryKey(id);
    }


    @Override
    public int insertSelective(SysDictionary record) {
        return sysDictionaryMapper.insertSelective(record);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public SysDictionary selectByPrimaryKey(Long id) {
        return sysDictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDictionary record) {
        return sysDictionaryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<SysDictionary> selectAll() {
        return sysDictionaryMapper.selectAll();
    }

    @Override
    public List<SysDictionary> getList(List<Long> ids) {
        return sysDictionaryMapper.getList(ids);
    }

    @Override
    public List<SysDictionary> getListByParentId(Long parentId) {
        return sysDictionaryMapper.getListByParentId(parentId);
    }

    @Override
    public SysDictionary getTree(Long id) {
        SysDictionary sysDictionary = sysDictionaryMapper.selectByPrimaryKey(id);
        sysDictionary.setLabel(sysDictionary.getName());
        sysDictionary.setValue(sysDictionary.getId());
        List<SysDictionary> children = sysDictionaryMapper.getListByParentId(id);
        List<SysDictionary> newChildren = new ArrayList<>(3);
        for (SysDictionary sysDictionary1 : children){
            if(sysDictionary1 != null){
                sysDictionary1.setLabel(sysDictionary1.getName());
                sysDictionary1.setValue(sysDictionary1.getId());
                sysDictionary1 = getTree(sysDictionary1.getId());
                newChildren.add(sysDictionary1);
            }
        }
        if(newChildren != null && newChildren.size()>0){
            sysDictionary.setChildren(newChildren);
        }

        return sysDictionary;
    }

    @Override
    public PageInfo<SysDictionary> queryByPage(Map params) {
        ServiceUtil.checkPageParams(params);
        PageHelper.startPage(params);
        List<SysDictionary> labels = sysDictionaryMapper.selectAll();
        return new PageInfo<SysDictionary>(labels);
    }
}