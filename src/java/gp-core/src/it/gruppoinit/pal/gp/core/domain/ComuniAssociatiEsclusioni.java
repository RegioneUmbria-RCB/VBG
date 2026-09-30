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
@Table(name = "COMUNIASSOCIATIESCLUSIONI")
public class ComuniAssociatiEsclusioni implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 54463094978274055L;
    private ComuniAssociatiEsclusioniId id;
    private Comuniassociati comuniassociati;

    public ComuniAssociatiEsclusioni() {

	this.id = new ComuniAssociatiEsclusioniId();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codicecomune", column = @Column(name = "CODICECOMUNE", nullable = false, length = 5)),
	    @AttributeOverride(name = "software", column = @Column(name = "SOFTWARE", nullable = false, length = 2)) })
    public ComuniAssociatiEsclusioniId getId() {

	return this.id;
    }

    public void setId(ComuniAssociatiEsclusioniId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE", nullable = false, insertable = false, updatable = false) })
    public Comuniassociati getComuniassociati() {

	return comuniassociati;
    }

    public void setComuniassociati(Comuniassociati comuniassociati) {

	this.comuniassociati = comuniassociati;
    }
}
