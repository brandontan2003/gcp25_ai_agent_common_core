package com.gcp.agent25.common.core.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorPayload {

    private String errorCode;
    private String errorMessage;

}
