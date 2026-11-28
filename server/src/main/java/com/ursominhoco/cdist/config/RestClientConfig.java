package com.ursominhoco.cdist.config;

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
    RestClient logisticaRestClient(@Value("${logistica.url}") String url,
                                   @Value("${logistica.timeout}") Duration timeoutLeitura) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();

        JdkClientHttpRequestFactory fabrica = new JdkClientHttpRequestFactory(httpClient);
        fabrica.setReadTimeout(timeoutLeitura);

        return RestClient.builder()
                .baseUrl(url)
                .requestFactory(fabrica)
                .build();
    }
}