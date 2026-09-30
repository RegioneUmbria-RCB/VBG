package it.gruppoinit.nlaproxy.service.stc;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio.DatiRispostaREA;
import it.gruppoinit.pdd.ri.service.RegistroImpreseService;
import it.gruppoinit.pdd.ri.service.RegistroImpreseService.TIPO_NOTIFICA_ENUM;
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
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.definitions.Nla;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.ValoreParametroType;
import it.init.sigepro.rte.types.XsdTypesVersion;

@WebService(targetNamespace = "http://sigepro.init.it/rte/definitions", name = "Nla", serviceName = "NlaService", portName = "NlaSoap11", endpointInterface = "it.init.sigepro.rte.definitions.Nla")
public class NlaWebService implements Nla {

    public static final String ALTRI_DATI_COMUNICAZIONE_SCIA = "Apertura";
    public static final String ALTRI_DATI_COMUNICAZIONE_ESITO = "Esito pratica";
    public static final String NLAREQUEST_ALTRIDATI_PARAMNAME_TIPOOPERAZIONE = "tipo_operazione";
    public static final String ALTRI_DATI_VISURA_PDF = "visurapdf";
    public static final String ALTRI_DATI_VISURA_XML = "visuraxml";
    private static final Logger log = LoggerFactory.getLogger(NlaWebService.class);
    private StcWebServiceClient stcWebServiceClient;
    private RegistroImpreseService registroImpreseService;

    public void setStcWebServiceClient(StcWebServiceClient stcWebServiceClient) {

	this.stcWebServiceClient = stcWebServiceClient;
    }

    public void setRegistroImpreseService(RegistroImpreseService registroImpreseService) {

	this.registroImpreseService = registroImpreseService;
    }

    @Override
    public InserimentoPraticaNLAResponse inserimentoPraticaNLA(InserimentoPraticaNLARequest inserimentoPraticaNLARequest) {

	InserimentoPraticaNLAResponse inserimentoPraticaNLAResponse = new InserimentoPraticaNLAResponse();
	RiferimentiPraticaType rifPratica = new RiferimentiPraticaType();
	rifPratica.setIdPratica(getIdPratica(inserimentoPraticaNLARequest.getSportelloMittente().getIdEnte(),
		inserimentoPraticaNLARequest.getDettaglioPratica().getIdPratica()));
	rifPratica.setNumeroPratica(inserimentoPraticaNLARequest.getDettaglioPratica().getNumeroPratica());
	rifPratica.setDataPratica(inserimentoPraticaNLARequest.getDettaglioPratica().getDataPratica());
	rifPratica.setDataProtocolloGenerale(inserimentoPraticaNLARequest.getDettaglioPratica().getDataProtocolloGenerale());
	rifPratica.setNumeroProtocolloGenerale(inserimentoPraticaNLARequest.getDettaglioPratica().getNumeroProtocolloGenerale());
	inserimentoPraticaNLAResponse.setDettaglioPratica(rifPratica);
	return inserimentoPraticaNLAResponse;
    }

    @Override
    public RichiestaPraticheListaNLAResponse richiestaPraticheListaNLA(RichiestaPraticheListaNLARequest richiestaPraticheListaNLARequest) {

	throw new NotImplementedException("Metodo non Implementato");
    }

    @Override
    public AllegatoBinarioNLAResponse allegatoBinarioNLA(AllegatoBinarioNLARequest allegatoBinarioNLARequest) {

	throw new NotImplementedException("Metodo non Implementato");
    }

    @Override
    public RichiestaPraticaNLAResponse richiestaPraticaNLA(RichiestaPraticaNLARequest richiestaPraticaNLARequest) {

	throw new NotImplementedException("Metodo non Implementato");
    }

