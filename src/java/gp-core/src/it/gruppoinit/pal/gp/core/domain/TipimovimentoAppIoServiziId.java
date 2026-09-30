package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class TipimovimentoAppIoServiziId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -290761301110483522L;
    private String idcomune;
    private String tipomovimento;
    private String identificativoServizio;

    public TipimovimentoAppIoServiziId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public TipimovimentoAppIoServiziId(String idcomune, String tipomovimento, String identificativoServizio) {

	this.idcomune = idcomune;
	this.tipomovimento = tipomovimento;
	this.identificativoServizio = identificativoServizio;
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

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((identificativoServizio == null) ? 0 : identificativoServizio.hashCode());
	result = prime * result + ((tipomovimento == null) ? 0 : tipomovimento.hashCode());
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
	TipimovimentoAppIoServiziId other = (TipimovimentoAppIoServiziId) obj;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	if (identificativoServizio == null) {
	    if (other.identificativoServizio != null)
		return false;
	} else if (!identificativoServizio.equals(other.identificativoServizio))
	    return false;
	if (tipomovimento == null) {
	    if (other.tipomovimento != null)
		return false;
	} else if (!tipomovimento.equals(other.tipomovimento))
	    return false;
	return true;
    }
}
