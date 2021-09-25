package com.ant.partronum.rule;

import java.util.List;

/**
 * <p>
 * 应用限流配置信息
 * </p>
 *
 * @author Ant
 * @since 2020/6/3 8:57 下午
 */
public class RuleConfig {

    private List<AppRuleConfig> configs;

    public List<AppRuleConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AppRuleConfig> configs) {
        this.configs = configs;
    }
}
