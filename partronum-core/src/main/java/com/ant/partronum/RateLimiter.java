package com.ant.partronum;

import com.ant.partronum.alg.FixedTimeWinRateLimitAlg;
import com.ant.partronum.alg.RateLimitAlg;
import com.ant.partronum.rule.ApiLimit;
import com.ant.partronum.rule.RateLimitRule;
import com.ant.partronum.rule.RuleConfig;
import com.ant.partronum.rule.TrieRateLimitRule;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * 限流继承入口
 * </p>
 *
 * @author Ant
 * @since 2020/6/3 9:12 上午
 */
public class RateLimiter {
    private static final Logger log = Logger.getLogger(RateLimiter.class.getName());

    // 为每个api在内存中存储限流计数器 (key: AppId + api)
    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();

    // 限流规则
    private RateLimitRule rule;

    // 读取限流规则
    public RateLimiter() {
        // 从配置文件中读取配置信息
        RuleConfig ruleConfig = null;
        try (InputStream resourceStream =
                     this.getClass().getResourceAsStream("META-INF/ratelimiter-rule.yaml")) {
            if (null != resourceStream) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(resourceStream, RuleConfig.class);
            }
        } catch (IOException e) {
            log.log(Level.WARNING, "读取配置失败", e);
        }

        // 将限流规则构建成支持快速查找的数据结构 RateLimitRule
        this.rule = new TrieRateLimitRule(ruleConfig);
    }

    // 判断是否需要限流

    public boolean limit(String appId, String api) {
        ApiLimit apiLimit = rule.getLimit(appId, api);
        if (null == apiLimit) {
            return true;
        }

        String limitKey = String.format("%S:%S", appId, api);
        RateLimitAlg rateLimitAlg = counters.get(limitKey);
        if (null == rateLimitAlg) {
            RateLimitAlg newRateLimitAlg = new FixedTimeWinRateLimitAlg(apiLimit.getLimit());
            rateLimitAlg = counters.putIfAbsent(limitKey, newRateLimitAlg);
            if (null == rateLimitAlg) {
                rateLimitAlg = newRateLimitAlg;
            }
        }

        // 判断是否限流
        return rateLimitAlg.tryAcquire();
    }
}
