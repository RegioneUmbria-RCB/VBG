
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}TestataListaCarico"/&gt;
 *         &lt;element name="ListaDiCarico" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}ListaDiCarico"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "testata",
    "listaDiCarico"
})
@XmlRootElement(name = "InserisciListaDiCaricoRequest")
public class InserisciListaDiCaricoRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataListaCarico testata;
    @XmlElement(name = "ListaDiCarico", required = true)
    protected ListaDiCarico listaDiCarico;

    /**
     * Recupera il valore della proprietà testata.
     * 
     * @return
     *     possible object is
     *     {@link TestataListaCarico }
     *     
     */
    public TestataListaCarico getTestata() {
        return testata;
    }

    /**
     * Imposta il valore della proprietà testata.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataListaCarico }
     *     
     */
    public void setTestata(TestataListaCarico value) {
        this.testata = value;
    }

    /**
     * Recupera il valore della proprietà listaDiCarico.
     * 
     * @return
     *     possible object is
     *     {@link ListaDiCarico }
     *     
     */
    public ListaDiCarico getListaDiCarico() {
        return listaDiCarico;
    }

    /**
     * Imposta il valore della proprietà listaDiCarico.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaDiCarico }
     *     
     */
    public void setListaDiCarico(ListaDiCarico value) {
        this.listaDiCarico = value;
    }

}
