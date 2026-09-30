package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.VwIstanzesoggetticollegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegati;
import it.gruppoinit.pal.gp.core.domain.VwIstanzesoggetticollegatiId;
import it.gruppoinit.pal.gp.core.domain.helper.VwIstanzesoggetticollegatiDTO;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class VwIstanzesoggetticollegatiDAOImpl extends BaseDAOImpl<VwIstanzesoggetticollegati, VwIstanzesoggetticollegatiId>
	implements VwIstanzesoggetticollegatiDAO {

    @Override
    public Class<VwIstanzesoggetticollegati> getEntityClass() {

	return VwIstanzesoggetticollegati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwIstanzesoggetticollegatiDTO> findByRichiedenteAndSoftware(Anagrafe richiedente, Software software) {

	String sql = buildQuery();
	String repFiltro1 = "FILTRO1";
	String repFiltro2 = "FILTRO2";
	String repFiltro3 = "FILTRO3";
	String repFiltro4 = "FILTRO4";
	String filtro1 = "istanze.idcomune=? and istanze.software=? and istanzerichiedenti.codicerichiedente=? and ";
	String filtro2 = "istanze.idcomune=? and istanze.software=? and istanze.codicerichiedente=?";
	String filtro3 = "istanze.idcomune=? and istanze.software=? and istanze.codicetitolarelegale=? and ";
	String filtro4 = "istanze.idcomune=? and istanze.software=? and istanze.codiceprofessionista=? and ";
	sql = sql.replaceAll(repFiltro1, filtro1);
	sql = sql.replaceAll(repFiltro2, filtro2);
	sql = sql.replaceAll(repFiltro3, filtro3);
	sql = sql.replaceAll(repFiltro4, filtro4);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("tipologia", Hibernate.STRING);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("codicerichiedente", Hibernate.INTEGER);
	q.addScalar("codiceinvitato", Hibernate.INTEGER);
	q.addScalar("codicetiposoggetto", Hibernate.INTEGER);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codiceanagrafecoll", Hibernate.INTEGER);
	q.addScalar("descrsoggetto", Hibernate.STRING);
	q.addScalar("codiceprocuratore", Hibernate.INTEGER);
	q.addScalar("fk_idi_attivita", Hibernate.INTEGER);
	q.addScalar("software", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, software.getCodice());
	q.setInteger(2, richiedente.getId().getCodice());
	q.setString(3, ORMHelper.getIdcomune());
	q.setString(4, software.getCodice());
	q.setInteger(5, richiedente.getId().getCodice());
	q.setString(6, ORMHelper.getIdcomune());
	q.setString(7, software.getCodice());
	q.setInteger(8, richiedente.getId().getCodice());
	q.setString(9, ORMHelper.getIdcomune());
	q.setString(10, software.getCodice());
	q.setInteger(11, richiedente.getId().getCodice());
	q.setResultTransformer(Transformers.aliasToBean(VwIstanzesoggetticollegatiDTO.class));
	List<VwIstanzesoggetticollegatiDTO> rs = q.list();
	return rs;
    }

    private String buildQuery() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	Dialect d = sessimpl.getDialect();
	String hibernateDialect = d.toString();
	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	String nullFX = "NVL";
	if (dialetto.equals(DialettoEnum.MYSQL)) {
	    nullFX = "IFNULL";
	}
	if (dialetto.equals(DialettoEnum.SQLSERVER)) {
	    nullFX = "ISNULL";
	}
	if (dialetto.equals(DialettoEnum.POSTGRES)) {
	    nullFX = "COALESCE";
	}
	String sql = "select 'S' as tipologia,istanzerichiedenti.codiceistanza as codiceistanza, istanzerichiedenti.codicerichiedente as codicerichiedente," +
		"istanzerichiedenti.codiceinvitato as codiceinvitato, istanzerichiedenti.codicetiposoggetto as codicetiposoggetto,istanzerichiedenti.idcomune as idcomune," +
		"istanzerichiedenti.codiceanagrafecoll as codiceanagrafecoll," +
		nullFX +
		"(istanzerichiedenti.descrsoggetto, tipisoggetto.tiposoggetto) as descrsoggetto," +
		"istanzerichiedenti.codiceprocuratore as codiceprocuratore,istanze.fk_idi_attivita as fk_idi_attivita, istanze.software as software from  " +
		schemaName +
		".istanzerichiedenti , " +
		schemaName +
		".istanze , " +
		schemaName +
		".tipisoggetto where FILTRO1 tipisoggetto.idcomune = istanzerichiedenti.idcomune " +
		" and tipisoggetto.codicetiposoggetto = istanzerichiedenti.codicetiposoggetto" +
		" and istanzerichiedenti.idcomune = istanze.idcomune and istanzerichiedenti.codiceistanza = istanze.codiceistanza  " +
		" union select 'R' as tipologia,istanze.codiceistanza as codiceistanza,istanze.codicerichiedente as codicerichiedente, " +
		"null as codiceinvitato," +
		"null as codicetiposoggetto," +
		" istanze.idcomune as idcomune,null as codiceanagrafecoll," +
		nullFX +
		"(tipisoggetto.tiposoggetto , 'RICHIEDENTE' ) as descrsoggetto " +
		",null as codiceprocuratore,fk_idi_attivita  as fk_idi_attivita, istanze.software as software from " +
		schemaName +
		".istanze left outer join " +
		schemaName +
		".tipisoggetto on " +
		"istanze.idcomune=tipisoggetto.idcomune" +
		"  and   istanze.fkcodicesoggetto=tipisoggetto.codicetiposoggetto  " +
		" where FILTRO2 " +
		" union select 'A' as tipologia,istanze.codiceistanza as codiceistanza,istanze.codicetitolarelegale as codicerichiedente, " +
		"null as codiceinvitato  " +
		",null as codicetiposoggetto , istanze.idcomune as idcomune,null as codiceanagrafecoll,'RAGIONE SOCIALE' as descrsoggetto,null " +
		"as codiceprocuratore,fk_idi_attivita  as fk_idi_attivita,istanze.software as software from  " +
		schemaName +
		".istanze where FILTRO3 not codicetitolarelegale  is  null" +
		" union select 'T' as tipologia,istanze.codiceistanza as codiceistanza,istanze.codiceprofessionista as codicerichiedente," +
		" null as codiceinvitato," +
		"null as codicetiposoggetto,istanze.idcomune as idcomune,null as codiceanagrafecoll,'PROFESSIONISTA' as descrsoggetto," +
		"null as codiceprocuratore,fk_idi_attivita as fk_idi_attivita, istanze.software as software from  " +
		schemaName +
		".istanze where FILTRO4 not codiceprofessionista  is  null";
	return sql;
    }
    /**
     * SessionFactoryImplementor sessimpl = (SessionFactoryImplementor)
     * getSessionFactory().getCurrentSession().getSessionFactory(); String schemaName =
     * StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), ""); String sql = "SELECT
     * max(iad2d_s.indice) as max FROM " + schemaName + ".i_attivitadyn2dati_snapshot iad2d_s join " + schemaName +
     * ".dyn2_modellid d2md on " + "iad2d_s.idcomune=d2md.idcomune and iad2d_s.fk_d2c_id = d2md.fk_d2c_id join " +
     * schemaName + ".i_attivitadyn2mod_t_snapshot iad2mt_s " + "on iad2mt_s.idcomune=d2md.idcomune and
     * iad2mt_s.fk_d2mt_id = d2md.fk_d2mt_id WHERE " + " iad2d_s.idcomune =? AND iad2d_s.fk_ia_id =? and
     * iad2mt_s.fk_d2mt_id=?"; SQLQuery q = getSession().createSQLQuery(sql); q.addScalar("max", Hibernate.BIG_DECIMAL);
     * q.setString(0, ORMHelper.getIdcomune()); q.setInteger(1, codiceAttivita); q.setInteger(2, idmodello);
     * List<BigDecimal> rs = q.list(); int ris = ((BigDecimal) rs.get(0)).intValue(); return ris;
     */
}
