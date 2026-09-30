
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiProtocolloLettoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiProtocolloLettoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Warning" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AnnoProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Origine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Classifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Classifica_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoDocumento_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MittenteInterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MittenteInterno_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InCaricoA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InCaricoA_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DocAllegati" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Annullato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotivoAnnullamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataAnnullamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MittentiDestinatari" type="{http://it.gruppoinit/Protocollazione}ArrayOfMittDestOutType" minOccurs="0"/>
 *         &lt;element name="NumeroPratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AnnoNumeroPratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataInserimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroProtocolloMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataProtocolloMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Allegati" type="{http://it.gruppoinit/Protocollazione}ArrayOfAllegatoResponseType" minOccurs="0"/>
 *         &lt;element name="Errore" type="{http://it.gruppoinit/Protocollazione}ErroreProtocolloType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiProtocolloLettoResponseType", propOrder = {
    "warning",
    "idProtocollo",
    "annoProtocollo",
    "numeroProtocollo",
    "dataProtocollo",
    "oggetto",
    "origine",
    "classifica",
    "classificaDescrizione",
    "tipoDocumento",
    "tipoDocumentoDescrizione",
    "mittenteInterno",
    "mittenteInternoDescrizione",
    "inCaricoA",
    "inCaricoADescrizione",
    "docAllegati",
    "annullato",
    "motivoAnnullamento",
    "dataAnnullamento",
    "mittentiDestinatari",
    "numeroPratica",
    "annoNumeroPratica",
    "dataInserimento",
    "numeroProtocolloMittente",
    "dataProtocolloMittente",
    "allegati",
    "errore"
})
public class DatiProtocolloLettoResponseType {

    @XmlElement(name = "Warning", nillable = true)
    protected String warning;
    @XmlElement(name = "IdProtocollo", nillable = true)
    protected String idProtocollo;
    @XmlElement(name = "AnnoProtocollo", nillable = true)
    protected String annoProtocollo;
    @XmlElement(name = "NumeroProtocollo", nillable = true)
    protected String numeroProtocollo;
    @XmlElement(name = "DataProtocollo", nillable = true)
    protected String dataProtocollo;
    @XmlElement(name = "Oggetto", nillable = true)
    protected String oggetto;
    @XmlElement(name = "Origine", nillable = true)
    protected String origine;
    @XmlElement(name = "Classifica", nillable = true)
    protected String classifica;
    @XmlElement(name = "Classifica_Descrizione", nillable = true)
    protected String classificaDescrizione;
    @XmlElement(name = "TipoDocumento", nillable = true)
    protected String tipoDocumento;
    @XmlElement(name = "TipoDocumento_Descrizione", nillable = true)
    protected String tipoDocumentoDescrizione;
    @XmlElement(name = "MittenteInterno", nillable = true)
    protected String mittenteInterno;
    @XmlElement(name = "MittenteInterno_Descrizione", nillable = true)
    protected String mittenteInternoDescrizione;
    @XmlElement(name = "InCaricoA", nillable = true)
    protected String inCaricoA;
    @XmlElement(name = "InCaricoA_Descrizione", nillable = true)
    protected String inCaricoADescrizione;
    @XmlElement(name = "DocAllegati", nillable = true)
    protected String docAllegati;
    @XmlElement(name = "Annullato", nillable = true)
    protected String annullato;
    @XmlElement(name = "MotivoAnnullamento", nillable = true)
    protected String motivoAnnullamento;
    @XmlElement(name = "DataAnnullamento", nillable = true)
    protected String dataAnnullamento;
    @XmlElement(name = "MittentiDestinatari", nillable = true)
    protected ArrayOfMittDestOutType mittentiDestinatari;
    @XmlElement(name = "NumeroPratica", nillable = true)
    protected String numeroPratica;
    @XmlElement(name = "AnnoNumeroPratica", nillable = true)
    protected String annoNumeroPratica;
    @XmlElement(name = "DataInserimento", nillable = true)
    protected String dataInserimento;
    @XmlElement(name = "NumeroProtocolloMittente", nillable = true)
    protected String numeroProtocolloMittente;
    @XmlElement(name = "DataProtocolloMittente", nillable = true)
    protected String dataProtocolloMittente;
    @XmlElement(name = "Allegati", nillable = true)
    protected ArrayOfAllegatoResponseType allegati;
    @XmlElement(name = "Errore", nillable = true)
    protected ErroreProtocolloType errore;

