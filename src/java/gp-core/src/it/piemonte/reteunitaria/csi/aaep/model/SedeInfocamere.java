
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SedeInfocamere complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SedeInfocamere">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listaDenAttivIncoc" type="{urn:AAEPCSI}ArrayOfDenAttivInfoc"/>
 *         &lt;element name="listaAlboArtgInfoc" type="{urn:AAEPCSI}ArrayOfAlboArtigInfoc"/>
 *         &lt;element name="codToponimoUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="viaUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataAperturaUL" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="codCausaleCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoUL5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoUL4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numTelef" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoUL3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoUL2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoUL1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDenunciaCessaz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="altreIndcazUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codStradarioUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="capUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProvCCIAA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataCessaz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="listaAtecoRI2007Infoc" type="{urn:AAEPCSI}ArrayOfAtecoRI2007Infoc"/>
 *         &lt;element name="superfVendita" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="listaAlboRuoloInfoc" type="{urn:AAEPCSI}ArrayOfAlboRuoloInfoc"/>
 *         &lt;element name="listaAttivInfoc" type="{urn:AAEPCSI}ArrayOfAttivitaInfoc"/>
 *         &lt;element name="descrStatoEsteroUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="frazioneUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoLocalizzazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataDenuncia" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="listaAttIstatInfoc" type="{urn:AAEPCSI}ArrayOfAttivIstatInfoc"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrSettMerceologico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrComuneUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaAtecoRI1991Infoc" type="{urn:AAEPCSI}ArrayOfAtecoRI1991Infoc"/>
 *         &lt;element name="progrSede" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codComuneUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="denominazioneUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrLocalizzazione" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codSettMerceologico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numCivicoUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codStatoEsteroUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaAtecoRI2002Infoc" type="{urn:AAEPCSI}ArrayOfAtecoRI2002Infoc"/>
 *         &lt;element name="descrToponimoUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrCausaleCessaz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numTelefax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoUL5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoUL4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoUL3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoUL2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoUL1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codTipoLocalizzazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numIscrizREA" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrStradarioUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="insegnaUL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numReaFuoriProv" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SedeInfocamere", propOrder = {
    "listaDenAttivIncoc",
    "listaAlboArtgInfoc",
    "codToponimoUL",
    "viaUL",
    "siglaProvUL",
    "dataAperturaUL",
    "codCausaleCessaz",
    "descrTipoUL5",
    "descrTipoUL4",
    "numTelef",
    "descrTipoUL3",
    "descrTipoUL2",
    "descrTipoUL1",
    "dataDenunciaCessaz",
    "idAAEPFonteDato",
    "altreIndcazUL",
    "codStradarioUL",
    "capUL",
    "siglaProvCCIAA",
    "dataCessaz",
    "listaAtecoRI2007Infoc",
    "superfVendita",
    "listaAlboRuoloInfoc",
    "listaAttivInfoc",
    "descrStatoEsteroUL",
    "frazioneUL",
    "descrTipoLocalizzazione",
    "dataDenuncia",
    "listaAttIstatInfoc",
    "idAAEPAzienda",
    "descrSettMerceologico",
    "descrComuneUL",
    "listaAtecoRI1991Infoc",
    "progrSede",
    "codComuneUL",
    "denominazioneUL",
    "progrLocalizzazione",
    "codSettMerceologico",
    "numCivicoUL",
    "codStatoEsteroUL",
    "listaAtecoRI2002Infoc",
    "descrToponimoUL",
    "descrCausaleCessaz",
    "numTelefax",
    "codTipoUL5",
    "codTipoUL4",
    "codTipoUL3",
    "codTipoUL2",
    "codTipoUL1",
    "codTipoLocalizzazione",
    "numIscrizREA",
    "descrStradarioUL",
    "insegnaUL",
    "numReaFuoriProv"
})
public class SedeInfocamere {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfDenAttivInfoc listaDenAttivIncoc;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfAlboArtigInfoc listaAlboArtgInfoc;
    @XmlElement(required = true, nillable = true)
    protected String codToponimoUL;
    @XmlElement(required = true, nillable = true)
    protected String viaUL;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvUL;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAperturaUL;
    @XmlElement(required = true, nillable = true)
    protected String codCausaleCessaz;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoUL5;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoUL4;
    @XmlElement(required = true, nillable = true)
    protected String numTelef;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoUL3;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoUL2;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoUL1;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDenunciaCessaz;
    protected long idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String altreIndcazUL;
    @XmlElement(required = true, nillable = true)
    protected String codStradarioUL;
    @XmlElement(required = true, nillable = true)
    protected String capUL;
    @XmlElement(required = true, nillable = true)
    protected String siglaProvCCIAA;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCessaz;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfAtecoRI2007Infoc listaAtecoRI2007Infoc;
    protected long superfVendita;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfAlboRuoloInfoc listaAlboRuoloInfoc;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfAttivitaInfoc listaAttivInfoc;
    @XmlElement(required = true, nillable = true)
    protected String descrStatoEsteroUL;
    @XmlElement(required = true, nillable = true)
    protected String frazioneUL;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoLocalizzazione;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDenuncia;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfAttivIstatInfoc listaAttIstatInfoc;
    protected long idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String descrSettMerceologico;
    @XmlElement(required = true, nillable = true)
    protected String descrComuneUL;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfAtecoRI1991Infoc listaAtecoRI1991Infoc;
    protected long progrSede;
    @XmlElement(required = true, nillable = true)
    protected String codComuneUL;
    @XmlElement(required = true, nillable = true)
    protected String denominazioneUL;
    protected long progrLocalizzazione;
    @XmlElement(required = true, nillable = true)
    protected String codSettMerceologico;
    @XmlElement(required = true, nillable = true)
    protected String numCivicoUL;
    @XmlElement(required = true, nillable = true)
    protected String codStatoEsteroUL;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfAtecoRI2002Infoc listaAtecoRI2002Infoc;
    @XmlElement(required = true, nillable = true)
    protected String descrToponimoUL;
    @XmlElement(required = true, nillable = true)
    protected String descrCausaleCessaz;
    @XmlElement(required = true, nillable = true)
    protected String numTelefax;
    @XmlElement(required = true, nillable = true)
    protected String codTipoUL5;
    @XmlElement(required = true, nillable = true)
    protected String codTipoUL4;
    @XmlElement(required = true, nillable = true)
    protected String codTipoUL3;
    @XmlElement(required = true, nillable = true)
    protected String codTipoUL2;
    @XmlElement(required = true, nillable = true)
    protected String codTipoUL1;
    @XmlElement(required = true, nillable = true)
    protected String codTipoLocalizzazione;
    protected long numIscrizREA;
    @XmlElement(required = true, nillable = true)
    protected String descrStradarioUL;
    @XmlElement(required = true, nillable = true)
    protected String insegnaUL;
    protected long numReaFuoriProv;

