package com.ant.partronum.properties;


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

    public PartronumProperties() {}

    public Boolean isEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
