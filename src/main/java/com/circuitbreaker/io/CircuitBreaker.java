package com.circuitbreaker.io;

import java.util.function.Supplier;

public sealed interface CircuitBreaker<T> permits DefaultCircuitBreaker {
     T execute(Supplier<T> fallback);
     boolean isOpen();
     void close();
}
