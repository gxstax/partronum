package com.ant.partronum;

import com.ant.partronum.limiters.RateLimiter;
import com.ant.partronum.limiters.SingleRateLimiter;
import com.ant.partronum.properties.PartronumProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 自动配置类
 * </P>
 *
 * @author Ant
 * @since 2022/11/01 12:23 上午
 **/
@Configuration
@EnableConfigurationProperties(PartronumProperties.class)
@ConditionalOnProperty(prefix = "spring.partronum", name = "enabled", havingValue = "true", matchIfMissing = false)
public class PartronumAutoConfiguration {

    private final PartronumProperties partronumProperties;

    public PartronumAutoConfiguration(PartronumProperties partronumProperties) {
        this.partronumProperties = partronumProperties;
    }


    @Bean
    @ConditionalOnMissingBean(value = RateLimiter.class)
    public SingleRateLimiter rateLimiter() {
        return new SingleRateLimiter(partronumProperties.getRuleConfig());
    }


}
