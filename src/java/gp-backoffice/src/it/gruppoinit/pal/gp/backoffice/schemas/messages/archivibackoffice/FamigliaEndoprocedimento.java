
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FamigliaEndoprocedimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FamigliaEndoprocedimento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proprietaBase" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}ProprietaBase" minOccurs="0"/>
 *         &lt;element name="categoriaEndoprocedimento" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}CategoriaEndoprocedimento" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FamigliaEndoprocedimento", propOrder = {
    "proprietaBase",
    "categoriaEndoprocedimento"
})
public class FamigliaEndoprocedimento {

    protected ProprietaBase proprietaBase;
    protected List<CategoriaEndoprocedimento> categoriaEndoprocedimento;

    /**
     * Gets the value of the proprietaBase property.
     * 
     * @return
     *     possible object is
     *     {@link ProprietaBase }
     *     
     */
    public ProprietaBase getProprietaBase() {
        return proprietaBase;
    }

    /**
     * Sets the value of the proprietaBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProprietaBase }
     *     
     */
    public void setProprietaBase(ProprietaBase value) {
        this.proprietaBase = value;
    }

    /**
     * Gets the value of the categoriaEndoprocedimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoriaEndoprocedimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoriaEndoprocedimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CategoriaEndoprocedimento }
     * 
     * 
     */
    public List<CategoriaEndoprocedimento> getCategoriaEndoprocedimento() {
        if (categoriaEndoprocedimento == null) {
            categoriaEndoprocedimento = new ArrayList<CategoriaEndoprocedimento>();
        }
        return this.categoriaEndoprocedimento;
    }

}
