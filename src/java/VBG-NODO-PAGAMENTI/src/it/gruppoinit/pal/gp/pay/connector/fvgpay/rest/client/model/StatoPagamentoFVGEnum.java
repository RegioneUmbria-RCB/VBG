package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

public enum StatoPagamentoFVGEnum {

    IN_ATTESA_DI_PAGAMENTO("in attesa di pagamento"), //    
    IN_ATTESA_DI_RICHIESTA("in_attesa_di_richiesta"), //
    IN_ATTESA_DI_ESITO("in_attesa_di_esito"), //
    IN_ELABORAZIONE("in_elaborazione"), //
    IN_PAGAMENTO("in pagamento"), // 
    PARZIALMENTE_PAGATO("parzialmente_pagato"), //
    PAGATO("pagato"), //
    PAGATA("pagata"), //
    PAGATO_EXTRA_SISTEMA("pagato_extra_sistema"), //
    PAGATO_CON_ERRORE("pagato_con_errore"), //
    ANNULLATO("annullato"), //
    CHIUSO("chiuso"), // 
    PARZIALMENTE_REVOCATO("parzialmente_revocato"), //
    REVOCATO("revocato"), //
    CANCELLATO("cancellato"), //
    NON_APPROVATO("non_approvato"), //
    ABBANDONATO("abbandonato"), //
    ERRORE("errore");
    //    - in attesa di pagamento - in pagamento
    //    - in_attesa_di_richiesta
    //    - in_attesa_di_esito
    //    - in_elaborazione
    //    - parzialmente_pagato
    //    - pagato
    //    - pagato_extra_sistema
    //    - pagato_con_errore
    //    - annullato
    //    - chiuso
    //    - parzialmente_revocato
    //    - revocato
    //    - cancellato
    //    - non_approvato
    //    - abbandonato
    //    - errore

    private String value;

    StatoPagamentoFVGEnum(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(this.value);
    }

    public static StatoPagamentoFVGEnum fromValue(String text) {

	String testoDaConfrontare = text.replace(" ", "").replace("_", "").toLowerCase();
	for (StatoPagamentoFVGEnum b : StatoPagamentoFVGEnum.values()) {
	    if (String.valueOf(b.value.replace(" ", "").replace("_", "").toLowerCase()).equalsIgnoreCase(testoDaConfrontare)) {
		return b;
	    }
	}
	return null;
    }
}
