package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "codiceUtente", "codiceEnte", "tipoUfficio", "codiceUfficio", "tipologiaServizio", "numeroOperazione", "numeroDocumento",
	"annoDocumento", "valuta", "importo", "datiSpecifici", "codiceTassonomia" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceData {

    @XmlElement(required = true, name = "CodiceUtente")
    private String codiceUtente;
    @XmlElement(required = true, name = "CodiceEnte")
    private String codiceEnte;
    @XmlElement(required = true, name = "TipoUfficio")
    private String tipoUfficio;
    @XmlElement(required = true, name = "CodiceUfficio")
    private String codiceUfficio;
    @XmlElement(required = true, name = "TipologiaServizio")
    private String tipologiaServizio;
    @XmlElement(required = true, name = "NumeroOperazione")
    private String numeroOperazione;
    @XmlElement(required = true, name = "NumeroDocumento")
    private String numeroDocumento;
    @XmlElement(name = "AnnoDocumento")
    private Integer annoDocumento;
    @XmlElement(name = "Valuta")
    private String valuta;
    @XmlElement(required = true, name = "Importo")
    private Integer importo;
    @XmlElement(name = "DatiSpecifici")
    private String datiSpecifici;
    @XmlElement(name = "CodiceTassonomia")
    private String codiceTassonomia;

    public String getCodiceUtente() {

	return codiceUtente;
    }

    public void setCodiceUtente(String codiceUtente) {

	this.codiceUtente = codiceUtente;
    }

    public String getCodiceEnte() {

	return codiceEnte;
    }

    public void setCodiceEnte(String codiceEnte) {

	this.codiceEnte = codiceEnte;
    }

    public String getTipoUfficio() {

	return tipoUfficio;
    }

    public void setTipoUfficio(String tipoUfficio) {

	this.tipoUfficio = tipoUfficio;
    }

    public String getCodiceUfficio() {

	return codiceUfficio;
    }

    public void setCodiceUfficio(String codiceUfficio) {

	this.codiceUfficio = codiceUfficio;
    }

    public String getTipologiaServizio() {

	return tipologiaServizio;
    }

    public void setTipologiaServizio(String tipologiaServizio) {

	this.tipologiaServizio = tipologiaServizio;
    }

    public String getNumeroOperazione() {

	return numeroOperazione;
    }

    public void setNumeroOperazione(String numeroOperazione) {

	this.numeroOperazione = numeroOperazione;
    }

    public String getNumeroDocumento() {

	return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {

	this.numeroDocumento = numeroDocumento;
    }

    public Integer getAnnoDocumento() {

	return annoDocumento;
    }

    public void setAnnoDocumento(Integer annoDocumento) {

	this.annoDocumento = annoDocumento;
    }

    public String getValuta() {

	return valuta;
    }

    public void setValuta(String valuta) {

	this.valuta = valuta;
    }

    public Integer getImporto() {

	return importo;
    }

    public void setImporto(Integer importo) {

	this.importo = importo;
    }

    public String getDatiSpecifici() {

	return datiSpecifici;
    }

    public void setDatiSpecifici(String datiSpecifici) {

	this.datiSpecifici = datiSpecifici;
    }

    public String getCodiceTassonomia() {

	return codiceTassonomia;
    }

    public void setCodiceTassonomia(String codiceTassonomia) {

	this.codiceTassonomia = codiceTassonomia;
    }
}
