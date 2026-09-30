package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

public class StatoPagamentiBreveJson {

    private Integer id;
    private Integer id_posizione_debitoria;
    private String stato;
    private Integer codice_stato;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public void setId_posizione_debitoria(Integer id_posizione_debitoria) {

	this.id_posizione_debitoria = id_posizione_debitoria;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public void setCodice_stato(Integer codice_stato) {

	this.codice_stato = codice_stato;
    }

    public StatoPagamentiBreveJson(DettPosizioneDebitoria pd, StatiPosizioniDebitorieConverter converter) {

	this.id = pd.getId().getCodice();
	this.id_posizione_debitoria = pd.getIdPosizioneDebitoria();
	IdentificativoDescrizioneBean convertStato = converter.convertStato(pd.getStato());
	this.codice_stato = convertStato.getId();
	this.stato = convertStato.getDescrizione();
    }

    public StatoPagamentiBreveJson(Integer id, Integer id_posizione_debitoria, String stato, Integer codice_stato) {

	super();
	this.id = id;
	this.id_posizione_debitoria = id_posizione_debitoria;
	this.stato = stato;
	this.codice_stato = codice_stato;
    }

    public Integer getId_posizione_debitoria() {

	return id_posizione_debitoria;
    }

    public String getStato() {

	return stato;
    }

    public Integer getCodice_stato() {

	return codice_stato;
    }
}
