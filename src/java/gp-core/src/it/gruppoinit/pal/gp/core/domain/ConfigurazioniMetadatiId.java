package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class ConfigurazioniMetadatiId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2392760275762220256L;
    private String idcomune;
    private Integer idComuniAssociatiSoftware;
    private String chiave;

    public ConfigurazioniMetadatiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public ConfigurazioniMetadatiId(Integer idComuniAssociatiSoftware, String chiave) {

	this.idcomune = ORMHelper.getIdcomune();
	this.idComuniAssociatiSoftware = idComuniAssociatiSoftware;
	this.chiave = chiave;
    }

    public ConfigurazioniMetadatiId(String idcomune, Integer idComuniAssociatiSoftware, String chiave) {

	super();
	this.idcomune = idcomune;
	this.idComuniAssociatiSoftware = idComuniAssociatiSoftware;
	this.chiave = chiave;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FKIDCOMUNIASSOCIATISOFTWARE", nullable = false, precision = 8, scale = 0)
    public Integer getIdComuniAssociatiSoftware() {

	return idComuniAssociatiSoftware;
    }

    public void setIdComuniAssociatiSoftware(Integer idComuniAssociatiSoftware) {

	this.idComuniAssociatiSoftware = idComuniAssociatiSoftware;
    }

    @Column(name = "CHIAVE", nullable = false, length = 254)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((chiave == null) ? 0 : chiave.hashCode());
	result = prime * result + ((idComuniAssociatiSoftware == null) ? 0 : idComuniAssociatiSoftware.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
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
	ConfigurazioniMetadatiId other = (ConfigurazioniMetadatiId) obj;
	if (chiave == null) {
	    if (other.chiave != null)
		return false;
	} else if (!chiave.equals(other.chiave))
	    return false;
	if (idComuniAssociatiSoftware == null) {
	    if (other.idComuniAssociatiSoftware != null)
		return false;
	} else if (!idComuniAssociatiSoftware.equals(other.idComuniAssociatiSoftware))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	return true;
    }
}
