package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.BollCfgTipoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollCfgTipo;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

/**
 * 
 * @author
 */
@Repository
public class BollCfgTipoDAOImpl extends BaseDAOImpl<BollCfgTipo, PkId> implements BollCfgTipoDAO {

    @Autowired
    ResponsabiliService responsabiliService;

    @Override
    public Class<BollCfgTipo> getEntityClass() {

	return BollCfgTipo.class;
    }

    @Override
    public List<BollCfgTipo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }

    @Override
    public List<CreazioneBollCfgTipo> findByResponsabile(Integer codiceResponsabile) {

	Responsabili responsabile = responsabiliService.findById(new PkId(codiceResponsabile));
	Set<Responsabiliruoli> responsabiliruoli = responsabile.getResponsabiliruolis();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (!responsabiliruoli.isEmpty()) {
	    List<Integer> codiceRuolis = new ArrayList<Integer>();
	    for (Responsabiliruoli responsabiliruolo : responsabiliruoli) {
		codiceRuolis.add(responsabiliruolo.getRuolo().getId().getCodice());
	    }
	    FilterRestriction fr = new FilterRestriction();
	    fr.setAndOrRestriction(AndOrRestriction.OR);
	    fr.addFilterField(FilterUtils.in("id.fkRuoliId", codiceRuolis.toArray(), "bllCfgRuolis", Integer.class));
	    fr.addFilterField(FilterUtils.isNull("id.fkRuoliId", "bllCfgRuolis"));
	    ft.addRestriction(fr);
	} else {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.isNull("id.fkRuoliId", "bllCfgRuolis"));
	    ft.addRestriction(fr);
	}
	FilterRestriction soft = new FilterRestriction();
	soft.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	ft.addRestriction(soft);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	List<BollCfgTipo> list = this.findByFilterTable(ft, null, null);
	List<CreazioneBollCfgTipo> result = new ArrayList<CreazioneBollCfgTipo>();
	for (BollCfgTipo bollCfgTipo : list) {
	    CreazioneBollCfgTipo creazioneBollCfgTipo = new CreazioneBollCfgTipo(bollCfgTipo.getId().getCodice(), bollCfgTipo.getDescrizione());
	    result.add(creazioneBollCfgTipo);
	}
	return result;
    }
}
