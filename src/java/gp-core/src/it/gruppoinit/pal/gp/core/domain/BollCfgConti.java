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

@Entity
@Table(name = "BOLL_CFG_CONTI")
public class BollCfgConti implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3100731548457736154L;
    private BollCfgContiId id;
    private BollCfgTipo bollCfgTipo;
    private Conti conto;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkBollcfgtipoId", column = @Column(name = "FK_BOLLCFGTIPO_ID", nullable = false, precision = 4, scale = 0)),
	    @AttributeOverride(name = "fkContoId", column = @Column(name = "FK_CONTO_ID", nullable = false, precision = 10, scale = 0)) })
    public BollCfgContiId getId() {

	return id;
    }

    public void setId(BollCfgContiId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_BOLLCFGTIPO_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public BollCfgTipo getBollCfgTipo() {

	return bollCfgTipo;
    }

    public void setBollCfgTipo(BollCfgTipo bollCfgTipo) {

	this.bollCfgTipo = bollCfgTipo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CONTO_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Conti getConto() {

	return conto;
    }

    public void setConto(Conti conto) {

	this.conto = conto;
    }
}
