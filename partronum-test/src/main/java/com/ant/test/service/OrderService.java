package com.ant.test.service;

import com.ant.partronum.limiters.SingleRateLimiter;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>
 * TODO
 * </P>
 *
 * @author Ant
 * @since 2025/8/21 23:28
 **/
@Service
public class OrderService {

    private static final Logger log = Logger.getLogger(OrderService.class.getName());

    private final SingleRateLimiter rateLimiter;

    public OrderService(SingleRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    /**
     * 处理订单业务逻辑
     */
    public void processOrder(Map<String, Object> orderData) {
        log.info("Processing order: {}" + orderData.get("orderId"));

        boolean limit = rateLimiter.limit("app-1", "/v1/user");
        if (limit) {
            System.out.println("Rate limit exceeded for order: " + orderData.get("orderId"));
            return;
        }

        // 模拟业务处理
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Order processed successfully: {}"+ orderData.get("orderId"));
    }

    /**
     * 查询订单 - 较宽松的限流
     */
    public Map<String, Object> queryOrder(String orderId) {
        Map<String, Object> order = new HashMap<>();
        order.put("orderId", orderId);
        order.put("status", "SUCCESS");
        order.put("amount", 99.99);

        return order;
    }
}
   
