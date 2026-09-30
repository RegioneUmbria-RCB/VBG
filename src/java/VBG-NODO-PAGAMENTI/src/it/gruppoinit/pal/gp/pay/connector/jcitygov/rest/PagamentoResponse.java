package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PagamentoResponse {

	@XmlElement
	private String codIpaRichiedente;
	@XmlElement
    private String codiceServizio;
	@XmlElement
    private Esito esitoDto;
	@XmlElement
    private String identTransazione;
	@XmlElement
    private String url;

    // Getters e Setters
    public String getCodIpaRichiedente() { return codIpaRichiedente; }
    public void setCodIpaRichiedente(String codIpaRichiedente) { this.codIpaRichiedente = codIpaRichiedente; }

    public String getCodiceServizio() { return codiceServizio; }
    public void setCodiceServizio(String codiceServizio) { this.codiceServizio = codiceServizio; }

    public Esito getEsitoDto() { return esitoDto; }
    public void setEsitoDto(Esito esitoDto) { this.esitoDto = esitoDto; }

    public String getIdentTransazione() { return identTransazione; }
    public void setIdentTransazione(String identTransazione) { this.identTransazione = identTransazione; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
	
}
