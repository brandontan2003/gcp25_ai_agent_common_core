package com.gcp.agent25.common.core.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorsPayload {

    private List<ErrorPayload> errors;

}
