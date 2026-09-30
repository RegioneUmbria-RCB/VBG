package it.gruppoinit.pal.gp.core.features.datidinamici.metadati.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Metadati;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface Dyn2MetadatiDAO extends BaseDAO<Dyn2Metadati, PkId> {

    public List<Dyn2Metadati> findByIdContesto(Integer id);
}
