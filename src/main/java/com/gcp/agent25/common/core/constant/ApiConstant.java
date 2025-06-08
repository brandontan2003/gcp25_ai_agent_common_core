package com.gcp.agent25.common.core.constant;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class ApiConstant {

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_ERROR = "ERROR";
    public static final String BEARER = "Bearer ";

    public static Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        headers.put(ACCEPT, APPLICATION_JSON_VALUE);
        return headers;
    }

    public static Map<String, String> getDefaultAuthHeaders(String token) {
        Map<String, String> headers = getDefaultHeaders();
        headers.put(AUTHORIZATION, BEARER + token);
        return headers;
    }

}
