package com.ant.partronum.rule;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * 应用配置
 * </p>
 *
 * @author GaoXin
 * @since 2021/9/25 6:00 下午
 */
public class TrieRateLimitRule implements RateLimitRule {

    private RuleConfig ruleConfig;

    public TrieRateLimitRule(RuleConfig ruleConfig) {
        this.ruleConfig = ruleConfig;
    }

//    public boolean limit(String appId, String api) {
//        AtomicBoolean limit = new AtomicBoolean(false);
//        ruleConfig.getConfigs().stream().forEach(config -> {
//            if (config.getAppId().equals(appId)) {
//                for (ApiLimit apiLimit : config.getLimits()) {
//                    if (apiLimit.getApi().equals(api)) {
//                        limit.set(true);
//                    }
//                }
//            }
//        });
//        return limit.get();
//    }

    @Override
    public ApiLimit getLimit(String appId, String api) {
        for (AppRuleConfig config : this.ruleConfig.getConfigs()) {
            if (config.getAppId().equals(appId)) {
                for (ApiLimit limit : config.getLimits()) {
                    if (limit.getApi().equals(api)) {
                        return limit;
                    }
                }
            }
        }
        return null;
    }
}
