package it.gruppoinit.nlapec.service;

import it.gruppoinit.impresainungiorno.schema.RiepilogoPraticaSUAP;
import it.gruppoinit.nlapec.service.sigepro.SigeproService;
import it.gruppoinit.nlapec.service.stc.StcHelper;
import it.gruppoinit.nlapec.service.stc.StcWebServiceClient;
import it.gruppoinit.nlapec.util.AllegatiUtil;
import it.gruppoinit.nlapec.util.MimeMessageHandler;
import it.gruppoinit.nlapec.util.OriginalMessage;
import it.gruppoinit.nlapec.util.PECMessage;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.SportelloType;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PECProcessor {

    private static final Logger log = LoggerFactory.getLogger(PECProcessor.class);
    private StcWebServiceClient stcClient;
    private SigeproService sigeproService;

    public static DettaglioPraticaType process(PECMessage pecMessage) {

	DettaglioPraticaType dettaglioPraticaType = null;
	try {
	    //TODO il PECProcessor ad oggi è vincolato a processare solo le pec secondo dpr160 art.5, invece dovrebbe 
	    //essere configurabile tramite la registrazione di classi che processano in modalità differenti
	    OriginalMessage om = MimeMessageHandler.getOriginalMessage(pecMessage);
	    if (om != null) {
		if (StcHelper.checkSubjectDPR160(om)) {
		    dettaglioPraticaType = StcHelper.populatePraticaDPR160(om);
		} else {
		    log.warn("process: il pecMessage non è di tipo DPR160 Art.5");
		}
	    } else {
		log.error("process: il pecMessage non contiene il messaggio originale");
	    }
	} catch (Exception e) {
	    log.error("process: {}", e.getMessage());
	}
	return dettaglioPraticaType;
    }

    public InserimentoPraticaResponse processPEC_ComUnicaLombardia(PECMessage pecMessage, String tmpPath, ArrayList<String> listaFileAttachment,
	    String stcToken, String idcomuneAlias, String software) throws Exception {

	InserimentoPraticaResponse response = null;
	try {
	    InputStream is = AllegatiUtil.getInputStreamModelloRiepilogo(tmpPath, listaFileAttachment);
	    if (is != null) {
		RiepilogoPraticaSUAP pratica = StcHelper.parsePraticaXML(is);
		is.close();
		DettaglioPraticaType dettaglioPraticaSTC = StcHelper.populatePratica(pratica, pecMessage, tmpPath, listaFileAttachment);
		SportelloType sportelloMittente = new SportelloType();
		SportelloType sportelloDestinatario = new SportelloType();
		sportelloDestinatario.setIdNodo(""); // l' IdNodo lo setto successivamente
		sportelloDestinatario.setIdEnte(idcomuneAlias);
		sportelloDestinatario.setIdSportello(software);
		InserimentoPraticaRequest ipr = new InserimentoPraticaRequest();
		ipr.setDettaglioPratica(dettaglioPraticaSTC);
		ipr.setSportelloMittente(sportelloMittente);
		ipr.setSportelloDestinatario(sportelloDestinatario);
		ipr.setToken(stcToken);
		String token = stcClient.login();
		ipr.setToken(token);
		response = stcClient.inserisciPratica2(ipr);
	    } else {
		log.warn("process: non è stato possibile recuperare il Modello di Riepilogo");
	    }
	} catch (Exception e) {
	    log.error("processPEC_ComUnicaLombardia: {}", e.getMessage());
	    throw e;
	}
	return response;
    }

    public InserimentoPraticaResponse processPEC_Cittadino(PECMessage pecMessage, String tmpPath, ArrayList<String> listaFileAttachment,
	    String stcToken, String idcomuneAlias, String software, Map<String, String> altriParametriVerticalizzazione, Properties connectionProps)
	    throws Exception {

	InserimentoPraticaResponse response = null;
	try {
	    StcHelper stcHelper = new StcHelper();
	    DettaglioPraticaType dettaglioPraticaSTC = stcHelper.populatePratica(pecMessage, software, tmpPath, listaFileAttachment,
		    altriParametriVerticalizzazione, connectionProps, sigeproService);
	    InserimentoPraticaRequest ipr = new InserimentoPraticaRequest();
	    SportelloType sportelloMittente = new SportelloType();
	    SportelloType sportelloDestinatario = new SportelloType();
	    //
	    sportelloDestinatario.setIdNodo(""); // l' IdNodo lo setto successivamente
	    sportelloDestinatario.setIdEnte(idcomuneAlias);
	    sportelloDestinatario.setIdSportello(software);
	    //
	    ipr.setDettaglioPratica(dettaglioPraticaSTC);
	    ipr.setSportelloMittente(sportelloMittente);
	    ipr.setSportelloDestinatario(sportelloDestinatario);
	    ipr.setToken(stcToken);
	    String token = stcClient.login();
	    ipr.setToken(token);
	    response = stcClient.inserisciPratica2(ipr);
	} catch (Exception e) {
	    log.error("processPEC_Cittadino: {}", e.getMessage());
	    throw e;
	}
	return response;
    }

    public InserimentoPraticaResponse processPEC_NonFormattata(PECMessage pecMessage, String tmpPath, ArrayList<String> listaFileAttachment,
	    String stcToken, String idcomuneAlias, String software, Map<String, String> altriParametriVerticalizzazione, Properties connectionProps)
	    throws Exception {

	InserimentoPraticaResponse response = null;
	try {
	    StcHelper stcHelper = new StcHelper();
	    DettaglioPraticaType dettaglioPraticaSTC = stcHelper.populatePraticaNonFormttata(pecMessage, tmpPath, listaFileAttachment,
		    altriParametriVerticalizzazione, connectionProps, sigeproService);
	    InserimentoPraticaRequest ipr = new InserimentoPraticaRequest();
	    SportelloType sportelloMittente = new SportelloType();
	    SportelloType sportelloDestinatario = new SportelloType();
	    //
	    sportelloDestinatario.setIdNodo(""); // l' IdNodo lo setto successivamente
	    sportelloDestinatario.setIdEnte(idcomuneAlias);
	    sportelloDestinatario.setIdSportello(software);
	    //
	    ipr.setDettaglioPratica(dettaglioPraticaSTC);
	    ipr.setSportelloMittente(sportelloMittente);
	    ipr.setSportelloDestinatario(sportelloDestinatario);
	    ipr.setToken(stcToken);
	    String token = stcClient.login();
	    ipr.setToken(token);
	    response = stcClient.inserisciPratica2(ipr);
	} catch (Exception e) {
	    log.error("processPEC_Cittadino: {}", e.getMessage());
	    throw e;
	}
	return response;
    }

    public StcWebServiceClient getStcClient() {

	return stcClient;
    }

    public void setStcClient(StcWebServiceClient stcClient) {

	this.stcClient = stcClient;
    }

    public SigeproService getSigeproService() {

	return sigeproService;
    }

    public void setSigeproService(SigeproService sigeproService) {

	this.sigeproService = sigeproService;
    }
}
