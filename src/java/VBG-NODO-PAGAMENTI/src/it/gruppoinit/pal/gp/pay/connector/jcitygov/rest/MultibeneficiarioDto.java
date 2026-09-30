package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class MultibeneficiarioDto {

    @XmlElement
    private BigDecimal importoTotale;

    @XmlElement
    private Boolean multibeneficiario;

    @XmlElement
    private List<InfoMultibeneficiarioDto> infoMultibeneficiario;

    
    public BigDecimal getImportoTotale() {
    
        return importoTotale;
    }

    
    public void setImportoTotale(BigDecimal importoTotale) {
    
        this.importoTotale = importoTotale;
    }

    
    public Boolean getMultibeneficiario() {
    
        return multibeneficiario;
    }

    
    public void setMultibeneficiario(Boolean multibeneficiario) {
    
        this.multibeneficiario = multibeneficiario;
    }

    
    public List<InfoMultibeneficiarioDto> getInfoMultibeneficiario() {
    
        return infoMultibeneficiario;
    }

    
    public void setInfoMultibeneficiario(List<InfoMultibeneficiarioDto> infoMultibeneficiario) {
    
        this.infoMultibeneficiario = infoMultibeneficiario;
    }

    
}
