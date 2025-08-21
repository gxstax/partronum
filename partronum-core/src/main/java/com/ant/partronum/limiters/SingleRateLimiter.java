package com.ant.partronum.limiters;

import com.ant.partronum.alg.FixedTimeWinRateLimitAlg;
import com.ant.partronum.alg.RateLimitAlg;
import com.ant.partronum.exceptions.InternalErrorException;
import com.ant.partronum.rule.ApiLimit;
import com.ant.partronum.rule.RateLimitRule;
import com.ant.partronum.rule.RuleConfig;
import com.ant.partronum.rule.TrieRateLimitRule;
import com.ant.partronum.rule.datasource.FileRuleConfigSource;
import com.ant.partronum.rule.datasource.RuleConfigSource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * <p>
 * 限流继承入口(单机)
 * </p>
 *
 * @author Ant
 * @since 2020/6/3 9:12 上午
 */
public class SingleRateLimiter implements RateLimiter {

    private static final Logger log = Logger.getLogger(SingleRateLimiter.class.getName());

    // 为每个api在内存中存储限流计数器
    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();

    private RateLimitRule rule;

    public SingleRateLimiter() {
        // 调用RuleConfigSource类来实现配置加载
        RuleConfigSource configSource = new FileRuleConfigSource();
        RuleConfig ruleConfig = configSource.load();
        this.rule = new TrieRateLimitRule(ruleConfig);
    }

    /**
     * 单机限流
     *
     * @param ruleConfig
     */
    public SingleRateLimiter(RuleConfig ruleConfig) {
        if (null == ruleConfig) {
            // 调用RuleConfigSource类来实现配置加载
            RuleConfigSource configSource = new FileRuleConfigSource();
            ruleConfig = configSource.load();
        }
        this.rule = new TrieRateLimitRule(ruleConfig);;
    }

    public boolean limit(String appId, String api) throws InternalErrorException {
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
