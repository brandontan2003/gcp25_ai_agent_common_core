package com.gcp25.common.core.service;

import com.gcp25.common.core.exception.ApiExecutionException;
import com.gcp25.common.core.properties.EndpointProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static com.gcp25.common.core.constant.ApiConstant.getDefaultHeaders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@ExtendWith(MockitoExtension.class)
class WebClientServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;
    @InjectMocks
    private WebClientService webClientService;

    private static final String URL =
            EndpointProperties.builder().scheme("http").host("api.example.com").port(5000).uri("/data").build().toUrl();
    private static final String RESPONSE_BODY = "Success";
    private static final ResponseEntity<String> MOCK_RESPONSE = new ResponseEntity<>(RESPONSE_BODY, HttpStatus.OK);
    private static final ParameterizedTypeReference<String> RESPONSE_TYPE = new ParameterizedTypeReference<>() {};

    private void setupMockApiRequestChain() {
        when(webClient.method(any())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.exchangeToMono(any())).thenReturn(Mono.just(MOCK_RESPONSE));
    }

    private void setupMockApiRequestChainWithRequestBody() {
        when(webClient.method(any())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);

        Answer<WebClient.RequestHeadersSpec<?>> answer = invocation -> requestHeadersSpec;
        when(requestBodySpec.bodyValue(any())).thenAnswer(answer);
        when(requestHeadersSpec.exchangeToMono(any())).thenReturn(Mono.just(MOCK_RESPONSE));
    }

    @Test
    void shouldThrowApiExecutionErrorException_whenApiCallFails() {
        String errorMessage = "API call failed with 502 Bad Gateway. Upstream service is unavailable.";

        when(webClient.method(any())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);

        ClientResponse mockErrorResponse = mock(ClientResponse.class);

        when(requestBodySpec.exchangeToMono(any())).thenAnswer(invocation -> {
            Function<ClientResponse, Mono<ResponseEntity<String>>> func = invocation.getArgument(0);
            return func.apply(mockErrorResponse);
        });

        when(mockErrorResponse.statusCode()).thenReturn(BAD_GATEWAY);
        when(mockErrorResponse.bodyToMono(String.class)).thenReturn(Mono.just(errorMessage));

        ApiExecutionException ex = assertThrows(ApiExecutionException.class, () -> webClientService.get(URL,
                RESPONSE_TYPE));

        verifyWebClientRequestChain(HttpMethod.GET);

        assertEquals(BAD_GATEWAY, ex.getHttpStatusCode());
        assertEquals(errorMessage, ex.getErrorMessage());
    }

    @Test
    void testGetResponse_withoutHeadersAndRequestBody_Success() {
        setupMockApiRequestChain();
        ResponseEntity<String> response = webClientService.get(URL, RESPONSE_TYPE);

        verifyWebClientRequestChain(HttpMethod.GET);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testGetResponse_withHeadersAndWithoutRequestBody_Success() {
        setupMockApiRequestChain();
        ResponseEntity<String> response = webClientService.get(URL, getDefaultHeaders(), RESPONSE_TYPE);

        verifyWebClientRequestChain(HttpMethod.GET);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testGetResponse_withoutHeadersAndWithRequestBody_Success() {
        setupMockApiRequestChainWithRequestBody();
        ResponseEntity<String> response = webClientService.get(URL, new Object(), RESPONSE_TYPE);

        verifyWebClientRequestChainWithRequestBody(HttpMethod.GET);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testGetResponse_withHeadersAndRequestBody_Success() {
        setupMockApiRequestChainWithRequestBody();
        ResponseEntity<String> response = webClientService.get(URL, getDefaultHeaders(), new Object(), RESPONSE_TYPE);


        verifyWebClientRequestChainWithRequestBody(HttpMethod.GET);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testDeleteResponse_withoutHeadersAndRequestBody_Success() {
        setupMockApiRequestChain();
        ResponseEntity<String> response = webClientService.delete(URL, RESPONSE_TYPE);

        verifyWebClientRequestChain(HttpMethod.DELETE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testDeleteResponse_withHeadersAndWithoutRequestBody_Success() {
        setupMockApiRequestChain();
        ResponseEntity<String> response = webClientService.delete(URL, getDefaultHeaders(), RESPONSE_TYPE);

        verifyWebClientRequestChain(HttpMethod.DELETE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testDeleteResponse_withoutHeadersAndWithRequestBody_Success() {
        setupMockApiRequestChainWithRequestBody();
        ResponseEntity<String> response = webClientService.delete(URL, new Object(), RESPONSE_TYPE);

        verifyWebClientRequestChainWithRequestBody(HttpMethod.DELETE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testDeleteResponse_withHeadersAndRequestBody_Success() {
        setupMockApiRequestChainWithRequestBody();
        ResponseEntity<String> response = webClientService.delete(URL, getDefaultHeaders(), new Object(), RESPONSE_TYPE);


        verifyWebClientRequestChainWithRequestBody(HttpMethod.DELETE);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testPostResponse_withoutHeadersAndWithRequestBody_Success() {
        setupMockApiRequestChainWithRequestBody();
        ResponseEntity<String> response = webClientService.post(URL, new Object(), RESPONSE_TYPE);

        verifyWebClientRequestChainWithRequestBody(HttpMethod.POST);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testPostResponse_withHeadersAndRequestBody_Success() {
        setupMockApiRequestChainWithRequestBody();
        ResponseEntity<String> response = webClientService.post(URL, getDefaultHeaders(), new Object(), RESPONSE_TYPE);

        verifyWebClientRequestChainWithRequestBody(HttpMethod.POST);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testPutResponse_withoutHeadersAndWithRequestBody_Success() {
        setupMockApiRequestChainWithRequestBody();
        ResponseEntity<String> response = webClientService.put(URL, new Object(), RESPONSE_TYPE);

        verifyWebClientRequestChainWithRequestBody(HttpMethod.PUT);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    @Test
    void testPutResponse_withHeadersAndRequestBody_Success() {
        setupMockApiRequestChainWithRequestBody();
        ResponseEntity<String> response = webClientService.put(URL, getDefaultHeaders(), new Object(), RESPONSE_TYPE);

        verifyWebClientRequestChainWithRequestBody(HttpMethod.PUT);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(RESPONSE_BODY, response.getBody());
    }

    private void verifyWebClientRequestChain(HttpMethod httpMethod) {
        verify(webClient).method(httpMethod);
        verify(requestBodyUriSpec).uri(anyString());
        verify(requestBodySpec).headers(any());
        verify(requestBodySpec).exchangeToMono(any());
    }

    private void verifyWebClientRequestChainWithRequestBody(HttpMethod httpMethod) {
        verify(webClient).method(httpMethod);
        verify(requestBodyUriSpec).uri(anyString());
        verify(requestBodySpec).bodyValue(any());
        verify(requestBodySpec).headers(any());
        verify(requestHeadersSpec).exchangeToMono(any());
    }
}
