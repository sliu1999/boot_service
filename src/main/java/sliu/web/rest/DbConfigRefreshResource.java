package sliu.web.rest;

import sliu.unit.MySysParameterOperate;
import sliu.unit.ResponseUtil;
import sliu.unit.sysTask.SystemTaskOperate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(
        tags = {"数据库配置信息信息"},
        description = "定时任务和系统缓存"
)
@RestController
@RequestMapping({"/api"})
public class DbConfigRefreshResource {

    @PostMapping({"/sys-task/refresh"})
    @ApiOperation(
            value = "刷新定时任务",
            notes = "刷新系统定时任务",
            code = 200,
            produces = "application/json"
    )
    public ResponseEntity<?> refreshSysTask() {
        try {
            SystemTaskOperate.getInstance().refreshSystemTask();
            return ResponseUtil.success("系统定时任务刷新成功！");
        } catch (Exception var2) {
            var2.printStackTrace();
            return ResponseUtil.error(var2.getMessage());
        }
    }

    @PostMapping({"/sys-task/current"})
    @ApiOperation(
            value = "查询定时任务",
            notes = "查询当前加载的系统定时任务",
            code = 200,
            produces = "application/json"
    )
    public ResponseEntity<?> currentSysTask() {
        try {
            HashMap<String, Object> result = SystemTaskOperate.getInstance().getTaskObjList();
            return ResponseUtil.success(result);
        } catch (Exception var2) {
            var2.printStackTrace();
            return ResponseUtil.error(var2.getMessage());
        }
    }

    @PostMapping({"/sys-params/refresh"})
    @ApiOperation(
            value = "刷新系统参数",
            notes = "刷新系统参数",
            code = 200,
            produces = "application/json"
    )
    public ResponseEntity<?> refreshSysParams() {
        try {
            MySysParameterOperate.getInstance().refreshSysParameters();
            return ResponseUtil.success("系统参数刷新成功！");
        } catch (Exception var2) {
            var2.printStackTrace();
            return ResponseUtil.error(var2.getMessage());
        }
    }

    @PostMapping({"/sys-params/current"})
    @ApiOperation(
            value = "查询系统参数",
            notes = "查询当前所有加载的系统参数",
            code = 200,
            produces = "application/json"
    )
    public ResponseEntity<?> currentSysParams() {
        try {
            Map<String, String> result = MySysParameterOperate.getInstance().getSysParamsMap();
            return ResponseUtil.success(result);
        } catch (Exception var2) {
            var2.printStackTrace();
            return ResponseUtil.error(var2.getMessage());
        }
    }
}
