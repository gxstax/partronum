package com.ant.partronum.rule;

/**
 * <p>
 * Api 限流限制条件
 * </p>
 *
 * @author Ant
 * @since 2020/6/3 8:57 下午
 */
public class ApiLimit {
    // 默认时间单位 1 秒
    private static final int DEFAULT_TIME_UNIT = 1;

    private String api;

    private Integer limit;

    private Integer unit;

    public ApiLimit() {

    }

    public ApiLimit(String api, Integer limit) {
        this(api, limit, DEFAULT_TIME_UNIT);
    }

    public ApiLimit(String api, Integer limit, Integer unit) {
        this.api = api;
        this.limit = limit;
        this.unit = unit;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }
}
