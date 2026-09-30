
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NodoAlbero complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NodoAlbero">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proprietaBase" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}ProprietaBase"/>
 *         &lt;element name="procedura" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}Procedura" minOccurs="0"/>
 *         &lt;element name="hasChilds" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nodoFiglio" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}NodoAlbero" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodoAlbero", propOrder = {
    "proprietaBase",
    "procedura",
    "hasChilds",
    "nodoFiglio"
})
public class NodoAlbero {

    @XmlElement(required = true)
    protected ProprietaBase proprietaBase;
    protected Procedura procedura;
    protected Boolean hasChilds;
    protected List<NodoAlbero> nodoFiglio;

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
     * Gets the value of the procedura property.
     * 
     * @return
     *     possible object is
     *     {@link Procedura }
     *     
     */
    public Procedura getProcedura() {
        return procedura;
    }

    /**
     * Sets the value of the procedura property.
     * 
     * @param value
     *     allowed object is
     *     {@link Procedura }
     *     
     */
    public void setProcedura(Procedura value) {
        this.procedura = value;
    }

    /**
     * Gets the value of the hasChilds property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasChilds() {
        return hasChilds;
    }

    /**
     * Sets the value of the hasChilds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasChilds(Boolean value) {
        this.hasChilds = value;
    }

    /**
     * Gets the value of the nodoFiglio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nodoFiglio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNodoFiglio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NodoAlbero }
     * 
     * 
     */
    public List<NodoAlbero> getNodoFiglio() {
        if (nodoFiglio == null) {
            nodoFiglio = new ArrayList<NodoAlbero>();
        }
        return this.nodoFiglio;
    }

}
