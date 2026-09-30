/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomandeEventiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomandeEventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoDomandeEventiService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.toscana.regione.suap.sem.types.procedimento.AttoreReteSuap;
import it.toscana.regione.suap.sem.types.procedimento.ConfermaRicezioneReqType;
import it.toscana.regione.suap.sem.types.procedimento.InviaAllegatoRespType;
import it.toscana.regione.suap.sem.types.procedimento.InviaStimoloResponseType;
import it.toscana.regione.suap.sem.types.procedimento.RichiediAllegatoRespType;
import it.toscana.regione.suap.sem.types.procedimento.StatoMessaggioRespType;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francol
 *
 */
@Service
public class FoDomandeEventiServiceImpl extends BaseServiceImpl<FoDomandeEventi, PkId> implements FoDomandeEventiService {

    private final Logger log = LoggerFactory.getLogger(FoDomandeEventiServiceImpl.class);
    private FoDomandeEventiDAO foDomandeEventiDao;
    private FoDomandeService foDomandeService;

    @Autowired
    public void setFoDomandeEventiDAO(FoDomandeEventiDAO dao) {

	this.foDomandeEventiDao = dao;
    }

    @Autowired
    public void setFoDomandeService(FoDomandeService srvc) {

	this.foDomandeService = srvc;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#insert(java.lang.Object)
     */
    @Override
    public void insert(FoDomandeEventi entity) {

	if (validateEntity(entity)) {
	    this.foDomandeEventiDao.insert(entity);
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#update(java.lang.Object)
     */
    @Override
    public void update(FoDomandeEventi entity) {

	if (validateEntity(entity)) {
	    this.foDomandeEventiDao.update(entity);
	}
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#delete(java.lang.Object)
     */
    @Override
    public void delete(FoDomandeEventi entity) {

	this.foDomandeEventiDao.delete(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<FoDomandeEventi> findAll(Integer firstResult, Integer maxResult) {

	return this.foDomandeEventiDao.findAll(firstResult, maxResult);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findById(java.io.Serializable)
     */
    @Override
    public FoDomandeEventi findById(PkId id) {

	return this.foDomandeEventiDao.findById(id);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#bindDomainObject(java.lang.Object, java.lang.Class, java.lang.String)
     */
    @Override
    public FoDomandeEventi bindDomainObject(FoDomandeEventi entity, Class<?> idClass, String idPath) {

	return entity;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#newIdFromSequencetable(java.lang.Object)
     */
    @Override
    public PkId newIdFromSequencetable(FoDomandeEventi entity) {

	return this.foDomandeEventiDao.newIdFromSequence(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.FoDomandeEventiService#findByIdDomanda(java.lang.Integer)
     */
    @Override
    public List<FoDomandeEventi> findByIdDomanda(String idComuneDomanda, Integer idDomanda) {

	return this.foDomandeEventiDao.findByIdDomanda(idComuneDomanda, idDomanda);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl#getEntityClass()
     */
    @Override
    protected Class<FoDomandeEventi> getEntityClass() {

	return FoDomandeEventi.class;
    }

    @Override
    public void registraEventoDomandaRFC239(FoDomandeEventi evt) {

	this.insert(evt);
    }

    @Override
    public FoDomandeEventi registraEventoInviaStimolo(InviaStimoloResponseType resp, Integer idDomanda, TipoEventoEnum tipoEvento) {

	if (resp != null && idDomanda != null) {
	    FoDomande domanda = this.foDomandeService.findById(new PkId(idDomanda));
	    if (null != domanda && tipoEvento != null) {
		FoDomandeEventi evt = new FoDomandeEventi();
		//		if (StringUtils.isNotBlank(domanda.getIdentificativoDestinatario())) {
		//		    evt.setAttore(domanda.getIdentificativoDestinatario());
		//		} else {
		evt.setAttore(AttoreReteSuap.FACCT.name());
		//		}
		evt.setFoDomande(domanda);
		evt.setDataEvento(new Date());
		evt.setEsito(resp.getEsito().value());
		evt.setMessaggioErrore(resp.getMsgErrore());
		evt.setTipoEvento(tipoEvento.name());
		evt.setIdMessaggio(resp.getIdMessaggio());
		this.registraEventoDomandaRFC239(evt);
		if (log.isInfoEnabled()) {
		    log.info("registraEventoPresentazioneDomanda - stimolo di {} trasmesso al SEM. Id messaggio: {}, esito: {}, errore: {}",
			    new Object[] { tipoEvento.name(), resp.getIdMessaggio(), evt.getEsito(), resp.getMsgErrore() });
		}
		return evt;
	    }
	}
	return null;
    }

    @Override
    public FoDomandeEventi registraEventoInvioAllegato(InviaAllegatoRespType resp, Integer idDomanda, String idMessaggio, String docId) {

	if (resp != null && idDomanda != null) {
	    FoDomande domanda = this.foDomandeService.findById(new PkId(idDomanda));
	    if (null != domanda) {
		FoDomandeEventi evt = new FoDomandeEventi();
		//		if (StringUtils.isNotBlank(domanda.getIdentificativoDestinatario())) {
		//		    evt.setAttore(domanda.getIdentificativoDestinatario());
		//		} else {
		evt.setAttore(AttoreReteSuap.FACCT.name());
		//}
		evt.setFoDomande(domanda);
		evt.setDataEvento(new Date());
		evt.setEsito(resp.getEsito().value());
		evt.setMessaggioErrore(resp.getMsgErrore());
		evt.setTipoEvento(FoDomandeEventiService.TipoEventoEnum.INVIO_ALLEGATO.name());
		evt.setIdMessaggio(idMessaggio);
		evt.setInfo(docId);
		this.registraEventoDomandaRFC239(evt);
		if (log.isInfoEnabled()) {
		    log.info("registraEventoInvioAllegato - messaggio di invio allegato trasmesso al SEM. Id messaggio: {}, esito: {}, errore: {}",
			    new Object[] { idMessaggio, evt.getEsito(), resp.getMsgErrore() });
		}
		return evt;
	    }
	}
	return null;
    }

    @Override
    public FoDomandeEventi registraEventoStatoMessaggio(StatoMessaggioRespType resp, Integer idDomanda, String idMessaggio) {

	if (resp != null && idDomanda != null) {
	    FoDomande domanda = this.foDomandeService.findById(new PkId(idDomanda));
	    if (null != domanda) {
		FoDomandeEventi evt = new FoDomandeEventi();
		//		if (StringUtils.isNotBlank(domanda.getIdentificativoDestinatario())) {
		//		    evt.setAttore(domanda.getIdentificativoDestinatario());
		//		} else {
		evt.setAttore(AttoreReteSuap.FACCT.name());
		//		}
		evt.setFoDomande(domanda);
		evt.setDataEvento(new Date());
		evt.setEsito(resp.getStato().value());
		evt.setMessaggioErrore(resp.getMsgErrore());
		evt.setTipoEvento(FoDomandeEventiService.TipoEventoEnum.STATO_MESSAGGIO.name());
		evt.setIdMessaggio(idMessaggio);
		this.registraEventoDomandaRFC239(evt);
		if (log.isInfoEnabled()) {
		    log.info("registraEventoStatoMessaggio - richiesta di statoMessaggio inviata al SEM. Id messaggio: {}, esito: {}, errore: {}",
			    new Object[] { idMessaggio, evt.getEsito(), resp.getMsgErrore() });
		}
		return evt;
	    }
	}
	return null;
    }

    @Override
    public FoDomandeEventi registraEventoErroreFACCT(Integer idDomanda, String messaggioErrore) {

	FoDomande domanda = this.foDomandeService.findById(new PkId(idDomanda));
	if (null != domanda) {
	    FoDomandeEventi evt = new FoDomandeEventi();
	    //	    if (StringUtils.isNotBlank(domanda.getIdentificativoDestinatario())) {
	    //		evt.setAttore(domanda.getIdentificativoDestinatario());
	    //	    } else {
	    evt.setAttore(AttoreReteSuap.FACCT.name());
	    //	    }
	    evt.setFoDomande(domanda);
	    evt.setDataEvento(new Date());
	    evt.setMessaggioErrore(messaggioErrore);
	    evt.setTipoEvento(FoDomandeEventiService.TipoEventoEnum.ERRORE_FACCT.name());
	    this.registraEventoDomandaRFC239(evt);
	    if (log.isInfoEnabled()) {
		log.info("registraEventoErroreFACCT - Errore interno al FACCT: {}", new Object[] { messaggioErrore });
	    }
	    return evt;
	}
	return null;
    }

    @Override
    public FoDomandeEventi registraEventoRicezioneStimolo(String idMessaggio, String tipoStimolo, String mittente, String messaggioErrore) {

	// l'evento RICEZIONE_STIMOLO è da considerarsi un evento di basso livello ed è a monte delle logiche per la gestione dello stimolo stesso
	// viene registrato solo per poter tracciare le chiamate in ingresso ancora prima che siano associate a un'ente o una pratica anche in caso in cui 
	// questa associasione fallisca
	FoDomandeEventi fde = new FoDomandeEventi();
	fde.setAttore(mittente);
	fde.setDataEvento(new Date());
	fde.setInfo(tipoStimolo);
	fde.setIdMessaggio(idMessaggio);
	fde.setMessaggioErrore(messaggioErrore);
	fde.setTipoEvento(FoDomandeEventiService.TipoEventoEnum.RICEZIONE_STIMOLO.name());
	this.registraEventoDomandaRFC239(fde);
	return fde;
    }

    @Override
    public FoDomandeEventi registraEventoRichiediAllegato(RichiediAllegatoRespType richAllResp, Integer idDomanda, String idMessaggio,
	    String idAllegato) {

	FoDomandeEventi evt = new FoDomandeEventi();
	evt.setAttore(AttoreReteSuap.FACCT.name());
	evt.setDataEvento(new Date());
	evt.setMessaggioErrore(richAllResp.getMsgErrore());
	evt.setTipoEvento(FoDomandeEventiService.TipoEventoEnum.RICHIEDI_ALLEGATO.name());
	if (idDomanda != null) {
	    FoDomande domanda = this.foDomandeService.findById(new PkId(idDomanda));
	    if (null != domanda) {
		//		if (StringUtils.isNotBlank(domanda.getIdentificativoDestinatario())) {
		//		    evt.setAttore(domanda.getIdentificativoDestinatario());
		//		}
		evt.setFoDomande(domanda);
	    }
	}
	this.registraEventoDomandaRFC239(evt);
	if (log.isInfoEnabled()) {
	    log.info(
		    "registraEventoRichiediAllegato - richiesta di allegato inviata al SEM. Id messaggio: {}, id allegato: {}, esito: {}, errore: {}",
		    new Object[] { idMessaggio, idAllegato, evt.getEsito(), richAllResp.getMsgErrore() });
	}
	return evt;
    }

    @Override
    public FoDomandeEventi registraEventoRicezioneComunica(Integer idDomanda, String messaggioErrore) {

	FoDomandeEventi evt = new FoDomandeEventi();
	evt.setAttore(AttoreReteSuap.FACCT.name());
	evt.setDataEvento(new Date());
	evt.setMessaggioErrore(messaggioErrore);
	evt.setTipoEvento(FoDomandeEventiService.TipoEventoEnum.RICEZIONE_COMUNICA.name());
	if (idDomanda != null) {
	    FoDomande domanda = this.foDomandeService.findById(new PkId(idDomanda));
	    if (null != domanda) {
		//		if (StringUtils.isNotBlank(domanda.getIdentificativoDestinatario())) {
		//		    evt.setAttore(domanda.getIdentificativoDestinatario());
		//		}
		evt.setFoDomande(domanda);
	    }
	}
	this.registraEventoDomandaRFC239(evt);
	if (log.isInfoEnabled()) {
	    log.info("registraEventoRicezioneComunica - ricevuto da COMACCT uno stimolo di presentazione pratica. Id domanda: {}, errore: {}",
		    new Object[] { idDomanda, messaggioErrore });
	}
	return evt;
    }

    @Override
    public FoDomandeEventi registraEventoRicezioneRichiestaIntegrazioni(Integer idDomanda, String messaggioErrore, String mittente) {

	FoDomandeEventi evt = new FoDomandeEventi();
	evt.setAttore(mittente);
	evt.setDataEvento(new Date());
	evt.setMessaggioErrore(messaggioErrore);
	evt.setTipoEvento(FoDomandeEventiService.TipoEventoEnum.RICEZIONE_RICHIESTA_INTEGRAZIONI.name());
	if (idDomanda != null) {
	    FoDomande domanda = this.foDomandeService.findById(new PkId(idDomanda));
	    if (null != domanda) {
		evt.setFoDomande(domanda);
	    }
	}
	this.registraEventoDomandaRFC239(evt);
	if (log.isInfoEnabled()) {
	    log.info(
		    "registraEventoRicezioneRichiestaIntegrazioni - richiesta di allegato inviata al SEM. Id messaggio: {}, id allegato: {}, esito: {}, errore: {}",
		    new Object[] { idDomanda, messaggioErrore });
	}
	return evt;
    }

    @Override
    public FoDomandeEventi registraEventoConfermaRicezione(ConfermaRicezioneReqType ricezioneReq, String info, String messaggioErrore) {

	FoDomandeEventi fde = new FoDomandeEventi();
	fde.setAttore(ricezioneReq.getMittente().getTipologia());
	fde.setDataEvento(new Date());
	fde.setInfo(info);
	fde.setIdMessaggio(ricezioneReq.getIdMessaggio());
	if (StringUtils.isBlank(messaggioErrore) && ricezioneReq.getErrore() != null) {
	    messaggioErrore = "Destinatario INCOERENTE";
	}
	fde.setMessaggioErrore(messaggioErrore);
	fde.setTipoEvento(FoDomandeEventiService.TipoEventoEnum.CONFERMA_RICEZIONE.name());
	this.registraEventoDomandaRFC239(fde);
	return fde;
    }

    @Override
    public List<FoDomandeEventi> findByIdSistemaAndAttore(String idMessaggioSistema, String attore) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("idMessaggio", idMessaggioSistema, String.class));
	fr.addFilterField(FilterUtils.startsWith("attore", attore));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataEvento"));
	return foDomandeEventiDao.findByFilterTable(ft);
    }
}
