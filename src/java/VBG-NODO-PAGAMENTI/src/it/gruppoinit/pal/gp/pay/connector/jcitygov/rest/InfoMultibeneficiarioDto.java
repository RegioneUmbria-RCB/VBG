package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class InfoMultibeneficiarioDto {

    @XmlElement
    private ChiaveDebitoDto chiaviDebito;

    @XmlElement
    private String codiceIpa;

    @XmlElement
    private BigDecimal importo;

    // getters/setters
    public ChiaveDebitoDto getChiaviDebito() {
        return chiaviDebito;
    }

    public void setChiaviDebito(ChiaveDebitoDto chiaviDebito) {
        this.chiaviDebito = chiaviDebito;
    }

    public String getCodiceIpa() {
        return codiceIpa;
    }

    public void setCodiceIpa(String codiceIpa) {
        this.codiceIpa = codiceIpa;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }
}