    /**
     * Gets the value of the listaDenAttivIncoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDenAttivInfoc }
     *     
     */
    public ArrayOfDenAttivInfoc getListaDenAttivIncoc() {
        return listaDenAttivIncoc;
    }

    /**
     * Sets the value of the listaDenAttivIncoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDenAttivInfoc }
     *     
     */
    public void setListaDenAttivIncoc(ArrayOfDenAttivInfoc value) {
        this.listaDenAttivIncoc = value;
    }

    /**
     * Gets the value of the listaAlboArtgInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlboArtigInfoc }
     *     
     */
    public ArrayOfAlboArtigInfoc getListaAlboArtgInfoc() {
        return listaAlboArtgInfoc;
    }

    /**
     * Sets the value of the listaAlboArtgInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlboArtigInfoc }
     *     
     */
    public void setListaAlboArtgInfoc(ArrayOfAlboArtigInfoc value) {
        this.listaAlboArtgInfoc = value;
    }

    /**
     * Gets the value of the codToponimoUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodToponimoUL() {
        return codToponimoUL;
    }

    /**
     * Sets the value of the codToponimoUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodToponimoUL(String value) {
        this.codToponimoUL = value;
    }

    /**
     * Gets the value of the viaUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViaUL() {
        return viaUL;
    }

    /**
     * Sets the value of the viaUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViaUL(String value) {
        this.viaUL = value;
    }

    /**
     * Gets the value of the siglaProvUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvUL() {
        return siglaProvUL;
    }

    /**
     * Sets the value of the siglaProvUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvUL(String value) {
        this.siglaProvUL = value;
    }

    /**
     * Gets the value of the dataAperturaUL property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAperturaUL() {
        return dataAperturaUL;
    }

    /**
     * Sets the value of the dataAperturaUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAperturaUL(XMLGregorianCalendar value) {
        this.dataAperturaUL = value;
    }

    /**
     * Gets the value of the codCausaleCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCausaleCessaz() {
        return codCausaleCessaz;
    }

    /**
     * Sets the value of the codCausaleCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCausaleCessaz(String value) {
        this.codCausaleCessaz = value;
    }

    /**
     * Gets the value of the descrTipoUL5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoUL5() {
        return descrTipoUL5;
    }

    /**
     * Sets the value of the descrTipoUL5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoUL5(String value) {
        this.descrTipoUL5 = value;
    }

    /**
     * Gets the value of the descrTipoUL4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoUL4() {
        return descrTipoUL4;
    }

    /**
     * Sets the value of the descrTipoUL4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoUL4(String value) {
        this.descrTipoUL4 = value;
    }

    /**
     * Gets the value of the numTelef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelef() {
        return numTelef;
    }

    /**
     * Sets the value of the numTelef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelef(String value) {
        this.numTelef = value;
    }

    /**
     * Gets the value of the descrTipoUL3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoUL3() {
        return descrTipoUL3;
    }

    /**
     * Sets the value of the descrTipoUL3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoUL3(String value) {
        this.descrTipoUL3 = value;
    }

    /**
     * Gets the value of the descrTipoUL2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoUL2() {
        return descrTipoUL2;
    }

    /**
     * Sets the value of the descrTipoUL2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoUL2(String value) {
        this.descrTipoUL2 = value;
    }

    /**
     * Gets the value of the descrTipoUL1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoUL1() {
        return descrTipoUL1;
    }

    /**
     * Sets the value of the descrTipoUL1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoUL1(String value) {
        this.descrTipoUL1 = value;
    }

    /**
     * Gets the value of the dataDenunciaCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDenunciaCessaz() {
        return dataDenunciaCessaz;
    }

    /**
     * Sets the value of the dataDenunciaCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDenunciaCessaz(XMLGregorianCalendar value) {
        this.dataDenunciaCessaz = value;
    }

    /**
     * Gets the value of the idAAEPFonteDato property.
     * 
     */
    public long getIdAAEPFonteDato() {
        return idAAEPFonteDato;
    }

