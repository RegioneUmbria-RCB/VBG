package it.gruppoinit.mailservice.oggetti;

public class VerticalizzazioniMailServiceBean {

    public enum TipoRicevuta {
	breve, sintetica, standard
    };

    private TipoRicevuta tipoRicevutaConsegna;
    private boolean checkReplayAttivo;
    private String folder;

    public VerticalizzazioniMailServiceBean() {

    }

    public TipoRicevuta getTipoRicevutaConsegna() {

	return tipoRicevutaConsegna;
    }

    public void setTipoRicevutaConsegna(TipoRicevuta tipoRicevutaConsegna) {

	this.tipoRicevutaConsegna = tipoRicevutaConsegna;
    }

    public boolean isCheckReplayAttivo() {

	return checkReplayAttivo;
    }

    public void setCheckReplayAttivo(boolean checkReplayAttivo) {

	this.checkReplayAttivo = checkReplayAttivo;
    }

    public String getFolder() {

	return folder;
    }

    public void setFolder(String folder) {

	this.folder = folder;
    }
}
