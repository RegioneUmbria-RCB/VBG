package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PosizioneDebitoriaModel;

public class BlackListOggettoDettaglio {

    private PosizioneDebitoriaModel posizioneDebitoria;
    private String data_inserimento_black_list;
    private String motivo;
    private AutorizzazioniFrontRestBean autorizzazione;
    private List<AutorizzazioniFrontRestBean> autorizzazioniCollegate;

    public PosizioneDebitoriaModel getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public void setPosizioneDebitoria(PosizioneDebitoriaModel posizioneDebitoria) {

	this.posizioneDebitoria = posizioneDebitoria;
    }

    public String getMotivo() {

	return motivo;
    }

    public void setMotivo(String motivo) {

	this.motivo = motivo;
    }

    public AutorizzazioniFrontRestBean getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(AutorizzazioniFrontRestBean autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public List<AutorizzazioniFrontRestBean> getAutorizzazioniCollegate() {

	if (this.autorizzazioniCollegate == null) {
	    this.autorizzazioniCollegate = new ArrayList<AutorizzazioniFrontRestBean>();
	}
	return autorizzazioniCollegate;
    }

    public void setAutorizzazioniCollegate(List<AutorizzazioniFrontRestBean> autorizzazioniCollegate) {

	this.autorizzazioniCollegate = autorizzazioniCollegate;
    }

    public String getData_inserimento_black_list() {

	return data_inserimento_black_list;
    }

    public void setData_inserimento_black_list(String data_inserimento_black_list) {

	this.data_inserimento_black_list = data_inserimento_black_list;
    }
}
