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

class AreaConversionStrategyTest {

    private AreaConversionStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new AreaConversionStrategy();
    }

    @Test
    void testMetricConversion_SquareMetersToSquareCentimeters() {
        ConversionRequest request = new ConversionRequest(1.0, "m²", "cm²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertTrue(result.isSuccess());
        assertEquals(10000.0, result.getConvertedValue());
    }

    @Test
    void testMetricConversion_SquareCentimetersToSquareMeters() {
        ConversionRequest request = new ConversionRequest(50000.0, "cm²", "m²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertTrue(result.isSuccess());
        assertEquals(5.0, result.getConvertedValue());
    }

    @Test
    void testImperialConversion_SquareFeetToSquareInches() {
        ConversionRequest request = new ConversionRequest(1.0, "ft²", "in²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertTrue(result.isSuccess());
        assertEquals(144.0, result.getConvertedValue(), 0.01);
    }

    @Test
    void testImperialConversion_SquareYardsToSquareFeet() {
        ConversionRequest request = new ConversionRequest(1.0, "yd²", "ft²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertTrue(result.isSuccess());
        assertEquals(9.0, result.getConvertedValue(), 0.01);
    }

    @Test
    void testCrossSystemConversion_SquareMetersToSquareFeet() {
        ConversionRequest request = new ConversionRequest(1.0, "m²", "ft²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertTrue(result.isSuccess());
        assertEquals(10.764, result.getConvertedValue(), 0.01);
    }

    @Test
    void testCrossSystemConversion_SquareFeetToSquareMeters() {
        ConversionRequest request = new ConversionRequest(100.0, "ft²", "m²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertTrue(result.isSuccess());
        assertEquals(9.290, result.getConvertedValue(), 0.01);
    }

    @Test
    void testExtremeConversion_SquareKilometersToSquareMillimeters() {
        ConversionRequest request = new ConversionRequest(0.000001, "km²", "mm²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertTrue(result.isSuccess());
        assertEquals(1000000.0, result.getConvertedValue());
    }

    @Test
    void testLargeArea_SquareMilesToSquareInches() {
        ConversionRequest request = new ConversionRequest(1.0, "mi²", "in²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertTrue(result.isSuccess());
        assertEquals(4.0144896E9, result.getConvertedValue(), 1E7);
    }

    @Test
    void testInvalidUnit() {
        ConversionRequest request = new ConversionRequest(1.0, "invalidAreaUnit", "m²", ConversionType.AREA);
        ConversionResult result = strategy.convert(request);

        assertFalse(result.isSuccess());
        assertNotNull(result.getErrorMessage());
    }

    @Test
    void testSupportsAreaType() {
        assertTrue(strategy.supports(ConversionType.AREA));
        assertFalse(strategy.supports(ConversionType.DISTANCE));
        assertFalse(strategy.supports(ConversionType.VOLUME));
    }
}