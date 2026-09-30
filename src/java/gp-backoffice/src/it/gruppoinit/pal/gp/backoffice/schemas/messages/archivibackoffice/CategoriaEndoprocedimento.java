
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CategoriaEndoprocedimento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CategoriaEndoprocedimento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proprietaBase" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}ProprietaBase"/>
 *         &lt;element name="endoprocedimento" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}Endoprocedimento" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="principale" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoriaEndoprocedimento", propOrder = {
    "proprietaBase",
    "endoprocedimento",
    "principale"
})
public class CategoriaEndoprocedimento {

    @XmlElement(required = true)
    protected ProprietaBase proprietaBase;
    protected List<Endoprocedimento> endoprocedimento;
    protected boolean principale;

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
     * Gets the value of the endoprocedimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the endoprocedimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndoprocedimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Endoprocedimento }
     * 
     * 
     */
    public List<Endoprocedimento> getEndoprocedimento() {
        if (endoprocedimento == null) {
            endoprocedimento = new ArrayList<Endoprocedimento>();
        }
        return this.endoprocedimento;
    }

    /**
     * Gets the value of the principale property.
     * 
     */
    public boolean isPrincipale() {
        return principale;
    }

    /**
     * Sets the value of the principale property.
     * 
     */
    public void setPrincipale(boolean value) {
        this.principale = value;
    }

}
