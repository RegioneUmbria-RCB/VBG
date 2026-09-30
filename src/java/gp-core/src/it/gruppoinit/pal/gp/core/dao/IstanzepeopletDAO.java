package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzepeoplet;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface IstanzepeopletDAO extends BaseDAO<Istanzepeoplet, PkId> {

    /**
     * Recupera istanza people a partire da un istanza se esiste
     */
    public List<Istanzepeoplet> findIstanzapeoletByIstanza(Istanze istanze);
}
