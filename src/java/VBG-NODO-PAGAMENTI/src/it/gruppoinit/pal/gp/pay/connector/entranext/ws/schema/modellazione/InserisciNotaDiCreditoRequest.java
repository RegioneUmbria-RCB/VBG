
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciNotaDiCreditoRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciNotaDiCreditoRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NotaDiCredito" type="{http://entranext.it/}PosizioniNoteDiCredito" minOccurs="0"/&gt;
 *         &lt;element name="ID_RUOLO" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciNotaDiCreditoRequest", propOrder = {
    "notaDiCredito",
    "idruolo"
})
public class InserisciNotaDiCreditoRequest
    extends LinkNextRequest
{

    @XmlElement(name = "NotaDiCredito")
    protected PosizioniNoteDiCredito notaDiCredito;
    @XmlElementRef(name = "ID_RUOLO", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idruolo;

    /**
     * Recupera il valore della proprietà notaDiCredito.
     * 
     * @return
     *     possible object is
     *     {@link PosizioniNoteDiCredito }
     *     
     */
    public PosizioniNoteDiCredito getNotaDiCredito() {
        return notaDiCredito;
    }

    /**
     * Imposta il valore della proprietà notaDiCredito.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioniNoteDiCredito }
     *     
     */
    public void setNotaDiCredito(PosizioniNoteDiCredito value) {
        this.notaDiCredito = value;
    }

    /**
     * Recupera il valore della proprietà idruolo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIDRUOLO() {
        return idruolo;
    }

    /**
     * Imposta il valore della proprietà idruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIDRUOLO(JAXBElement<Integer> value) {
        this.idruolo = value;
    }

}
