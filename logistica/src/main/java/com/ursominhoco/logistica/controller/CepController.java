package com.ursominhoco.logistica.controller;

import com.ursominhoco.logistica.dto.CepResponse;
import com.ursominhoco.logistica.service.CepService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cep")
@RequiredArgsConstructor
public class CepController {

    private final CepService cepService;

    @GetMapping("{cep}")
    public CepResponse buscar(@PathVariable String cep) {
        return cepService.buscar(cep);
    }
}