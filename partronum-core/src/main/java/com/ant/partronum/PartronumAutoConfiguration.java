package com.ant.partronum;

import com.ant.partronum.properties.PartronumProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * TODO
 * </P>
 *
 * @author Ant
 * @since 2022/11/01 12:23 上午
 **/
@Configuration
@EnableConfigurationProperties(PartronumProperties.class)
public class PartronumAutoConfiguration {

}
