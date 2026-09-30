package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.PresenzeAutorizzazioniHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

@Repository
public class MercatipresenzeStoricoDAOImpl extends BaseDAOImpl<MercatipresenzeStorico, PkId> implements MercatipresenzeStoricoDAO {

    private static final Logger log = LoggerFactory.getLogger(MercatipresenzeStoricoDAOImpl.class);

    @Override
    public Class<MercatipresenzeStorico> getEntityClass() {

	return MercatipresenzeStorico.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findAnniDaStorico() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	ProjectionList projList = Projections.projectionList();
	projList.add(Projections.distinct(Projections.property("anno")));
	criteria.setProjection(projList);
	criteria.addOrder(Order.desc("anno"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatiPresenzeDTO findSommaDellePresenzeDaStorico(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("mercato", "_mercato", DetachedCriteria.INNER_JOIN);
	if (uso != null) {
	    criteria.createCriteria("mercatoUso", "_uso", DetachedCriteria.INNER_JOIN);
	}
	criteria.add(Restrictions.eq("mercato.id.codice", mercato.getId().getCodice()));
	if (uso != null) {
	    criteria.add(Restrictions.eq("mercatoUso.id.codice", uso.getId().getCodice()));
	}
	// la ricerca va solo per autorizzazione
	// criteria.add(Restrictions.eq("anagrafe.id.codice", autorizzazione.getAnagrafe().getId().getCodice()));
	criteria.add(Restrictions.eq("autorizzazioni.id.codice", autorizzazione.getId().getCodice()));
	if (StringUtils.isNotBlank(catMerc)) {
	    criteria.add(Restrictions.eq("catMerc", catMerc));
	}
	if (anno != null && anno.intValue() > 0) {
	    criteria.add(Restrictions.eq("anno", anno));
	}
	if (posteggio != null) {
	    criteria.add(Restrictions.eq("posteggio.id.codice", posteggio.getId().getCodice()));
	}
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.sum("numeropresenze"), "presenze");
	projectionList.add(Projections.sum("numPresProprietario"), "presenzeComeProprietario");
	criteria.setProjection(projectionList);
	criteria.setResultTransformer(Transformers.aliasToBean(MercatiPresenzeDTO.class));
	List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	MercatiPresenzeDTO presenze = new MercatiPresenzeDTO();
	if (!list.isEmpty()) {
	    presenze = (MercatiPresenzeDTO) list.get(0);
	}
	return presenze;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeStorico> findByAutorizzazione(Autorizzazioni aut) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("autorizzazioni.id.codice", aut.getId().getCodice()));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findAutorizzazioniPerCalcoloPresenze(Mercati mercato, MercatiUso uso, MercatiD posteggio, String catMerc, Integer anno) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql1 = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	String whereStorico = " idcomune=? ";
	String whereAttuale = " mercatipresenze_d.idcomune=? ";
	if (mercato != null) {
	    whereStorico += " and (mercatipresenze_storico.fkcodicemercato=? or mercatipresenze_storico.fkcodicemercato is null) ";
	    whereAttuale += " and mercatipresenze_t.fkcodicemercato=? ";
	    if (uso != null) {
		whereStorico += " and (mercatipresenze_storico.fkidmercatiuso=? or mercatipresenze_storico.fkidmercatiuso is null) ";
		whereAttuale += " and mercatipresenze_t.fkidmercatiuso=? ";
	    }
	    if (posteggio != null) {
		whereStorico += " and (mercatipresenze_storico.fkidposteggio=? or mercatipresenze_storico.fkidposteggio is null) ";
		whereAttuale += " and mercatipresenze_d.fkidposteggio=? ";
	    }
	}
	if (StringUtils.isNotBlank(catMerc)) {
	    whereStorico += " and (mercatipresenze_storico.cat_merc=? or mercatipresenze_storico.cat_merc is null) ";
	    whereAttuale += " and mercatipresenze_d.cat_merc=? ";
	}
	if (anno != null && anno.intValue() > 0) {
	    whereStorico += " and (mercatipresenze_storico.anno=?) ";
	    whereAttuale += " and (mercatipresenze_t.anno=?) ";
	}
	sql1 = sql1.replaceAll("#WHERE_CLAUSE#", whereStorico);
	sql1 = sql1.replaceAll("#WHERE_CLAUSE1#", whereAttuale);
	SQLQuery q = getSession().createSQLQuery(sql1);
	q.addScalar("autorizzazioni", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	int pos = 1;
	if (mercato != null) {
	    q.setInteger(pos, mercato.getId().getCodice());
	    pos++;
	    if (uso != null) {
		q.setInteger(pos, uso.getId().getCodice());
		pos++;
	    }
	    if (posteggio != null) {
		q.setInteger(pos, posteggio.getId().getCodice());
		pos++;
	    }
	}
	if (StringUtils.isNotBlank(catMerc)) {
	    q.setString(pos, catMerc);
	    pos++;
	}
	if (anno != null && anno.intValue() > 0) {
	    q.setInteger(pos, anno);
	    pos++;
	}
	q.setString(pos, ORMHelper.getIdcomune());
	pos++;
	if (mercato != null) {
	    q.setInteger(pos, mercato.getId().getCodice());
	    pos++;
	    if (uso != null) {
		q.setInteger(pos, uso.getId().getCodice());
		pos++;
	    }
	    if (posteggio != null) {
		q.setInteger(pos, posteggio.getId().getCodice());
		pos++;
	    }
	}
	if (StringUtils.isNotBlank(catMerc)) {
	    q.setString(pos, catMerc);
	    pos++;
	}
	if (anno != null && anno.intValue() > 0) {
	    q.setInteger(pos, anno);
	    pos++;
	}
	return q.list();
    }

    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";
    private String sql = "select fk_autorizzazioni_id as autorizzazioni " //
	    +
	    "from " + //
	    "  ((select " + //
	    "    fk_autorizzazioni_id " + //
	    "  from " +
	    SCHEMA_NAME +
	    "mercatipresenze_storico " + //
	    "  where #WHERE_CLAUSE# " + //
	    "  ) union (" + //
	    "  select " + //
	    "    mercatipresenze_d.fk_autorizzazioni_id " + //
	    "  from " +
	    SCHEMA_NAME +
	    "mercatipresenze_d inner join  " + //
	    "    " +
	    SCHEMA_NAME +
	    "mercatipresenze_t on " + //
	    "  mercatipresenze_d.idcomune=mercatipresenze_t.idcomune " + //
	    "  and mercatipresenze_d.fkidtestata=mercatipresenze_t.id " + //
	    "  and mercatipresenze_d.codiceanagrafe is not null " + //
	    " where #WHERE_CLAUSE1#" + //
	    "  )) t1";

    @SuppressWarnings("unchecked")
    @Override
    public void updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato(Integer codiceAutorizzazione, Integer codiceMercato, Integer codiceuso) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// alias
	criteria.createAlias("mercato", "_mercati");
	criteria.createAlias("mercatoUso", "_mercatiuso");
	criteria.createAlias("autorizzazioni", "_autorizzazioni");
	// Projection
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	criteria.setProjection(plist);
	// condizioni di where
	criteria.add(Restrictions.eq("_mercati.id.codice", codiceMercato));
	criteria.add(Restrictions.eq("_mercatiuso.id.codice", codiceuso));
	criteria.add(Restrictions.eq("_autorizzazioni.id.codice", codiceAutorizzazione));
	log.debug("updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato# Recupero presenze da annullare per aut = {}, mercato = {}, giorno = {}",
		new Object[] { codiceAutorizzazione, codiceMercato, codiceuso });
	List<Integer> list = (List<Integer>) getHibernateTemplate().findByCriteria(criteria);
	log.debug("updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato# Metto a zero il valore presenza per i record trovati (Annullo presneza) ");
	for (Integer integer : list) {
	    updateCampoPresenzaAZero(integer);
	}
	log.debug("updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato# Recupero le presenze storico aut = {}, mercato = {}, giorno = {}",
		new Object[] { codiceAutorizzazione, codiceMercato, codiceuso });
	//
    }

    @Override
    public void updateCampoPresenzaAZero(Integer codiceMercatopresenzaStorico) {

	if (codiceMercatopresenzaStorico == null) {
	    throw new RuntimeException("updatePresenza: il parametro codiceMercatopresenzaD passato è nullo");
	}
	if (codiceMercatopresenzaStorico != null) {
	    String hql = "update MercatipresenzeStorico set numeropresenze = ? where id.idcomune = ? and id.codice=?";
	    int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { 0, ORMHelper.getIdcomune(), codiceMercatopresenzaStorico });
	    if (i != 1) {
		throw new RuntimeException("La query di aggiornamento della presenza di MercatopresenzaStorico   :[" +
			codiceMercatopresenzaStorico +
			"] con valore [" +
			0 +
			"] ha influito su " +
			i +
			" record");
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PresenzeAutorizzazioniHelper> findPresenzeSpuntistiByAutorizzazioni(Set<Integer> autsS, Integer anno) {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String hibernateDialect = sfi.getDialect().toString();
	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	String sqlPresenzeSpuntisti = "select " + // 
		" mpt.dataregistrazione as datapresenzamercato," + //
		"	m.codicemercato as mercatoid," + //
		"	m.descrizione as mercato," + //
		"	g.id as giornoid," + //
		"	g.descrizione as giorno," + //
		"	a.autoriznumero," + //
		"	a.autorizdata," + //
		"	caut.comune as comuneautorizzazione," + //
		"	a.id as autorizzazioneid," + //
		"	a.autorig_numero as autorizzazioneoriginaria," + //
		"	mpd.numeropresenze," + //
		"	post.codiceposteggio," + //
		"	post.superficie," + //
		"	csi.aut_precedente_numero as autprecedentenumero," + //
		"	csi.aut_precedente_data as autprecedentedata," + //
		"	caut_prec.comune as autprecomune," + //
		"	gerente.nome as gerentenome," + //
		"	gerente.nominativo as gerentecognome," + //
		"	gerente.codicefiscale as gerentecf," + //
		"	gerente.partitaiva as gerentepiva," + //
		"	gerente.tipoanagrafe as gerentetipoanagrafe," + //
		"	titolare.nome as titolarenome," + //
		"	titolare.nominativo as titolarecognome," + //
		"	titolare.codicefiscale as titolarecf," + //
		"	titolare.partitaiva as titolarepiva," + //
		"	titolare.tipoanagrafe as titolaretipoanagrafe," + //		
		"	'GIORNATA' as storico, " + //
		"	mpd.id as presenzaid, " + //
		"	mpd.fk_pay_pos_deb as payposdspuntid " + //
		"	from " + //
		"	mercatipresenze_d mpd " + //
		"	inner join mercatipresenze_t mpt on mpt.idcomune = mpd.idcomune " + //
		"	                        and mpt.id = mpd.fkidtestata " + //	                        
		"	inner join mercati m on m.idcomune = mpt.idcomune " + //
		"	            and m.codicemercato = mpt.fkcodicemercato " + //
		"	inner join mercati_uso g on g.idcomune = mpt.idcomune " + //
		"	            and g.id = mpt.fkidmercatiuso                   " + //          
		"	inner join autorizzazioni a on a.idcomune = mpd.idcomune " + //
		"	                   and a.id = mpd.fk_autorizzazioni_id " + //
		"	left join autorizzazioni_csi csi on  " + //
		"	csi.idcomune=a.idcomune and " + //
		"	csi.fk_autorizzazioni_id=a.id " + //
		"	left join anagrafe titolare on " + //
		"	a.idcomune=titolare.idcomune and " + //
		"	a.fk_codiceanagrafe=titolare.codiceanagrafe " + //	
		"	left join anagrafe gerente on " + //
		"	csi.idcomune=gerente.idcomune and " + //
		"	csi.fk_codicegerente=gerente.codiceanagrafe " + //
		"	left join mercati_d post on post.idcomune = mpd.idcomune " + //
		"	and post.idposteggio = mpd.fkidposteggio " + //
		"	left join vw_entilocali caut on  " + //
		"	caut.codicecomune=a.autorizcomune " + //
		"	left join vw_entilocali caut_prec on  " + //
		"	caut_prec.codicecomune=csi.aut_precedente_comune " + //                                
		"	where " + //
		"	mpd.idcomune = ? " + //
		"       #ANNO#" + //
		"	#AUTORIZZAZIONI# " + //
		"	and   mpd.spuntista = ?  " + //	
		"	union " + //	
		"	select " + //
		"	#DATA_STORICO# as datapresenzamercato, " + //
		"	m.codicemercato as mercatoid, " + //
		"	m.descrizione as mercato, " + //
		"	g.id as giornoid, " + //
		"	g.descrizione as giorno, " + //
		"	a.autoriznumero, " + //
		"	a.autorizdata, " + //
		"	caut.comune as comuneautorizzazione, " + //
		"	a.id as autorizzazioneid, " + //
		"	a.autorig_numero as autorizzazioneoriginaria, " + //
		"	mpd.numeropresenze, " + //
		"	post.codiceposteggio, " + //
		"	post.superficie, " + //
		"	csi.aut_precedente_numero as autprecedentenumero, " + //
		"	csi.aut_precedente_data as autprecedentedata, " + //
		"	caut_prec.comune as autprecomune, " + //
		"	gerente.nome as gerentenome, " + //
		"	gerente.nominativo as gerentecognome, " + //
		"	gerente.codicefiscale as gerentecf, " + //
		"	gerente.partitaiva as gerentepiva, " + //
		"	gerente.tipoanagrafe as gerentetipoanagrafe, " + //
		"	titolare.nome as titolarenome, " + //
		"	titolare.nominativo as titolarecognome, " + //
		"	titolare.codicefiscale as titolarecf, " + //
		"	titolare.partitaiva as titolarepiva, " + //
		"	titolare.tipoanagrafe as titolaretipoanagrafe," + //		
		"	'STORICO' as storico, " + //
		"	mpd.id as presenzaid, " + //
		"	null as payposdspuntid " + //
		"	from " + //
		"	mercatipresenze_storico mpd " + //
		"	inner join mercati m on m.idcomune = mpd.idcomune " + //
		"	            and m.codicemercato = mpd.fkcodicemercato " + //
		"	inner join mercati_uso g on g.idcomune = mpd.idcomune " + //
		"	            and g.id = mpd.fkidmercatiuso                   " + //          
		"	inner join autorizzazioni a on a.idcomune = mpd.idcomune " + //
		"	                   and a.id = mpd.fk_autorizzazioni_id " + //
		"	left join autorizzazioni_csi csi on  " + //
		"	csi.idcomune=a.idcomune and " + //
		"	csi.fk_autorizzazioni_id=a.id " + //
		"	left join anagrafe titolare on " + //
		"	a.idcomune=titolare.idcomune and " + //
		"	a.fk_codiceanagrafe=titolare.codiceanagrafe " + //		
		"	left join anagrafe gerente on " + //
		"	csi.idcomune=gerente.idcomune and " + //
		"	csi.fk_codicegerente=gerente.codiceanagrafe " + //
		"	left join mercati_d post on post.idcomune = mpd.idcomune " + //
		"	and post.idposteggio = mpd.fk_idposteggio " + //
		"	left join vw_entilocali caut on  " + //
		"	caut.codicecomune=a.autorizcomune " + //
		"	left join vw_entilocali caut_prec on  " + //
		"	caut_prec.codicecomune=csi.aut_precedente_comune " + //                                
		"	where " + //
		"	mpd.idcomune = ? " + //
		"       #ANNO_STORICO#" + //
		"	#AUTORIZZAZIONI#";
	String sql1 = sqlPresenzeSpuntisti.replaceAll(SCHEMA_NAME, schemaName + ".");
	String dataStorico = "";
	switch (dialetto) {
	    case MYSQL:
		dataStorico = "str_to_date(concat_ws('-',mpd.anno,'01','01'),'%Y-%m-%d') ";
		break;
	    case ORACLE:
		dataStorico = "to_date( mpd.anno || '-01-01','yyyy-MM-dd') ";
		break;
	    default:
		throw new NotImplementedException("Dialetto " + dialetto + " non implementato");
	}
	String annostorico = "  ";
	String annoPresenze = " ";
	if (anno != null) {
	    annostorico = " and mpd.anno=? ";
	    annoPresenze = " and mpt.anno=? ";
	}
	sql1 = sql1.replaceAll("#DATA_STORICO#", dataStorico).replaceAll("#ANNO_STORICO#", annostorico).replaceAll("#ANNO#", annoPresenze);
	String autorizzazioni = "";
	int num = autsS.size();
	Double filter_getListaCodiceAttivita_length = Double.valueOf(num);
	Double cicli = filter_getListaCodiceAttivita_length / 1000;
	int cicliDaMille = cicli.intValue();
	int resto = num - (cicliDaMille * 1000);
	autorizzazioni += " and ( 1=2 ";
	for (int i = 0; i < cicliDaMille; i++) {
	    String qm = StringUtils.repeat("?,", 1000);
	    qm = qm.substring(0, qm.length() - 1);
	    autorizzazioni += " or a.id in (" + qm + ")";
	}
	if (resto > 0) {
	    String qm = StringUtils.repeat("?,", resto);
	    qm = qm.substring(0, qm.length() - 1);
	    autorizzazioni += " or a.id in (" + qm + ")";
	}
	autorizzazioni += ")";
	sql1 = sql1.replaceAll("#AUTORIZZAZIONI#", autorizzazioni);
	SQLQuery q = getSession().createSQLQuery(sql1);
	//  SCALAR VALUES
	//	" mpt.dataregistrazione as datapresenzamercato," + //
	q.addScalar("datapresenzamercato", Hibernate.DATE);
	//	"	m.codicemercato as mercato_id," + //
	q.addScalar("mercatoid", Hibernate.INTEGER);
	//	"	m.descrizione as mercato," + //
	q.addScalar("mercato", Hibernate.STRING);
	//	"	g.id as giorno_id," + //
	q.addScalar("giornoid", Hibernate.INTEGER);
	//	"	g.descrizione as giorno," + //
	q.addScalar("giorno", Hibernate.STRING);
	//	"	a.autoriznumero," + //
	q.addScalar("autoriznumero", Hibernate.STRING);
	//	"	a.autorizdata," + //
	q.addScalar("autorizdata", Hibernate.DATE);
	//	"	caut.comune as comune_autorizzazione," + //
	q.addScalar("comuneautorizzazione", Hibernate.STRING);
	//	"	a.id as autorizzazione_id," + //
	q.addScalar("autorizzazioneid", Hibernate.INTEGER);
	//	"	a.autorig_numero as autorizzazione_originaria," + //
	q.addScalar("autorizzazioneoriginaria", Hibernate.STRING);
	//	"	mpd.numeropresenze," + //
	q.addScalar("numeropresenze", Hibernate.INTEGER);
	//	"	post.codiceposteggio," + //
	q.addScalar("codiceposteggio", Hibernate.STRING);
	//	"	post.superficie," + //
	q.addScalar("superficie", Hibernate.BIG_DECIMAL);
	//	"	csi.aut_precedente_numero," + //
	q.addScalar("autprecedentenumero", Hibernate.STRING);
	//	"	csi.aut_precedente_data," + //
	q.addScalar("autprecedentedata", Hibernate.DATE);
	//	"	caut_prec.comune as aut_pre_comune," + //
	q.addScalar("autprecomune", Hibernate.STRING);
	//	"	gerente.nome as gerente_nome," + //
	q.addScalar("gerentenome", Hibernate.STRING);
	//	"	gerente.nominativo as gerente_cognome," + //
	q.addScalar("gerentecognome", Hibernate.STRING);
	//	"	gerente.codicefiscale as gerente_cf," + //
	q.addScalar("gerentecf", Hibernate.STRING);
	//	"	gerente.partitaiva as gerente_piva," + //
	q.addScalar("gerentepiva", Hibernate.STRING);
	//	"	gerente.tipoanagrafe as gerente_tipoanagrafe," + //
	q.addScalar("gerentetipoanagrafe", Hibernate.STRING);
	//	"	titolare.nome as titolare_nome," + //
	q.addScalar("titolarenome", Hibernate.STRING);
	//	"	titolare.nominativo as titolare_cognome," + //
	q.addScalar("titolarecognome", Hibernate.STRING);
	//	"	titolare.codicefiscale as titolare_cf," + //
	q.addScalar("titolarecf", Hibernate.STRING);
	//	"	titolare.partitaiva as titolare_piva," + //
	q.addScalar("titolarepiva", Hibernate.STRING);
	//	"	titolare.tipoanagrafe as titolare_tipoanagrafe" + //
	q.addScalar("titolaretipoanagrafe", Hibernate.STRING);
	// 	"	'STORICO' as storico, " + //
	q.addScalar("storico", Hibernate.STRING);
	//	"	mpd.id as presenzaid " + //
	q.addScalar("presenzaid", Hibernate.INTEGER);
	//	"	null as payposdspuntid " + //
	q.addScalar("payposdspuntid", Hibernate.INTEGER);
	// SCALAR VALUES
	q.setString(0, ORMHelper.getIdcomune());//	"	mpd.idcomune = ? " + //
	int position = 1;
	if (anno != null) {
	    q.setInteger(position++, anno);//	"	mpt.anno = ? " + //
	}
	for (Integer codiceattivita : autsS) { //	"	and #AUTORIZZAZIONI# " + //
	    q.setInteger(position++, codiceattivita);
	}
	q.setBoolean(position++, Boolean.TRUE); //	"	and   mpd.spuntista = ?  " + //
	//	seconda query della union
	q.setString(position++, ORMHelper.getIdcomune());//	"	mpd.idcomune = ? " + //
	if (anno != null) {
	    q.setInteger(position++, anno);//	"	mpt.anno = ? " + //
	}
	for (Integer codiceattivita : autsS) { //	"	and #AUTORIZZAZIONI# " + //
	    q.setInteger(position++, codiceattivita);
	}
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(PresenzeAutorizzazioniHelper.class));
	return q.list();
    }
}
