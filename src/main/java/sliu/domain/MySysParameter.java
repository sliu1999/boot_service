package sliu.domain;

import lombok.Data;

@Data
public class MySysParameter {

    private String id;
    private String parameterId;
    private String parameterValue;
    private String remark;
    private String canDelete;


}