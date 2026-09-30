package it.gruppoinit.pal.gp.core.domain.helper;


public class SessioneElaborazionePEC {
    
    public enum TipoSessioneElaborazionePEC {
	PROCESSAMENTO, SINCRONIZZAZIONE;

	public String value() {

	    return name();
	}

	public static TipoSessioneElaborazionePEC fromValue(String v) {

		return valueOf(v);
	}
    }

    private ReportProcessamentoPecDTO esitoProcessamento;
    private boolean running = false;
    private long startMillis;
    private long endMillis;
    private String errore;
    private TipoSessioneElaborazionePEC tipoElaborazione;

    public ReportProcessamentoPecDTO getEsitoProcessamento() {

	return esitoProcessamento;
    }

    public void setEsitoProcessamento(ReportProcessamentoPecDTO esitoProcessamento) {

	this.esitoProcessamento = esitoProcessamento;
    }

    public boolean isRunning() {

	return running;
    }

    public void setRunning(boolean running) {

	this.running = running;
    }

    public long getStartMillis() {

	return startMillis;
    }

    public void setStartMillis(long startMillis) {

	this.startMillis = startMillis;
    }

    public long getEndMillis() {

	return endMillis;
    }

    public void setEndMillis(long endMillis) {

	this.endMillis = endMillis;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    
    public TipoSessioneElaborazionePEC getTipoProcessamento() {
    
        return tipoElaborazione;
    }

    
    public void setTipoProcessamento(TipoSessioneElaborazionePEC tipoElaborazione) {
    
        this.tipoElaborazione = tipoElaborazione;
    }
}
