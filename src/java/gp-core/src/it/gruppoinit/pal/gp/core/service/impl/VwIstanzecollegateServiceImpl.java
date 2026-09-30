package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.VwIstanzecollegateDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegate;
import it.gruppoinit.pal.gp.core.domain.VwIstanzecollegateId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.VwIstanzecollegateService;

/**
 * 
 * @author gianpaolot
 */
@Service
public class VwIstanzecollegateServiceImpl extends BaseServiceImpl<VwIstanzecollegate, VwIstanzecollegateId> implements VwIstanzecollegateService {

    private VwIstanzecollegateDAO vwistanzecollegateDAO;
    private IstanzecollegateService istanzecollegateService;

    @Autowired
    public void setIstanzecollegateService(IstanzecollegateService istanzecollegateService) {

	this.istanzecollegateService = istanzecollegateService;
    }

    @Autowired
    public void setVwIstanzecollegateDAO(VwIstanzecollegateDAO vwistanzecollegateDAO) {

	this.vwistanzecollegateDAO = vwistanzecollegateDAO;
    }

    @Override
    protected Class<VwIstanzecollegate> getEntityClass() {

	return VwIstanzecollegate.class;
    }

    @Override
    public List<VwIstanzecollegate> findAll(Integer firstResult, Integer maxResult) {

	return vwistanzecollegateDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(VwIstanzecollegate entity) {

	throw new NotImplementedException();
    }

    @Override
    public VwIstanzecollegate findById(VwIstanzecollegateId id) {

	return vwistanzecollegateDAO.findById(id);
    }

    @Override
    public void update(VwIstanzecollegate entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwIstanzecollegate entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwIstanzecollegate> findByFilterTable(FilterTable filterTable) {

	return vwistanzecollegateDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<IstanzecollegateHelper> findIstanzecollegateByIstanza(Istanze istanze) {

	return istanzecollegateService.findIstanzecollegateByIstanzaPerVisualizzazione(istanze.getId().getCodice());
    }
    //    protected boolean isDeleteAllowed(VwIstanzecollegate entity) {
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
