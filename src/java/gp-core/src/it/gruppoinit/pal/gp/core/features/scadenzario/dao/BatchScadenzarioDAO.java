package it.gruppoinit.pal.gp.core.features.scadenzario.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BatchScadenzario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzarioListHelper;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;

public interface BatchScadenzarioDAO extends BaseDAO<BatchScadenzario, PkId> {

    public List<ScadenzarioListHelper> findByHelperFilter(BatchScadenzarioFilter batchScadenzarioFilter, Integer startRowPage, Integer endRowPage);

    public int countByHelperFilter(BatchScadenzarioFilter batchScadenzarioFilter);
}
