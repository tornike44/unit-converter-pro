package com.converter.service.conversion;

import java.util.Map;

import com.converter.model.ConversionRequest;
import com.converter.model.ConversionResult;

public abstract class AbstractConversionStrategy implements ConversionStrategy {

    protected abstract Map<String, Double> getFactors();

    protected Double convertViaFactors(Double value, String from, String to) {
        Map<String, Double> factors = getFactors();
        Double fromFactor = factors.get(from.toLowerCase());
        Double toFactor = factors.get(to.toLowerCase());

        if (fromFactor == null || toFactor == null) {
            throw new IllegalArgumentException("Unsupported unit conversion: " + from + " to " + to);
        }

        Double valueInBase = value * fromFactor;
        return valueInBase / toFactor;
    }

    protected Double convertSpecial(Double value, String from, String to) {
        throw new UnsupportedOperationException("Special conversion not implemented");
    }

    protected boolean hasSpecialConversion(String from, String to) {
        return false;
    }

    @Override
    public ConversionResult convert(ConversionRequest request) {
        try {
            Double result;

            if (hasSpecialConversion(request.getFromUnit(), request.getToUnit())) {
                result = convertSpecial(request.getValue(), request.getFromUnit(), request.getToUnit());
            } else {
                result = convertViaFactors(request.getValue(), request.getFromUnit(), request.getToUnit());
            }

            return new ConversionResult(
                    request.getValue(),
                    request.getFromUnit(),
                    result,
                    request.getToUnit(),
                    request.getType());

        } catch (Exception e) {
            ConversionResult error = new ConversionResult();
            error.setSuccess(false);
            error.setErrorMessage(e.getMessage());
            return error;
        }
    }

    @Override
    public boolean isRealTime() {
        return false;
    }
}