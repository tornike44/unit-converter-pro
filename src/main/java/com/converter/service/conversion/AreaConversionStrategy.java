package com.converter.service.conversion;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.converter.model.ConversionType;

@Component
public class AreaConversionStrategy extends AbstractConversionStrategy {

    private static final Map<String, Double> AREA_FACTORS = Map.ofEntries(
            Map.entry("mm²", 1e-6), // square millimeter to m²
            Map.entry("cm²", 1e-4), // square centimeter to m²
            Map.entry("m²", 1.0), // square meter (base)
            Map.entry("km²", 1e6), // square kilometer to m²
            Map.entry("in²", 0.00064516), // square inch to m² (exact)
            Map.entry("ft²", 0.09290304), // square foot to m² (exact)
            Map.entry("yd²", 0.83612736), // square yard to m² (exact)
            Map.entry("mi²", 2589988.110336) // square mile to m² (exact)
    );

    @Override
    protected Map<String, Double> getFactors() {
        return AREA_FACTORS;
    }

    @Override
    public boolean supports(ConversionType type) {
        return type == ConversionType.AREA;
    }
}