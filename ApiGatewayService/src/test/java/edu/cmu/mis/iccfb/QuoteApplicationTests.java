package edu.cmu.mis.iccfb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
@EnableCircuitBreaker
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuoteApplicationTests {

	@Test
	public void contextLoads() {
	}

}