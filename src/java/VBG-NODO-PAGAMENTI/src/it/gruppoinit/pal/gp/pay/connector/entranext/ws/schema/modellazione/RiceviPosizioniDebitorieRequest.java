
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiceviPosizioniDebitorieRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiceviPosizioniDebitorieRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Da" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="A" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ContaPosizioni" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiceviPosizioniDebitorieRequest", propOrder = {
    "codiceFiscale",
    "da",
    "a",
    "contaPosizioni"
})
public class RiceviPosizioniDebitorieRequest
    extends LinkNextRequest
{

    @XmlElement(name = "CodiceFiscale")
    protected String codiceFiscale;
    @XmlElement(name = "Da")
    protected int da;
    @XmlElement(name = "A")
    protected int a;
    @XmlElement(name = "ContaPosizioni")
    protected boolean contaPosizioni;

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà da.
     * 
     */
    public int getDa() {
        return da;
    }

    /**
     * Imposta il valore della proprietà da.
     * 
     */
    public void setDa(int value) {
        this.da = value;
    }

    /**
     * Recupera il valore della proprietà a.
     * 
     */
    public int getA() {
        return a;
    }

    /**
     * Imposta il valore della proprietà a.
     * 
     */
    public void setA(int value) {
        this.a = value;
    }

    /**
     * Recupera il valore della proprietà contaPosizioni.
     * 
     */
    public boolean isContaPosizioni() {
        return contaPosizioni;
    }

    /**
     * Imposta il valore della proprietà contaPosizioni.
     * 
     */
    public void setContaPosizioni(boolean value) {
        this.contaPosizioni = value;
    }

}
