package it.gruppoinit.pal.gp.core.dao.helper;

/**
 * DTO utilizzato per veicolare il risultato del calcolo delle presenze delle manifestazioni.<br />
 * Contiene il numero delle presenze ed il numero delle presenze come proprietario.
 * 
 * @author fabrizioc
 * 
 */
public class MercatiPresenzeDTO {

    private Integer presenze;
    private Integer presenzeComeProprietario;
    private boolean inseritoAutorizzazione;
    private boolean inseritoPresenze;
    private boolean inseritoSpuntistiMercato;

    public MercatiPresenzeDTO() {

    }

    public Integer getPresenze() {

	return presenze == null ? 0 : presenze;
    }

    public void setPresenze(Integer presenze) {

	this.presenze = presenze;
    }

    public void setPresenzeComeProprietario(Integer presenzeComeProprietario) {

	this.presenzeComeProprietario = presenzeComeProprietario;
    }

    public Integer getPresenzeComeProprietario() {

	return presenzeComeProprietario == null ? 0 : presenzeComeProprietario;
    }

    /**
     * @return the inseritoAutorizzazione
     */
    public boolean isInseritoAutorizzazione() {

	return inseritoAutorizzazione;
    }

    /**
     * @param inseritoAutorizzazione
     *            the inseritoAutorizzazione to set
     */
    public void setInseritoAutorizzazione(boolean inseritoAutorizzazione) {

	this.inseritoAutorizzazione = inseritoAutorizzazione;
    }

    /**
     * @return the inseritoPresenze
     */
    public boolean isInseritoPresenze() {

	return inseritoPresenze;
    }

    /**
     * @param inseritoPresenze
     *            the inseritoPresenze to set
     */
    public void setInseritoPresenze(boolean inseritoPresenze) {

	this.inseritoPresenze = inseritoPresenze;
    }

    /**
     * @return the inseritoSpuntistiMercato
     */
    public boolean isInseritoSpuntistiMercato() {

	return inseritoSpuntistiMercato;
    }

    /**
     * @param inseritoSpuntistiMercato
     *            the inseritoSpuntistiMercato to set
     */
    public void setInseritoSpuntistiMercato(boolean inseritoSpuntistiMercato) {

	this.inseritoSpuntistiMercato = inseritoSpuntistiMercato;
    }

    public void addPresenze(Integer presenze) {

	if (presenze != null) {
	    if (this.presenze != null) {
		this.presenze = this.presenze.intValue() + presenze.intValue();
	    } else {
		this.presenze = presenze.intValue();
	    }
	}
    }

    public void addPresenzeComeProprietario(Integer presenzeComeProprietario) {

	if (presenzeComeProprietario != null) {
	    if (this.presenzeComeProprietario != null) {
		this.presenzeComeProprietario = this.presenzeComeProprietario.intValue() + presenzeComeProprietario.intValue();
	    } else {
		this.presenzeComeProprietario = presenzeComeProprietario.intValue();
	    }
	}
    }

    @Override
    public String toString() {

	return "presenze:" + getPresenze() + ", presenzeComeProprietario:" + getPresenzeComeProprietario();
    }
}
