package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AlberoprocGruppiSmistDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocGruppiSmist;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GruppiSmistHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

@Repository
public class AlberoprocGruppiSmistDAOImpl extends BaseDAOImpl<AlberoprocGruppiSmist, PkId> implements AlberoprocGruppiSmistDAO {

    @Override
    public Class<AlberoprocGruppiSmist> getEntityClass() {

	return AlberoprocGruppiSmist.class;
    }

    private String getQueryForTutteConfigurazioni(String schemaName, DialettoEnum dialetto) {

	StringBuffer sql = new StringBuffer("select t.id as id,t.gid as gruppo from  (");
	sql.append("  select id, fk_get_id_primo as gid from");
	sql.append("  SCHEMA_OWNER.alberoproc_gruppi_smist");
	sql.append("  where idcomune=?  and software=?");
	sql.append("  union ");
	sql.append("  select id, fk_get_id_secondo as gid from");
	sql.append("  SCHEMA_OWNER.alberoproc_gruppi_smist");
	sql.append("  where idcomune=?  and software=? ");
	sql.append("  union ");
	sql.append("  select id, fk_get_id_terzo as gid from");
	sql.append("  SCHEMA_OWNER.alberoproc_gruppi_smist");
	sql.append("  where idcomune=?  and software=?");
	sql.append("  ) t");
	sql.append("  where not gid is null");
	sql.append("  order by id asc");
	return sql.toString().replaceAll("SCHEMA_OWNER", schemaName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GruppiSmistHelper> getConfigurazioni() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	DialettoEnum d = DialettoEnum.fromHibernateDialect(dialetto.toString());
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = getQueryForTutteConfigurazioni(schemaName, d);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	q.setString(2, ORMHelper.getIdcomune());
	q.setString(3, ORMHelper.getSoftware());
	q.setString(4, ORMHelper.getIdcomune());
	q.setString(5, ORMHelper.getSoftware());
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("gruppo", Hibernate.INTEGER);
	//	q.addScalar("idcomune", Hibernate.STRING);
	//	q.addScalar("software", Hibernate.STRING);
	List<Integer[]> list = (List<Integer[]>) q.list();
	List<GruppiSmistHelper> result = new ArrayList<GruppiSmistHelper>();
	for (Object[] objects : list) {
	    Integer codiceConf = (Integer) objects[0];
	    Integer codiceGruppo = (Integer) objects[1];
	    GruppiSmistHelper e = new GruppiSmistHelper();
	    e.setId(codiceConf);
	    e.setGruppo(codiceGruppo);
	    result.add(e);
	}
	return result;
    }
}
