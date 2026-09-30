package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

public enum StatoPagamentoOpenWebEnum {

    PAGATO("Pagato"), //
    DISPONIBILE("Disponibile"), //
    PENDENTE("Pendente"), //
    NON_ESEGUITO("Non eseguito"), //
    AVVIATO("Avviato"), //
    DA_RICARICARE("Da Ricaricare"), //
    IN_ATTESA_RISPOSTA_TELEMATICA("In attesa RT"), //
    ANNULLATO("Annullato"), //
    PAGAMENTO_NON_ESEGUITO("Non eseguito"), //
    DECORRENZA_TERMINI("Decorrenza termini"), //
    ELIMINATO_DA_UFFICIO("Eliminato Ufficio"), //
    CARICATO("Caricato");

    private String value;

    private StatoPagamentoOpenWebEnum(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(this.value);
    }

    public static void main(String[] args) {

	System.out.println(fromValue("in_attesa_rt"));
    }

    public static StatoPagamentoOpenWebEnum fromValue(String text) {

	String testoDaConfrontare = text.replace(" ", "").replace("_", "").toLowerCase();
	for (StatoPagamentoOpenWebEnum b : StatoPagamentoOpenWebEnum.values()) {
	    if (String.valueOf(b.value.replace(" ", "").replace("_", "").toLowerCase()).equalsIgnoreCase(testoDaConfrontare)) {
		return b;
	    }
	}
	return null;
    }
}
