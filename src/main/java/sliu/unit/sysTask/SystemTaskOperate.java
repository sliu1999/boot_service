package sliu.unit.sysTask;

import sliu.domain.SysTask;
import sliu.service.SysTaskService;
import sliu.unit.MySpringManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

@Component
@Order(4)
public class SystemTaskOperate implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(SystemTaskOperate.class);
    private static SystemTaskOperate singleton;
    private ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    private int taskSchedulerCorePoolSize = 50;
    private HashMap<String, Object> taskObjList = new HashMap();
    private static String KEY_TASK = "task";
    private static String KEY_FUTURE = "future";
    @Autowired
    private SysTaskService sysTaskService;

    private SystemTaskOperate() {
        this.threadPoolTaskScheduler.setPoolSize(this.taskSchedulerCorePoolSize);
        this.threadPoolTaskScheduler.initialize();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getInstance().refreshSystemTask();
    }

    public HashMap<String, Object> getTaskObjList() {
        return this.taskObjList;
    }

    public static synchronized SystemTaskOperate getInstance() {
        if (singleton == null) {
            ApplicationContext wac = MySpringManager.getApplicationContext();
            singleton = (SystemTaskOperate)wac.getBean("systemTaskOperate");
        }

        return singleton;
    }

    public synchronized void refreshSystemTask() {
        this.stopAllSystemTask();
        if (this.sysTaskService.isEnabled()) {
            List<SysTask> sysTasks = this.sysTaskService.selectAllAbledTask();
            sysTasks.forEach((task) -> {
                try {
                    logger.info("启动定时任务：" + task.getName());
                    ScheduledFuture<?> future = this.threadPoolTaskScheduler.schedule(new SysTaskRunable(task), new CronTrigger(task.getCron()));
                    HashMap<String, Object> taskObj = new HashMap(16);
                    taskObj.put(KEY_TASK, task);
                    taskObj.put(KEY_FUTURE, future);
                    this.taskObjList.put(task.getId().toString(), taskObj);
                    logger.info("定时任务 " + task.getName() + " 启动成功！");
                } catch (Exception var4) {
                    logger.info("定时任务 " + task.getName() + " 启动失败！");
                    var4.printStackTrace();
                }

            });
        } else {
            logger.info("系统定时任务表不存在");
        }

    }

    public synchronized void stopAllSystemTask() {
        if (this.taskObjList == null) {
            this.taskObjList = new HashMap(16);
        }

        if (this.taskObjList.keySet().size() != 0) {
            Set<String> keySet = this.taskObjList.keySet();
            keySet.forEach((key) -> {
                try {
                    HashMap<String, Object> taskObj = (HashMap)this.taskObjList.get(key);
                    ScheduledFuture<?> future = (ScheduledFuture)taskObj.get(KEY_FUTURE);
                    if (future != null) {
                        future.cancel(true);
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            });
            this.taskObjList.clear();
        }
    }

    @PreDestroy
    public void destory() {
        this.stopAllSystemTask();
    }
}
