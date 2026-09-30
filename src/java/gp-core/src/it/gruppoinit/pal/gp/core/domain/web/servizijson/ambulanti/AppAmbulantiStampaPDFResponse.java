package it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti;

import java.io.InputStream;

import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione.ESITO;

public class AppAmbulantiStampaPDFResponse {

    private InputStream contenuto;
    private String nomeFile;

    public InputStream getContenuto() {

	return contenuto;
    }

    public void setContenuto(InputStream contenuto) {

	this.contenuto = contenuto;
    }

    public AppAmbulantiStampaPDFResponse() {

	this.esito = new BaseEsitoOperazione(ESITO.SUCCESS);
    }

    private BaseEsitoOperazione esito;

    public BaseEsitoOperazione getEsito() {

	return esito;
    }

    public void setEsito(BaseEsitoOperazione esito) {

	this.esito = esito;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }
}
