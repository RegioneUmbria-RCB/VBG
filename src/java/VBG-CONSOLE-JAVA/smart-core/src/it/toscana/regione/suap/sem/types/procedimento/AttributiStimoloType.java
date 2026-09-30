
package it.toscana.regione.suap.sem.types.procedimento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for attributiStimoloType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="attributiStimoloType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="presentazionePratica" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}presentazionePraticaType"/>
 *           &lt;element name="notificaET" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}notificaETType"/>
 *           &lt;element name="notifica" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}notificaType"/>
 *           &lt;element name="valutazioneIntegrazione" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}valutazioneIntegrazioneType"/>
 *           &lt;element name="richiestaIntegrazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}richiestaIntegrazioniType"/>
 *           &lt;element name="invioIntegrazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}invioIntegrazioniType"/>
 *           &lt;element name="inoltroIntegrazioneET" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}inoltroIntegrazioniETType"/>
 *           &lt;element name="inoltroIntegrazione" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}inoltroIntegrazioniType"/>
 *           &lt;element name="chiusuraIntegrazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}chiusuraIntegrazioniType"/>
 *           &lt;element name="valutazioneConformazione" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}valutazioneConformazioneType"/>
 *           &lt;element name="richiestaConformazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}richiestaConformazioniType"/>
 *           &lt;element name="invioConformazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}invioConformazioniType"/>
 *           &lt;element name="inoltroConformazioneET" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}inoltroConformazioniETType"/>
 *           &lt;element name="inoltroConformazione" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}inoltroConformazioniType"/>
 *           &lt;element name="chiusuraConformazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}chiusuraConformazioniType"/>
 *           &lt;element name="esitoNegativo" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}esitoNegativoType"/>
 *           &lt;element name="chiusuraValutazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}chiusuraValutazioniType"/>
 *           &lt;element name="diniego" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}diniegoType"/>
 *           &lt;element name="comunicazioneResponsabile" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}comunicazioneResponsabileType"/>
 *           &lt;element name="rigetto" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}rigettoType"/>
 *           &lt;element name="invioPareri" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}invioPareriType"/>
 *           &lt;element name="invioMotivazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}invioMotivazioniType"/>
 *           &lt;element name="invioOsservazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}invioOsservazioniType"/>
 *           &lt;element name="inoltroOsservazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}inoltroOsservazioniType"/>
 *           &lt;element name="inoltroOsservazioniET" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}inoltroOsservazioniETType"/>
 *           &lt;element name="esitoOsservazioni" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}esitoOsservazioniType"/>
 *           &lt;element name="chiusuraProcedimento" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}chiusuraProcedimentoType"/>
 *           &lt;element name="invioProvvedimento" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}invioProvvedimentoType"/>
 *           &lt;element name="segnalazioneErrore" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}segnalazioneErroreType"/>
 *           &lt;element name="comunicazione" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}comunicazioneType"/>
 *           &lt;element name="pubblicaRicevuta" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}pubblicaRicevutaType"/>
 *         &lt;/choice>
 *         &lt;element name="allegati" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}allegatoGenericoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributiStimoloType", propOrder = {
    "presentazionePratica",
    "notificaET",
    "notifica",
    "valutazioneIntegrazione",
    "richiestaIntegrazioni",
    "invioIntegrazioni",
    "inoltroIntegrazioneET",
    "inoltroIntegrazione",
    "chiusuraIntegrazioni",
    "valutazioneConformazione",
    "richiestaConformazioni",
    "invioConformazioni",
    "inoltroConformazioneET",
    "inoltroConformazione",
    "chiusuraConformazioni",
    "esitoNegativo",
    "chiusuraValutazioni",
    "diniego",
    "comunicazioneResponsabile",
    "rigetto",
    "invioPareri",
    "invioMotivazioni",
    "invioOsservazioni",
    "inoltroOsservazioni",
    "inoltroOsservazioniET",
    "esitoOsservazioni",
    "chiusuraProcedimento",
    "invioProvvedimento",
    "segnalazioneErrore",
    "comunicazione",
    "pubblicaRicevuta",
    "allegati"
})
public class AttributiStimoloType {

