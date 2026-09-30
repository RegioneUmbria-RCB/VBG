package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveD;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveDId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class AppIoCodaMassiveDDAOImpl extends BaseDAOImpl<AppIoCodaMassiveD, AppIoCodaMassiveDId> implements IAppIoCodaMassiveDDAO {

    @Override
    public Class<AppIoCodaMassiveD> getEntityClass() {

	return AppIoCodaMassiveD.class;
    }

    @Override
    public List<AppIoCodaMassiveD> findByIdDettaglioMassiveD(int idDettaglioComunicazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idDettaglioComunicazione", idDettaglioComunicazione, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	ft.addRestriction(fr);
	return (List<AppIoCodaMassiveD>) findByFilterTable(ft);
    }
}
