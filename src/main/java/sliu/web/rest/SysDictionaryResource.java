package sliu.web.rest;


import sliu.domain.SysDictionary;
import sliu.service.SysDictionaryService;
import sliu.unit.ResponseUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Api(tags = "SysDictionary")
@RestController
@RequestMapping("/api/SysDictionary")
public class SysDictionaryResource {

    private final Logger log = LoggerFactory.getLogger(SysDictionaryResource.class);

    private static final String ENTITY_NAME = "sysDictionary";

    @Autowired
    private SysDictionaryService sysDictionaryService;


    @ApiOperation(value = "新增__SysDictionary__", notes = "新增一个__SysDictionary__", response = ResponseUtil.Response.class)
    @ApiImplicitParam(name = "sysDictionary", value = "__SysDictionary__", required = true, paramType = "body", dataType = "SysDictionary")
    @PostMapping("/sysDictionarys")
    public ResponseEntity<Map> createSysDictionary(@Valid @RequestBody SysDictionary sysDictionary) throws URISyntaxException {
        log.debug("REST request to save SysDictionary : {}", sysDictionary);
        try {
            int result = sysDictionaryService.insertSelective(sysDictionary);
            if (result == 1) {
                return ResponseUtil.success("");
            } else {
                return ResponseUtil.error("新增失败！");
            }
        }catch (Exception e){
        return ResponseUtil.error(e.getMessage());
        }
    }


    @ApiOperation(value = "更新__SysDictionary__", notes = "根据指定wid 更新一个__SysDictionary__", response = ResponseUtil.Response.class)
    @ApiImplicitParam(name = "sysDictionary", value = "__SysDictionary__", required = true, paramType = "body", dataType = "SysDictionary")
    @PutMapping("/sysDictionarys")
    public ResponseEntity<Map> updateSysDictionary(@Valid @RequestBody SysDictionary sysDictionary) {
        log.debug("REST request to update SysDictionary : {}", sysDictionary);
        try {
            int result = sysDictionaryService.updateByPrimaryKeySelective(sysDictionary);
            if (result == 1) {
                return ResponseUtil.success("");
            } else {
                return ResponseUtil.error("更新失败");
            }
        }catch (Exception e){
            return ResponseUtil.error(e.getMessage());
        }
    }


    @GetMapping("/sysDictionarys")
    @ApiOperation(value = "分页获取__SysDictionary__", notes = "分页获取__SysDictionary__列表", response = ResponseUtil.Response.class)
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(name = "pageNum", value = "分页参数：第几页", required = true, paramType = "query", dataType = "int"),
                    @ApiImplicitParam(name = "pageSize", value = "分页参数：每页数量", required = true, paramType = "query", dataType = "int")
            }
    )
    public ResponseEntity<Map> queryByPageSysDictionarys(@ApiIgnore @RequestParam Map params) {
        try {
            PageInfo<SysDictionary> result = sysDictionaryService.queryByPage(params);
            if (result != null) {
                return ResponseUtil.success(result);
            } else {
                return ResponseUtil.error("有错误");
            }
        }catch (Exception e){
            return ResponseUtil.error(e.getMessage());
        }
    }


    @ApiOperation(value = "获取__SysDictionary__", notes = "根据wid 获取一个__SysDictionary__", response = ResponseUtil.Response.class)
    @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
    @GetMapping("/sysDictionarys/{id}")
    public ResponseEntity<Map> getSysDictionary(@PathVariable Long id) {
        SysDictionary result = sysDictionaryService.selectByPrimaryKey(id);
        if (result != null) {
            return ResponseUtil.success(result);
        } else {
            return ResponseUtil.error("获取失败！");
        }
    }

    @ApiOperation(value = "删除__SysDictionary__", notes = "根据wid 删除一个__SysDictionary__", response = ResponseUtil.Response.class)
    @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "path")
    @DeleteMapping("/sysDictionarys/{id}")
    public ResponseEntity<Map> deleteSysDictionary(@PathVariable Long id) {
        log.debug("REST request to delete SysDictionary: {}", id);
        int result = sysDictionaryService.deleteByPrimaryKey(id);
        if(result == 1){
            return ResponseUtil.success("删除成功！");
        }else{
            return ResponseUtil.error("删除失败！");
        }
    }

    @GetMapping(value = "/list/{parentId}")
    @ApiOperation(value = "获取系统字典表详情", notes = "通过条件获取系统字典表全部详情")
    public ResponseEntity<Map> getLists(@PathVariable(value = "parentId") Long parentId){
        SysDictionary result = new SysDictionary();
        if (0 == parentId) {
            result.setId(0L);
            result.setName("一级字典");
        }else {
            result = sysDictionaryService.selectByPrimaryKey(parentId);
        }
        List<SysDictionary> children = sysDictionaryService.getListByParentId(parentId);
        result.setChildren(children);
        return ResponseUtil.success(result);
    }

    /********************************************************************************
     * @Title: getDictionaryTREE
     * @Description: 通过条件获取系统组织机构表全部列表
     ********************************************************************************/
    @GetMapping(value = "/tree/{id}")
    @ApiOperation(value = "获取tree", notes = "获取tree")
    public ResponseEntity<Map> getTree(@PathVariable(value = "id") Long id) {
        SysDictionary result = sysDictionaryService.getTree(id);
        if (result != null) {
            return ResponseUtil.success(result);
        } else {
            return ResponseUtil.error("获取失败！");
        }
    }

    @GetMapping(value = "/getList/{ids}")
    @ApiOperation(value = "获取字符串字典详情名字", notes = "获取字符串字典详情名字")
    public ResponseEntity<Map> getList(@PathVariable(value = "ids") String ids) {
        try {
            String[] idArr = ids.split(",");
            List<Long> idList = new ArrayList(Arrays.asList(idArr));
            List<SysDictionary> result = sysDictionaryService.getList(idList);
            if (result != null) {
                return ResponseUtil.success(result);
            } else {
                return ResponseUtil.error("有错误");
            }
        }catch (Exception e){
            return ResponseUtil.error(e.getMessage());
        }
    }


    @GetMapping(value = "/getNameByIds/{ids}")
    @ApiOperation(value = "获取字符串字典详情名字", notes = "获取字符串字典详情名字")
    public ResponseEntity<Map> getNameByIds(@PathVariable(value = "ids") String ids)
    {
        List<String> resultName = new ArrayList<>();
        for(String id:ids.split(","))
        {
            SysDictionary result = sysDictionaryService.selectByPrimaryKey(Long.valueOf(id));
            resultName.add(result.getName());
        }
        return ResponseUtil.success(resultName);
    }

}
