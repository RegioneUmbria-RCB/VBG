package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MercatiCfgContiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class MercatiCfgContiDAOImpl extends BaseDAOImpl<MercatiCfgConti, PkId> implements MercatiCfgContiDAO {

    @Override
    public Class<MercatiCfgConti> getEntityClass() {

	return MercatiCfgConti.class;
    }

    @Override
    public List<MercatiCfgConti> findAttiviInData(Date data) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction frDate = new FilterRestriction();
	frDate.addFilterField(FilterUtils.smallerEqual("datainizioval", data, Date.class));
	frDate.addFilterField(FilterUtils.greaterEqual("datafineval", data, Date.class));
	ft.addRestriction(frDate);
	ft.addOrder(FilterUtils.orderDesc("mercatiCategorieId", FunctionsEnum.NVL_FUNCTION, "0"));
	ft.addOrder(FilterUtils.orderDesc("concessioniusoId", FunctionsEnum.NVL_FUNCTION, "0"));
	ft.addOrder(FilterUtils.orderDesc("posteggiSettoriId", FunctionsEnum.NVL_FUNCTION, "0"));
	ft.addOrder(FilterUtils.orderDesc("attivitaId", FunctionsEnum.NVL_FUNCTION, "' '"));
	return this.findByFilterTable(ft);
    }
}
