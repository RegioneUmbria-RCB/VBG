
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfEsitoInvioCarrelloPosizioni complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfEsitoInvioCarrelloPosizioni"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoInvioCarrelloPosizioni" type="{http://e-fil.eu/PnP/PlugAndPayPayment}EsitoInvioCarrelloPosizioni" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfEsitoInvioCarrelloPosizioni", propOrder = {
    "esitoInvioCarrelloPosizioni"
})
public class ArrayOfEsitoInvioCarrelloPosizioni {

    @XmlElement(name = "EsitoInvioCarrelloPosizioni", nillable = true)
    protected List<EsitoInvioCarrelloPosizioni> esitoInvioCarrelloPosizioni;

    /**
     * Gets the value of the esitoInvioCarrelloPosizioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esitoInvioCarrelloPosizioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsitoInvioCarrelloPosizioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsitoInvioCarrelloPosizioni }
     * 
     * 
     */
    public List<EsitoInvioCarrelloPosizioni> getEsitoInvioCarrelloPosizioni() {
        if (esitoInvioCarrelloPosizioni == null) {
            esitoInvioCarrelloPosizioni = new ArrayList<EsitoInvioCarrelloPosizioni>();
        }
        return this.esitoInvioCarrelloPosizioni;
    }

}
