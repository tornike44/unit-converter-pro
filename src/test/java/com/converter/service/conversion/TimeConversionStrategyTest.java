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

class TimeConversionStrategyTest {
    
    private TimeConversionStrategy strategy;
    
    @BeforeEach
    void setUp() {
        strategy = new TimeConversionStrategy();
    }
    
    @Test
    void testBasicConversion_MinutesToSeconds() {
        ConversionRequest request = new ConversionRequest(2.0, "min", "s", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(120.0, result.getConvertedValue());
    }
    
    @Test
    void testBasicConversion_HoursToMinutes() {
        ConversionRequest request = new ConversionRequest(1.0, "h", "min", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(60.0, result.getConvertedValue());
    }
    
    @Test
    void testMillisecondsToSeconds() {
        ConversionRequest request = new ConversionRequest(5000.0, "ms", "s", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(5.0, result.getConvertedValue());
    }
    
    @Test
    void testDaysToHours() {
        ConversionRequest request = new ConversionRequest(1.0, "day", "h", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(24.0, result.getConvertedValue());
    }
    
    @Test
    void testWeeksToDays() {
        ConversionRequest request = new ConversionRequest(2.0, "week", "day", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(14.0, result.getConvertedValue());
    }
    
    @Test
    void testYearsToDays() {
        ConversionRequest request = new ConversionRequest(1.0, "year", "day", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(365.25, result.getConvertedValue());
    }
    
    @Test
    void testMonthsToWeeks() {
        ConversionRequest request = new ConversionRequest(1.0, "month", "week", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(4.348, result.getConvertedValue(), 0.01);
    }
    
    @Test
    void testNanosecondsToMicroseconds() {
        ConversionRequest request = new ConversionRequest(1000.0, "ns", "μs", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(1.0, result.getConvertedValue(), 0.0001);
    }
    
    @Test
    void testMicrosecondsAlternativeSymbol() {
        ConversionRequest request = new ConversionRequest(1000.0, "ns", "us", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(1.0, result.getConvertedValue(), 0.0001);
    }
    
    @Test
    void testSameUnitConversion() {
        ConversionRequest request = new ConversionRequest(42.0, "s", "s", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(42.0, result.getConvertedValue());
    }
    
    @Test
    void testExtremeConversion_YearsToNanoseconds() {
        ConversionRequest request = new ConversionRequest(1.0, "year", "ns", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertTrue(result.isSuccess());
        assertEquals(3.15576E16, result.getConvertedValue(), 1E14);
    }
    
    @Test
    void testInvalidUnit() {
        ConversionRequest request = new ConversionRequest(1.0, "invalidTimeUnit", "s", ConversionType.TIME);
        ConversionResult result = strategy.convert(request);
        
        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessage());
    }
    
    @Test
    void testSupportsTimeType() {
        assertTrue(strategy.supports(ConversionType.TIME));
        assertFalse(strategy.supports(ConversionType.DISTANCE));
        assertFalse(strategy.supports(ConversionType.TEMPERATURE));
    }
}