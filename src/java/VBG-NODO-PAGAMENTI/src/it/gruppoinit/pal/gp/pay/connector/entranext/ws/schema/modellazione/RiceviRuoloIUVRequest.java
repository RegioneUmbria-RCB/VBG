
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiceviRuoloIUVRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiceviRuoloIUVRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID_RUOLO" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiceviRuoloIUVRequest", propOrder = {
    "idruolo"
})
public class RiceviRuoloIUVRequest
    extends LinkNextRequest
{

    @XmlElement(name = "ID_RUOLO")
    protected int idruolo;

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

}
