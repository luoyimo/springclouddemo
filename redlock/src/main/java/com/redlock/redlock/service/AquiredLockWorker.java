package com.redlock.redlock.service;

/**
 * @Author huqi
 * @Description:
 * @Date Create In 14:39 2018/5/17 0017
 */
public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
