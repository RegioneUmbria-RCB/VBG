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
@Table(name = "MASSIVE_D_APPIO_CODA")
public class AppIoCodaMassiveD implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5734562450131874846L;
    private AppIoCodaMassiveDId id;
    private MassiveDettaglio massiveDettaglio;
    private AppIoCoda appIoCoda;

    public AppIoCodaMassiveD() {

	this.id = new AppIoCodaMassiveDId();
	this.massiveDettaglio = new MassiveDettaglio();
	this.appIoCoda = new AppIoCoda();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "idDettaglioComunicazione", column = @Column(name = "FK_MASSIVE_D", nullable = false, length = 10)),
	    @AttributeOverride(name = "guidCoda", column = @Column(name = "FK_GUIDCODA", nullable = false, length = 40)) })
    public AppIoCodaMassiveDId getId() {

	return id;
    }

    public void setId(AppIoCodaMassiveDId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_MASSIVE_D", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveDettaglio getMassiveDettaglio() {

	return massiveDettaglio;
    }

    public void setMassiveDettaglio(MassiveDettaglio massiveDettaglio) {

	this.massiveDettaglio = massiveDettaglio;
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
