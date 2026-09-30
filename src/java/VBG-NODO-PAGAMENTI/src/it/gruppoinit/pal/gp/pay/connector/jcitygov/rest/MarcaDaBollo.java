package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MarcaDaBollo {

    @XmlElement
    private String hashDocumento;

    @XmlElement
    private String provinciaResidenzaSoggettoPagatore;

    @XmlElement
    private String tipologia;

	public String getHashDocumento() {
		return hashDocumento;
	}

	public void setHashDocumento(String hashDocumento) {
		this.hashDocumento = hashDocumento;
	}

	public String getProvinciaResidenzaSoggettoPagatore() {
		return provinciaResidenzaSoggettoPagatore;
	}

	public void setProvinciaResidenzaSoggettoPagatore(String provinciaResidenzaSoggettoPagatore) {
		this.provinciaResidenzaSoggettoPagatore = provinciaResidenzaSoggettoPagatore;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

    
}
