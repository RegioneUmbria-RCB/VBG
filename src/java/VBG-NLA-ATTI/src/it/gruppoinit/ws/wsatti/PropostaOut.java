
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropostaOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropostaOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Organo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Organo_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Trattamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Trattamento_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Proponente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Proponente_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Relatore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Relatore_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImmediatamenteEsecutiva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACapigruppo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="APrefetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ACoReCo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DaPubblicare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IdentificativoDelibera" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropostaOut", propOrder = {
    "organo",
    "organoDescrizione",
    "trattamento",
    "trattamentoDescrizione",
    "proponente",
    "proponenteDescrizione",
    "relatore",
    "relatoreDescrizione",
    "immediatamenteEsecutiva",
    "aCapigruppo",
    "aPrefetto",
    "aCoReCo",
    "daPubblicare",
    "anno",
    "numero",
    "identificativoDelibera"
})
public class PropostaOut {

    @XmlElementRef(name = "Organo", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> organo;
    @XmlElementRef(name = "Organo_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> organoDescrizione;
    @XmlElementRef(name = "Trattamento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> trattamento;
    @XmlElementRef(name = "Trattamento_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> trattamentoDescrizione;
    @XmlElementRef(name = "Proponente", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> proponente;
    @XmlElementRef(name = "Proponente_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> proponenteDescrizione;
    @XmlElementRef(name = "Relatore", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> relatore;
    @XmlElementRef(name = "Relatore_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> relatoreDescrizione;
    @XmlElementRef(name = "ImmediatamenteEsecutiva", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> immediatamenteEsecutiva;
    @XmlElement(name = "ACapigruppo")
    protected String aCapigruppo;
    @XmlElement(name = "APrefetto")
    protected String aPrefetto;
    @XmlElement(name = "ACoReCo")
    protected String aCoReCo;
    @XmlElementRef(name = "DaPubblicare", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> daPubblicare;
    @XmlElement(name = "Anno")
    protected short anno;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "IdentificativoDelibera")
    protected int identificativoDelibera;

    /**
     * Gets the value of the organo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrgano() {
        return organo;
    }

    /**
     * Sets the value of the organo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrgano(JAXBElement<String> value) {
        this.organo = value;
    }

    /**
     * Gets the value of the organoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrganoDescrizione() {
        return organoDescrizione;
    }

    /**
     * Sets the value of the organoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrganoDescrizione(JAXBElement<String> value) {
        this.organoDescrizione = value;
    }

    /**
     * Gets the value of the trattamento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTrattamento() {
        return trattamento;
    }

    /**
     * Sets the value of the trattamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTrattamento(JAXBElement<String> value) {
        this.trattamento = value;
    }

    /**
     * Gets the value of the trattamentoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTrattamentoDescrizione() {
        return trattamentoDescrizione;
    }

    /**
     * Sets the value of the trattamentoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTrattamentoDescrizione(JAXBElement<String> value) {
        this.trattamentoDescrizione = value;
    }

    /**
     * Gets the value of the proponente property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProponente() {
        return proponente;
    }

    /**
     * Sets the value of the proponente property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProponente(JAXBElement<String> value) {
        this.proponente = value;
    }

    /**
     * Gets the value of the proponenteDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProponenteDescrizione() {
        return proponenteDescrizione;
    }

    /**
     * Sets the value of the proponenteDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProponenteDescrizione(JAXBElement<String> value) {
        this.proponenteDescrizione = value;
    }

    /**
     * Gets the value of the relatore property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRelatore() {
        return relatore;
    }

    /**
     * Sets the value of the relatore property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRelatore(JAXBElement<String> value) {
        this.relatore = value;
    }

    /**
     * Gets the value of the relatoreDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRelatoreDescrizione() {
        return relatoreDescrizione;
    }

    /**
     * Sets the value of the relatoreDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRelatoreDescrizione(JAXBElement<String> value) {
        this.relatoreDescrizione = value;
    }

    /**
     * Gets the value of the immediatamenteEsecutiva property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getImmediatamenteEsecutiva() {
        return immediatamenteEsecutiva;
    }

    /**
     * Sets the value of the immediatamenteEsecutiva property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setImmediatamenteEsecutiva(JAXBElement<String> value) {
        this.immediatamenteEsecutiva = value;
    }

    /**
     * Gets the value of the aCapigruppo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACapigruppo() {
        return aCapigruppo;
    }

    /**
     * Sets the value of the aCapigruppo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACapigruppo(String value) {
        this.aCapigruppo = value;
    }

    /**
     * Gets the value of the aPrefetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPrefetto() {
        return aPrefetto;
    }

    /**
     * Sets the value of the aPrefetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPrefetto(String value) {
        this.aPrefetto = value;
    }

    /**
     * Gets the value of the aCoReCo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getACoReCo() {
        return aCoReCo;
    }

    /**
     * Sets the value of the aCoReCo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setACoReCo(String value) {
        this.aCoReCo = value;
    }

    /**
     * Gets the value of the daPubblicare property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDaPubblicare() {
        return daPubblicare;
    }

    /**
     * Sets the value of the daPubblicare property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDaPubblicare(JAXBElement<String> value) {
        this.daPubblicare = value;
    }

    /**
     * Gets the value of the anno property.
     * 
     */
    public short getAnno() {
        return anno;
    }

    /**
     * Sets the value of the anno property.
     * 
     */
    public void setAnno(short value) {
        this.anno = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Gets the value of the identificativoDelibera property.
     * 
     */
    public int getIdentificativoDelibera() {
        return identificativoDelibera;
    }

    /**
     * Sets the value of the identificativoDelibera property.
     * 
     */
    public void setIdentificativoDelibera(int value) {
        this.identificativoDelibera = value;
    }

}
