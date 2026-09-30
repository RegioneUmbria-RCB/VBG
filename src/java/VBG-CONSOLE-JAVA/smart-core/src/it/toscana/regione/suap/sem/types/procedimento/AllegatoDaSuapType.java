
package it.toscana.regione.suap.sem.types.procedimento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Allegato proveniente dal Suap. Estende 'allegatoDaSuapSDType' aggiungendo 'destinatari'.
 * 			
 * 
 * <p>Java class for allegatoDaSuapType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="allegatoDaSuapType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.suap.regione.toscana.it/sem/types/procedimento}allegatoDaSuapSDType">
 *       &lt;sequence>
 *         &lt;element name="destinatari" type="{http://www.suap.regione.toscana.it/sem/types/attori}attoreReteSuap" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegatoDaSuapType", propOrder = {
    "destinatari"
})
public class AllegatoDaSuapType
    extends AllegatoDaSuapSDType
{

    @XmlElement(required = true)
    protected List<String> destinatari;

    /**
     * Gets the value of the destinatari property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destinatari property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestinatari().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDestinatari() {
        if (destinatari == null) {
            destinatari = new ArrayList<String>();
        }
        return this.destinatari;
    }

}
