package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiLivelloServizioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MercatiLivelloServizioDAOImpl extends BaseDAOImpl<MercatiLivelloServizio, PkId> implements MercatiLivelloServizioDAO {

    @Override
    public Class<MercatiLivelloServizio> getEntityClass() {

	return MercatiLivelloServizio.class;
    }

    @Override
    public List<MercatiLivelloServizio> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
    
    
    
    
    
}
