package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit;

public enum TipimovimentoRabbitCategoriaEnum {

    PRATICA_PRESA_IN_CARICO("PraticaPresaInCarico", "PRATICA PRESA IN CARICO"),
    COMUNICAZIONI_GENERALI("ComunicazioniGenerali", "COMUNICAZIONI GENERALI"),
    PAGAMENTI("Pagamenti", "PAGAMENTI"),
    SCADENZA_PRATICA("ScadenzaPratica", "SCADENZA PRATICA");

    private String value;
    private String descrizione;

    private TipimovimentoRabbitCategoriaEnum(String v, String d) {

	this.value = v;
	this.descrizione = d;
    }

    public String getValue() {

	return value;
    }

    public void setValue(String value) {

	this.value = value;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public static TipimovimentoRabbitCategoriaEnum fromName(String v) {

	for (TipimovimentoRabbitCategoriaEnum b : TipimovimentoRabbitCategoriaEnum.values()) {
	    if (b.value.equalsIgnoreCase(v)) {
		return b;
	    }
	}
	return null;
    }
}
