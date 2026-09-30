
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idModelloT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceScheda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errore" type="{http://gruppoinit.it/sigepro/schemas/messages/base}ErroreBackofficeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idModelloT",
    "codiceScheda",
    "errore"
})
@XmlRootElement(name = "ModellitInsertResponse")
public class ModellitInsertResponse {

    @XmlElement(required = true)
    protected String idModelloT;
    protected String codiceScheda;
    protected List<ErroreBackofficeType> errore;

    /**
     * Gets the value of the idModelloT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdModelloT() {
        return idModelloT;
    }

    /**
     * Sets the value of the idModelloT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdModelloT(String value) {
        this.idModelloT = value;
    }

    /**
     * Gets the value of the codiceScheda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceScheda() {
        return codiceScheda;
    }

    /**
     * Sets the value of the codiceScheda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceScheda(String value) {
        this.codiceScheda = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errore property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrore().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErroreBackofficeType }
     * 
     * 
     */
    public List<ErroreBackofficeType> getErrore() {
        if (errore == null) {
            errore = new ArrayList<ErroreBackofficeType>();
        }
        return this.errore;
    }

}
