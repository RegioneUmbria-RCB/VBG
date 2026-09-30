package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.GruppiEndoprocedimentiDDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.GruppiEndoprocedimentiDService;

@Service
public class GruppiEndoprocedimentiDServiceImpl extends BaseServiceImpl<GruppiEndoprocedimentiD, PkId> implements GruppiEndoprocedimentiDService {

    private GruppiEndoprocedimentiDDAO gruppiEndoprocedimentiDDAO;

    @Autowired
    public void setGruppiEndoprocedimentiDDAO(GruppiEndoprocedimentiDDAO gruppiEndoprocedimentiDDAO) {

	this.gruppiEndoprocedimentiDDAO = gruppiEndoprocedimentiDDAO;
    }

    @Override
    public void insert(GruppiEndoprocedimentiD entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    gruppiEndoprocedimentiDDAO.insert(entity);
	}
    }

    private void dataIntegration(GruppiEndoprocedimentiD entity) {

    }

    @Override
    public void update(GruppiEndoprocedimentiD entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    gruppiEndoprocedimentiDDAO.update(entity);
	}
    }

    @Override
    public void delete(GruppiEndoprocedimentiD entity) {

	if (isDeleteAllowed(entity)) {
	    gruppiEndoprocedimentiDDAO.delete(entity);
	}
    }

    @Override
    public List<GruppiEndoprocedimentiD> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public GruppiEndoprocedimentiD findById(PkId id) {

	return gruppiEndoprocedimentiDDAO.findById(id);
    }

    @Override
    protected Class<GruppiEndoprocedimentiD> getEntityClass() {

	return GruppiEndoprocedimentiD.class;
    }

    @Override
    public List<GruppiEndoprocedimentiD> findByGruppiT(Integer codiceGruppoT) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("gruppiEndoprocedimentiTId", codiceGruppoT, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("procedimento", "inventarioprocedimento"));
	return gruppiEndoprocedimentiDDAO.findByFilterTable(ft);
    }

    @Override
    public List<GruppiEndoprocedimentiD> findByCodiceInventario(Integer codiceEndo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentoId", codiceEndo, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "gruppiEndoprocedimentiT"));
	return gruppiEndoprocedimentiDDAO.findByFilterTable(ft);
    }

    @Override
    public Set<Integer> findByEndoprocedimenti(Set<Integer> codiciEndoprocedimenti, String software) {

	return gruppiEndoprocedimentiDDAO.findByEndoprocedimenti(codiciEndoprocedimenti, software);
    }

    @Override
    public List<ChiaveValoreBean<Integer, String>> findByEndoprocedimentiConWarning(Set<Integer> codiciEndoprocedimenti, String software) {

	return gruppiEndoprocedimentiDDAO.findByEndoprocedimentiConWarning(codiciEndoprocedimenti, software);
    }

    @Override
    public Set<Integer> findEndoProcedimentiNonPresentiInGruppi(Set<Integer> codiciEndoprocedimenti) {

	return gruppiEndoprocedimentiDDAO.findEndoProcedimentiNonPresentiInGruppi(codiciEndoprocedimenti);
    }
}
