package it.gruppoinit.service.helper;

import it.gruppoinit.domain.helper.VerticalizzazioniHelper;
import it.gruppoinit.service.VerticalizzazioniHelperService;
import it.gruppoinit.sigepro.definitions.regole.RegoleWSClient;
import it.gruppoinit.sigepro.schemas.messages.regole.ParametroRegolaRequest;
import it.gruppoinit.sigepro.schemas.messages.regole.RegolaRequest;
import it.gruppoinit.sigepro.schemas.messages.regole.RegolaResponse;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerticalizzazioniHelperServiceImpl implements VerticalizzazioniHelperService {

    private static final Logger log = LoggerFactory.getLogger(VerticalizzazioniHelperServiceImpl.class);
    @Autowired
    private RegoleWSClient regoleWSClient;

    @Override
    public VerticalizzazioniHelper getRegole(String software, String token) {

	return getRegole(software, null, token);
    }

    @Override
    public String getRegola(String nomeParametro, String software, String codicecomune, String token) {

	String res = "";
	try {
	    ParametroRegolaRequest parametroRegolaRequest = new ParametroRegolaRequest();
	    parametroRegolaRequest.setCodiceComune(codicecomune);
	    parametroRegolaRequest.setNomeParametro(nomeParametro);
	    parametroRegolaRequest.setNomeRegola("NLA_INFOCAMERE");
	    parametroRegolaRequest.setSoftware(software);
	    parametroRegolaRequest.setToken(token);
	    res = regoleWSClient.getParametroRegola(parametroRegolaRequest);
	} catch (Exception e) {
	    log.error("getRegola# Non è stato trovata tra le regole il valore del parametro {}. E = {}", nomeParametro, e);
	}
	return res;
    }

    @Override
    public VerticalizzazioniHelper getRegole(String software, String codicecomune, String token) {

	log.debug("getRegole# Call..");
	VerticalizzazioniHelper verticalizzazioniHelper = new VerticalizzazioniHelper();
	try {
	    RegolaResponse res = new RegolaResponse();
	    RegolaRequest req = new RegolaRequest();
	    req.setNomeRegola("NLA_INFOCAMERE");
	    req.setSoftware(software);
	    req.setRecuperaParametri(true);
	    if (StringUtils.isNotBlank(codicecomune)) {
		req.setCodiceComune(codicecomune);
	    }
	    req.setToken(token);
	    res = regoleWSClient.getRegole(req);
	    List<it.gruppoinit.sigepro.schemas.messages.regole.ParametroType> listParametri = res.getListaParametri();
	    verticalizzazioniHelper.setParametri(listParametri);
	    for (it.gruppoinit.sigepro.schemas.messages.regole.ParametroType parametroType : listParametri) {
		if (parametroType.getDescrizione().equals("PSW_WS")) {
		    verticalizzazioniHelper.setPSW_WS(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("URL_WS")) {
		    verticalizzazioniHelper.setURL_WS(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("USER_WS")) {
		    verticalizzazioniHelper.setUSER_WS(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("INFO_SCHEMA_VERSIONE")) {
		    verticalizzazioniHelper.setINFO_SCHEMA_VERSIONE(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("CODICE_AMMINISTRAZIONE")) {
		    verticalizzazioniHelper.setCODICE_AMMINISTRAZIONE(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("CODICE_AOO")) {
		    verticalizzazioniHelper.setCODICE_AOO(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("IDENTIFICATIVO_SUAP")) {
		    verticalizzazioniHelper.setIDENTIFICATIVO_SUAP(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("DESCRIZIONE_SUAP")) {
		    verticalizzazioniHelper.setDESCRIZIONE_SUAP(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("CODICE_AMMINISTRAZIONE_DEST")) {
		    verticalizzazioniHelper.setCODICE_AMMINISTRAZIONE_DEST(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("CODICE_AMMINISTRAZIONE_DEST")) {
		    verticalizzazioniHelper.setCODICE_AMMINISTRAZIONE_DEST(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("IDENTIFICATIVO_SPORTELLO_DEST")) {
		    verticalizzazioniHelper.setIDENTIFICATIVO_SPORTELLO_DEST(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("PEC_SPORTELLO_DEST")) {
		    verticalizzazioniHelper.setPEC_SPORTELLO_DEST(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("COD_MOV_RIENTRO_INTEGRAZ_DOC")) {
		    verticalizzazioniHelper.setCOD_MOV_RIENTRO_INTEGRAZ_DOC(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		} else if (parametroType.getDescrizione().equals("SALVA_XML_COMUNICAZIONE")) {
		    verticalizzazioniHelper.setSALVA_XML_COMUNICAZIONE(StringUtils.defaultIfEmpty(parametroType.getValore(), ""));
		}
	    }
	} catch (Exception e) {
	    log.error("getRegole# {}", e);
	    throw new RuntimeException(e);
	}
	log.debug("getRegole# End call..");
	return verticalizzazioniHelper;
    }
}
