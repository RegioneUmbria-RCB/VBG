package it.gruppoinit.pal.gp.core.features.scadenzario.service;

import it.gruppoinit.pal.gp.core.domain.BatchScadenzario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzarioListHelper;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.List;

public interface BatchScadenzarioService extends BaseService<BatchScadenzario, PkId> {

    public List<BatchScadenzario> findByFilter(BatchScadenzarioFilter filter, Integer firstResult, Integer maxResult);

    /**
     * NON IMPLEMENTATO SU VISTA
     */
    @Override
    public void insert(BatchScadenzario entity);

    /**
     * NON IMPLEMENTATO SU VISTA
     */
    @Override
    public void update(BatchScadenzario entity);

    /**
     * NON IMPLEMENTATO SU VISTA
     */
    @Override
    public void delete(BatchScadenzario entity);

    public int countByFilter(BatchScadenzarioFilter batchScadenzarioFilter);

    public void clear();

    public int countByHelperFilter(BatchScadenzarioFilter batchScadenzarioFilter);

    public List<ScadenzarioListHelper> findByHelperFilter(BatchScadenzarioFilter batchScadenzarioFilter, Integer startRowPage, Integer endRowPage);
}
