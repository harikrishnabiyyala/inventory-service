package com.application.microservices.inventory;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

//	@ServiceConnection
//	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0");

//	@ServiceConnection
//	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0")
//			.withDatabaseName("testdb")
//			.withUsername("testuser")
//			.withPassword("testpass");

//	@ServiceConnection
//	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0")
//		.withCommand("--skip-host-cache", "--skip-name-resolve")
//		.waitingFor(Wait.forListeningPort()) // Wait until the container is listening on its port
//		.withStartupTimeout(Duration.ofMinutes(3)); // Optional: Extend timeout if needed
//

//	@DynamicPropertySource
//	static void configureProperties(DynamicPropertyRegistry registry) {
//		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//		registry.add("spring.datasource.username", mySQLContainer::getUsername);
//		registry.add("spring.datasource.password", mySQLContainer::getPassword);
//	}


	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

//	static {
//		mySQLContainer.start();
//	}

	@Test
	void shouldReadInventory() {

		var response = RestAssured.given()
				.when()
				.get("/api/inventory?skuCode=iphone_15&quantity=1")
				.then()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		assertTrue(response);

		var negativeResponse = RestAssured.given()
				.when()
				.get("/api/inventory?skuCode=iphone_15&quantity=1000")
				.then()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		assertFalse(negativeResponse);

	}

}

