package com.converter.model;

public enum ImperialDistanceUnit {
    INCH("in", 1.0),
    FOOT("ft", 12.0),
    YARD("yd", 36.0),
    MILE("mi", 63360.0);
    
    private final String symbol;
    private final double factorToInch;
    
    ImperialDistanceUnit(String symbol, double factorToInch) {
        this.symbol = symbol;
        this.factorToInch = factorToInch;
    }
    
    public String getSymbol() { return symbol; }
    public double getFactorToInch() { return factorToInch; }
    
    public static ImperialDistanceUnit fromSymbol(String symbol) {
        for (ImperialDistanceUnit unit : values()) {
            if (unit.symbol.equals(symbol)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown imperial unit: " + symbol);
    }
    
    public static double convert(double value, String from, String to) {
        ImperialDistanceUnit fromUnit = fromSymbol(from);
        ImperialDistanceUnit toUnit = fromSymbol(to);
        
        double valueInInches = value * fromUnit.getFactorToInch();
        return valueInInches / toUnit.getFactorToInch();
    }
}