package com.ursominhoco.logistica;

import com.ursominhoco.logistica.frete.RegiaoFrete;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegiaoFreteTest {

    private static final List<String> TODAS_AS_UFS = List.of(
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
            "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");

    @Test
    void porUf_todasAs27UfsTemRegiao() {
        assertThat(TODAS_AS_UFS).hasSize(27);
        assertThat(TODAS_AS_UFS).allSatisfy(uf -> assertThat(RegiaoFrete.porUf(uf)).isPresent());
    }

    @Test
    void porUf_aceitaMinusculaEEspacos() {
        assertThat(RegiaoFrete.porUf(" pr ")).contains(RegiaoFrete.SUL);
    }

    @Test
    void porUf_ufInvalidaOuNula_devolveVazio() {
        assertThat(RegiaoFrete.porUf("XX")).isEmpty();
        assertThat(RegiaoFrete.porUf(null)).isEmpty();
    }
}