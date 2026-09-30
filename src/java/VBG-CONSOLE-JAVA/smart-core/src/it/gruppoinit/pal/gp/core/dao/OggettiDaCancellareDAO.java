package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OggettiDaCancellare;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface OggettiDaCancellareDAO extends BaseDAO<OggettiDaCancellare, PkId> {

    List<OggettiDaCancellare> findByCodiceOggetto(Integer codiceoggetto);
}
