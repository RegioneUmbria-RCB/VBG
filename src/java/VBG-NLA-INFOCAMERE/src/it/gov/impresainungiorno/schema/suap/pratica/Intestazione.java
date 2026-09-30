package it.gov.impresainungiorno.schema.suap.pratica;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per Intestazione complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Intestazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ufficio-destinatario" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiSuap"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="impresa" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}AnagraficaImpresa"/&gt;
 *           &lt;element name="richiedente" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}AnagraficaPersona"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="oggetto-comunicazione" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}OggettoComunicazione"/&gt;
 *         &lt;element name="codice-pratica" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/&gt;
 *         &lt;element name="procura-speciale" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProcuraSpeciale" minOccurs="0"/&gt;
 *         &lt;element name="dichiarante" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiDichiarante"/&gt;
 *         &lt;element name="domicilio-elettronico" type="{http://www.impresainungiorno.gov.it/schema/base}EMailIndirizzo" minOccurs="0"/&gt;
 *         &lt;element name="impianto-produttivo" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ImpiantoProduttivo" minOccurs="0"/&gt;
 *         &lt;element name="protocollo" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}ProtocolloSUAP" minOccurs="0"/&gt;
 *         &lt;element name="riferimento" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}RiferimentoSUAP" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Intestazione", propOrder = { "ufficioDestinatario", "impresa", "richiedente", "oggettoComunicazione", "codicePratica",
	"procuraSpeciale", "dichiarante", "domicilioElettronico", "impiantoProduttivo", "protocollo", "riferimento" })
public class Intestazione {

    @XmlElement(name = "ufficio-destinatario", required = true)
    protected EstremiSuap ufficioDestinatario;
    protected AnagraficaImpresa impresa;
    protected AnagraficaPersona richiedente;
    @XmlElement(name = "oggetto-comunicazione", required = true)
    protected OggettoComunicazione oggettoComunicazione;
    @XmlElement(name = "codice-pratica")
    protected String codicePratica;
    @XmlElement(name = "procura-speciale")
    protected ProcuraSpeciale procuraSpeciale;
    @XmlElement(required = true)
    protected EstremiDichiarante dichiarante;
    @XmlElement(name = "domicilio-elettronico")
    protected String domicilioElettronico;
    @XmlElement(name = "impianto-produttivo")
    protected ImpiantoProduttivo impiantoProduttivo;
    protected ProtocolloSUAP protocollo;
    protected List<RiferimentoSUAP> riferimento;

    /**
     * Recupera il valore della proprietà ufficioDestinatario.
     * 
     * @return possible object is {@link EstremiSuap }
     * 
     */
    public EstremiSuap getUfficioDestinatario() {

	return ufficioDestinatario;
    }

    /**
     * Imposta il valore della proprietà ufficioDestinatario.
     * 
     * @param value
     *            allowed object is {@link EstremiSuap }
     * 
     */
    public void setUfficioDestinatario(EstremiSuap value) {

	this.ufficioDestinatario = value;
    }

    /**
     * Recupera il valore della proprietà impresa.
     * 
     * @return possible object is {@link AnagraficaImpresa }
     * 
     */
    public AnagraficaImpresa getImpresa() {

	return impresa;
    }

    /**
     * Imposta il valore della proprietà impresa.
     * 
     * @param value
     *            allowed object is {@link AnagraficaImpresa }
     * 
     */
    public void setImpresa(AnagraficaImpresa value) {

	this.impresa = value;
    }

    /**
     * Recupera il valore della proprietà richiedente.
     * 
     * @return possible object is {@link AnagraficaPersona }
     * 
     */
    public AnagraficaPersona getRichiedente() {

	return richiedente;
    }

    /**
     * Imposta il valore della proprietà richiedente.
     * 
     * @param value
     *            allowed object is {@link AnagraficaPersona }
     * 
     */
    public void setRichiedente(AnagraficaPersona value) {

	this.richiedente = value;
    }

    /**
     * Recupera il valore della proprietà oggettoComunicazione.
     * 
     * @return possible object is {@link OggettoComunicazione }
     * 
     */
    public OggettoComunicazione getOggettoComunicazione() {

	return oggettoComunicazione;
    }

    /**
     * Imposta il valore della proprietà oggettoComunicazione.
     * 
     * @param value
     *            allowed object is {@link OggettoComunicazione }
     * 
     */
    public void setOggettoComunicazione(OggettoComunicazione value) {

	this.oggettoComunicazione = value;
    }

    /**
     * Recupera il valore della proprietà codicePratica.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodicePratica() {

	return codicePratica;
    }

    /**
     * Imposta il valore della proprietà codicePratica.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodicePratica(String value) {

	this.codicePratica = value;
    }

    /**
     * Recupera il valore della proprietà procuraSpeciale.
     * 
     * @return possible object is {@link ProcuraSpeciale }
     * 
     */
    public ProcuraSpeciale getProcuraSpeciale() {

	return procuraSpeciale;
    }

    /**
     * Imposta il valore della proprietà procuraSpeciale.
     * 
     * @param value
     *            allowed object is {@link ProcuraSpeciale }
     * 
     */
    public void setProcuraSpeciale(ProcuraSpeciale value) {

	this.procuraSpeciale = value;
    }

    /**
     * Recupera il valore della proprietà dichiarante.
     * 
     * @return possible object is {@link EstremiDichiarante }
     * 
     */
    public EstremiDichiarante getDichiarante() {

	return dichiarante;
    }

    /**
     * Imposta il valore della proprietà dichiarante.
     * 
     * @param value
     *            allowed object is {@link EstremiDichiarante }
     * 
     */
    public void setDichiarante(EstremiDichiarante value) {

	this.dichiarante = value;
    }

    /**
     * Recupera il valore della proprietà domicilioElettronico.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDomicilioElettronico() {

	return domicilioElettronico;
    }

    /**
     * Imposta il valore della proprietà domicilioElettronico.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDomicilioElettronico(String value) {

	this.domicilioElettronico = value;
    }

    /**
     * Recupera il valore della proprietà impiantoProduttivo.
     * 
     * @return possible object is {@link ImpiantoProduttivo }
     * 
     */
    public ImpiantoProduttivo getImpiantoProduttivo() {

	return impiantoProduttivo;
    }

    /**
     * Imposta il valore della proprietà impiantoProduttivo.
     * 
     * @param value
     *            allowed object is {@link ImpiantoProduttivo }
     * 
     */
    public void setImpiantoProduttivo(ImpiantoProduttivo value) {

	this.impiantoProduttivo = value;
    }

    /**
     * Recupera il valore della proprietà protocollo.
     * 
     * @return possible object is {@link ProtocolloSUAP }
     * 
     */
    public ProtocolloSUAP getProtocollo() {

	return protocollo;
    }

    /**
     * Imposta il valore della proprietà protocollo.
     * 
     * @param value
     *            allowed object is {@link ProtocolloSUAP }
     * 
     */
    public void setProtocollo(ProtocolloSUAP value) {

	this.protocollo = value;
    }

    /**
     * Gets the value of the riferimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the riferimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getRiferimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link RiferimentoSUAP }
     * 
     * 
     */
    public List<RiferimentoSUAP> getRiferimento() {

	if (riferimento == null) {
	    riferimento = new ArrayList<RiferimentoSUAP>();
	}
	return this.riferimento;
    }
}
