package com.ursominhoco.logistica;

import com.ursominhoco.logistica.dto.CepResponse;
import com.ursominhoco.logistica.dto.CotacaoResponse;
import com.ursominhoco.logistica.exception.CepInvalidoException;
import com.ursominhoco.logistica.service.CepService;
import com.ursominhoco.logistica.service.FreteService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class FreteServiceTest {

    private final CepService cepService = mock(CepService.class);
    private final FreteService freteService = new FreteService(cepService);

    @Test
    void cotar_semItensFisicos_devolveZeroSemConsultarCep() {
        CotacaoResponse cotacao = freteService.cotar("01001000", 0);

        assertThat(cotacao.valor()).isEqualByComparingTo("0");
        assertThat(cotacao.prazoDias()).isZero();
        assertThat(cotacao.regiao()).isNull();
        verifyNoInteractions(cepService);
    }

    @Test
    void cotar_variosItens_usaARegiaoDaUfDoCep() {
        when(cepService.buscar("01001000"))
                .thenReturn(new CepResponse("01001000", "Praça da Sé", "Sé", "São Paulo", "SP"));

        CotacaoResponse cotacao = freteService.cotar("01001-000", 3); // 18 + 4 * 2

        assertThat(cotacao.regiao()).isEqualTo("Sudeste");
        assertThat(cotacao.valor()).isEqualByComparingTo("26.00");
        assertThat(cotacao.prazoDias()).isEqualTo(5);
        verify(cepService).buscar("01001000");
    }

    @Test
    void cotar_cepMalFormado_lancaCepInvalido() {
        assertThatThrownBy(() -> freteService.cotar("123", 2))
                .isInstanceOf(CepInvalidoException.class);
    }
}