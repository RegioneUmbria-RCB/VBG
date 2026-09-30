package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParam;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParamId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class AppIoServiziConfigParamDAOImpl extends BaseDAOImpl<AppIoServiziConfigParam, AppIoServiziConfigParamId>
	implements IAppIoServiziConfigParamDAO {

    @Override
    public Class<AppIoServiziConfigParam> getEntityClass() {

	return AppIoServiziConfigParam.class;
    }

    @Override
    public List<AppIoServiziConfigParam> findByIdServizio(String idServizio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.identificativoServizio", idServizio, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	fr.addFilterField(FilterUtils.equals("id.software", ORMHelper.getSoftware(), String.class));
	ft.addRestriction(fr);
	List<AppIoServiziConfigParam> lst = findByFilterTable(ft);
	return lst;
    }

    @Override
    public List<AppIoServiziConfigParam> findByIdServizioEComune(String idServizio, String codComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.identificativoServizio", idServizio, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	fr.addFilterField(FilterUtils.equals("id.codiceComune", codComune, String.class));
	fr.addFilterField(FilterUtils.equals("id.software", ORMHelper.getSoftware(), String.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }
}
