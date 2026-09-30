package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IstanzeaffissioniassegnazioniId implements Serializable {

    private static final long serialVersionUID = -3620519457758611710L;
    private String idcomune;
    private Integer codiceistanza;
    private Integer fkIstanzeaffissioniid;
    private Integer fkImpiantopubblicitario;
    private Integer fkImpiantopubblicitarioiddett;

    public IstanzeaffissioniassegnazioniId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public IstanzeaffissioniassegnazioniId(Integer codiceistanza, Integer fkIstanzeaffissioniid, Integer fkImpiantopubblicitario,
	    Integer fkImpiantopubblicitarioiddett) {

	this();
	this.codiceistanza = codiceistanza;
	this.fkIstanzeaffissioniid = fkIstanzeaffissioniid;
	this.fkImpiantopubblicitario = fkImpiantopubblicitario;
	this.fkImpiantopubblicitarioiddett = fkImpiantopubblicitarioiddett;
    }

    public IstanzeaffissioniassegnazioniId(String idcomune, Integer codiceistanza, Integer fkIstanzeaffissioniid, Integer fkImpiantopubblicitario,
	    Integer fkImpiantopubblicitarioiddett) {

	this();
	this.idcomune = idcomune;
	this.codiceistanza = codiceistanza;
	this.fkIstanzeaffissioniid = fkIstanzeaffissioniid;
	this.fkImpiantopubblicitario = fkImpiantopubblicitario;
	this.fkImpiantopubblicitarioiddett = fkImpiantopubblicitarioiddett;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "CODICEISTANZA", nullable = false, precision = 6, scale = 0)
    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    @Column(name = "FK_ISTANZEAFFISSIONIID", nullable = false, precision = 3, scale = 0)
    public Integer getFkIstanzeaffissioniid() {

	return fkIstanzeaffissioniid;
    }

    public void setFkIstanzeaffissioniid(Integer fkIstanzeaffissioniid) {

	this.fkIstanzeaffissioniid = fkIstanzeaffissioniid;
    }

    @Column(name = "FK_IMPIANTOPUBBLICITARIO", nullable = false, precision = 3, scale = 0)
    public Integer getFkImpiantopubblicitario() {

	return fkImpiantopubblicitario;
    }

    public void setFkImpiantopubblicitario(Integer fkImpiantopubblicitario) {

	this.fkImpiantopubblicitario = fkImpiantopubblicitario;
    }

    @Column(name = "FK_IMPIANTOPUBBLICITARIOIDDETT", nullable = false, precision = 3, scale = 0)
    public Integer getFkImpiantopubblicitarioiddett() {

	return fkImpiantopubblicitarioiddett;
    }

    public void setFkImpiantopubblicitarioiddett(Integer fkImpiantopubblicitarioiddett) {

	this.fkImpiantopubblicitarioiddett = fkImpiantopubblicitarioiddett;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceistanza == null) ? 0 : codiceistanza.hashCode());
	result = prime * result + ((fkImpiantopubblicitario == null) ? 0 : fkImpiantopubblicitario.hashCode());
	result = prime * result + ((fkImpiantopubblicitarioiddett == null) ? 0 : fkImpiantopubblicitarioiddett.hashCode());
	result = prime * result + ((fkIstanzeaffissioniid == null) ? 0 : fkIstanzeaffissioniid.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	IstanzeaffissioniassegnazioniId other = (IstanzeaffissioniassegnazioniId) obj;
	if (codiceistanza == null) {
	    if (other.codiceistanza != null) {
		return false;
	    }
	} else if (!codiceistanza.equals(other.codiceistanza)) {
	    return false;
	}
	if (fkImpiantopubblicitario == null) {
	    if (other.fkImpiantopubblicitario != null) {
		return false;
	    }
	} else if (!fkImpiantopubblicitario.equals(other.fkImpiantopubblicitario)) {
	    return false;
	}
	if (fkImpiantopubblicitarioiddett == null) {
	    if (other.fkImpiantopubblicitarioiddett != null) {
		return false;
	    }
	} else if (!fkImpiantopubblicitarioiddett.equals(other.fkImpiantopubblicitarioiddett)) {
	    return false;
	}
	if (fkIstanzeaffissioniid == null) {
	    if (other.fkIstanzeaffissioniid != null) {
		return false;
	    }
	} else if (!fkIstanzeaffissioniid.equals(other.fkIstanzeaffissioniid)) {
	    return false;
	}
	if (idcomune == null) {
	    if (other.idcomune != null) {
		return false;
	    }
	} else if (!idcomune.equals(other.idcomune)) {
	    return false;
	}
	return true;
    }
}
