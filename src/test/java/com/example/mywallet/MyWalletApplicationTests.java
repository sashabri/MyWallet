package com.example.mywallet;

import com.example.mywallet.controller.entity.DepositOrWithdrawBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyWalletApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Value("${server.port}")
	private int randomServerPort;

	@Container
	private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
			.withUsername("postgres")
			.withPassword("postgres")
			.withDatabaseName("postgres")
			.withExposedPorts(5432);

	@DynamicPropertySource
	static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:" + postgres.getMappedPort(5432) + "/postgres");
	}

	@Test
	void positiveGetWallet() {
		var response = restTemplate.getForEntity("/api/v1/wallets/0a10b078-6c36-498a-84bb-a886913ea030", Integer.class);

		Assertions.assertEquals(200, response.getStatusCode().value());

		Assertions.assertEquals(500, response.getBody());
	}
	@Test
	void negativeGetWallet() {
		var response = restTemplate.getForEntity("/api/v1/wallets/0a10b078-6c36-498a-84bb-a886913ea031", String.class);

		Assertions.assertEquals(400, response.getStatusCode().value());

		Assertions.assertTrue(response.getBody().contains("Input walletId not found"));
	}

	@Test
	void positiveDeposit() {
		var body = new DepositOrWithdrawBody(UUID.fromString("0a10b078-6c36-498a-84bb-a886913ea030"), "DEPOSIT", 50);

		var response = restTemplate.postForEntity("/api/v1/wallet", body, String.class);

		Assertions.assertEquals(200, response.getStatusCode().value());

		var actualAmount = restTemplate.getForEntity("/api/v1/wallets/0a10b078-6c36-498a-84bb-a886913ea030", Integer.class).getBody();

		Assertions.assertEquals(550, actualAmount);
	}

	@Test
	void positiveWithdraw() {
		var body = new DepositOrWithdrawBody(UUID.fromString("4c669955-a9c3-46b2-81bb-75aeefe345d0"), "WITHDRAW", 50);

		var response = restTemplate.postForEntity("/api/v1/wallet", body, String.class);

		Assertions.assertEquals(200, response.getStatusCode().value());

		var actualAmount = restTemplate.getForEntity("/api/v1/wallets/4c669955-a9c3-46b2-81bb-75aeefe345d0", Integer.class).getBody();

		Assertions.assertEquals(4950, actualAmount);
	}

	@Test
	void negativeWithdraw() {
		var body = new DepositOrWithdrawBody(UUID.fromString("0a10b078-6c36-498a-84bb-a886913ea030"), "WITHDRAW", 550);

		var response = restTemplate.postForEntity("/api/v1/wallet", body, String.class);

		Assertions.assertEquals(400, response.getStatusCode().value());

		Assertions.assertTrue(response.getBody().contains("There are not enough funds in the account"));
	}
}
