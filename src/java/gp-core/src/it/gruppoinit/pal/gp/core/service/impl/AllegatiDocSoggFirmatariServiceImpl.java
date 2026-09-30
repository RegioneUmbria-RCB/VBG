package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AllegatiDocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.AllegatiDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AllegatiDocSoggFirmatariService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AllegatiDocSoggFirmatariServiceImpl extends BaseServiceImpl<AllegatiDocSoggFirmatari, PkId> implements AllegatiDocSoggFirmatariService {

    private AllegatiDocSoggFirmatariDAO allegatidocsoggfirmatariDAO;
    private AllegatiService allegatiService;
    private TipisoggettoService tipisoggettoService;

    @Autowired
    public void setAllegatiService(AllegatiService allegatiService) {

	this.allegatiService = allegatiService;
    }

    @Autowired
    public void setTipisoggettoService(TipisoggettoService tipisoggettoService) {

	this.tipisoggettoService = tipisoggettoService;
    }

    @Autowired
    public void setAllegatiDocSoggFirmatariDAO(AllegatiDocSoggFirmatariDAO allegatidocsoggfirmatariDAO) {

	this.allegatidocsoggfirmatariDAO = allegatidocsoggfirmatariDAO;
    }

    @Override
    protected Class<AllegatiDocSoggFirmatari> getEntityClass() {

	return AllegatiDocSoggFirmatari.class;
    }

    @Override
    public List<AllegatiDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult) {

	return allegatidocsoggfirmatariDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AllegatiDocSoggFirmatari entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    allegatidocsoggfirmatariDAO.insert(entity);
	}
    }

    @Override
    public AllegatiDocSoggFirmatari findById(PkId id) {

	return allegatidocsoggfirmatariDAO.findById(id);
    }

    @Override
    public void update(AllegatiDocSoggFirmatari entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    allegatidocsoggfirmatariDAO.update(entity);
	}
    }

    @Override
    public void delete(AllegatiDocSoggFirmatari entity) {

	if (isDeleteAllowed(entity)) {
	    allegatidocsoggfirmatariDAO.delete(entity);
	}
    }

    @Override
    public List<AllegatiDocSoggFirmatari> findByDocumento(Integer codiceallegato, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceallegato, "allegati", Integer.class));
	ft.addRestriction(fr);
	return allegatidocsoggfirmatariDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    private void dataIntegration(AllegatiDocSoggFirmatari entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro AllegatiDocSoggFirmatari è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AllegatiDocSoggFirmatari entity) {

	Tipisoggetto tipisoggetto = tipisoggettoService.bindDomainObject(entity.getTipisoggetto(), PkId.class, "id.codice");
	entity.setTipisoggetto(tipisoggetto);
	Allegati allegati = allegatiService.bindDomainObject(entity.getAllegati(), PkId.class,
		"id.codice");
	entity.setAllegati(allegati);
    }

    protected boolean isDeleteAllowed(AllegatiDocSoggFirmatari entity) {

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
