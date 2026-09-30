package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TempirispostaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.TempirispostaId;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.TempirispostaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.TempirispostaCommand;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.TempirispostaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.helper.TempirispostaHelperBean;
import it.gruppoinit.pal.gp.core.service.helper.TempirispostaValoriHelper;

/**
 * 
 * @author
 */
@Service
public class TempirispostaServiceImpl extends BaseServiceImpl<Tempirisposta, TempirispostaId> implements TempirispostaService {

    private TempirispostaDAO tempirispostaDAO;
    private TipiprocedureService tipiprocedureService;
    private AmministrazioniService amministrazioniService;
    private TipiMovimentoService tipiMovimentoService;

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setTempirispostaDAO(TempirispostaDAO tempirispostaDAO) {

	this.tempirispostaDAO = tempirispostaDAO;
    }

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Override
    protected Class<Tempirisposta> getEntityClass() {

	return Tempirisposta.class;
    }

    @Override
    public List<Tempirisposta> findAll(Integer firstResult, Integer maxResult) {

	return tempirispostaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tempirisposta entity) {

	if (validateEntity(entity)) {
	    tempirispostaDAO.insert(entity);
	}
    }

    @Override
    public Tempirisposta findById(TempirispostaId id) {

	return tempirispostaDAO.findById(id);
    }

    @Override
    public void update(Tempirisposta entity) {

	if (validateEntity(entity)) {
	    tempirispostaDAO.update(entity);
	}
    }

    @Override
    public void delete(Tempirisposta entity) {

	if (isDeleteAllowed(entity)) {
	    tempirispostaDAO.delete(entity);
	}
    }

    @Override
    public List<Tempirisposta> findByFilter(Tempirisposta tempirisposta) {

	return tempirispostaDAO.findByFilter(tempirisposta);
    }

