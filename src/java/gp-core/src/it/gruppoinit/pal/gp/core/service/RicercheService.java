package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Ricerche;

import java.util.List;

public interface RicercheService extends BaseService<Ricerche, PkId> {

    public List<Ricerche> findByFilter(Integer codiceresponsabile, String chiave);
}
