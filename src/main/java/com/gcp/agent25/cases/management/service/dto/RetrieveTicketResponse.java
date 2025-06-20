package com.gcp.agent25.cases.management.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveTicketResponse implements Serializable {

    private String ticketId;
    private String title;
    private String description;
    private String assignee;
    private String priority;
    private String status;

}
