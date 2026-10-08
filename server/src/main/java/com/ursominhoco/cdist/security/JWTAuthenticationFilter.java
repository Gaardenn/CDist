package com.ursominhoco.cdist.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ursominhoco.cdist.dto.AuthRequestDTO;
import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.security.dto.AuthenticationResponse;
import com.ursominhoco.cdist.security.dto.UsuarioResponseDTO;
import com.ursominhoco.cdist.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.core.JacksonException;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

@NoArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager gerenciadorAutenticacao;
    private AuthService authService;

    public JWTAuthenticationFilter(AuthenticationManager gerenciadorAutenticacao, AuthService authService) {
        this.gerenciadorAutenticacao = gerenciadorAutenticacao;
        this.authService = authService;
        setFilterProcessesUrl("/usuarios/login");
        setAuthenticationFailureHandler((request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":401,\"message\":\"Erro de autenticação!\"}");
        });

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, @NotNull HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthRequestDTO credenciais = new AuthRequestDTO();
            Usuario usuario = new Usuario();
            if (request.getInputStream() != null || request.getInputStream().available() > 0) {
                credenciais = new ObjectMapper().readValue(request.getInputStream(), AuthRequestDTO.class);
                usuario = (Usuario) authService.loadUserByUsername(credenciais.getEmail());
            }
            return gerenciadorAutenticacao.authenticate(new UsernamePasswordAuthenticationToken(credenciais.getEmail(),
                    credenciais.getSenha(), usuario.getAuthorities()));
        } catch (IOException | JacksonException e) {
            throw new AuthenticationServiceException("Corpo da requisição inválido!", e);
        }
    }

    @Override
    protected void successfulAuthentication(@NonNull HttpServletRequest request, HttpServletResponse response, @NonNull
    FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Usuario usuario = (Usuario) authService.loadUserByUsername(authResult.getName());
        String token = JWT.create().withSubject(authResult.getName()).withExpiresAt(new Date(System.currentTimeMillis()
                + SecurityConstants.EXPIRATION_TIME)).sign(Algorithm.HMAC512(SecurityConstants.SECRET));

        response.setContentType("application/json");

        response.getWriter().write(new ObjectMapper().writeValueAsString(new AuthenticationResponse(token, new
                UsuarioResponseDTO(usuario))));
    }

    @Override
    public AuthenticationSuccessHandler getSuccessHandler() {
        return super.getSuccessHandler();
    }
}