    /**
     * Sets the value of the idAAEPFonteDato property.
     * 
     */
    public void setIdAAEPFonteDato(long value) {
        this.idAAEPFonteDato = value;
    }

    /**
     * Gets the value of the altreIndcazUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltreIndcazUL() {
        return altreIndcazUL;
    }

    /**
     * Sets the value of the altreIndcazUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltreIndcazUL(String value) {
        this.altreIndcazUL = value;
    }

    /**
     * Gets the value of the codStradarioUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStradarioUL() {
        return codStradarioUL;
    }

    /**
     * Sets the value of the codStradarioUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStradarioUL(String value) {
        this.codStradarioUL = value;
    }

    /**
     * Gets the value of the capUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapUL() {
        return capUL;
    }

    /**
     * Sets the value of the capUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapUL(String value) {
        this.capUL = value;
    }

    /**
     * Gets the value of the siglaProvCCIAA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvCCIAA() {
        return siglaProvCCIAA;
    }

    /**
     * Sets the value of the siglaProvCCIAA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvCCIAA(String value) {
        this.siglaProvCCIAA = value;
    }

    /**
     * Gets the value of the dataCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCessaz() {
        return dataCessaz;
    }

    /**
     * Sets the value of the dataCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCessaz(XMLGregorianCalendar value) {
        this.dataCessaz = value;
    }

    /**
     * Gets the value of the listaAtecoRI2007Infoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAtecoRI2007Infoc }
     *     
     */
    public ArrayOfAtecoRI2007Infoc getListaAtecoRI2007Infoc() {
        return listaAtecoRI2007Infoc;
    }

    /**
     * Sets the value of the listaAtecoRI2007Infoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAtecoRI2007Infoc }
     *     
     */
    public void setListaAtecoRI2007Infoc(ArrayOfAtecoRI2007Infoc value) {
        this.listaAtecoRI2007Infoc = value;
    }

    /**
     * Gets the value of the superfVendita property.
     * 
     */
    public long getSuperfVendita() {
        return superfVendita;
    }

    /**
     * Sets the value of the superfVendita property.
     * 
     */
    public void setSuperfVendita(long value) {
        this.superfVendita = value;
    }

    /**
     * Gets the value of the listaAlboRuoloInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlboRuoloInfoc }
     *     
     */
    public ArrayOfAlboRuoloInfoc getListaAlboRuoloInfoc() {
        return listaAlboRuoloInfoc;
    }

    /**
     * Sets the value of the listaAlboRuoloInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlboRuoloInfoc }
     *     
     */
    public void setListaAlboRuoloInfoc(ArrayOfAlboRuoloInfoc value) {
        this.listaAlboRuoloInfoc = value;
    }