    protected PresentazionePraticaType presentazionePratica;
    protected NotificaETType notificaET;
    protected NotificaType notifica;
    protected ValutazioneIntegrazioneType valutazioneIntegrazione;
    protected RichiestaIntegrazioniType richiestaIntegrazioni;
    protected InvioIntegrazioniType invioIntegrazioni;
    protected InoltroIntegrazioniETType inoltroIntegrazioneET;
    protected InoltroIntegrazioniType inoltroIntegrazione;
    protected ChiusuraIntegrazioniType chiusuraIntegrazioni;
    protected ValutazioneConformazioneType valutazioneConformazione;
    protected RichiestaConformazioniType richiestaConformazioni;
    protected InvioConformazioniType invioConformazioni;
    protected InoltroConformazioniETType inoltroConformazioneET;
    protected InoltroConformazioniType inoltroConformazione;
    protected ChiusuraConformazioniType chiusuraConformazioni;
    protected EsitoNegativoType esitoNegativo;
    protected ChiusuraValutazioniType chiusuraValutazioni;
    protected DiniegoType diniego;
    protected ComunicazioneResponsabileType comunicazioneResponsabile;
    protected RigettoType rigetto;
    protected InvioPareriType invioPareri;
    protected InvioMotivazioniType invioMotivazioni;
    protected InvioOsservazioniType invioOsservazioni;
    protected InoltroOsservazioniType inoltroOsservazioni;
    protected InoltroOsservazioniETType inoltroOsservazioniET;
    protected EsitoOsservazioniType esitoOsservazioni;
    protected ChiusuraProcedimentoType chiusuraProcedimento;
    protected InvioProvvedimentoType invioProvvedimento;
    protected SegnalazioneErroreType segnalazioneErrore;
    protected ComunicazioneType comunicazione;
    protected PubblicaRicevutaType pubblicaRicevuta;
    protected List<AllegatoGenericoType> allegati;

    /**
     * Gets the value of the presentazionePratica property.
     * 
     * @return
     *     possible object is
     *     {@link PresentazionePraticaType }
     *     
     */
    public PresentazionePraticaType getPresentazionePratica() {
        return presentazionePratica;
    }

    /**
     * Sets the value of the presentazionePratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link PresentazionePraticaType }
     *     
     */
    public void setPresentazionePratica(PresentazionePraticaType value) {
        this.presentazionePratica = value;
    }

    /**
     * Gets the value of the notificaET property.
     * 
     * @return
     *     possible object is
     *     {@link NotificaETType }
     *     
     */
    public NotificaETType getNotificaET() {
        return notificaET;
    }

    /**
     * Sets the value of the notificaET property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificaETType }
     *     
     */
    public void setNotificaET(NotificaETType value) {
        this.notificaET = value;
    }

    /**
     * Gets the value of the notifica property.
     * 
     * @return
     *     possible object is
     *     {@link NotificaType }
     *     
     */
    public NotificaType getNotifica() {
        return notifica;
    }

    /**
     * Sets the value of the notifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificaType }
     *     
     */
    public void setNotifica(NotificaType value) {
        this.notifica = value;
    }

    /**
     * Gets the value of the valutazioneIntegrazione property.
     * 
     * @return
     *     possible object is
     *     {@link ValutazioneIntegrazioneType }
     *     
     */
    public ValutazioneIntegrazioneType getValutazioneIntegrazione() {
        return valutazioneIntegrazione;
    }

    /**
     * Sets the value of the valutazioneIntegrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValutazioneIntegrazioneType }
     *     
     */
    public void setValutazioneIntegrazione(ValutazioneIntegrazioneType value) {
        this.valutazioneIntegrazione = value;
    }

    /**
     * Gets the value of the richiestaIntegrazioni property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaIntegrazioniType }
     *     
     */
    public RichiestaIntegrazioniType getRichiestaIntegrazioni() {
        return richiestaIntegrazioni;
    }

    /**
     * Sets the value of the richiestaIntegrazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaIntegrazioniType }
     *     
     */
    public void setRichiestaIntegrazioni(RichiestaIntegrazioniType value) {
        this.richiestaIntegrazioni = value;
    }

    /**
     * Gets the value of the invioIntegrazioni property.
     * 
     * @return
     *     possible object is
     *     {@link InvioIntegrazioniType }
     *     
     */
    public InvioIntegrazioniType getInvioIntegrazioni() {
        return invioIntegrazioni;
    }

