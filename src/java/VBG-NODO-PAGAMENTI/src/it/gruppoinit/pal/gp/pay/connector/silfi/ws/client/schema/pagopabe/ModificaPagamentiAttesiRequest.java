
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="datiPagamentoAttesoWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}datiModificaPagamentoAttesoWs" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datiPagamentoAttesoWs"
})
@XmlRootElement(name = "modificaPagamentiAttesiRequest")
public class ModificaPagamentiAttesiRequest {

    @XmlElement(required = true)
    protected List<DatiModificaPagamentoAttesoWs> datiPagamentoAttesoWs;

    /**
     * Gets the value of the datiPagamentoAttesoWs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datiPagamentoAttesoWs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatiPagamentoAttesoWs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatiModificaPagamentoAttesoWs }
     * 
     * 
     */
    public List<DatiModificaPagamentoAttesoWs> getDatiPagamentoAttesoWs() {
        if (datiPagamentoAttesoWs == null) {
            datiPagamentoAttesoWs = new ArrayList<DatiModificaPagamentoAttesoWs>();
        }
        return this.datiPagamentoAttesoWs;
    }

}
