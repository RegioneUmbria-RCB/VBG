package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MERCATI_MASSIVE_T")
public class MercatiMassiveT implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5799285723416765732L;
    private PkId id;
    private MassiveTestata massiveTestata;
    private String software;

    public MercatiMassiveT() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATI_MASSIVE_T.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 24)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_TESTATA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveTestata getMassiveTestata() {

	return massiveTestata;
    }

    public void setMassiveTestata(MassiveTestata massiveTestata) {

	this.massiveTestata = massiveTestata;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveTestataId;

    @Column(name = "FKID_TESTATA")
    @SuppressWarnings("unused")
    private Integer getMassiveTestataId() {

	if (null != this.getMassiveTestata()) {
	    if (null != this.getMassiveTestata().getId()) {
		this.massiveTestataId = getMassiveTestata().getId().getCodice();
		return this.massiveTestataId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveTestataId(Integer massiveTestataId) {

	if (null != this.getMassiveTestata()) {
	    if (null != this.getMassiveTestata().getId()) {
		this.massiveTestataId = getMassiveTestata().getId().getCodice();
	    }
	}
    }

    @Column(name = "SOFTWARE", length = 2)
    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
    //END FIX/////////////////////////////////////////////////////
}
