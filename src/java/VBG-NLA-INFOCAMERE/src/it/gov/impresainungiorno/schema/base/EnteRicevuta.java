
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EnteRicevuta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EnteRicevuta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="delegante" type="{http://www.impresainungiorno.gov.it/schema/base}UtentePortale" minOccurs="0"/&gt;
 *         &lt;element name="invio" type="{http://www.impresainungiorno.gov.it/schema/base}InvioAdempimento" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="nome" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnteRicevuta", propOrder = {
    "delegante",
    "invio"
})
public class EnteRicevuta {

    protected UtentePortale delegante;
    @XmlElement(required = true)
    protected List<InvioAdempimento> invio;
    @XmlAttribute(name = "nome")
    protected String nome;

    /**
     * Recupera il valore della proprietà delegante.
     * 
     * @return
     *     possible object is
     *     {@link UtentePortale }
     *     
     */
    public UtentePortale getDelegante() {
        return delegante;
    }

    /**
     * Imposta il valore della proprietà delegante.
     * 
     * @param value
     *     allowed object is
     *     {@link UtentePortale }
     *     
     */
    public void setDelegante(UtentePortale value) {
        this.delegante = value;
    }

    /**
     * Gets the value of the invio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvioAdempimento }
     * 
     * 
     */
    public List<InvioAdempimento> getInvio() {
        if (invio == null) {
            invio = new ArrayList<InvioAdempimento>();
        }
        return this.invio;
    }

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

}
