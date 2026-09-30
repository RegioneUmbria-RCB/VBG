package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniCsiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniCsiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AutorizzazioniCsiServiceImpl extends BaseServiceImpl<AutorizzazioniCsi, PkId> implements AutorizzazioniCsiService {

    private AutorizzazioniCsiDAO autorizzazionicsiDAO;

    @Autowired
    public void setAutorizzazioniCsiDAO(AutorizzazioniCsiDAO autorizzazionicsiDAO) {

	this.autorizzazionicsiDAO = autorizzazionicsiDAO;
    }

    @Override
    protected Class<AutorizzazioniCsi> getEntityClass() {

	return AutorizzazioniCsi.class;
    }

    @Override
    public List<AutorizzazioniCsi> findAll(Integer firstResult, Integer maxResult) {

	return autorizzazionicsiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AutorizzazioniCsi entity) {

	if (validateEntity(entity)) {
	    autorizzazionicsiDAO.insert(entity);
	}
    }

    @Override
    public AutorizzazioniCsi findById(PkId id) {

	return autorizzazionicsiDAO.findById(id);
    }

    @Override
    public void update(AutorizzazioniCsi entity) {

	if (validateEntity(entity)) {
	    autorizzazionicsiDAO.update(entity);
	}
    }

    @Override
    public void delete(AutorizzazioniCsi entity) {

	if (isDeleteAllowed(entity)) {
	    autorizzazionicsiDAO.delete(entity);
	}
    }

    @Override
    public AutorizzazioniCsi findByAutorizzazione(Integer idAutorizzazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", idAutorizzazione, "autorizzazioni", Integer.class));
	ft.addRestriction(fr);
	List<AutorizzazioniCsi> r = autorizzazionicsiDAO.findByFilterTable(ft, 0, 1);
	if (!r.isEmpty()) {
	    return r.get(0);
	}
	return null;
    }

    protected boolean isDeleteAllowed(AutorizzazioniCsi entity) {

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
    public boolean existsRecordsPerEnte() {

	return autorizzazionicsiDAO.existsRecords(new FilterTable(DAOEnum.FIND_BY_IDCOMUNE));
    }
}
