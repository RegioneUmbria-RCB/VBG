package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocArendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocArendoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class AlberoprocArendoServiceImpl extends BaseServiceImpl<AlberoprocArendo, PkId> implements AlberoprocArendoService {

    private AlberoprocArendoDAO alberoprocarendoDAO;
    private TipiendoService tipiendoService;
    private TipifamiglieendoService tipifamiglieendoService;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setAlberoprocArendoDAO(AlberoprocArendoDAO alberoprocarendoDAO) {

	this.alberoprocarendoDAO = alberoprocarendoDAO;
    }

    @Autowired
    public void setTipiendoService(TipiendoService tipiendoService) {

	this.tipiendoService = tipiendoService;
    }

    @Autowired
    public void setTipifamiglieendoService(TipifamiglieendoService tipifamiglieendoService) {

	this.tipifamiglieendoService = tipifamiglieendoService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Override
    protected Class<AlberoprocArendo> getEntityClass() {

	return AlberoprocArendo.class;
    }

    @Override
    public List<AlberoprocArendo> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocarendoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocArendo entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) { //  && isInsertAllowed(entity)
	    alberoprocarendoDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocArendo findById(PkId id) {

	return alberoprocarendoDAO.findById(id);
    }

    @Override
    public void update(AlberoprocArendo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    alberoprocarendoDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocArendo entity) {

	if (isDeleteAllowed(entity)) {
	    alberoprocarendoDAO.delete(entity);
	}
    }

    public List<AlberoprocArendo> findByAlberoProc(Integer codiceAlberoproc, String idcomuneAlberoproc, String idcomunerecord) {

	FilterTable ft = null;
	FilterRestriction restriction = new FilterRestriction();
	ft = new FilterTable(DAOEnum.FIND_ALL);
	restriction.addFilterField(FilterUtils.equals("id.idcomune", idcomunerecord, String.class));
	restriction.addFilterField(FilterUtils.equals("id.idcomune", idcomuneAlberoproc, "alberoproc", String.class));
	restriction.addFilterField(FilterUtils.equals("id.codice", codiceAlberoproc, "alberoproc", Integer.class));
	ft.addRestriction(restriction);
	ft.addOrder(FilterUtils.orderAsc("ordine", "tipifamiglieendo"));
	ft.addOrder(FilterUtils.orderAsc("tipo", "tipifamiglieendo"));
	ft.addOrder(FilterUtils.orderAsc("ordine", "tipiendo"));
	ft.addOrder(FilterUtils.orderAsc("tipo", "tipiendo"));
	return alberoprocarendoDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(AlberoprocArendo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private boolean isInsertAllowed(AlberoprocArendo entity) {

	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_insert
	Set<AlberoprocArendo> alberoprocArendos = entity.getAlberoproc().getAlberoprocArendos();
	for (AlberoprocArendo alberoprocArendo : alberoprocArendos) {
	    if (alberoprocArendo.getTipifamiglieendo().getId().getCodice().equals(entity.getTipifamiglieendo().getId().getCodice())
		    && alberoprocArendo.getTipifamiglieendo().getId().getIdcomune().equals(entity.getTipifamiglieendo().getId().getIdcomune())) {
		if ((alberoprocArendo.getTipiendo() == null && entity.getTipiendo() == null)) {
		    _ivs.add(new InvalidValue("validator.unique.constraint", null, null, null, null));
		} else {
		    if (EntityUtils.getNestedProperty(alberoprocArendo.getTipiendo(), "id.codice") != null
			    && EntityUtils.getNestedProperty(entity.getTipiendo(), "id.codice") != null
			    && EntityUtils.equals(alberoprocArendo.getTipiendo().getId(), entity.getTipiendo().getId())) {
			_ivs.add(new InvalidValue("validator.unique.constraint", null, null, null, null));
		    }
		}
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    private void dataIntegration(AlberoprocArendo entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro AlberoprocArendo è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(AlberoprocArendo entity) {

	Tipiendo tipiendo = tipiendoService.bindDomainObject(entity.getTipiendo(), PkId.class, "id.codice");
	entity.setTipiendo(tipiendo);
	Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService.bindDomainObject(entity.getTipifamiglieendo(), PkId.class, "id.codice");
	entity.setTipifamiglieendo(tipifamiglieendo);
	if (tipiendo != null && tipifamiglieendo == null) {
	    entity.setTipifamiglieendo(tipiendo.getTipifamiglieendo());
	}
    }
}
