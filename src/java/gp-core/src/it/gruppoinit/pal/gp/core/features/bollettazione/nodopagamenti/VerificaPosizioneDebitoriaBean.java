package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

public class VerificaPosizioneDebitoriaBean {

    private Integer dettPosizioneDebitoriaId;
    private String cfEnteCreditore;

    public VerificaPosizioneDebitoriaBean() {

    }

    public Integer getDettPosizioneDebitoriaId() {

	return dettPosizioneDebitoriaId;
    }

    public void setDettPosizioneDebitoriaId(Integer dettPosizioneDebitoriaId) {

	this.dettPosizioneDebitoriaId = dettPosizioneDebitoriaId;
    }

    public String getCfEnteCreditore() {

	return cfEnteCreditore;
    }

    public void setCfEnteCreditore(String cfEnteCreditore) {

	this.cfEnteCreditore = cfEnteCreditore;
    }
}
