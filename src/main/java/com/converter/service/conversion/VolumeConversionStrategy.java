package com.converter.service.conversion;

import org.springframework.stereotype.Component;

import com.converter.model.ConversionType;

@Component
public class VolumeConversionStrategy extends MetricImperialConversionStrategy {
    
    @Override
    protected String getMetricBaseSuffix() {
        return "l";
    }
    
    @Override
    protected String getImperialCategory() {
        return "volume";
    }
    
    @Override
    protected double getMetricToImperialBaseFactor() {
        return 33.814;
    }
    
    @Override
    public boolean supports(ConversionType type) {
        return type == ConversionType.VOLUME;
    }
}