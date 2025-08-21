package com.ant.test.controller;

import com.ant.test.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * TODO
 * </P>
 *
 * @author Ant
 * @since 2025/8/21 23:27
 **/
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final OrderService orderService;

    /**
     * 基础限流测试 - 每秒最多10次请求
     */
    @GetMapping("/basic")
    public ResponseEntity<String> basicTest() {
        return ResponseEntity.ok("Basic rate limit test - " + LocalDateTime.now());
    }

    /**
     * 高并发测试 - 每秒最多5次
     */
    @GetMapping("/high-concurrent")
    public ResponseEntity<Map<String, Object>> highConcurrentTest() {
        Map<String, Object> result = new HashMap<>();
        result.put("timestamp", System.currentTimeMillis());
        result.put("message", "High concurrent test");
        result.put("thread", Thread.currentThread().getName());

        // 模拟一些处理时间
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 基于用户ID的限流
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<String> userRateLimit(@PathVariable String userId) {
        return ResponseEntity.ok("User: " + userId + " - " + LocalDateTime.now());
    }

    /**
     * 订单创建限流 - 模拟实际业务
     */
    @PostMapping("/order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> orderData) {
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", UUID.randomUUID().toString());
        result.put("status", "CREATED");
        result.put("timestamp", LocalDateTime.now());

        // 调用服务层
        orderService.processOrder(orderData);

        return ResponseEntity.ok(result);
    }

    /**
     * 无限制接口 - 用于对比
     */
    @GetMapping("/unlimited")
    public ResponseEntity<String> unlimited() {
        return ResponseEntity.ok("Unlimited - " + LocalDateTime.now());
    }
}
   
