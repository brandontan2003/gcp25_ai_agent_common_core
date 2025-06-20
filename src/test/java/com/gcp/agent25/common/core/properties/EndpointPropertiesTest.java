package com.gcp.agent25.common.core.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EndpointPropertiesTest {

    private static EndpointProperties getEndpointProperties(String uri) {
        EndpointProperties endpointProperties = new EndpointProperties();
        endpointProperties.setScheme("http");
        endpointProperties.setHost("api.example.com");
        endpointProperties.setPort(5000);
        endpointProperties.setUri(uri);
        return endpointProperties;
    }

    @Test
    void test_generateUrl_Success() {
        EndpointProperties endpointProperties = getEndpointProperties("/data");
        assertEquals("http://api.example.com:5000/data", endpointProperties.toUrl());
    }

    @Test
    void test_generateUrlWithVariables_Success() {
        EndpointProperties endpointProperties = getEndpointProperties("/data/%s");
        assertEquals("http://api.example.com:5000/data/123", endpointProperties.toUrl("123"));
    }
}
