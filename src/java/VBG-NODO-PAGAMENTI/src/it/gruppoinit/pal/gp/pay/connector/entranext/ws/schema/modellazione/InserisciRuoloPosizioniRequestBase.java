
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InserisciRuoloPosizioniRequestBase complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InserisciRuoloPosizioniRequestBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RuoloPosizioniDebitorie" type="{http://entranext.it/}RuoloPosizioniDebitorie" minOccurs="0"/&gt;
 *         &lt;element name="ID_RUOLO" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentoRuoloEsterno" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModalitaTest" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="UltimoInserimento" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="EmailNotificaAcquisizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InserisciRuoloPosizioniRequestBase", propOrder = {
    "ruoloPosizioniDebitorie",
    "idruolo",
    "riferimentoRuoloEsterno",
    "modalitaTest",
    "ultimoInserimento",
    "emailNotificaAcquisizione"
})
@XmlSeeAlso({
    InserisciRuoloPosizioniRequest.class,
    InserisciRuoloPosizioniICPRequest.class,
    InserisciRuoloPosizioniOSAPRequest.class
})
public abstract class InserisciRuoloPosizioniRequestBase
    extends LinkNextRequest
{

    @XmlElement(name = "RuoloPosizioniDebitorie")
    protected RuoloPosizioniDebitorie ruoloPosizioniDebitorie;
    @XmlElementRef(name = "ID_RUOLO", namespace = "http://entranext.it/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idruolo;
    @XmlElement(name = "RiferimentoRuoloEsterno", required = true, nillable = true)
    protected String riferimentoRuoloEsterno;
    @XmlElement(name = "ModalitaTest")
    protected boolean modalitaTest;
    @XmlElement(name = "UltimoInserimento")
    protected boolean ultimoInserimento;
    @XmlElement(name = "EmailNotificaAcquisizione")
    protected String emailNotificaAcquisizione;

    /**
     * Recupera il valore della proprietà ruoloPosizioniDebitorie.
     * 
     * @return
     *     possible object is
     *     {@link RuoloPosizioniDebitorie }
     *     
     */
    public RuoloPosizioniDebitorie getRuoloPosizioniDebitorie() {
        return ruoloPosizioniDebitorie;
    }

    /**
     * Imposta il valore della proprietà ruoloPosizioniDebitorie.
     * 
     * @param value
     *     allowed object is
     *     {@link RuoloPosizioniDebitorie }
     *     
     */
    public void setRuoloPosizioniDebitorie(RuoloPosizioniDebitorie value) {
        this.ruoloPosizioniDebitorie = value;
    }

    /**
     * Recupera il valore della proprietà idruolo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIDRUOLO() {
        return idruolo;
    }

    /**
     * Imposta il valore della proprietà idruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIDRUOLO(JAXBElement<Integer> value) {
        this.idruolo = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoRuoloEsterno.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoRuoloEsterno() {
        return riferimentoRuoloEsterno;
    }

    /**
     * Imposta il valore della proprietà riferimentoRuoloEsterno.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoRuoloEsterno(String value) {
        this.riferimentoRuoloEsterno = value;
    }

    /**
     * Recupera il valore della proprietà modalitaTest.
     * 
     */
    public boolean isModalitaTest() {
        return modalitaTest;
    }

    /**
     * Imposta il valore della proprietà modalitaTest.
     * 
     */
    public void setModalitaTest(boolean value) {
        this.modalitaTest = value;
    }

    /**
     * Recupera il valore della proprietà ultimoInserimento.
     * 
     */
    public boolean isUltimoInserimento() {
        return ultimoInserimento;
    }

    /**
     * Imposta il valore della proprietà ultimoInserimento.
     * 
     */
    public void setUltimoInserimento(boolean value) {
        this.ultimoInserimento = value;
    }

    /**
     * Recupera il valore della proprietà emailNotificaAcquisizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailNotificaAcquisizione() {
        return emailNotificaAcquisizione;
    }

    /**
     * Imposta il valore della proprietà emailNotificaAcquisizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailNotificaAcquisizione(String value) {
        this.emailNotificaAcquisizione = value;
    }

}
