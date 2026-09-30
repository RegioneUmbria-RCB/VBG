package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.EsportazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;

/**
 * 
 * @author
 */
@Service
public class EsportazioniServiceImpl extends BaseServiceImpl<Esportazioni, PkId> implements EsportazioniService {

    private EsportazioniDAO esportazioniDAO;
    private PentahocfgService pentahocfgService;

    @Autowired
    public void setEsportazioniDAO(EsportazioniDAO esportazioniDAO) {

	this.esportazioniDAO = esportazioniDAO;
    }

    @Autowired
    public void setPentahocfgService(PentahocfgService pentahocfgService) {

	this.pentahocfgService = pentahocfgService;
    }

    @Override
    protected Class<Esportazioni> getEntityClass() {

	return Esportazioni.class;
    }

    @Override
    public List<Esportazioni> findAll(Integer firstResult, Integer maxResult) {

	return esportazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Esportazioni entity) {

	if (validateEntity(entity)) {
	    esportazioniDAO.insert(entity);
	}
    }

    @Override
    public Esportazioni findById(PkId id) {

	return esportazioniDAO.findById(id);
    }

    @Override
    public void update(Esportazioni entity) {

	if (validateEntity(entity)) {
	    esportazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Esportazioni entity) {

	if (isDeleteAllowed(entity)) {
	    esportazioniDAO.delete(entity);
	}
    }

    @Override
    public List<Esportazioni> findEsportazioni(TipicontestoesportazioniEnum tipicontestoesportazioniEnum) {

	return this.findEsportazioniEscludiRecord(tipicontestoesportazioniEnum, null);
    }

    @Override
    public List<Esportazioni> findEsportazioniEscludiRecord(TipicontestoesportazioniEnum tipicontestoesportazioniEnum, List<PkId> ids) {

	// recupero se esiste la configurazione su pentaho cfg
	Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
	String identebase = "";
	if (pentahocfg != null && StringUtils.isNotBlank(pentahocfg.getIdcomune())) {
	    identebase = pentahocfg.getIdentebase();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("id.idcomune", new Object[] { ORMHelper.getIdcomune(), identebase }, String.class));
	fr.addFilterField(FilterUtils.in("software.codice", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }, String.class));
	fr.addFilterField(FilterUtils.equals("flgAbilitata", true, Boolean.class));
	if (ids != null && !ids.isEmpty()) {
	    for (PkId pkId : ids) {
		//fr.addFilterField(FilterUtils.notEquals("id.idcomune", pkId.getIdcomune(), String.class));
		fr.addFilterField(FilterUtils.notEquals("id.codice", pkId.getCodice(), Integer.class));
	    }
	}
	String contesto = "";
	switch (tipicontestoesportazioniEnum) {
	case ISTANZE:
	    contesto = "IST";
	    break;
	case ATTIVITA:
	    contesto = "ATT";
	    break;
	case ABBONAMENTO:
	    contesto = "ABB";
	    break;
	case ATTIVITA_SNAPSHOT:
	    contesto = "ATS";
	    break;
	case CONCESSIONI:
	    contesto = "CON";
	    break;
	case POSTEGGI_MERCATO:
	    contesto = "POS";
	    break;
	case GIORNATA_MERCATO:
	    contesto = "MPT";
	    break;
	case GENERICI:
	    contesto = "GEN";
	    break;
	case BOLLETTAZIONE:
	    contesto = "BOL";
	    break;
	case AUTORIZZAZIONI:
	    contesto = "AUT";
	    break;
	default:
	    break;
	}
	fr.addFilterField(FilterUtils.equals("codice", contesto, "tipicontestoesportazione", String.class));
	ft.addRestriction(fr);
	return esportazioniDAO.findByFilterTable(ft);
    }
    //	protected boolean isDeleteAllowed(Esportazioni entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }

    @Override
    public void inserisci(Esportazioni esportazioni) {

	esportazioniDAO.inserisci(esportazioni);
    }
}
