package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.GruppiEndoprocedimentiDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

@Repository
public class GruppiEndoprocedimentiDDAOImpl extends BaseDAOImpl<GruppiEndoprocedimentiD, PkId> implements GruppiEndoprocedimentiDDAO {

    @Override
    public Class<GruppiEndoprocedimentiD> getEntityClass() {

	return GruppiEndoprocedimentiD.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Integer> findByEndoprocedimenti(Set<Integer> codiciEndoprocedimenti, String software) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	DialettoEnum d = DialettoEnum.fromHibernateDialect(dialetto.toString());
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = getQueryForTutteConfigurazioni(schemaName, d, codiciEndoprocedimenti);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	int pos = 2;
	for (Integer scC : codiciEndoprocedimenti) {
	    q.setInteger(pos, scC);
	    pos++;
	}
	q.addScalar("idgruppo", Hibernate.INTEGER);
	List<Integer> list = (List<Integer>) q.list();
	Set<Integer> result = new HashSet<Integer>();
	for (Integer codiceGruppo : list) {
	    result.add(codiceGruppo);
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChiaveValoreBean<Integer, String>> findByEndoprocedimentiConWarning(Set<Integer> codiciEndoprocedimenti, String software) {

	List<ChiaveValoreBean<Integer, String>> result = new ArrayList<ChiaveValoreBean<Integer, String>>();
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	DialettoEnum d = DialettoEnum.fromHibernateDialect(dialetto.toString());
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = getQueryForGruppiConWarning(schemaName, d, codiciEndoprocedimenti);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	q.setInteger(2, Integer.valueOf(0));
	int pos = 3;
	for (Integer scC : codiciEndoprocedimenti) {
	    q.setInteger(pos, scC);
	    pos++;
	}
	q.addScalar("idgruppo", Hibernate.INTEGER);
	q.addScalar("tipomovimento", Hibernate.STRING);
	List<Object[]> list = (List<Object[]>) q.list();
	for (Object[] objects : list) {
	    Integer codiceGruppo = (Integer) objects[0];
	    String tipomovimento = (String) objects[1];
	    ChiaveValoreBean<Integer, String> cvb = new ChiaveValoreBean<Integer, String>();
	    cvb.setChiave(codiceGruppo);
	    cvb.setValore(tipomovimento);
	    result.add(cvb);
	}
	return result;
    }

    private String getQueryForTutteConfigurazioni(String schemaName, DialettoEnum dialetto, Set<Integer> codiciEndoprocedimenti) {

	StringBuffer sql = new StringBuffer(
		"select gruppi_endoprocedimenti_t.id as idgruppo from  SCHEMA_OWNER.gruppi_endoprocedimenti_t inner join ");
	sql.append("  SCHEMA_OWNER.gruppi_endoprocedimenti_d on ");
	sql.append("  gruppi_endoprocedimenti_t.idcomune = gruppi_endoprocedimenti_d.idcomune and ");
	sql.append("  gruppi_endoprocedimenti_t.id = gruppi_endoprocedimenti_d.fk_get_id ");
	sql.append("  where gruppi_endoprocedimenti_t.idcomune=? and gruppi_endoprocedimenti_t.software=? ");
	sql.append("  and gruppi_endoprocedimenti_d.codiceinventario in (LISTACODICI)");
	sql.append("  group by gruppi_endoprocedimenti_t.id ");
	String[] qMarks = new String[codiciEndoprocedimenti.size()];
	Arrays.fill(qMarks, "?");
	String qMark = StringUtils.join(qMarks, ",");
	return sql.toString().replaceAll("SCHEMA_OWNER", schemaName).replaceAll("LISTACODICI", qMark);
    }

    private String getQueryForGruppiConWarning(String schemaName, DialettoEnum dialetto, Set<Integer> codiciEndoprocedimenti) {

	StringBuffer sql = new StringBuffer(
		"  select gruppi_endoprocedimenti_t.id as idgruppo, gruppi_endoprocedimenti_t.tipomovimento as tipomovimento ");
	sql.append("  from gruppi_endoprocedimenti_t inner join gruppi_endoprocedimenti_d ");
	sql.append("  on gruppi_endoprocedimenti_t.idcomune = gruppi_endoprocedimenti_d.idcomune and ");
	sql.append("  gruppi_endoprocedimenti_t.id = gruppi_endoprocedimenti_d.fk_get_id ");
	sql.append(
		"  where gruppi_endoprocedimenti_t.idcomune=? and gruppi_endoprocedimenti_t.software=? and gruppi_endoprocedimenti_t.num_endo_warning>?  and ");
	sql.append("  gruppi_endoprocedimenti_d.codiceinventario in (LISTACODICI)  ");
	sql.append("  group by gruppi_endoprocedimenti_t.id,gruppi_endoprocedimenti_t.num_endo_warning, gruppi_endoprocedimenti_t.tipomovimento ");
	sql.append(
		"  having count(gruppi_endoprocedimenti_d.id) >= gruppi_endoprocedimenti_t.num_endo_warning order by count(gruppi_endoprocedimenti_d.id) desc");
	String[] qMarks = new String[codiciEndoprocedimenti.size()];
	Arrays.fill(qMarks, "?");
	String qMark = StringUtils.join(qMarks, ",");
	return sql.toString().replaceAll("SCHEMA_OWNER", schemaName).replaceAll("LISTACODICI", qMark);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Integer> findEndoProcedimentiNonPresentiInGruppi(Set<Integer> codiciEndoprocedimenti) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	DialettoEnum d = DialettoEnum.fromHibernateDialect(dialetto.toString());
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = getQueryForEndoNonConfigurati(schemaName, d, codiciEndoprocedimenti);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	int pos = 1;
	for (Integer scC : codiciEndoprocedimenti) {
	    q.setInteger(pos, scC);
	    pos++;
	}
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IdentificativoDescrizioneBean.class));
	List<IdentificativoDescrizioneBean> list = q.list();
	Set<Integer> result = new HashSet<Integer>();
	for (IdentificativoDescrizioneBean idb : list) {
	    if ("NON_PRESENTE".equalsIgnoreCase(idb.getDescrizione()) || !ORMHelper.getSoftware().equals(idb.getDescrizione())) {
		result.add(idb.getId());
	    }
	}
	return result;
    }

    private String getQueryForEndoNonConfigurati(String schemaName, DialettoEnum d, Set<Integer> codiciEndoprocedimenti) {

	String sql = "select inventarioprocedimenti.codiceinventario as id,coalesce(gruppi_endoprocedimenti_t.software,'NON_PRESENTE',gruppi_endoprocedimenti_t.software) as descrizione " + //
		" from SCHEMA_OWNER.inventarioprocedimenti " + //
		" left join SCHEMA_OWNER.gruppi_endoprocedimenti_d on gruppi_endoprocedimenti_d.idcomune = inventarioprocedimenti.idcomune and gruppi_endoprocedimenti_d.codiceinventario = inventarioprocedimenti.codiceinventario " + //
		" left join gruppi_endoprocedimenti_t on gruppi_endoprocedimenti_t.idcomune = gruppi_endoprocedimenti_d.idcomune and gruppi_endoprocedimenti_t.id = gruppi_endoprocedimenti_d.fk_get_id " + //
		" where inventarioprocedimenti.idcomune=? and inventarioprocedimenti.codiceinventario in (LISTACODICI)";
	String[] qMarks = new String[codiciEndoprocedimenti.size()];
	Arrays.fill(qMarks, "?");
	String qMark = StringUtils.join(qMarks, ",");
	return sql.replace("SCHEMA_OWNER", schemaName).replace("LISTACODICI", qMark);
    }
}
