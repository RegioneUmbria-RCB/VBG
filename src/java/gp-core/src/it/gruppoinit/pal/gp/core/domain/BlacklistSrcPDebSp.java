package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BLACKLIST_SRC_P_DEB_SP")
public class BlacklistSrcPDebSp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1875240141473510848L;
    private PkId id;
    private Date dataInserimento;
    private BlacklistMotivi blacklistMotivi;
    private DettPosizioneDebitoria dettPosizioneDebitoria;

    public BlacklistSrcPDebSp() {

    }

    public BlacklistSrcPDebSp(BlacklistMotivi blacklistMotivi, Date dataInserimento, DettPosizioneDebitoria dettPosizioneDebitoria) {

	this.setBlacklistMotivi(blacklistMotivi);
	this.setDataInserimento(dataInserimento);
	this.setDettPosizioneDebitoria(dettPosizioneDebitoria);
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BLACKLIST_SRC_P_DEB_SP.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_INSERIMENTO")
    public Date getDataInserimento() {

	return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {

	this.dataInserimento = dataInserimento;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_ID_BLACKLIST_MOT", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public BlacklistMotivi getBlacklistMotivi() {

	return blacklistMotivi;
    }

    public void setBlacklistMotivi(BlacklistMotivi blacklistMotivi) {

	this.blacklistMotivi = blacklistMotivi;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer blacklistMotiviId;

    @Column(name = "FK_ID_BLACKLIST_MOT")
    @SuppressWarnings("unused")
    private Integer getBlacklistMotiviId() {

	if (null != this.getBlacklistMotivi()) {
	    if (null != this.getBlacklistMotivi().getId()) {
		this.blacklistMotiviId = getBlacklistMotivi().getId().getCodice();
		return this.blacklistMotiviId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setBlacklistMotiviId(Integer blacklistMotiviId) {

	if (null != this.getBlacklistMotivi()) {
	    if (null != this.getBlacklistMotivi().getId()) {
		this.blacklistMotiviId = getBlacklistMotivi().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_ID_PAYPOS_DEB", referencedColumnName = "ID", insertable = false, updatable = false) })
    public DettPosizioneDebitoria getDettPosizioneDebitoria() {

	return this.dettPosizioneDebitoria;
    }

    public void setDettPosizioneDebitoria(DettPosizioneDebitoria dettPosizioneDebitoria) {

	this.dettPosizioneDebitoria = dettPosizioneDebitoria;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer dettPosizioneDebitoriaId;

    @Column(name = "FK_ID_PAYPOS_DEB")
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