    @Override
    public List<TempirispostaHelper> findByTempirispostaHelperByTipoControMov(Tipicontromovimento tipicontromovimento) {

	// Creiamo una lista di Tempi di risposta helper secondo le regole (la lista è una lista temporanea,non sarà
	// quella
	// ritornata dal metodo):
	// Se la procedura del tipo contro mevimento è null significa che dobbiamo considerare i tempi di risposta per
	// tutte le procedure per quel software,
	// altrimenti solo per quella passata.
	// Se l'amministrazione ha codice -1 (TUTTE LE AMMINISTRAZIONI) dobbiamo considerare i tempi di risposta di
	// tutte le amministrazioni configuare per quel software
	// e associate a tutte le procedure scelte (una o tutte)
	// amministrazioni configurate per quel software
	List<TempirispostaHelper> risultatoTemp = new ArrayList<TempirispostaHelper>();
	TempirispostaHelper tempirispostaHelper = null;
	// tutte le procedure
	if (tipicontromovimento.getTipiprocedure() == null) {
	    List<Tipiprocedure> listTipiprocedure = tipiprocedureService.findAll(null, null);
	    for (Iterator iterator = listTipiprocedure.iterator(); iterator.hasNext();) {
		Tipiprocedure tipiprocedure = (Tipiprocedure) iterator.next();
		tempirispostaHelper = new TempirispostaHelper();
		tempirispostaHelper.setTipiprocedure(tipiprocedure);
		tempirispostaHelper.setTipicontromovimento(tipicontromovimento.getTipocontromovimento());
		tempirispostaHelper.setTipimovimento(tipicontromovimento.getTipomovimento());
		// tutte le amministrazioni
		if (EntityUtils.isNestedPropertyBlank(tipicontromovimento.getAmministrazioniTipiMovimento(), "id.codice") || tipicontromovimento
			.getAmministrazioniTipiMovimento().getId().getCodice().intValue() == WebConstants.CODICETUTTEAMMINISTRAZIONI) {
		    List<Amministrazioni> amministrazionis = amministrazioniService.findAll(null, null);
		    List<AmministrazioniHelper> amministrazioniHelpers = copyAmministrazioniToAmministrazioHelper(amministrazionis);
		    tempirispostaHelper.setAmministrazionis(amministrazioniHelpers);
		    // una sola amministrazione
		} else {
		    List<AmministrazioniHelper> amministrazionis = new ArrayList<AmministrazioniHelper>();
		    Amministrazioni amministrazioni = amministrazioniService
			    .findById(new PkId(tipicontromovimento.getAmministrazioniTipiMovimento().getId().getCodice()));
		    AmministrazioniHelper amministrazioniHelper = new AmministrazioniHelper();
		    amministrazioniHelper.setAmministrazioni(amministrazioni);
		    amministrazionis.add(amministrazioniHelper);
		    tempirispostaHelper.setAmministrazionis(amministrazionis);
		}
		risultatoTemp.add(tempirispostaHelper);
	    }
	    // una sola procedura
	} else {
	    Tipiprocedure tipiprocedure = tipiprocedureService.findById(new PkId(tipicontromovimento.getTipiprocedure().getId().getCodice()));
	    tempirispostaHelper = new TempirispostaHelper();
	    tempirispostaHelper.setTipiprocedure(tipiprocedure);
	    tempirispostaHelper.setTipicontromovimento(tipicontromovimento.getTipocontromovimento());
	    tempirispostaHelper.setTipimovimento(tipicontromovimento.getTipomovimento());
	    // tutte le amministrazioni
	    if (EntityUtils.isNestedPropertyBlank(tipicontromovimento.getAmministrazioniTipiMovimento(), "id.codice") || tipicontromovimento
		    .getAmministrazioniTipiMovimento().getId().getCodice().intValue() == WebConstants.CODICETUTTEAMMINISTRAZIONI) {
		List<Amministrazioni> amministrazionis = amministrazioniService.findAll(null, null);
		List<AmministrazioniHelper> amministrazioniHelpers = copyAmministrazioniToAmministrazioHelper(amministrazionis);
		tempirispostaHelper.setAmministrazionis(amministrazioniHelpers);
		// una sola amministrazione
	    } else {
		List<AmministrazioniHelper> amministrazionis = new ArrayList<AmministrazioniHelper>();
		Amministrazioni amministrazioni = amministrazioniService
			.findById(new PkId(tipicontromovimento.getAmministrazioniTipiMovimento().getId().getCodice()));
		AmministrazioniHelper amministrazioniHelper = new AmministrazioniHelper();
		amministrazioniHelper.setAmministrazioni(amministrazioni);
		amministrazionis.add(amministrazioniHelper);
		tempirispostaHelper.setAmministrazionis(amministrazionis);
	    }
	    risultatoTemp.add(tempirispostaHelper);
	}
	// controllo che nel DB non siano già configurati tempi di attesa IN MODO DA VISUALIZZARLI
	// creo un oggetto filter in modo da cercare il solo record (se esiste) di mio interesse
	Tempirisposta tempirispostaFilter = null;
	// creo una lista di tempi di risposta che sarà quella che verra ritornata dal metodo
	List<TempirispostaHelper> risultato = new ArrayList<TempirispostaHelper>();
	// controllo ogni oggetto della lista di tempi di risposta helper temporanei
	for (Iterator iterator = risultatoTemp.iterator(); iterator.hasNext();) {
	    TempirispostaHelper temp = (TempirispostaHelper) iterator.next();
	    // con i dati dell'oggetto tempi di risposta helper popolo il filtro
	    tempirispostaFilter = new Tempirisposta();
	    tempirispostaFilter.setTipiprocedure(temp.getTipiprocedure());
	    tempirispostaFilter.setTipimovimento(temp.getTipimovimento());
	    tempirispostaFilter.setTipicontromovimento(temp.getTipicontromovimento());
	    // dall'oggetto helper recupero la lista delle amministrazioni helper
	    List<AmministrazioniHelper> listAministrazioniTemp = temp.getAmministrazionis();
	    List<AmministrazioniHelper> listAministrazioni = new ArrayList<AmministrazioniHelper>();
	    // scorro tutta la lista delle amministrazioni
	    for (Iterator iterator2 = listAministrazioniTemp.iterator(); iterator2.hasNext();) {
		AmministrazioniHelper amministrazioniHelper = (AmministrazioniHelper) iterator2.next();
		// aggiungo al filtro l'amministrazione
		tempirispostaFilter.setAmministrazione(amministrazioniHelper.getAmministrazioni());
		// faccio una find sulla tabella tempi di risposta
		List<Tempirisposta> listTempirisposta = tempirispostaDAO.findByFilter(tempirispostaFilter);
		// se recupero un oggetto significa che per quella procedura e amminitsrazione è settato un tempo di
		// attesa
		// e lo metto nella lista che poi andrò a visualizzare
		if (!listTempirisposta.isEmpty()) {
		    Tempirisposta tempiRisposta = listTempirisposta.get(0);
		    amministrazioniHelper.setAttesa(tempiRisposta.getAttesa());
		    amministrazioniHelper.setCalcoladainizioistanza(tempiRisposta.getCalcoladainizioistanza());
		}
		listAministrazioni.add(amministrazioniHelper);
	    }
	    temp.setAmministrazionis(listAministrazioni);
	    risultato.add(temp);
	}
	return risultato;
    }

