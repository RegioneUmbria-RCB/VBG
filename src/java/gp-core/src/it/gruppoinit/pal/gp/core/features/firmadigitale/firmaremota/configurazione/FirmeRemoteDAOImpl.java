package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.FirmeRemote;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class FirmeRemoteDAOImpl extends BaseDAOImpl<FirmeRemote, PkId> implements FirmeRemoteDAO {

    @Override
    public Class<FirmeRemote> getEntityClass() {

	return FirmeRemote.class;
    }

    @Override
    public List<FirmeRemote> getFirmeAttive() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagAttiva", true, Boolean.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return this.findByFilterTable(ft);
    }
}
