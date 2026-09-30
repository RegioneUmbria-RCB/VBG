package it.gruppoinit.nlaenti.service.stc;

import it.gruppoinit.nlaenti.service.NlaEntiService;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.TestNLARequest;
import it.init.sigepro.rte.TestNLAResponse;
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.RiferimentiAllegatoType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.XsdTypesVersion;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

@Endpoint
public class NlaWebService {

    private static final Logger log = LoggerFactory.getLogger(NlaWebService.class);
    private static final String MESSAGES_NAMESPACE = "http://sigepro.init.it/rte";
    private static final String INSERIMENTO_ATTIVITA = "InserimentoAttivitaNLARequest";
    private static final String INSERIMENTO_PRATICA = "InserimentoPraticaNLARequest";
    private static final String RICHIESTA_PRATICA = "RichiestaPraticaNLARequest";
    private static final String ALLEGATO_BINARIO = "AllegatoBinarioNLARequest";
    private static final String TEST_NLA = "TestNLARequest";
    private StcWebServiceClient stcWebServiceClient;
    private NlaEntiService nlaEntiService;

    public void setStcWebServiceClient(StcWebServiceClient stcWebServiceClient) {

	this.stcWebServiceClient = stcWebServiceClient;
    }

    public void setNlaEntiService(NlaEntiService nlaEntiService) {

	this.nlaEntiService = nlaEntiService;
    }

    @PayloadRoot(localPart = INSERIMENTO_ATTIVITA, namespace = MESSAGES_NAMESPACE)
    public InserimentoAttivitaNLAResponse inserimentoAttivita(InserimentoAttivitaNLARequest request) {

	stcWebServiceClient.checkToken(request.getToken());
	InserimentoAttivitaNLAResponse response = new InserimentoAttivitaNLAResponse();
	RichiestaPraticaRequest praticaRequest = new RichiestaPraticaRequest();
	praticaRequest.setSportelloDestinatario(request.getSportelloMittente());
	praticaRequest.setSportelloMittente(request.getSportelloDestinatario());
	praticaRequest.setToken(request.getToken());
	RiferimentiPraticaType rifPratica = new RiferimentiPraticaType();
	rifPratica.setIdPratica(request.getDatiAttivita().getIdPratica());
	praticaRequest.setRifPratica(rifPratica);
	RichiestaPraticaResponse praticaResponse = stcWebServiceClient.richiestaPratica(praticaRequest);
	if (praticaResponse.getDettaglioErrore() != null && !praticaResponse.getDettaglioErrore().isEmpty()) {
	    String errMsg = getErrorMsg(request, praticaResponse);
	    log.error("inserimentoAttivita(): {}", errMsg);
	    throw new RuntimeException(errMsg);
	}
	this.recuperaAllegati(request);
	RiferimentiAttivitaType rifAtt = nlaEntiService.gestioneComunicazioneEnte(request, praticaResponse.getDettaglioPratica()
		.getDettaglioPratica());
	response.setDettaglioAttivita(rifAtt);
	return response;
    }

    @PayloadRoot(localPart = INSERIMENTO_PRATICA, namespace = MESSAGES_NAMESPACE)
    public InserimentoPraticaNLAResponse inserimentoPratica(InserimentoPraticaNLARequest request) {

	InserimentoPraticaNLAResponse response = new InserimentoPraticaNLAResponse();
	RiferimentiPraticaType riferimentiPraticaType = new RiferimentiPraticaType();
	riferimentiPraticaType.setIdPratica(request.getDettaglioPratica().getIdPratica());
	riferimentiPraticaType.setNumeroPratica(request.getDettaglioPratica().getNumeroPratica());
	response.setDettaglioPratica(riferimentiPraticaType);
	return response;
    }

