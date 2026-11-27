package com.ursominhoco.logistica;

import com.ursominhoco.logistica.dto.CotacaoResponse;
import com.ursominhoco.logistica.frete.FreteCalculator;
import com.ursominhoco.logistica.frete.RegiaoFrete;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class FreteCalculatorTest {

    // Uma UF de cada região com 1 item físico cada
    @ParameterizedTest(name = "{0} -> {1}: R$ {2}, {3} dias")
    @CsvSource({
            "PR, Sul, 12.00, 3",
            "SP, Sudeste, 18.00, 5",
            "GO, Centro-Oeste, 24.00, 7",
            "BA, Nordeste, 30.00, 9",
            "AM, Norte, 36.00, 12"
    })
    void calcular_umItemFisico_cobraSoABaseDaRegiao(String uf, String regiao, String valor, int prazo) {
        RegiaoFrete tabela = RegiaoFrete.porUf(uf).orElseThrow();

        CotacaoResponse cotacao = FreteCalculator.calcular(tabela, 1);

        assertThat(cotacao.regiao()).isEqualTo(regiao);
        assertThat(cotacao.valor()).isEqualByComparingTo(valor);
        assertThat(cotacao.prazoDias()).isEqualTo(prazo);
    }

    @Test
    void calcular_zeroItensFisicos_devolveZero() {
        CotacaoResponse cotacao = FreteCalculator.calcular(RegiaoFrete.NORTE, 0);

        assertThat(cotacao.valor()).isEqualByComparingTo("0");
        assertThat(cotacao.prazoDias()).isZero();
        assertThat(cotacao.regiao()).isNull();
    }

    @Test
    void calcular_variosItensNoSul_somaOAdicionalDosItensExtras() {
        // 12 + 3 * 2
        assertThat(FreteCalculator.calcular(RegiaoFrete.SUL, 3).valor()).isEqualByComparingTo("18.00");
    }

    @Test
    void calcular_variosItensNoNorte_somaOAdicionalDosItensExtras() {
        // 36 + 7 * 4
        CotacaoResponse cotacao = FreteCalculator.calcular(RegiaoFrete.NORTE, 5);

        assertThat(cotacao.valor()).isEqualByComparingTo("64.00");
        assertThat(cotacao.prazoDias()).isEqualTo(12);
    }
}