    /**
     * Gets the value of the warning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarning() {
        return warning;
    }

    /**
     * Sets the value of the warning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarning(String value) {
        this.warning = value;
    }

    /**
     * Gets the value of the idProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProtocollo() {
        return idProtocollo;
    }

    /**
     * Sets the value of the idProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProtocollo(String value) {
        this.idProtocollo = value;
    }

    /**
     * Gets the value of the annoProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoProtocollo() {
        return annoProtocollo;
    }

    /**
     * Sets the value of the annoProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoProtocollo(String value) {
        this.annoProtocollo = value;
    }

    /**
     * Gets the value of the numeroProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Sets the value of the numeroProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocollo(String value) {
        this.numeroProtocollo = value;
    }

    /**
     * Gets the value of the dataProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Sets the value of the dataProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocollo(String value) {
        this.dataProtocollo = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the origine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigine() {
        return origine;
    }

    /**
     * Sets the value of the origine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigine(String value) {
        this.origine = value;
    }

    /**
     * Gets the value of the classifica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassifica() {
        return classifica;
    }

    /**
     * Sets the value of the classifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassifica(String value) {
        this.classifica = value;
    }

    /**
     * Gets the value of the classificaDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassificaDescrizione() {
        return classificaDescrizione;
    }

    /**
     * Sets the value of the classificaDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassificaDescrizione(String value) {
        this.classificaDescrizione = value;
    }

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the tipoDocumentoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumentoDescrizione() {
        return tipoDocumentoDescrizione;
    }

    /**
     * Sets the value of the tipoDocumentoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumentoDescrizione(String value) {
        this.tipoDocumentoDescrizione = value;
    }

    /**
     * Gets the value of the mittenteInterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMittenteInterno() {
        return mittenteInterno;
    }

    /**
     * Sets the value of the mittenteInterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMittenteInterno(String value) {
        this.mittenteInterno = value;
    }

    /**
     * Gets the value of the mittenteInternoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMittenteInternoDescrizione() {
        return mittenteInternoDescrizione;
    }

    /**
     * Sets the value of the mittenteInternoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMittenteInternoDescrizione(String value) {
        this.mittenteInternoDescrizione = value;
    }

    /**
     * Gets the value of the inCaricoA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInCaricoA() {
        return inCaricoA;
    }

    /**
     * Sets the value of the inCaricoA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInCaricoA(String value) {
        this.inCaricoA = value;
    }

    /**
     * Gets the value of the inCaricoADescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInCaricoADescrizione() {
        return inCaricoADescrizione;
    }

    /**
     * Sets the value of the inCaricoADescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInCaricoADescrizione(String value) {
        this.inCaricoADescrizione = value;
    }

    /**
     * Gets the value of the docAllegati property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocAllegati() {
        return docAllegati;
    }

    /**
     * Sets the value of the docAllegati property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocAllegati(String value) {
        this.docAllegati = value;
    }

    /**
     * Gets the value of the annullato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnullato() {
        return annullato;
    }

    /**
     * Sets the value of the annullato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnullato(String value) {
        this.annullato = value;
    }

    /**
     * Gets the value of the motivoAnnullamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoAnnullamento() {
        return motivoAnnullamento;
    }

    /**
     * Sets the value of the motivoAnnullamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoAnnullamento(String value) {
        this.motivoAnnullamento = value;
    }

    /**
     * Gets the value of the dataAnnullamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataAnnullamento() {
        return dataAnnullamento;
    }

    /**
     * Sets the value of the dataAnnullamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataAnnullamento(String value) {
        this.dataAnnullamento = value;
    }

    /**
     * Gets the value of the mittentiDestinatari property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMittDestOutType }
     *     
     */
    public ArrayOfMittDestOutType getMittentiDestinatari() {
        return mittentiDestinatari;
    }

    /**
     * Sets the value of the mittentiDestinatari property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMittDestOutType }
     *     
     */
    public void setMittentiDestinatari(ArrayOfMittDestOutType value) {
        this.mittentiDestinatari = value;
    }

    /**
     * Gets the value of the numeroPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPratica() {
        return numeroPratica;
    }

    /**
     * Sets the value of the numeroPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPratica(String value) {
        this.numeroPratica = value;
    }

    /**
     * Gets the value of the annoNumeroPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoNumeroPratica() {
        return annoNumeroPratica;
    }

    /**
     * Sets the value of the annoNumeroPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoNumeroPratica(String value) {
        this.annoNumeroPratica = value;
    }

    /**
     * Gets the value of the dataInserimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInserimento() {
        return dataInserimento;
    }

    /**
     * Sets the value of the dataInserimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInserimento(String value) {
        this.dataInserimento = value;
    }

    /**
     * Gets the value of the numeroProtocolloMittente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolloMittente() {
        return numeroProtocolloMittente;
    }

    /**
     * Sets the value of the numeroProtocolloMittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolloMittente(String value) {
        this.numeroProtocolloMittente = value;
    }

    /**
     * Gets the value of the dataProtocolloMittente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocolloMittente() {
        return dataProtocolloMittente;
    }

    /**
     * Sets the value of the dataProtocolloMittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocolloMittente(String value) {
        this.dataProtocolloMittente = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAllegatoResponseType }
     *     
     */
    public ArrayOfAllegatoResponseType getAllegati() {
        return allegati;
    }

    /**
     * Sets the value of the allegati property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAllegatoResponseType }
     *     
     */
    public void setAllegati(ArrayOfAllegatoResponseType value) {
        this.allegati = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public ErroreProtocolloType getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public void setErrore(ErroreProtocolloType value) {
        this.errore = value;
    }

}
