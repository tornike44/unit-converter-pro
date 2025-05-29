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

class VolumeConversionStrategyTest {
    
    private VolumeConversionStrategy strategy;
    
    @BeforeEach
    void setUp() {
        strategy = new VolumeConversionStrategy();
    }
    
    @Test
    void testMetricConversion_LitersToMilliliters() {
        ConversionRequest request = new ConversionRequest(2.0, "l", "ml", ConversionType.VOLUME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(2000.0, result.getConvertedValue());
    }
    
    @Test
    void testMetricConversion_MillilitersToLiters() {
        ConversionRequest request = new ConversionRequest(1500.0, "ml", "l", ConversionType.VOLUME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(1.5, result.getConvertedValue());
    }
    
    @Test
    void testImperialConversion_GallonsToFluidOunces() {
        ConversionRequest request = new ConversionRequest(1.0, "gallon", "fl_oz", ConversionType.VOLUME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(128.0, result.getConvertedValue());
    }
    
    @Test
    void testImperialConversion_CupsToPints() {
        ConversionRequest request = new ConversionRequest(4.0, "cup", "pint", ConversionType.VOLUME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(2.0, result.getConvertedValue());
    }
    
    @Test
    void testCrossSystemConversion_LitersToFluidOunces() {
        ConversionRequest request = new ConversionRequest(1.0, "l", "fl_oz", ConversionType.VOLUME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(33.814, result.getConvertedValue(), 0.001);
    }
    
    @Test
    void testCrossSystemConversion_GallonsToLiters() {
        ConversionRequest request = new ConversionRequest(1.0, "gallon", "l", ConversionType.VOLUME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(3.785, result.getConvertedValue(), 0.01);
    }
    
    @Test
    void testExtremeConversion_KilolitersToMilliliters() {
        ConversionRequest request = new ConversionRequest(0.001, "kl", "ml", ConversionType.VOLUME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(1000.0, result.getConvertedValue());
    }
    
    @Test
    void testInvalidUnit() {
        ConversionRequest request = new ConversionRequest(1.0, "invalidUnit", "l", ConversionType.VOLUME);
        ConversionResult result = strategy.convert(request);
        
        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessage());
    }
    
    @Test
    void testSupportsVolumeType() {
        assertTrue(strategy.supports(ConversionType.VOLUME));
        assertFalse(strategy.supports(ConversionType.DISTANCE));
        assertFalse(strategy.supports(ConversionType.WEIGHT));
    }
}