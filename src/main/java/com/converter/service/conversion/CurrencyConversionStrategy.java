package com.converter.service.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.converter.model.ConversionRequest;
import com.converter.model.ConversionResult;
import com.converter.model.ConversionType;
import com.converter.model.dto.ExchangeRateResponse;

@Service
public class CurrencyConversionStrategy implements ConversionStrategy {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${exchange.api.base-url}")
    private String baseUrl;

    @Override
    public ConversionResult convert(ConversionRequest request) {
        try {
            String from = request.getFromUnit().toUpperCase();
            String to = request.getToUnit().toUpperCase();

            if (from.equals(to)) {
                return new ConversionResult(
                        request.getValue(),
                        from,
                        request.getValue(),
                        to,
                        ConversionType.CURRENCY);
            }

            Double rate = getExchangeRate(from, to);
            Double convertedValue = request.getValue() * rate;

            ConversionResult result = new ConversionResult(
                    request.getValue(),
                    from,
                    convertedValue,
                    to,
                    ConversionType.CURRENCY);

            result.setFormula(String.format("1 %s = %.6f %s", from, rate, to));
            return result;

        } catch (Exception e) {
            ConversionResult error = new ConversionResult();
            error.setSuccess(false);
            error.setErrorMessage("Currency conversion failed: " + e.getMessage());
            return error;
        }
    }

    @Cacheable(value = "currency-rates", key = "#from + '_' + #to")
    public Double getExchangeRate(String from, String to) {
        try {
            String url = baseUrl + "/" + from;

            ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

            if (response == null || response.getRates() == null) {
                throw new RuntimeException("Invalid response from ExchangeRate API");
            }

            if (!response.getRates().containsKey(to)) {
                throw new RuntimeException("Currency not supported: " + to);
            }

            return response.getRates().get(to);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to get exchange rate from " + from + " to " + to + ": " + e.getMessage());
        }
    }

    @Override
    public boolean supports(ConversionType type) {
        return type == ConversionType.CURRENCY;
    }

    @Override
    public boolean isRealTime() {
        return true;
    }
}