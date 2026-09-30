package it.gruppoinit.pal.gp.core.domain;

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

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "ALBEROPROC_COMUNI_ESCLUSI")
public class AlberoprocComuniEsclusi implements java.io.Serializable {

    private static final long serialVersionUID = -823616836991567883L;
    private AlberoprocComuniEsclusiId id;
    private Alberoproc alberoproc;
    private Comuni comune;

    public AlberoprocComuniEsclusi() {

	this.id = new AlberoprocComuniEsclusiId();
	this.alberoproc = new Alberoproc();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkScid", column = @Column(name = "FK_SCID", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "codiceComune", column = @Column(name = "CODICECOMUNE", nullable = false, length = 5)) })
    public AlberoprocComuniEsclusiId getId() {

	return this.id;
    }

    public void setId(AlberoprocComuniEsclusiId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_SCID", referencedColumnName = "SC_ID", nullable = false, insertable = false, updatable = false) })
    public Alberoproc getAlberoproc() {

	return alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE", nullable = false, insertable = false, updatable = false)
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }
}