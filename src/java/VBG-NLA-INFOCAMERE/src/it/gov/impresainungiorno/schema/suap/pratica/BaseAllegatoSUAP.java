
package it.gov.impresainungiorno.schema.suap.pratica;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;


/**
 * 
 *             Descrive il singolo file allegato. Tipo astratto, non istanziabile
 * 			
 * 
 * <p>Classe Java per BaseAllegatoSUAP complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BaseAllegatoSUAP"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="descrizione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/&gt;
 *         &lt;element name="nome-file-originale" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/&gt;
 *         &lt;element name="mime" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}MimeType"/&gt;
 *         &lt;element name="mime-base" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}MimeType" minOccurs="0"/&gt;
 *         &lt;element name="dimensione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="tipo" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}TipoAllegato" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseAllegatoSUAP", propOrder = {
    "descrizione",
    "nomeFileOriginale",
    "mime",
    "mimeBase",
    "dimensione",
    "tipo"
})
@XmlSeeAlso({
    ModelloAttivita.TracciatoXml.class,
    ModelloAttivita.class,
    ProcuraSpeciale.TracciatoXml.class,
    ProcuraSpeciale.class,
    AllegatoGenerico.class,
    AllegatoCooperazione.class
})
public abstract class BaseAllegatoSUAP {

    protected String descrizione;
    @XmlElement(name = "nome-file-originale")
    protected String nomeFileOriginale;
    @XmlElement(required = true)
    protected String mime;
    @XmlElement(name = "mime-base")
    protected String mimeBase;
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger dimensione;
    protected TipoAllegato tipo;

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
     * Recupera il valore della proprietà nomeFileOriginale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeFileOriginale() {
        return nomeFileOriginale;
    }

    /**
     * Imposta il valore della proprietà nomeFileOriginale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeFileOriginale(String value) {
        this.nomeFileOriginale = value;
    }

    /**
     * Recupera il valore della proprietà mime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMime() {
        return mime;
    }

    /**
     * Imposta il valore della proprietà mime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMime(String value) {
        this.mime = value;
    }

    /**
     * Recupera il valore della proprietà mimeBase.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeBase() {
        return mimeBase;
    }

    /**
     * Imposta il valore della proprietà mimeBase.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeBase(String value) {
        this.mimeBase = value;
    }

    /**
     * Recupera il valore della proprietà dimensione.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDimensione() {
        return dimensione;
    }

    /**
     * Imposta il valore della proprietà dimensione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDimensione(BigInteger value) {
        this.dimensione = value;
    }

    /**
     * Recupera il valore della proprietà tipo.
     * 
     * @return
     *     possible object is
     *     {@link TipoAllegato }
     *     
     */
    public TipoAllegato getTipo() {
        return tipo;
    }

    /**
     * Imposta il valore della proprietà tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAllegato }
     *     
     */
    public void setTipo(TipoAllegato value) {
        this.tipo = value;
    }

}
