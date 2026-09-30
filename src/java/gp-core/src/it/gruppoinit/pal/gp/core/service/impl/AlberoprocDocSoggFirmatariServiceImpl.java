package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocSoggFirmatariService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class AlberoprocDocSoggFirmatariServiceImpl extends BaseServiceImpl<AlberoprocDocSoggFirmatari, PkId> implements
	AlberoprocDocSoggFirmatariService {

    private AlberoprocDocSoggFirmatariDAO alberoprocdocsoggfirmatariDAO;
    private AlberoprocDocumentiService alberoprocDocumentiService;
    private TipisoggettoService tipisoggettoService;

    @Autowired
    public void setAlberoprocDocSoggFirmatariDAO(AlberoprocDocSoggFirmatariDAO alberoprocdocsoggfirmatariDAO) {

	this.alberoprocdocsoggfirmatariDAO = alberoprocdocsoggfirmatariDAO;
    }

    @Autowired
    public void setAlberoprocDocumentiService(AlberoprocDocumentiService alberoprocDocumentiService) {

	this.alberoprocDocumentiService = alberoprocDocumentiService;
    }

    @Autowired
    public void setTipisoggettoService(TipisoggettoService tipisoggettoService) {

	this.tipisoggettoService = tipisoggettoService;
    }

    @Override
    protected Class<AlberoprocDocSoggFirmatari> getEntityClass() {

	return AlberoprocDocSoggFirmatari.class;
    }

    @Override
    public List<AlberoprocDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocdocsoggfirmatariDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocDocSoggFirmatari entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocdocsoggfirmatariDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocDocSoggFirmatari findById(PkId id) {

	return alberoprocdocsoggfirmatariDAO.findById(id);
    }

    @Override
    public void update(AlberoprocDocSoggFirmatari entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocdocsoggfirmatariDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocDocSoggFirmatari entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocdocsoggfirmatariDAO.delete(entity);
	}
    }

    private void dataIntegration(AlberoprocDocSoggFirmatari entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro inventarioprocedimenti è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AlberoprocDocSoggFirmatari entity) {

	Tipisoggetto tipisoggetto = tipisoggettoService.bindDomainObject(entity.getTipisoggetto(), PkId.class, "id.codice");
	entity.setTipisoggetto(tipisoggetto);
	AlberoprocDocumenti alberoprocDocumenti = alberoprocDocumentiService.bindDomainObject(entity.getAlberoprocDocumenti(), PkId.class,
		"id.codice");
	entity.setAlberoprocDocumenti(alberoprocDocumenti);
    }

    @Override
    public List<AlberoprocDocSoggFirmatari> findByDocumento(Integer codicedocumento, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codicedocumento, "alberoprocDocumenti", Integer.class));
	ft.addRestriction(fr);
	return alberoprocdocsoggfirmatariDAO.findByFilterTable(ft, firstResult, maxResult);
    }
    //    protected boolean isDeleteAllowed(AlberoprocDocSoggFirmatari entity) {
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
    public Integer countSoggettiInUsoForDocuments(List<Integer> alberoprocids, Integer tipisoggetto ) {
	return alberoprocdocsoggfirmatariDAO.countSoggettiInUsoForDocuments(alberoprocids, tipisoggetto);
    }
}
