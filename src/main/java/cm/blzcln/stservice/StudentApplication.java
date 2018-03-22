package cm.blzcln.stservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
 
@SpringBootApplication(scanBasePackages={"cm.blzcln.stservice"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class StudentApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
    
}