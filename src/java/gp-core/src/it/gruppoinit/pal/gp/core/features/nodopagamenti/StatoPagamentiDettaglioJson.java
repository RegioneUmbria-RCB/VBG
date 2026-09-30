package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class StatoPagamentiDettaglioJson extends StatoPagamentiBreveJson {

    private String data_ultimo_stato;
    private String codice_stato_nodo;
    private boolean avvisoSupportato;

    public StatoPagamentiDettaglioJson(DettPosizioneDebitoria pd, StatiPosizioniDebitorieConverter converter, boolean avvisoSupportato) {

	super(pd, converter);
	if (pd.getDataUltimoStato() != null) {
	    this.data_ultimo_stato = Utilities.formatDate(pd.getDataUltimoStato(), true);
	}
	this.codice_stato_nodo = pd.getStato();
	this.avvisoSupportato = avvisoSupportato;
    }

    public String getData_ultimo_stato() {

	return data_ultimo_stato;
    }

    public void setData_ultimo_stato(String data_ultimo_stato) {

	this.data_ultimo_stato = data_ultimo_stato;
    }

    public String getCodice_stato_nodo() {

	return codice_stato_nodo;
    }

    public void setCodice_stato_nodo(String codice_stato_nodo) {

	this.codice_stato_nodo = codice_stato_nodo;
    }

    public boolean getAvvisoSupportato() {

	return avvisoSupportato;
    }

    public void setAvvisoSupportato(boolean avvisoSupportato) {

	this.avvisoSupportato = avvisoSupportato;
    }
}
