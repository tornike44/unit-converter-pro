package com.converter.model;

public enum ImperialUnit {
    INCH("in", "distance", 1.0),
    FOOT("ft", "distance", 12.0),
    YARD("yd", "distance", 36.0),
    MILE("mi", "distance", 63360.0),
    
    OUNCE("oz", "weight", 1.0),
    POUND("lb", "weight", 16.0),
    STONE("st", "weight", 224.0),
    TON("ton", "weight", 32000.0);
    
    private final String symbol;
    private final String category;
    private final double factorToBase;
    
    ImperialUnit(String symbol, String category, double factorToBase) {
        this.symbol = symbol;
        this.category = category;
        this.factorToBase = factorToBase;
    }
    
    public String getSymbol() { return symbol; }
    public String getCategory() { return category; }
    public double getFactorToBase() { return factorToBase; }
    
    public static ImperialUnit findBySymbolAndCategory(String symbol, String category) {
        for (ImperialUnit unit : values()) {
            if (unit.symbol.equals(symbol) && unit.category.equals(category)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown " + category + " unit: " + symbol);
    }
    
    public static double convert(double value, String from, String to, String category) {
        ImperialUnit fromUnit = findBySymbolAndCategory(from, category);
        ImperialUnit toUnit = findBySymbolAndCategory(to, category);
        
        double valueInBase = value * fromUnit.factorToBase;
        return valueInBase / toUnit.factorToBase;
    }
}