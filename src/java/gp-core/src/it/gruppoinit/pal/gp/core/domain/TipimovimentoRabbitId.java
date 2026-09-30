package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class TipimovimentoRabbitId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6548235103699315189L;
    private String idcomune;
    private String fkTipimovimento;
    private String topic;

    public TipimovimentoRabbitId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public TipimovimentoRabbitId(String fkTipimovimento, String topic) {

	super();
	this.idcomune = ORMHelper.getIdcomune();
	this.fkTipimovimento = fkTipimovimento;
	this.topic = topic;
    }

    public TipimovimentoRabbitId(String idcomune, String fkTipimovimento, String topic) {

	super();
	this.idcomune = idcomune;
	this.fkTipimovimento = fkTipimovimento;
	this.topic = topic;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_TIPIMOVIMENTO", nullable = false, length = 8)
    public String getFkTipimovimento() {

	return fkTipimovimento;
    }

    public void setFkTipimovimento(String fkTipimovimento) {

	this.fkTipimovimento = fkTipimovimento;
    }

    @Column(name = "TOPIC", length = 50)
    public String getTopic() {

	return topic;
    }

    public void setTopic(String topic) {

	this.topic = topic;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((getIdcomune() == null) ? 0 : this.getIdcomune().hashCode());
	result = prime * result + ((getFkTipimovimento() == null) ? 0 : this.getFkTipimovimento().hashCode());
	result = prime * result + ((getTopic() == null) ? 0 : this.getTopic().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object other) {

	if (this == other)
	    return true;
	if (other == null)
	    return false;
	if (!(other instanceof TipimovimentoRabbitId))
	    return false;
	TipimovimentoRabbitId castOther = (TipimovimentoRabbitId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getFkTipimovimento() == castOther.getFkTipimovimento()) || (this.fkTipimovimento != null
			&& castOther.getFkTipimovimento() != null && this.getFkTipimovimento().equals(castOther.getFkTipimovimento())))
		&& ((this.getTopic() == castOther.getTopic())
			|| (this.getTopic() != null && castOther.getTopic() != null && this.getTopic().equals(castOther.getTopic())));
    }
}
