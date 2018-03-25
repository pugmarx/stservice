package cm.blzcln.stservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
 
 
@SpringBootApplication(scanBasePackages={"cm.blzcln.stservice"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories("cm.blzcln.stservice.repository")
public class StudentApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
    
}