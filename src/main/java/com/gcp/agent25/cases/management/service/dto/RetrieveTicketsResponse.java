package com.gcp.agent25.cases.management.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveTicketsResponse implements Serializable {

    private List<RetrieveTicketResponse> tickets;

}
