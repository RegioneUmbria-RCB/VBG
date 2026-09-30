package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AlberoprocProtocolloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

/**
 * 
 * @author
 */
@Repository
public class AlberoprocProtocolloDAOImpl extends BaseDAOImpl<AlberoprocProtocollo, PkId> implements AlberoprocProtocolloDAO {

    @Override
    public Class<AlberoprocProtocollo> getEntityClass() {

	return AlberoprocProtocollo.class;
    }

    @Override
    public List<AlberoprocProtocollo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object findProprietaByAlberoprocId(Integer codiceAlberoproc, String propertyName, String codiceComune) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("alberoproc", "_alberoproc", DetachedCriteria.INNER_JOIN);
	det.createAlias("comuni", "_comuni", DetachedCriteria.LEFT_JOIN);
	if (StringUtils.isNotBlank(codiceComune)) {
	    Criterion isNull = Restrictions.isNull("_comuni.codicecomune");
	    Criterion codComuneCrit = Restrictions.eq("_comuni.codicecomune", codiceComune);
	    LogicalExpression orComuni = Restrictions.or(codComuneCrit, isNull);
	    det.add(orComuni);
	} else {
	    det.add(Restrictions.isNull("_comuni.codicecomune"));
	}
	det.add(Restrictions.eq("_alberoproc.id.codice", codiceAlberoproc));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.groupProperty(propertyName));
	projectionList.add(Projections.groupProperty("_comuni.comune"));
	det.setProjection(projectionList);
	det.addOrder(OrderBySqlFormula.desc("_comuni.comune", FunctionsEnum.NVL_FUNCTION, "'AAAAAAAAAAAA'"));
	List<Object> list = getHibernateTemplate().findByCriteria(det);
	for (Object o : list) {
	    final Object[] fields = (Object[]) o;
	    if (fields[0] != null) {
		if (fields[0] instanceof String) {
		    // BOCCI AGGIUNTO QUESTO CONTROLLO PERCHE' IN MYSQL LE STRINGHE VUOTE VENGONO SALVATE COME '' E NON
		    // COME NULL E LA RICERCA DEI PARAMETRI FALLISCE PERCHE' RITORNA IL PRIMO VALORE NON NULLO
		    // CON LA MODIFICA VIENE PRESO IL PRIMO VALORE NON NULLO E NON VUOTO
		    if (StringUtils.isNotBlank((String) fields[0])) {
			return fields[0];
		    }
		} else {
		    return fields[0];
		}
	    }
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, AlberoprocProtocollo> findConfigurazioniHelper(Integer codiceAlberoproc) {

	DynaProperty[] properties = { new DynaProperty("scCodice", String.class) };
	DynaClass userDynaClass = new BasicDynaClass("TipiprocedureDC", null, properties);
	DynaBean procedura = findDynaBeanById(ORMHelper.getIdcomune(), codiceAlberoproc, userDynaClass, Alberoproc.class);
	String scCodice = (String) procedura.get("scCodice");
	Map<String, AlberoprocProtocollo> result = new HashMap<String, AlberoprocProtocollo>();
	if (StringUtils.isNotBlank(scCodice)) {
	    SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	    Dialect dialetto = sessimpl.getDialect();
	    DialettoEnum d = DialettoEnum.fromHibernateDialect(dialetto.toString());
	    String[] codiciAlbero = new String[(scCodice.length() / 2)]; //scCodice.split("(?<=\\G.{2})");
	    int c = 0;
	    for (int i = 2; i <= scCodice.length(); i += 2) {
		String string = scCodice.substring(0, i);
		codiciAlbero[c] = string;
		c++;
	    }
	    String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	    String sql = getQueryForConfigurazioni(schemaName, codiciAlbero, d);
	    SQLQuery q = getSession().createSQLQuery(sql);
	    q.setString(0, ORMHelper.getIdcomune());
	    q.setString(1, ORMHelper.getSoftware());
	    int pos = 2;
	    for (String scC : codiciAlbero) {
		q.setString(pos, scC);
		pos++;
	    }
	    q.addScalar("sc_codice", Hibernate.STRING);
	    q.addScalar("codicecomune", Hibernate.STRING);
	    q.addScalar("comune", Hibernate.STRING);
	    q.addScalar("sc_protautomatica", Hibernate.INTEGER);
	    q.addScalar("sc_protclassifica", Hibernate.STRING);
	    q.addScalar("sc_protcodtesto", Hibernate.INTEGER);
	    q.addScalar("oggetto_protocollazione", Hibernate.STRING);
	    q.addScalar("sc_prottipodocumento", Hibernate.STRING);
	    q.addScalar("sc_fascautomatica", Hibernate.INTEGER);
	    q.addScalar("sc_fascclassifica", Hibernate.STRING);
	    q.addScalar("sc_fasccodtesto", Hibernate.INTEGER);
	    q.addScalar("oggetto_fascicolazione", Hibernate.STRING);
	    q.addScalar("codiceamministrazione", Hibernate.INTEGER);
	    q.addScalar("amministrazione", Hibernate.STRING);
	    List<IstanzeListHelper> list = (List<IstanzeListHelper>) q.list();
	    for (Object object : list) {
		if (object instanceof Object[]) {
		    Object[] rs = (Object[]) object;
		    String comune = (String) rs[2];
		    Integer sc_protautomatica = (Integer) rs[3];
		    String sc_protclassifica = (String) rs[4];
		    Integer sc_protcodtesto = (Integer) rs[5];
		    String oggetto_protocollazione = (String) rs[6];
		    String sc_prottipodocumento = (String) rs[7];
		    Integer sc_fascautomatica = (Integer) rs[8];
		    String sc_fascclassifica = (String) rs[9];
		    Integer sc_fasccodtesto = (Integer) rs[10];
		    String oggetto_fascicolazione = (String) rs[11];
		    Integer codiceamministrazione = (Integer) rs[12];
		    String amministrazione = (String) rs[13];
		    AlberoprocProtocollo ap = result.get(comune);
		    if (ap == null) {
			ap = new AlberoprocProtocollo();
		    }
		    if (sc_protautomatica != null) {
			ap.setScProtautomatica(sc_protautomatica);
		    }
		    if (StringUtils.isNotBlank(sc_protclassifica)) {
			ap.setScProtclassifica(sc_protclassifica);
		    }
		    if (sc_protcodtesto != null) {
			Mailtipo testoProtocollo = new Mailtipo();
			testoProtocollo.setId(new PkId(sc_protcodtesto));
			testoProtocollo.setDescrizione(oggetto_protocollazione);
			ap.setTestoProtocollo(testoProtocollo);
		    } else {
			if (ap.getTestoProtocollo() == null) {
			    ap.setTestoProtocollo(new Mailtipo());
			}
		    }
		    if (StringUtils.isNotBlank(sc_prottipodocumento)) {
			ap.setScProttipodocumento(sc_prottipodocumento);
		    }
		    if (sc_fascautomatica != null) {
			ap.setScFascautomatica(sc_fascautomatica);
		    }
		    if (StringUtils.isNotBlank(sc_fascclassifica)) {
			ap.setScFascclassifica(sc_fascclassifica);
		    }
		    if (sc_fasccodtesto != null) {
			Mailtipo testoFascicolo = new Mailtipo();
			testoFascicolo.setId(new PkId(sc_fasccodtesto));
			testoFascicolo.setDescrizione(oggetto_fascicolazione);
			ap.setTestoFascicolo(testoFascicolo);
		    } else {
			if (ap.getTestoFascicolo() == null) {
			    ap.setTestoFascicolo(new Mailtipo());
			}
		    }
		    if (codiceamministrazione != null) {
			Amministrazioni amministrazioni = new Amministrazioni();
			amministrazioni.setId(new PkId(codiceamministrazione));
			amministrazioni.setAmministrazione(amministrazione);
			ap.setAmministrazioni(amministrazioni);
		    } else {
			if (ap.getAmministrazioni() == null) {
			    ap.setAmministrazioni(new Amministrazioni());
			}
		    }
		    result.put(comune, ap);
		}
	    }
	}
	return result;
    }

    private String getQueryForConfigurazioni(String schemaName, String[] listaCodici, DialettoEnum dialetto) {

	StringBuffer sql = new StringBuffer("select ap.sc_descrizione intervento, ");
	sql.append("  ap.sc_codice sc_codice, ");
	sql.append("  ca.codicecomune codicecomune, ");
	sql.append("  casc.comune comune, ");
	// sql.append("  nvl(com.comune,'tutti') , ");
	sql.append("  app.sc_protautomatica sc_protautomatica, ");
	sql.append("  app.sc_protclassifica sc_protclassifica, ");
	sql.append("  app.sc_protcodtesto sc_protcodtesto, ");
	sql.append("  mtprot.descrizione as oggetto_protocollazione, ");
	sql.append("  app.sc_prottipodocumento sc_prottipodocumento, ");
	sql.append("  app.sc_fascautomatica sc_fascautomatica, ");
	sql.append("  app.sc_fascclassifica sc_fascclassifica, ");
	sql.append("  app.sc_fasccodtesto sc_fasccodtesto, ");
	sql.append("  mtfasc.descrizione as oggetto_fascicolazione, ");
	sql.append("  app.codiceamministrazione codiceamministrazione, ");
	sql.append("  amm.amministrazione amministrazione ");
	sql.append("from SCHEMA_OWNER.alberoproc ap ");
	sql.append("left join SCHEMA_OWNER.alberoproc_protocollo app ");
	sql.append("on app.idcomune =ap.idcomune ");
	sql.append("and app.fkscid  =ap.sc_id ");
	sql.append("left join SCHEMA_OWNER.amministrazioni amm ");
	sql.append("on amm.idcomune              =app.idcomune ");
	sql.append("and amm.codiceamministrazione=app.codiceamministrazione ");
	sql.append("left join SCHEMA_OWNER.comuniassociati ca ");
	sql.append("on (( ca.idcomune     =app.idcomune ");
	sql.append("and ca.codicecomune   =app.codicecomune ) ");
	sql.append("or ( ca.idcomune      =ap.idcomune ");
	sql.append("and app.codicecomune is null )) ");
	sql.append("left join SCHEMA_OWNER.comuni com ");
	sql.append("on com.codicecomune=app.codicecomune ");
	sql.append("left join SCHEMA_OWNER.comuni casc ");
	sql.append("on casc.codicecomune=ca.codicecomune ");
	sql.append("left join SCHEMA_OWNER.mailtipo mtprot ");
	sql.append("on mtprot.idcomune   =app.idcomune ");
	sql.append("and mtprot.codicemail=app.sc_protcodtesto ");
	sql.append("left join SCHEMA_OWNER.mailtipo mtfasc ");
	sql.append("on mtfasc.idcomune   =app.idcomune ");
	sql.append("and mtfasc.codicemail=app.sc_protcodtesto ");
	sql.append("where ap.idcomune    =? ");
	sql.append("and ap.software      =? ");
	sql.append("and ap.sc_codice    in (LISTACODICI) ");
	sql.append("order by sc_codice asc, ");
	sql.append(BaseQueryHelper.applySimpleNVLFunction("com.comune", "'AAAAAAAAAAA'", dialetto));
	sql.append("  asc, casc.comune  asc");
	String[] qMarks = new String[listaCodici.length];
	Arrays.fill(qMarks, "?");
	String qMark = StringUtils.join(qMarks, ",");
	return sql.toString().replaceAll("SCHEMA_OWNER", schemaName).replaceAll("LISTACODICI", qMark);
    }
}
