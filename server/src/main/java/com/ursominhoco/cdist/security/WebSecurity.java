package com.ursominhoco.cdist.security;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
@EnableWebSecurity
@Configuration
public class WebSecurity {

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.cors(cors -> corsConfigurationSource());

        return http.build();
    }

    /*
        O compartilhamento de recursos de origem cruzada (CORS) é um mecanismo para integração de aplicativos.
        O CORS define uma maneira de os aplicativos Web clientes carregados em um domínio interagirem com recursos em um domínio diferente.
    */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Lista das origens autorizadas, no nosso caso que iremos rodar a aplicação localmente o * poderia ser trocado
        // por: http://localhost:porta, em que :porta será a porta em que a aplicação cliente será executada
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // Lista dos métodos HTTP autorizados
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
        // Lista dos Headers autorizados, o Authorization será o header que iremos utilizar para transferir o Token
        configuration.setAllowedHeaders(List.of("Authorization","x-xsrf-token",
                "Access-Control-Allow-Headers", "Origin",
                "Accept", "X-Requested-With", "Content-Type",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers", "Auth-Id-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
