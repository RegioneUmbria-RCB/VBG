package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Entity
@Table(name = "DETT_POSIZIONE_DA_ALLINEARE")
public class DettPosizioneDaAllineare implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2058497870171981836L;
    private DettPosizioneDaAllineareId id;

    public DettPosizioneDaAllineare() {

	this.id = new DettPosizioneDaAllineareId();
    }

    public DettPosizioneDaAllineare(Integer dettPosizioneDebitoriaId) {

	this.id = new DettPosizioneDaAllineareId(ORMHelper.getIdcomune(), dettPosizioneDebitoriaId);
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkDettposizionedebitoriaid", column = @Column(name = "FK_DETTPOSIZIONEDEBITORIAID", nullable = false, precision = 10, scale = 0)) })
    public DettPosizioneDaAllineareId getId() {

	return id;
    }

    public void setId(DettPosizioneDaAllineareId id) {

	this.id = id;
    }
}
