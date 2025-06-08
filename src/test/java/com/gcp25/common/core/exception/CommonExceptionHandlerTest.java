package com.gcp25.common.core.exception;

import com.gcp25.common.core.TestUtils;
import com.gcp25.common.core.constant.TestConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

import static com.gcp25.common.core.constant.ApiConstant.STATUS_ERROR;
import static com.gcp25.common.core.exception.CommonErrorMessage.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CommonExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String VALIDATE_URL = "/validate";
    private static final String ACCESS_DENIED_URL = "/access-denied";
    private static final String MISSING_PARAM_URL = "/missing-param";
    private static final String METHOD_NOT_ALLOWED_URL = "/method-not-allowed";
    private static final String API_FAILURE_URL = "/api-failure";
    public static final String ERROR_MESSAGE = "Something went wrong.";

    @RestController
    static class TestController {

        @PostMapping(VALIDATE_URL)
        public String validateRequest(@RequestBody @Valid TestRequest request) {
            return "Valid";
        }

        @GetMapping(ACCESS_DENIED_URL)
        public void accessDenied() throws AccessDeniedException {
            throw new AccessDeniedException("Access denied");
        }

        @GetMapping(MISSING_PARAM_URL)
        public String missingParam(@RequestParam String requiredParam) {
            return "Valid";
        }

        @PostMapping(METHOD_NOT_ALLOWED_URL)
        public String methodNotAllowed() {
            return "Valid";
        }

        @PostMapping(API_FAILURE_URL)
        public String apiExecutionFailure() throws ApiExecutionException {
            throw new ApiExecutionException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MESSAGE);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    static class TestRequest {
        @NotBlank(message = "Name is required")
        private String name;
    }

    @Test
    void shouldReturnBadRequestForInvalidInput() throws Exception {
        mockMvc.perform(post(VALIDATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.writeValueAsString(TestRequest.builder().build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(TestConstant.JSON_PATH_STATUS).value(STATUS_ERROR))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_CODE).value(FIELD_VALIDATION_ERROR.getErrorCode()))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_MESSAGE).value("Name is required"));
    }

    @Test
    void shouldReturnForbiddenForAccessDeniedException() throws Exception {
        mockMvc.perform(get(ACCESS_DENIED_URL))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath(TestConstant.JSON_PATH_STATUS).value(STATUS_ERROR))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_CODE).value(FORBIDDEN_ERROR.getErrorCode()))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_MESSAGE).value(FORBIDDEN_ERROR.getErrorMessage()));
    }

    @Test
    void shouldReturnBadRequestForMissingRequestParam() throws Exception {
        mockMvc.perform(get(MISSING_PARAM_URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(TestConstant.JSON_PATH_STATUS).value(STATUS_ERROR))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_CODE).value(FIELD_VALIDATION_ERROR.getErrorCode()))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_MESSAGE).value("requiredParam is required."));
    }

    @Test
    void shouldReturnMethodNotAllowedForWrongMethod() throws Exception {
        mockMvc.perform(get(METHOD_NOT_ALLOWED_URL))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath(TestConstant.JSON_PATH_STATUS).value(STATUS_ERROR))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_CODE).value(METHOD_NOT_ALLOWED_ERROR.getErrorCode()))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_MESSAGE).value(METHOD_NOT_ALLOWED_ERROR.getErrorMessage()));
    }

    @Test
    void shouldReturnApiExecutionErrorForApiFailure() throws Exception {
        mockMvc.perform(post(API_FAILURE_URL))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath(TestConstant.JSON_PATH_STATUS).value(STATUS_ERROR))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_CODE).value(API_EXECUTION_ERROR.getErrorCode()))
                .andExpect(jsonPath(TestConstant.JSON_PATH_ERRORS_MESSAGE).value(ERROR_MESSAGE));
    }
}
