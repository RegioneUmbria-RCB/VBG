package it.gruppoinit.stc.ws.client;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.stc.domain.Configurazione;
import it.gruppoinit.stc.service.ConfigurazioneService;
import it.init.sigepro.rte.AggiungiDocumentiNLARequest;
import it.init.sigepro.rte.AggiungiDocumentiNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticheListaNLARequest;
import it.init.sigepro.rte.RichiestaPraticheListaNLAResponse;
import it.init.sigepro.rte.TestNLARequest;
import it.init.sigepro.rte.TestNLAResponse;
import it.init.sigepro.rte.definitions.Nla;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.ProcuraType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.XsdTypesVersion;

@Component
public class NlaWebServiceClient {

    private static final Logger log = LoggerFactory.getLogger(NlaWebServiceClient.class);
    @Autowired
    private ConfigurazioneService configurazioneService;
    private Map<String, XsdTypesVersion> mappaNodiCompatibilita = new HashMap<String, XsdTypesVersion>();

    public InserimentoAttivitaNLAResponse inserisciAttivita(InserimentoAttivitaNLARequest request, Configurazione nodo) {

	InserimentoAttivitaNLAResponse response = null;
	Configurazione configurazione = configurazioneService.findById(nodo.getIdnodo());
	String uri = configurazione.getWsurl();
	try {
	    logXML(request, InserimentoAttivitaNLARequest.class);
	    Nla port = getNlaWsPort(uri);
	    //	    InserimentoAttivitaNLARequest attivitaNLARequest = null;
	    //	    attivitaNLARequest.getDatiAttivita();
	    response = port.inserimentoAttivitaNLA(request);
	    logXML(response, InserimentoAttivitaNLAResponse.class);
	} catch (Exception e) {
	    log.error("inserisciAttivita(idnodo={})", nodo.getIdnodo(), e);
	    throw new RuntimeException("Errore durante la chiamata all'NLA destinatario[" + nodo.getIdnodo() + "] (" + getSOAPFAULT(e) + ")");
	}
	return response;
    }

    public InserimentoPraticaNLAResponse inserisciPratica(InserimentoPraticaNLARequest request, Configurazione nodo) {

	InserimentoPraticaNLAResponse response = null;
	Configurazione configurazione = configurazioneService.findById(nodo.getIdnodo());
	String uri = configurazione.getWsurl();
	try {
	    logXML(request, InserimentoPraticaNLARequest.class);
	    Nla port = getNlaWsPort(uri);
	    //	    if (request.getDettaglioPratica() != null) {
	    //		adaptPratica(request.getToken(), request.getSportelloDestinatario(), request.getDettaglioPratica());
	    //	    }
	    response = port.inserimentoPraticaNLA(request);
	    logXML(response, InserimentoPraticaNLAResponse.class);
	} catch (Exception e) {
	    log.error("inserisciPratica(idnodo={})", nodo.getIdnodo(), e);
	    throw new RuntimeException("Errore durante la chiamata all'NLA destinatario[" + nodo.getIdnodo() + "] (" + getSOAPFAULT(e) + ")");
	}
	return response;
    }

    public RichiestaPraticheListaNLAResponse richiestaPraticheLista(RichiestaPraticheListaNLARequest request, Configurazione nodo) {

	RichiestaPraticheListaNLAResponse response = null;
	Configurazione configurazione = configurazioneService.findById(nodo.getIdnodo());
	String uri = configurazione.getWsurl();
	try {
	    logXML(request, RichiestaPraticheListaNLARequest.class);
	    Nla port = getNlaWsPort(uri);
	    response = port.richiestaPraticheListaNLA(request);
	    logXML(response, RichiestaPraticheListaNLAResponse.class);
	} catch (Exception e) {
	    log.error("richiestaPraticheLista(idnodo={})", nodo.getIdnodo(), e);
	    throw new RuntimeException("Errore durante la chiamata all'NLA destinatario[" + nodo.getIdnodo() + "] (" + getSOAPFAULT(e) + ")");
	}
	return response;
    }

