package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RichiestaInfoPagamentoDebitoDto {

    @XmlElement(name="chiaveDebitoDto")
    private ChiaveDebitoDto chiaveDebito;

    @XmlElement
    private String codIpaRichiedente;

    @XmlElement
    private String codiceServizio;

    // getter e setter
    public ChiaveDebitoDto getChiaveDebito() { return chiaveDebito; }
    public void setChiaveDebito(ChiaveDebitoDto chiaveDebito) { this.chiaveDebito = chiaveDebito; }

    public String getCodIpaRichiedente() { return codIpaRichiedente; }
    public void setCodIpaRichiedente(String codIpaRichiedente) { this.codIpaRichiedente = codIpaRichiedente; }

    public String getCodiceServizio() { return codiceServizio; }
    public void setCodiceServizio(String codiceServizio) { this.codiceServizio = codiceServizio; }
}
