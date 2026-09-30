package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.ManifAreePubbliche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniAreePubblicheSearchFilter;

import java.util.List;

/**
 * 
 * @author
 */
public interface ManifAreePubblicheService extends BaseService<ManifAreePubbliche, PkId> {

    public List<ManifAreePubbliche> findByFilter(ManifestazioniAreePubblicheSearchFilter command);
}
