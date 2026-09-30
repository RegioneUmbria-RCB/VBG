
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RispostaRicercaPosizioniPerCodiceFiscalePartitaIva complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RispostaRicercaPosizioniPerCodiceFiscalePartitaIva"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Posizioni" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}ArrayOfPosizione" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaRicercaPosizioniPerCodiceFiscalePartitaIva", propOrder = {
    "posizioni"
})
public class RispostaRicercaPosizioniPerCodiceFiscalePartitaIva {

    @XmlElementRef(name = "Posizioni", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfPosizione> posizioni;

    /**
     * Recupera il valore della proprietà posizioni.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code >}
     *     
     */
    public JAXBElement<ArrayOfPosizione> getPosizioni() {
        return posizioni;
    }

    /**
     * Imposta il valore della proprietà posizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code >}
     *     
     */
    public void setPosizioni(JAXBElement<ArrayOfPosizione> value) {
        this.posizioni = value;
    }

}
