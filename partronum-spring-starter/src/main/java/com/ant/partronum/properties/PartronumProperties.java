package com.ant.partronum.properties;


import com.ant.partronum.rule.RuleConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * 限流框架自动配置信息
 * </P>
 *
 * @author Ant
 * @since 2022/11/01 12:14 上午
 **/
@ConfigurationProperties("spring.partronum")
public class PartronumProperties {

    private Boolean enable = false;

    private RuleConfig ruleConfig;

    public void setRuleConfig(RuleConfig ruleConfig) {
        this.ruleConfig = ruleConfig;
    }

    public RuleConfig getRuleConfig() {
        return ruleConfig;
    }

    public PartronumProperties() {}

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
