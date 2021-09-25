package com.ant.partronum.rule;

import java.util.List;

/**
 * <p>
 * 应用限流配置
 * </p>
 *
 * @author GaoXin
 * @since 2020/6/5 8:32 上午
 */
public class AppRuleConfig {
    private String appId;

    private List<ApiLimit> limits;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<ApiLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<ApiLimit> limits) {
        this.limits = limits;
    }
}
