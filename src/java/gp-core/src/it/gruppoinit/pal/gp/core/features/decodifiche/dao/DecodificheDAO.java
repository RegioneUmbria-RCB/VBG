package it.gruppoinit.pal.gp.core.features.decodifiche.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Decodifiche;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface DecodificheDAO extends BaseDAO<Decodifiche, PkId> {

    List<Decodifiche> findByTabella(String tabella);

    List<String> findDistinctTabelle();

    List<Decodifiche> findByTabellaAndRaggruppamento(String tabella, String raggruppamento);
}