    @PayloadRoot(localPart = RICHIESTA_PRATICA, namespace = MESSAGES_NAMESPACE)
    public RichiestaPraticaNLAResponse richiestaPratica(RichiestaPraticaNLARequest request) {

	RichiestaPraticaNLAResponse response = new RichiestaPraticaNLAResponse();
	ErroreType e = new ErroreType();
	e.setNumeroErrore("NLA_ENTI_ERR_03");
	e.setDescrizione("Il nodo NLA non dispone della funzionalità di visura della pratica");
	response.getDettaglioErrore().add(e);
	return response;
    }

    @PayloadRoot(localPart = ALLEGATO_BINARIO, namespace = MESSAGES_NAMESPACE)
    public AllegatoBinarioNLAResponse allegatoBinario(AllegatoBinarioNLARequest request) {

	AllegatoBinarioNLAResponse response = new AllegatoBinarioNLAResponse();
	response.setBinaryData(null);
	response.setFileName("");
	response.setMimeType("");
	return response;
    }

    @PayloadRoot(localPart = TEST_NLA, namespace = MESSAGES_NAMESPACE)
    public TestNLAResponse testNLA(TestNLARequest request) {

	TestNLAResponse response = new TestNLAResponse();
	response.setNlaXsdVersion(XsdNlaVersion.V_1_13);
	response.setTypesXsdVersion(XsdTypesVersion.V_1_13);
	return response;
    }

    private void recuperaAllegati(InserimentoAttivitaNLARequest request) {

	List<DocumentiType> list = request.getDatiAttivita().getDocumenti();
	if (list != null) {
	    for (DocumentiType documento : list) {
		AllegatiType allegato = documento.getAllegati();
		if (allegato != null) {
		    AllegatoBinarioType allegatoBin = allegato.getFile();
		    if (allegatoBin != null) {
			allegatoBin.setBinaryData(allegatoBin.getBinaryData());
			allegatoBin.setFileName(allegatoBin.getFileName());
			allegatoBin.setMimeType(allegatoBin.getMimeType());
		    } else if (StringUtils.isNotBlank(allegato.getId())) {
			AllegatoBinarioRequest allRequest = new AllegatoBinarioRequest();
			allRequest.setSportelloDestinatario(request.getSportelloMittente());
			allRequest.setSportelloMittente(request.getSportelloDestinatario());
			allRequest.setToken(request.getToken());
			RiferimentiAllegatoType riferimentiAllegatoType = new RiferimentiAllegatoType();
			riferimentiAllegatoType.setIdDocumento(allegato.getId());
			riferimentiAllegatoType.setIdAllegato(allegato.getId());
			riferimentiAllegatoType.setIdAttivita(request.getDatiAttivita().getIdAttivita());
			riferimentiAllegatoType.setIdPratica(request.getDatiAttivita().getIdPratica());
			allRequest.setRiferimentiAllegato(riferimentiAllegatoType);
			AllegatoBinarioResponse allResponse = stcWebServiceClient.allegatoBinario(allRequest);
			allegatoBin = new AllegatoBinarioType();
			allegatoBin.setBinaryData(allResponse.getBinaryData());
			allegatoBin.setFileName(allResponse.getFileName());
			allegatoBin.setMimeType(allResponse.getMimeType());
			allegato.setFile(allegatoBin);
		    }
		}
	    }
	}
    }

    private String getErrorMsg(InserimentoAttivitaNLARequest request, RichiestaPraticaResponse praticaResponse) {

	StringBuffer buf = new StringBuffer();
	buf.append("[");
	buf.append(request.getSportelloDestinatario().getIdEnte());
	buf.append("-");
	buf.append(request.getSportelloDestinatario().getIdSportello());
	buf.append("]: ");
	buf.append("Errore durante il recupero della pratica dall'NLA mittente: ");
	buf.append("[");
	buf.append(request.getSportelloMittente().getIdEnte());
	buf.append("-");
	buf.append(request.getSportelloMittente().getIdSportello());
	buf.append("], errCod=");
	buf.append(praticaResponse.getDettaglioErrore().get(0).getNumeroErrore());
	buf.append(", errDesc=");
	buf.append(praticaResponse.getDettaglioErrore().get(0).getDescrizione());
	return buf.toString();
    }
}
