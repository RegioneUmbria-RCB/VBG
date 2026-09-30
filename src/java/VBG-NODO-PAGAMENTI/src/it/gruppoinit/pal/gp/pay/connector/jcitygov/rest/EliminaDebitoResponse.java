package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EliminaDebitoResponse {

	@XmlElement
	private String codIpaRichiedente;
	@XmlElement
	private String codiceServizio;
	@XmlElement
	private NumeroAvviso numeroAvvisoDto;
	
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
	public NumeroAvviso getNumeroAvvisoDto() {
		return numeroAvvisoDto;
	}
	public void setNumeroAvvisoDto(NumeroAvviso numeroAvvisoDto) {
		this.numeroAvvisoDto = numeroAvvisoDto;
	}
		
}
