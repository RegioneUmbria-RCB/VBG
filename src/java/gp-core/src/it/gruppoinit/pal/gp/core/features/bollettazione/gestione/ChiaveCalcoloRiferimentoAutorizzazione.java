package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

public class ChiaveCalcoloRiferimentoAutorizzazione {

    private Integer idAutorizzazione;
    private boolean subentro;
    private Integer idPosteggio;
    private Integer idUso;

    public ChiaveCalcoloRiferimentoAutorizzazione(Integer idAutorizzazione, boolean subentro, Integer idPosteggio, Integer idUso) {

	super();
	this.idAutorizzazione = idAutorizzazione;
	this.subentro = subentro;
	this.idPosteggio = idPosteggio;
	this.idUso = idUso;
    }

    public String getChiave() {

	return String.valueOf(this.idAutorizzazione) + "-" + idUso + "-" + idPosteggio + "-" + this.subentro;
    }
}
