package com.ant.partronum.alg;

import com.ant.partronum.exceptions.InternalErrorException;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 基于滑动时间窗口限流算法类
 * </p>
 *
 * @author GaoXin
 * @since 2021/9/25 6:13 下午
 */
public class FixedTimeWinRateLimitAlg implements RateLimitAlg {

    /* timeout for {@code Lock.tryLock() }. */
    private static final long TRY_LOCK_TIMEOUT = 200L; //200ms

    private Stopwatch stopwatch;

    private AtomicInteger currentCount = new AtomicInteger(0);

    private final Integer limit;

    private Lock lock = new ReentrantLock();

    public FixedTimeWinRateLimitAlg (Integer limit) {
        this.limit = limit;
    }

    @Override
    public boolean tryAcquire() {
        int updateCount = currentCount.incrementAndGet();
        if (updateCount <= limit) {
            return true;
        }

        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.SECONDS)) {
                try {
                    // 超过一秒则重置
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMillis(1)) {
                        currentCount.set(0);
                        stopwatch.reset();
                    }
                    updateCount = currentCount.incrementAndGet();
                    return updateCount <= limit;
                } finally {
                    lock.unlock();
                }
            } else {
                throw new InternalErrorException("tryAcquire() wait lock too long:" + TRY_LOCK_TIMEOUT + "ms");
            }
        } catch (InterruptedException e) {
            throw new InternalErrorException("tryAcquire() is interrupted by lock-time-out.", e);
        }
    }

}
