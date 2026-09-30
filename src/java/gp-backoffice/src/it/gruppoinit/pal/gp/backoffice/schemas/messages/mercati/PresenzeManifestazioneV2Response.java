package it.gruppoinit.pal.gp.backoffice.schemas.messages.mercati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="numeroPresenze" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeroPresenzeProp" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="inseritoAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *	   &lt;element name="inseritoPresenze" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *	   &lt;element name="inseritoSpuntistiMercato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "PresenzeManifestazioneV2Response")
public class PresenzeManifestazioneV2Response {

    protected int numeroPresenze;
    protected int numeroPresenzeProp;
    protected boolean inseritoAutorizzazione;
    protected boolean inseritoPresenze;
    protected boolean inseritoSpuntistiMercato;

    /**
     * Gets the value of the numeroPresenze property.
     * 
     */
    public int getNumeroPresenze() {

	return numeroPresenze;
    }

    /**
     * Sets the value of the numeroPresenze property.
     * 
     */
    public void setNumeroPresenze(int value) {

	this.numeroPresenze = value;
    }

    /**
     * Gets the value of the numeroPresenzeProp property.
     * 
     */
    public int getNumeroPresenzeProp() {

	return numeroPresenzeProp;
    }

    /**
     * Sets the value of the numeroPresenzeProp property.
     * 
     */
    public void setNumeroPresenzeProp(int value) {

	this.numeroPresenzeProp = value;
    }

    /**
     * Gets the value of the inseritoAutorizzazione property.
     * 
     */
    public boolean isInseritoAutorizzazione() {

	return inseritoAutorizzazione;
    }

    /**
     * Sets the value of the inseritoAutorizzazione property.
     * 
     */
    public void setInseritoAutorizzazione(boolean inseritoAutorizzazione) {

	this.inseritoAutorizzazione = inseritoAutorizzazione;
    }

    /**
     * Gets the value of the inseritoPresenze property.
     * 
     */
    public boolean isInseritoPresenze() {

	return inseritoPresenze;
    }

    /**
     * Sets the value of the inseritoPresenze property.
     * 
     */
    public void setInseritoPresenze(boolean inseritoPresenze) {

	this.inseritoPresenze = inseritoPresenze;
    }

    /**
     * Gets the value of the inseritoSpuntistiMercato property.
     * 
     */
    public boolean isInseritoSpuntistiMercato() {

	return inseritoSpuntistiMercato;
    }

    /**
     * Sets the value of the inseritoSpuntistiMercato property.
     * 
     */
    public void setInseritoSpuntistiMercato(boolean inseritoSpuntistiMercato) {

	this.inseritoSpuntistiMercato = inseritoSpuntistiMercato;
    }
}
