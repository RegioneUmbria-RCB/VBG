
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Pendenza complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Pendenza"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}HeadPendenza"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Insert" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Pendenza.InsertReplace" minOccurs="0"/&gt;
 *         &lt;element name="UpdateMassivo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Pendenza.UpdateMassivo" minOccurs="0"/&gt;
 *         &lt;element name="UpdateStatus" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Pendenza.UpdateStatus" minOccurs="0"/&gt;
 *         &lt;element name="Replace" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}Pendenza.InsertReplace" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pendenza", propOrder = {
    "insert",
    "updateMassivo",
    "updateStatus",
    "replace"
})
public class Pendenza
    extends HeadPendenza
{

    @XmlElement(name = "Insert")
    protected PendenzaInsertReplace insert;
    @XmlElement(name = "UpdateMassivo")
    protected PendenzaUpdateMassivo updateMassivo;
    @XmlElement(name = "UpdateStatus")
    protected PendenzaUpdateStatus updateStatus;
    @XmlElement(name = "Replace")
    protected PendenzaInsertReplace replace;

    /**
     * Recupera il valore della proprietà insert.
     * 
     * @return
     *     possible object is
     *     {@link PendenzaInsertReplace }
     *     
     */
    public PendenzaInsertReplace getInsert() {
        return insert;
    }

    /**
     * Imposta il valore della proprietà insert.
     * 
     * @param value
     *     allowed object is
     *     {@link PendenzaInsertReplace }
     *     
     */
    public void setInsert(PendenzaInsertReplace value) {
        this.insert = value;
    }

    /**
     * Recupera il valore della proprietà updateMassivo.
     * 
     * @return
     *     possible object is
     *     {@link PendenzaUpdateMassivo }
     *     
     */
    public PendenzaUpdateMassivo getUpdateMassivo() {
        return updateMassivo;
    }

    /**
     * Imposta il valore della proprietà updateMassivo.
     * 
     * @param value
     *     allowed object is
     *     {@link PendenzaUpdateMassivo }
     *     
     */
    public void setUpdateMassivo(PendenzaUpdateMassivo value) {
        this.updateMassivo = value;
    }

    /**
     * Recupera il valore della proprietà updateStatus.
     * 
     * @return
     *     possible object is
     *     {@link PendenzaUpdateStatus }
     *     
     */
    public PendenzaUpdateStatus getUpdateStatus() {
        return updateStatus;
    }

    /**
     * Imposta il valore della proprietà updateStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link PendenzaUpdateStatus }
     *     
     */
    public void setUpdateStatus(PendenzaUpdateStatus value) {
        this.updateStatus = value;
    }

    /**
     * Recupera il valore della proprietà replace.
     * 
     * @return
     *     possible object is
     *     {@link PendenzaInsertReplace }
     *     
     */
    public PendenzaInsertReplace getReplace() {
        return replace;
    }

    /**
     * Imposta il valore della proprietà replace.
     * 
     * @param value
     *     allowed object is
     *     {@link PendenzaInsertReplace }
     *     
     */
    public void setReplace(PendenzaInsertReplace value) {
        this.replace = value;
    }

}
