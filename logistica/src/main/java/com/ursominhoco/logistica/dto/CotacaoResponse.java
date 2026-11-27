package com.ursominhoco.logistica.dto;

import java.math.BigDecimal;

public record CotacaoResponse(
        BigDecimal valor,
        int prazoDias,
        String regiao
) {
    /** Se não tiver item físico não tem o que enviar */
    public static CotacaoResponse semFrete() {
        return new CotacaoResponse(BigDecimal.ZERO.setScale(2), 0, null);
    }
}