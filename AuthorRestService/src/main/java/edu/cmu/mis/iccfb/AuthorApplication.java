package edu.cmu.mis.iccfb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import edu.cmu.mis.iccfb.SeedData;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class AuthorApplication {
    @Autowired
    private SeedData seedData;
    public static void main(String[] args) {

        SpringApplication.run(AuthorApplication.class, args);

    }
}
