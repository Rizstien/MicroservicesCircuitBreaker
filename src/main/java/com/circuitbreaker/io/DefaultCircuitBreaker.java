package com.circuitbreaker.io;

import java.util.function.Supplier;

public final class DefaultCircuitBreaker<T> implements CircuitBreaker<T> {
    private Supplier<T> supplier;
    private long resetTimeOut;
    private long lastTimeOut;
    private long thresholdCount;
    private long currentFailureCount;
    private boolean isOpen = false;

    public DefaultCircuitBreaker(Supplier<T> supplier, long resetTimeOut, long thresholdCount) {
        this.supplier = supplier;
        this.resetTimeOut = resetTimeOut;
        this.thresholdCount = thresholdCount;
    }

    @Override
    public T execute(Supplier<T> fallback) {

        if(isOpen() &&  System.currentTimeMillis() - lastTimeOut >= this.resetTimeOut){
            close();
            currentFailureCount = 0;
        }

        if(isOpen()){
            return fallback.get();
        }

        try{
            return this.supplier.get();
        }catch (Exception ex){
            ex.printStackTrace();
            currentFailureCount++;
            if(currentFailureCount >= thresholdCount){
                lastTimeOut = System.currentTimeMillis();
                isOpen = true;
                return fallback.get();
            }
        }
        return this.supplier.get();
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void close() {
        isOpen = false;
    }
}
