package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AtecoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Ateco;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class AtecoDAOImpl extends BaseDAOImpl<Ateco, Integer> implements AtecoDAO {

    @Override
    public Class<Ateco> getEntityClass() {

	return Ateco.class;
    }

    /**
     * Ricerca tutti gli ateco (senza filtro per idcomune) ordinandoli per codice
     */
    @Override
    public List<Ateco> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "codice", DAOOrderTypeEnum.ASC);
    }
}
