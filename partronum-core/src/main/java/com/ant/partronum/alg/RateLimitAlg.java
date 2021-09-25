package com.ant.partronum.alg;

import com.ant.partronum.exceptions.InternalErrorException;
import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
