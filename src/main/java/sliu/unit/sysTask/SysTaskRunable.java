package sliu.unit.sysTask;


import sliu.domain.SysTask;
import sliu.unit.MySpringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class SysTaskRunable  implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(SysTaskRunable.class);
    private SysTask sysTask;

    public SysTaskRunable(SysTask sysTask) {
        this.sysTask = sysTask;
    }

    @Override
    public void run() {
        try {
            ApplicationContext wac = MySpringManager.getApplicationContext();
            Object obj = wac.getBean(this.sysTask.getMethod());
            Method method = obj.getClass().getMethod("doTask");
            method.invoke(obj);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public Long getTaskId() {
        return this.sysTask.getId();
    }
}
