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

class TemperatureConversionStrategyTest {
    
    private TemperatureConversionStrategy strategy;
    
    @BeforeEach
    void setUp() {
        strategy = new TemperatureConversionStrategy();
    }
    
    @Test
    void testCelsiusToFahrenheit() {
        ConversionRequest request = new ConversionRequest(0.0, "C", "F", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(32.0, result.getConvertedValue());
        assertEquals("F = (C × 9/5) + 32", result.getFormula());
    }
    
    @Test
    void testFahrenheitToCelsius() {
        ConversionRequest request = new ConversionRequest(32.0, "F", "C", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(0.0, result.getConvertedValue());
        assertEquals("C = (F - 32) × 5/9", result.getFormula());
    }
    
    @Test
    void testCelsiusToKelvin() {
        ConversionRequest request = new ConversionRequest(0.0, "C", "K", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(273.15, result.getConvertedValue());
        assertEquals("K = C + 273.15", result.getFormula());
    }
    
    @Test
    void testKelvinToCelsius() {
        ConversionRequest request = new ConversionRequest(273.15, "K", "C", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(0.0, result.getConvertedValue());
        assertEquals("C = K - 273.15", result.getFormula());
    }
    
    @Test
    void testFahrenheitToKelvin() {
        ConversionRequest request = new ConversionRequest(32.0, "F", "K", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(273.15, result.getConvertedValue());
        assertEquals("K = (F - 32) × 5/9 + 273.15", result.getFormula());
    }
    
    @Test
    void testKelvinToFahrenheit() {
        ConversionRequest request = new ConversionRequest(273.15, "K", "F", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(32.0, result.getConvertedValue());
        assertEquals("F = (K - 273.15) × 9/5 + 32", result.getFormula());
    }
    
    @Test
    void testSameUnitConversion() {
        ConversionRequest request = new ConversionRequest(25.0, "C", "C", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(25.0, result.getConvertedValue());
    }
    
    @Test
    void testRealWorldTemperatures() {
        ConversionRequest request = new ConversionRequest(20.0, "C", "F", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(68.0, result.getConvertedValue());
    }
    
    @Test
    void testBodyTemperature() {
        ConversionRequest request = new ConversionRequest(98.6, "F", "C", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(37.0, result.getConvertedValue(), 0.1);
    }
    
    @Test
    void testInvalidUnit() {
        ConversionRequest request = new ConversionRequest(25.0, "X", "C", ConversionType.TEMPERATURE);
        ConversionResult result = strategy.convert(request);
        
        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessage());
    }
    
    @Test
    void testSupportsTemperatureType() {
        assertTrue(strategy.supports(ConversionType.TEMPERATURE));
        assertFalse(strategy.supports(ConversionType.DISTANCE));
        assertFalse(strategy.supports(ConversionType.VOLUME));
    }
}