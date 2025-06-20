package com.gcp.agent25.common.core.service;

import com.gcp.agent25.cases.management.service.dto.CreateTicketRequest;
import com.gcp.agent25.cases.management.service.dto.RetrieveTicketResponse;
import com.gcp.agent25.common.core.properties.TicketEndpointProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketWebClientService {
    @Autowired
    private WebClientService webClientService;

    @Autowired
    private TicketEndpointProperties properties;

    ParameterizedTypeReference<RetrieveTicketResponse> retrieveTicketParameterizedTypeReference =
            new ParameterizedTypeReference<>() {
            };

    public RetrieveTicketResponse createTicket(CreateTicketRequest request) {
        String url = properties.getCreateTicket().toUrl();
        log.info("Calling Create Ticket Request, URL :::: {} Request Body ::::: {}", url, request);
        return webClientService.post(url, request, retrieveTicketParameterizedTypeReference).getBody();
    }
}
