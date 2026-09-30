package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAccettazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class BorsellinoAccettazioniDAOImpl extends BaseDAOImpl<BorsellinoAccettazioni, PkId> implements IBorsellinoAccettazioniDAO {

    @Override
    public Class<BorsellinoAccettazioni> getEntityClass() {

	return BorsellinoAccettazioni.class;
    }

    @Override
    public List<BorsellinoAccettazioni> findByBorsellino(Integer idBorsellino) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("borsellinoId", idBorsellino, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }
}