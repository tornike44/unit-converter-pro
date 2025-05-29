package com.converter.service.conversion;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.converter.model.ConversionType;

@Component
public class TimeConversionStrategy extends AbstractConversionStrategy {
    
    private static final Map<String, Double> TIME_FACTORS = Map.ofEntries(
        Map.entry("ns", 1e-9),           // nanosecond
        Map.entry("μs", 1e-6),           // microsecond
        Map.entry("us", 1e-6),           // microsecond (alternative)
        Map.entry("ms", 1e-3),           // millisecond
        Map.entry("s", 1.0),             // second (base unit)
        Map.entry("min", 60.0),          // minute
        Map.entry("h", 3600.0),          // hour
        Map.entry("day", 86400.0),       // day
        Map.entry("week", 604800.0),     // week (7 * 24 * 3600)
        Map.entry("month", 2629746.0),   // month (30.44 * 24 * 3600)
        Map.entry("year", 31557600.0)    // year (365.25 * 24 * 3600)
    );
    
    @Override
    protected Map<String, Double> getFactors() {
        return TIME_FACTORS;
    }
    
    @Override
    public boolean supports(ConversionType type) {
        return type == ConversionType.TIME;
    }
}