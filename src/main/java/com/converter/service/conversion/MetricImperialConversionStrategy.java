package com.converter.service.conversion;

import java.util.Map;

import com.converter.model.ConversionRequest;
import com.converter.model.ConversionResult;
import com.converter.model.ImperialUnit;
import com.converter.model.MetricPrefix;

public abstract class MetricImperialConversionStrategy extends AbstractConversionStrategy {
    
    protected abstract String getMetricBaseSuffix();
    protected abstract String getImperialCategory();
    protected abstract double getMetricToImperialBaseFactor();
    
    @Override
    public ConversionResult convert(ConversionRequest request) {
        try {
            Double result = convertUnit(
                request.getValue(),
                request.getFromUnit(),
                request.getToUnit()
            );
            
            return new ConversionResult(
                request.getValue(),
                request.getFromUnit(),
                result,
                request.getToUnit(),
                request.getType()
            );
            
        } catch (Exception e) {
            ConversionResult error = new ConversionResult();
            error.setSuccess(false);
            error.setErrorMessage(e.getMessage());
            return error;
        }
    }
    
    private Double convertUnit(Double value, String from, String to) {
        boolean fromIsMetric = isMetricUnit(from);
        boolean toIsMetric = isMetricUnit(to);
        
        if (fromIsMetric && toIsMetric) {
            return convertMetric(value, from, to);
        } else if (!fromIsMetric && !toIsMetric) {
            return convertImperial(value, from, to);
        } else {
            return convertBetweenSystems(value, from, to, fromIsMetric);
        }
    }
    
    private Double convertMetric(Double value, String from, String to) {
        String fromPrefix = extractPrefix(from);
        String toPrefix = extractPrefix(to);
        
        MetricPrefix fromMetric = MetricPrefix.fromSymbol(fromPrefix);
        MetricPrefix toMetric = MetricPrefix.fromSymbol(toPrefix);
        
        return MetricPrefix.convert(value, fromMetric.getPower(), toMetric.getPower());
    }
    
    private Double convertImperial(Double value, String from, String to) {
        return ImperialUnit.convert(value, from, to, getImperialCategory());
    }
    
    private Double convertBetweenSystems(Double value, String from, String to, boolean fromIsMetric) {
        if (fromIsMetric) {
            double baseMetric = convertMetric(value, from, getMetricBaseSuffix());
            double baseImperial = baseMetric * getMetricToImperialBaseFactor();
            return convertImperial(baseImperial, getFirstImperialUnit(), to);
        } else {
            double baseImperial = convertImperial(value, from, getFirstImperialUnit());
            double baseMetric = baseImperial / getMetricToImperialBaseFactor();
            return convertMetric(baseMetric, getMetricBaseSuffix(), to);
        }
    }
    
    private String getFirstImperialUnit() {
        for (ImperialUnit unit : ImperialUnit.values()) {
            if (unit.getCategory().equals(getImperialCategory()) && unit.getFactorToBase() == 1.0) {
                return unit.getSymbol();
            }
        }
        throw new IllegalStateException("No base imperial unit found for category: " + getImperialCategory());
    }
    
    private boolean isMetricUnit(String unit) {
        return unit.endsWith(getMetricBaseSuffix()) || unit.equals(getMetricBaseSuffix());
    }
    
    private String extractPrefix(String unit) {
        if (unit.equals(getMetricBaseSuffix())) return "";
        return unit.substring(0, unit.length() - getMetricBaseSuffix().length());
    }
    
    @Override
    protected Map<String, Double> getFactors() {
        return Map.of();
    }
}