    /**
     * Sets the value of the invioIntegrazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvioIntegrazioniType }
     *     
     */
    public void setInvioIntegrazioni(InvioIntegrazioniType value) {
        this.invioIntegrazioni = value;
    }

    /**
     * Gets the value of the inoltroIntegrazioneET property.
     * 
     * @return
     *     possible object is
     *     {@link InoltroIntegrazioniETType }
     *     
     */
    public InoltroIntegrazioniETType getInoltroIntegrazioneET() {
        return inoltroIntegrazioneET;
    }

    /**
     * Sets the value of the inoltroIntegrazioneET property.
     * 
     * @param value
     *     allowed object is
     *     {@link InoltroIntegrazioniETType }
     *     
     */
    public void setInoltroIntegrazioneET(InoltroIntegrazioniETType value) {
        this.inoltroIntegrazioneET = value;
    }

    /**
     * Gets the value of the inoltroIntegrazione property.
     * 
     * @return
     *     possible object is
     *     {@link InoltroIntegrazioniType }
     *     
     */
    public InoltroIntegrazioniType getInoltroIntegrazione() {
        return inoltroIntegrazione;
    }

    /**
     * Sets the value of the inoltroIntegrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link InoltroIntegrazioniType }
     *     
     */
    public void setInoltroIntegrazione(InoltroIntegrazioniType value) {
        this.inoltroIntegrazione = value;
    }

    /**
     * Gets the value of the chiusuraIntegrazioni property.
     * 
     * @return
     *     possible object is
     *     {@link ChiusuraIntegrazioniType }
     *     
     */
    public ChiusuraIntegrazioniType getChiusuraIntegrazioni() {
        return chiusuraIntegrazioni;
    }

    /**
     * Sets the value of the chiusuraIntegrazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChiusuraIntegrazioniType }
     *     
     */
    public void setChiusuraIntegrazioni(ChiusuraIntegrazioniType value) {
        this.chiusuraIntegrazioni = value;
    }

    /**
     * Gets the value of the valutazioneConformazione property.
     * 
     * @return
     *     possible object is
     *     {@link ValutazioneConformazioneType }
     *     
     */
    public ValutazioneConformazioneType getValutazioneConformazione() {
        return valutazioneConformazione;
    }

    /**
     * Sets the value of the valutazioneConformazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValutazioneConformazioneType }
     *     
     */
    public void setValutazioneConformazione(ValutazioneConformazioneType value) {
        this.valutazioneConformazione = value;
    }

    /**
     * Gets the value of the richiestaConformazioni property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaConformazioniType }
     *     
     */
    public RichiestaConformazioniType getRichiestaConformazioni() {
        return richiestaConformazioni;
    }

    /**
     * Sets the value of the richiestaConformazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaConformazioniType }
     *     
     */
    public void setRichiestaConformazioni(RichiestaConformazioniType value) {
        this.richiestaConformazioni = value;
    }

    /**
     * Gets the value of the invioConformazioni property.
     * 
     * @return
     *     possible object is
     *     {@link InvioConformazioniType }
     *     
     */
    public InvioConformazioniType getInvioConformazioni() {
        return invioConformazioni;
    }

    /**
     * Sets the value of the invioConformazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvioConformazioniType }
     *     
     */
    public void setInvioConformazioni(InvioConformazioniType value) {
        this.invioConformazioni = value;
    }

    /**
     * Gets the value of the inoltroConformazioneET property.
     * 
     * @return
     *     possible object is
     *     {@link InoltroConformazioniETType }
     *     
     */
    public InoltroConformazioniETType getInoltroConformazioneET() {
        return inoltroConformazioneET;
    }

    /**
     * Sets the value of the inoltroConformazioneET property.
     * 
     * @param value
     *     allowed object is
     *     {@link InoltroConformazioniETType }
     *     
     */
    public void setInoltroConformazioneET(InoltroConformazioniETType value) {
        this.inoltroConformazioneET = value;
    }

    /**
     * Gets the value of the inoltroConformazione property.
     * 
     * @return
     *     possible object is
     *     {@link InoltroConformazioniType }
     *     
     */
    public InoltroConformazioniType getInoltroConformazione() {
        return inoltroConformazione;
    }

