package sliu.unit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class MySpringManager implements ApplicationListener<ContextRefreshedEvent> {
    private static ApplicationContext applicationContext = null;

    public MySpringManager() {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (applicationContext == null) {
            applicationContext = event.getApplicationContext();
        }

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}