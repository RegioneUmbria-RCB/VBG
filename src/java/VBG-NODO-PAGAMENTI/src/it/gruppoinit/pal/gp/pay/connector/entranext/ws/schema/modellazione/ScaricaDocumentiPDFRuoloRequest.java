
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ScaricaDocumentiPDFRuoloRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ScaricaDocumentiPDFRuoloRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID_RUOLO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ForzaRigenerazione" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScaricaDocumentiPDFRuoloRequest", propOrder = {
    "idruolo",
    "forzaRigenerazione"
})
public class ScaricaDocumentiPDFRuoloRequest
    extends LinkNextRequest
{

    @XmlElement(name = "ID_RUOLO")
    protected int idruolo;
    @XmlElement(name = "ForzaRigenerazione", defaultValue = "false")
    protected Boolean forzaRigenerazione;

    /**
     * Recupera il valore della proprietà idruolo.
     * 
     */
    public int getIDRUOLO() {
        return idruolo;
    }

    /**
     * Imposta il valore della proprietà idruolo.
     * 
     */
    public void setIDRUOLO(int value) {
        this.idruolo = value;
    }

    /**
     * Recupera il valore della proprietà forzaRigenerazione.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isForzaRigenerazione() {
        return forzaRigenerazione;
    }

    /**
     * Imposta il valore della proprietà forzaRigenerazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForzaRigenerazione(Boolean value) {
        this.forzaRigenerazione = value;
    }

}
