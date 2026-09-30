
package it.phoops.people.hostingtemporaneo.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for richiestaCancellazioneFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="richiestaCancellazioneFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uris" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="uri" type="{http://service.hostingtemporaneo.people.phoops.it/}fileURI" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "richiestaCancellazioneFile", propOrder = {
    "uris"
})
public class RichiestaCancellazioneFile {

    protected RichiestaCancellazioneFile.Uris uris;

    /**
     * Gets the value of the uris property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaCancellazioneFile.Uris }
     *     
     */
    public RichiestaCancellazioneFile.Uris getUris() {
        return uris;
    }

    /**
     * Sets the value of the uris property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaCancellazioneFile.Uris }
     *     
     */
    public void setUris(RichiestaCancellazioneFile.Uris value) {
        this.uris = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="uri" type="{http://service.hostingtemporaneo.people.phoops.it/}fileURI" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "uri"
    })
    public static class Uris {

        @XmlElement(required = true)
        protected List<FileURI> uri;

        /**
         * Gets the value of the uri property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the uri property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUri().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FileURI }
         * 
         * 
         */
        public List<FileURI> getUri() {
            if (uri == null) {
                uri = new ArrayList<FileURI>();
            }
            return this.uri;
        }

    }

}
