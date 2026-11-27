package com.ursominhoco.logistica;

import com.ursominhoco.logistica.client.ViaCepClient;
import com.ursominhoco.logistica.controller.CepController;
import com.ursominhoco.logistica.dto.CepResponse;
import com.ursominhoco.logistica.exception.ExceptionHandlerAdvice;
import com.ursominhoco.logistica.exception.ServicoCepIndisponivelException;
import com.ursominhoco.logistica.service.CepService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CepControllerTest {

    private final ViaCepClient viaCepClient = mock(ViaCepClient.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new CepController(new CepService(viaCepClient)))
            .setControllerAdvice(new ExceptionHandlerAdvice())
            .build();

    @Test
    void buscar_cepValido_retorna200() throws Exception {
        when(viaCepClient.buscar("01001000")).thenReturn(Optional.of(
                new CepResponse("01001000", "Praça da Sé", "Sé", "São Paulo", "SP")));

        mockMvc.perform(get("/cep/01001000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("01001000"))
                .andExpect(jsonPath("$.logradouro").value("Praça da Sé"))
                .andExpect(jsonPath("$.bairro").value("Sé"))
                .andExpect(jsonPath("$.cidade").value("São Paulo"))
                .andExpect(jsonPath("$.estado").value("SP"));
    }

    @Test
    void buscar_cepComHifen_retorna200() throws Exception {
        when(viaCepClient.buscar("01001000")).thenReturn(Optional.of(
                new CepResponse("01001000", "Praça da Sé", "Sé", "São Paulo", "SP")));

        mockMvc.perform(get("/cep/01001-000"))
                .andExpect(status().isOk());
    }

    @Test
    void buscar_cepMalFormado_retorna400SemConsultarViaCep() throws Exception {
        mockMvc.perform(get("/cep/abc123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        mockMvc.perform(get("/cep/1234567"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(viaCepClient);
    }

    @Test
    void buscar_cepInexistente_retorna404() throws Exception {
        when(viaCepClient.buscar("99999999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/cep/99999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("CEP não encontrado"));
    }

    @Test
    void buscar_viaCepForaDoAr_retorna502() throws Exception {
        when(viaCepClient.buscar("01001000"))
                .thenThrow(new ServicoCepIndisponivelException("Serviço de consulta de CEP indisponível"));

        mockMvc.perform(get("/cep/01001000"))
                .andExpect(status().isBadGateway());
    }
}