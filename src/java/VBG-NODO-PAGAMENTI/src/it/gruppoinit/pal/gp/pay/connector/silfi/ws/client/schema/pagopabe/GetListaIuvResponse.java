
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.IuvFeList;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceErrore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="iuvFeList" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}IuvFeList"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codiceErrore",
    "iuvFeList"
})
@XmlRootElement(name = "getListaIuvResponse")
public class GetListaIuvResponse {

    @XmlElement(required = true)
    protected String codiceErrore;
    @XmlElement(required = true)
    protected IuvFeList iuvFeList;

    /**
     * Recupera il valore della proprietà codiceErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceErrore() {
        return codiceErrore;
    }

    /**
     * Imposta il valore della proprietà codiceErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceErrore(String value) {
        this.codiceErrore = value;
    }

    /**
     * Recupera il valore della proprietà iuvFeList.
     * 
     * @return
     *     possible object is
     *     {@link IuvFeList }
     *     
     */
    public IuvFeList getIuvFeList() {
        return iuvFeList;
    }

    /**
     * Imposta il valore della proprietà iuvFeList.
     * 
     * @param value
     *     allowed object is
     *     {@link IuvFeList }
     *     
     */
    public void setIuvFeList(IuvFeList value) {
        this.iuvFeList = value;
    }

}
