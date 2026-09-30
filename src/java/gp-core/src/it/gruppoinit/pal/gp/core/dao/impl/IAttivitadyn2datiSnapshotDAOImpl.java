package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiSnapshotDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshotId;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class IAttivitadyn2datiSnapshotDAOImpl extends BaseDAOImpl<IAttivitadyn2datiSnapshot, IAttivitadyn2datiSnapshotId>
	implements IAttivitadyn2datiSnapshotDAO {

    @Override
    public Class<IAttivitadyn2datiSnapshot> getEntityClass() {

	return IAttivitadyn2datiSnapshot.class;
    }

    @Override
    public List<IAttivitadyn2datiSnapshot> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @Override
    public int getMaxIndice(Integer codiceAttivita, Integer idmodello) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "SELECT max(iad2d_s.indice) as max FROM " + schemaName + ".i_attivitadyn2dati_snapshot iad2d_s join " + schemaName
		+ ".dyn2_modellid d2md on " + "iad2d_s.idcomune=d2md.idcomune and iad2d_s.fk_d2c_id = d2md.fk_d2c_id join " + schemaName
		+ ".i_attivitadyn2mod_t_snapshot iad2mt_s " + "on  iad2mt_s.idcomune=d2md.idcomune and iad2mt_s.fk_d2mt_id = d2md.fk_d2mt_id WHERE "
		+ " iad2d_s.idcomune =? AND iad2d_s.fk_ia_id =? and iad2mt_s.fk_d2mt_id=?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("max", Hibernate.BIG_DECIMAL);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceAttivita);
	q.setInteger(2, idmodello);
	List<BigDecimal> rs = q.list();
	int ris = 0;
	if (rs != null) {
	    if (rs.size() > 0) {
		if (rs.get(0) != null) {
		    ris = ((BigDecimal) rs.get(0)).intValue();
		}
	    }
	}
	return ris;
    }

    @Override
    public int getMaxIndiceMolteplicita(Integer codiceAttivita, Integer idmodello) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "SELECT max(iad2d_s. indice_molteplicita) as max FROM " + schemaName + ".i_attivitadyn2dati_snapshot iad2d_s join " + schemaName
		+ ".dyn2_modellid d2md on iad2d_s.idcomune=d2md.idcomune and iad2d_s.fk_d2c_id = d2md.fk_d2c_id join " + schemaName
		+ ".i_attivitadyn2mod_t_snapshot iad2mt_s on iad2mt_s.idcomune=d2md.idcomune and iad2mt_s.fk_d2mt_id = d2md.fk_d2mt_id "
		+ "WHERE iad2d_s.idcomune=? AND  iad2d_s.fk_ia_id=? and iad2mt_s.fk_d2mt_id=?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("max", Hibernate.BIG_DECIMAL);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceAttivita);
	q.setInteger(2, idmodello);
	List<BigDecimal> rs = q.list();
	int ris = 0;
	if (rs != null) {
	    if (rs.size() > 0) {
		if (rs.get(0) != null) {
		    ris = ((BigDecimal) rs.get(0)).intValue();
		}
	    }
	}
	return ris;
    }

    @Override
    public void deleteByAttivitaAndCampo(Integer codiceAttivita, Integer idCampo) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "delete from " + schemaName + ".I_ATTIVITADYN2DATI_SNAPSHOT where FK_D2C_ID=? and FK_IA_ID=? and IDCOMUNE=?";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setInteger(0, idCampo);
	query.setInteger(1, codiceAttivita);
	query.setString(2, ORMHelper.getIdcomune());
	query.executeUpdate();
    }

    @Override
    public void deleteBySnapshot(Integer idSnapshot, boolean deleteOnlyAutoIns) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "delete from " + schemaName + ".I_ATTIVITADYN2DATI_SNAPSHOT where IDCOMUNE=? and ID=?";
	if (deleteOnlyAutoIns) {
	    sql += " and AUTOINS=?";
	}
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idSnapshot);
	if (deleteOnlyAutoIns) {
	    query.setInteger(2, 1);
	}
	query.executeUpdate();
    }
}
