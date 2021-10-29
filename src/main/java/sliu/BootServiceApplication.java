package sliu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"sliu.*"})
@MapperScan("sliu.mapper")
@ServletComponentScan(basePackages = {"sliu.*"})
public class BootServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(BootServiceApplication.class,args);
    }
}
