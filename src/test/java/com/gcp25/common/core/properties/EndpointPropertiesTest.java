package com.gcp25.common.core.properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EndpointPropertiesTest {

    private static EndpointProperties getEndpointProperties(String uri) {
        return EndpointProperties.builder().scheme("http").host("api.example.com").port(5000).uri(uri).build();
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
