package com.ursominhoco.logistica.controller;

import com.ursominhoco.logistica.dto.CotacaoRequest;
import com.ursominhoco.logistica.dto.CotacaoResponse;
import com.ursominhoco.logistica.service.FreteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("frete")
@RequiredArgsConstructor
public class FreteController {

    private final FreteService freteService;

    @PostMapping("cotacao")
    public CotacaoResponse cotar(@Valid @RequestBody CotacaoRequest request) {
        return freteService.cotar(request.cepDestino(), request.itensFisicos());
    }
}