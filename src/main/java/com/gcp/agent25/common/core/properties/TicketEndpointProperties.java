package com.gcp.agent25.common.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "services.ticket")
public class TicketEndpointProperties {

    private EndpointProperties createTicket;

}
