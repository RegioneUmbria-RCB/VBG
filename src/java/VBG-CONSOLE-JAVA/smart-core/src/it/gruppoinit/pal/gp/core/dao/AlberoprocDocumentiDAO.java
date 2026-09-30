package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AlberoprocDocumentiDAO extends BaseDAO<AlberoprocDocumenti, PkId> {

    public List<AlberoprocDocumenti> findByAlberoProc(Integer codice);

    public int findMaxOrder();
}
