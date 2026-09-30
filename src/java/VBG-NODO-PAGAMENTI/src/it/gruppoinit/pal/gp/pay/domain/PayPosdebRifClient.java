package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.gruppoinit.pal.gp.core.domain.IdAwareDomainObject;

@Entity
@Table(name = "PAY_POSDEB_RIFCLIENT")
public class PayPosdebRifClient implements Serializable, IdAwareDomainObject<PayPosdebRifClientId> {

    /**
     * 
     */
    private static final long serialVersionUID = -193046764928956398L;
    private PayPosdebRifClientId id;
    private PayPosizioniDebitorie posizioneDebitoria;
    private String riferimentoClient;

    public PayPosdebRifClient() {

	super();
	this.id = new PayPosdebRifClientId();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "guid", column = @Column(name = "GUID", nullable = false, length = 60)) })
    public PayPosdebRifClientId getId() {

	return id;
    }

    public void setId(PayPosdebRifClientId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_POSIZIONE_DEBITORIA", referencedColumnName = "ID", insertable = false, updatable = false) })
    public PayPosizioniDebitorie getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public void setPosizioneDebitoria(PayPosizioniDebitorie posizioneDebitoria) {

	this.posizioneDebitoria = posizioneDebitoria;
    }

    private Integer posizioneDebitoriaId;

    @Column(name = "FK_POSIZIONE_DEBITORIA")
    private Integer getPosizioneDebitoriaId() {

	if (null != this.getPosizioneDebitoria() && null != this.getPosizioneDebitoria().getId()) {
	    this.posizioneDebitoriaId = getPosizioneDebitoria().getId().getCodice();
	    return this.posizioneDebitoriaId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setPosizioneDebitoriaId(Integer id) {

	if (null != this.getPosizioneDebitoria() && null != this.getPosizioneDebitoria().getId()) {
	    this.posizioneDebitoriaId = getPosizioneDebitoria().getId().getCodice();
	}
    }

    @Column(name = "RIFERIMENTO_CLIENT")
    public String getRiferimentoClient() {

	return riferimentoClient;
    }

    public void setRiferimentoClient(String riferimentoClient) {

	this.riferimentoClient = riferimentoClient;
    }
}