    private List<AmministrazioniHelper> copyAmministrazioniToAmministrazioHelper(List<Amministrazioni> amministrazionis) {

	AmministrazioniHelper helper = null;
	List<AmministrazioniHelper> list = new ArrayList<AmministrazioniHelper>();
	for (Iterator iterator = amministrazionis.iterator(); iterator.hasNext();) {
	    Amministrazioni amministrazioni = (Amministrazioni) iterator.next();
	    helper = new AmministrazioniHelper();
	    helper.setAmministrazioni(amministrazioni);
	    list.add(helper);
	}
	return list;
    }

    @Override
    public void insertAndUpdateTempirisposta(TempirispostaHelperBean tempirispostaHelperBean) {

	Tipimovimento tipocontromovimento = tipiMovimentoService.findById(new TipimovimentoId(tempirispostaHelperBean.getTipocontromovimento()));
	Tipimovimento tipomovimento = tipiMovimentoService.findById(new TipimovimentoId(tempirispostaHelperBean.getTipomovimento()));
	Map<Integer, Amministrazioni> mAmms = new HashMap<Integer, Amministrazioni>();
	Map<Integer, Tipiprocedure> mprocedure = new HashMap<Integer, Tipiprocedure>();
	for (TempirispostaValoriHelper tempirispostaValoriHelper : tempirispostaHelperBean.getValori()) {
	    Amministrazioni amministrazione = mAmms.get(tempirispostaValoriHelper.getCodiceamministrazione());
	    if (amministrazione == null) {
		amministrazione = amministrazioniService.findById(new PkId(tempirispostaValoriHelper.getCodiceamministrazione()));
		mAmms.put(tempirispostaValoriHelper.getCodiceamministrazione(), amministrazione);
	    }
	    Tipiprocedure procedura = mprocedure.get(tempirispostaValoriHelper.getCodiceprocedura());
	    if (procedura == null) {
		procedura = tipiprocedureService.findById(new PkId(tempirispostaValoriHelper.getCodiceprocedura()));
		mprocedure.put(tempirispostaValoriHelper.getCodiceprocedura(), procedura);
	    }
	    Tempirisposta tempirisposta = new Tempirisposta();
	    TempirispostaId id = new TempirispostaId();
	    id.setCodiceprocedura(tempirispostaValoriHelper.getCodiceprocedura());
	    id.setCodiceamministrazione(tempirispostaValoriHelper.getCodiceamministrazione());
	    id.setTipocontromovimento(tipocontromovimento.getId().getTipomovimento());
	    id.setTipomovimento(tipomovimento.getId().getTipomovimento());
	    tempirisposta.setId(id);
	    tempirisposta.setAmministrazione(amministrazione);
	    tempirisposta.setTipicontromovimento(tipocontromovimento);
	    tempirisposta.setTipimovimento(tipomovimento);
	    tempirisposta.setTipiprocedure(procedura);
	    Tempirisposta entity = tempirispostaDAO.findById(id);
	    if (tempirispostaValoriHelper.getAttesa() != null) {
		tempirisposta.setAttesa(tempirispostaValoriHelper.getAttesa());
		tempirisposta.setCalcoladainizioistanza(tempirispostaValoriHelper.isCalcoladainizioistanza());
		if (entity != null) {
		    tempirispostaDAO.update(tempirisposta);
		} else {
		    tempirispostaDAO.insert(tempirisposta);
		}
	    } else {
		if (entity != null) { // potrebbe non essere stato inserito		     
		    tempirispostaDAO.delete(entity);
		}
	    }
	}
    }

