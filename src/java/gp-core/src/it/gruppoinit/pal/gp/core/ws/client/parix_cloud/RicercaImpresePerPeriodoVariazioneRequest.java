
package it.gruppoinit.pal.gp.core.ws.client.parix_cloud;

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
 *         &lt;element name="dt_ult_agg_ri_1" type="{http://parixgate.infocamere.it/services/gate/types}date"/>
 *         &lt;element name="dt_ult_agg_ri_2" type="{http://parixgate.infocamere.it/services/gate/types}date"/>
 *         &lt;element name="cciaa_regz" type="{http://parixgate.infocamere.it/services/gate/types}string2"/>
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
    "dtUltAggRi1",
    "dtUltAggRi2",
    "cciaaRegz",
    "user",
    "password"
})
@XmlRootElement(name = "RicercaImpresePerPeriodoVariazioneRequest")
public class RicercaImpresePerPeriodoVariazioneRequest {

    @XmlElement(name = "dt_ult_agg_ri_1", required = true)
    protected String dtUltAggRi1;
    @XmlElement(name = "dt_ult_agg_ri_2", required = true)
    protected String dtUltAggRi2;
    @XmlElement(name = "cciaa_regz", required = true)
    protected String cciaaRegz;
    protected String user;
    protected String password;

    /**
     * Gets the value of the dtUltAggRi1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtUltAggRi1() {
        return dtUltAggRi1;
    }

    /**
     * Sets the value of the dtUltAggRi1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtUltAggRi1(String value) {
        this.dtUltAggRi1 = value;
    }

    /**
     * Gets the value of the dtUltAggRi2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDtUltAggRi2() {
        return dtUltAggRi2;
    }

    /**
     * Sets the value of the dtUltAggRi2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDtUltAggRi2(String value) {
        this.dtUltAggRi2 = value;
    }

    /**
     * Gets the value of the cciaaRegz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCciaaRegz() {
        return cciaaRegz;
    }

    /**
     * Sets the value of the cciaaRegz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCciaaRegz(String value) {
        this.cciaaRegz = value;
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
