package it.gruppoinit.pal.gp.pay.domain;

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.TableGenerator;

import it.gruppoinit.pal.gp.core.domain.BaseDomainObject;
import it.gruppoinit.pal.gp.core.domain.IdAwareDomainObject;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.service.helper.CaricamentoMassivoStatiEnum;

@Entity
@Table(name = "PAY_POS_DEB_MASSIVE")
public class PayPosDebMassive extends BaseDomainObject implements Serializable, IdAwareDomainObject<PkId> {

    /**
     * 
     */
    private static final long serialVersionUID = -4774924483405678137L;
    private PkId id;
    private PayPosizioniDebitorie posizioneDebitoria;
    private String idOperazione;
    private String flagProcessata;
    private String messaggio;

    public PayPosDebMassive() {

	super();
	this.id = new PkId();
    }

    public PayPosDebMassive(PayPosizioniDebitorie posElaborata, String identificativoOperazione, CaricamentoMassivoStatiEnum statoProcessamento) {

	this();
	this.posizioneDebitoria = posElaborata;
	this.idOperazione = identificativoOperazione;
	this.flagProcessata = statoProcessamento.getValore();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = TableGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = TableGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = TableGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = TableGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = TableGenerator.SEGMENT_VALUE_PARAM, value = "PAY_POS_DEB_MASSIVE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 9, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({ @JoinColumn(name = "FK_POSIZIONE_DEBITORIA", referencedColumnName = "ID", insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false) })
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

    @Column(name = "ID_OPERAZIONE", nullable = true, length = 100)
    public String getIdOperazione() {

	return idOperazione;
    }

    public void setIdOperazione(String idOperazione) {

	this.idOperazione = idOperazione;
    }

    @Column(name = "FLAG_PROCESSATA", nullable = true, length = 1)
    public String getFlagProcessata() {

	return flagProcessata;
    }

    public void setFlagProcessata(String flagProcessata) {

	this.flagProcessata = flagProcessata;
    }

    @Column(name = "MESSAGGIO", nullable = true, length = 4000)
    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }
}
