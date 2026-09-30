package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.FoDomrichAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface FoDomrichAllegatiService extends BaseService<FoDomrichAllegati, PkId> {

    List<FoDomrichAllegati> findByRichiesta(String idcomune, Integer idrichiesta);
}
