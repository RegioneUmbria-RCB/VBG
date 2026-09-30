
package it.gruppoinit.pal.gp.core.ws.client.parix_cloud;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="sgl_prv_sede" type="{http://parixgate.infocamere.it/services/gate/types}string2"/>
 *         &lt;element name="n_rea_sede" type="{http://parixgate.infocamere.it/services/gate/types}int9"/>
 *         &lt;element name="switch_control" type="{http://parixgate.infocamere.it/services/gate/types}switch_control" minOccurs="0"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "sglPrvSede",
    "nReaSede",
    "switchControl",
    "user",
    "password"
})
@XmlRootElement(name = "DettaglioRidottoImpresaRequest")
public class DettaglioRidottoImpresaRequest {

    @XmlElement(name = "sgl_prv_sede", required = true)
    protected String sglPrvSede;
    @XmlElement(name = "n_rea_sede")
    protected int nReaSede;
    @XmlElementRef(name = "switch_control", namespace = "http://parixgate.infocamere.it/services/gate/", type = JAXBElement.class)
    protected JAXBElement<String> switchControl;
    protected String user;
    protected String password;

    /**
     * Gets the value of the sglPrvSede property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSglPrvSede() {
        return sglPrvSede;
    }

    /**
     * Sets the value of the sglPrvSede property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSglPrvSede(String value) {
        this.sglPrvSede = value;
    }

    /**
     * Gets the value of the nReaSede property.
     * 
     */
    public int getNReaSede() {
        return nReaSede;
    }

    /**
     * Sets the value of the nReaSede property.
     * 
     */
    public void setNReaSede(int value) {
        this.nReaSede = value;
    }

    /**
     * Gets the value of the switchControl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSwitchControl() {
        return switchControl;
    }

    /**
     * Sets the value of the switchControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSwitchControl(JAXBElement<String> value) {
        this.switchControl = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

}
