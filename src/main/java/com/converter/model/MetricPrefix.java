package com.converter.model;

public enum MetricPrefix {
    PICO("p", -12),
    NANO("n", -9),
    MICRO("μ", -6),
    MILLI("m", -3),
    CENTI("c", -2),
    DECI("d", -1),
    UNIT("", 0),
    DEKA("da", 1),
    HECTO("h", 2),
    KILO("k", 3),
    MEGA("M", 6),
    GIGA("G", 9),
    TERA("T", 12);
    
    private final String symbol;
    private final int power;
    
    MetricPrefix(String symbol, int power) {
        this.symbol = symbol;
        this.power = power;
    }
    
    public String getSymbol() { return symbol; }
    public int getPower() { return power; }
    
    public static MetricPrefix fromSymbol(String symbol) {
        for (MetricPrefix prefix : values()) {
            if (prefix.symbol.equals(symbol)) {
                return prefix;
            }
        }
        throw new IllegalArgumentException("Unknown prefix: " + symbol);
    }
    
    public static double convert(double value, int fromPower, int toPower) {
        return value * Math.pow(10, fromPower - toPower);
    }
}