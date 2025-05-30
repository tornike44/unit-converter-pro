package com.converter.service.conversion;

import com.converter.model.ConversionRequest;
import com.converter.model.ConversionResult;
import com.converter.model.ConversionType;

public interface ConversionStrategy {

    ConversionResult convert(ConversionRequest request);

    boolean supports(ConversionType type);

    boolean isRealTime();

}