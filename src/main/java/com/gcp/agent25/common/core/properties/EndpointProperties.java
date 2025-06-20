package com.gcp.agent25.common.core.properties;

import lombok.Data;

@Data
public class EndpointProperties {

    private String scheme;
    private String host;
    private Integer port;
    private String uri;

    public String toUrl(String... pathVariables) {
        return String.format("%s://%s:%d%s", scheme, host, port, String.format(uri, (Object[]) pathVariables));
    }

}
