package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.InventarioprocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DocumentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocSoggFirmatariService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class InventarioprocSoggFirmatariServiceImpl extends BaseServiceImpl<InventarioprocSoggFirmatari, PkId> implements
	InventarioprocSoggFirmatariService {

    private InventarioprocSoggFirmatariDAO inventarioprocsoggfirmatariDAO;
    private DocumentiService documentiService;
    private TipisoggettoService tipisoggettoService;

    @Autowired
    public void setInventarioprocSoggFirmatariDAO(InventarioprocSoggFirmatariDAO inventarioprocsoggfirmatariDAO) {

	this.inventarioprocsoggfirmatariDAO = inventarioprocsoggfirmatariDAO;
    }

    @Autowired
    public void setDocumentiService(DocumentiService documentiService) {

	this.documentiService = documentiService;
    }

    @Autowired
    public void setTipisoggettoService(TipisoggettoService tipisoggettoService) {

	this.tipisoggettoService = tipisoggettoService;
    }

    @Override
    protected Class<InventarioprocSoggFirmatari> getEntityClass() {

	return InventarioprocSoggFirmatari.class;
    }

    @Override
    public List<InventarioprocSoggFirmatari> findAll(Integer firstResult, Integer maxResult) {

	return inventarioprocsoggfirmatariDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(InventarioprocSoggFirmatari entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocsoggfirmatariDAO.insert(entity);
	}
    }

    @Override
    public InventarioprocSoggFirmatari findById(PkId id) {

	return inventarioprocsoggfirmatariDAO.findById(id);
    }

    @Override
    public void update(InventarioprocSoggFirmatari entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    inventarioprocsoggfirmatariDAO.update(entity);
	}
    }

    @Override
    public void delete(InventarioprocSoggFirmatari entity) {

	if (isDeleteAllowed(entity)) {
	    inventarioprocsoggfirmatariDAO.delete(entity);
	}
    }

    @Override
    public List<InventarioprocSoggFirmatari> findByDocumento(Integer codicedocumento, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codicedocumento, "documenti", Integer.class));
	ft.addRestriction(fr);
	return inventarioprocsoggfirmatariDAO.findByFilterTable(ft, firstResult, maxResult);
    }
    
    private void dataIntegration(InventarioprocSoggFirmatari entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro inventarioprocedimenti è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(InventarioprocSoggFirmatari entity) {

	Tipisoggetto tipisoggetto = tipisoggettoService.bindDomainObject(entity.getTipisoggetto(), PkId.class, "id.codice");
	entity.setTipisoggetto(tipisoggetto);
	Documenti documenti = documentiService.bindDomainObject(entity.getDocumenti(), PkId.class, "id.codice");
	entity.setDocumenti(documenti);
    }
    //	protected boolean isDeleteAllowed(InventarioprocSoggFirmatari entity) {
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
