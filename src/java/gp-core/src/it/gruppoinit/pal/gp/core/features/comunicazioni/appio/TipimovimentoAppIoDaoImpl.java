package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.TipimovAppioServiziEndo;
import it.gruppoinit.pal.gp.core.domain.TipimovAppioServiziEndoId;
import it.gruppoinit.pal.gp.core.domain.TipimovAppioServiziInt;
import it.gruppoinit.pal.gp.core.domain.TipimovAppioServiziIntId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServizi;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServiziId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioTipimovimentoEndoBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.AppioTipimovimentoInterventoBean;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class TipimovimentoAppIoDaoImpl extends BaseDAOImpl<TipimovimentoAppIoServizi, TipimovimentoAppIoServiziId> implements ItipimovimentoAppIoDao {

    private static final String TIPOMOV = "tipomov";
    private static final String IDSERVIZIO = "idservizio";
    private static final String IDCOMUNE = "idcomune";
    private static final Logger log = LoggerFactory.getLogger(TipimovimentoAppIoDaoImpl.class);

    @Override
    public Class<TipimovimentoAppIoServizi> getEntityClass() {

	return TipimovimentoAppIoServizi.class;
    }

    @Override
    public List<String> isConfiguratoTipomov(Integer codiceMovimento) {

	Movimenti m = getById(Movimenti.class, codiceMovimento);
	String tipoMovimento = m.getTipomovimento().getId().getTipomovimento();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipoMovimento, String.class));
	ft.addRestriction(fr);
	List<TipimovimentoAppIoServizi> list = findByFilterTable(ft);
	if (log.isDebugEnabled()) {
	    log.debug("isConfiguratoTipomov cmov={},tipomov={}, servizi.size{}", new Object[] { codiceMovimento, tipoMovimento, list.size() });
	}
	List<String> identServizi = new ArrayList<String>();
	Integer codiceInterventoIstanza = m.getIstanza().getAlberoproc().getId().getCodice();
	String softwareIstanza = m.getIstanza().getSoftware().getCodice();
	log.debug("isConfiguratoTipomov tipomov={}, codiceintervento={}, software={}",
		new Object[] { tipoMovimento, codiceInterventoIstanza, softwareIstanza });
	if (list.isEmpty()) {
	    return identServizi;
	}
	for (TipimovimentoAppIoServizi ta : list) {
	    String idServizio = ta.getId().getIdentificativoServizio();
	    log.debug("isConfiguratoTipomov tipomov={}, servizio ={}", new Object[] { tipoMovimento, idServizio });
	    if (checkServizioAttivoPerIntervento(codiceInterventoIstanza, idServizio, tipoMovimento, softwareIstanza)) {
		identServizi.add(ta.getId().getIdentificativoServizio());
	    }
	}
	return identServizi;
    }

    @SuppressWarnings("unchecked")
    private boolean checkServizioAttivoPerIntervento(Integer codiceIntervento, String idServizio, String tipomovimento, String softwareIstanza) {

	if (findConfigurazioniInterventoAndSoftware(idServizio, tipomovimento, softwareIstanza).isEmpty()) {
	    log.debug(
		    "findConfigurazioniInterventoAndSoftware esco con true in quanto è vuoto per i parametri idservizio={}, tipomov={}, software={}",
		    new Object[] { idServizio, tipomovimento, softwareIstanza });
	    return true;
	}
	Dialect d = ((SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory()).getDialect();
	String hibernateDialect = d.toString();
	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	//..
	StringBuilder sq = new StringBuilder();
	sq.append("select  "); //
	sq.append(" gerarchia.sc_id as codiceintervento "); //
	//	sq.append(", gerarchia.sc_codice "); //
	//	sq.append(", gerarchia.sc_descrizione "); //
	sq.append("  from alberoproc  "); //
	sq.append(" left join alberoproc gerarchia on  "); //
	sq.append(" gerarchia.idcomune=alberoproc.idcomune and "); //
	sq.append(" gerarchia.software=alberoproc.software and "); //
	if (DialettoEnum.MYSQL.equals(dialetto)) {
	    sq.append(" alberoproc.sc_codice like concat_ws('',gerarchia.sc_codice, '%')  "); //
	} else if (DialettoEnum.ORACLE.equals(dialetto)) {
	    sq.append(" alberoproc.sc_codice like gerarchia.sc_codice || '%'  "); //
	} else {
	    throw new InvalidConfigurationException("findByIdServizioMovimentoAndIntervento# Dialetto Hibernate non implementato " + dialetto);
	}
	sq.append(" where  "); //
	sq.append(" alberoproc.idcomune=:idcomune and  "); //
	sq.append(" alberoproc.sc_id=:codiceintervento "); //
	sq.append(" order by gerarchia.sc_codice desc"); //
	SQLQuery q = getSession().createSQLQuery(sq.toString());
	q.addScalar("codiceintervento", Hibernate.INTEGER);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("codiceintervento", codiceIntervento);
	List<Integer> list = q.list();
	if (list.isEmpty()) {
	    if (log.isDebugEnabled()) {
		log.debug(
			"findConfigurazioniInterventoAndSoftware esco con false in quanto è vuoto per i parametri idservizio={}, tipomov={}, codiceintervento={}, query= {}",
			new Object[] { idServizio, tipomovimento, codiceIntervento, sq.toString() });
	    }
	    return false;
	}
	String sql = "select fk_alberoproc_scid as codiceintervento from tipimov_appioservizi_int where idcomune=:idcomune and tipomovimento=:tipomovimento and identificativo_servizio=:identificativo_servizio and fk_alberoproc_scid=:alberoproc";
	q = getSession().createSQLQuery(sql);
	q.addScalar("codiceintervento", Hibernate.INTEGER);
	for (Integer codiceInterventoGerarchico : list) {
	    q.setString("idcomune", ORMHelper.getIdcomune());
	    q.setString("tipomovimento", tipomovimento);
	    q.setString("identificativo_servizio", idServizio);
	    q.setInteger("alberoproc", codiceInterventoGerarchico);
	    List<Integer> ret = q.list();
	    if (!ret.isEmpty()) {
		if (log.isDebugEnabled()) {
		    log.debug(
			    "findConfigurazioniInterventoAndSoftware esco con true in quanto trovate la configurazione per i parametri idservizio={}, tipomov={}, codiceInterventoGerarchico={}, query= {}",
			    new Object[] { idServizio, tipomovimento, codiceInterventoGerarchico, sq.toString() });
		}
		return true;
	    }
	}
	return false;
    }

    @Override
    public List<TipimovimentoAppIoServizi> findByIdservizio(String idservizio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.identificativoServizio", idservizio, String.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public void deleteTipimovAppioServiziEndo(String idservizio, String tipomov) {

	String hql = "delete from TipimovAppioServiziEndo tme where " + // 
		"tme.id.idcomune=:idcomune and tme.id.identificativoServizio=:idservizio and tme.id.tipomovimento=:tipomov";
	Query query = getSession().createQuery(hql);
	query.setString(IDCOMUNE, ORMHelper.getIdcomune());
	query.setString(IDSERVIZIO, idservizio);
	query.setString(TIPOMOV, tipomov);
	query.executeUpdate();
    }

    @Override
    public void deleteTipimovAppioServiziEndo(String idservizio, String tipomov, Integer codiceinventario) {

	String hql = "delete from TipimovAppioServiziEndo tme where " + // 
		"tme.id.idcomune=:idcomune and tme.id.identificativoServizio=:idservizio and tme.id.tipomovimento=:tipomov and tme.id.codiceinventario=:codiceinventario";
	Query query = getSession().createQuery(hql);
	query.setString(IDCOMUNE, ORMHelper.getIdcomune());
	query.setString(IDSERVIZIO, idservizio);
	query.setString(TIPOMOV, tipomov);
	query.setInteger("codiceinventario", codiceinventario);
	query.executeUpdate();
    }

    @Override
    public void deleteTipimovAppioServiziInt(String idservizio, String tipomov) {

	String hql = "delete from TipimovAppioServiziInt tme where " + // 
		"tme.id.idcomune=:idcomune and tme.id.identificativoServizio=:idservizio and tme.id.tipomovimento=:tipomov";
	Query query = getSession().createQuery(hql);
	query.setString(IDCOMUNE, ORMHelper.getIdcomune());
	query.setString(IDSERVIZIO, idservizio);
	query.setString(TIPOMOV, tipomov);
	query.executeUpdate();
    }

    @Override
    public void deleteTipimovAppioServiziInt(String idservizio, String tipomov, Integer codiceIntervento) {

	String hql = "delete from TipimovAppioServiziInt tme where " + // 
		"tme.id.idcomune=:idcomune and tme.id.identificativoServizio=:idservizio and tme.id.tipomovimento=:tipomov and tme.id.fkAlberoprocScid=:codiceIntervento";
	Query query = getSession().createQuery(hql);
	query.setString(IDCOMUNE, ORMHelper.getIdcomune());
	query.setString(IDSERVIZIO, idservizio);
	query.setString(TIPOMOV, tipomov);
	query.setInteger("codiceIntervento", codiceIntervento);
	query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AppioTipimovimentoEndoBean> findConfigurazioniEndoProcedimenti(String idservizio, String tipomovimento) {

	StringBuilder sql = new StringBuilder();
	sql.append("select  "); //
	sql.append(" identificativo_servizio as identificativoservizio "); //
	sql.append(" , tipimov_appioservizi_endo.tipomovimento as tipomovimento "); //
	sql.append(" , tipimovimento.movimento "); //
	sql.append(" , tipimov_appioservizi_endo.codiceinventario as codiceinventario "); //
	sql.append(" , inventarioprocedimenti.procedimento "); //
	sql.append(" , template_oggetto as templateoggetto "); //
	sql.append(" ,template_messaggio as templatemessaggio "); //
	sql.append("from tipimov_appioservizi_endo  "); //
	sql.append(" inner join tipimovimento on  "); //
	sql.append(" tipimovimento.idcomune=tipimov_appioservizi_endo.idcomune and "); //
	sql.append(" tipimovimento.tipomovimento=tipimov_appioservizi_endo.tipomovimento "); //
	sql.append(" inner join inventarioprocedimenti on  "); //
	sql.append(" inventarioprocedimenti.idcomune=tipimov_appioservizi_endo.idcomune and "); //
	sql.append(" inventarioprocedimenti.codiceinventario=tipimov_appioservizi_endo.codiceinventario "); //
	sql.append(" where tipimov_appioservizi_endo.idcomune = :idcomune "); //
	sql.append(" and tipimov_appioservizi_endo.tipomovimento = :tipomov "); //
	sql.append(" and tipimov_appioservizi_endo.identificativo_servizio = :idservizio "); //
	sql.append(" order by inventarioprocedimenti.procedimento"); //
	SQLQuery q = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(TipimovAppioServiziEndo.class);
	q.addScalar("identificativoServizio", Hibernate.STRING);
	q.addScalar("tipomovimento", Hibernate.STRING);
	q.addScalar("movimento", Hibernate.STRING);
	q.addScalar("codiceinventario", Hibernate.INTEGER);
	q.addScalar("procedimento", Hibernate.STRING);
	q.addScalar("templateOggetto", Hibernate.STRING);
	q.addScalar("templateMessaggio", Hibernate.STRING);
	q.setString(IDCOMUNE, ORMHelper.getIdcomune());
	q.setString(TIPOMOV, tipomovimento);
	q.setString(IDSERVIZIO, idservizio);
	q.setResultTransformer(Transformers.aliasToBean(AppioTipimovimentoEndoBean.class));
	return q.list();
    }

    @Override
    public void insertConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario, String templateOggetto,
	    String templateMessaggio) {

	TipimovAppioServiziEndo entity = new TipimovAppioServiziEndo();
	TipimovAppioServiziEndoId id = new TipimovAppioServiziEndoId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setCodiceinventario(codiceInventario);
	id.setIdentificativoServizio(idservizio);
	id.setTipomovimento(tipomovimento);
	entity.setId(id);
	entity.setTemplateOggetto(templateOggetto);
	entity.setTemplateMessaggio(templateMessaggio);
	saveEntity(entity);
    }

    @Override
    public void updateConfigurazioneEndo(String idservizio, String tipomovimento, Integer codiceInventario, String templateOggetto,
	    String templateMessaggio) {

	TipimovAppioServiziEndoId id = new TipimovAppioServiziEndoId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setCodiceinventario(codiceInventario);
	id.setIdentificativoServizio(idservizio);
	id.setTipomovimento(tipomovimento);
	TipimovAppioServiziEndo entity = getByIdCustom(TipimovAppioServiziEndo.class, id);
	entity.setTemplateOggetto(templateOggetto);
	entity.setTemplateMessaggio(templateMessaggio);
	saveEntity(entity);
    }

    @SuppressWarnings("unchecked")
    private List<AppioTipimovimentoInterventoBean> findConfigurazioniInterventoAndSoftware(String idservizio, String tipomovimento, String software) {

	StringBuilder sql = new StringBuilder();
	sql.append("select  "); //
	sql.append(" identificativo_servizio as identificativoServizio "); //
	sql.append(" , tipimov_appioservizi_int.tipomovimento as tipomovimento "); //
	sql.append(" , tipimovimento.movimento "); //
	sql.append(" , tipimov_appioservizi_int.fk_alberoproc_scid as codiceintervento "); //
	sql.append(" , alberoproc.sc_descrizione as intervento "); //
	sql.append(" , template_oggetto as templateoggetto "); //
	sql.append(" , template_messaggio as templatemessaggio "); //
	sql.append(" , software.codice as codicesoftware "); //
	sql.append(" , software.descrizione as descrizionesoftware "); //
	sql.append("from tipimov_appioservizi_int  "); //
	sql.append(" inner join tipimovimento on  "); //
	sql.append(" tipimovimento.idcomune=tipimov_appioservizi_int.idcomune and "); //
	sql.append(" tipimovimento.tipomovimento=tipimov_appioservizi_int.tipomovimento "); //
	sql.append(" inner join alberoproc on  "); //
	sql.append(" alberoproc.idcomune=tipimov_appioservizi_int.idcomune and "); //
	sql.append(" alberoproc.sc_id=tipimov_appioservizi_int.fk_alberoproc_scid "); //
	// 
	sql.append(" inner join software on  "); //
	sql.append(" software.codice = alberoproc.software "); //
	//
	sql.append(" where tipimov_appioservizi_int.idcomune = :idcomune "); //
	sql.append(" and tipimov_appioservizi_int.tipomovimento = :tipomov "); //
	sql.append(" and tipimov_appioservizi_int.identificativo_servizio = :idservizio "); //
	if (StringUtils.isNotBlank(software)) {
	    sql.append(" and alberoproc.software = :software "); //
	}
	sql.append(" order by software.ordine, software.descrizione, alberoproc.sc_descrizione"); //
	SQLQuery q = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(TipimovAppioServiziEndo.class);
	q.addScalar("identificativoServizio", Hibernate.STRING);
	q.addScalar("tipomovimento", Hibernate.STRING);
	q.addScalar("movimento", Hibernate.STRING);
	q.addScalar("codiceintervento", Hibernate.INTEGER);
	q.addScalar("intervento", Hibernate.STRING);
	q.addScalar("templateOggetto", Hibernate.STRING);
	q.addScalar("templateMessaggio", Hibernate.STRING);
	q.addScalar("codicesoftware", Hibernate.STRING);
	q.addScalar("descrizionesoftware", Hibernate.STRING);
	q.setString(IDCOMUNE, ORMHelper.getIdcomune());
	q.setString(TIPOMOV, tipomovimento);
	q.setString(IDSERVIZIO, idservizio);
	if (StringUtils.isNotBlank(software)) {
	    q.setString("software", software);
	}
	q.setResultTransformer(Transformers.aliasToBean(AppioTipimovimentoInterventoBean.class));
	return q.list();
    }

    @Override
    public List<AppioTipimovimentoInterventoBean> findConfigurazioniIntervento(String idservizio, String tipomovimento) {

	return findConfigurazioniInterventoAndSoftware(idservizio, tipomovimento, null);
    }

    @Override
    public void insertConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento, String templateOggetto,
	    String templateMessaggio) {

	TipimovAppioServiziInt entity = new TipimovAppioServiziInt();
	TipimovAppioServiziIntId id = new TipimovAppioServiziIntId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setFkAlberoprocScid(codiceIntervento);
	id.setIdentificativoServizio(idservizio);
	id.setTipomovimento(tipomovimento);
	entity.setId(id);
	entity.setTemplateOggetto(templateOggetto);
	entity.setTemplateMessaggio(templateMessaggio);
	saveEntity(entity);
    }

    @Override
    public void updateConfigurazioneIntervento(String idservizio, String tipomovimento, Integer codiceIntervento, String templateOggetto,
	    String templateMessaggio) {

	TipimovAppioServiziIntId id = new TipimovAppioServiziIntId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setFkAlberoprocScid(codiceIntervento);
	id.setIdentificativoServizio(idservizio);
	id.setTipomovimento(tipomovimento);
	TipimovAppioServiziInt entity = getByIdCustom(TipimovAppioServiziInt.class, id);
	entity.setTemplateOggetto(templateOggetto);
	entity.setTemplateMessaggio(templateMessaggio);
	saveEntity(entity);
    }
}
