package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DehorsCfgDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.DehorsCfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.DehorsCfgService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class DehorsCfgServiceImpl extends BaseServiceImpl<DehorsCfg, PkId> implements DehorsCfgService {

    private DehorsCfgDAO dehorscfgDAO;
    private ConcessionicausaliService concessionicausaliService;
    private TipologiaregistriService tipologiaregistriService;

    @Autowired
    public void setDehorsCfgDAO(DehorsCfgDAO dehorscfgDAO) {

	this.dehorscfgDAO = dehorscfgDAO;
    }

    @Autowired
    public void setConcessionicausaliService(ConcessionicausaliService concessionicausaliService) {

	this.concessionicausaliService = concessionicausaliService;
    }

    @Autowired
    public void setTipologiaregistriService(TipologiaregistriService tipologiaregistriService) {

	this.tipologiaregistriService = tipologiaregistriService;
    }

    @Override
    protected Class<DehorsCfg> getEntityClass() {

	return DehorsCfg.class;
    }

    @Override
    public List<DehorsCfg> findAll(Integer firstResult, Integer maxResult) {

	return dehorscfgDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DehorsCfg entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dehorscfgDAO.insert(entity);
	}
    }

    @Override
    public DehorsCfg findById(PkId id) {

	return dehorscfgDAO.findById(id);
    }

    @Override
    public void update(DehorsCfg entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    dehorscfgDAO.update(entity);
	}
    }

    @Override
    public void delete(DehorsCfg entity) {

	if (isDeleteAllowed(entity)) {
	    dehorscfgDAO.delete(entity);
	}
    }

    private void dataIntegration(DehorsCfg entity) {

	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(DehorsCfg entity) {

	Tipologiaregistri tipologiaregistri = tipologiaregistriService.bindDomainObject(entity.getTipologiaregistri(), PkId.class, "id.codice");
	entity.setTipologiaregistri(tipologiaregistri);
	Concessionicausali concessionicausali = concessionicausaliService.bindDomainObject(entity.getConcessionicausali(), PkId.class, "id.codice");
	entity.setConcessionicausali(concessionicausali);
    }

    @Override
    public boolean isExistRecord() {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	return dehorscfgDAO.existsRecords(filterTable);
    }

    @Override
    public DehorsCfg findByTipologiaregistro(Integer codiceRegistro) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceRegistro, "tipologiaregistri", Integer.class));
	filterTable.addRestriction(fr);
	List<DehorsCfg> list = dehorscfgDAO.findByFilterTable(filterTable);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
    //	protected boolean isDeleteAllowed(DehorsCfg entity) {
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
