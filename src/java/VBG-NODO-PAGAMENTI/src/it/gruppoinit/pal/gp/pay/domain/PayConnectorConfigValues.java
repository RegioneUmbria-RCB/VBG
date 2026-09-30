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

@Entity
@Table(name = "PAY_CONNECTOR_CONFIG_VALUES")
public class PayConnectorConfigValues extends BaseDomainObject implements Serializable, IdAwareDomainObject<PkId> {

    private static final long serialVersionUID = 2590267198191015361L;
    private PkId id;
    private String valore;
    private PayConnectorConfigParams configParam;
    private PayConnectorConfig connettore;

    public PayConnectorConfigValues() {

	this.id = new PkId();
	this.connettore = new PayConnectorConfig();
	this.configParam = new PayConnectorConfigParams();
    }

    public PayConnectorConfigValues(PkId id) {

	this.id = id;
	this.connettore = new PayConnectorConfig();
	this.configParam = new PayConnectorConfigParams();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = TableGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = TableGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = TableGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = TableGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = TableGenerator.SEGMENT_VALUE_PARAM, value = "PAY_CONNECTOR_CONFIG_VALUES.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "VALORE", nullable = true, length = 500)
    public String getValore() {

	return this.valore;
    }

    public void setValore(String val) {

	this.valore = val;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CONFIG_PARAM", referencedColumnName = "CONFIG_PARAM", insertable = false, updatable = false)
    public PayConnectorConfigParams getConfigParam() {

	return configParam;
    }

    public void setConfigParam(PayConnectorConfigParams configParam) {

	this.configParam = configParam;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({ @JoinColumn(name = "CODICE_CONNETTORE", insertable = false, updatable = false) })
    public PayConnectorConfig getConnettore() {

	return connettore;
    }

    public void setConnettore(PayConnectorConfig connettore) {

	this.connettore = connettore;
    }
}
