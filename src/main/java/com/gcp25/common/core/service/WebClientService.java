package com.gcp25.common.core.service;

import com.gcp25.common.core.exception.ApiExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Slf4j
@Service
public class WebClientService {

    @Autowired
    private WebClient webClient;

    private <T> Mono<ResponseEntity<T>> getResponse(HttpMethod httpMethod, String url, Map<String, String> headers,
                                                    ParameterizedTypeReference<T> responseType) {

        return webClient
                .method(httpMethod)
                .uri(url)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .exchangeToMono(response -> exchangeToMono(url, responseType, response));
    }

    private <T, R> Mono<ResponseEntity<R>> getResponse(HttpMethod httpMethod, String url, Map<String, String> headers,
                                                       T requestBody, ParameterizedTypeReference<R> responseType) {

        return webClient
                .method(httpMethod)
                .uri(url)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .bodyValue(requestBody)
                .exchangeToMono(response -> exchangeToMono(url, responseType, response));
    }


    private static <T> Mono<ResponseEntity<T>> exchangeToMono(String url, ParameterizedTypeReference<T> responseType,
                                                              ClientResponse response) {
        if (response.statusCode().isError()) {
            return response.bodyToMono(String.class)
                    .flatMap(errorBody -> {
                        log.error("API call failed: {} Status: {} Body: {}", url, response.statusCode(),
                                errorBody);
                        return Mono.error(new ApiExecutionException(response.statusCode(), errorBody));
                    });
        } else {
            return response.toEntity(responseType);
        }
    }

    private <T, R> ResponseEntity<R> sendWithRequestBody(HttpMethod httpMethod, String url, Map<String, String> headers,
                                                         T requestBody, ParameterizedTypeReference<R> responseType) {
        return getResponse(httpMethod, url, headers, requestBody, responseType).block();
    }

    private <T> ResponseEntity<T> sendRequest(HttpMethod httpMethod, String url, Map<String, String> headers,
                                              ParameterizedTypeReference<T> responseType) {
        return getResponse(httpMethod, url, headers, responseType).block();
    }

    /**
     * Sends a GET request with a request body and returns the full {@link ResponseEntity} containing the response
     * payload.
     * Note: While GET requests typically do not have a body, some APIs may allow it.
     * This method requires a header.
     *
     * @param <T>          the type parameter
     * @param <R>          the type parameter
     * @param url          the url
     * @param headers      the headers
     * @param requestBody  the request body
     * @param responseType the response type
     * @return the response entity
     */
    public <T, R> ResponseEntity<R> get(String url, Map<String, String> headers, T requestBody,
                                        ParameterizedTypeReference<R> responseType) {
        return sendWithRequestBody(HttpMethod.GET, url, headers, requestBody, responseType);
    }

    /**
     * Sends a GET request with a request body and returns the full {@link ResponseEntity} containing the response
     * payload.
     * Note: While GET requests typically do not have a body, some APIs may allow it.
     * This method sends an empty header map by default.
     *
     * @param <T>          the type parameter
     * @param <R>          the type parameter
     * @param url          the url
     * @param requestBody  the request body
     * @param responseType the response type
     * @return the response entity
     */
    public <T, R> ResponseEntity<R> get(String url, T requestBody, ParameterizedTypeReference<R> responseType) {
        return sendWithRequestBody(HttpMethod.GET, url, Map.of(), requestBody, responseType);
    }

    /**
     * Sends a GET request and returns the full {@link ResponseEntity} containing the response payload.
     * Note: This method requires a header.
     *
     * @param <T>          the type parameter
     * @param url          the url
     * @param headers      the headers
     * @param responseType the response type
     * @return the response entity
     */
    public <T> ResponseEntity<T> get(String url, Map<String, String> headers,
                                     ParameterizedTypeReference<T> responseType) {
        return sendRequest(HttpMethod.GET, url, headers, responseType);
    }

