package sliu.task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sliu.web.rest.NacosResource;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

/**
 * 获取所有车位状态
 */
@Component
public class GetAllLotsStatusTask {

    @Autowired
    private NacosResource nacosResource;

    private static GetAllLotsStatusTask getAllLotsStatusTask;


    @PostConstruct
    public void init() {
        getAllLotsStatusTask = this;
        getAllLotsStatusTask.nacosResource = this.nacosResource;
    }


    /**
     * 每30秒
     * @throws SQLException
     */
    @Scheduled(cron="0 0 0/1 * * ? ")
    public void outTimePark() throws SQLException {
        System.out.println(nacosResource.getNacosConfIcon());
    }
}
