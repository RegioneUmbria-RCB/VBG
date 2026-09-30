package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

public class MercatiRestHelper {

    private Integer id;
    private String descrizione;
    private String tipoManifestazione;
    private CodiceDescrizioneBean giorno;
    private boolean gestisciMappa = false;
    private List<String> stradario = new ArrayList<String>();

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getTipoManifestazione() {

	return tipoManifestazione;
    }

    public void setTipoManifestazione(String tipoManifestazione) {

	this.tipoManifestazione = tipoManifestazione;
    }

    public CodiceDescrizioneBean getGiorno() {

	return giorno;
    }

    public void setGiorno(CodiceDescrizioneBean giorno) {

	this.giorno = giorno;
    }

    public boolean getGestisciMappa() {

	return gestisciMappa;
    }

    public void setGestisciMappa(boolean gestisciMappa) {

	this.gestisciMappa = gestisciMappa;
    }

    public List<String> getStradario() {

	return stradario;
    }

    public void setStradario(List<String> stradario) {

	this.stradario = stradario;
    }
}
