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

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MERCATI_AUD_PRESENZE")
public class MercatiAudPresenze {
    
    
    private PkId id;
    private Responsabili responsabile;
    private Autorizzazioni autorizzazioneSorgente;
    private Autorizzazioni autorizzazioneDestinazione;
    private Date dataOperazione;
    private String note;
    private Integer totPresenze;
    
    
    private Integer codiceOperatore;
    private Integer fkIdAutSorgente;
    private Integer fkIdAutDest;
    

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATI_AUD_PRESENZE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {
        return id;
    }

    public void setId(PkId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
        @JoinColumn(name = "CODICEOPERATORE", referencedColumnName = "CODICERESPONSABILE", insertable = false, updatable = false)
    })
    public Responsabili getResponsabile() {
    
        return responsabile;
    }

    
    public void setResponsabile(Responsabili responsabile) {
    
        this.responsabile = responsabile;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
        @JoinColumn(name = "FK_ID_AUT_SORGENTE", referencedColumnName = "ID", insertable = false, updatable = false)
    })
    public Autorizzazioni getAutorizzazioneSorgente() {
    
        return autorizzazioneSorgente;
    }

    
    public void setAutorizzazioneSorgente(Autorizzazioni autorizzazioneSorgente) {
    
        this.autorizzazioneSorgente = autorizzazioneSorgente;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
        @JoinColumn(name = "FK_ID_AUT_DEST", referencedColumnName = "ID", insertable = false, updatable = false)
    })
    public Autorizzazioni getAutorizzazioneDestinazione() {
    
        return autorizzazioneDestinazione;
    }

    
    public void setAutorizzazioneDestinazione(Autorizzazioni autorizzazioneDestinazione) {
    
        this.autorizzazioneDestinazione = autorizzazioneDestinazione;
    }

    @Column(name = "DATA_OPERAZIONE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDataOperazione() {
    
        return dataOperazione;
    }

    public void setDataOperazione(Date dataOperazione) {
    
        this.dataOperazione = dataOperazione;
    }

    @Column(name = "NOTE", length = 4000)
    public String getNote() {
    
        return note;
    }

    
    public void setNote(String note) {
    
        this.note = note;
    }

    @Column(name = "TOT_PRESENZE", nullable = false, precision = 6, scale = 0)
    public Integer getTotPresenze() {
    
        return totPresenze;
    }

    
    public void setTotPresenze(Integer totPresenze) {
    
        this.totPresenze = totPresenze;
    }

    @Column(name = "CODICEOPERATORE")
    public Integer getCodiceOperatore() {
    
        return codiceOperatore;
    }

    
    public void setCodiceOperatore(Integer codiceOperatore) {
    
        this.codiceOperatore = codiceOperatore;
    }

    @Column(name = "FK_ID_AUT_SORGENTE")
    public Integer getFkIdAutSorgente() {
    
        return fkIdAutSorgente;
    }

    
    public void setFkIdAutSorgente(Integer fkIdAutSorgente) {
    
        this.fkIdAutSorgente = fkIdAutSorgente;
    }

    @Column(name = "FK_ID_AUT_DEST")
    public Integer getFkIdAutDest() {
    
        return fkIdAutDest;
    }

    
    public void setFkIdAutDest(Integer fkIdAutDest) {
    
        this.fkIdAutDest = fkIdAutDest;
    }

    
}
