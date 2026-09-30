package it.gruppoinit.pal.gp.core.features.datidinamici.metadati.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Dyn2MetadatiContesti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.dao.Dyn2MetadatiContestiDAO;

@Repository
public class Dyn2MetadatiContestiDAOImpl extends BaseDAOImpl<Dyn2MetadatiContesti, PkId> implements Dyn2MetadatiContestiDAO {

    @Override
    public Class<Dyn2MetadatiContesti> getEntityClass() {

	return Dyn2MetadatiContesti.class;
    }
}
