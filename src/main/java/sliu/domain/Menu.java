//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sliu.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(
        description = "菜单"
)
@Data
public class Menu {
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("交互")
    private String action;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("父节点")
    private String parentId;
    @ApiModelProperty("父节点名称")
    private String parentName;
    @ApiModelProperty("样式")
    private String style;
    @ApiModelProperty("菜单类型")
    private Integer menuType;
    @ApiModelProperty("菜单类型名称")
    private String menuTypeName;
    private String roleId;
    private List<Menu> subMenus;
    @ApiModelProperty("绑定此菜单的所有角色")
    private String useRoles;

}
