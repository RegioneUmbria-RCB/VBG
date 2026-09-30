package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

import java.util.List;

public interface IComunicazioneREA {

    /**
     * Gets the value of the soggetti property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoSUAP }
     *     
     */
    SoggettoSUAP getSoggetti();

    /**
     * Sets the value of the soggetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoSUAP }
     *     
     */
    void setSoggetti(SoggettoSUAP value);

    /**
     * Gets the value of the pridPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    String getPridPratica();

    /**
     * Sets the value of the pridPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    void setPridPratica(String value);

    /**
     * Gets the value of the suapReaXml property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoSUAPReaXml }
     *     
     */
    AllegatoSUAPReaXml getSuapReaXml();

    /**
     * Sets the value of the suapReaXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoSUAPReaXml }
     *     
     */
    void setSuapReaXml(AllegatoSUAPReaXml value);

    /**
     * Gets the value of the suapXml property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoSUAPXml }
     *     
     */
    AllegatoSUAPXml getSuapXml();

    /**
     * Sets the value of the suapXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoSUAPXml }
     *     
     */
    void setSuapXml(AllegatoSUAPXml value);

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoSUAP }
     * 
     * 
     */
    List<AllegatoSUAP> getAllegati();

    /**
     * Gets the value of the visuraXML property.
     * 
     */
    boolean isVisuraXML();

    /**
     * Sets the value of the visuraXML property.
     * 
     */
    void setVisuraXML(boolean value);

    /**
     * Gets the value of the visuraPDF property.
     * 
     */
    boolean isVisuraPDF();

    /**
     * Sets the value of the visuraPDF property.
     * 
     */
    void setVisuraPDF(boolean value);
}