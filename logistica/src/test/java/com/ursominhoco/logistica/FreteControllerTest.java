package com.ursominhoco.logistica;

import com.ursominhoco.logistica.controller.FreteController;
import com.ursominhoco.logistica.dto.CepResponse;
import com.ursominhoco.logistica.exception.CepNaoEncontradoException;
import com.ursominhoco.logistica.exception.ExceptionHandlerAdvice;
import com.ursominhoco.logistica.service.CepService;
import com.ursominhoco.logistica.service.FreteService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FreteControllerTest {

    private final CepService cepService = mock(CepService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new FreteController(new FreteService(cepService)))
            .setControllerAdvice(new ExceptionHandlerAdvice())
            .build();

    private org.springframework.test.web.servlet.ResultActions cotar(String json) throws Exception {
        return mockMvc.perform(post("/frete/cotacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Test
    void cotar_semItensFisicos_retornaFreteZero() throws Exception {
        cotar("""
                {"cepDestino": "01001000", "itensFisicos": 0}
                """)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor").value(0.0))
                .andExpect(jsonPath("$.prazoDias").value(0))
                .andExpect(jsonPath("$.regiao").value(nullValue()));
    }

    @Test
    void cotar_itensFisicos_retornaValorPrazoERegiao() throws Exception {
        when(cepService.buscar("01001000"))
                .thenReturn(new CepResponse("01001000", "Praça da Sé", "Sé", "São Paulo", "SP"));

        cotar("""
                {"cepDestino": "01001000", "itensFisicos": 3}
                """)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor").value(26.0))
                .andExpect(jsonPath("$.prazoDias").value(5))
                .andExpect(jsonPath("$.regiao").value("Sudeste"));
    }

    @Test
    void cotar_semCamposObrigatorios_retorna400ComErrosDeValidacao() throws Exception {
        cotar("{}")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.cepDestino").exists())
                .andExpect(jsonPath("$.validationErrors.itensFisicos").exists());
    }

    @Test
    void cotar_quantidadeNegativa_retorna400() throws Exception {
        cotar("""
                {"cepDestino": "01001000", "itensFisicos": -1}
                """)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.itensFisicos").exists());
    }

    @Test
    void cotar_cepMalFormado_retorna400() throws Exception {
        cotar("""
                {"cepDestino: "abc", "itensFisicos": 1}
                """)
                .andExpect(status().isBadRequest());
    }

    @Test
    void cotar_cepInexistente_retorna404() throws Exception {
        when(cepService.buscar("99999999")).thenThrow(new CepNaoEncontradoException());

        cotar("""
                {"cepDestino": "99999999", "itensFisicos": 1}
                """)
                .andExpect(status().isNotFound());
    }
}