package com.ant.partronum.rule.parser;

import com.ant.partronum.rule.RuleConfig;

import java.io.InputStream;

/**
 * <p>
 * 限流配置解析接口
 * </p>
 *
 * @author GaoXin
 * @since 2021/9/25 6:02 下午
 */
public interface RuleConfigParser {
    
    /**
     * <p>
     * 基于配置文件路径解析规则
     * </p>
     *
     * @param configText
     * @return {@link RuleConfig}
     */
    RuleConfig parse(String configText);
    
    /**
     * <p>
     * 基于输入流解析规则
     * </p>
     *
     * @param in
     * @return {@link RuleConfig}
     */
    RuleConfig parse(InputStream in);
}
