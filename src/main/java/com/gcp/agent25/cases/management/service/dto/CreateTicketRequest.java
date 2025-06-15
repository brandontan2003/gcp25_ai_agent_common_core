package com.gcp.agent25.cases.management.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gcp.agent25.cases.management.service.enums.PriorityEnum;
import com.gcp.agent25.cases.management.service.enums.TicketStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketRequest implements Serializable {

    @NotBlank(message = "title cannot be blank.")
    private String title;
    private String description;
    private String assignee;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PriorityEnum priority;
    @NotNull(message = "status cannot be null.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TicketStatusEnum status;

}