    /**
     * Sets the value of the inoltroConformazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link InoltroConformazioniType }
     *     
     */
    public void setInoltroConformazione(InoltroConformazioniType value) {
        this.inoltroConformazione = value;
    }

    /**
     * Gets the value of the chiusuraConformazioni property.
     * 
     * @return
     *     possible object is
     *     {@link ChiusuraConformazioniType }
     *     
     */
    public ChiusuraConformazioniType getChiusuraConformazioni() {
        return chiusuraConformazioni;
    }

    /**
     * Sets the value of the chiusuraConformazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChiusuraConformazioniType }
     *     
     */
    public void setChiusuraConformazioni(ChiusuraConformazioniType value) {
        this.chiusuraConformazioni = value;
    }

    /**
     * Gets the value of the esitoNegativo property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoNegativoType }
     *     
     */
    public EsitoNegativoType getEsitoNegativo() {
        return esitoNegativo;
    }

    /**
     * Sets the value of the esitoNegativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoNegativoType }
     *     
     */
    public void setEsitoNegativo(EsitoNegativoType value) {
        this.esitoNegativo = value;
    }

    /**
     * Gets the value of the chiusuraValutazioni property.
     * 
     * @return
     *     possible object is
     *     {@link ChiusuraValutazioniType }
     *     
     */
    public ChiusuraValutazioniType getChiusuraValutazioni() {
        return chiusuraValutazioni;
    }

    /**
     * Sets the value of the chiusuraValutazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChiusuraValutazioniType }
     *     
     */
    public void setChiusuraValutazioni(ChiusuraValutazioniType value) {
        this.chiusuraValutazioni = value;
    }

    /**
     * Gets the value of the diniego property.
     * 
     * @return
     *     possible object is
     *     {@link DiniegoType }
     *     
     */
    public DiniegoType getDiniego() {
        return diniego;
    }

    /**
     * Sets the value of the diniego property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiniegoType }
     *     
     */
    public void setDiniego(DiniegoType value) {
        this.diniego = value;
    }

    /**
     * Gets the value of the comunicazioneResponsabile property.
     * 
     * @return
     *     possible object is
     *     {@link ComunicazioneResponsabileType }
     *     
     */
    public ComunicazioneResponsabileType getComunicazioneResponsabile() {
        return comunicazioneResponsabile;
    }

    /**
     * Sets the value of the comunicazioneResponsabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComunicazioneResponsabileType }
     *     
     */
    public void setComunicazioneResponsabile(ComunicazioneResponsabileType value) {
        this.comunicazioneResponsabile = value;
    }

    /**
     * Gets the value of the rigetto property.
     * 
     * @return
     *     possible object is
     *     {@link RigettoType }
     *     
     */
    public RigettoType getRigetto() {
        return rigetto;
    }

    /**
     * Sets the value of the rigetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link RigettoType }
     *     
     */
    public void setRigetto(RigettoType value) {
        this.rigetto = value;
    }

    /**
     * Gets the value of the invioPareri property.
     * 
     * @return
     *     possible object is
     *     {@link InvioPareriType }
     *     
     */
    public InvioPareriType getInvioPareri() {
        return invioPareri;
    }

    /**
     * Sets the value of the invioPareri property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvioPareriType }
     *     
     */
    public void setInvioPareri(InvioPareriType value) {
        this.invioPareri = value;
    }

    /**
     * Gets the value of the invioMotivazioni property.
     * 
     * @return
     *     possible object is
     *     {@link InvioMotivazioniType }
     *     
     */
    public InvioMotivazioniType getInvioMotivazioni() {
        return invioMotivazioni;
    }

    /**
     * Sets the value of the invioMotivazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvioMotivazioniType }
     *     
     */
    public void setInvioMotivazioni(InvioMotivazioniType value) {
        this.invioMotivazioni = value;
    }

    /**
     * Gets the value of the invioOsservazioni property.
     * 
     * @return
     *     possible object is
     *     {@link InvioOsservazioniType }
     *     
     */
    public InvioOsservazioniType getInvioOsservazioni() {
        return invioOsservazioni;
    }

    /**
     * Sets the value of the invioOsservazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvioOsservazioniType }
     *     
     */
    public void setInvioOsservazioni(InvioOsservazioniType value) {
        this.invioOsservazioni = value;
    }

