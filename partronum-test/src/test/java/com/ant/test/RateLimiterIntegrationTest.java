package com.ant.test;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * TODO
 * </P>
 *
 * @author Ant
 * @since 2025/8/21 23:46
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RateLimiterIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    /**
     * 测试基础限流功能
     */
    @Test
    @Order(1)
    public void testBasicRateLimit() throws InterruptedException {
        String url = baseUrl + "/api/test/basic";

        // 发送15个请求（超过限制10个）
        List<HttpStatus> statuses = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            statuses.add(response.getStatusCode());
            Thread.sleep(50); // 每50ms发送一个请求
        }

        // 验证：应该有5个请求被拒绝
        long successCount = statuses.stream()
                .filter(status -> status == HttpStatus.OK)
                .count();
        long forbiddenCount = statuses.stream()
                .filter(status -> status == HttpStatus.TOO_MANY_REQUESTS)
                .count();

        assertThat(successCount).isLessThanOrEqualTo(10);
        assertThat(forbiddenCount).isGreaterThanOrEqualTo(5);

        System.out.println("Basic rate limit test - Success: " + successCount +
                ", Forbidden: " + forbiddenCount);
    }

    /**
     * 测试用户级别限流
     */
    @Test
    @Order(2)
    public void testUserRateLimit() throws InterruptedException {
        String url1 = baseUrl + "/api/test/user/user1";
        String url2 = baseUrl + "/api/test/order";

        // user1 发送5个请求（限制是3个）
        List<HttpStatus> user1Statuses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity(url1, String.class);
            user1Statuses.add(response.getStatusCode());
            Thread.sleep(100);
        }

        // user2 发送5个请求（限制是3个）
        List<HttpStatus> user2Statuses = new ArrayList<>();
        for (int i = 0; i < 555; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity(url2, String.class);
            user2Statuses.add(response.getStatusCode());
            Thread.sleep(100);
        }

        // 验证：每个用户都应该有2个请求被拒绝
        long user1Forbidden = user1Statuses.stream()
                .filter(status -> status == HttpStatus.TOO_MANY_REQUESTS)
                .count();
        long user2Forbidden = user2Statuses.stream()
                .filter(status -> status == HttpStatus.TOO_MANY_REQUESTS)
                .count();

        assertThat(user1Forbidden).isGreaterThanOrEqualTo(2);
        assertThat(user2Forbidden).isGreaterThanOrEqualTo(2);

        System.out.println("User rate limit test - User1 forbidden: " + user1Forbidden +
                ", User2 forbidden: " + user2Forbidden);
    }

    /**
     * 测试订单创建接口的限流功能
     */
    @Test
    @Order(1)
    void testCreateOrderRateLimit() throws InterruptedException {
        String createOrderUrl = baseUrl + "/api/test/order";

        // 准备订单数据
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("productId", "PROD001");
        orderData.put("quantity", 2);
        orderData.put("amount", 199.98);

        // 发送10个创建订单请求（限制是每秒2个）
        List<HttpStatus> statuses = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    createOrderUrl,
                    orderData,
                    Map.class
            );

            statuses.add(response.getStatusCode());

            // 每100ms发送一个请求（比限制频率高）
            Thread.sleep(100);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 验证结果
        long successCount = statuses.stream()
                .filter(status -> status == HttpStatus.OK)
                .count();
        long forbiddenCount = statuses.stream()
                .filter(status -> status == HttpStatus.TOO_MANY_REQUESTS)
                .count();

        // 期望：在1秒内，应该有大约2个成功，8个被限流
        // 由于测试时间跨度约1秒，允许一定的误差
        assertThat(successCount).isLessThanOrEqualTo(5); // 最多5个成功（考虑突发容量）
        assertThat(forbiddenCount).isGreaterThanOrEqualTo(5); // 至少5个被限流

        System.out.println("=== Create Order Rate Limit Test ===");
        System.out.println("Total requests: 10");
        System.out.println("Success: " + successCount);
        System.out.println("Too Many Requests: " + forbiddenCount);
        System.out.println("Duration: " + duration + "ms");
        System.out.println("Success rate: " + (successCount * 10.0) + "%");

        // 验证成功响应的格式
        if (successCount > 0) {
            ResponseEntity<Map> firstSuccess = restTemplate.postForEntity(
                    createOrderUrl, orderData, Map.class
            );
            Map<String, Object> responseBody = firstSuccess.getBody();

            assertThat(responseBody).isNotNull();
            assertThat(responseBody).containsKey("orderId");
            assertThat(responseBody).containsKey("status");
            assertThat(responseBody).containsKey("timestamp");
            assertThat(responseBody.get("status")).isEqualTo("CREATED");
        }
    }

}
   
