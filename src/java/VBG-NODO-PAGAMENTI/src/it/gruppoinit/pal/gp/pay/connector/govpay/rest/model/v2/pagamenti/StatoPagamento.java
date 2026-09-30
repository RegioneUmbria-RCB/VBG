package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Stato del pagamento * IN_CORSO: Pagamento in corso * ANNULLATO: Pagamento annullato dall'utente o scaduto per timeout
 * * FALLITO: Pagamento non perfezionato per errori della piattaforma * ESEGUITO: Pagamento concluso con successo *
 * NON_ESEGUITO: Processo concluso senza il perfezionamento del pagamento * ESEGUITO_PARZIALE: Pagamento parzialmente
 * eseguito
 */
@XmlType(name = "StatoPagamento")
@XmlEnum
public enum StatoPagamento {

    @XmlEnumValue("IN_CORSO")
    IN_CORSO("IN_CORSO"),
    @XmlEnumValue("ESEGUITO")
    ESEGUITO("ESEGUITO"),
    @XmlEnumValue("NON_ESEGUITO")
    NON_ESEGUITO("NON_ESEGUITO"),
    @XmlEnumValue("ESEGUITO_PARZIALE")
    ESEGUITO_PARZIALE("ESEGUITO_PARZIALE"),
    @XmlEnumValue("ANNULLATO")
    ANNULLATO("ANNULLATO"),
    @XmlEnumValue("FALLITO")
    FALLITO("FALLITO");

    private String value;

    StatoPagamento(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(this.value);
    }

    public static StatoPagamento fromValue(String text) {

	for (StatoPagamento b : StatoPagamento.values()) {
	    if (String.valueOf(b.value).equals(text)) {
		return b;
	    }
	}
	return null;
    }
}
