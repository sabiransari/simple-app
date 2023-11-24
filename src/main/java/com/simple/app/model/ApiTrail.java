package com.simple.app.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

@Data
public class ApiTrail implements Trail {
    private RequestMethod requestMethod;
    private String requestPath;
    private String requestSource;
    private String requestBody;
    private String responseBody;
    private boolean successful;
    @Override
    public TrailType getType() {
        return TrailType.Api;
    }
}
