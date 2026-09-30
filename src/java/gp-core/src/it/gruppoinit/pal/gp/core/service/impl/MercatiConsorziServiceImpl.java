package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiConsorziDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiConsorzi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiConsorziService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatiConsorziServiceImpl extends BaseServiceImpl<MercatiConsorzi, PkId> implements MercatiConsorziService {

    private MercatiConsorziDAO mercatiConsorziDAO;

    @Autowired
    public void setMercatiConsorziDAO(MercatiConsorziDAO mercatiConsorziDAO) {

	this.mercatiConsorziDAO = mercatiConsorziDAO;
    }

    @Override
    public void insert(MercatiConsorzi entity) {

	if (validateEntity(entity)) {
	    mercatiConsorziDAO.insert(entity);
	}
    }

    @Override
    public void update(MercatiConsorzi entity) {

	if (validateEntity(entity)) {
	    mercatiConsorziDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiConsorzi entity) {

	if (isDeleteAllowed(entity)) {
	    mercatiConsorziDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiConsorzi> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public MercatiConsorzi findById(PkId id) {

	return mercatiConsorziDAO.findById(id);
    }

    @Override
    public List<MercatiConsorzi> findByCodiceMercato(Integer codiceMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInizioEsercizio"));
	return mercatiConsorziDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiConsorzi> findByCodiceAnagrafe(Integer codiceAnagrafe) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("consorzioId", codiceAnagrafe, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInizioEsercizio"));
	return mercatiConsorziDAO.findByFilterTable(ft);
    }

    @Override
    protected Class<MercatiConsorzi> getEntityClass() {

	return MercatiConsorzi.class;
    }

    @Override
    public List<MercatiConsorzi> findByCodiceMercatoAndDataRiferimento(Integer codiceMercato, Date datariferimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	ft.addRestriction(fr);
	FilterRestriction die = new FilterRestriction();
	die.setAndOrRestriction(AndOrRestriction.OR);
	die.addFilterField(FilterUtils.smallerEqual("dataInizioEsercizio", datariferimento, Date.class));
	die.addFilterField(FilterUtils.isNull("dataInizioEsercizio"));
	ft.addRestriction(die);
	FilterRestriction dfe = new FilterRestriction();
	dfe.setAndOrRestriction(AndOrRestriction.OR);
	dfe.addFilterField(FilterUtils.greaterEqual("dataFineEsercizio", datariferimento, Date.class));
	dfe.addFilterField(FilterUtils.isNull("dataFineEsercizio"));
	ft.addRestriction(dfe);
	ft.addOrder(FilterUtils.orderAsc("dataInizioEsercizio"));
	return mercatiConsorziDAO.findByFilterTable(ft);
    }
}
