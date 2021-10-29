package sliu.domain;

import lombok.Data;

@Data
public class SysTask {
    private Long id;
    private String name;
    private String method;
    private String cron;
    private String enable;

}
