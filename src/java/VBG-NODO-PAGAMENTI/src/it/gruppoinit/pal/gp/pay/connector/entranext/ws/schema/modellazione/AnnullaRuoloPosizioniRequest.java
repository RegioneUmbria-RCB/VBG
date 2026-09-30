
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AnnullaRuoloPosizioniRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnnullaRuoloPosizioniRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID_RUOLO" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoRuoloEsterno" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnnullaRuoloPosizioniRequest", propOrder = {
    "idruolo",
    "riferimentoRuoloEsterno"
})
public class AnnullaRuoloPosizioniRequest
    extends LinkNextRequest
{

    @XmlElementRef(name = "ID_RUOLO", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idruolo;
    @XmlElement(name = "RiferimentoRuoloEsterno", required = true, nillable = true)
    protected String riferimentoRuoloEsterno;

    /**
     * Recupera il valore della proprietà idruolo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIDRUOLO() {
        return idruolo;
    }

    /**
     * Imposta il valore della proprietà idruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIDRUOLO(JAXBElement<Integer> value) {
        this.idruolo = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoRuoloEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoRuoloEsterno() {
        return riferimentoRuoloEsterno;
    }

    /**
     * Imposta il valore della proprietà riferimentoRuoloEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoRuoloEsterno(String value) {
        this.riferimentoRuoloEsterno = value;
    }

}
