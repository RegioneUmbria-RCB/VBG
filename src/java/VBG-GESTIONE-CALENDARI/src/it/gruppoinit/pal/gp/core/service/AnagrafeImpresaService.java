package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AnagrafeImpresa;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.AnagrafeImpresaFilter;

import java.util.List;

/**
 * 
 * @author
 */
public interface AnagrafeImpresaService extends BaseService<AnagrafeImpresa, PkId> {

    public List<AnagrafeImpresa> findByFilter(AnagrafeImpresaFilter filter);
}
