package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.io.Serializable;

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "FO_DOMRICH_ALLEGATI")
public class FoDomrichAllegati implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1149647897707174659L;
    private PkId id;
    private FoDomRichieste foDomRichieste;
    private Oggetti oggetti;
    private String modulo;
    private String attore;
    private Boolean firmato;
    private String link;
    private FoDomrichAllegati allegatoRichiesto;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "FO_DOMRICH_ALLEGATI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_FODOMRICHIESTE", referencedColumnName = "ID", insertable = false, updatable = false) })
    public FoDomRichieste getFoDomRichieste() {

	return foDomRichieste;
    }

    public void setFoDomRichieste(FoDomRichieste foDomRichieste) {

	this.foDomRichieste = foDomRichieste;
    }

    private Integer foDomRichiesteId;

    @Column(name = "FKID_FODOMRICHIESTE")
    @SuppressWarnings("unused")
    private Integer getFoDomRichiesteId() {

	if (null != this.getFoDomRichieste()) {
	    if (null != this.getFoDomRichieste().getId()) {
		this.foDomRichiesteId = getFoDomRichieste().getId().getCodice();
		return this.foDomRichiesteId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setFoDomRichiesteId(Integer foDomRichiesteId) {

	if (null != this.getFoDomRichieste()) {
	    if (null != this.getFoDomRichieste().getId()) {
		this.foDomRichiesteId = getFoDomRichieste().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEOGGETTO", referencedColumnName = "CODICEOGGETTO", nullable = false, insertable = false, updatable = false) })
    public Oggetti getOggetti() {

	return this.oggetti;
    }

    public void setOggetti(Oggetti oggetti) {

	this.oggetti = oggetti;
    }

    private Integer oggettiId;

    @Column(name = "CODICEOGGETTO")
    @SuppressWarnings("unused")
    private Integer getOggettiId() {

	if (null != this.getOggetti()) {
	    if (null != this.getOggetti().getId()) {
		this.oggettiId = getOggetti().getId().getCodice();
		return this.oggettiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setOggettiId(Integer oggettiId) {

	if (null != this.getOggetti()) {
	    if (null != this.getOggetti().getId()) {
		this.oggettiId = getOggetti().getId().getCodice();
	    }
	}
    }

    @Column(name = "MODULO", length = 100)
    public String getModulo() {

	return modulo;
    }

    public void setModulo(String modulo) {

	this.modulo = modulo;
    }

    @Column(name = "ATTORE", length = 500)
    public String getAttore() {

	return attore;
    }

    public void setAttore(String attore) {

	this.attore = attore;
    }

    @Column(name = "FIRMATO", precision = 1, scale = 0)
    public Boolean getFirmato() {

	return firmato;
    }

    public void setFirmato(Boolean firmato) {

	this.firmato = firmato;
    }

    @Column(name = "LINK", length = 1500)
    public String getLink() {

	return link;
    }

    public void setLink(String link) {

	this.link = link;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_FODOMRICHALL_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public FoDomrichAllegati getAllegatoRichiesto() {

	return allegatoRichiesto;
    }

    public void setAllegatoRichiesto(FoDomrichAllegati allegatoRichiesto) {

	this.allegatoRichiesto = allegatoRichiesto;
    }

    private Integer allegatoRichiestoId;

    @Column(name = "FK_FODOMRICHALL_ID")
    @SuppressWarnings("unused")
    private Integer getAllegatoRichiestoId() {

	if (null != this.getAllegatoRichiesto()) {
	    if (null != this.getAllegatoRichiesto().getId()) {
		this.allegatoRichiestoId = getAllegatoRichiesto().getId().getCodice();
		return this.allegatoRichiestoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAllegatoRichiestoId(Integer allegatoRichiestoId) {

	if (null != this.getAllegatoRichiesto()) {
	    if (null != this.getAllegatoRichiesto().getId()) {
		this.allegatoRichiestoId = getAllegatoRichiesto().getId().getCodice();
	    }
	}
    }
}
