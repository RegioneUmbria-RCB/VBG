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

@Entity
@Table(name = "ALBEROPROC_TEMPI")
public class AlberoprocTempi implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1345195554508341322L;
    private AlberoprocTempiId id;
    private Alberoproc alberoProc;
    private TempiFoT tempiFO;

    public AlberoprocTempi() {

	this.id = new AlberoprocTempiId();
	this.alberoProc = new Alberoproc();
	this.tempiFO = new TempiFoT();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkscid", column = @Column(name = "FK_SCID", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "fkidtempi", column = @Column(name = "FKID_TEMPI", nullable = false, precision = 10, scale = 0)) })
    public AlberoprocTempiId getId() {

	return this.id;
    }

    public void setId(AlberoprocTempiId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "FK_SCID", referencedColumnName = "SC_ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Alberoproc getAlberoProc() {

	return alberoProc;
    }

    public void setAlberoProc(Alberoproc alberoProc) {

	this.alberoProc = alberoProc;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({ @JoinColumn(name = "FKID_TEMPI", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public TempiFoT getTempiFO() {

	return tempiFO;
    }

    public void setTempiFO(TempiFoT tempiFO) {

	this.tempiFO = tempiFO;
    }
}
