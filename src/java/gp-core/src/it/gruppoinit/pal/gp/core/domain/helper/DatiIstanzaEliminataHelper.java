package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiIstanzaEliminataHelper", propOrder = { "codicecomune", "comune", "numeroIstanza", "codiceistanza", "codicePraticaTelematica",
	"operatore", "codiceOperatore", "richiedente", "codiceRichiedente", "tipoSoggetto", "titolareLegale", "codiceTitolatelegale",
	"professionista", "codiceProfessionista", "intervento", "codiceintervento", "data", "numeroProtocollo", "dataProtocollo", "lavori",
	"operatoreGestioneCancellazione" })
public class DatiIstanzaEliminataHelper {

    public String getOperatoreGestioneCancellazione() {

	return operatoreGestioneCancellazione;
    }

    public void setOperatoreGestioneCancellazione(String operatoreGestioneCancellazione) {

	this.operatoreGestioneCancellazione = operatoreGestioneCancellazione;
    }

    @XmlElement(name = "codicecomune")
    private String codicecomune;
    @XmlElement(name = "comune")
    private String comune;
    @XmlElement(name = "numeroIstanza")
    private String numeroIstanza;
    @XmlElement(name = "codiceistanza")
    private Integer codiceistanza;
    @XmlElement(name = "codicePraticaTelematica")
    private String codicePraticaTelematica;
    @XmlElement(name = "operatore")
    private String operatore;
    @XmlElement(name = "codiceOperatore")
    private Integer codiceOperatore;
    @XmlElement(name = "richiedente")
    private String richiedente;
    @XmlElement(name = "codiceRichiedente")
    private Integer codiceRichiedente;
    @XmlElement(name = "tipoSoggetto")
    private String tipoSoggetto;
    @XmlElement(name = "titolareLegale")
    private String titolareLegale;
    @XmlElement(name = "codiceTitolatelegale")
    private Integer codiceTitolatelegale;
    @XmlElement(name = "professionista")
    private String professionista;
    @XmlElement(name = "codiceProfessionista")
    private Integer codiceProfessionista;
    @XmlElement(name = "intervento")
    private String intervento;
    @XmlElement(name = "codiceintervento")
    private Integer codiceintervento;
    @XmlElement(name = "data")
    private Date data;
    @XmlElement(name = "numeroProtocollo")
    private String numeroProtocollo;
    @XmlElement(name = "dataProtocollo")
    private Date dataProtocollo;
    @XmlElement(name = "lavori")
    private String lavori;
    @XmlElement(name = "operatoreGestioneCancellazione")
    private String operatoreGestioneCancellazione;

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getCodicePraticaTelematica() {

	return codicePraticaTelematica;
    }

    public void setCodicePraticaTelematica(String codicePraticaTelematica) {

	this.codicePraticaTelematica = codicePraticaTelematica;
    }

    public String getOperatore() {

	return operatore;
    }

    public void setOperatore(String operatore) {

	this.operatore = operatore;
    }

    public Integer getCodiceOperatore() {

	return codiceOperatore;
    }

    public void setCodiceOperatore(Integer codiceOperatore) {

	this.codiceOperatore = codiceOperatore;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    public Integer getCodiceRichiedente() {

	return codiceRichiedente;
    }

    public void setCodiceRichiedente(Integer codiceRichiedente) {

	this.codiceRichiedente = codiceRichiedente;
    }

    public String getTipoSoggetto() {

	return tipoSoggetto;
    }

    public void setTipoSoggetto(String tipoSoggetto) {

	this.tipoSoggetto = tipoSoggetto;
    }

    public String getTitolareLegale() {

	return titolareLegale;
    }

    public void setTitolareLegale(String titolareLegale) {

	this.titolareLegale = titolareLegale;
    }

    public Integer getCodiceTitolatelegale() {

	return codiceTitolatelegale;
    }

    public void setCodiceTitolatelegale(Integer codiceTitolatelegale) {

	this.codiceTitolatelegale = codiceTitolatelegale;
    }

    public String getProfessionista() {

	return professionista;
    }

    public void setProfessionista(String professionista) {

	this.professionista = professionista;
    }

    public Integer getCodiceProfessionista() {

	return codiceProfessionista;
    }

    public void setCodiceProfessionista(Integer codiceProfessionista) {

	this.codiceProfessionista = codiceProfessionista;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    public Integer getCodiceintervento() {

	return codiceintervento;
    }

    public void setCodiceintervento(Integer codiceintervento) {

	this.codiceintervento = codiceintervento;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public String getLavori() {

	return lavori;
    }

    public void setLavori(String lavori) {

	this.lavori = lavori;
    }
}
