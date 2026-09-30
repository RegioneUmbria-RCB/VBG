package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BlacklistAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniFrontRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackList;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListWrapper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class BlacklistAutorizzazioniServiceImpl extends BaseServiceImpl<BlacklistAutorizzazioni, PkId> implements BlacklistAutorizzazioniService {

    private BlacklistAutorizzazioniDAO blacklistAutorizzazioniDAO;
    private AutorizzazioniService autorizzazioniService;
    private MercatiAppService mercatiAppService;
    private MercatipresenzeTService mercatipresenzeTService;

    @Autowired
    public void setBlacklistAutorizzazioniDAO(BlacklistAutorizzazioniDAO blacklistAutorizzazioniDAO) {

	this.blacklistAutorizzazioniDAO = blacklistAutorizzazioniDAO;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setMercatiAppService(MercatiAppService mercatiAppService) {

	this.mercatiAppService = mercatiAppService;
    }

    @Autowired
    public void setMercatipresenzeTService(MercatipresenzeTService mercatipresenzeTService) {

	this.mercatipresenzeTService = mercatipresenzeTService;
    }

    @Override
    public void insert(BlacklistAutorizzazioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    blacklistAutorizzazioniDAO.insert(entity);
	}
    }

    @Override
    public void update(BlacklistAutorizzazioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    blacklistAutorizzazioniDAO.update(entity);
	}
    }

    private void dataIntegration(BlacklistAutorizzazioni entity) {

	if (entity.getFlagPrincipale() == null) {
	    entity.setFlagPrincipale(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    public void delete(BlacklistAutorizzazioni entity) {

	if (isDeleteAllowed(entity)) {
	    blacklistAutorizzazioniDAO.delete(entity);
	}
    }

    @Override
    public List<BlacklistAutorizzazioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public BlacklistAutorizzazioni findById(PkId id) {

	return blacklistAutorizzazioniDAO.findById(id);
    }

    @Override
    public Class<BlacklistAutorizzazioni> getEntityClass() {

	return BlacklistAutorizzazioni.class;
    }

    @Override
    public List<BlackListAttivaBean> findAutorizzazioniInBlackListAttive() {

	return blacklistAutorizzazioniDAO.findAutorizzazioniInBlackListAttive();
    }

    private FilterTable findByBlAttiveFilterTable(boolean isCount) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction data = new FilterRestriction();
	data.addFilterField(FilterUtils.isNull("dataFineBl", "blacklistMotivi"));
	ft.addRestriction(data);
	if (!isCount) {
	    ft.addOrder(FilterUtils.orderDesc("flagPrincipale"));
	}
	return ft;
    }

    @Override
    public int AutorizzazioniInBlackListAttive() {

	FilterTable ft = findByBlAttiveFilterTable(true);
	return blacklistAutorizzazioniDAO.countRecord(ft);
    }

    @Override
    public List<BlacklistAutorizzazioni> findByBlackListMotivo(Integer codiceMotivo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("blacklistMotiviId", codiceMotivo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("flagPrincipale"));
	return blacklistAutorizzazioniDAO.findByFilterTable(ft);
    }

    @Override
    public void aggiungiABlackList(BlacklistMotivi motivo, MercatipresenzeD giornata, Anagrafe titolare) {

	Autorizzazioni autorizzazione = giornata.getAutorizzazioni();
	MercatiUso uso = null;
	if (!giornata.isSpuntista()) {
	    uso = giornata.getMercatiPresenzeT().getMercatoUso();
	}
	this.insert(new BlacklistAutorizzazioni(motivo, autorizzazione, titolare, uso, Boolean.TRUE));
	if (uso != null) {
	    return;
	}
	List<AutorizzazioniFrontRestBean> autorizzazioniCollegate = this.mercatiAppService.findAutorizzazioniHelperByUtente(titolare, false, null, true);
	String cfPiva = titolare.getCfPiva();
	for (AutorizzazioniFrontRestBean afrb : autorizzazioniCollegate) {
	    if (!afrb.getId().equals(autorizzazione.getId().getCodice())) {
		Autorizzazioni autCollegata = this.autorizzazioniService.findById(new PkId(afrb.getId()));
		Anagrafe an = this.autorizzazioniService.findAnagrafeAutorizzazione(afrb.getId());
		if (an.getCfPiva().equalsIgnoreCase(cfPiva)) {
		    this.insert(new BlacklistAutorizzazioni(motivo, autCollegata, an, null, Boolean.FALSE));
		}
	    }
	}
    }

    @Override
    public BlackListWrapper findAutorizzazioniInBlackListAttivePerGiornata(Integer idGiornata, BlackListContestoEnum[] contesti) {

	MercatipresenzeT testata = this.mercatipresenzeTService.findById(new PkId(idGiornata));
	//Così evito la lettura LAZY
	Integer codicemercato = null;
	if (null != testata.getMercato()) {
	    if (null != testata.getMercato().getId()) {
		codicemercato = testata.getMercato().getId().getCodice().intValue();
	    }
	}
	List<BlackListAttivaBean> autInBlackListAttivePerMercatiUso = this.blacklistAutorizzazioniDAO
		.findAutorizzazioniInBlackListAttivePerMercatiUso(testata.getMercatoUso().getId().getCodice(), contesti);
	BlackListWrapper result = new BlackListWrapper();
	for (BlackListAttivaBean bl : autInBlackListAttivePerMercatiUso) {
	    BlackList blacklist;
	    if (BlackListContestoEnum.PRESENZE.name().toLowerCase().equals(bl.getContesto())) {
		blacklist = result.getPresenze();
		//Comportamento come AS IS
		if (bl.getMercatiUsoId() == null) {
		    blacklist.getAutorizzazioniSpuntisti().add(bl.getAutorizzazioniId());
		} else {
		    blacklist.getAutorizzazioniConcessionari().add(bl.getAutorizzazioniId());
		}
	    } else {
		blacklist = result.getBollettazione();
		/*
		 * Non dovrebbe essere null, ma vedo che è nullable. Per ora faccio così, poi lo discuteremo
		 */
		if (codicemercato == null) {
		    continue;
		}
		if (bl.getCodicemercato() != null && bl.getCodicemercato().equals(codicemercato)) {
		    blacklist.getAutorizzazioniConcessionari().add(bl.getAutorizzazioniId());
		} else {
		    blacklist.getAutorizzazioniSpuntisti().add(bl.getAutorizzazioniId());
		}
	    }
	}
	return result;
    }
}
