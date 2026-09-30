
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiferimentoDebito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiferimentoDebito"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Pendenza" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="TipoDebito" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiferimentoDebito", propOrder = {
    "pendenza"
})
public class RiferimentoDebito {

    @XmlElement(name = "Pendenza", required = true)
    protected String pendenza;
    @XmlAttribute(name = "TipoDebito", required = true)
    protected String tipoDebito;

    /**
     * Recupera il valore della proprietà pendenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPendenza() {
        return pendenza;
    }

    /**
     * Imposta il valore della proprietà pendenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPendenza(String value) {
        this.pendenza = value;
    }

    /**
     * Recupera il valore della proprietà tipoDebito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDebito() {
        return tipoDebito;
    }

    /**
     * Imposta il valore della proprietà tipoDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDebito(String value) {
        this.tipoDebito = value;
    }

}
