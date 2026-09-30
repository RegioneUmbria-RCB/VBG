
package it.gruppoinit.pal.gp.core.schema.importexport.sivbg;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="RepositoryManagerException" type="{http://repertorio.webred.it/xsd}RepositoryManagerException" minOccurs="0"/>
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
    "repositoryManagerException"
})
@XmlRootElement(name = "RepertorioWSRepositoryManagerException")
public class RepertorioWSRepositoryManagerException {

    @XmlElementRef(name = "RepositoryManagerException", namespace = "http://ws.repertorio.webred.it", type = JAXBElement.class)
    protected JAXBElement<RepositoryManagerException> repositoryManagerException;

    /**
     * Gets the value of the repositoryManagerException property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RepositoryManagerException }{@code >}
     *     
     */
    public JAXBElement<RepositoryManagerException> getRepositoryManagerException() {
        return repositoryManagerException;
    }

    /**
     * Sets the value of the repositoryManagerException property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RepositoryManagerException }{@code >}
     *     
     */
    public void setRepositoryManagerException(JAXBElement<RepositoryManagerException> value) {
        this.repositoryManagerException = value;
    }

}
