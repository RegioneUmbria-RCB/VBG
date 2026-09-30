package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

public enum GetDebPositionStatusEnum {

    PAGATO("0"),
    NON_PAGATO("1"),
    ANNULLATO_DALL_ENTE("2"),
    NON_ANCORA_ATTIVA("3"),
    NON_PIU_ATTIVA("4");

    private String descrizioneStato;
    private static Map<String, GetDebPositionStatusEnum> map = new HashMap<>();

    private GetDebPositionStatusEnum(String descrizioneStato) {

	this.descrizioneStato = descrizioneStato;
    }

    static {
	for (GetDebPositionStatusEnum stato : GetDebPositionStatusEnum.values()) {
	    map.put(stato.descrizioneStato, stato);
	}
    }

    public String value() {

	return name();
    }

    public static GetDebPositionStatusEnum fromDescrizione(String stato) {

	return map.get(stato);
    }

    public StatoPagamentoType toStatoPagamentoType() {

	switch (this) {
	case NON_PAGATO:
	case NON_ANCORA_ATTIVA:
	case NON_PIU_ATTIVA:
	    return StatoPagamentoType.ATTIVATO_IN_PSP;
	case PAGATO:
	    return StatoPagamentoType.NOTIFICATO_DA_PSP;
	case ANNULLATO_DALL_ENTE:
	    return StatoPagamentoType.ANNULLATO;
	}
	throw new IllegalArgumentException("Stato Pagamento non valido " + this);
    }
}
