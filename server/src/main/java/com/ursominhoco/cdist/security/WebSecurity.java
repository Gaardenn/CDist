package com.ursominhoco.cdist.security;

import com.ursominhoco.cdist.service.AuthService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurity {
    private final AuthService authService;
//    private final AuthenticationEntryPoint pontoEntradaAutenticacao;

    public WebSecurity(AuthService authService/*, AuthenticationEntryPoint pontoEntradaAutenticacao*/) {
        this.authService = authService;
//        this.pontoEntradaAutenticacao = pontoEntradaAutenticacao;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        AuthenticationManagerBuilder construtorGerenciadorAutenticacao = http.getSharedObject(
                AuthenticationManagerBuilder.class);
        construtorGerenciadorAutenticacao.userDetailsService(authService).passwordEncoder(codificadorSenha());
        AuthenticationManager gerenciadorAutenticacao = construtorGerenciadorAutenticacao.build();

        http.headers(cabecalhos -> cabecalhos.frameOptions(HeadersConfigurer
                .FrameOptionsConfig::disable));
        http.csrf(AbstractHttpConfigurer::disable);

//        http.cors(cors -> corsConfigurationSource());

//        http.exceptionHandling(tratamentoExcecoes -> tratamentoExcecoes
//                .authenticationEntryPoint(pontoEntradaAutenticacao));

        http.authorizeHttpRequests((autorizar) -> autorizar
                .requestMatchers(HttpMethod.POST, "/users/**").permitAll().requestMatchers("/erro/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll().requestMatchers("/produtos/**").permitAll()
                .requestMatchers("/categorias/**").permitAll().anyRequest().authenticated());
        http.authenticationManager(gerenciadorAutenticacao).addFilter(new
                JWTAuthenticationFilter(gerenciadorAutenticacao, authService)).addFilter(new
                JWTAuthorizationFilter(gerenciadorAutenticacao, authService)).sessionManagement(
                        s -> s.sessionCreationPolicy(SessionCreationPolicy
                                .STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }

//    Manter comentado por enquanto para poder testar de forma fácil sem tokens e limitações, depois vamos ativar de
//    volta
//
//    /*
//        O compartilhamento de recursos de origem cruzada (CORS) é um mecanismo para integração de aplicativos.
//        O CORS define uma maneira de os aplicativos Web clientes carregados em um domínio interagirem com recursos em um domínio diferente.
//    */
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        // Lista das origens autorizadas, no nosso caso que iremos rodar a aplicação localmente o * poderia ser trocado
//        // por: http://localhost:porta, em que :porta será a porta em que a aplicação cliente será executada
//        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//        // Lista dos métodos HTTP autorizados
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"));
//        // Lista dos Headers autorizados, o Authorization será o header que iremos utilizar para transferir o Token
//        configuration.setAllowedHeaders(List.of("Authorization","x-xsrf-token",
//                "Access-Control-Allow-Headers", "Origin",
//                "Accept", "X-Requested-With", "Content-Type",
//                "Access-Control-Request-Method",
//                "Access-Control-Request-Headers", "Auth-Id-Token"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
