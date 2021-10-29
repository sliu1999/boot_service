package sliu.web.rest.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(
        description = "字典表查询参数"
)
@Data
public class DicSearchVO {
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("排序字段")
    private String orderBy;
    @ApiModelProperty("返回对象名")
    private String objName;
}