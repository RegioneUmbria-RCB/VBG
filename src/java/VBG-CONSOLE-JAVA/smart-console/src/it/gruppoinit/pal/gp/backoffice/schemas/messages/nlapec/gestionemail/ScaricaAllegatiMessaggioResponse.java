//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.05.27 alle 03:53:35 PM CEST 
//


package it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="messaggio" type="{http://gruppoinit.it/nlapec}MessaggioType"/>
 *         &lt;element name="allegati" type="{http://gruppoinit.it/nlapec}AllegatoMailType" maxOccurs="unbounded" minOccurs="0"/>
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
    "messaggio",
    "allegati"
})
@XmlRootElement(name = "ScaricaAllegatiMessaggioResponse", namespace = "http://gruppoinit.it/nlapec")
public class ScaricaAllegatiMessaggioResponse {

    @XmlElement(namespace = "http://gruppoinit.it/nlapec", required = true)
    protected MessaggioType messaggio;
    @XmlElement(namespace = "http://gruppoinit.it/nlapec")
    protected List<AllegatoMailType> allegati;

    /**
     * Recupera il valore della proprietà messaggio.
     * 
     * @return
     *     possible object is
     *     {@link MessaggioType }
     *     
     */
    public MessaggioType getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il valore della proprietà messaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link MessaggioType }
     *     
     */
    public void setMessaggio(MessaggioType value) {
        this.messaggio = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoMailType }
     * 
     * 
     */
    public List<AllegatoMailType> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<AllegatoMailType>();
        }
        return this.allegati;
    }

}
