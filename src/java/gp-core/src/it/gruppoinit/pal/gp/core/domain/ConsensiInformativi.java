package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.io.Serializable;
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

@Entity
@Table(name = "CONSENSI_INFORMATIVI")
public class ConsensiInformativi implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5686275153600791354L;
    private PkId id;
    private String descrizione;
    private Integer versione;
    private String contesto;
    private Integer ordine;
    private Boolean obbligatorio;
    private Date dataInserimento;
    private Oggetti oggetto;

    public ConsensiInformativi() {

    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "CONSENSI_INFORMATIVI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "DESCRIZIONE", nullable = false, length = 100)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Column(name = "VERSIONE", precision = 4, scale = 0)
    public Integer getVersione() {

	return versione;
    }

    public void setVersione(Integer versione) {

	this.versione = versione;
    }

    @Column(name = "CONTESTO", nullable = false, length = 50)
    public String getContesto() {

	return contesto;
    }

    public void setContesto(String contesto) {

	this.contesto = contesto;
    }

    @Column(name = "ORDINE", precision = 4, scale = 0)
    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    @Column(name = "OBBLIGATORIO")
    public Boolean getObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(Boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_INSERIMENTO", length = 7)
    public Date getDataInserimento() {

	return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {

	this.dataInserimento = dataInserimento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEOGGETTO", referencedColumnName = "CODICEOGGETTO", nullable = false, insertable = false, updatable = false) })
    public Oggetti getOggetto() {

	return this.oggetto;
    }

    public void setOggetto(Oggetti oggetto) {

	this.oggetto = oggetto;
    }

    // FIX WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer oggettoId;

    @Column(name = "CODICEOGGETTO")
    @SuppressWarnings("unused")
    private Integer getOggettoId() {

	if (null != this.getOggetto()) {
	    if (null != this.getOggetto().getId()) {
		this.oggettoId = getOggetto().getId().getCodice();
		return this.oggettoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setOggettoId(Integer oggettoId) {

	if (null != this.getOggetto()) {
	    if (null != this.getOggetto().getId()) {
		this.oggettoId = getOggetto().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////
}
