package it.gruppoinit.pal.gp.core.features.datidinamici.metadati.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Dyn2Metadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.dao.Dyn2MetadatiDAO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class Dyn2MetadatiDAOImpl extends BaseDAOImpl<Dyn2Metadati, PkId> implements Dyn2MetadatiDAO {

    @Override
    public Class<Dyn2Metadati> getEntityClass() {

	return Dyn2Metadati.class;
    }

    @Override
    public List<Dyn2Metadati> findByIdContesto(Integer id) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("dyn2MetadatiContesti.id.codice", id, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }
}
