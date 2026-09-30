package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProtTipidocumentoMetadatiId implements java.io.Serializable {

    private static final long serialVersionUID = 2461953888665926889L;
    private String idcomune;
    private Integer fkidprottpdoc;
    private String fkidmetadatidizbase;

    public ProtTipidocumentoMetadatiId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public ProtTipidocumentoMetadatiId(Integer fkidprottpdoc, String fkidmetadatidizbase) {

	this();
	this.fkidprottpdoc = fkidprottpdoc;
	this.fkidmetadatidizbase = fkidmetadatidizbase;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FKIDPROTTPDOC", nullable = false, precision = 10, scale = 0)
    public Integer getFkidprottpdoc() {

	return fkidprottpdoc;
    }

    public void setFkidprottpdoc(Integer fkidprottpdoc) {

	this.fkidprottpdoc = fkidprottpdoc;
    }

    @Column(name = "FKIDMETADATIDIZBASE", nullable = false, length = 30)
    public String getFkidmetadatidizbase() {

	return fkidmetadatidizbase;
    }

    public void setFkidmetadatidizbase(String fkidmetadatidizbase) {

	this.fkidmetadatidizbase = fkidmetadatidizbase;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((fkidmetadatidizbase == null) ? 0 : fkidmetadatidizbase.hashCode());
	result = prime * result + ((fkidprottpdoc == null) ? 0 : fkidprottpdoc.hashCode());
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
	ProtTipidocumentoMetadatiId other = (ProtTipidocumentoMetadatiId) obj;
	if (fkidmetadatidizbase == null) {
	    if (other.fkidmetadatidizbase != null)
		return false;
	} else if (!fkidmetadatidizbase.equals(other.fkidmetadatidizbase))
	    return false;
	if (fkidprottpdoc == null) {
	    if (other.fkidprottpdoc != null)
		return false;
	} else if (!fkidprottpdoc.equals(other.fkidprottpdoc))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	return true;
    }
}
