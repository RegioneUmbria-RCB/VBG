
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfPagamentoGiornaliero complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPagamentoGiornaliero"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PagamentoGiornaliero" type="{http://e-fil.eu/PnP/PlugAndPayDeliver}PagamentoGiornaliero" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPagamentoGiornaliero", propOrder = {
    "pagamentoGiornaliero"
})
public class ArrayOfPagamentoGiornaliero {

    @XmlElement(name = "PagamentoGiornaliero", nillable = true)
    protected List<PagamentoGiornaliero> pagamentoGiornaliero;

    /**
     * Gets the value of the pagamentoGiornaliero property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagamentoGiornaliero property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagamentoGiornaliero().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PagamentoGiornaliero }
     * 
     * 
     */
    public List<PagamentoGiornaliero> getPagamentoGiornaliero() {
        if (pagamentoGiornaliero == null) {
            pagamentoGiornaliero = new ArrayList<PagamentoGiornaliero>();
        }
        return this.pagamentoGiornaliero;
    }

}
