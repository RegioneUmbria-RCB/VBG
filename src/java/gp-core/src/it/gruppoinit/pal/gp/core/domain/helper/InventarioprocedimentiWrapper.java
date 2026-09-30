package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

public class InventarioprocedimentiWrapper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1145847798676377191L;
    private PkId id;
    private Inventarioprocedimenti ip;
    private String ovverrideDescrizione;
    private boolean intervento;

    public String getOvverrideDescrizione() {

	return ovverrideDescrizione;
    }

    public void setOvverrideDescrizione(String ovverrideDescrizione) {

	this.ovverrideDescrizione = ovverrideDescrizione;
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Inventarioprocedimenti getIp() {

	return ip;
    }

    public void setIp(Inventarioprocedimenti ip) {

	this.ip = ip;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof InventarioprocedimentiWrapper))
	    return false;
	InventarioprocedimentiWrapper castOther = (InventarioprocedimentiWrapper) other;
	return ((this.getId().getIdcomune() == castOther.getId().getIdcomune()) || (this.getId().getIdcomune() != null
		&& castOther.getId().getIdcomune() != null && this.getId().getIdcomune().equals(castOther.getId().getIdcomune())))
		&& ((this.getId().getCodice() == castOther.getId().getCodice()) || (this.getId().getCodice() != null
			&& castOther.getId().getCodice() != null && this.getId().getCodice().equals(castOther.getId().getCodice())));
    }

    public int hashCode() {

	int result = 17;
	result = 38 * result + (getId().getIdcomune() == null ? 0 : this.getId().getIdcomune().hashCode());
	result = 38 * result + (getId().getCodice() == null ? 0 : this.getId().getCodice().intValue());
	return result;
    }

    public boolean isIntervento() {

	return intervento;
    }

    public void setIntervento(boolean intervento) {

	this.intervento = intervento;
    }
}
