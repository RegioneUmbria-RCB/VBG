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
@Table(name = "ISTONERI_DETT_POSIZIONI")
public class IstoneriDettPosizioni implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3650185984174202663L;
    private PkId id;
    private Istanzeoneri istanzeoneri;
    private DettPosizioneDebitoria dettPosizioneDebitoria;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ISTONERI_DETT_POSIZIONI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_ISTANZEONERI_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Istanzeoneri getIstanzeoneri() {

	return istanzeoneri;
    }

    public void setIstanzeoneri(Istanzeoneri istanzeoneri) {

	this.istanzeoneri = istanzeoneri;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer istanzeoneriId;

    @Column(name = "FK_ISTANZEONERI_ID")
    @SuppressWarnings("unused")
    private Integer getIstanzeoneriId() {

	if (null != this.getIstanzeoneri()) {
	    if (null != this.getIstanzeoneri().getId()) {
		this.istanzeoneriId = getIstanzeoneri().getId().getCodice();
		return this.istanzeoneriId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setIstanzeoneriId(Integer istanzeoneriId) {

	if (null != this.getIstanzeoneri()) {
	    if (null != this.getIstanzeoneri().getId()) {
		this.istanzeoneriId = getIstanzeoneri().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_DETTPOSDEBITORIA_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public DettPosizioneDebitoria getDettPosizioneDebitoria() {

	return this.dettPosizioneDebitoria;
    }

    public void setDettPosizioneDebitoria(DettPosizioneDebitoria dettPosizioneDebitoria) {

	this.dettPosizioneDebitoria = dettPosizioneDebitoria;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer dettPosizioneDebitoriaId;

    @Column(name = "FK_DETTPOSDEBITORIA_ID")
    @SuppressWarnings("unused")
    private Integer getDettPosizioneDebitoriaId() {

	if (null != this.getDettPosizioneDebitoria()) {
	    if (null != this.getDettPosizioneDebitoria().getId()) {
		this.dettPosizioneDebitoriaId = getDettPosizioneDebitoria().getId().getCodice();
		return this.dettPosizioneDebitoriaId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setDettPosizioneDebitoriaId(Integer dettPosizioneDebitoriaId) {

	if (null != this.getDettPosizioneDebitoria()) {
	    if (null != this.getDettPosizioneDebitoria().getId()) {
		this.dettPosizioneDebitoriaId = getDettPosizioneDebitoria().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
