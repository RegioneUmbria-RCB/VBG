package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "DYN2_METADATI", uniqueConstraints = @UniqueConstraint(columnNames = { "IDCOMUNE", "FK_CONTESTO_ID", "CONTESTO_CAMPO" }))
public class Dyn2Metadati implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8986832238996354566L;
    private PkId id;
    private Dyn2MetadatiContesti dyn2MetadatiContesti;
    private Dyn2Campi dyn2Campi;
    private String contestoCampo;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "DYN2_METADATI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_CONTESTO_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Dyn2MetadatiContesti getDyn2MetadatiContesti() {

	return dyn2MetadatiContesti;
    }

    public void setDyn2MetadatiContesti(Dyn2MetadatiContesti dyn2MetadatiContesti) {

	this.dyn2MetadatiContesti = dyn2MetadatiContesti;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer dyn2MetadatiContestiId;

    @Column(name = "FK_CONTESTO_ID")
    @SuppressWarnings("unused")
    private Integer getDyn2MetadatiContestiId() {

	if (null != this.getDyn2MetadatiContesti()) {
	    if (null != this.getDyn2MetadatiContesti().getId()) {
		this.dyn2MetadatiContestiId = getDyn2MetadatiContesti().getId().getCodice();
		return this.dyn2MetadatiContestiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setDyn2MetadatiContestiId(Integer dyn2MetadatiContestiId) {

	if (null != this.getDyn2MetadatiContesti()) {
	    if (null != this.getDyn2MetadatiContesti().getId()) {
		this.dyn2MetadatiContestiId = getDyn2MetadatiContesti().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_CAMPODINAMICO_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Dyn2Campi getDyn2Campi() {

	return dyn2Campi;
    }

    public void setDyn2Campi(Dyn2Campi dyn2Campi) {

	this.dyn2Campi = dyn2Campi;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer dyn2CampiId;

    @Column(name = "FK_CAMPODINAMICO_ID")
    @SuppressWarnings("unused")
    private Integer getDyn2CampiId() {

	if (null != this.getDyn2Campi()) {
	    if (null != this.getDyn2Campi().getId()) {
		this.dyn2CampiId = getDyn2Campi().getId().getCodice();
		return this.dyn2CampiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setDyn2CampiId(Integer dyn2CampiId) {

	if (null != this.getDyn2Campi()) {
	    if (null != this.getDyn2Campi().getId()) {
		this.dyn2CampiId = getDyn2Campi().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @Column(name = "CONTESTO_CAMPO", length = 255)
    public String getContestoCampo() {

	return contestoCampo;
    }

    public void setContestoCampo(String contestoCampo) {

	this.contestoCampo = contestoCampo;
    }
}
