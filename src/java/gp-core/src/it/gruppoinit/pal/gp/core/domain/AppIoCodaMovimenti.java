package it.gruppoinit.pal.gp.core.domain;

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

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "APP_IO_CODA_MOVIMENTI")
public class AppIoCodaMovimenti implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8037209481848917388L;
    private AppIoCodaMovimentiId id;
    private Movimenti movimento;
    private AppIoCoda appIoCoda;

    public AppIoCodaMovimenti() {

	this.id = new AppIoCodaMovimentiId();
	this.movimento = new Movimenti();
	this.appIoCoda = new AppIoCoda();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codiceMovimenti", column = @Column(name = "CODICEMOVIMENTI", nullable = false, length = 10)),
	    @AttributeOverride(name = "guidCoda", column = @Column(name = "FK_GUIDCODA", nullable = false, length = 40)) })
    public AppIoCodaMovimentiId getId() {

	return id;
    }

    public void setId(AppIoCodaMovimentiId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEMOVIMENTO", referencedColumnName = "CODICEMOVIMENTO", nullable = false, insertable = false, updatable = false) })
    public Movimenti getMovimento() {

	return movimento;
    }

    public void setMovimento(Movimenti movimento) {

	this.movimento = movimento;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_GUIDCODA", referencedColumnName = "GUID", nullable = false, insertable = false, updatable = false) })
    public AppIoCoda getAppIoCoda() {

	return appIoCoda;
    }

    public void setAppIoCoda(AppIoCoda appIoCoda) {

	this.appIoCoda = appIoCoda;
    }
}
