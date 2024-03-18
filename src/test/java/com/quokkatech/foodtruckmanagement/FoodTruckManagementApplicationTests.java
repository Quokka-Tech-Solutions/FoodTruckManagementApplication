package com.quokkatech.foodtruckmanagement;

import com.quokkatech.foodtruckmanagement.integrationTests.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
class FoodTruckManagementApplicationTests {

	@Test
	void contextLoads() {
	}

}
