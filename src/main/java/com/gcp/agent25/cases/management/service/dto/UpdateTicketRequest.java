package com.gcp.agent25.cases.management.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gcp.agent25.cases.management.service.enums.PriorityEnum;
import com.gcp.agent25.cases.management.service.enums.TicketStatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketRequest implements Serializable {

    @NotBlank(message = "ticketId cannot be blank.")
    private String ticketId;
    private String assignee;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PriorityEnum priority;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TicketStatusEnum status;

}
