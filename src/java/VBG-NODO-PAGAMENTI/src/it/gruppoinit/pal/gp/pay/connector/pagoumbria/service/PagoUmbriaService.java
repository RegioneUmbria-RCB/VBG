package it.gruppoinit.pal.gp.pay.connector.pagoumbria.service;

import java.util.List;

import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.server.notificapagamenti.schema.esito.InfoMessaggio;

public interface PagoUmbriaService {

    /**
     * esiti relativo soltanto alla ricezione della notifica dei pagamenti avvenuti (non verificati da PagoUmbria)
     * 
     * @author francol
     *
     */
    public enum EsitiPagoUmbria {
	//
	PA_000("OK"), PA_100("Errore nella ricezione della notifica di pagamento");

	private String desc;

	private EsitiPagoUmbria(String desc) {

	    this.desc = desc;
	}

	public String value() {

	    return name();
	}

	public String errorCode() {

	    return this.name();
	}

	public String description() {

	    return desc;
	}

	public static EsitiPagoUmbria fromValue(String v) {

	    return valueOf(v);
	}
    }

    public InfoMessaggio registraNotificaPagamenti(List<Pagamento> datiPag);
}
