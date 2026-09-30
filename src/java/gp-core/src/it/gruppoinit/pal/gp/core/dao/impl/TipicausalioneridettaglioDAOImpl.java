package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.TipicausalioneridettaglioDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneridettaglio;
import it.gruppoinit.pal.gp.core.domain.TipicausalioneridettaglioId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@Repository
public class TipicausalioneridettaglioDAOImpl extends BaseDAOImpl<Tipicausalioneridettaglio, TipicausalioneridettaglioId>
	implements TipicausalioneridettaglioDAO {

    @Override
    public Class<Tipicausalioneridettaglio> getEntityClass() {

	return Tipicausalioneridettaglio.class;
    }

    @Override
    public List<Tipicausalioneridettaglio> findAll(Integer firstResult, Integer maxResult) {

	//	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
	throw new NotImplementedException();
    }

    @Override
    public List<Tipicausalioneridettaglio> findByCausaleOnere(Integer idCausaleOnere) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkCausaliId", idCausaleOnere, Integer.class));
	ft.addRestriction(fr);
	return this.findByFilterTable(ft);
    }

    @Override
    public Integer findIdContoAttivoByCausaleOneri(Integer codice) {

	Conti conto = this.findContoAttivoByCausaleOneri(codice);
	return conto == null ? null : conto.getId().getCodice();
    }

    @Override
    public Conti findContoAttivoByCausaleOneri(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkCausaliId", codice, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagAttivo", Boolean.TRUE, Boolean.class));
	ft.addRestriction(fr);
	List<Tipicausalioneridettaglio> tipicausalioneridettaglioList = this.findByFilterTable(ft);
	if (!tipicausalioneridettaglioList.isEmpty()) {
	    return tipicausalioneridettaglioList.get(0).getConti();
	}
	return null;
    }

    @Override
    public void deleteByIdOnere(Integer codice) {

	List<Tipicausalioneridettaglio> list = this.findByCausaleOnere(codice);
	for (Tipicausalioneridettaglio tipicausalioneridettaglio : list) {
	    this.delete(tipicausalioneridettaglio);
	}
    }
}
