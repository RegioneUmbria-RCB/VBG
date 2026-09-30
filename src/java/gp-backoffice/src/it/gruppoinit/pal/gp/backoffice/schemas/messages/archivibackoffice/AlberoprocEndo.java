
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AlberoprocEndo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlberoprocEndo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodo" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}NodoAlbero"/>
 *         &lt;element name="endoprocedimento" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}Endoprocedimento"/>
 *         &lt;element name="azione" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}Azione" minOccurs="0"/>
 *         &lt;element name="flagRichiesto" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="flagPrincipale" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="flagPubblicato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="flagUsaNelBack" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlberoprocEndo", propOrder = {
    "nodo",
    "endoprocedimento",
    "azione",
    "flagRichiesto",
    "flagPrincipale",
    "flagPubblicato",
    "flagUsaNelBack"
})
public class AlberoprocEndo {

    @XmlElement(required = true)
    protected NodoAlbero nodo;
    @XmlElement(required = true)
    protected Endoprocedimento endoprocedimento;
    protected Azione azione;
    protected boolean flagRichiesto;
    protected boolean flagPrincipale;
    protected boolean flagPubblicato;
    protected boolean flagUsaNelBack;

    /**
     * Gets the value of the nodo property.
     * 
     * @return
     *     possible object is
     *     {@link NodoAlbero }
     *     
     */
    public NodoAlbero getNodo() {
        return nodo;
    }

    /**
     * Sets the value of the nodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link NodoAlbero }
     *     
     */
    public void setNodo(NodoAlbero value) {
        this.nodo = value;
    }

    /**
     * Gets the value of the endoprocedimento property.
     * 
     * @return
     *     possible object is
     *     {@link Endoprocedimento }
     *     
     */
    public Endoprocedimento getEndoprocedimento() {
        return endoprocedimento;
    }

    /**
     * Sets the value of the endoprocedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Endoprocedimento }
     *     
     */
    public void setEndoprocedimento(Endoprocedimento value) {
        this.endoprocedimento = value;
    }

    /**
     * Gets the value of the azione property.
     * 
     * @return
     *     possible object is
     *     {@link Azione }
     *     
     */
    public Azione getAzione() {
        return azione;
    }

    /**
     * Sets the value of the azione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Azione }
     *     
     */
    public void setAzione(Azione value) {
        this.azione = value;
    }

    /**
     * Gets the value of the flagRichiesto property.
     * 
     */
    public boolean isFlagRichiesto() {
        return flagRichiesto;
    }

    /**
     * Sets the value of the flagRichiesto property.
     * 
     */
    public void setFlagRichiesto(boolean value) {
        this.flagRichiesto = value;
    }

    /**
     * Gets the value of the flagPrincipale property.
     * 
     */
    public boolean isFlagPrincipale() {
        return flagPrincipale;
    }

    /**
     * Sets the value of the flagPrincipale property.
     * 
     */
    public void setFlagPrincipale(boolean value) {
        this.flagPrincipale = value;
    }

    /**
     * Gets the value of the flagPubblicato property.
     * 
     */
    public boolean isFlagPubblicato() {
        return flagPubblicato;
    }

    /**
     * Sets the value of the flagPubblicato property.
     * 
     */
    public void setFlagPubblicato(boolean value) {
        this.flagPubblicato = value;
    }

    /**
     * Gets the value of the flagUsaNelBack property.
     * 
     */
    public boolean isFlagUsaNelBack() {
        return flagUsaNelBack;
    }

    /**
     * Sets the value of the flagUsaNelBack property.
     * 
     */
    public void setFlagUsaNelBack(boolean value) {
        this.flagUsaNelBack = value;
    }

}
