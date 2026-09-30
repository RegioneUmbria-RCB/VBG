package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipigraduatorietEsprArtDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigraduatorietEsprArt;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietEsprArtService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class TipigraduatorietEsprArtServiceImpl extends BaseServiceImpl<TipigraduatorietEsprArt, PkId> implements TipigraduatorietEsprArtService {

    private TipigraduatorietEsprArtDAO tipigraduatorietesprartDAO;
    private Dyn2CampiService dyn2CampiService;

    @Autowired
    public void setTipigraduatorietEsprArtDAO(TipigraduatorietEsprArtDAO tipigraduatorietesprartDAO) {

	this.tipigraduatorietesprartDAO = tipigraduatorietesprartDAO;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Override
    protected Class<TipigraduatorietEsprArt> getEntityClass() {

	return TipigraduatorietEsprArt.class;
    }

    @Override
    public List<TipigraduatorietEsprArt> findAll(Integer firstResult, Integer maxResult) {

	return tipigraduatorietesprartDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipigraduatorietEsprArt entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipigraduatorietesprartDAO.insert(entity);
	}
    }

    @Override
    public TipigraduatorietEsprArt findById(PkId id) {

	return tipigraduatorietesprartDAO.findById(id);
    }

    @Override
    public void update(TipigraduatorietEsprArt entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipigraduatorietesprartDAO.update(entity);
	}
    }

    @Override
    public void delete(TipigraduatorietEsprArt entity) {

	if (isDeleteAllowed(entity)) {
	    tipigraduatorietesprartDAO.delete(entity);
	}
    }

    @Override
    public List<TipigraduatorietEsprArt> findByTipigraduatoriet(Integer codice) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "tipigraduatoriet", Integer.class));
	filterTable.addRestriction(fr);
	return tipigraduatorietesprartDAO.findByFilterTable(filterTable);
    }

    private void dataIntegration(TipigraduatorietEsprArt entity) {

	if (entity == null) {
	    throw new RuntimeException("Non si può inserire/aggiornare TipigraduatorietEsprArt nulla");
	}
	if (entity.getFlagMultiplo() == null) {
	    entity.setFlagMultiplo(false);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(TipigraduatorietEsprArt entity) {

	Dyn2Campi campiA = dyn2CampiService.bindDomainObject(entity.getCampiA(), PkId.class, "id.codice");
	entity.setCampiA(campiA);
	Dyn2Campi campiDa = dyn2CampiService.bindDomainObject(entity.getCampiDa(), PkId.class, "id.codice");
	entity.setCampiDa(campiDa);
	Dyn2Campi campiPosteggio = dyn2CampiService.bindDomainObject(entity.getCampiPosteggio(), PkId.class, "id.codice");
	entity.setCampiPosteggio(campiPosteggio);
    }
    //    protected boolean isDeleteAllowed(TipigraduatorietEsprArt entity) {
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
}
