package com.ursominhoco.logistica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient viaCepRestClient(@Value("${viacep.base-url}") String baseUrl,
                                @Value("${viacep.timeout}") Duration timeout) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(timeout)
                .build();

        JdkClientHttpRequestFactory fabrica = new JdkClientHttpRequestFactory(httpClient);
        fabrica.setReadTimeout(timeout);

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(fabrica)
                .build();
    }
}