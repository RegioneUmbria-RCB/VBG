package it.gov.pagopa.rendicontazione;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author riccardob
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "versioneOggetto" //
	, "identificativoFlusso" //
	, "dataOraFlusso"//
	, "identificativoUnivocoRegolamento"//
	, "dataRegolamento"//
	, "istitutoMittente"//
	, "codiceBicBancaDiRiversamento" //
	, "istitutoRicevente"//
	, "numeroTotalePagamenti"//
	, "importoTotalePagamenti"//
	, "datiSingoliPagamenti"//
})
@XmlRootElement(name = "FlussoRiversamento", namespace = "http://www.digitpa.gov.it/schemas/2011/Pagamenti/")
public class FlussoRiversamento {

    @XmlElement
    private String versioneOggetto;
    @XmlElement
    private String identificativoFlusso;
    @XmlElement
    private Date dataOraFlusso;
    @XmlElement
    private String identificativoUnivocoRegolamento;
    @XmlElement
    private String dataRegolamento;
    @XmlElement
    private IstitutoMittente istitutoMittente;
    @XmlElement
    private String codiceBicBancaDiRiversamento;
    @XmlElement
    private IstitutoRicevente istitutoRicevente;
    @XmlElement
    private Integer numeroTotalePagamenti;
    @XmlElement
    private Double importoTotalePagamenti;
    @XmlElement
    private List<DatiSingoloPagamento> datiSingoliPagamenti;

    public String getVersioneOggetto() {

	return versioneOggetto;
    }

    public void setVersioneOggetto(String versioneOggetto) {

	this.versioneOggetto = versioneOggetto;
    }

    public String getIdentificativoFlusso() {

	return identificativoFlusso;
    }

    public void setIdentificativoFlusso(String identificativoFlusso) {

	this.identificativoFlusso = identificativoFlusso;
    }

    public Date getDataOraFlusso() {

	return dataOraFlusso;
    }

    public void setDataOraFlusso(Date dataOraFlusso) {

	this.dataOraFlusso = dataOraFlusso;
    }

    public String getIdentificativoUnivocoRegolamento() {

	return identificativoUnivocoRegolamento;
    }

    public void setIdentificativoUnivocoRegolamento(String identificativoUnivocoRegolamento) {

	this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
    }

    public String getDataRegolamento() {

	return dataRegolamento;
    }

    public void setDataRegolamento(String dataRegolamento) {

	this.dataRegolamento = dataRegolamento;
    }

    public IstitutoMittente getIstitutoMittente() {

	return istitutoMittente;
    }

    public void setIstitutoMittente(IstitutoMittente istitutoMittente) {

	this.istitutoMittente = istitutoMittente;
    }

    public IstitutoRicevente getIstitutoRicevente() {

	return istitutoRicevente;
    }

    public void setIstitutoRicevente(IstitutoRicevente istitutoRicevente) {

	this.istitutoRicevente = istitutoRicevente;
    }

    public Integer getNumeroTotalePagamenti() {

	return numeroTotalePagamenti;
    }

    public void setNumeroTotalePagamenti(Integer numeroTotalePagamenti) {

	this.numeroTotalePagamenti = numeroTotalePagamenti;
    }

    public Double getImportoTotalePagamenti() {

	return importoTotalePagamenti;
    }

    public void setImportoTotalePagamenti(Double importoTotalePagamenti) {

	this.importoTotalePagamenti = importoTotalePagamenti;
    }

    public List<DatiSingoloPagamento> getDatiSingoliPagamenti() {

	if (this.datiSingoliPagamenti == null) {
	    this.datiSingoliPagamenti = new ArrayList<>();
	}
	return datiSingoliPagamenti;
    }

    public void setDatiSingoliPagamenti(List<DatiSingoloPagamento> datiSingoliPagamenti) {

	this.datiSingoliPagamenti = datiSingoliPagamenti;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
