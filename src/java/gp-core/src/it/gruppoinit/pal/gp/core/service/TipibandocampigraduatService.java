package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandocampigraduat;

import java.util.List;

public interface TipibandocampigraduatService extends BaseService<Tipibandocampigraduat, PkId> {

    List<Tipibandocampigraduat> findByTipigraduatoriet(Tipibandocampigraduat tipibandocampigraduat);
}
