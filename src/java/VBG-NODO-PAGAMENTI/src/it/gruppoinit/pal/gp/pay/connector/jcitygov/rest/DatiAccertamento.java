package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DatiAccertamento {

    @XmlElement
    private String annoAccertamento;

    @XmlElement
    private String codiceAccertamento;

    @XmlElement
    private String descrizioneAccertamento;

    @XmlElement
    private BigDecimal importoAccertamento;

	public String getAnnoAccertamento() {
		return annoAccertamento;
	}

	public void setAnnoAccertamento(String annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}

	public String getCodiceAccertamento() {
		return codiceAccertamento;
	}

	public void setCodiceAccertamento(String codiceAccertamento) {
		this.codiceAccertamento = codiceAccertamento;
	}

	public String getDescrizioneAccertamento() {
		return descrizioneAccertamento;
	}

	public void setDescrizioneAccertamento(String descrizioneAccertamento) {
		this.descrizioneAccertamento = descrizioneAccertamento;
	}

	public BigDecimal getImportoAccertamento() {
		return importoAccertamento;
	}

	public void setImportoAccertamento(BigDecimal importoAccertamento) {
		this.importoAccertamento = importoAccertamento;
	}

    
}
