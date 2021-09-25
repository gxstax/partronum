package com.ant.partronum.rule.datasource;

import com.ant.partronum.rule.RuleConfig;

/**
 * <p>
 * 配置文件数据源接口
 * </p>
 *
 * @author GaoXin
 * @since 2021/9/25 6:37 下午
 */
public interface RuleConfigSource {
    
    /**
     * <p>
     * 加载配置
     * </p>
     *
     * @param 
     * @return {@link RuleConfig}
     */
    RuleConfig load();

}
