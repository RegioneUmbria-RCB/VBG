package com.paevolution.appioproducer.utils;

public enum StatoMessaggioEnum {

    DA_PROCESSARE("DA_PROCESSARE"),
    INVIATA_A_GATEWAY("INVIATA_A_GATEWAY"),
    ACCEPTED("NOTIFICATA_APPIO"),
    THROTTLED("IN_LAVORAZIONE"),
    PROCESSED("NOTIFICATA_UTENTE"),
    FAILED("ERRORE"),
    REJECTED("ERRORE"),
    ERRORE_GATEWAY("ERRORE");

    private String name;

    StatoMessaggioEnum(String name) {

	this.name = name;
    }

    public String getName() {

	return name;
    }
}
