package com.converter.service.conversion;

import org.springframework.stereotype.Component;

import com.converter.model.ConversionType;

@Component
public class WeightConversionStrategy extends MetricImperialConversionStrategy {
    
    @Override
    protected String getMetricBaseSuffix() {
        return "g";
    }
    
    @Override
    protected String getImperialCategory() {
        return "weight";
    }
    
    @Override
    protected double getMetricToImperialBaseFactor() {
        return 28.3495;
    }
    
    @Override
    public boolean supports(ConversionType type) {
        return type == ConversionType.WEIGHT;
    }
}