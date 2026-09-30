package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@XmlRootElement(name = "mercatiposteggio")
public class AggiornaPosteggioRequest extends AbstractRequest {

    @XmlElement(name = "codice_posteggio")
    private String codicePosteggio;
    @XmlElement(name = "disabilitato")
    private boolean disabilitato;
    @XmlElement(name = "temporaneo")
    private boolean temporaneo;
    @XmlElement(name = "larghezza")
    private BigDecimal larghezza;
    @XmlElement(name = "lunghezza")
    private BigDecimal lunghezza;
    @XmlElement(name = "note")
    private String note;
    @XmlElement(name = "codice_stradario")
    private Integer codiceStradario;
    @XmlElement(name = "superficie_complessiva")
    private BigDecimal superficieComplessiva;
    @XmlElement(name = "codice_tipo_spazio")
    private Integer codiceTipoSpazio;
    
    public String getCodicePosteggio() {

	return codicePosteggio;
    }

    public void setCodicePosteggio(String codicePosteggio) {

	this.codicePosteggio = codicePosteggio;
    }

    public boolean isDisabilitato() {

	return disabilitato;
    }

    public void setDisabilitato(boolean disabilitato) {

	this.disabilitato = disabilitato;
    }

    public boolean isTemporaneo() {

	return temporaneo;
    }

    public void setTemporaneo(boolean temporaneo) {

	this.temporaneo = temporaneo;
    }

    public BigDecimal getLarghezza() {

	return larghezza;
    }

    public void setLarghezza(BigDecimal larghezza) {

	this.larghezza = larghezza;
    }

    public BigDecimal getLunghezza() {

	return lunghezza;
    }

    public void setLunghezza(BigDecimal lunghezza) {

	this.lunghezza = lunghezza;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Integer getCodiceStradario() {

	return codiceStradario;
    }

    public void setCodiceStradario(Integer codiceStradario) {

	this.codiceStradario = codiceStradario;
    }

    public BigDecimal getSuperficieComplessiva() {

	return superficieComplessiva;
    }

    public void setSuperficieComplessiva(BigDecimal superficieComplessiva) {

	this.superficieComplessiva = superficieComplessiva;
    }
    
    public Integer getCodiceTipoSpazio() {

	return codiceTipoSpazio;
    }
    
    public void setCodiceTipoSpazio(Integer codiceTipoSpazio) {

	this.codiceTipoSpazio = codiceTipoSpazio;
    }

    @Override
    public void valida() throws BusinessValidationException {

	if (StringUtils.isBlank(this.codicePosteggio)) {
	    throw new BusinessValidationException("La proprietà codice_posteggio non è valorizzata");
	}
	if (larghezza != null && BigDecimal.ZERO.compareTo(larghezza) > 0) {
	    throw new BusinessValidationException("La proprietà larghezza non è valorizzata correttamente");
	}
	if (lunghezza != null && BigDecimal.ZERO.compareTo(lunghezza) > 0) {
	    throw new BusinessValidationException("La proprietà lunghezza non è valorizzata correttamente");
	}
	if (superficieComplessiva != null && BigDecimal.ZERO.compareTo(superficieComplessiva) > 0) {
	    throw new BusinessValidationException("La proprietà superficie_complessiva non è valorizzata correttamente");
	}
    }
}
