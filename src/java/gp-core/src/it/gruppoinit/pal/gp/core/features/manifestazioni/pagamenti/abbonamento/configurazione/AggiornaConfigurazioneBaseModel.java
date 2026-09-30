package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.math.BigDecimal;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AggiornaConfigurazioneBaseModel {

    @XmlElement(name = "messaggio")
    private String messaggio;
    @XmlElement(name = "attivofo")
    private boolean attivoFo;
    @XmlElement(name = "tipo")
    private String tipo;
    @XmlElement(name = "destinatari")
    private String destinatari;
    @XmlElement(name = "importomassimo")
    private BigDecimal importomassimo;

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    public boolean isAttivoFo() {

	return attivoFo;
    }

    public void setAttivoFo(boolean attivoFo) {

	this.attivoFo = attivoFo;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }
        
    public String getDestinatari() {
    
        return destinatari;
    }
    
    public void setDestinatari(String destinatari) {
    
        this.destinatari = destinatari;
    }

    public BigDecimal getImportomassimo() {
    
        return importomassimo;
    }

    public void setImportomassimo(BigDecimal importomassimo) {
    
        this.importomassimo = importomassimo;
    }

    public BorsellinoConfigurazione toBorsellinoConfigurazione() {

	BorsellinoConfigurazione cfg = new BorsellinoConfigurazione();
	cfg.setMsgNodoPagNonDisp(this.getMessaggio());
	cfg.setGestioneFO(this.isAttivoFo());
	cfg.setTipoInstallazione(this.getTipo());
	cfg.setAttivaPagamenti(this.getDestinatari());
	cfg.setImportomassimo(this.importomassimo);
	return cfg;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
