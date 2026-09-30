
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AttoOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttoOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdDocumento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoDocumento_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Classifica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Classifica_Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataDocumento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="NumeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Conservabile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Proposta" type="{http://tempuri.org/}PropostaOut" minOccurs="0"/>
 *         &lt;element name="Delibera" type="{http://tempuri.org/}DeliberaOut" minOccurs="0"/>
 *         &lt;element name="Determina" type="{http://tempuri.org/}DeterminaOut" minOccurs="0"/>
 *         &lt;element name="Allegati" type="{http://tempuri.org/}ArrayOfAllegatoOut" minOccurs="0"/>
 *         &lt;element name="Impegni" type="{http://tempuri.org/}ArrayOfImpegnoOut" minOccurs="0"/>
 *         &lt;element name="Accertamenti" type="{http://tempuri.org/}ArrayOfAccertamentoOut" minOccurs="0"/>
 *         &lt;element name="CentriDiCosto" type="{http://tempuri.org/}ArrayOfCentriDiCostoOut" minOccurs="0"/>
 *         &lt;element name="Registri" type="{http://tempuri.org/}ArrayOfRegistroAssegnatoOut" minOccurs="0"/>
 *         &lt;element name="Messaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Errore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datiUtente" type="{http://tempuri.org/}ArrayOfTabellaUtente"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttoOut", propOrder = {
    "idDocumento",
    "tipoDocumento",
    "tipoDocumentoDescrizione",
    "oggetto",
    "classifica",
    "classificaDescrizione",
    "dataDocumento",
    "numeroProtocollo",
    "conservabile",
    "proposta",
    "delibera",
    "determina",
    "allegati",
    "impegni",
    "accertamenti",
    "centriDiCosto",
    "registri",
    "messaggio",
    "errore",
    "datiUtente"
})
public class AttoOut {

