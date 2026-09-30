
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AnomaliaTRBMIN complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnomaliaTRBMIN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Gravita" type="{http://entranext.it/}GravitaAnomalia"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnomaliaTRBMIN", propOrder = {
    "gravita",
    "descrizione"
})
@XmlSeeAlso({
    AnomaliaPosizioneDebitoria.class
})
public class AnomaliaTRBMIN {

    @XmlElement(name = "Gravita", required = true)
    @XmlSchemaType(name = "string")
    protected GravitaAnomalia gravita;
    @XmlElement(name = "Descrizione")
    protected String descrizione;

    /**
     * Recupera il valore della proprietà gravita.
     * 
     * @return
     *     possible object is
     *     {@link GravitaAnomalia }
     *     
     */
    public GravitaAnomalia getGravita() {
        return gravita;
    }

    /**
     * Imposta il valore della proprietà gravita.
     * 
     * @param value
     *     allowed object is
     *     {@link GravitaAnomalia }
     *     
     */
    public void setGravita(GravitaAnomalia value) {
        this.gravita = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

}
