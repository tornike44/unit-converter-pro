package com.converter.model;

import java.time.LocalDateTime;
import java.util.Map;

public class ConversionRequest {
    
    private Double value;
    private String fromUnit;
    private String toUnit;
    private ConversionType type;
    private Map<String, Object> options;
    private LocalDateTime timestamp;
    
    public ConversionRequest() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ConversionRequest(Double value, String fromUnit, String toUnit, ConversionType type) {
        this();
        this.value = value;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.type = type;
    }
    
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    
    public String getFromUnit() { return fromUnit; }
    public void setFromUnit(String fromUnit) { this.fromUnit = fromUnit; }
    
    public String getToUnit() { return toUnit; }
    public void setToUnit(String toUnit) { this.toUnit = toUnit; }
    
    public ConversionType getType() { return type; }
    public void setType(ConversionType type) { this.type = type; }
    
    public Map<String, Object> getOptions() { return options; }
    public void setOptions(Map<String, Object> options) { this.options = options; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}