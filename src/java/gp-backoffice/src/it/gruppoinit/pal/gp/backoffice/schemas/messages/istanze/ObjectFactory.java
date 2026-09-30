
package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanze;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.AnagrafeGiuridicaType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.AnagrafeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.ComuneType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.ErroreType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoAnagrafeRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoAnagrafeResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.InserimentoPersonaGiuridicaRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.LocalizzazioneType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.anagrafe.RiferimentiAnagrafeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.AllegatoBaseType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.gruppoinit.pal.gp.backoffice.schemas.messages.istanze package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.gruppoinit.pal.gp.backoffice.schemas.messages.istanze
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EsitoOperazioneType }
     * 
     */
    public EsitoOperazioneType createEsitoOperazioneType() {
        return new EsitoOperazioneType();
    }

    /**
     * Create an instance of {@link ErroreBackofficeType }
     * 
     */
    public ErroreBackofficeType createErroreBackofficeType() {
        return new ErroreBackofficeType();
    }

    /**
     * Create an instance of {@link AllegatoBaseType }
     * 
     */
    public AllegatoBaseType createAllegatoBaseType() {
        return new AllegatoBaseType();
    }

    /**
     * Create an instance of {@link SoggettiCollegatiInsertByIdentificativoResponse }
     * 
     */
    public SoggettiCollegatiInsertByIdentificativoResponse createSoggettiCollegatiInsertByIdentificativoResponse() {
        return new SoggettiCollegatiInsertByIdentificativoResponse();
    }

    /**
     * Create an instance of {@link IstanzeRuoliResponse }
     * 
     */
    public IstanzeRuoliResponse createIstanzeRuoliResponse() {
        return new IstanzeRuoliResponse();
    }

    /**
     * Create an instance of {@link IstanzeRuoliRequest }
     * 
     */
    public IstanzeRuoliRequest createIstanzeRuoliRequest() {
        return new IstanzeRuoliRequest();
    }

    /**
     * Create an instance of {@link IstanzeResponsabiliResponse }
     * 
     */
    public IstanzeResponsabiliResponse createIstanzeResponsabiliResponse() {
        return new IstanzeResponsabiliResponse();
    }

    /**
     * Create an instance of {@link IstanzeResponsabiliRequest }
     * 
     */
    public IstanzeResponsabiliRequest createIstanzeResponsabiliRequest() {
        return new IstanzeResponsabiliRequest();
    }

    /**
     * Create an instance of {@link SoggettiCollegatiInsertByIdentificativoRequest }
     * 
     */
    public SoggettiCollegatiInsertByIdentificativoRequest createSoggettiCollegatiInsertByIdentificativoRequest() {
        return new SoggettiCollegatiInsertByIdentificativoRequest();
    }

    /**
     * Create an instance of {@link IdentificativiAnagrafeType }
     * 
     */
    public IdentificativiAnagrafeType createIdentificativiAnagrafeType() {
        return new IdentificativiAnagrafeType();
    }

    /**
     * Create an instance of {@link InserimentoAnagrafeResponse }
     * 
     */
    public InserimentoAnagrafeResponse createInserimentoAnagrafeResponse() {
        return new InserimentoAnagrafeResponse();
    }

    /**
     * Create an instance of {@link RiferimentiAnagrafeType }
     * 
     */
    public RiferimentiAnagrafeType createRiferimentiAnagrafeType() {
        return new RiferimentiAnagrafeType();
    }

    /**
     * Create an instance of {@link ErroreType }
     * 
     */
    public ErroreType createErroreType() {
        return new ErroreType();
    }

    /**
     * Create an instance of {@link InserimentoAnagrafeRequest }
     * 
     */
    public InserimentoAnagrafeRequest createInserimentoAnagrafeRequest() {
        return new InserimentoAnagrafeRequest();
    }

    /**
     * Create an instance of {@link AnagrafeType }
     * 
     */
    public AnagrafeType createAnagrafeType() {
        return new AnagrafeType();
    }

    /**
     * Create an instance of {@link InserimentoPersonaGiuridicaRequest }
     * 
     */
    public InserimentoPersonaGiuridicaRequest createInserimentoPersonaGiuridicaRequest() {
        return new InserimentoPersonaGiuridicaRequest();
    }

    /**
     * Create an instance of {@link AnagrafeGiuridicaType }
     * 
     */
    public AnagrafeGiuridicaType createAnagrafeGiuridicaType() {
        return new AnagrafeGiuridicaType();
    }

    /**
     * Create an instance of {@link ComuneType }
     * 
     */
    public ComuneType createComuneType() {
        return new ComuneType();
    }

    /**
     * Create an instance of {@link LocalizzazioneType }
     * 
     */
    public LocalizzazioneType createLocalizzazioneType() {
        return new LocalizzazioneType();
    }

}
