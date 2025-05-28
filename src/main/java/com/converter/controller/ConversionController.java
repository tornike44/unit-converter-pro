package com.converter.controller;

import com.converter.model.ConversionRequest;
import com.converter.model.ConversionResult;
import com.converter.service.conversion.ConversionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/convert")
@CrossOrigin(origins = "*")
public class ConversionController {
    
    @Autowired
    private List<ConversionStrategy> conversionStrategies;
    
    @PostMapping
    public ResponseEntity<ConversionResult> convert(@RequestBody ConversionRequest request) {
        
        ConversionStrategy strategy = conversionStrategies.stream()
            .filter(s -> s.supports(request.getType()))
            .findFirst()
            .orElse(null);
            
        if (strategy == null) {
            ConversionResult error = new ConversionResult();
            error.setSuccess(false);
            error.setErrorMessage("Unsupported conversion type: " + request.getType());
            return ResponseEntity.badRequest().body(error);
        }
        
        ConversionResult result = strategy.convert(request);
        
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Conversion API is running!");
    }
}