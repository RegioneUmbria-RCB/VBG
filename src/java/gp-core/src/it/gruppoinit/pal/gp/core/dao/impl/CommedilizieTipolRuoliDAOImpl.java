package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.CommedilizieTipolRuoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipolRuoli;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipolRuoliId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class CommedilizieTipolRuoliDAOImpl extends BaseDAOImpl<CommedilizieTipolRuoli, CommedilizieTipolRuoliId>
	implements CommedilizieTipolRuoliDAO {

    @Override
    public Class<CommedilizieTipolRuoli> getEntityClass() {

	return CommedilizieTipolRuoli.class;
    }

    @Override
    public List<CommedilizieTipolRuoli> findByTipologia(Integer idTipologia) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkCommeditipoId", idTipologia, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public List<CommedilizieTipolRuoli> findByRuolo(Integer idRuolo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkRuoliId", idRuolo, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }
}
