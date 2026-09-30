package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class DettaglioImportoDto {

	@XmlElement
	private String anno;
	@XmlElement
    private String capitoloBilancio;
	@XmlElement
    private String codice;
	@XmlElement
    private String descrizione;
	@XmlElement
    private BigDecimal importo;
	
    public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getCapitoloBilancio() {
		return capitoloBilancio;
	}
	public void setCapitoloBilancio(String capitoloBilancio) {
		this.capitoloBilancio = capitoloBilancio;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
     	
}
