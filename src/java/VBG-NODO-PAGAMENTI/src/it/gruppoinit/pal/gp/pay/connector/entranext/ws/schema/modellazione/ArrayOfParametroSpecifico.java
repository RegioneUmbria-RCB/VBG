
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfParametroSpecifico complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfParametroSpecifico"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ParametroSpecifico" type="{http://entranext.it/}ParametroSpecifico" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfParametroSpecifico", propOrder = {
    "parametroSpecifico"
})
public class ArrayOfParametroSpecifico {

    @XmlElement(name = "ParametroSpecifico", nillable = true)
    protected List<ParametroSpecifico> parametroSpecifico;

    /**
     * Gets the value of the parametroSpecifico property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametroSpecifico property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametroSpecifico().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParametroSpecifico }
     * 
     * 
     */
    public List<ParametroSpecifico> getParametroSpecifico() {
        if (parametroSpecifico == null) {
            parametroSpecifico = new ArrayList<ParametroSpecifico>();
        }
        return this.parametroSpecifico;
    }

}
