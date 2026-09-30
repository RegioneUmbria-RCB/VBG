
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="esito" type="{http://www.paevolution.com/ws/pagamenti_types/}EsitoType"/&gt;
 *         &lt;element name="messaggio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="statoPosizioni" type="{http://www.paevolution.com/ws/pagamenti_types/}ElencoStatoPosizioniType"/&gt;
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
    "esito",
    "messaggio",
    "statoPosizioni"
})
@XmlRootElement(name = "VerificaStatoPosizioniResponseType")
public class VerificaStatoPosizioniResponseType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EsitoType esito;
    @XmlElement(required = true)
    protected String messaggio;
    @XmlElement(required = true)
    protected ElencoStatoPosizioniType statoPosizioni;

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link EsitoType }
     *     
     */
    public EsitoType getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoType }
     *     
     */
    public void setEsito(EsitoType value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà messaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il valore della proprietà messaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggio(String value) {
        this.messaggio = value;
    }

    /**
     * Recupera il valore della proprietà statoPosizioni.
     * 
     * @return
     *     possible object is
     *     {@link ElencoStatoPosizioniType }
     *     
     */
    public ElencoStatoPosizioniType getStatoPosizioni() {
        return statoPosizioni;
    }

    /**
     * Imposta il valore della proprietà statoPosizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoStatoPosizioniType }
     *     
     */
    public void setStatoPosizioni(ElencoStatoPosizioniType value) {
        this.statoPosizioni = value;
    }

}
