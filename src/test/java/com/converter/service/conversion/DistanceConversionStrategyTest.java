package com.converter.service.conversion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.converter.model.ConversionRequest;
import com.converter.model.ConversionResult;
import com.converter.model.ConversionType;

class DistanceConversionStrategyTest {
    
    private DistanceConversionStrategy strategy;
    
    @BeforeEach
    void setUp() {
        strategy = new DistanceConversionStrategy();
    }
    
    @Test
    void testMetricConversion_KilometersToMeters() {
        ConversionRequest request = new ConversionRequest(5.0, "km", "m", ConversionType.DISTANCE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(5000.0, result.getConvertedValue());
    }
    
    @Test
    void testMetricConversion_MillimetersToKilometers() {
        ConversionRequest request = new ConversionRequest(5000000.0, "mm", "km", ConversionType.DISTANCE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(5.0, result.getConvertedValue());
    }
    
    @Test
    void testImperialConversion_FeetToInches() {
        ConversionRequest request = new ConversionRequest(2.0, "ft", "in", ConversionType.DISTANCE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(24.0, result.getConvertedValue());
    }
    
    @Test
    void testCrossSystemConversion_MetersToFeet() {
        ConversionRequest request = new ConversionRequest(1.0, "m", "ft", ConversionType.DISTANCE);
        ConversionResult result = strategy.convert(request);
        
        System.out.println("Success: " + result.isSuccess());
        System.out.println("Error message: " + result.getErrorMessage());
        System.out.println("Converted value: " + result.getConvertedValue());
        
        assertTrue(result.isSuccess());
        assertEquals(3.28084, result.getConvertedValue(), 0.001);
    }
    
    @Test
    void testInvalidUnit() {
        ConversionRequest request = new ConversionRequest(1.0, "invalidUnit", "m", ConversionType.DISTANCE);
        ConversionResult result = strategy.convert(request);
        
        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessage());
    }
}