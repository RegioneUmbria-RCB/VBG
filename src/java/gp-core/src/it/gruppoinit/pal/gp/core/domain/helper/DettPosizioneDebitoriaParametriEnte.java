package it.gruppoinit.pal.gp.core.domain.helper;

public class DettPosizioneDebitoriaParametriEnte {

    private String cfEnteCreditore;
    private String codiceComune;
    private String software;

    public DettPosizioneDebitoriaParametriEnte(String cfEnteCreditore, String codiceComune, String software) {

	this.cfEnteCreditore = cfEnteCreditore;
	this.codiceComune = codiceComune;
	this.software = software;
    }

    public String getCfEnteCreditore() {

	return cfEnteCreditore;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public String getSoftware() {

	return software;
    }
}
