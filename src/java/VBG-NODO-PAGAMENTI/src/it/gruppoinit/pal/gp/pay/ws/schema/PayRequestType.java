
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PayRequestType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PayRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="cfEnteCreditore" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayRequestType")
@XmlSeeAlso({
    ModificaDataScadenzaType.class,
    ModificaDataFineValiditaType.class,
    DocumentiPosizioneDebitoriaType.class,
    ScaricaRicevuteTelematicheType.class,
    GeneraFattureType.class,
    InviaAvvisiPagamentoType.class,
    NotificaPagamentoOffline.class,
    VerificaStatoPosizioniType.class,
    AttivaPagamentoOnTheFlyType.class,
    AttivaSessionePagamentoType.class,
    AnnullaPosizioniDebitorieType.class,
    InserisciPosizioniDebitorieType.class
})
public class PayRequestType {

    @XmlAttribute(name = "cfEnteCreditore", required = true)
    protected String cfEnteCreditore;

    /**
     * Recupera il valore della proprietà cfEnteCreditore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfEnteCreditore() {
        return cfEnteCreditore;
    }

    /**
     * Imposta il valore della proprietà cfEnteCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfEnteCreditore(String value) {
        this.cfEnteCreditore = value;
    }

}
