package com.converter.model;

import java.time.LocalDateTime;

public class ConversionResult {
    
    private Double originalValue;
    private String originalUnit;
    private Double convertedValue;
    private String convertedUnit;
    private ConversionType type;
    private String formula;
    private LocalDateTime timestamp;
    private boolean success;
    private String errorMessage;
    
    public ConversionResult() {
        this.timestamp = LocalDateTime.now();
        this.success = true;
    }
    
    public ConversionResult(Double originalValue, String originalUnit, 
                           Double convertedValue, String convertedUnit, ConversionType type) {
        this();
        this.originalValue = originalValue;
        this.originalUnit = originalUnit;
        this.convertedValue = convertedValue;
        this.convertedUnit = convertedUnit;
        this.type = type;
    }
    
    public Double getOriginalValue() { return originalValue; }
    public void setOriginalValue(Double originalValue) { this.originalValue = originalValue; }
    
    public String getOriginalUnit() { return originalUnit; }
    public void setOriginalUnit(String originalUnit) { this.originalUnit = originalUnit; }
    
    public Double getConvertedValue() { return convertedValue; }
    public void setConvertedValue(Double convertedValue) { this.convertedValue = convertedValue; }
    
    public String getConvertedUnit() { return convertedUnit; }
    public void setConvertedUnit(String convertedUnit) { this.convertedUnit = convertedUnit; }
    
    public ConversionType getType() { return type; }
    public void setType(ConversionType type) { this.type = type; }
    
    public String getFormula() { return formula; }
    public void setFormula(String formula) { this.formula = formula; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}