    /**
     * Gets the value of the listaAttivInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAttivitaInfoc }
     *     
     */
    public ArrayOfAttivitaInfoc getListaAttivInfoc() {
        return listaAttivInfoc;
    }

    /**
     * Sets the value of the listaAttivInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAttivitaInfoc }
     *     
     */
    public void setListaAttivInfoc(ArrayOfAttivitaInfoc value) {
        this.listaAttivInfoc = value;
    }

    /**
     * Gets the value of the descrStatoEsteroUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStatoEsteroUL() {
        return descrStatoEsteroUL;
    }

    /**
     * Sets the value of the descrStatoEsteroUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStatoEsteroUL(String value) {
        this.descrStatoEsteroUL = value;
    }

    /**
     * Gets the value of the frazioneUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrazioneUL() {
        return frazioneUL;
    }

    /**
     * Sets the value of the frazioneUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrazioneUL(String value) {
        this.frazioneUL = value;
    }

    /**
     * Gets the value of the descrTipoLocalizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoLocalizzazione() {
        return descrTipoLocalizzazione;
    }

    /**
     * Sets the value of the descrTipoLocalizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoLocalizzazione(String value) {
        this.descrTipoLocalizzazione = value;
    }

    /**
     * Gets the value of the dataDenuncia property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDenuncia() {
        return dataDenuncia;
    }

    /**
     * Sets the value of the dataDenuncia property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDenuncia(XMLGregorianCalendar value) {
        this.dataDenuncia = value;
    }

    /**
     * Gets the value of the listaAttIstatInfoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAttivIstatInfoc }
     *     
     */
    public ArrayOfAttivIstatInfoc getListaAttIstatInfoc() {
        return listaAttIstatInfoc;
    }

    /**
     * Sets the value of the listaAttIstatInfoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAttivIstatInfoc }
     *     
     */
    public void setListaAttIstatInfoc(ArrayOfAttivIstatInfoc value) {
        this.listaAttIstatInfoc = value;
    }

    /**
     * Gets the value of the idAAEPAzienda property.
     * 
     */
    public long getIdAAEPAzienda() {
        return idAAEPAzienda;
    }

    /**
     * Sets the value of the idAAEPAzienda property.
     * 
     */
    public void setIdAAEPAzienda(long value) {
        this.idAAEPAzienda = value;
    }

    /**
     * Gets the value of the descrSettMerceologico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrSettMerceologico() {
        return descrSettMerceologico;
    }

    /**
     * Sets the value of the descrSettMerceologico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrSettMerceologico(String value) {
        this.descrSettMerceologico = value;
    }

    /**
     * Gets the value of the descrComuneUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrComuneUL() {
        return descrComuneUL;
    }

    /**
     * Sets the value of the descrComuneUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrComuneUL(String value) {
        this.descrComuneUL = value;
    }

    /**
     * Gets the value of the listaAtecoRI1991Infoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAtecoRI1991Infoc }
     *     
     */
    public ArrayOfAtecoRI1991Infoc getListaAtecoRI1991Infoc() {
        return listaAtecoRI1991Infoc;
    }

    /**
     * Sets the value of the listaAtecoRI1991Infoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAtecoRI1991Infoc }
     *     
     */
    public void setListaAtecoRI1991Infoc(ArrayOfAtecoRI1991Infoc value) {
        this.listaAtecoRI1991Infoc = value;
    }

    /**
     * Gets the value of the progrSede property.
     * 
     */
    public long getProgrSede() {
        return progrSede;
    }

    /**
     * Sets the value of the progrSede property.
     * 
     */
    public void setProgrSede(long value) {
        this.progrSede = value;
    }

    /**
     * Gets the value of the codComuneUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodComuneUL() {
        return codComuneUL;
    }

    /**
     * Sets the value of the codComuneUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodComuneUL(String value) {
        this.codComuneUL = value;
    }

    /**
     * Gets the value of the denominazioneUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneUL() {
        return denominazioneUL;
    }

    /**
     * Sets the value of the denominazioneUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneUL(String value) {
        this.denominazioneUL = value;
    }

    /**
     * Gets the value of the progrLocalizzazione property.
     * 
     */
    public long getProgrLocalizzazione() {
        return progrLocalizzazione;
    }

    /**
     * Sets the value of the progrLocalizzazione property.
     * 
     */
    public void setProgrLocalizzazione(long value) {
        this.progrLocalizzazione = value;
    }

