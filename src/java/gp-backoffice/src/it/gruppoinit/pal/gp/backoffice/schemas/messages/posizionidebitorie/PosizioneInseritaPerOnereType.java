
package it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PosizioneInseritaPerOnereType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PosizioneInseritaPerOnereType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="riferimentoPosizioneDebitoria" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="riferimentoOnere" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="messaggioErrore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneInseritaPerOnereType", propOrder = {
    "riferimentoPosizioneDebitoria",
    "riferimentoOnere",
    "messaggioErrore"
})
public class PosizioneInseritaPerOnereType {

    @XmlElement(required = true)
    protected BigInteger riferimentoPosizioneDebitoria;
    @XmlElement(required = true)
    protected BigInteger riferimentoOnere;
    @XmlElement
    protected String messaggioErrore;

    /**
     * Gets the value of the riferimentoPosizioneDebitoria property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRiferimentoPosizioneDebitoria() {
        return riferimentoPosizioneDebitoria;
    }

    /**
     * Sets the value of the riferimentoPosizioneDebitoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRiferimentoPosizioneDebitoria(BigInteger value) {
        this.riferimentoPosizioneDebitoria = value;
    }

    /**
     * Gets the value of the riferimentoOnere property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRiferimentoOnere() {
        return riferimentoOnere;
    }

    /**
     * Sets the value of the riferimentoOnere property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRiferimentoOnere(BigInteger value) {
        this.riferimentoOnere = value;
    }


    /**
     * Gets the value of the messaggioErrore property.
     * 
     * @return
     *     possible object is
     *     {@link messaggioErrore }
     *     
     */
    public String getMessaggioErrore() {
    
        return messaggioErrore;
    }

    /**
     * Sets the value of the messaggioErrore property.
     * 
     * @param value
     *     allowed object is
     *     {@link messaggioErrore }
     *     
     */
    public void setMessaggioErrore(String messaggioErrore) {
    
        this.messaggioErrore = messaggioErrore;
    }
   
}
