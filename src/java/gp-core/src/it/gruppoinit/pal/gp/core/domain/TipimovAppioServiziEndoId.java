package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class TipimovAppioServiziEndoId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8950875849764821432L;
    private String idcomune;
    private String tipomovimento;
    private String identificativoServizio;
    private Integer codiceinventario;

    public TipimovAppioServiziEndoId() {

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

    @Column(name = "CODICEINVENTARIO", nullable = false, length = 10)
    public Integer getCodiceinventario() {

	return codiceinventario;
    }

    public void setCodiceinventario(Integer codiceinventario) {

	this.codiceinventario = codiceinventario;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((identificativoServizio == null) ? 0 : identificativoServizio.hashCode());
	result = prime * result + ((tipomovimento == null) ? 0 : tipomovimento.hashCode());
	result = prime * result + ((codiceinventario == null) ? 0 : codiceinventario.hashCode());
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
	TipimovAppioServiziEndoId other = (TipimovAppioServiziEndoId) obj;
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
	if (codiceinventario == null) {
	    if (other.codiceinventario != null) {
		return false;
	    }
	} else if (!codiceinventario.equals(other.codiceinventario)) {
	    return false;
	}
	return true;
    }
}
