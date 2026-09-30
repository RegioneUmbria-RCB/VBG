package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcDettaglisuperficieDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CcDettaglisuperficie;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcDettaglisuperficieDAOImpl extends BaseDAOImpl<CcDettaglisuperficie, PkId> implements CcDettaglisuperficieDAO {

    @Override
    public Class<CcDettaglisuperficie> getEntityClass() {

	return CcDettaglisuperficie.class;
    }

    @Override
    public List<CcDettaglisuperficie> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
