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
@Table(name = "STP_TIPOLOGIE_ENDO2")
public class StpTipologieEndo2 implements java.io.Serializable {

    private static final long serialVersionUID = 3936480848202631443L;
    private PkId id;
    private String descrizione;
    private Azioni azioni;

    public StpTipologieEndo2() {

	this.id = new PkId();
	this.azioni = new Azioni();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    // modificato
    @Column(name = "DESCRIZIONE", length = 250)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FKIDAZIONE")
    public Azioni getAzioni() {

	return azioni;
    }

    public void setAzioni(Azioni azioni) {

	this.azioni = azioni;
    }
}