    /**
     * Sends a GET request and returns the full {@link ResponseEntity} containing the response payload.
     * Note: This method sends an empty header map by default.
     *
     * @param <T>          the type parameter
     * @param url          the url
     * @param responseType the response type
     * @return the response entity
     */
    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType) {
        return sendRequest(HttpMethod.GET, url, Map.of(), responseType);
    }

    /**
     * Sends a DELETE request with a request body and returns the full {@link ResponseEntity} containing the response
     * payload.
     * Note: This method requires a header.
     *
     * @param <T>          the type parameter
     * @param <R>          the type parameter
     * @param url          the url
     * @param headers      the headers
     * @param requestBody  the request body
     * @param responseType the response type
     * @return the response entity
     */
    public <T, R> ResponseEntity<R> delete(String url, Map<String, String> headers, T requestBody,
                                           ParameterizedTypeReference<R> responseType) {
        return sendWithRequestBody(HttpMethod.DELETE, url, headers, requestBody, responseType);
    }

    /**
     * Sends a DELETE request with a request body and returns the full {@link ResponseEntity} containing the response
     * payload.
     * Note: This method sends an empty header map by default.
     *
     * @param <T>          the type parameter
     * @param <R>          the type parameter
     * @param url          the url
     * @param requestBody  the request body
     * @param responseType the response type
     * @return the response entity
     */
    public <T, R> ResponseEntity<R> delete(String url, T requestBody, ParameterizedTypeReference<R> responseType) {
        return sendWithRequestBody(HttpMethod.DELETE, url, Map.of(), requestBody, responseType);
    }

    /**
     * Sends a DELETE request and returns the full {@link ResponseEntity} containing the response payload.
     * Note: This method requires a header.
     *
     * @param <T>          the type parameter
     * @param url          the url
     * @param headers      the headers
     * @param responseType the response type
     * @return the response entity
     */
    public <T> ResponseEntity<T> delete(String url, Map<String, String> headers,
                                        ParameterizedTypeReference<T> responseType) {
        return sendRequest(HttpMethod.DELETE, url, headers, responseType);
    }

    /**
     * Sends a DELETE request and returns the full {@link ResponseEntity} containing the response payload.
     * Note: This method sends an empty header map by default.
     *
     * @param <T>          the type parameter
     * @param url          the url
     * @param responseType the response type
     * @return the response entity
     */
    public <T> ResponseEntity<T> delete(String url, ParameterizedTypeReference<T> responseType) {
        return sendRequest(HttpMethod.DELETE, url, Map.of(), responseType);
    }

    /**
     * Sends a PUT request with a request body and returns the full {@link ResponseEntity} containing the response
     * payload.
     * Note: This method requires a header.
     *
     * @param <T>          the type parameter
     * @param <R>          the type parameter
     * @param url          the url
     * @param headers      the headers
     * @param requestBody  the request body
     * @param responseType the response type
     * @return the response entity
     */
    public <T, R> ResponseEntity<R> put(String url, Map<String, String> headers, T requestBody,
                                        ParameterizedTypeReference<R> responseType) {
        return sendWithRequestBody(HttpMethod.PUT, url, headers, requestBody, responseType);
    }

    /**
     * Sends a PUT request with a request body and returns the full {@link ResponseEntity} containing the response
     * payload.
     * Note: This method sends an empty header map by default.
     *
     * @param <T>          the type parameter
     * @param <R>          the type parameter
     * @param url          the url
     * @param requestBody  the request body
     * @param responseType the response type
     * @return the response entity
     */
    public <T, R> ResponseEntity<R> put(String url, T requestBody, ParameterizedTypeReference<R> responseType) {
        return sendWithRequestBody(HttpMethod.PUT, url, Map.of(), requestBody, responseType);
    }

    /**
     * Sends a POST request with a request body and returns the full {@link ResponseEntity} containing the response
     * payload.
     * Note: This method requires a header.
     *
     * @param <T>          the type parameter
     * @param <R>          the type parameter
     * @param url          the url
     * @param headers      the headers
     * @param requestBody  the request body
     * @param responseType the response type
     * @return the response entity
     */
    public <T, R> ResponseEntity<R> post(String url, Map<String, String> headers, T requestBody,
                                         ParameterizedTypeReference<R> responseType) {
        return sendWithRequestBody(HttpMethod.POST, url, headers, requestBody, responseType);
    }

    /**
     * Sends a POST request with a request body and returns the full {@link ResponseEntity} containing the response
     * payload.
     * Note: This method sends an empty header map by default.
     *
     * @param <T>          the type parameter
     * @param <R>          the type parameter
     * @param url          the url
     * @param requestBody  the request body
     * @param responseType the response type
     * @return the response entity
     */
    public <T, R> ResponseEntity<R> post(String url, T requestBody, ParameterizedTypeReference<R> responseType) {
        return sendWithRequestBody(HttpMethod.POST, url, Map.of(), requestBody, responseType);
    }

}
