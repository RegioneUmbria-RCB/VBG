//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChiaveDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito"/>
 *         &lt;sequence>
 *           &lt;element name="InfoPagamentoTelematico" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctInfoPagamentoTelematico" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "chiaveDebito",
    "infoPagamentoTelematico"
})
@XmlRootElement(name = "RispostaInfoPagamentoDebito")
public class RispostaInfoPagamentoDebito {

    @XmlElement(name = "ChiaveDebito", required = true)
    protected CtChiaveDebito chiaveDebito;
    @XmlElement(name = "InfoPagamentoTelematico")
    protected List<CtInfoPagamentoTelematico> infoPagamentoTelematico;

    /**
     * Recupera il valore della proprietà chiaveDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtChiaveDebito }
     *     
     */
    public CtChiaveDebito getChiaveDebito() {
        return chiaveDebito;
    }

    /**
     * Imposta il valore della proprietà chiaveDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtChiaveDebito }
     *     
     */
    public void setChiaveDebito(CtChiaveDebito value) {
        this.chiaveDebito = value;
    }

    /**
     * Gets the value of the infoPagamentoTelematico property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoPagamentoTelematico property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoPagamentoTelematico().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtInfoPagamentoTelematico }
     * 
     * 
     */
    public List<CtInfoPagamentoTelematico> getInfoPagamentoTelematico() {
        if (infoPagamentoTelematico == null) {
            infoPagamentoTelematico = new ArrayList<CtInfoPagamentoTelematico>();
        }
        return this.infoPagamentoTelematico;
    }

}
