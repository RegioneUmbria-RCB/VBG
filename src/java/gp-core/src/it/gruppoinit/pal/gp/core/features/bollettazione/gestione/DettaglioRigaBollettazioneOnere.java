package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

public class DettaglioRigaBollettazioneOnere {

    private String istanza;
    private String protocollo;
    private String intervento;

    public DettaglioRigaBollettazioneOnere(String istanza, String protocollo, String intervento) {

	this.istanza = istanza;
	this.protocollo = protocollo;
	this.intervento = intervento;
    }

    public String getIstanza() {

	return istanza;
    }

    public String getProtocollo() {

	return protocollo;
    }

    public String getIntervento() {

	return intervento;
    }
}
