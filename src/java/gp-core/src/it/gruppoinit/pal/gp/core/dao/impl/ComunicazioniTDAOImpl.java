package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniTStatoEnum;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ComunicazioniTDAOImpl extends BaseDAOImpl<ComunicazioniT, PkId> implements ComunicazioniTDAO {

    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";

    @Override
    public Class<ComunicazioniT> getEntityClass() {

	return ComunicazioniT.class;
    }

    @Override
    public List<ComunicazioniT> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public void updateStatoComunicazioneT(Integer codiceComunicazioneT, ComunicazioniTStatoEnum comunicazioniTStatoEnum) {

	if (codiceComunicazioneT == null) {
	    throw new RuntimeException("updateStatoComunicazioneT: il parametro cod istanza passato è nullo");
	}
	if (comunicazioniTStatoEnum != null) {
	    String hql = "update ComunicazioniT set statoElaborazione = ? where id.idcomune = ? and id.codice=?";
	    int i = getHibernateTemplate().bulkUpdate(hql,
		    new Object[] { comunicazioniTStatoEnum.value(), ORMHelper.getIdcomune(), codiceComunicazioneT });
	    if (i != 1) {
		throw new RuntimeException("La query di aggiornamento dello stato ComunicazioniT :[" + codiceComunicazioneT + "] con stato ["
			+ comunicazioniTStatoEnum.value() + "] ha influito su " + i + " record");
	    }
	}
    }
}
