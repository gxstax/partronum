package com.ant.partronum.rule.parser;

import com.ant.partronum.rule.RuleConfig;

import java.io.InputStream;

/**
 * <p>
 * 限流配置解析接口（yaml格式配置）
 * </p>
 *
 * @author Ant
 * @since 2021/9/25 6:11 下午
 */
public class YamlRuleConfigParser implements RuleConfigParser{

    /**
     * <p>
     * 基于配置文件路径解析规则
     * </p>
     *
     * @param configText
     * @return {@link RuleConfig}
     */
    @Override
    public RuleConfig parse(String configText) {
        return null;
    }

    /**
     * <p>
     * 基于输入流解析规则
     * </p>
     *
     * @param in
     * @return {@link RuleConfig}
     */
    @Override
    public RuleConfig parse(InputStream in) {
        return null;
    }
}
