package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class TipimovAppioServiziIntId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3170210545530036448L;
    private String idcomune;
    private String tipomovimento;
    private String identificativoServizio;
    private Integer fkAlberoprocScid;

    public TipimovAppioServiziIntId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "TIPOMOVIMENTO", nullable = false, length = 8)
    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    @Column(name = "IDENTIFICATIVO_SERVIZIO", nullable = false, length = 50)
    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }

    @Column(name = "FK_ALBEROPROC_SCID", nullable = false, length = 10)
    public Integer getFkAlberoprocScid() {

	return fkAlberoprocScid;
    }

    public void setFkAlberoprocScid(Integer fkAlberoprocScid) {

	this.fkAlberoprocScid = fkAlberoprocScid;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((identificativoServizio == null) ? 0 : identificativoServizio.hashCode());
	result = prime * result + ((tipomovimento == null) ? 0 : tipomovimento.hashCode());
	result = prime * result + ((fkAlberoprocScid == null) ? 0 : fkAlberoprocScid.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TipimovAppioServiziIntId other = (TipimovAppioServiziIntId) obj;
	if (idcomune == null) {
	    if (other.idcomune != null) {
		return false;
	    }
	} else if (!idcomune.equals(other.idcomune)) {
	    return false;
	}
	if (identificativoServizio == null) {
	    if (other.identificativoServizio != null) {
		return false;
	    }
	} else if (!identificativoServizio.equals(other.identificativoServizio)) {
	    return false;
	}
	if (tipomovimento == null) {
	    if (other.tipomovimento != null) {
		return false;
	    }
	} else if (!tipomovimento.equals(other.tipomovimento)) {
	    return false;
	}
	if (fkAlberoprocScid == null) {
	    if (other.fkAlberoprocScid != null) {
		return false;
	    }
	} else if (!fkAlberoprocScid.equals(other.fkAlberoprocScid)) {
	    return false;
	}
	return true;
    }
}