    public AllegatoBinarioNLAResponse richiestaAllegato(AllegatoBinarioNLARequest request, Configurazione nodo) {

	AllegatoBinarioNLAResponse response = null;
	Configurazione configurazione = configurazioneService.findById(nodo.getIdnodo());
	String uri = configurazione.getWsurl();
	try {
	    logXML(request, AllegatoBinarioNLARequest.class);
	    Nla port = getNlaWsPort(uri);
	    response = port.allegatoBinarioNLA(request);
	    logXML(response, AllegatoBinarioNLAResponse.class);
	} catch (Exception e) {
	    log.error("richiestaAllegato(idnodo={})", nodo.getIdnodo(), e);
	    throw new RuntimeException("Errore durante la chiamata all'NLA destinatario[" + nodo.getIdnodo() + "] (" + getSOAPFAULT(e) + ")");
	}
	return response;
    }

    public RichiestaPraticaNLAResponse richiestaPratica(RichiestaPraticaNLARequest request, Configurazione nodo) {

	RichiestaPraticaNLAResponse response = null;
	Configurazione configurazione = configurazioneService.findById(nodo.getIdnodo());
	String uri = configurazione.getWsurl();
	try {
	    logXML(request, RichiestaPraticaNLARequest.class);
	    Nla port = getNlaWsPort(uri);
	    response = port.richiestaPraticaNLA(request);
	    //	    if (response.getDettaglioPratica() != null && response.getDettaglioPratica().getDettaglioPratica() != null) {
	    //		adaptPratica(request.getToken(), request.getSportelloDestinatario(), response.getDettaglioPratica().getDettaglioPratica());
	    //	    }
	    logXML(response, RichiestaPraticaNLAResponse.class);
	} catch (Exception e) {
	    log.error("richiestaPratica(idnodo={})", nodo.getIdnodo(), e);
	    throw new RuntimeException("Errore durante la chiamata all'NLA destinatario[" + nodo.getIdnodo() + "] (" + getSOAPFAULT(e) + ")");
	}
	return response;
    }

    public AggiungiDocumentiNLAResponse aggiungiDocumenti(AggiungiDocumentiNLARequest request, Configurazione nodo) {

	AggiungiDocumentiNLAResponse response = null;
	Configurazione configurazione = configurazioneService.findById(nodo.getIdnodo());
	String uri = configurazione.getWsurl();
	try {
	    logXML(request, AggiungiDocumentiNLARequest.class);
	    Nla port = getNlaWsPort(uri);
	    response = port.aggiungiDocumentiNLA(request);
	    logXML(response, AggiungiDocumentiNLAResponse.class);
	} catch (Exception e) {
	    log.error("aggiungiDocumenti(idnodo={})", nodo.getIdnodo(), e);
	    throw new RuntimeException("Errore durante la chiamata all'NLA destinatario[" + nodo.getIdnodo() + "] (" + getSOAPFAULT(e) + ")");
	}
	return response;
    }

    public TestNLAResponse testNLA(TestNLARequest request, Configurazione nodo) {

	TestNLAResponse response = null;
	try {
	    Nla port = getNlaWsPort(nodo.getWsurl());
	    response = port.testNLA(request);
	} catch (Exception e) {
	    log.error("testNLA(url={}) ", nodo.getWsurl(), e);
	    throw new RuntimeException(e.getMessage());
	}
	return response;
    }

    private Nla getNlaWsPort(String wsUrl) throws Exception {

	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	factory.setServiceClass(Nla.class);
	factory.setAddress(wsUrl);
	Nla port = (Nla) factory.create();
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	BindingProvider bp = (BindingProvider) port;
	bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
	SOAPBinding binding = (SOAPBinding) bp.getBinding();
	binding.setMTOMEnabled(true);
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(12000); // Line #2  
	httpClientPolicy.setReceiveTimeout(1200000); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }

    @SuppressWarnings("rawtypes")
    private void logXML(Object jaxbElement, Class clazz) {

	if (log.isDebugEnabled()) {
	    try {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Marshaller marshaller = jaxbContext.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(jaxbElement, stringWriter);
		log.debug(stringWriter.toString());
	    } catch (JAXBException e) {
		log.warn("logXML()" + e);
	    }
	}
    }

    private String getSOAPFAULT(Exception e) {

	StringBuffer details = new StringBuffer();
	details.append(e.getMessage());
	return details.toString();
    }