    /**
     * Gets the value of the codSettMerceologico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodSettMerceologico() {
        return codSettMerceologico;
    }

    /**
     * Sets the value of the codSettMerceologico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodSettMerceologico(String value) {
        this.codSettMerceologico = value;
    }

    /**
     * Gets the value of the numCivicoUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCivicoUL() {
        return numCivicoUL;
    }

    /**
     * Sets the value of the numCivicoUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCivicoUL(String value) {
        this.numCivicoUL = value;
    }

    /**
     * Gets the value of the codStatoEsteroUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStatoEsteroUL() {
        return codStatoEsteroUL;
    }

    /**
     * Sets the value of the codStatoEsteroUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStatoEsteroUL(String value) {
        this.codStatoEsteroUL = value;
    }

    /**
     * Gets the value of the listaAtecoRI2002Infoc property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAtecoRI2002Infoc }
     *     
     */
    public ArrayOfAtecoRI2002Infoc getListaAtecoRI2002Infoc() {
        return listaAtecoRI2002Infoc;
    }

    /**
     * Sets the value of the listaAtecoRI2002Infoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAtecoRI2002Infoc }
     *     
     */
    public void setListaAtecoRI2002Infoc(ArrayOfAtecoRI2002Infoc value) {
        this.listaAtecoRI2002Infoc = value;
    }

    /**
     * Gets the value of the descrToponimoUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrToponimoUL() {
        return descrToponimoUL;
    }

    /**
     * Sets the value of the descrToponimoUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrToponimoUL(String value) {
        this.descrToponimoUL = value;
    }

    /**
     * Gets the value of the descrCausaleCessaz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCausaleCessaz() {
        return descrCausaleCessaz;
    }

    /**
     * Sets the value of the descrCausaleCessaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCausaleCessaz(String value) {
        this.descrCausaleCessaz = value;
    }

    /**
     * Gets the value of the numTelefax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelefax() {
        return numTelefax;
    }

    /**
     * Sets the value of the numTelefax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelefax(String value) {
        this.numTelefax = value;
    }

    /**
     * Gets the value of the codTipoUL5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoUL5() {
        return codTipoUL5;
    }

    /**
     * Sets the value of the codTipoUL5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoUL5(String value) {
        this.codTipoUL5 = value;
    }

    /**
     * Gets the value of the codTipoUL4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoUL4() {
        return codTipoUL4;
    }

    /**
     * Sets the value of the codTipoUL4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoUL4(String value) {
        this.codTipoUL4 = value;
    }

    /**
     * Gets the value of the codTipoUL3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoUL3() {
        return codTipoUL3;
    }

    /**
     * Sets the value of the codTipoUL3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoUL3(String value) {
        this.codTipoUL3 = value;
    }

    /**
     * Gets the value of the codTipoUL2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoUL2() {
        return codTipoUL2;
    }

    /**
     * Sets the value of the codTipoUL2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoUL2(String value) {
        this.codTipoUL2 = value;
    }

    /**
     * Gets the value of the codTipoUL1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoUL1() {
        return codTipoUL1;
    }

    /**
     * Sets the value of the codTipoUL1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoUL1(String value) {
        this.codTipoUL1 = value;
    }

    /**
     * Gets the value of the codTipoLocalizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodTipoLocalizzazione() {
        return codTipoLocalizzazione;
    }

    /**
     * Sets the value of the codTipoLocalizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodTipoLocalizzazione(String value) {
        this.codTipoLocalizzazione = value;
    }

    /**
     * Gets the value of the numIscrizREA property.
     * 
     */
    public long getNumIscrizREA() {
        return numIscrizREA;
    }

    /**
     * Sets the value of the numIscrizREA property.
     * 
     */
    public void setNumIscrizREA(long value) {
        this.numIscrizREA = value;
    }

    /**
     * Gets the value of the descrStradarioUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrStradarioUL() {
        return descrStradarioUL;
    }

    /**
     * Sets the value of the descrStradarioUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrStradarioUL(String value) {
        this.descrStradarioUL = value;
    }

    /**
     * Gets the value of the insegnaUL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsegnaUL() {
        return insegnaUL;
    }

    /**
     * Sets the value of the insegnaUL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsegnaUL(String value) {
        this.insegnaUL = value;
    }

    /**
     * Gets the value of the numReaFuoriProv property.
     * 
     */
    public long getNumReaFuoriProv() {
        return numReaFuoriProv;
    }

    /**
     * Sets the value of the numReaFuoriProv property.
     * 
     */
    public void setNumReaFuoriProv(long value) {
        this.numReaFuoriProv = value;
    }

}
