package com.ant.partronum.rule;

/**
 * <p>
 * 限流限制规则
 * </p>
 *
 * @author Ant
 * @since 2020/6/3 9:17 上午
 */
public interface RateLimitRule {

    /**
     * <p>
     * 获取配置信息
     * </p>
     *
     * @param appId
     * @param api
     * @return {@link ApiLimit}
     */
    ApiLimit getLimit(String appId, String api);

}
