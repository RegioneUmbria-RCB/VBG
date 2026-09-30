package it.gruppoinit.pal.gp.core.features.segnaposto;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;

@SuppressWarnings("rawtypes")
@Repository
public class SegnapostoDAOImpl extends BaseDAOImpl implements ISegnapostoDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public String getTemplateHTML(String segnaposto) {

	if (segnaposto == null) {
	    throw new IllegalArgumentException("Impossibile cercare il template del segnaposto senza specificare il segnaposto");
	}
	String sql = "select " + //
		     "  coalesce(segnapostipersonalizzati.template, segnaposti.templatebase) as template " + //
		     "from " + //
		     "  segnaposti " + //
		     "    left join segnapostipersonalizzati on " + //
		     "      segnaposti.segnaposto = segnapostipersonalizzati.segnaposto and " + //
		     "      segnapostipersonalizzati.idcomune = ? and " + //
		     "      segnapostipersonalizzati.software IN (?,?) and " + //
		     "      segnapostipersonalizzati.template is not null " + //
		     "    left join software on " + //
		     "      segnapostipersonalizzati.software = software.codice " + //
		     "where " + //
		     "  segnaposti.segnaposto = ? " + //
		     "order by " + //
		     "  software.moduloopzionale desc";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, ORMHelper.getSoftware());
	query.setString(2, "TT");
	query.setString(3, segnaposto);
	query.addScalar("template", Hibernate.STRING);
	if (query.list().isEmpty()) {
	    return "";
	}
	return query.list().get(0).toString();
    }

    @Override
    public String getTemplateRTF(String segnaposto) {

	if (segnaposto == null) {
	    throw new IllegalArgumentException("Impossibile cercare il template del segnaposto senza specificare il segnaposto");
	}
	String sql = "select " + //
		     "  coalesce(segnapostipersonalizzati.templatertf, segnaposti.templatebasertf) as template " + //
		     "from " + //
		     "  segnaposti " + //
		     "    left join segnapostipersonalizzati on " + //
		     "      segnaposti.segnaposto = segnapostipersonalizzati.segnaposto and " + //
		     "      segnapostipersonalizzati.idcomune = ? and " + //
		     "      segnapostipersonalizzati.software IN (?,?) and " + //
		     "      segnapostipersonalizzati.templatertf is not null " + //
		     "    left join software on " + //
		     "      segnapostipersonalizzati.software = software.codice " + //
		     "where " + //
		     "  segnaposti.segnaposto = ? " + //
		     "order by " + //
		     "  software.moduloopzionale desc";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, ORMHelper.getSoftware());
	query.setString(2, "TT");
	query.setString(3, segnaposto);
	query.addScalar("template", Hibernate.STRING);
	if (query.list().isEmpty()) {
	    return "";
	}
	return query.list().get(0).toString();
    }
}