    /**
     * Gets the value of the inoltroOsservazioni property.
     * 
     * @return
     *     possible object is
     *     {@link InoltroOsservazioniType }
     *     
     */
    public InoltroOsservazioniType getInoltroOsservazioni() {
        return inoltroOsservazioni;
    }

    /**
     * Sets the value of the inoltroOsservazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link InoltroOsservazioniType }
     *     
     */
    public void setInoltroOsservazioni(InoltroOsservazioniType value) {
        this.inoltroOsservazioni = value;
    }

    /**
     * Gets the value of the inoltroOsservazioniET property.
     * 
     * @return
     *     possible object is
     *     {@link InoltroOsservazioniETType }
     *     
     */
    public InoltroOsservazioniETType getInoltroOsservazioniET() {
        return inoltroOsservazioniET;
    }

    /**
     * Sets the value of the inoltroOsservazioniET property.
     * 
     * @param value
     *     allowed object is
     *     {@link InoltroOsservazioniETType }
     *     
     */
    public void setInoltroOsservazioniET(InoltroOsservazioniETType value) {
        this.inoltroOsservazioniET = value;
    }

    /**
     * Gets the value of the esitoOsservazioni property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoOsservazioniType }
     *     
     */
    public EsitoOsservazioniType getEsitoOsservazioni() {
        return esitoOsservazioni;
    }

    /**
     * Sets the value of the esitoOsservazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoOsservazioniType }
     *     
     */
    public void setEsitoOsservazioni(EsitoOsservazioniType value) {
        this.esitoOsservazioni = value;
    }

    /**
     * Gets the value of the chiusuraProcedimento property.
     * 
     * @return
     *     possible object is
     *     {@link ChiusuraProcedimentoType }
     *     
     */
    public ChiusuraProcedimentoType getChiusuraProcedimento() {
        return chiusuraProcedimento;
    }

    /**
     * Sets the value of the chiusuraProcedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChiusuraProcedimentoType }
     *     
     */
    public void setChiusuraProcedimento(ChiusuraProcedimentoType value) {
        this.chiusuraProcedimento = value;
    }

    /**
     * Gets the value of the invioProvvedimento property.
     * 
     * @return
     *     possible object is
     *     {@link InvioProvvedimentoType }
     *     
     */
    public InvioProvvedimentoType getInvioProvvedimento() {
        return invioProvvedimento;
    }

    /**
     * Sets the value of the invioProvvedimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvioProvvedimentoType }
     *     
     */
    public void setInvioProvvedimento(InvioProvvedimentoType value) {
        this.invioProvvedimento = value;
    }

    /**
     * Gets the value of the segnalazioneErrore property.
     * 
     * @return
     *     possible object is
     *     {@link SegnalazioneErroreType }
     *     
     */
    public SegnalazioneErroreType getSegnalazioneErrore() {
        return segnalazioneErrore;
    }

    /**
     * Sets the value of the segnalazioneErrore property.
     * 
     * @param value
     *     allowed object is
     *     {@link SegnalazioneErroreType }
     *     
     */
    public void setSegnalazioneErrore(SegnalazioneErroreType value) {
        this.segnalazioneErrore = value;
    }

    /**
     * Gets the value of the comunicazione property.
     * 
     * @return
     *     possible object is
     *     {@link ComunicazioneType }
     *     
     */
    public ComunicazioneType getComunicazione() {
        return comunicazione;
    }

    /**
     * Sets the value of the comunicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComunicazioneType }
     *     
     */
    public void setComunicazione(ComunicazioneType value) {
        this.comunicazione = value;
    }

    /**
     * Gets the value of the pubblicaRicevuta property.
     * 
     * @return
     *     possible object is
     *     {@link PubblicaRicevutaType }
     *     
     */
    public PubblicaRicevutaType getPubblicaRicevuta() {
        return pubblicaRicevuta;
    }

    /**
     * Sets the value of the pubblicaRicevuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link PubblicaRicevutaType }
     *     
     */
    public void setPubblicaRicevuta(PubblicaRicevutaType value) {
        this.pubblicaRicevuta = value;
    }

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
     * {@link AllegatoGenericoType }
     * 
     * 
     */
    public List<AllegatoGenericoType> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<AllegatoGenericoType>();
        }
        return this.allegati;
    }

}
