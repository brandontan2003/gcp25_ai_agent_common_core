package com.gcp.agent25.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> String writeValueAsString(T value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}
