package com.converter.service.conversion;

import org.springframework.stereotype.Component;

import com.converter.model.ConversionType;

@Component
public class DistanceConversionStrategy extends MetricImperialConversionStrategy {
    
    @Override
    protected String getMetricBaseSuffix() {
        return "m";
    }
    
    @Override
    protected String getImperialCategory() {
        return "distance";
    }
    
    @Override
    protected double getMetricToImperialBaseFactor() {
        return 39.3701;
    }
    
    @Override
    public boolean supports(ConversionType type) {
        return type == ConversionType.DISTANCE;
    }
}