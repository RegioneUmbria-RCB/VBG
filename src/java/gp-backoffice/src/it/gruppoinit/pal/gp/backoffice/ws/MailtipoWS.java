/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.ws;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo.MailtipoFrontendRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo.MailtipoRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo.MailtipoRequestProtokol;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo.MailtipoResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo.MailtipoResponseProtokol;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

/**
 * Web Service per il recupero della mailtipo dopo aver elaborato i segnaposto
 * 
 * @author fabrizioc
 * 
 */
@WebService(serviceName = "MailtipoService", portName = "MailtipoSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/mailtipo", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.mailtipo.Mailtipo")
public class MailtipoWS extends BaseWS implements it.gruppoinit.pal.gp.backoffice.definitions.mailtipo.Mailtipo {

    private static final Logger log = LoggerFactory.getLogger(MailtipoWS.class);
    private MailtipoService mailtipoService;
    private IstanzeService istanzeService;
    private MovimentiService movimentiService;

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Override
    public MailtipoResponse mailtipo(MailtipoRequest request) {

	MailtipoResponse response = new MailtipoResponse();
	log.debug("mailtipo(): MailtipoRequest[token=" +
		request.getToken() +
		", codicemailtipo=" +
		request.getCodicemailtipo() +
		", codiceistanza=" +
		request.getCodiceistanza() +
		", codicemovimento=" +
		request.getCodicemovimento());
	setORMHelper(null, request.getToken());
	try {
	    if (request.getCodicemailtipo() != 0) {
		Mailtipo mailtipo = mailtipoService.findById(new PkId(request.getCodicemailtipo()));
		Software software = mailtipo.getSoftware();
		ORMHelper.setSoftware(software.getCodice());
		if (request.getCodicemovimento() != null) {
		    Movimenti movimento = movimentiService.findById(new PkId(request.getCodicemovimento()));
		    Mailtipo mailtiporeplace = mailtipoService.replaceOggettoCorpo(mailtipo, null, movimento);
		    response.setCorpo(mailtiporeplace.getCorpo());
		    response.setOggetto(mailtiporeplace.getOggetto());
		} else if (request.getCodiceistanza() != null) {
		    Istanze istanza = istanzeService.findById(new PkId(request.getCodiceistanza()));
		    Mailtipo mailtiporeplace = mailtipoService.replaceOggettoCorpo(mailtipo, istanza, null);
		    response.setCorpo(mailtiporeplace.getCorpo());
		    response.setOggetto(mailtiporeplace.getOggetto());
		} else {
		    throw new RuntimeException("codice istanza o codice movimento sono obbligatori.");
		}
		log.debug("mailtipo(): oggetto={}, corpo={}", response.getOggetto(), response.getCorpo());
		return response;
	    } else {
		throw new RuntimeException("codice mailtipo obbligatorio.");
	    }
	} catch (Exception e) {
	    log.error("mailtipo():", e);
	    throw new RuntimeException("BACKOFFICE WS MAILTIPO: Errore! [" + e.getMessage() + "]");
	} finally {
	    resetThreadLocalVars();
	}
    }

    @Override
    public MailtipoResponse mailtipoFrontend(MailtipoFrontendRequest request) {

	MailtipoResponse response = new MailtipoResponse();
	log.debug("mailtipo(): MailtipoRequest[token={}, codicemailtipo={}]", request.getToken(), request.getCodicemailtipo());
	setORMHelper(null, request.getToken());
	Mailtipo responseMailtipo = mailtipoService.eseguiSostituzioniFrontend(request.getCodicemailtipo(), request.getDettaglioPratica());
	response.setOggetto(StringUtils.defaultString(responseMailtipo.getOggetto()));
	response.setCorpo(StringUtils.defaultString(responseMailtipo.getCorpo()));
	return response;
    }

    @Override
    public MailtipoResponseProtokol mailtipoProtokol(MailtipoRequestProtokol request) {

	MailtipoResponseProtokol response = new MailtipoResponseProtokol();
	log.debug("mailtipo(): MailtipoRequest[token=" + request.getToken() + ", codicemailtipo=" + request.getCodicemailtipo() + ", codiceistanza="
		+ request.getCodiceistanza() + ", codicemovimento=" + request.getCodicemovimento());
	setORMHelper(null, request.getToken());
	try {
	    if (request.getCodicemailtipo() != 0) {
		Mailtipo mailtipo = mailtipoService.findById(new PkId(request.getCodicemailtipo()));
		Software software = mailtipo.getSoftware();
		ORMHelper.setSoftware(software.getCodice());
		if (request.getCodicemovimento() != null) {
		    Movimenti movimento = movimentiService.findById(new PkId(request.getCodicemovimento()));
		    Mailtipo mailtiporeplace = mailtipoService.replaceOggettoCorpo(mailtipo, null, movimento);
		    Mailtipo mailtiporeplaceProt = mailtipoService.replaceOggettoCorpoProtocollo(mailtipo, null, movimento);
		    response.setCorpoProt(mailtiporeplaceProt.getProtocolloCorpoMail());
		    response.setCorpo(mailtiporeplace.getCorpo());
		    response.setOggettoProt(mailtiporeplaceProt.getProtocolloOggettoMail());
		    response.setOggetto(mailtiporeplace.getOggetto());
		} else if (request.getCodiceistanza() != null) {
		    Istanze istanza = istanzeService.findById(new PkId(request.getCodiceistanza()));
		    Mailtipo mailtiporeplace = mailtipoService.replaceOggettoCorpo(mailtipo, istanza, null);
		    Mailtipo mailtiporeplaceProt = mailtipoService.replaceOggettoCorpoProtocollo(mailtipo, istanza, null);
		    response.setCorpoProt(mailtiporeplaceProt.getProtocolloCorpoMail());
		    response.setCorpo(mailtiporeplace.getCorpo());
		    response.setOggettoProt(mailtiporeplaceProt.getProtocolloOggettoMail());
		    response.setOggetto(mailtiporeplace.getOggetto());
		} else {
		    throw new RuntimeException("codice istanza o codice movimento sono obbligatori.");
		}
		log.debug("mailtipo(): oggettoProt={}, corpoProt={}", response.getOggettoProt(), response.getCorpoProt());
		return response;
	    } else {
		throw new RuntimeException("codice mailtipo obbligatorio.");
	    }
	} catch (Exception e) {
	    log.error("mailtipo():", e);
	    throw new RuntimeException("BACKOFFICE WS MAILTIPO: Errore! [" + e.getMessage() + "]");
	} finally {
	    resetThreadLocalVars();
	}
    }
}
