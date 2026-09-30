
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdpBodyType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdpBodyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InfoMessaggio" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}InfoMessaggio"/&gt;
 *         &lt;element name="InfoDettaglio" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpEsito}InfoDettaglio" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdpBodyType", propOrder = {
    "infoMessaggio",
    "infoDettaglio"
})
public class IdpBodyType {

    @XmlElement(name = "InfoMessaggio", required = true)
    protected InfoMessaggio infoMessaggio;
    @XmlElement(name = "InfoDettaglio")
    protected InfoDettaglio infoDettaglio;

    /**
     * Recupera il valore della proprietà infoMessaggio.
     * 
     * @return
     *     possible object is
     *     {@link InfoMessaggio }
     *     
     */
    public InfoMessaggio getInfoMessaggio() {
        return infoMessaggio;
    }

    /**
     * Imposta il valore della proprietà infoMessaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoMessaggio }
     *     
     */
    public void setInfoMessaggio(InfoMessaggio value) {
        this.infoMessaggio = value;
    }

    /**
     * Recupera il valore della proprietà infoDettaglio.
     * 
     * @return
     *     possible object is
     *     {@link InfoDettaglio }
     *     
     */
    public InfoDettaglio getInfoDettaglio() {
        return infoDettaglio;
    }

    /**
     * Imposta il valore della proprietà infoDettaglio.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoDettaglio }
     *     
     */
    public void setInfoDettaglio(InfoDettaglio value) {
        this.infoDettaglio = value;
    }

}