    @Override
    public InserimentoAttivitaNLAResponse inserimentoAttivitaNLA(InserimentoAttivitaNLARequest request) {

	log.debug("inserimentoAttivita() - invocata l'operazione InserimentoAttivitaNLA");
	try {
	    stcWebServiceClient.checkToken(request.getToken());
	} catch (Exception e) {
	    log.error("Errore nella verifica del token STC: {}" + e.getMessage(), e);
	    throw new RuntimeException("Errore nella verifica del token STC: " + e.getMessage(), e);
	}
	String tipoOperazione = getTipoOperazioneFromRequest(request);
	log.debug("{} richiesta={}", NLAREQUEST_ALTRIDATI_PARAMNAME_TIPOOPERAZIONE, tipoOperazione);
	DatiRispostaREA reaResponse = null;
	if (ALTRI_DATI_COMUNICAZIONE_SCIA.equalsIgnoreCase(tipoOperazione)) {
	    reaResponse = registroImpreseService.notificaComunicazioneREA(request, TIPO_NOTIFICA_ENUM.AVVIO);
	} else if (ALTRI_DATI_COMUNICAZIONE_ESITO.equalsIgnoreCase(tipoOperazione)) {
	    reaResponse = registroImpreseService.notificaComunicazioneREA(request, TIPO_NOTIFICA_ENUM.ESITO);
	} else {
	    log.error("Tipo Operazione non valida per la notifica Registro Imprese: {}", tipoOperazione);
	    throw new RuntimeException("Tipo Operazione non valida per la notifica Registro Imprese: " + tipoOperazione);
	}
	if (reaResponse == null) {
	    log.error("Risposta nulla tornata da Registro Imprese");
	    throw new RuntimeException("Risposta nulla tornata da Registro Imprese");
	}
	// 2 INSERIRE IN ISTANZE_COMUNICAZIONI_RI CON IL CODICEOGGETTO RESTITUITO
	InserimentoAttivitaNLAResponse response = new InserimentoAttivitaNLAResponse();
	RiferimentiAttivitaType rifAtt = new RiferimentiAttivitaType();
	rifAtt.setIdAttivita(getIdAttivita(request.getSportelloMittente().getIdEnte(), request.getDatiAttivita().getIdAttivita()));
	rifAtt.setIdPratica(getIdPratica(request.getSportelloMittente().getIdEnte(), request.getDatiAttivita().getIdPratica()));
	response.setDettaglioAttivita(rifAtt);
	return response;
    }

    @Override
    public TestNLAResponse testNLA(TestNLARequest request) {

	TestNLAResponse response = new TestNLAResponse();
	response.setNlaXsdVersion(XsdNlaVersion.V_1_13);
	response.setTypesXsdVersion(XsdTypesVersion.V_1_13);
	return response;
    }

    private String getTipoOperazioneFromRequest(InserimentoAttivitaNLARequest request) {

	String retVal = null;
	List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
	ParametroType tipoOpParam = null;
	for (ParametroType param : altriDati) {
	    if (param.getNome().equalsIgnoreCase(NLAREQUEST_ALTRIDATI_PARAMNAME_TIPOOPERAZIONE)) {
		tipoOpParam = param;
		break;
	    }
	}
	if (tipoOpParam != null) {
	    List<ValoreParametroType> paramValues = tipoOpParam.getValore();
	    if (!paramValues.isEmpty()) {
		retVal = paramValues.get(0).getCodice();
	    }
	}
	return retVal;
    }

    public static String getValoreAltroDato(InserimentoAttivitaNLARequest request, String nomeParametro) {

	String retVal = null;
	List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
	ParametroType tipoOpParam = null;
	for (ParametroType param : altriDati) {
	    if (param.getNome().equalsIgnoreCase(nomeParametro)) {
		tipoOpParam = param;
		break;
	    }
	}
	if (tipoOpParam != null) {
	    List<ValoreParametroType> paramValues = tipoOpParam.getValore();
	    if (!paramValues.isEmpty()) {
		retVal = paramValues.get(0).getCodice();
	    }
	}
	return retVal;
    }

    public static String getIdAttivita(String idEnteMittente, String idAttivita) {

	return idEnteMittente + idAttivita;
    }

    public static String getIdPratica(String idEnteMittente, String idPratica) {

	return idEnteMittente + idPratica;
    }

    @Override
    @WebResult(name = "AggiungiDocumentiNLAResponse", targetNamespace = "http://sigepro.init.it/rte", partName = "AggiungiDocumentiNLAResponse")
    @WebMethod(operationName = "AggiungiDocumentiNLA")
    public AggiungiDocumentiNLAResponse aggiungiDocumentiNLA(
	    @WebParam(partName = "AggiungiDocumentiNLARequest", name = "AggiungiDocumentiNLARequest", targetNamespace = "http://sigepro.init.it/rte") AggiungiDocumentiNLARequest arg0) {

	throw new NotImplementedException("Metodo non Implementato");
    }
}
