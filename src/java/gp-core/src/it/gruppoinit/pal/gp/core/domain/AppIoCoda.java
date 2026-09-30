package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

@Entity
@Table(name = "APP_IO_CODA")
public class AppIoCoda implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5985218616118118566L;
    private AppIoCodaId id;
    private String identificativoServizio;
    private String codiceFiscale;
    private String oggetto;
    private String stato;
    private String messaggio;
    private Date statoData;
    private String statoMessaggio;
    private Date dataPrevistaElaborazione;
    private Integer ordine;
    private AppIoServizi appIoServizi;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "guid", column = @Column(name = "GUID", nullable = false, length = 40)) })
    public AppIoCodaId getId() {

	return id;
    }

    public void setId(AppIoCodaId id) {

	this.id = id;
    }

    @Length(max = 50)
    @Column(name = "IDENTIFICATIVO_SERVIZIO")
    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }

    @Length(max = 32)
    @Column(name = "CODICEFISCALE")
    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    @Length(min = 10, max = 120)
    @Column(name = "OGGETTO")
    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    @Length(min = 80, max = 10000)
    @Column(name = "MESSAGGIO")
    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    @Length(max = 50)
    @Column(name = "STATO")
    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATO_DATA")
    public Date getStatoData() {

	return statoData;
    }

    public void setStatoData(Date statoData) {

	this.statoData = statoData;
    }

    @Length(max = 500)
    @Column(name = "STATO_MESSAGGIO")
    public String getStatoMessaggio() {

	return statoMessaggio;
    }

    public void setStatoMessaggio(String statoMessaggio) {

	this.statoMessaggio = statoMessaggio;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_PREVISTA_ELABORAZIONE")
    public Date getDataPrevistaElaborazione() {

	return dataPrevistaElaborazione;
    }

    public void setDataPrevistaElaborazione(Date dataPrevistaElaborazione) {

	this.dataPrevistaElaborazione = dataPrevistaElaborazione;
    }

    @Column(name = "ORDINE", precision = 10, scale = 0)
    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDENTIFICATIVO_SERVIZIO", referencedColumnName = "IDENTIFICATIVO_SERVIZIO", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public AppIoServizi getAppIoServizi() {

	return appIoServizi;
    }

    public void setAppIoServizi(AppIoServizi appIoServizi) {

	this.appIoServizi = appIoServizi;
    }
}
