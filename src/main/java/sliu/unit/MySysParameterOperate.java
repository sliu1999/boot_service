package sliu.unit;


import sliu.domain.MySysParameter;
import sliu.service.MySysParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@Order(11)
public class MySysParameterOperate implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(MySysParameterOperate.class);
    private static MySysParameterOperate singleton;
    private Map<String, String> sysParamsMap;
    @Autowired
    private MySysParameterService sysParameterService;

    private MySysParameterOperate() {
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getInstance().refreshSysParameters();
    }

    public static synchronized MySysParameterOperate getInstance() {
        if (singleton == null) {
            ApplicationContext wac = MySpringManager.getApplicationContext();
            singleton = (MySysParameterOperate)wac.getBean("mySysParameterOperate");
        }

        return singleton;
    }

    public synchronized String getSysParameterByName(String param) {
        if (this.sysParamsMap == null || this.sysParamsMap.size() == 0) {
            logger.info("有请求获取系统参数,但是系统参数尚未初始化，正在尝试重新初始化");
            this.initSysParameterFromDb();
        }

        logger.info("直接从缓存中查找参数" + param);
        if (this.sysParamsMap.get(param) != null && !"".equals(this.sysParamsMap.get(param))) {
            return (String)this.sysParamsMap.get(param);
        } else {
            logger.info("获取参数为空:" + param);
            return null;
        }
    }

    public synchronized boolean refreshSysParameters() {
        logger.info("读取系统内存");
        this.clearParamters();

        try {
            this.initSysParameterFromDb();
            return true;
        } catch (Exception var2) {
            logger.error(var2.getMessage(), var2);
            return false;
        }
    }

    public Map<String, String> getSysParamsMap() {
        return this.sysParamsMap;
    }

    private void clearParamters() {
        if (this.sysParamsMap == null) {
            this.sysParamsMap = new HashMap(1024);
        } else {
            this.sysParamsMap.clear();
        }

        logger.info("系统参数清理完毕");
    }

    private synchronized void initSysParameterFromDb() {
        try {
            if (this.sysParameterService.isEnabled()) {
                List<MySysParameter> listSp = this.sysParameterService.selectAllList();
                if (this.sysParamsMap == null) {
                    this.sysParamsMap = new HashMap(1024);
                }

                Iterator i = listSp.iterator();

                while(i.hasNext()) {
                    MySysParameter so = (MySysParameter)i.next();
                    this.sysParamsMap.put(so.getParameterId(), so.getParameterValue());
                }
            } else {
                logger.info("系统参数表不存在");
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        logger.info("系统参数初始化完毕");
    }
}
