package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MERCATI_D_DISABILITATI")
public class MercatiDDisabilitati implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2514396590059086406L;
    private PkId id;
    private Mercati mercato;
    private MercatiD posteggio;
    private Date dallaData;
    private Date allaData;
    private String note;

    public MercatiDDisabilitati() {

	this.id = new PkId();
	this.mercato = new Mercati();
	this.posteggio = new MercatiD();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATI_D_DISABILITATI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CODICEMERCATO", referencedColumnName = "CODICEMERCATO", nullable = false, insertable = false, updatable = false) })
    public Mercati getMercato() {

	return this.mercato;
    }

    public void setMercato(Mercati mercato) {

	this.mercato = mercato;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatoId;

    @Column(name = "FK_CODICEMERCATO")
    @SuppressWarnings("unused")
    private Integer getMercatoId() {

	if (this.getMercato() != null && this.getMercato().getId() != null) {
	    this.mercatoId = this.getMercato().getId().getCodice();
	    return this.mercatoId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatoId(Integer mercatoId) {

	if (this.getMercato() != null && this.getMercato().getId() != null) {
	    this.mercatoId = this.getMercato().getId().getCodice();
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDPOSTEGGIO", referencedColumnName = "IDPOSTEGGIO", insertable = false, updatable = false) })
    public MercatiD getPosteggio() {

	return this.posteggio;
    }

    public void setPosteggio(MercatiD posteggio) {

	this.posteggio = posteggio;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer posteggioId;

    @Column(name = "FK_IDPOSTEGGIO")
    @SuppressWarnings("unused")
    private Integer getPosteggioId() {

	if (this.getPosteggio() != null && this.getPosteggio().getId() != null) {
	    this.posteggioId = getPosteggio().getId().getCodice();
	    return this.posteggioId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setPosteggioId(Integer posteggioId) {

	if (this.getPosteggio() != null && this.getPosteggio().getId() != null) {
	    this.posteggioId = getPosteggio().getId().getCodice();
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "DALLA_DATA", length = 7)
    public Date getDallaData() {

	return dallaData;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "ALLA_DATA", length = 7)
    public Date getAllaData() {

	return allaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }

    @NotEmpty
    @Length(max = 4000)
    @Column(name = "NOTE", nullable = false, length = 4000)
    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
