
package it.gov.impresainungiorno.schema.suap.pratica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ProtocolloSUAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ProtocolloSUAP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}FormatoProtocollo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="riferimento" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}FormatoProtocollo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtocolloSUAP", propOrder = {
    "riferimento"
})
public class ProtocolloSUAP
    extends FormatoProtocollo
{

    protected List<FormatoProtocollo> riferimento;

    /**
     * Gets the value of the riferimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riferimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiferimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormatoProtocollo }
     * 
     * 
     */
    public List<FormatoProtocollo> getRiferimento() {
        if (riferimento == null) {
            riferimento = new ArrayList<FormatoProtocollo>();
        }
        return this.riferimento;
    }

}
