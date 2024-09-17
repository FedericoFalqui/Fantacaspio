package com.federico.fantacaspio.enums;

public enum MaxPlayers {

    MAX_PORTIERI(3),
    MAX_DIFENSORI(8),
    MAX_CENTROCAMPISTI(8),
    MAX_ATTACCANTI(6); // Aggiungi altri valori massimi se necessario

    private final int valoreMassimo;

    MaxPlayers(int valoreMassimo) {
        this.valoreMassimo = valoreMassimo;
    }

    public int getValoreMassimo() {
        return valoreMassimo;
    }
}
