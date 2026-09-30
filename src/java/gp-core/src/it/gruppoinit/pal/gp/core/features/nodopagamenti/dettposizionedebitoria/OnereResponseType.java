package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class OnereResponseType {

    @XmlElement(name = "raggruppamento")
    private String raggruppamento;
    @XmlElement(name = "causale")
    private String causale;
    @XmlElement(name = "importo")
    private BigDecimal importo;

    public OnereResponseType() {

	super();
    }

    public OnereResponseType(String raggruppamento, String causale, BigDecimal importo) {

	super();
	this.raggruppamento = raggruppamento;
	this.causale = causale;
	this.importo = importo;
    }

    @XmlTransient
    public String getRaggruppamento() {

	return raggruppamento;
    }

    @XmlTransient
    public String getCausale() {

	return causale;
    }

    @XmlTransient
    public BigDecimal getImporto() {

	return importo;
    }
}