    @XmlElement(name = "IdDocumento")
    protected int idDocumento;
    @XmlElementRef(name = "TipoDocumento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> tipoDocumento;
    @XmlElementRef(name = "TipoDocumento_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> tipoDocumentoDescrizione;
    @XmlElementRef(name = "Oggetto", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> oggetto;
    @XmlElementRef(name = "Classifica", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> classifica;
    @XmlElementRef(name = "Classifica_Descrizione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> classificaDescrizione;
    @XmlElement(name = "DataDocumento", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDocumento;
    @XmlElementRef(name = "NumeroProtocollo", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> numeroProtocollo;
    @XmlElementRef(name = "Conservabile", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> conservabile;
    @XmlElementRef(name = "Proposta", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<PropostaOut> proposta;
    @XmlElementRef(name = "Delibera", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<DeliberaOut> delibera;
    @XmlElementRef(name = "Determina", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<DeterminaOut> determina;
    @XmlElementRef(name = "Allegati", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfAllegatoOut> allegati;
    @XmlElementRef(name = "Impegni", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfImpegnoOut> impegni;
    @XmlElementRef(name = "Accertamenti", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfAccertamentoOut> accertamenti;
    @XmlElementRef(name = "CentriDiCosto", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfCentriDiCostoOut> centriDiCosto;
    @XmlElementRef(name = "Registri", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfRegistroAssegnatoOut> registri;
    @XmlElementRef(name = "Messaggio", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> messaggio;
    @XmlElementRef(name = "Errore", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> errore;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfTabellaUtente datiUtente;

    /**
     * Gets the value of the idDocumento property.
     * 
     */
    public int getIdDocumento() {
        return idDocumento;
    }

    /**
     * Sets the value of the idDocumento property.
     * 
     */
    public void setIdDocumento(int value) {
        this.idDocumento = value;
    }

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoDocumento(JAXBElement<String> value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the tipoDocumentoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoDocumentoDescrizione() {
        return tipoDocumentoDescrizione;
    }

    /**
     * Sets the value of the tipoDocumentoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoDocumentoDescrizione(JAXBElement<String> value) {
        this.tipoDocumentoDescrizione = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOggetto(JAXBElement<String> value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the classifica property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClassifica() {
        return classifica;
    }

    /**
     * Sets the value of the classifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClassifica(JAXBElement<String> value) {
        this.classifica = value;
    }

    /**
     * Gets the value of the classificaDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClassificaDescrizione() {
        return classificaDescrizione;
    }

    /**
     * Sets the value of the classificaDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClassificaDescrizione(JAXBElement<String> value) {
        this.classificaDescrizione = value;
    }

    /**
     * Gets the value of the dataDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDocumento() {
        return dataDocumento;
    }

    /**
     * Sets the value of the dataDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDocumento(XMLGregorianCalendar value) {
        this.dataDocumento = value;
    }

    /**
     * Gets the value of the numeroProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Sets the value of the numeroProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNumeroProtocollo(JAXBElement<String> value) {
        this.numeroProtocollo = value;
    }

    /**
     * Gets the value of the conservabile property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getConservabile() {
        return conservabile;
    }

    /**
     * Sets the value of the conservabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setConservabile(JAXBElement<String> value) {
        this.conservabile = value;
    }

    /**
     * Gets the value of the proposta property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PropostaOut }{@code >}
     *     
     */
    public JAXBElement<PropostaOut> getProposta() {
        return proposta;
    }

    /**
     * Sets the value of the proposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PropostaOut }{@code >}
     *     
     */
    public void setProposta(JAXBElement<PropostaOut> value) {
        this.proposta = value;
    }

    /**
     * Gets the value of the delibera property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DeliberaOut }{@code >}
     *     
     */
    public JAXBElement<DeliberaOut> getDelibera() {
        return delibera;
    }

    /**
     * Sets the value of the delibera property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DeliberaOut }{@code >}
     *     
     */
    public void setDelibera(JAXBElement<DeliberaOut> value) {
        this.delibera = value;
    }

    /**
     * Gets the value of the determina property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DeterminaOut }{@code >}
     *     
     */
    public JAXBElement<DeterminaOut> getDetermina() {
        return determina;
    }

    /**
     * Sets the value of the determina property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DeterminaOut }{@code >}
     *     
     */
    public void setDetermina(JAXBElement<DeterminaOut> value) {
        this.determina = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfAllegatoOut }{@code >}
     *     
     */
    public JAXBElement<ArrayOfAllegatoOut> getAllegati() {
        return allegati;
    }

    /**
     * Sets the value of the allegati property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfAllegatoOut }{@code >}
     *     
     */
    public void setAllegati(JAXBElement<ArrayOfAllegatoOut> value) {
        this.allegati = value;
    }

    /**
     * Gets the value of the impegni property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfImpegnoOut }{@code >}
     *     
     */
    public JAXBElement<ArrayOfImpegnoOut> getImpegni() {
        return impegni;
    }

    /**
     * Sets the value of the impegni property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfImpegnoOut }{@code >}
     *     
     */
    public void setImpegni(JAXBElement<ArrayOfImpegnoOut> value) {
        this.impegni = value;
    }

    /**
     * Gets the value of the accertamenti property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfAccertamentoOut }{@code >}
     *     
     */
    public JAXBElement<ArrayOfAccertamentoOut> getAccertamenti() {
        return accertamenti;
    }

    /**
     * Sets the value of the accertamenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfAccertamentoOut }{@code >}
     *     
     */
    public void setAccertamenti(JAXBElement<ArrayOfAccertamentoOut> value) {
        this.accertamenti = value;
    }

    /**
     * Gets the value of the centriDiCosto property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfCentriDiCostoOut }{@code >}
     *     
     */
    public JAXBElement<ArrayOfCentriDiCostoOut> getCentriDiCosto() {
        return centriDiCosto;
    }

    /**
     * Sets the value of the centriDiCosto property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfCentriDiCostoOut }{@code >}
     *     
     */
    public void setCentriDiCosto(JAXBElement<ArrayOfCentriDiCostoOut> value) {
        this.centriDiCosto = value;
    }

    /**
     * Gets the value of the registri property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRegistroAssegnatoOut }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRegistroAssegnatoOut> getRegistri() {
        return registri;
    }

    /**
     * Sets the value of the registri property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRegistroAssegnatoOut }{@code >}
     *     
     */
    public void setRegistri(JAXBElement<ArrayOfRegistroAssegnatoOut> value) {
        this.registri = value;
    }

    /**
     * Gets the value of the messaggio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMessaggio() {
        return messaggio;
    }

    /**
     * Sets the value of the messaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMessaggio(JAXBElement<String> value) {
        this.messaggio = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setErrore(JAXBElement<String> value) {
        this.errore = value;
    }

    /**
     * Gets the value of the datiUtente property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTabellaUtente }
     *     
     */
    public ArrayOfTabellaUtente getDatiUtente() {
        return datiUtente;
    }

    /**
     * Sets the value of the datiUtente property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTabellaUtente }
     *     
     */
    public void setDatiUtente(ArrayOfTabellaUtente value) {
        this.datiUtente = value;
    }

}
