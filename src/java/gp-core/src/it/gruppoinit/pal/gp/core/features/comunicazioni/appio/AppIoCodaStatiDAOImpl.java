package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStati;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStatiId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class AppIoCodaStatiDAOImpl extends BaseDAOImpl<AppIoCodaStati, AppIoCodaStatiId> implements IAppIoCodaStatiDAO {

    @Override
    public Class<AppIoCodaStati> getEntityClass() {

	return AppIoCodaStati.class;
    }

    @Override
    public List<AppIoCodaStati> findByGuid(String guid) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.guid", guid, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	ft.addOrder(FilterUtils.orderDesc("id.data"));
	ft.addRestriction(fr);
	List<AppIoCodaStati> lst = findByFilterTable(ft);
	return lst;
    }
}
