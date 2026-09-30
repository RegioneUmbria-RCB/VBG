package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AvvisoPagamentoResponse {

    @XmlElement
    private AvvisoPagamentoResultDto avvisoPagamentoResultDto;

    @XmlElement
    private String codIpaRichiedente;

    @XmlElement
    private String codiceServizio;

	public AvvisoPagamentoResultDto getAvvisoPagamentoResultDto() {
		return avvisoPagamentoResultDto;
	}

	public void setAvvisoPagamentoResultDto(AvvisoPagamentoResultDto avvisoPagamentoResultDto) {
		this.avvisoPagamentoResultDto = avvisoPagamentoResultDto;
	}

	public String getCodIpaRichiedente() {
		return codIpaRichiedente;
	}

	public void setCodIpaRichiedente(String codIpaRichiedente) {
		this.codIpaRichiedente = codIpaRichiedente;
	}

	public String getCodiceServizio() {
		return codiceServizio;
	}

	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}

    
}
