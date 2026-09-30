package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SDECOMUNIASSOCIATI")
public class Sdecomuniassociati {

    private SdecomuniassociatiId id;
    private Comuni comune;
    private String nomePdPresdom;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idente", column = @Column(name = "IDENTE", length = 15)),
	    @AttributeOverride(name = "codicecatastalecomune", column = @Column(name = "CODICECATASTALECOMUNE", length = 6)) })
    public SdecomuniassociatiId getId() {

	return id;
    }

    public void setId(SdecomuniassociatiId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICECATASTALECOMUNE", referencedColumnName = "CODICECOMUNE", nullable = false, insertable = false, updatable = false)
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    @Column(name = "NOME_PD_PRESDOM", length = 200)
    public String getNomePdPresdom() {

	return nomePdPresdom;
    }

    public void setNomePdPresdom(String nomePdPresdom) {

	this.nomePdPresdom = nomePdPresdom;
    }
}
