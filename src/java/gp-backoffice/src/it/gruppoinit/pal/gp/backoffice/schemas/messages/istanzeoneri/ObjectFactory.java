package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.AllegatoBaseType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content.
 * The Java representation of XML content can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in
 * this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RegistraPagamentoResponse_QNAME = new QName("http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri",
	    "RegistraPagamentoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri
     * 
     */
    public ObjectFactory() {

    }

    /**
     * Create an instance of {@link RegistraPagamentoRequest }
     * 
     */
    public RegistraPagamentoRequest createRegistraPagamentoRequest() {

	return new RegistraPagamentoRequest();
    }

    /**
     * Create an instance of {@link ModalitaPagamentoType }
     * 
     */
    public ModalitaPagamentoType createModalitaPagamentoType() {

	return new ModalitaPagamentoType();
    }

    /**
     * Create an instance of {@link InsertOnereRequest }
     * 
     */
    public InsertOnereRequest createInsertOnereRequest() {

	return new InsertOnereRequest();
    }

    /**
     * Create an instance of {@link InsertOnereResponse }
     * 
     */
    public InsertOnereResponse createInsertOnereResponse() {

	return new InsertOnereResponse();
    }

    /**
     * Create an instance of {@link EsitoOperazioneType }
     * 
     */
    public EsitoOperazioneType createEsitoOperazioneType() {

	return new EsitoOperazioneType();
    }

    /**
     * Create an instance of {@link EliminaOnereResponse }
     * 
     */
    public EliminaOnereResponse createEliminaOnereResponse() {

	return new EliminaOnereResponse();
    }

    /**
     * Create an instance of {@link RegistraPagamentoRataRequest }
     * 
     */
    public RegistraPagamentoRataRequest createRegistraPagamentoRataRequest() {

	return new RegistraPagamentoRataRequest();
    }

    /**
     * Create an instance of {@link EliminaOnereRequest }
     * 
     */
    public EliminaOnereRequest createEliminaOnereRequest() {

	return new EliminaOnereRequest();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link EsitoOperazioneType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri", name = "RegistraPagamentoResponse")
    public JAXBElement<EsitoOperazioneType> createRegistraPagamentoResponse(EsitoOperazioneType value) {

	return new JAXBElement<EsitoOperazioneType>(_RegistraPagamentoResponse_QNAME, EsitoOperazioneType.class, null, value);
    }
}
