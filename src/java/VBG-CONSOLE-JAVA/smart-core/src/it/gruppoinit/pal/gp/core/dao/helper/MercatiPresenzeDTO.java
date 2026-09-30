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
