package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

public enum ContestoOverrideEnum {

    ALBEROPROC("Albero degli interventi");

    private String descrizione;

    ContestoOverrideEnum(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getDescrizione() {

	return descrizione;
    }
}
