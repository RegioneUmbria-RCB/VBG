package it.gruppoinit.pal.gp.pay.domain;

public enum EsitiEvento {

    KO((short) 0),
    OK((short) 1),
    PARZIALE((short) 2);

    private short codice;

    private EsitiEvento(short codice) {

	this.codice = codice;
    }

    public String value() {

	return name();
    }

    public short codice() {

	return codice;
    }

    public static EsitiEvento fromValue(short val) {

	switch (val) {
	case 0:
	    return KO;
	case 1:
	    return OK;
	default:
	    return PARZIALE;
	}
    }
}