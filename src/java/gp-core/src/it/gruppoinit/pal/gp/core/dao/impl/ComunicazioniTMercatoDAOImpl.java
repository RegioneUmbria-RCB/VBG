package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniTMercatoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniTMercato;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ComunicazioniTMercatoDAOImpl extends BaseDAOImpl<ComunicazioniTMercato, PkId> implements ComunicazioniTMercatoDAO {

    @Override
    public Class<ComunicazioniTMercato> getEntityClass() {

	return ComunicazioniTMercato.class;
    }

    @Override
    public List<ComunicazioniTMercato> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }
}
