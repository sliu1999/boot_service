package sliu.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysDictionary {
    private Long id;

    private Long parentId;

    private String name;

    private Integer level;

    private Long sort;

    private Integer status;

    private String remarks;

    private List<SysDictionary> children;

    private String label;
    private Long value;
}