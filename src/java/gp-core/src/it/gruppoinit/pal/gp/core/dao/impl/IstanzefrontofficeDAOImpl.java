package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzefrontofficeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Istanzefrontoffice;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class IstanzefrontofficeDAOImpl extends BaseDAOImpl<Istanzefrontoffice, PkId> implements IstanzefrontofficeDAO {

    @Override
    public Class<Istanzefrontoffice> getEntityClass() {

	return Istanzefrontoffice.class;
    }

    @Override
    public List<Istanzefrontoffice> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "datapresentazione", DAOOrderTypeEnum.ASC);
    }
}
