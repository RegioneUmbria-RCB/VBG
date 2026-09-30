package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.TipicausalioninteressiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@Repository
public class TipicausalioninteressiDAOImpl extends BaseDAOImpl<Tipicausalioninteressi, PkId> implements TipicausalioninteressiDAO {

    @Override
    public Class<Tipicausalioninteressi> getEntityClass() {

	return Tipicausalioninteressi.class;
    }

    @Override
    public List<Tipicausalioninteressi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "ggritardopagamento", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Tipicausalioninteressi> findByIdEGGpassati(Integer id, Integer ggPassati) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipicausalioneri.id.codice", id, Integer.class));
	fr.addFilterField(FilterUtils.greaterEqual("ggritardopagamento", ggPassati, Integer.class));
	ft.addOrder(FilterUtils.orderAsc("ggritardopagamento"));
	ft.addRestriction(fr);
	return this.findByFilterTable(ft);
    }
}
