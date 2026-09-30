
package it.gruppoinit.wssit;

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
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filtro" type="{http://init.sigepro.it}FiltroRicercaListaVie"/>
 *         &lt;element name="codiciComuni" type="{http://init.sigepro.it}ArrayOfString" minOccurs="0"/>
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
    "token",
    "software",
    "filtro",
    "codiciComuni"
})
@XmlRootElement(name = "GetListaVie")
public class GetListaVie {

    protected String token;
    protected String software;
    @XmlElement(required = true)
    protected FiltroRicercaListaVie filtro;
    protected ArrayOfString codiciComuni;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the software property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

    /**
     * Gets the value of the filtro property.
     * 
     * @return
     *     possible object is
     *     {@link FiltroRicercaListaVie }
     *     
     */
    public FiltroRicercaListaVie getFiltro() {
        return filtro;
    }

    /**
     * Sets the value of the filtro property.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltroRicercaListaVie }
     *     
     */
    public void setFiltro(FiltroRicercaListaVie value) {
        this.filtro = value;
    }

    /**
     * Gets the value of the codiciComuni property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getCodiciComuni() {
        return codiciComuni;
    }

    /**
     * Sets the value of the codiciComuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setCodiciComuni(ArrayOfString value) {
        this.codiciComuni = value;
    }

}
