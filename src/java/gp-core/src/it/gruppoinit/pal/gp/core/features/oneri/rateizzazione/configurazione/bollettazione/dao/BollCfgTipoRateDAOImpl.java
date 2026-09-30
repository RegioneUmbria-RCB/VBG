package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.bollettazione.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipoRate;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class BollCfgTipoRateDAOImpl extends BaseDAOImpl<BollCfgTipoRate, PkId> implements IBollCfgTipoRateDAO {

    @Override
    public Class<BollCfgTipoRate> getEntityClass() {

	return BollCfgTipoRate.class;
    }

    @Override
    public List<BollCfgTipoRate> findByTipo(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "bollCfgTipo", Integer.class));
	ft.addRestriction(fr);
	return this.findByFilterTable(ft);
    }

    @Override
    public boolean verificaSePresente(Integer idBoll, Integer idRange) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollCfgTipoId", idBoll, Integer.class));
	fr.addFilterField(FilterUtils.equals("rangeRateizzazioniId", idRange, Integer.class));
	ft.addRestriction(fr);
	List<BollCfgTipoRate> list = this.findByFilterTable(ft);
	return list.size() > 0 ? true : false;
    }
}
