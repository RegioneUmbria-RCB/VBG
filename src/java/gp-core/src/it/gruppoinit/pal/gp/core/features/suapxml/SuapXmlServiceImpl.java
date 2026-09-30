package it.gruppoinit.pal.gp.core.features.suapxml;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.suapxml.exceptions.GenerazioneSuapXmlException;
import it.gruppoinit.pal.gp.core.features.suapxml.upgr.VecchioParametroSuapXml;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;

@Service
public class SuapXmlServiceImpl implements ISuapXmlService {

    private static final Logger log = LoggerFactory.getLogger(SuapXmlServiceImpl.class);
    private static final String ENCODING = "UTF-8";
    private static final String ALIAS = "${ALIAS}";
    private static final String CODICE_ISTANZA = "${CODICE_ISTANZA}";
    private static final String VALIDA = "${VALIDA}";
    private VerticalizzazioniService verticalizzazioniService;
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    private MovimentiallegatiService movimentiallegatiService;
    public static final String PREFIX_NAME_PRATICA_XML_SUAP = "pratica_suap_";
    public static final String ANNOTAZIONI_DOCUMENTO = "Allegato generato automaticamente dalla funzionalità Genera XML pratica Suap";

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setVerticalizzazioniparametriService(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Override
    public byte[] generaSuapXML(String codiceComune, Integer codiceIstanza) throws GenerazioneSuapXmlException {

	try {
	    VerticalizzazioneSuapXmlServiceImpl vertSuapXMLService = new VerticalizzazioneSuapXmlServiceImpl(this.verticalizzazioniService,
		    codiceComune);
	    if (!vertSuapXMLService.isAttiva()) {
		return new byte[0];
	    }
	    log.debug("Costruisco l'url a partire da quello salvato in {}.{}", VerticalizzazioneSuapXmlServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneSuapXmlServiceImpl.PAR_URL);
	    String url = vertSuapXMLService.url();
	    url = StringUtils.replace(url, ALIAS, ORMHelper.getIdcomuneAlias());
	    url = StringUtils.replace(url, CODICE_ISTANZA, codiceIstanza.toString());
	    url = StringUtils.replace(url, VALIDA, vertSuapXMLService.valida() ? "S" : "N");
	    log.debug("Url invocato {}", url);
	    log.debug("Apro la connesione con l'url");
	    URLConnection con = new URL(url).openConnection();
	    log.debug("Recupero lo stream della response");
	    InputStream inputStream = con.getInputStream();
	    // Controllo il contenuto della response
	    String response = IOUtils.toString(inputStream, ENCODING);
	    if (StringUtils.contains(response, "[KO]")) {
		throw new GenerazioneSuapXmlException("Impossibile generare l'xml della domanda: " + StringUtils.replace(response, "[KO]", ""));
	    }
	    log.debug("Trasformo la response da stream in byte[]");
	    // Non posso usare InputStream perchè dopo avero trasformato in String viene corrotto, quindi devo riportarlo
	    // in array byte da string
	    return response.getBytes(ENCODING);
	} catch (Exception e) {
	    throw new GenerazioneSuapXmlException(e);
	}
    }

    @Override
    public List<VecchioParametroSuapXml> recuperaVecchiParametriPerUPGR() {

	String modulo = "COMPORTAMENTI_ISTANZE";
	String[] nomiParametro = new String[] { "GENERA_PRATICA_SUAP", "GENERA_PRATICA_SUAP_URL", "GENERA_PRATICA_SUAP_VALIDA" };
	List<Verticalizzazioniparametri> parametri = this.verticalizzazioniparametriService.findParametriByModulo(modulo, nomiParametro);
	if (parametri == null || parametri.isEmpty()) {
	    return new ArrayList<VecchioParametroSuapXml>();
	}
	List<VecchioParametroSuapXml> vecchiParametri = new ArrayList<VecchioParametroSuapXml>();
	for (Verticalizzazioniparametri parametro : parametri) {
	    vecchiParametri.add(VecchioParametroSuapXml.fromVerticalizzazioniparametri(parametro));
	}
	return vecchiParametri;
    }

    @Override
    public boolean isDocPresenteSuMovimento(Integer codiceMovimento) {

	if (codiceMovimento == null) {
	    throw new InvalidParameterException("Impossibile verificare la presenza del SUAP Xml su un movimento senza passare l'id del movimento");
	}
	log.debug("Cerco tra gli allegati del movimento con codice {} se c'è quello che comincia con {} ", codiceMovimento,
		SuapXmlServiceImpl.PREFIX_NAME_PRATICA_XML_SUAP);
	List<Movimentiallegati> allegatiMov = this.movimentiallegatiService.findByMovimento(codiceMovimento);
	for (Movimentiallegati movimentiallegati : allegatiMov) {
	    if (movimentiallegati.getDescrizione().startsWith(SuapXmlServiceImpl.PREFIX_NAME_PRATICA_XML_SUAP)) {
		log.debug("Il file {} è presente ", SuapXmlServiceImpl.PREFIX_NAME_PRATICA_XML_SUAP);
		return true;
	    }
	}
	log.debug("Il file {} NON è presente ", SuapXmlServiceImpl.PREFIX_NAME_PRATICA_XML_SUAP);
	return false;
    }
}
