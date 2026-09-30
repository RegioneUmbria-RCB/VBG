package it.gov.impresainungiorno.schema.suap.ente;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloRI;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP;

/**
 * <p>
 * Classe Java per CooperazioneSUAPEnte complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CooperazioneSUAPEnte"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="info-schema"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="versione" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;pattern value="(\d+.)?\d+.\d+(-beta)?"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="data" use="required" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="intestazione"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="suap-competente" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiSuap"/&gt;
 *                   &lt;element name="ente-destinatario" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiEnte" maxOccurs="unbounded"/&gt;
 *                   &lt;group ref="{http://www.impresainungiorno.gov.it/schema/suap/ente}RiferimentoPratica"/&gt;
 *                   &lt;group ref="{http://www.impresainungiorno.gov.it/schema/suap/ente}ComunicazioneEnte"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="progressivo" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *                 &lt;attribute name="totale" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="allegato" type="{http://www.impresainungiorno.gov.it/schema/suap/ente}AllegatoCooperazione" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "cooperazione-suap-ente")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CooperazioneSUAPEnte", propOrder = { "infoSchema", "intestazione", "allegato" })
public class CooperazioneSUAPEnte {

    @XmlElement(name = "info-schema", required = true)
    protected CooperazioneSUAPEnte.InfoSchema infoSchema;
    @XmlElement(required = true)
    protected CooperazioneSUAPEnte.Intestazione intestazione;
    protected List<AllegatoCooperazione> allegato;

    /**
     * Recupera il valore della proprietà infoSchema.
     * 
     * @return possible object is {@link CooperazioneSUAPEnte.InfoSchema }
     * 
     */
    public CooperazioneSUAPEnte.InfoSchema getInfoSchema() {

	return infoSchema;
    }

    /**
     * Imposta il valore della proprietà infoSchema.
     * 
     * @param value
     *            allowed object is {@link CooperazioneSUAPEnte.InfoSchema }
     * 
     */
    public void setInfoSchema(CooperazioneSUAPEnte.InfoSchema value) {

	this.infoSchema = value;
    }

    /**
     * Recupera il valore della proprietà intestazione.
     * 
     * @return possible object is {@link CooperazioneSUAPEnte.Intestazione }
     * 
     */
    public CooperazioneSUAPEnte.Intestazione getIntestazione() {

	return intestazione;
    }

    /**
     * Imposta il valore della proprietà intestazione.
     * 
     * @param value
     *            allowed object is {@link CooperazioneSUAPEnte.Intestazione }
     * 
     */
    public void setIntestazione(CooperazioneSUAPEnte.Intestazione value) {

	this.intestazione = value;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the allegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAllegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link AllegatoCooperazione }
     * 
     * 
     */
    public List<AllegatoCooperazione> getAllegato() {

	if (allegato == null) {
	    allegato = new ArrayList<AllegatoCooperazione>();
	}
	return this.allegato;
    }

    /**
     * <p>
     * Classe Java per anonymous complex type.
     * 
     * <p>
     * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="versione" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *             &lt;pattern value="(\d+.)?\d+.\d+(-beta)?"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="data" use="required" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class InfoSchema {

	@XmlAttribute(name = "versione", required = true)
	protected String versione;
	@XmlAttribute(name = "data", required = true)
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar data;

	/**
	 * Recupera il valore della proprietà versione.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVersione() {

	    return versione;
	}

	/**
	 * Imposta il valore della proprietà versione.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVersione(String value) {

	    this.versione = value;
	}

	/**
	 * Recupera il valore della proprietà data.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getData() {

	    return data;
	}

	/**
	 * Imposta il valore della proprietà data.
	 * 
	 * @param value
	 *            allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setData(XMLGregorianCalendar value) {

	    this.data = value;
	}
    }

    /**
     * <p>
     * Classe Java per anonymous complex type.
     * 
     * <p>
     * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="suap-competente" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiSuap"/&gt;
     *         &lt;element name="ente-destinatario" type="{http://www.impresainungiorno.gov.it/schema/suap/pratica}EstremiEnte" maxOccurs="unbounded"/&gt;
     *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/suap/ente}RiferimentoPratica"/&gt;
     *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/suap/ente}ComunicazioneEnte"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="progressivo" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *       &lt;attribute name="totale" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "suapCompetente", "enteDestinatario", "codicePratica", "impresa", "oggettoPratica", "protocolloPraticaSuap",
	    "protocolloRi", "oggettoComunicazione", "testoComunicazione", "protocollo" })
    public static class Intestazione {

	@XmlElement(name = "suap-competente", required = true)
	protected EstremiSuap suapCompetente;
	@XmlElement(name = "ente-destinatario", required = true)
	protected List<EstremiEnte> enteDestinatario;
	@XmlElement(name = "codice-pratica", required = true)
	protected String codicePratica;
	@XmlElement(required = true)
	protected AnagraficaImpresa impresa;
	@XmlElement(name = "oggetto-pratica", required = true)
	protected OggettoComunicazione oggettoPratica;
	@XmlElement(name = "protocollo-pratica-suap", required = true)
	protected ProtocolloSUAP protocolloPraticaSuap;
	@XmlElement(name = "protocollo-ri")
	protected ProtocolloRI protocolloRi;
	@XmlElement(name = "oggetto-comunicazione", required = true)
	protected OggettoCooperazione oggettoComunicazione;
	@XmlElement(name = "testo-comunicazione")
	protected String testoComunicazione;
	@XmlElement(required = true)
	protected ProtocolloSUAP protocollo;
	@XmlAttribute(name = "progressivo")
	protected Integer progressivo;
	@XmlAttribute(name = "totale")
	protected Integer totale;

	/**
	 * Recupera il valore della proprietà suapCompetente.
	 * 
	 * @return possible object is {@link EstremiSuap }
	 * 
	 */
	public EstremiSuap getSuapCompetente() {

	    return suapCompetente;
	}

	/**
	 * Imposta il valore della proprietà suapCompetente.
	 * 
	 * @param value
	 *            allowed object is {@link EstremiSuap }
	 * 
	 */
	public void setSuapCompetente(EstremiSuap value) {

	    this.suapCompetente = value;
	}

	/**
	 * Gets the value of the enteDestinatario property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you
	 * make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
	 * method for the enteDestinatario property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getEnteDestinatario().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link EstremiEnte }
	 * 
	 * 
	 */
	public List<EstremiEnte> getEnteDestinatario() {

	    if (enteDestinatario == null) {
		enteDestinatario = new ArrayList<EstremiEnte>();
	    }
	    return this.enteDestinatario;
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
	 * Recupera il valore della proprietà oggettoPratica.
	 * 
	 * @return possible object is {@link OggettoComunicazione }
	 * 
	 */
	public OggettoComunicazione getOggettoPratica() {

	    return oggettoPratica;
	}

	/**
	 * Imposta il valore della proprietà oggettoPratica.
	 * 
	 * @param value
	 *            allowed object is {@link OggettoComunicazione }
	 * 
	 */
	public void setOggettoPratica(OggettoComunicazione value) {

	    this.oggettoPratica = value;
	}

	/**
	 * Recupera il valore della proprietà protocolloPraticaSuap.
	 * 
	 * @return possible object is {@link ProtocolloSUAP }
	 * 
	 */
	public ProtocolloSUAP getProtocolloPraticaSuap() {

	    return protocolloPraticaSuap;
	}

	/**
	 * Imposta il valore della proprietà protocolloPraticaSuap.
	 * 
	 * @param value
	 *            allowed object is {@link ProtocolloSUAP }
	 * 
	 */
	public void setProtocolloPraticaSuap(ProtocolloSUAP value) {

	    this.protocolloPraticaSuap = value;
	}

	/**
	 * Recupera il valore della proprietà protocolloRi.
	 * 
	 * @return possible object is {@link ProtocolloRI }
	 * 
	 */
	public ProtocolloRI getProtocolloRi() {

	    return protocolloRi;
	}

	/**
	 * Imposta il valore della proprietà protocolloRi.
	 * 
	 * @param value
	 *            allowed object is {@link ProtocolloRI }
	 * 
	 */
	public void setProtocolloRi(ProtocolloRI value) {

	    this.protocolloRi = value;
	}

	/**
	 * Recupera il valore della proprietà oggettoComunicazione.
	 * 
	 * @return possible object is {@link OggettoCooperazione }
	 * 
	 */
	public OggettoCooperazione getOggettoComunicazione() {

	    return oggettoComunicazione;
	}

	/**
	 * Imposta il valore della proprietà oggettoComunicazione.
	 * 
	 * @param value
	 *            allowed object is {@link OggettoCooperazione }
	 * 
	 */
	public void setOggettoComunicazione(OggettoCooperazione value) {

	    this.oggettoComunicazione = value;
	}

	/**
	 * Recupera il valore della proprietà testoComunicazione.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTestoComunicazione() {

	    return testoComunicazione;
	}

	/**
	 * Imposta il valore della proprietà testoComunicazione.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTestoComunicazione(String value) {

	    this.testoComunicazione = value;
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
	 * Recupera il valore della proprietà progressivo.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getProgressivo() {

	    return progressivo;
	}

	/**
	 * Imposta il valore della proprietà progressivo.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setProgressivo(Integer value) {

	    this.progressivo = value;
	}

	/**
	 * Recupera il valore della proprietà totale.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getTotale() {

	    return totale;
	}

	/**
	 * Imposta il valore della proprietà totale.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setTotale(Integer value) {

	    this.totale = value;
	}
    }
}
