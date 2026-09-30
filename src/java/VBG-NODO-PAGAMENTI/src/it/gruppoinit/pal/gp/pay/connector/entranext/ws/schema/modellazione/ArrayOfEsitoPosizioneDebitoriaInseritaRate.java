
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ArrayOfEsitoPosizioneDebitoriaInserita_Rate complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfEsitoPosizioneDebitoriaInserita_Rate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoPosizioneDebitoriaInserita_Rate" type="{http://entranext.it/}EsitoPosizioneDebitoriaInserita_Rate" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfEsitoPosizioneDebitoriaInserita_Rate", propOrder = {
    "esitoPosizioneDebitoriaInseritaRate"
})
public class ArrayOfEsitoPosizioneDebitoriaInseritaRate {

    @XmlElement(name = "EsitoPosizioneDebitoriaInserita_Rate", nillable = true)
    protected List<EsitoPosizioneDebitoriaInseritaRate> esitoPosizioneDebitoriaInseritaRate;

    /**
     * Gets the value of the esitoPosizioneDebitoriaInseritaRate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esitoPosizioneDebitoriaInseritaRate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsitoPosizioneDebitoriaInseritaRate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsitoPosizioneDebitoriaInseritaRate }
     * 
     * 
     */
    public List<EsitoPosizioneDebitoriaInseritaRate> getEsitoPosizioneDebitoriaInseritaRate() {
        if (esitoPosizioneDebitoriaInseritaRate == null) {
            esitoPosizioneDebitoriaInseritaRate = new ArrayList<EsitoPosizioneDebitoriaInseritaRate>();
        }
        return this.esitoPosizioneDebitoriaInseritaRate;
    }

}
