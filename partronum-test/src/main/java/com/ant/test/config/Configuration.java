package com.ant.test.config;

import com.ant.partronum.limiters.RateLimiter;
import com.ant.partronum.limiters.SingleRateLimiter;
import com.ant.partronum.properties.PartronumProperties;
import com.ant.partronum.rule.ApiLimit;
import com.ant.partronum.rule.AppRuleConfig;
import com.ant.partronum.rule.RuleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * TODO
 * </P>
 *
 * @author Ant
 * @since 2025/8/21 23:58
 **/
@org.springframework.context.annotation.Configuration
public class Configuration {

    private PartronumProperties partronumProperties;

    @Bean
    public SingleRateLimiter rateLimiter() {
        PartronumProperties partronumProperties = new PartronumProperties();
        RuleConfig ruleConfig = new RuleConfig();
        partronumProperties.setRuleConfig(ruleConfig);
        List<AppRuleConfig> configs = new ArrayList<>();
        ruleConfig.setConfigs(configs);

        AppRuleConfig appRuleConfig = new AppRuleConfig();
        configs.add(appRuleConfig);
        appRuleConfig.setAppId("app-1");
        List<ApiLimit> limits = new ArrayList<>();
        appRuleConfig.setLimits(limits);
        limits.add(new ApiLimit("/v1/user", 6, 1000));
        return new SingleRateLimiter(partronumProperties.getRuleConfig());
    }
}
   
