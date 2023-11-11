package com.circuitbreaker.io;

public class Main {
    public static void main(String[] args) {
        CircuitBreaker<String> CB = new DefaultCircuitBreaker<>(null,2000, 0);
        System.out.println(CB.execute(()->"Fallback"));
    }
}