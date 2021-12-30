package sliu;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"sliu.*"})
@MapperScan("sliu.mapper")
@ServletComponentScan(basePackages = {"sliu.*"})
@NacosPropertySource(dataId = "example" ,autoRefreshed = true)
@EnableScheduling
public class BootServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(BootServiceApplication.class,args);
    }
}
