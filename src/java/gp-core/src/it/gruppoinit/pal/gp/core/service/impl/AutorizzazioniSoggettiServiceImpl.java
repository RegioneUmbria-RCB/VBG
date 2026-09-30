package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSoggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSoggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSoggettiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AutorizzazioniSoggettiServiceImpl extends BaseServiceImpl<AutorizzazioniSoggetti, PkId> implements AutorizzazioniSoggettiService {

    private AutorizzazioniSoggettiDAO autorizzazionisoggettiDAO;

    @Autowired
    public void setAutorizzazioniSoggettiDAO(AutorizzazioniSoggettiDAO autorizzazionisoggettiDAO) {

	this.autorizzazionisoggettiDAO = autorizzazionisoggettiDAO;
    }

    @Override
    protected Class<AutorizzazioniSoggetti> getEntityClass() {

	return AutorizzazioniSoggetti.class;
    }

    @Override
    public List<AutorizzazioniSoggetti> findAll(Integer firstResult, Integer maxResult) {

	return autorizzazionisoggettiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AutorizzazioniSoggetti entity) {

	if (validateEntity(entity)) {
	    autorizzazionisoggettiDAO.insert(entity);
	}
    }

    @Override
    public AutorizzazioniSoggetti findById(PkId id) {

	return autorizzazionisoggettiDAO.findById(id);
    }

    @Override
    public void update(AutorizzazioniSoggetti entity) {

	if (validateEntity(entity)) {
	    autorizzazionisoggettiDAO.update(entity);
	}
    }

    @Override
    public void delete(AutorizzazioniSoggetti entity) {

	if (isDeleteAllowed(entity)) {
	    autorizzazionisoggettiDAO.delete(entity);
	}
    }

    @Override
    public List<AutorizzazioniSoggetti> findByAutorizzazione(Integer codiceAut) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAut, "autorizzazioni", Integer.class));
	ft.addRestriction(fr);
	return autorizzazionisoggettiDAO.findByFilterTable(ft);
    }

    @Override
    public AutorizzazioniSoggetti findByAutorizzazioneAndAnagrafe(Integer idAutorizzazione, Integer codiceAnagrafe) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", idAutorizzazione, "autorizzazioni", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	ft.addRestriction(fr);
	List<AutorizzazioniSoggetti> l = autorizzazionisoggettiDAO.findByFilterTable(ft);
	if (!l.isEmpty()) {
	    return l.get(0);
	}
	return null;
    }

    protected boolean isDeleteAllowed(AutorizzazioniSoggetti entity) {

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
}