    @Override
    public void insertAndUpdateTempirisposta(TempirispostaCommand tempirispostaCommand) {

	List<TempirispostaHelper> listTempirisp = tempirispostaCommand.getTempirispostaHelpers();
	Tempirisposta tempirispostaFilter = null;
	for (TempirispostaHelper tempirispostaHelper : listTempirisp) {
	    List<AmministrazioniHelper> listAmministrazioniHelper = tempirispostaHelper.getAmministrazionis();
	    for (AmministrazioniHelper amministrazioniHelper : listAmministrazioniHelper) {
		tempirispostaFilter = new Tempirisposta();
		tempirispostaFilter.setAmministrazione(amministrazioniHelper.getAmministrazioni());
		Tempirisposta tempirisposta = new Tempirisposta();
		TempirispostaId id = new TempirispostaId();
		id.setCodiceprocedura(tempirispostaHelper.getTipiprocedure().getId().getCodice());
		id.setCodiceamministrazione(amministrazioniHelper.getAmministrazioni().getId().getCodice());
		id.setTipocontromovimento(tempirispostaHelper.getTipicontromovimento().getId().getTipomovimento());
		id.setTipomovimento(tempirispostaHelper.getTipimovimento().getId().getTipomovimento());
		tempirisposta.setId(id);
		tempirisposta.setAmministrazione(amministrazioniHelper.getAmministrazioni());
		tempirisposta.setTipicontromovimento(tempirispostaHelper.getTipicontromovimento());
		tempirisposta.setTipimovimento(tempirispostaHelper.getTipimovimento());
		tempirisposta.setTipiprocedure(tempirispostaHelper.getTipiprocedure());
		if (amministrazioniHelper.getAttesa() != null) {
		    tempirisposta.setAttesa(amministrazioniHelper.getAttesa());
		    tempirisposta.setCalcoladainizioistanza(amministrazioniHelper.getCalcoladainizioistanza());
		    tempirispostaDAO.insert(tempirisposta);
		} else {
		    List<Tempirisposta> list = tempirispostaDAO.findByFilter(tempirisposta);
		    if (!list.isEmpty()) {
			Tempirisposta objectDelete = list.get(0);
			tempirispostaDAO.delete(objectDelete);
		    }
		}
	    }
	}
    }

    // protected boolean isDeleteAllowed(Tempirisposta entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
    @Override
    public List<Tempirisposta> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazione", Integer.class));
	filterTable.addRestriction(fr);
	return tempirispostaDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Tempirisposta> findByTipimovimentoAndTipicontromovimento(String codiceTipimovimento, String codiceTipicontromovimento) {

	if (codiceTipimovimento == null) {
	    throw new IllegalArgumentException("findByTipimovimentoAndTipicontromovimento: il parametro codiceTipimovimento e' nullo");
	}
	if (codiceTipicontromovimento == null) {
	    throw new IllegalArgumentException("findByTipimovimentoAndTipicontromovimento: il parametro codiceTipicontromovimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", codiceTipimovimento, String.class));
	fr.addFilterField(FilterUtils.equals("id.tipocontromovimento", codiceTipicontromovimento, String.class));
	filterTable.addRestriction(fr);
	return tempirispostaDAO.findByFilterTable(filterTable);
    }
}
