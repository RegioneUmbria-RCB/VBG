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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import it.gruppoinit.pal.gp.core.domain.BaseDomainObject;
import it.gruppoinit.pal.gp.core.domain.IdAwareDomainObject;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Entity
@Table(name = "PAY_REGCAUSALI_PARAMETRI")
public class PayRegcausaliParametri extends BaseDomainObject implements Serializable, IdAwareDomainObject<PkId> {

    /**
     * 
     */
    private static final long serialVersionUID = -8845336490342024986L;
    private PkId id;
    private PayRegistrazioniCausali payRegistrazioniCausali;
    private String chiave;
    private String valore;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = TableGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = TableGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = TableGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = TableGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = TableGenerator.SEGMENT_VALUE_PARAM, value = "PAY_REGCAUSALI_PARAMETRI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumns({ @JoinColumn(name = "FK_PAYREGCAUSALE_ID", referencedColumnName = "ID", insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false) })
    public PayRegistrazioniCausali getPayRegistrazioniCausali() {

	return payRegistrazioniCausali;
    }

    public void setPayRegistrazioniCausali(PayRegistrazioniCausali payRegistrazioniCausali) {

	this.payRegistrazioniCausali = payRegistrazioniCausali;
    }

    private Integer payRegistrazioniCausaliId;

    @Column(name = "FK_PAYREGCAUSALE_ID")
    private Integer getPayRegistrazioniCausaliId() {

	if (null != this.getPayRegistrazioniCausali()) {
	    if (null != this.getPayRegistrazioniCausali().getId()) {
		this.payRegistrazioniCausaliId = getPayRegistrazioniCausali().getId().getCodice();
		return this.payRegistrazioniCausaliId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setPayRegistrazioniCausaliId(Integer ammId) {

	if (null != this.getPayRegistrazioniCausali()) {
	    if (null != this.getPayRegistrazioniCausali().getId()) {
		this.payRegistrazioniCausaliId = getPayRegistrazioniCausali().getId().getCodice();
	    }
	}
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "CHIAVE", nullable = false, length = 50)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    @NotEmpty
    @Length(max = 4000)
    @Column(name = "VALORE", nullable = false, length = 4000)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
