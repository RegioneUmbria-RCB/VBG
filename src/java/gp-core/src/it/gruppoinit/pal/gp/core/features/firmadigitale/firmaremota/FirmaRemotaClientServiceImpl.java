package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.ConfigurazioneParametroWs;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.ConfigurazioneWs;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.ConfigurazioneWsResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.FirmaRemotaClient;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.NuovoProcessoResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.RecuperaFileFirmatoWsResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.RecuperaParametriRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.RecuperaParametriResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.VerificaStatoWSResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione.FirmeRemoteDAO;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.AvviaProcessoResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.ConfigurazioneParametro;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.RecuperaFileFirmatoResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.VerificaStatoResponse;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class FirmaRemotaClientServiceImpl implements FirmaRemotaClientService {

    private static final Logger logger = LoggerFactory.getLogger(FirmaRemotaClientServiceImpl.class);
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    protected ApplicationContext context;
    @Autowired
    protected FirmeRemoteDAO firmeRemoteDao;
    @Autowired
    protected UserSecurityService userSecurityService;
    @Autowired
    protected DocumentiDaFirmareService documentiDaFirmareService;

    @Override
    public RecuperaParametriResponse recuperaParametri(RecuperaParametriRequest request) {

	logger.info("Inizio chiamata a recuperaParametri");
	ConfigurazioneWsResponse response = new ConfigurazioneWsResponse();
	try {
	    response = new FirmaRemotaClient(request.getEndpoint()).getConfigurazione();
	} catch (FunzioneBusinessRemotaException e) {
	    logger.error(e.getMessage());
	}
	RecuperaParametriResponse retVal = RecuperaParametriResponse.fromConfigurazioneResponse(response);
	for (ConfigurazioneParametro parametro : retVal.getParametri()) {
	    String messagesProperty = "firmaremota." + request.getNomeComponente() + "." + parametro.getChiave();
	    parametro.setEtichetta(Utilities.getMessageFromBundle(context, messagesProperty.toLowerCase()));
	}
	return retVal;
    }

    @Override
    public AvviaProcessoResponse avviaProcesso(String endPoint, List<ConfigurazioneParametro> parametri) {

	NuovoProcessoResponse response = new NuovoProcessoResponse();
	try {
	    ConfigurazioneWs wsConfig = new ConfigurazioneWs();
	    for (ConfigurazioneParametro parametro : parametri) {
		ConfigurazioneParametroWs wsParametro = new ConfigurazioneParametroWs();
		wsParametro.setChiave(parametro.getChiave());
		wsParametro.setValore(parametro.getValore());
		wsConfig.getParametri().add(wsParametro);
	    }
	    response = new FirmaRemotaClient(endPoint).avviaProcesso(wsConfig);
	} catch (FunzioneBusinessRemotaException e) {
	    logger.error(e.getMessage());
	}
	return AvviaProcessoResponse.fromNuovoProcessoResponse(response);
    }

    @Override
    public void aggiungiDocumenti(String endpoint, String sessionId, List<Integer> codiciOgggetto) {

	FirmaRemotaClient client = new FirmaRemotaClient(endpoint);
	for (Integer idOggetto : codiciOgggetto) {
	    Oggetti oggetto = this.oggettiService.findById(new PkId(idOggetto));
	    oggetto = oggettiService.verificaConvertiPdf(oggetto);
	    String nomeFile = oggetto.getNomefile();
	    String guid = this.oggettiMetadatiService.getUIDFromCodiceOggetto(idOggetto);
	    InputStream is = this.oggettiService.getOggettoAsInputStream(idOggetto);
	    try {
		client.aggiungiDocumento(sessionId, guid, nomeFile, is);
	    } catch (FunzioneBusinessRemotaException e) {
		logger.error(e.getMessage());
	    }
	}
    }

    @Override
    public void firmaDocumenti(String endpoint, String sessionId, List<ConfigurazioneParametro> parametri, List<Integer> codiciOgggetto) {

	try {
	    FirmaRemotaClient client = new FirmaRemotaClient(endpoint);
	    List<String> guidDocumenti = new ArrayList<String>();
	    for (Integer idOggetto : codiciOgggetto) {
		String guid = this.oggettiMetadatiService.getUIDFromCodiceOggetto(idOggetto);
		guidDocumenti.add(guid);
	    }
	    ConfigurazioneWs wsConfig = new ConfigurazioneWs();
	    for (ConfigurazioneParametro parametro : parametri) {
		ConfigurazioneParametroWs wsParametro = new ConfigurazioneParametroWs();
		wsParametro.setChiave(parametro.getChiave());
		wsParametro.setValore(parametro.getValore());
		wsConfig.getParametri().add(wsParametro);
	    }
	    client.firmaDocumento(sessionId, wsConfig, guidDocumenti);
	    for (Integer codiceOggetto : codiciOgggetto) {
		//1. Rimuovo eventuale conservazione sospesa
		this.oggettiMetadatiService.rimuoviConservazioneSospesa(codiceOggetto);
		//2. Rimuovo eventuale blocco
		Integer codiceResponsabile = ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
		this.oggettiService.updateFileRimuoviBloccoModifica(codiceOggetto, codiceResponsabile);
		//3. Lo tolgo dai documenti da firmare se presente
		List<Integer> idDocsDaFirmare = this.documentiDaFirmareService.findIdDocumentiDaFirmare(codiceOggetto, codiceResponsabile);
		for (Integer idDocDaFirmare : idDocsDaFirmare) {
		    this.documentiDaFirmareService.updateSegnaComeFirmato(idDocDaFirmare);
		}
	    }
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public VerificaStatoResponse verificaStato(String endpoint, String sessionId) {

	VerificaStatoResponse response = new VerificaStatoResponse();
	try {
	    FirmaRemotaClient client = new FirmaRemotaClient(endpoint);
	    VerificaStatoWSResponse wsResponse = client.verificaStato(sessionId);
	    response = VerificaStatoResponse.fromWSResponse(wsResponse);
	} catch (FunzioneBusinessRemotaException e) {
	    logger.error(e.getMessage());
	}
	return response;
    }

    @Override
    public RecuperaFileFirmatoResponse recuperaFileFirmato(String endpoint, String sessionId, Integer codiceOggetto) {

	try {
	    RecuperaFileFirmatoResponse response = new RecuperaFileFirmatoResponse();
	    String guid = this.oggettiMetadatiService.getUIDFromCodiceOggetto(codiceOggetto);
	    FirmaRemotaClient client = new FirmaRemotaClient(endpoint);
	    RecuperaFileFirmatoWsResponse wsResponse = client.recuperaFileFirmato(sessionId, guid);
	    //1. Recupero la classe oggetti originale
	    Oggetti oggetto = this.oggettiService.findById(new PkId(codiceOggetto));
	    //2. Setto il bytearray
	    oggetto.setOggetto(IOUtils.toByteArray(wsResponse.getContent()));
	    //3. Chiamo  updateOggettoFirmatoCAdES o updateOggettoFirmatoPAdES a seconda del tipo di firma richiesta
	    if (wsResponse.getTipoFirma().equalsIgnoreCase("CADES")) {
		this.oggettiService.updateOggettoFirmatoCAdES(oggetto);
	    } else {
		this.oggettiService.updateOggettoFirmatoPAdES(oggetto);
	    }
	    response.setGuid(wsResponse.getGuid());
	    response.setCodiceOggetto(codiceOggetto);
	    response.setNomeFile(oggetto.getNomefile());
	    return response;
	} catch (FunzioneBusinessRemotaException e) {
	    logger.error(e.getMessage());
	} catch (IOException e) {
	    logger.error(e.getMessage());
	}
	return null;
    }
}