    private XsdTypesVersion getCompatibilitaNodo(SportelloType destinatario, String token) {

	String key = destinatario.getIdNodo() + "_" + destinatario.getIdEnte() + "_" + destinatario.getIdSportello();
	XsdTypesVersion version = mappaNodiCompatibilita.get(key);
	if (version != null) {
	    return version;
	} else {
	    try {
		Configurazione configurazione = configurazioneService.findById(Integer.parseInt(destinatario.getIdNodo()));
		TestNLARequest testNLARequest = new TestNLARequest();
		testNLARequest.setTest(token);
		TestNLAResponse testNLAResponse = testNLA(testNLARequest, configurazione);
		if (testNLAResponse != null) {
		    if (testNLAResponse.getTypesXsdVersion() != null) {
			mappaNodiCompatibilita.put(key, testNLAResponse.getTypesXsdVersion());
			return testNLAResponse.getTypesXsdVersion();
		    }
		}
	    } catch (Exception e) {
		log.error("{}", e);
	    }
	}
	return null;
    }

    // TODO ad ogni modifica di versione aggiornare il metodo per la compatibilità
    private void adaptPratica(String token, SportelloType sportelloDestinatario, DettaglioPraticaType dettaglioPratica) {

	XsdTypesVersion v = getCompatibilitaNodo(sportelloDestinatario, token);
	if (v != null) {
	    if (v == XsdTypesVersion.V_1_11) {
		//		DETTAGLIOPRATICATYPE
		//		<element name="naturaFo" type="tns:NaturaFoType" minOccurs="0" maxOccurs="1">
		//			<annotation>
		//				<documentation>
		//					la natura del procedimento che viene impostato dal frontend
		//				</documentation>
		//			</annotation>			
		//		</element>
		//                
		//                PROCURATYPE	
		//                <element name="documentoIdentita" type="tns:DocumentiType"
		//                			minOccurs="0" maxOccurs="1"></element>	
		if (dettaglioPratica != null) {
		    dettaglioPratica.setNaturaFo(null);
		    List<ProcuraType> procures = dettaglioPratica.getProcure();
		    if (procures != null) {
			for (ProcuraType p : procures) {
			    if (p != null) {
				p.setDocumentoIdentita(null);
			    }
			}
		    }
		}
		v = XsdTypesVersion.V_1_12; // elaboro anche la successiva
	    }
	    if (v == XsdTypesVersion.V_1_12) {
		//		<complexType name="LocalizzazioneNelComuneType">
		//		.......
		//		<element name="accessoTipo" type="string" maxOccurs="1" minOccurs="0"></element>
		//		<element name="accessoNumero" type="string" maxOccurs="1" minOccurs="0"></element>
		//		<element name="accessoDescrizione" type="string" maxOccurs="1" minOccurs="0"></element>
		//		......
		//			
		//            </complexType>
		//            
		//            <complexType name="PersonaGiuridicaType">
		//                ....
		//            	<element name="dataInizioAttivita" type="date" maxOccurs="1" minOccurs="0"></element>
		//                ....
		//            </complexType>
		if (dettaglioPratica != null) {
		    List<LocalizzazioneNelComuneType> localizzazione = dettaglioPratica.getLocalizzazione();
		    if (localizzazione != null) {
			for (LocalizzazioneNelComuneType l : localizzazione) {
			    if (l != null) {
				l.setAccessoTipo(null);
				l.setAccessoNumero(null);
				l.setAccessoDescrizione(null);
			    }
			}
		    }
		    PersonaGiuridicaType aziendaRichiedente = dettaglioPratica.getAziendaRichiedente();
		    if (aziendaRichiedente != null) {
			aziendaRichiedente.setDataInizioAttivita(null);
		    }
		    if (dettaglioPratica.getAltriSoggetti() != null) {
			List<AltriSoggettiType> as = dettaglioPratica.getAltriSoggetti();
			for (AltriSoggettiType a : as) {
			    if (a.getSoggetto() != null && a.getSoggetto().getPersonaGiuridica() != null) {
				a.getSoggetto().getPersonaGiuridica().setDataInizioAttivita(null);
			    }
			    if (a.getAnagraficaCollegata() != null && a.getAnagraficaCollegata().getPersonaGiuridica() != null) {
				a.getAnagraficaCollegata().getPersonaGiuridica().setDataInizioAttivita(null);
			    }
			}
		    }
		}
	    }
	}
    }
}
