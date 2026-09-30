package it.gruppoinit.pal.gp.core.features.buslightyear.model;

public class OperazioneEventoBean {

    public enum CHIAMANTE {
	MERCATIPRESENZE_D,
	AUTORIZZAZIONI_SUBENTRI,
	ABBONAMENTI_MODIFICA_OCCUPANTE
    }

    private String idRiferimento;
    private String messaggio;
    private CHIAMANTE chiamante;

    public OperazioneEventoBean(String idRiferimento, String messaggio, CHIAMANTE chiamante) {

	this.idRiferimento = idRiferimento;
	this.messaggio = messaggio;
	this.chiamante = chiamante;
    }

    public String getIdRiferimento() {

	return idRiferimento;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public CHIAMANTE getChiamante() {

	return chiamante;
    }
}
