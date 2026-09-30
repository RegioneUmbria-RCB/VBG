package it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniConcessioniDatiGenerali;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ConcessionitipiService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

/**
 * 
 * @author fabrizioc
 */
@Service
public class AutorizzazioniConcessioniServiceImpl extends BaseServiceImpl<AutorizzazioniConcessioni, PkId>
	implements AutorizzazioniConcessioniService {

    private static final Logger log = LoggerFactory.getLogger(AutorizzazioniConcessioniServiceImpl.class);
    private AutorizzazioniConcessioniDAO autorizzazioniconcessioniDAO;
    private AutorizzazioniService autorizzazioniService;
    private ConcessionitipiService concessionitipiService;
    private MercatiDService mercatiDService;
    private MercatiService mercatiService;
    private MercatiUsoService mercatiUsoService;

    @Autowired
    public void setAutorizzazioniConcessioniDAO(AutorizzazioniConcessioniDAO autorizzazioniconcessioniDAO) {

	this.autorizzazioniconcessioniDAO = autorizzazioniconcessioniDAO;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setConcessionitipiService(ConcessionitipiService concessionitipiService) {

	this.concessionitipiService = concessionitipiService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Override
    protected Class<AutorizzazioniConcessioni> getEntityClass() {

	return AutorizzazioniConcessioni.class;
    }

    @Override
    public List<AutorizzazioniConcessioni> findAll(Integer firstResult, Integer maxResult) {

	return autorizzazioniconcessioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AutorizzazioniConcessioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    autorizzazioniconcessioniDAO.insert(entity);
	}
    }

    @Override
    public AutorizzazioniConcessioni findById(PkId id) {

	return autorizzazioniconcessioniDAO.findById(id);
    }

    private void dataIntegration(AutorizzazioniConcessioni entity) {

	if (entity == null) {
	    throw new RuntimeException("L'argomento (AutorizzazioniConcessioni) passato è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AutorizzazioniConcessioni entity) {

	MercatiD posteggio = mercatiDService.bindDomainObject(entity.getMercatiD(), PkId.class, "id.codice");
	entity.setMercatiD(posteggio);
	MercatiUso uso = mercatiUsoService.bindDomainObject(entity.getMercatiUso(), PkId.class, "id.codice");
	entity.setMercatiUso(uso);
	Mercati mercato = mercatiService.bindDomainObject(entity.getMercati(), PkId.class, "id.codice");
	entity.setMercati(mercato);
	Autorizzazioni autorizzazioneAtt = autorizzazioniService.bindDomainObject(entity.getAutorizzazioniByFkAutconcAutatt(), PkId.class,
		"id.codice");
	entity.setAutorizzazioniByFkAutconcAutatt(autorizzazioneAtt);
	Autorizzazioni autorizzazioneColl = autorizzazioniService.bindDomainObject(entity.getAutorizzazioniByFkAutconcAutcoll(), PkId.class,
		"id.codice");
	entity.setAutorizzazioniByFkAutconcAutcoll(autorizzazioneColl);
	Concessionitipi concessionitipi = concessionitipiService.bindDomainObject(entity.getConcessionitipi(), String.class, "tipoconcessione");
	entity.setConcessionitipi(concessionitipi);
    }

    @Override
    public void update(AutorizzazioniConcessioni entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    autorizzazioniconcessioniDAO.update(entity);
	}
    }

    @Override
    public void delete(AutorizzazioniConcessioni entity) {

	if (isDeleteAllowed(entity)) {
	    autorizzazioniconcessioniDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(AutorizzazioniConcessioni entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO validare la delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREING_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<AutorizzazioniConcessioni> findConcessioniByIstanza(Integer codiceIstanza) {

	return autorizzazioniconcessioniDAO.findConcessioniByIstanza(codiceIstanza);
    }

    @Override
    public List<AutorizzazioniConcessioni> findConcessioniAttiveByMercatoEUso(Mercati mercato, MercatiUso uso) {

	return autorizzazioniconcessioniDAO.findConcessioniAttiveByMercatoEUso(mercato, uso);
    }

    @Override
    public List<AutorizzazioniConcessioni> findConcessioniAttiveByMercato(Mercati mercato) {

	return autorizzazioniconcessioniDAO.findConcessioniAttiveByMercato(mercato);
    }

    @Override
    public List<AutorizzazioniConcessioni> findConcessioniByMercato(Mercati mercato) {

	return autorizzazioniconcessioniDAO.findConcessioniByMercato(mercato);
    }

    @Override
    public int countConcessioniByCodiceMercato(Integer codiceMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	ft.addRestriction(fr);
	return autorizzazioniconcessioniDAO.countRecord(ft);
    }

    @Override
    public AutorizzazioniConcessioni findConcessioneAttualeByMercatoAndUsoAndPosteggio(Integer codiceMercato, Integer codiceUso,
	    Integer codicePosteggio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiDId", codicePosteggio, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagAttiva", true, "autorizzazioniByFkAutconcAutatt", Integer.class));
	ft.addRestriction(fr);
	List<AutorizzazioniConcessioni> list = autorizzazioniconcessioniDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return autorizzazioniconcessioniDAO.findByFilterTable(ft).get(0);
	}
	return null;
    }

    @Override
    public List<AutorizzazioniConcessioni> findConcessioniAttive(Mercati mercato, MercatiUso uso) {

	if (EntityUtils.getNestedProperty(uso, "id.codice") != null) {
	    if (log.isDebugEnabled()) {
		log.debug(
			"findConcessioniAttive# Uso predefinito dall'albero dei procedimenti presente : la lista sarà filtrata per mercato :  {}[{}]  ed uso :  {}[{}] ",
			new Object[] { mercato.getDescrizione(), mercato.getId().getCodice(), uso.getDescrizione(), uso.getId().getCodice() });
	    }
	    return this.findConcessioniAttiveByMercatoEUso(mercato, uso);
	} else {
	    if (log.isDebugEnabled()) {
		log.debug(
			"findConcessioniAttive# Uso predefinito dall'albero dei procedimenti non presente : la lista sarà filtrata per mercato :  {}[{}]   ",
			new Object[] { mercato.getDescrizione(), mercato.getId().getCodice() });
	    }
	    return this.findConcessioniAttiveByMercato(mercato);
	}
    }

    @Override
    public List<AutorizzazioniConcessioni> findByAutorizzazioneAttuale(Integer codiceAutorizzazioneAttuale) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("autorizzazioniByFkAutconcAutattId", codiceAutorizzazioneAttuale, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("giornisettimana.id", "mercatiUso"));
	return autorizzazioniconcessioniDAO.findByFilterTable(ft);
    }

    @Override
    public AutorizzazioniConcessioniDatiGenerali findDatiGeneraliConcessione(Integer codiceAutorizzazione) {

	AutorizzazioniConcessioniDatiGenerali ret = new AutorizzazioniConcessioniDatiGenerali();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("autorizzazioniByFkAutconcAutattId", codiceAutorizzazione, Integer.class));
	ft.addRestriction(fr);
	List<AutorizzazioniConcessioni> autc = autorizzazioniconcessioniDAO.findByFilterTable(ft, 0, 1);
	if (autc.size() > 0) {
	    AutorizzazioniConcessioni conc = autc.get(0);
	    autorizzazioniconcessioniDAO.evict(conc);
	    Autorizzazioni aut = conc.getAutorizzazioniByFkAutconcAutatt();
	    Autorizzazioni autColl = conc.getAutorizzazioniByFkAutconcAutcoll();
	    ret.setConcessionitipi(conc.getConcessionitipi());
	    ret.setDatascadenza(conc.getDatascadenza());
	    ret.setIdAutorizzazione(codiceAutorizzazione);
	    ret.setStagionalea(conc.getStagionalea());
	    ret.setStagionaleda(conc.getStagionaleda());
	    ret.setAutorizzazioniByFkAutconcAutatt(aut);
	    ret.setAutorizzazioniByFkAutconcAutcoll(autColl);
	}
	return ret;
    }

    @Override
    public int cessaConcessioniByIdPosteggio(int idPosteggio, Date dataCessazione, int idCausaleCessazione) {

	List<Integer> autorizzazioni = this.autorizzazioniconcessioniDAO.findIdAutorizzazioniAttiveByIdPosteggio(idPosteggio);
	for (Integer idAutorizzazione : autorizzazioni) {
	    this.autorizzazioniService.cessaAutorizzazione(idAutorizzazione, dataCessazione, idCausaleCessazione);
	}
	return autorizzazioni.size();
    }

    @Override
    public Integer getFkIdautAttualePerAutCollegata(Integer fkIdautCollegata) {

	return autorizzazioniconcessioniDAO.getFkIdautAttualePerAutCollegata(fkIdautCollegata);
    }

    @Override
    public Map<Integer, IdentificativoDescrizioneBean> findAutorizzazioniCollegate(Set<Integer> auts) {

	if (auts == null || auts.isEmpty()) {
	    return new HashMap<Integer, IdentificativoDescrizioneBean>(0);
	}
	return autorizzazioniconcessioniDAO.findAutorizzazioniCollegate(auts);
    }
}
