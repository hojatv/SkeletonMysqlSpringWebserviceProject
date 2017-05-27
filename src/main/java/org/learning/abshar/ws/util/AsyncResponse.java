package org.learning.abshar.ws.util;

import java.util.concurrent.*;

public class AsyncResponse<V> implements Future<V> {
    private V value;
    private Exception executionException;
    private boolean isCompletedExceptionally = false;
    private boolean isCancelled = false;
    private boolean isDone = false;
    private long checkCompletedInterval = 100;

    public AsyncResponse() {

    }

    public AsyncResponse(V value) {
        this.value = value;
        isDone = true;
    }

    public AsyncResponse(Throwable ex) {
        this.executionException = new ExecutionException(ex);
        this.isCompletedExceptionally = true;
        this.isDone = true;

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        this.isDone = true;

        return false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    public boolean isCompletedExceptionally() {
        return this.isCompletedExceptionally;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        block(0);
        if(isCancelled()){
            throw new CancellationException();
        }
        if(isCompletedExceptionally()){
            throw new ExecutionException(this.executionException);
        }
        if(isDone){
            return this.value;
        }
        throw new InterruptedException();
    }

    private V block(long timeout) throws InterruptedException {
        long start = System.currentTimeMillis();

        //Block until done, cancelled or timeout is exceeded
        while (!isDone && !isCancelled) {
            if (timeout > 0) {
                long now = System.currentTimeMillis();
                if (now > start + timeout) {
                    break;
                }
            }
            Thread.sleep(checkCompletedInterval);
        }
        return null;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        long timeoutInMillis = unit.toMillis(timeout);
        block(timeout);
        if (isCancelled) {
            throw new CancellationException();
        }
        if (isCompletedExceptionally) {
            throw new ExecutionException(this.executionException);
        }
        if (isDone) {
            return this.value;
        }
        throw new InterruptedException();
    }

    public long getCheckCompletedInterval() {
        return checkCompletedInterval;
    }

    public void setCheckCompletedInterval(long checkCompletedInterval) {
        this.checkCompletedInterval = checkCompletedInterval;
    }

    public boolean complete(V value) {
        this.value = value;
        this.isDone = true;

        return true;
    }
}
