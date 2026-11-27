package com.ursominhoco.logistica.frete;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/** Tabela de frete por região, com origem em Pato Branco */
@Getter
public enum RegiaoFrete {

    SUL("Sul", "12.00", "3.00", 3, "PR", "SC", "RS"),
    SUDESTE("Sudeste", "18.00", "4.00", 5, "SP", "RJ", "MG", "ES"),
    CENTRO_OESTE("Centro-Oeste", "24.00", "5.00", 7, "DF", "GO", "MT", "MS"),
    NORDESTE("Nordeste", "30.00", "6.00", 9, "BA", "SE", "AL", "PE", "PB", "RN", "CE", "PI", "MA"),
    NORTE("Norte", "36.00", "7.00", 12, "AC", "AM", "AP", "PA", "RO", "RR", "TO");

    private final String nome;
    private final BigDecimal base;  // 1o item físico
    private final BigDecimal adicional;  // cada item físico a mais
    private final int prazoDias;
    private final Set<String> ufs;

    RegiaoFrete(String nome, String base, String adicional, int prazoDias, String... ufs) {
        this.nome = nome;
        this.base = new BigDecimal(base);
        this.adicional = new BigDecimal(adicional);
        this.prazoDias = prazoDias;
        this.ufs = Set.of(ufs);
    }

    public static Optional<RegiaoFrete> porUf(String uf) {
        if (uf == null) {
            return Optional.empty();
        }
        String sigla = uf.trim().toUpperCase();
        return Arrays.stream(values())
                .filter(regiao -> regiao.ufs.contains(sigla))
                .findFirst();
    }
}