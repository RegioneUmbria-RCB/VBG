package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DeterminaIn complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeterminaIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Trattamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Proponente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dirigente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Classifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DaPubblicare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Utente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ruolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Allegati" type="{http://tempuri.org/}ArrayOfAllegatoIn" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeterminaIn", propOrder = { "oggetto", "trattamento", "proponente", "dirigente", "dataDocumento", "classifica", "daPubblicare",
	"utente", "ruolo", "allegati" })
public class DeterminaIn {

    @XmlElementRef(name = "Oggetto", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> oggetto;
    @XmlElementRef(name = "Trattamento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> trattamento;
    @XmlElementRef(name = "Proponente", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> proponente;
    @XmlElementRef(name = "Dirigente", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> dirigente;
    @XmlElementRef(name = "DataDocumento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> dataDocumento;
    @XmlElementRef(name = "Classifica", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> classifica;
    @XmlElementRef(name = "DaPubblicare", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> daPubblicare;
    @XmlElementRef(name = "Utente", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> utente;
    @XmlElementRef(name = "Ruolo", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> ruolo;
    @XmlElementRef(name = "Allegati", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfAllegatoIn> allegati;

    /**
     * Gets the value of the oggetto property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getOggetto() {

	return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setOggetto(JAXBElement<String> value) {

	this.oggetto = value;
    }

    /**
     * Gets the value of the trattamento property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getTrattamento() {

	return trattamento;
    }

    /**
     * Sets the value of the trattamento property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setTrattamento(JAXBElement<String> value) {

	this.trattamento = value;
    }

    /**
     * Gets the value of the proponente property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getProponente() {

	return proponente;
    }

    /**
     * Sets the value of the proponente property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setProponente(JAXBElement<String> value) {

	this.proponente = value;
    }

    /**
     * Gets the value of the dirigente property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getDirigente() {

	return dirigente;
    }

    /**
     * Sets the value of the dirigente property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setDirigente(JAXBElement<String> value) {

	this.dirigente = value;
    }

    /**
     * Gets the value of the dataDocumento property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getDataDocumento() {

	return dataDocumento;
    }

    /**
     * Sets the value of the dataDocumento property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setDataDocumento(JAXBElement<String> value) {

	this.dataDocumento = value;
    }

    /**
     * Gets the value of the classifica property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getClassifica() {

	return classifica;
    }

    /**
     * Sets the value of the classifica property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setClassifica(JAXBElement<String> value) {

	this.classifica = value;
    }

    /**
     * Gets the value of the daPubblicare property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getDaPubblicare() {

	return daPubblicare;
    }

    /**
     * Sets the value of the daPubblicare property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setDaPubblicare(JAXBElement<String> value) {

	this.daPubblicare = value;
    }

    /**
     * Gets the value of the utente property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getUtente() {

	return utente;
    }

    /**
     * Sets the value of the utente property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setUtente(JAXBElement<String> value) {

	this.utente = value;
    }

    /**
     * Gets the value of the ruolo property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getRuolo() {

	return ruolo;
    }

    /**
     * Sets the value of the ruolo property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setRuolo(JAXBElement<String> value) {

	this.ruolo = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link ArrayOfAllegatoIn }{@code >}
     * 
     */
    public JAXBElement<ArrayOfAllegatoIn> getAllegati() {

	return allegati;
    }

    /**
     * Sets the value of the allegati property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link ArrayOfAllegatoIn }{@code >}
     * 
     */
    public void setAllegati(JAXBElement<ArrayOfAllegatoIn> value) {

	this.allegati = value;
    }
}
