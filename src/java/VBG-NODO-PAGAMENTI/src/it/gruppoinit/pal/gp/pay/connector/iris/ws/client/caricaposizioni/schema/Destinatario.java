
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.include.TipoDestinatario;


/**
 * <p>Classe Java per Destinatario complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Destinatario"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DatiDestinatario" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}DatiDestinatarioType" minOccurs="0"/&gt;
 *         &lt;element name="IdentificativoAlternativo" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze}IdentificativoAlternativoType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Tipo" use="required" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}TipoDestinatario" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Destinatario", propOrder = {
    "id",
    "descrizione",
    "datiDestinatario",
    "identificativoAlternativo"
})
public class Destinatario {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "Descrizione")
    protected String descrizione;
    @XmlElement(name = "DatiDestinatario")
    protected DatiDestinatarioType datiDestinatario;
    @XmlElement(name = "IdentificativoAlternativo")
    protected IdentificativoAlternativoType identificativoAlternativo;
    @XmlAttribute(name = "Tipo", required = true)
    protected TipoDestinatario tipo;

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà datiDestinatario.
     * 
     * @return
     *     possible object is
     *     {@link DatiDestinatarioType }
     *     
     */
    public DatiDestinatarioType getDatiDestinatario() {
        return datiDestinatario;
    }

    /**
     * Imposta il valore della proprietà datiDestinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiDestinatarioType }
     *     
     */
    public void setDatiDestinatario(DatiDestinatarioType value) {
        this.datiDestinatario = value;
    }

    /**
     * Recupera il valore della proprietà identificativoAlternativo.
     * 
     * @return
     *     possible object is
     *     {@link IdentificativoAlternativoType }
     *     
     */
    public IdentificativoAlternativoType getIdentificativoAlternativo() {
        return identificativoAlternativo;
    }

    /**
     * Imposta il valore della proprietà identificativoAlternativo.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificativoAlternativoType }
     *     
     */
    public void setIdentificativoAlternativo(IdentificativoAlternativoType value) {
        this.identificativoAlternativo = value;
    }

    /**
     * Recupera il valore della proprietà tipo.
     * 
     * @return
     *     possible object is
     *     {@link TipoDestinatario }
     *     
     */
    public TipoDestinatario getTipo() {
        return tipo;
    }

    /**
     * Imposta il valore della proprietà tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDestinatario }
     *     
     */
    public void setTipo(TipoDestinatario value) {
        this.tipo = value;
    }

}
