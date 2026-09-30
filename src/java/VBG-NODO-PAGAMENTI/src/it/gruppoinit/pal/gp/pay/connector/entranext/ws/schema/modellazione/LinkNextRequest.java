
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per LinkNextRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="LinkNextRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LinkNextRequest")
@XmlSeeAlso({
    LoginRequest.class,
    InserisciPosizioniInAttesaRequest.class,
    InserisciPosizioneRequest.class,
    InserisciPosizioneICPRequest.class,
    InserisciPosizioneOSAPRequest.class,
    InserisciRuoloNoteDiCreditoRequest.class,
    InserisciNotaDiCreditoRequest.class,
    InserisciPagamentiRequest.class,
    RiceviEsitoTransazioneRequest.class,
    ScaricaDocumentoPDFRequest.class,
    AggiornaPosizioneRequest.class,
    AggiornaPosizioneICPRequest.class,
    AggiornaPosizioneOSAPRequest.class,
    VerificaPosizioneRequest.class,
    RiceviSottoServiziRequest.class,
    RiceviVociDiCostoRequest.class,
    RiceviPosizioniDebitorieRequest.class,
    RiceviRendicontazionePagamentiRequest.class,
    ScaricaDocumentiPDFRuoloRequest.class,
    VerificaStatoRuoloRequest.class,
    RiceviRuoloIUVRequest.class,
    AnnullaPosizioneDebitoriaRequest.class,
    ValidaPosizioneDebitoriaRequest.class,
    ValidaPosizioneDebitoriaICPRequest.class,
    ValidaPosizioneDebitoriaOSAPRequest.class,
    ValidaNotaDiCreditoRequest.class,
    ValidaRuoloPosizioniDebitorieRequest.class,
    ValidaRuoloPosizioniDebitorieICPRequest.class,
    ValidaRuoloPosizioniDebitorieOSAPRequest.class,
    AnnullaRuoloPosizioniRequest.class,
    ApprovaRuoloPosizioniRequest.class,
    ScaricaPagamentiRTPosizioniDebitorieRequest.class,
    ScaricaPagamentoRTRequest.class,
    InserisciSgravioPosizioneRequest.class,
    InserisciPosizioniInAttesaCARequest.class,
    VersioneRequest.class,
    InserisciRuoloPosizioniRequestBase.class
})
public class LinkNextRequest {


}
