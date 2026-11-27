package com.ursominhoco.logistica.frete;

import com.ursominhoco.logistica.dto.CotacaoResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class FreteCalculator {

    private FreteCalculator() {
    }

    /** valor = base + adicional * (itensFisicos - 1). Sem item físico fica sem frete (RN01). */
    public static CotacaoResponse calcular(RegiaoFrete regiao, int itensFisicos) {
        if (itensFisicos <= 0) {
            return CotacaoResponse.semFrete();
        }

        BigDecimal valor = regiao.getBase()
                .add(regiao.getAdicional().multiply(BigDecimal.valueOf(itensFisicos - 1L)))
                .setScale(2, RoundingMode.HALF_UP);

        return new CotacaoResponse(valor, regiao.getPrazoDias(), regiao.getNome());
    }
}