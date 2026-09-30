package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TmpEsportazioni;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.VerificaCausaliDettaglioBollettazioneBean;
import it.gruppoinit.pal.gp.core.service.BollGestDettaglioService;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;

/**
 * 
 * @author
 */
@Loggable(featureName = "bollettazione")
@Service
public class BollGestDettaglioServiceImpl extends BaseServiceImpl<BollGestDettaglio, PkId> implements BollGestDettaglioService {

    private static final Logger log = LoggerFactory.getLogger(BollGestDettaglioServiceImpl.class);
    private BollGestDettaglioDAO bollgestdettaglioDAO;
    private TmpEsportazioniService tmpEsportazioniService;

    @Autowired
    public void setBollGestDettaglioDAO(BollGestDettaglioDAO bollgestdettaglioDAO) {

	this.bollgestdettaglioDAO = bollgestdettaglioDAO;
    }

    @Autowired
    public void setTmpEsportazioniService(TmpEsportazioniService tmpEsportazioniService) {

	this.tmpEsportazioniService = tmpEsportazioniService;
    }

    @Override
    protected Class<BollGestDettaglio> getEntityClass() {

	return BollGestDettaglio.class;
    }

    @Override
    public List<BollGestDettaglio> findAll(Integer firstResult, Integer maxResult) {

	return bollgestdettaglioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BollGestDettaglio entity) {

	if (validateEntity(entity)) {
	    bollgestdettaglioDAO.insert(entity);
	}
    }

    @Override
    public BollGestDettaglio findById(PkId id) {

	return bollgestdettaglioDAO.findById(id);
    }

    @Override
    public void update(BollGestDettaglio entity) {

	if (validateEntity(entity)) {
	    bollgestdettaglioDAO.update(entity);
	}
    }

    @Override
    public void delete(BollGestDettaglio entity) {

	if (isDeleteAllowed(entity)) {
	    bollgestdettaglioDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollGestDettaglio entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public String getComune(Integer idRigaDettaglio) {

	return bollgestdettaglioDAO.getComune(idRigaDettaglio);
    }

    @Override
    public String exportModalitaPentaho(Integer idBollettazione, Esportazioni esportazioni, String email, boolean isInvioMail) {

	log.debug("exportModalitaPentaho# Start export pentaho, contesto : {}", TipicontestoesportazioniEnum.BOLLETTAZIONE);
	String sessionId = ORMHelper.getToken();
	log.debug("exportModalitaPentaho# Cancello i record su tmp_esportazioni con sessionId: {}", sessionId);
	tmpEsportazioniService.deleteBysessionId(sessionId);
	log.debug("exportModalitaPentaho# Inizio esportazione bollettazione. Invio email. {}", isInvioMail);
	//bollgestdettaglioDAO.exportModalitaPentaho(attivitaFilter, esportazioni, data, emailResponsabile, contesto, isInvioMail);
	//inserisco i record nella tabella TMP_ESPORTAZIONE
	TmpEsportazioni entity = new TmpEsportazioni();
	entity.setCodice(idBollettazione);
	entity.setCodicecomune(null);
	entity.setData(Calendar.getInstance().getTime());
	entity.setIdcomune(ORMHelper.getIdcomune());
	entity.setSessionid(ORMHelper.getToken());
	tmpEsportazioniService.insert(entity);
	return sessionId;
    }

    @Override
    public void verificaConfigurazioniCausali(Set<Integer> idRigheDettaglioBollettazione) throws InvalidConfigurationException {

	List<String> errori = new ArrayList<String>();
	log.debug("verificaConfigurazioniCausali");
	List<VerificaCausaliDettaglioBollettazioneBean> idtco = bollgestdettaglioDAO.verificaConfigurazioniCausali(idRigheDettaglioBollettazione);
	if (log.isDebugEnabled()) {
	    log.debug("verificaConfigurazioniCausali fatto idtco.size() ==> {}", idtco.size());
	}
	if (idtco.isEmpty()) {
	    log.error("verificaConfigurazioniCausali Attenzione! Non è presente alcuna causale oneri configurata");
	    errori.add("Attenzione! Non è presente alcuna causale oneri configurata.");
	}
	Set<Integer> idRigheDettaglioBollettazioneTornate = new HashSet<Integer>(); // la query non ha tornato tutte le configurazioniper le righe richieste es non torna le righe attivo = 0
	String msg = "";
	for (VerificaCausaliDettaglioBollettazioneBean vd : idtco) {
	    idRigheDettaglioBollettazioneTornate.add(vd.getIddettaglio());
	    if (vd.getIdcausaleonere() == null || StringUtils.isBlank(vd.getMappaturanodopag()) || !BooleanUtils.toBoolean(vd.getAttivo())) {
		msg = "Attenzione! Causale oneri non configurata ";
		if (vd.getIdconto() != null) {
		    msg += " per il conto [" + vd.getDescrizioneConto() + "] ";
		}
		msg += " nella riga di bollettazione di " + vd.getDescrizioneRichiedenteBreve() + " con ";
		msg += " descrizione [" + vd.getDescrizione() + " - id:" + vd.getIddettaglio() + "]";
		errori.add(msg);
	    }
	}
	if (idRigheDettaglioBollettazione.size() != idRigheDettaglioBollettazioneTornate.size()) {
	    // la query non ha tornato tutte le configurazioniper le righe richieste es non torna le righe attivo = 0
	    for (Integer id : idRigheDettaglioBollettazione) {
		if (!idRigheDettaglioBollettazioneTornate.contains(id)) {
		    BollGestDettaglio rigaDettaglio = bollgestdettaglioDAO.getById(BollGestDettaglio.class, id);
		    Conti c = rigaDettaglio.getConti();
		    msg = "Attenzione! Non è presente alcuna causale oneri configurata ";
		    if (c != null) {
			msg += " per il conto [" + c.getDescrizioneConto() + "] ";
		    }
		    msg += " nella riga di bollettazione di " + rigaDettaglio.getAnagrafe().getDescrizioneRichiedenteBreve() + " con id " + id;
		    errori.add(msg);
		}
	    }
	}
	if (!errori.isEmpty()) {
	    msg = "Errori di configurazione: ";
	    for (String errore : errori) {
		msg += "\n<br/>" + errore;
	    }
	    log.error("verificaConfigurazioniCausali: {}", msg);
	    throw new InvalidConfigurationException(msg);
	}
    }

    @Override
    public Map<Integer, Set<String>> getComuniPerDettagliBollettazione(int idTestataBollettazione) {

	return bollgestdettaglioDAO.getComuniPerDettagliBollettazione(idTestataBollettazione);
    }

    @Override
    public String recuperaMappaturaNodoPagDaIdDettaglio(Integer idRigaDettaglio) {

	List<String> codici = this.bollgestdettaglioDAO.recuperaMappaturaNodoPagDaIdDettaglio(idRigaDettaglio);
	if (codici.isEmpty()) {
	    return null;
	}
	if (codici.size() > 1) {
	    throw new InvalidConfigurationException(
		    "Impossibile recuperare univocamente la mappatura del conto in quanto ci sono più causali oneri collegate allo stesso conto. idRigaDettaglio: " +
						    idRigaDettaglio);
	}
	return codici.get(0);
    }

    public String findArrotondamentoByBollettazione(Integer idBollettazione) {

	return this.bollgestdettaglioDAO.findArrotondamentoByBollettazione(idBollettazione);
    }
}
