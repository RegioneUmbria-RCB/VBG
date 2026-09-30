package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

public enum ProtocolloSourceEnum {

    NONPROTOCOLLARE(0),
    INSERIMENTO_NORMALE(1),
    ON_LINE(2),
    INSERIMENTO_RAPIDO(4),
    CONTR_RAMO_PADRE(8),
    PROT_IST_MOV_AUT_BO(16);

    private final int value;

    ProtocolloSourceEnum(final int newValue) {

	value = newValue;
    }

    public int getValue() {

	return value;
    }
}
