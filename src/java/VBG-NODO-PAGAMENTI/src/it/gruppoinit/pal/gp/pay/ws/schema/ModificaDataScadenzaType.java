
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}PayRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="riferimentoPosizione" type="{http://www.paevolution.com/ws/pagamenti_types/}RiferimentoPosizioneDebitoriaType"/&gt;
 *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "riferimentoPosizione",
    "dataScadenza"
})
@XmlRootElement(name = "ModificaDataScadenzaType")
public class ModificaDataScadenzaType
    extends PayRequestType
{

    @XmlElement(required = true)
    protected RiferimentoPosizioneDebitoriaType riferimentoPosizione;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenza;

    /**
     * Recupera il valore della proprietà riferimentoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link RiferimentoPosizioneDebitoriaType }
     *     
     */
    public RiferimentoPosizioneDebitoriaType getRiferimentoPosizione() {
        return riferimentoPosizione;
    }

    /**
     * Imposta il valore della proprietà riferimentoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link RiferimentoPosizioneDebitoriaType }
     *     
     */
    public void setRiferimentoPosizione(RiferimentoPosizioneDebitoriaType value) {
        this.riferimentoPosizione = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Imposta il valore della proprietà dataScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

}
