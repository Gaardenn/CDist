package com.ursominhoco.cdist.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ursominhoco.cdist.entity.Usuario;
import com.ursominhoco.cdist.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final AuthService authService;

    public JWTAuthorizationFilter(AuthenticationManager gerenciadorAutenticacao, AuthService authService) {
        super(gerenciadorAutenticacao);
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        String cabecalho = request.getHeader(SecurityConstants.HEADER_STRING);
        if (cabecalho == null || !cabecalho.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken tokenAutenticacao = getAutenticacao(request);
        SecurityContextHolder.createEmptyContext().setAuthentication(tokenAutenticacao);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAutenticacao(HttpServletRequest requisicao) {
        String token = requisicao.getHeader(SecurityConstants.HEADER_STRING);

        String email = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET)).build().verify(token.replace(
                SecurityConstants.TOKEN_PREFIX, "")).getSubject();

        if (email != null) {
            Usuario usuario = (Usuario) authService.loadUserByUsername(email);
            return new UsernamePasswordAuthenticationToken(usuario.getEmail(), null, usuario
                    .getAuthorities());
        }
        return null;
    }
}