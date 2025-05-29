package com.converter.service.conversion;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.converter.model.ConversionRequest;
import com.converter.model.ConversionResult;
import com.converter.model.ConversionType;

@Component
public class TemperatureConversionStrategy extends AbstractConversionStrategy {
    
    @Override
    public ConversionResult convert(ConversionRequest request) {
        try {
            Double result = convertTemperature(
                request.getValue(),
                request.getFromUnit(),
                request.getToUnit()
            );
            
            ConversionResult conversionResult = new ConversionResult(
                request.getValue(),
                request.getFromUnit(),
                result,
                request.getToUnit(),
                request.getType()
            );
            
            conversionResult.setFormula(getFormula(request.getFromUnit(), request.getToUnit()));
            return conversionResult;
            
        } catch (Exception e) {
            ConversionResult error = new ConversionResult();
            error.setSuccess(false);
            error.setErrorMessage(e.getMessage());
            return error;
        }
    }
    
    private Double convertTemperature(Double value, String from, String to) {
        String conversion = from.toUpperCase() + "_TO_" + to.toUpperCase();
        
        switch (conversion) {
            case "C_TO_F":
                return (value * 9.0 / 5.0) + 32.0;
            case "F_TO_C":
                return (value - 32.0) * 5.0 / 9.0;
            case "C_TO_K":
                return value + 273.15;
            case "K_TO_C":
                return value - 273.15;
            case "F_TO_K":
                return (value - 32.0) * 5.0 / 9.0 + 273.15;
            case "K_TO_F":
                return (value - 273.15) * 9.0 / 5.0 + 32.0;
            case "C_TO_C":
            case "F_TO_F":
            case "K_TO_K":
                return value;
            default:
                throw new IllegalArgumentException("Unsupported temperature conversion: " + from + " to " + to);
        }
    }
    
    private String getFormula(String from, String to) {
        String conversion = from.toUpperCase() + "_TO_" + to.toUpperCase();
        
        switch (conversion) {
            case "C_TO_F":
                return "F = (C × 9/5) + 32";
            case "F_TO_C":
                return "C = (F - 32) × 5/9";
            case "C_TO_K":
                return "K = C + 273.15";
            case "K_TO_C":
                return "C = K - 273.15";
            case "F_TO_K":
                return "K = (F - 32) × 5/9 + 273.15";
            case "K_TO_F":
                return "F = (K - 273.15) × 9/5 + 32";
            default:
                return null;
        }
    }
    
    @Override
    public boolean supports(ConversionType type) {
        return type == ConversionType.TEMPERATURE;
    }
    
    @Override
    protected Map<String, Double> getFactors() {
        return Map.of();
    }
}