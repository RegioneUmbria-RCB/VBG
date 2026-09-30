package it.gruppoinit.pal.gp.core.features.datidinamici.model;

public class CampoDinamicoScheda {

    private CampoDinamico campoDinamico;
    private Integer posizioneVerticale;
    private Integer posizioneOrizzontale;

    public CampoDinamicoScheda(CampoDinamico campoDinamico, Integer posizioneVerticale, Integer posizioneOrizzontale) {

	super();
	this.campoDinamico = campoDinamico;
	this.posizioneVerticale = posizioneVerticale;
	this.posizioneOrizzontale = posizioneOrizzontale;
    }

    public CampoDinamico getCampoDinamico() {

	return campoDinamico;
    }

    public Integer getPosizioneVerticale() {

	return posizioneVerticale;
    }

    public Integer getPosizioneOrizzontale() {

	return posizioneOrizzontale;
    }
}
