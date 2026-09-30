package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generazioneavvisi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per ElencoIdentificativiType complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ElencoIdentificativiType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ElencoIdentificativiType", propOrder = { "identificativoUnivocoVersamento" })
public class ElencoIdentificativiType {

    @XmlElement(name = "IdentificativoUnivocoVersamento", required = true)
    protected List<String> identificativoUnivocoVersamento;

    /**
     * Gets the value of the identificativoUnivocoVersamento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the identificativoUnivocoVersamento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getIdentificativoUnivocoVersamento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getIdentificativoUnivocoVersamento() {

	if (identificativoUnivocoVersamento == null) {
	    identificativoUnivocoVersamento = new ArrayList<String>();
	}
	return this.identificativoUnivocoVersamento;
    }
}
