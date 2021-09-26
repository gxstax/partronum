package com.ant.partronum.alg;

/**
 * <p>
 * 限流算法接口
 * </p>
 *
 * @author Ant
 * @since 2020/6/3 9:17 上午
 */
public interface RateLimitAlg {

    boolean tryAcquire();

}
