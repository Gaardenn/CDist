package com.ursominhoco.cdist.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
public class ErrorHandler implements ErrorController {
    private final ErrorAttributes atributosErro;

    public ErrorHandler(ErrorAttributes atributosErro) {
        this.atributosErro = atributosErro;
    }

    @RequestMapping("error")
    public ApiError lidarErro(WebRequest requisicaoWeb, HttpServletResponse resposta) {
        Map<String, Object> atributos = atributosErro.getErrorAttributes(requisicaoWeb, ErrorAttributeOptions
                .of(ErrorAttributeOptions.Include.MESSAGE));

        if (atributos.get("status") == null) {
            atributos.put("status", resposta.getStatus());
        }

        return new ApiError((Integer) atributos.get("status"), (String) atributos.get("message"), (String) atributos
                .get("path"));
    }
}