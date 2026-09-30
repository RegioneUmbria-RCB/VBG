package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class AlberoprocTempiId implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8822023323358657101L;
    private String idcomune;
    private Integer fkscid;
    private Integer fkidtempi;

    public AlberoprocTempiId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public AlberoprocTempiId(Integer codiceIntervento, Integer codiceTempoFo) {

	this.idcomune = ORMHelper.getIdcomune();
	this.fkscid = codiceIntervento;
	this.fkidtempi = codiceTempoFo;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_SCID", nullable = false, precision = 10, scale = 0)
    public Integer getFkscid() {

	return fkscid;
    }

    public void setFkscid(Integer fkscid) {

	this.fkscid = fkscid;
    }

    @Column(name = "FKID_TEMPI", nullable = false, precision = 10, scale = 0)
    public Integer getFkidtempi() {

	return fkidtempi;
    }

    public void setFkidtempi(Integer fkidtempi) {

	this.fkidtempi = fkidtempi;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof AlberoprocTempiId))
	    return false;
	AlberoprocTempiId castOther = (AlberoprocTempiId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkscid() == castOther.getFkscid())
			|| (this.getFkscid() != null && castOther.getFkscid() != null && this.getFkscid().equals(castOther.getFkscid())))
		&& ((this.getFkidtempi() == castOther.getFkidtempi())
			|| (this.getFkidtempi() != null && castOther.getFkidtempi() != null && this.getFkidtempi().equals(castOther.getFkidtempi())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getFkscid() == null ? 0 : this.getFkscid().hashCode());
	result = 37 * result + (getFkidtempi() == null ? 0 : this.getFkidtempi().hashCode());
	return result;
    }
}
