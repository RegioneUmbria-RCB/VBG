package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeConcessioniMercatoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioInfoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioMercatiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.MercatiDFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioMercatoBean;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;

@Repository
public class MercatiDDAOImpl extends BaseDAOImpl<MercatiD, PkId> implements MercatiDDAO {

    private static final String PROP_DISABILITATO = "disabilitato";
    private static final String PROP_CODICEPOSTEGGIO = "codiceposteggio";
    private static final String PROP_MERCATI = "mercati";

    @Override
    public Class<MercatiD> getEntityClass() {

	return MercatiD.class;
    }

    @Override
    public List<MercatiD> findAllByMercato(Mercati mercati) {

	return this.findByMercato(mercati, PosteggiEnum.ALL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findByMercato(Mercati mercati, PosteggiEnum posteggiEnum) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq(PROP_MERCATI, mercati));
	det.addOrder(Order.asc(PROP_CODICEPOSTEGGIO));
	switch (posteggiEnum) {
	    case ACTIVE:
		det.add(Restrictions.or(Restrictions.eq(PROP_DISABILITATO, false), Restrictions.isNull(PROP_DISABILITATO)));
		break;
	    case DISABLED:
		det.add(Restrictions.eq(PROP_DISABILITATO, true));
		break;
	    case ALL:
		break;
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findByMercatoOrderByPeso(Mercati mercati, PosteggiEnum posteggiEnum) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq(PROP_MERCATI, mercati));
	det.addOrder(Order.desc("peso"));
	switch (posteggiEnum) {
	    case ACTIVE:
		det.add(Restrictions.or(Restrictions.eq(PROP_DISABILITATO, false), Restrictions.isNull(PROP_DISABILITATO)));
		break;
	    case DISABLED:
		det.add(Restrictions.eq(PROP_DISABILITATO, true));
		break;
	    case ALL:
		break;
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findByPosteggiConConti(Mercati mercati, Integer anno) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq(PROP_MERCATI, mercati));
	if (anno != null) {
	    criteria.createAlias("listaContiPosteggio", "_listaContiPosteggio", CriteriaSpecification.LEFT_JOIN);
	    criteria.add(Restrictions.eq("_listaContiPosteggio.anno", anno.shortValue()));
	    criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	}
	criteria.addOrder(Order.asc(PROP_CODICEPOSTEGGIO));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findPosteggioByMercatiMercatoUso(Mercati mercati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq(PROP_MERCATI, mercati));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatiD findPosteggioByCodicePosteggio(String codiceposteggio, Mercati mercati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq(PROP_MERCATI, mercati));
	criteria.add(Restrictions.eq(PROP_CODICEPOSTEGGIO, codiceposteggio));
	List<MercatiD> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<MercatiD> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, PROP_CODICEPOSTEGGIO, DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findByMercatiD(MercatiD filter) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercati.id.codice", filter.getMercati().getId().getCodice()));
	// se sono non nulli uno dei campi transiet per la ricerca significa che stiamo cercando una lista filtra e
	// applichiamo i filtri
	if (filter.getConsentita() != null || (filter.getListaCodiciMerceologie() != null && filter.getListaCodiciMerceologie().length > 0)) {
	    criteria.createCriteria("mercatiDattivitaistats", "mercatiDattivitaistat");
	    if (filter.getListaCodiciMerceologie() != null && filter.getListaCodiciMerceologie().length > 0) {
		String[] listacodice = filter.getListaCodiciMerceologie();
		criteria.createAlias("mercatiDattivitaistat.attivita", "_attivita");
		criteria.add(Restrictions.in("_attivita.id.codiceistat", listacodice));
	    }
	    if (filter.getConsentita() != null) {
		criteria.add(Restrictions.eq("mercatiDattivitaistat.flagConsentito", filter.getConsentita()));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	    }
	}
	if (filter.getStradario() != null && filter.getStradario().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("stradario.id.codice", filter.getStradario().getId().getCodice()));
	}
	if (filter.getNote() != null && !filter.getNote().equals("")) {
	    criteria.add(Restrictions.ilike("note", filter.getNote(), MatchMode.ANYWHERE));
	}
	criteria.addOrder(Order.asc(PROP_CODICEPOSTEGGIO));
	criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	// se non sono applicati i filtri si comporta come un findAll filtrando solo per idcomune e software
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CodiceDescrizioneBean> findPosteggiNonAssegnatiByMercato(Integer codiceMercato, Integer codiceMercatiUso, Integer posteggioEscluso,
	    PosteggiEnum tipo) {

	if (tipo == null) {
	    tipo = PosteggiEnum.ALL;
	}
	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	StringBuilder SQL = new StringBuilder();
	SQL.append("SELECT idposteggio, ");
	SQL.append("  codiceposteggio ");
	SQL.append("FROM " + schema + ".mercati_d ");
	SQL.append("WHERE idcomune =? ");
	SQL.append("AND fkcodicemercato=? ");
	switch (tipo) {
	    case ACTIVE:
		SQL.append("AND (disabilitato=0 or disabilitato is null) ");
		break;
	    case DISABLED:
		SQL.append("AND disabilitato=1 ");
		break;
	    case ALL:
		break;
	}
	SQL.append("AND idposteggio NOT IN ");
	SQL.append("  (SELECT md.idposteggio ");
	SQL.append("  FROM " + schema + ".mercati_d md ");
	SQL.append("  INNER JOIN " + schema + ".autorizzazioni_concessioni ac ");
	SQL.append("  ON ac.idcomune        =md.idcomune ");
	SQL.append("  AND ac.fk_idposteggio =md.idposteggio ");
	// Per mostrare i posteggi dove è stata cessata una concessione
	////////////////////////////////////////////////////////
	SQL.append("  INNER JOIN " + schema + ".autorizzazioni aut ");
	SQL.append("  ON aut.idcomune        =ac.idcomune ");
	SQL.append("  AND aut.id =ac.fk_idaut_attuale ");
	///////////////////////////////////////////////////
	SQL.append("  WHERE md.idcomune     =? ");
	SQL.append("  AND md.fkcodicemercato=? ");
	if (codiceMercatiUso != null) {
	    SQL.append("  AND ac.fk_idmercatiuso=? ");
	}
	//Condizione where per mostrare i posteggi dove è stata cessata una concessione
	////////////////////////////////////////////////////////
	SQL.append("  AND aut.flag_attiva=? ");
	if (null != posteggioEscluso) {
	    SQL.append("  AND (not md.idposteggio=?) ");
	}
	//////////////////////////////////////////////////////////////
	SQL.append("  ) order by codiceposteggio asc");
	SQLQuery q = session.createSQLQuery(SQL.toString());
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceMercato);
	q.setString(2, ORMHelper.getIdcomune());
	q.setInteger(3, codiceMercato);
	int pos = 4;
	if (codiceMercatiUso != null) {
	    q.setInteger(pos++, codiceMercatiUso);
	    q.setInteger(pos++, 1);
	} else {
	    q.setInteger(pos++, 1);
	}
	if (null != posteggioEscluso) {
	    q.setInteger(pos++, posteggioEscluso);
	}
	q.addScalar("idposteggio", Hibernate.INTEGER);
	q.addScalar(PROP_CODICEPOSTEGGIO, Hibernate.STRING);
	List<Object> result = q.list();
	List<CodiceDescrizioneBean> output = new ArrayList<CodiceDescrizioneBean>();
	if (!result.isEmpty()) {
	    for (Object o : result) {
		if (o instanceof Object[]) {
		    Object[] os = (Object[]) o;
		    Integer codice = (Integer) os[0];
		    String descrizione = (String) os[1];
		    CodiceDescrizioneBean b = new CodiceDescrizioneBean();
		    b.setCodice(String.valueOf(codice));
		    b.setDescrizione(descrizione);
		    output.add(b);
		}
	    }
	}
	return output;
    }

    /**
     * SELECT MERCATI_D.IDCOMUNE, MERCATI_D.IDPOSTEGGIO, MERCATI_D.FKCODICEMERCATO, MERCATI_D.CODICEPOSTEGGIO,
     * MERCATI_D.LARGHEZZA, MERCATI_D.LUNGHEZZA, MERCATI_D.SUPERFICIE, MERCATI_D.DISABILITATO, MERCATI_D.NOTE,
     * MERCATI_D.COORDINATE, MERCATI_D.PESO, STRADARIO.PREFISSO || '' || STRADARIO.DESCRIZIONE || '' AS
     * STRADARIOPOSTEGGIO, POSTEGGITIPOSPAZIO.TIPOSPAZIO, ISTANZE.CODICEISTANZA, MERCATI_USO.DESCRIZIONE AS
     * USOCONCESSIONE, AUTORIZZAZIONI.ID AS CODICECONCESSIONE, AUTORIZZAZIONI.AUTORIZCOMUNE AS CODICECOMUNECONCESSIONE,
     * AUTORIZZAZIONI.AUTORIZNUMERO AS NUMEROCONCESSIONE, AUTORIZZAZIONI.FKIDREGISTRO AS IDREGISTROCONCESSIONE,
     * TIPOLOGIAREGISTRI.TR_DESCRIZIONE AS REGISTROCONCESSIONE, COMUNI.COMUNE AS COMUNECONCESSIONE,
     * TITOLARE.CODICEANAGRAFE AS IDTITOLARE, TITOLARE.NOMINATIVO || '' || TITOLARE.NOME || '' AS TITOLARE,
     * OCCUPANTE1.CODICEANAGRAFE AS IDOCCUPANTE1, OCCUPANTE1.NOMINATIVO AS OCCUPANTE1, OCCUPANTE2.CODICEANAGRAFE AS
     * IDOCCUPANTE2, OCCUPANTE2.NOMINATIVO || '' || OCCUPANTE2.NOME || '' AS OCCUPANTE2, MERCATI_USO.ID AS
     * CODICEUSO,POSTEGGI_SETTORI.SETTORE AS SETTOREPOSTEGGIO FROM SIGEPRO2.MERCATI_D LEFT JOIN
     * SIGEPRO2.POSTEGGITIPOSPAZIO ON SIGEPRO2.MERCATI_D.IDCOMUNE =SIGEPRO2.POSTEGGITIPOSPAZIO.IDCOMUNE AND
     * SIGEPRO2.MERCATI_D.FKCODICETIPOSPAZIO=SIGEPRO2.POSTEGGITIPOSPAZIO.CODICE LEFT OUTER JOIN SIGEPRO2.STRADARIO ON
     * SIGEPRO2.STRADARIO.IDCOMUNE =SIGEPRO2.MERCATI_D.IDCOMUNE AND
     * SIGEPRO2.STRADARIO.CODICESTRADARIO=SIGEPRO2.MERCATI_D.FKCODICESTRADARIO LEFT OUTER JOIN LEFT OUTER JOIN
     * POSTEGGI_SETTORI ON POSTEGGI_SETTORI.IDCOMUNE=MERCATI_D.IDCOMUNE AND
     * POSTEGGI_SETTORI.ID=MERCATI_D.FK_POSTEGGISETTORI_ID SIGEPRO2.AUTORIZZAZIONI_CONCESSIONI ON
     * SIGEPRO2.AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE =MERCATI_D.IDCOMUNE AND
     * SIGEPRO2.AUTORIZZAZIONI_CONCESSIONI.FK_IDPOSTEGGIO=SIGEPRO2.MERCATI_D.IDPOSTEGGIO LEFT JOIN SIGEPRO2.MERCATI_USO
     * ON SIGEPRO2.AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE =SIGEPRO2.MERCATI_USO.IDCOMUNE AND
     * SIGEPRO2.AUTORIZZAZIONI_CONCESSIONI.FK_IDMERCATIUSO =SIGEPRO2.MERCATI_USO.ID LEFT JOIN SIGEPRO2.AUTORIZZAZIONI ON
     * (SIGEPRO2.AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE =SIGEPRO2.AUTORIZZAZIONI.IDCOMUNE AND
     * SIGEPRO2.AUTORIZZAZIONI_CONCESSIONI.FK_IDAUT_ATTUALE=SIGEPRO2.AUTORIZZAZIONI.ID AND (AUTORIZZAZIONI.FLAG_ATTIVA =
     * 1 AND (AUTORIZZAZIONI.DATA_CESSAZIONE >= SYSDATE OR AUTORIZZAZIONI.DATA_CESSAZIONE IS NULL))) LEFT JOIN
     * SIGEPRO2.TIPOLOGIAREGISTRI ON TIPOLOGIAREGISTRI.IDCOMUNE=AUTORIZZAZIONI.IDCOMUNE AND TIPOLOGIAREGISTRI.TR_ID
     * =AUTORIZZAZIONI.FKIDREGISTRO LEFT JOIN COMUNI ON COMUNI.CODICECOMUNE=AUTORIZZAZIONI.AUTORIZCOMUNE LEFT JOIN
     * ISTANZE ON AUTORIZZAZIONI.IDCOMUNE = ISTANZE.IDCOMUNE AND AUTORIZZAZIONI.FKIDISTANZA = ISTANZE.CODICEISTANZA LEFT
     * JOIN ANAGRAFE TITOLARE ON TITOLARE.IDCOMUNE = AUTORIZZAZIONI.IDCOMUNE AND
     * TITOLARE.CODICEANAGRAFE=AUTORIZZAZIONI.FK_CODICEANAGRAFE LEFT JOIN ANAGRAFE OCCUPANTE1 ON OCCUPANTE1.IDCOMUNE =
     * ISTANZE.IDCOMUNE AND OCCUPANTE1.CODICEANAGRAFE=ISTANZE.CODICETITOLARELEGALE LEFT JOIN ANAGRAFE OCCUPANTE2 ON
     * OCCUPANTE2.IDCOMUNE =ISTANZE.IDCOMUNE AND OCCUPANTE2.CODICEANAGRAFE =ISTANZE.CODICERICHIEDENTE WHERE
     * MERCATI_D.IDCOMUNE = ? AND MERCATI_D.FKCODICEMERCATO = ? ORDER BY lpad(CODICEPOSTEGGIO, 20,'0') ASC
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PosteggioMercatiHelper> findMercatiDWithConcessioniSQL(Integer codicemercato, MercatiD mercatiD, boolean isSingoloPosteggio,
	    Integer codiceMercatoUso) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	Dialect dialetto = sessimpl.getDialect();
	DialettoEnum d = DialettoEnum.fromHibernateDialect(dialetto.toString());
	StringBuilder sql = createQueryMercatiDWithConcessioniAndSubentri(schema, mercatiD, d, isSingoloPosteggio, codiceMercatoUso);
	SQLQuery q = session.createSQLQuery(sql.toString());
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codicemercato);
	int indexParamQuery = 2;
	if (mercatiD != null) {
	    if (EntityUtils.getNestedProperty(mercatiD.getStradario(), "id.codice") != null) {
		q.setInteger(indexParamQuery, mercatiD.getStradario().getId().getCodice());
		indexParamQuery++;
	    }
	    if (StringUtils.isNotBlank(mercatiD.getNote())) {
		q.setString(indexParamQuery, "%" + mercatiD.getNote() + "%");
		indexParamQuery++;
	    }
	    if (isSingoloPosteggio) {
		q.setInteger(indexParamQuery, mercatiD.getId().getCodice());
		indexParamQuery++;
	    }
	}
	if (codiceMercatoUso != null) {
	    q.setInteger(indexParamQuery, codiceMercatoUso);
	    indexParamQuery++;
	}
	if (mercatiD.getAttivitaTransient() != null && mercatiD.getAttivitaTransient().getId() != null
		&& StringUtils.isNotBlank(mercatiD.getAttivitaTransient().getId().getCodiceistat())) {
	    q.setString(indexParamQuery, mercatiD.getAttivitaTransient().getId().getCodiceistat());
	    indexParamQuery++;
	    if (mercatiD.getAttivataAmmessaNonAmmessaTransient() != null) {
		boolean ammessa = BooleanUtils.toBoolean(mercatiD.getAttivataAmmessaNonAmmessaTransient());
		q.setBoolean(indexParamQuery, ammessa);
		indexParamQuery++;
	    }
	}
	// esplicito che tipo di valore si aspetta che torni per ogni alias
	addScalarMapping(q);
	List<PosteggioMercatiHelper> posteggioMercatiHelpers = new ArrayList<PosteggioMercatiHelper>();
	PosteggioMercatiHelper posteggioMercatiHelper = null;
	List<Object[]> result = q.list();
	if (!result.isEmpty()) {
	    PosteggioInfoHelper posteggioInfoHelper = null;
	    IstanzeConcessioniMercatoHelper istanzeConcessioniMercatoHelper = null;
	    int i = 0;
	    for (int index = 0; index < result.size(); index++) {
		Object[] os = (Object[]) result.get(index);
		// CASO A: primo elemento
		if (index == 0) {
		    posteggioMercatiHelper = new PosteggioMercatiHelper();
		    posteggioInfoHelper = this.populatePosteggioInfoHelper(os);
		    istanzeConcessioniMercatoHelper = this.populateIstanzeConcessioniMercatoHelper(os);
		    List<IstanzeConcessioniMercatoHelper> _list = new ArrayList<IstanzeConcessioniMercatoHelper>();
		    if (istanzeConcessioniMercatoHelper.getCodiceconcessione() != null) {
			_list.add(istanzeConcessioniMercatoHelper);
		    }
		    posteggioMercatiHelper.setIstanzeConcessioniMercatoHelpers(_list);
		    posteggioMercatiHelper.setPosteggioInfoHelper(posteggioInfoHelper);
		    posteggioMercatiHelpers.add(posteggioMercatiHelper);
		}
		// CASO B: valuto gli elementi 2....N-1
		if (index > 0 && index < result.size()) {
		    // Caso stesso posteggio
		    if (posteggioMercatiHelpers.get(i).getPosteggioInfoHelper().getCodiceposteggio().equals(((String) os[3]))) {
			istanzeConcessioniMercatoHelper = this.populateIstanzeConcessioniMercatoHelper(os);
			if (istanzeConcessioniMercatoHelper.getCodiceconcessione() != null) {
			    posteggioMercatiHelpers.get(i).getIstanzeConcessioniMercatoHelpers().add(istanzeConcessioniMercatoHelper);
			}
		    } else {
			posteggioMercatiHelper = new PosteggioMercatiHelper();
			posteggioInfoHelper = this.populatePosteggioInfoHelper(os);
			istanzeConcessioniMercatoHelper = this.populateIstanzeConcessioniMercatoHelper(os);
			List<IstanzeConcessioniMercatoHelper> _list = new ArrayList<IstanzeConcessioniMercatoHelper>();
			if (istanzeConcessioniMercatoHelper.getCodiceconcessione() != null) {
			    _list.add(istanzeConcessioniMercatoHelper);
			}
			posteggioMercatiHelper.setIstanzeConcessioniMercatoHelpers(_list);
			posteggioMercatiHelper.setPosteggioInfoHelper(posteggioInfoHelper);
			posteggioMercatiHelpers.add(posteggioMercatiHelper);
			i++;
		    }
		}
	    }
	}
	return posteggioMercatiHelpers;
    }

    private void addScalarMapping(SQLQuery q) {

	/**
	 * IDCOMUNE IDPOSTEGGIO FKCODICEMERCATO CODICEPOSTEGGIO LARGHEZZA LUNGHEZZA SUPERFICIE DISABILITATO NOTE
	 * COORDINATE PESO STRADARIOPOSTEGGIO TIPOSPAZIO CODICEISTANZA USOCONCESSIONE CODICECONCESSIONE
	 * CODICECOMUNECONCESSIONE NUMEROCONCESSIONE IDREGISTROCONCESSIONE REGISTROCONCESSIONE COMUNECONCESSIONE
	 * IDTITOLARE TITOLARE IDOCCUPANTE1 OCCUPANTE1 IDOCCUPANTE2 OCCUPANTE2 CODICEUSO POSIZIONE
	 */
	q.addScalar("IDCOMUNE", Hibernate.STRING);
	q.addScalar("IDPOSTEGGIO", Hibernate.BIG_DECIMAL);
	q.addScalar("FKCODICEMERCATO", Hibernate.BIG_DECIMAL);
	q.addScalar("CODICEPOSTEGGIO", Hibernate.STRING);
	q.addScalar("LARGHEZZA", Hibernate.BIG_DECIMAL);
	q.addScalar("LUNGHEZZA", Hibernate.BIG_DECIMAL);
	q.addScalar("SUPERFICIE", Hibernate.BIG_DECIMAL);
	q.addScalar("DISABILITATO", Hibernate.BIG_DECIMAL);
	q.addScalar("NOTE", Hibernate.STRING);
	q.addScalar("COORDINATE", Hibernate.STRING);
	q.addScalar("PESO", Hibernate.BIG_DECIMAL);
	q.addScalar("STRADARIOPOSTEGGIO", Hibernate.STRING);
	q.addScalar("TIPOSPAZIO", Hibernate.STRING);
	q.addScalar("CODICEISTANZA", Hibernate.BIG_DECIMAL);
	q.addScalar("USOCONCESSIONE", Hibernate.STRING);
	q.addScalar("CODICECONCESSIONE", Hibernate.BIG_DECIMAL);
	q.addScalar("CODICECOMUNECONCESSIONE", Hibernate.STRING);
	q.addScalar("NUMEROCONCESSIONE", Hibernate.STRING);
	q.addScalar("IDREGISTROCONCESSIONE", Hibernate.BIG_DECIMAL);
	q.addScalar("REGISTROCONCESSIONE", Hibernate.STRING);
	q.addScalar("COMUNECONCESSIONE", Hibernate.STRING);
	q.addScalar("IDTITOLARE", Hibernate.BIG_DECIMAL);
	q.addScalar("TITOLARE", Hibernate.STRING);
	q.addScalar("IDOCCUPANTE", Hibernate.BIG_DECIMAL);
	q.addScalar("OCCUPANTE", Hibernate.STRING);
	q.addScalar("CODICEUSO", Hibernate.BIG_DECIMAL);
	q.addScalar("POSIZIONE", Hibernate.INTEGER);
	q.addScalar("SETTOREPOSTEGGIO", Hibernate.STRING);
    }

    private IstanzeConcessioniMercatoHelper populateIstanzeConcessioniMercatoHelper(Object[] os) {

	IstanzeConcessioniMercatoHelper istanzeConcessioniMercatoHelper = new IstanzeConcessioniMercatoHelper();
	//13
	if (((BigDecimal) os[13]) != null) {
	    istanzeConcessioniMercatoHelper.setCodiceistanza(((BigDecimal) os[13]).intValue());
	}
	istanzeConcessioniMercatoHelper.setUsoConcessione(((String) os[14]));
	if (((BigDecimal) os[15]) != null) {
	    istanzeConcessioniMercatoHelper.setCodiceconcessione(((BigDecimal) os[15]).intValue());
	}
	istanzeConcessioniMercatoHelper.setCodicecomuneConcessione(((String) os[16]));
	istanzeConcessioniMercatoHelper.setNumeroConcessione(((String) os[17]));
	if (((BigDecimal) os[18]) != null) {
	    istanzeConcessioniMercatoHelper.setIdRegistroConcessione(((BigDecimal) os[18]).intValue());
	}
	istanzeConcessioniMercatoHelper.setRegistroConcessione(((String) os[19]));
	istanzeConcessioniMercatoHelper.setComuneConcessione(((String) os[20]));
	if (((BigDecimal) os[21]) != null) {
	    istanzeConcessioniMercatoHelper.setIdTitolare(((BigDecimal) os[21]).intValue());
	}
	istanzeConcessioniMercatoHelper.setTitolare(((String) os[22]));
	if (((BigDecimal) os[23]) != null) {
	    istanzeConcessioniMercatoHelper.setIdOccupante(((BigDecimal) os[23]).intValue());
	}
	istanzeConcessioniMercatoHelper.setOccupante(((String) os[24]));
	if (((BigDecimal) os[25]) != null) {
	    istanzeConcessioniMercatoHelper.setCodiceuso(((BigDecimal) os[25]).intValue());
	}
	return istanzeConcessioniMercatoHelper;
    }

    private PosteggioInfoHelper populatePosteggioInfoHelper(Object[] os) {

	PosteggioInfoHelper posteggioInfoHelper = new PosteggioInfoHelper();
	PkId id = new PkId();
	id.setIdcomune((String) os[0]);
	Integer idposteggio = ((BigDecimal) os[1]).intValue();
	id.setCodice(idposteggio);
	posteggioInfoHelper.setId(id);
	posteggioInfoHelper.setCociceMercato(((BigDecimal) os[2]).intValue());
	posteggioInfoHelper.setCodiceposteggio((String) os[3]);
	posteggioInfoHelper.setLarghezza((BigDecimal) os[4]);
	posteggioInfoHelper.setLunghezza((BigDecimal) os[5]);
	posteggioInfoHelper.setSuperficie((BigDecimal) os[6]);
	Boolean disabilitato = false;
	if (((BigDecimal) os[7]) != null) {
	    disabilitato = BooleanUtils.toBoolean(((BigDecimal) os[7]).intValue());
	}
	posteggioInfoHelper.setDisabilitato(disabilitato);
	if (StringUtils.isNotBlank((String) os[8])) {
	    posteggioInfoHelper.setNote((String) os[8]);
	}
	posteggioInfoHelper.setCoordinate((String) os[9]);
	if (((BigDecimal) os[10]) != null) {
	    posteggioInfoHelper.setPeso(((BigDecimal) os[10]).intValue());
	}
	posteggioInfoHelper.setDescrizioneStradario((String) os[11]);
	posteggioInfoHelper.setTipospazio((String) os[12]);
	posteggioInfoHelper.setPosizione((Integer) os[26]);
	posteggioInfoHelper.setSettoreposteggio((String) os[27]);
	return posteggioInfoHelper;
    }

    private StringBuilder createQueryMercatiDWithConcessioniAndSubentri(String schema, MercatiD mercatiD, DialettoEnum d, boolean isSingoloPosteggio,
	    Integer codiceMercatoUso) {

	StringBuilder sql = new StringBuilder();
	// SEZIONE SELECT
	//
	// Informazioni sul posteggio
	sql.append("SELECT MERCATI_D.IDCOMUNE,MERCATI_D.IDPOSTEGGIO,MERCATI_D.FKCODICEMERCATO,MERCATI_D.CODICEPOSTEGGIO,MERCATI_D.LARGHEZZA,");
	sql.append("MERCATI_D.LUNGHEZZA,MERCATI_D.SUPERFICIE, MERCATI_D.DISABILITATO,MERCATI_D.NOTE,MERCATI_D.COORDINATE,MERCATI_D.PESO,");
	sql.append(applyConcatFunction(d, "' '", new String[] { "STRADARIO.PREFISSO", "STRADARIO.DESCRIZIONE" })).append("AS STRADARIOPOSTEGGIO,");
	sql.append("POSTEGGITIPOSPAZIO.TIPOSPAZIO,");
	// Informazioni sulle concessioni associate al posteggio
	sql.append(
		"ISTANZE.CODICEISTANZA,MERCATI_USO.DESCRIZIONE AS USOCONCESSIONE,AUTORIZZAZIONI.ID AS CODICECONCESSIONE,AUTORIZZAZIONI.AUTORIZCOMUNE AS CODICECOMUNECONCESSIONE,");
	sql.append(
		"AUTORIZZAZIONI.AUTORIZNUMERO AS NUMEROCONCESSIONE,AUTORIZZAZIONI.FKIDREGISTRO AS IDREGISTROCONCESSIONE,TIPOLOGIAREGISTRI.TR_DESCRIZIONE AS REGISTROCONCESSIONE,");
	sql.append("COMUNI.COMUNE AS COMUNECONCESSIONE,TITOLARE.CODICEANAGRAFE AS IDTITOLARE,");
	sql.append(applyConcatFunction(d, "' '", new String[] { "TITOLARE.NOMINATIVO", "TITOLARE.NOME" })).append("AS TITOLARE,");
	sql.append("OCCUPANTE.CODICEANAGRAFE  AS IDOCCUPANTE,");
	sql.append("trim(").append(applyConcatFunction(d, "' '", new String[] { "OCCUPANTE.NOMINATIVO", "OCCUPANTE.NOME" }))
		.append(") AS OCCUPANTE,");
	sql.append("MERCATI_USO.ID AS CODICEUSO,");
	sql.append("MERCATI_D.POSIZIONE AS POSIZIONE,");
	sql.append("POSTEGGI_SETTORI.SETTORE AS SETTOREPOSTEGGIO");
	// Sezione FROM
	// 
	sql.append(" FROM MERCATI_D LEFT JOIN POSTEGGITIPOSPAZIO");
	sql.append(" ON MERCATI_D.IDCOMUNE=POSTEGGITIPOSPAZIO.IDCOMUNE AND MERCATI_D.FKCODICETIPOSPAZIO=POSTEGGITIPOSPAZIO.CODICE");
	sql.append(" LEFT OUTER JOIN STRADARIO ON  STRADARIO.IDCOMUNE=MERCATI_D.IDCOMUNE AND STRADARIO.CODICESTRADARIO=MERCATI_D.FKCODICESTRADARIO");
	sql.append(
		" LEFT OUTER JOIN POSTEGGI_SETTORI ON  POSTEGGI_SETTORI.IDCOMUNE=MERCATI_D.IDCOMUNE AND POSTEGGI_SETTORI.ID=MERCATI_D.FK_POSTEGGISETTORI_ID");
	sql.append(
		" LEFT OUTER JOIN AUTORIZZAZIONI_CONCESSIONI ON AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE=MERCATI_D.IDCOMUNE AND AUTORIZZAZIONI_CONCESSIONI.FK_IDPOSTEGGIO=MERCATI_D.IDPOSTEGGIO");
	sql.append(
		" LEFT JOIN MERCATI_USO ON AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE=MERCATI_USO.IDCOMUNE AND AUTORIZZAZIONI_CONCESSIONI.FK_IDMERCATIUSO =MERCATI_USO.ID");
	sql.append(" LEFT JOIN AUTORIZZAZIONI ON (AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE=AUTORIZZAZIONI.IDCOMUNE");
	sql.append(
		" AND AUTORIZZAZIONI_CONCESSIONI.FK_IDAUT_ATTUALE=AUTORIZZAZIONI.ID AND (AUTORIZZAZIONI.FLAG_ATTIVA= 1 AND (AUTORIZZAZIONI.DATA_CESSAZIONE>=" +
			applySysdateFunction(d))
		.append(" OR AUTORIZZAZIONI.DATA_CESSAZIONE IS NULL)))");
	sql.append(
		" LEFT JOIN TIPOLOGIAREGISTRI ON TIPOLOGIAREGISTRI.IDCOMUNE=AUTORIZZAZIONI.IDCOMUNE AND TIPOLOGIAREGISTRI.TR_ID  =AUTORIZZAZIONI.FKIDREGISTRO");
	sql.append(" LEFT JOIN COMUNI ON COMUNI.CODICECOMUNE=AUTORIZZAZIONI.AUTORIZCOMUNE");
	sql.append(" LEFT JOIN ISTANZE ON AUTORIZZAZIONI.IDCOMUNE=  ISTANZE.IDCOMUNE AND AUTORIZZAZIONI.FKIDISTANZA = ISTANZE.CODICEISTANZA");
	sql.append(
		" LEFT JOIN ANAGRAFE TITOLARE ON TITOLARE.IDCOMUNE = AUTORIZZAZIONI.IDCOMUNE AND TITOLARE.CODICEANAGRAFE=AUTORIZZAZIONI.FK_CODICEANAGRAFE");
	sql.append(
		" LEFT JOIN ANAGRAFE OCCUPANTE ON OCCUPANTE.IDCOMUNE = AUTORIZZAZIONI.IDCOMUNE AND OCCUPANTE.CODICEANAGRAFE=AUTORIZZAZIONI.CODICEOCCUPANTE ");
	sql.append(
		" LEFT JOIN MERCATI_DATTIVITAISTAT MERCEOLOGIA ON MERCEOLOGIA.IDCOMUNE = MERCATI_D.IDCOMUNE AND MERCEOLOGIA.FKIDPOSTEGGIO=MERCATI_D.IDPOSTEGGIO ");
	// SEZIONE WHERE
	//
	sql.append(" WHERE MERCATI_D.IDCOMUNE = ? ");
	sql.append(" AND MERCATI_D.FKCODICEMERCATO = ? ");
	if (mercatiD != null) {
	    if (EntityUtils.getNestedProperty(mercatiD.getStradario(), "id.codice") != null) {
		sql.append(" AND MERCATI_D.FKCODICESTRADARIO = ? ");
	    }
	    if (StringUtils.isNotBlank(mercatiD.getNote())) {
		sql.append(" AND LOWER(MERCATI_D.NOTE) LIKE ? ");
	    }
	}
	if (isSingoloPosteggio) {
	    sql.append(" AND MERCATI_D.IDPOSTEGGIO = ? ");
	}
	if (codiceMercatoUso != null) {
	    sql.append(" AND MERCATI_USO.ID = ?");
	}
	if (mercatiD != null && mercatiD.getAttivitaTransient() != null && mercatiD.getAttivitaTransient().getId() != null
		&& StringUtils.isNotBlank(mercatiD.getAttivitaTransient().getId().getCodiceistat())) {
	    sql.append(" AND MERCEOLOGIA.FKCODICEATTIVITAISTAT = ? ");
	    if (mercatiD.getAttivataAmmessaNonAmmessaTransient() != null) {
		sql.append(" AND MERCEOLOGIA.FLAG_CONSENTITO = ? ");
	    }
	}
	// SEZIONE ORDINAMAENTO
	sql.append(" order by " + applyLpadFunction("CODICEPOSTEGGIO", "50", "'0'", d) + " " + OrderTypeEnum.ASC).append(",MERCATI_USO.DESCRIZIONE");
	return sql;
    }

    protected String applySysdateFunction(DialettoEnum dialetto) {

	String functionSysdate = "";
	switch (dialetto) {
	    case MYSQL:
		functionSysdate = "SYSDATE()";
		break;
	    case POSTGRES:
		functionSysdate = "now()";
		break;
	    case ORACLE:
		functionSysdate = "SYSDATE";
		break;
	    case SQLSERVER:
		functionSysdate = "GETDATE()";
		break;
	}
	return functionSysdate;
    }

    protected String applyLpadFunction(String fragment, String padNum, String padChar, DialettoEnum dialetto) {

	StringBuffer result = null;
	switch (dialetto) {
	    case MYSQL:
	    case POSTGRES:
	    case ORACLE:
		// lpad( string1, padded_length, [ pad_string ] )
		result = new StringBuffer(" lpad(").append(fragment).append(", ");
		result.append(padNum).append(",").append(padChar);
		result.append(")");
		break;
	    case SQLSERVER:
		result = new StringBuffer(" replicate(");
		// replicate([carattere da ripetere], ([lunghezza] - LEN([campo]))
		result.append(padChar).append(", (").append(padNum).append(" - len(").append(fragment).append(")").append(")");
		result.append(") + ").append(fragment);
		break;
	    default:
		result = new StringBuffer(fragment);
		break;
	}
	return result.toString();
    }

    protected String applyConcatFunction(DialettoEnum dialetto, String separator, String... paramsToConcat) {

	String result = new String();
	switch (dialetto) {
	    case MYSQL:
		// concat(ifnull(param[0],''),ifnull(param[1],''),....)
		result = result.concat("concat(");
		for (String param : paramsToConcat) {
		    result = result.concat("ifnull(").concat(param).concat(",''),");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(",");
		    }
		}
		result = result.substring(0, (result.length() - 1));
		result = result.concat(")");
		break;
	    case POSTGRES:
		// coalesce(param[0],'') || ' ' ||  coalesce(param[0],'') 
		for (String param : paramsToConcat) {
		    result = result.concat("coalesce(").concat(param).concat(",'') || ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" || ");
		    }
		}
		result = result.substring(0, (result.length() - 3));
		break;
	    case ORACLE:
		// param[0] || param[1] || ...
		for (String param : paramsToConcat) {
		    result = result.concat(param).concat(" || ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" || ");
		    }
		}
		result = result.substring(0, (result.length() - 3));
		break;
	    case SQLSERVER:
		// param[0] + param[1] + ...
		for (String param : paramsToConcat) {
		    result = result.concat(param).concat(" + ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" + ");
		    }
		}
		result = result.substring(0, (result.length() - 2));
		break;
	    default:
		break;
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer findPosizioneMaxByMercato(Integer codice) {

	DetachedCriteria det = getIdcomuneCriteria();
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("posizione"));
	det.setProjection(projectionList);
	List<Integer> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    if (list.get(0) != null) {
		return list.get(0);
	    }
	}
	return 0;
    }

    @Override
    public void exportModalitaPentaho(MercatiD mercatiD, Esportazioni esportazioni, String email, TipicontestoesportazioniEnum posteggiMercato,
	    boolean isInvioMail) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	List<PosteggioMercatiHelper> _mercatidList = this.findMercatiDWithConcessioniSQL(mercatiD.getMercati().getId().getCodice(), mercatiD, false,
		null);
	for (PosteggioMercatiHelper posteggioMercatiHelper : _mercatidList) {
	    String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	    String sql = "INSERT INTO " + schema + ".tmp_esportazioni (IDCOMUNE, SESSIONID, CODICE, CODICECOMUNE, DATA) VALUES (?,?,?,?,?)";
	    SQLQuery sqlQuery = getSession().createSQLQuery(sql);
	    sqlQuery = sqlQuery.addScalar("IDCOMUNE", Hibernate.STRING).addScalar("SESSIONID", Hibernate.STRING)
		    .addScalar("CODICE", Hibernate.INTEGER).addScalar("CODICECOMUNE", Hibernate.STRING).addScalar("DATA", Hibernate.DATE);
	    sqlQuery.setString(0, posteggioMercatiHelper.getPosteggioInfoHelper().getId().getIdcomune());
	    sqlQuery.setString(1, ORMHelper.getToken());
	    sqlQuery.setInteger(2, new Integer(posteggioMercatiHelper.getPosteggioInfoHelper().getId().getCodice()));
	    // campo obbligatorio, metto idcomune
	    sqlQuery.setString(3, posteggioMercatiHelper.getPosteggioInfoHelper().getId().getIdcomune());
	    // campo obbligatorio, metto data di sistema
	    sqlQuery.setDate(4, new Date());
	    sqlQuery.executeUpdate();
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiDDTO> findByMercatiDDTO(MercatiDFilter filter) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	addAliasAndProjection(criteria);
	// projection
	criteria.add(Restrictions.eq("_mercati.id.codice", filter.getCodiceMercato()));
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatiDDTO.class));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatiDDTO findById(Integer codiceposteggio) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	addAliasAndProjection(criteria);
	// restriction applicabili verificare "addAliasAndProjection"
	criteria.add(Restrictions.eq("id.codice", codiceposteggio));
	List<MercatiDDTO> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    private DetachedCriteria addAliasAndProjection(DetachedCriteria criteria) {

	criteria.createAlias(PROP_MERCATI, "_mercati");
	criteria.createAlias("stradario", "_stradario", DetachedCriteria.LEFT_JOIN);
	// projection
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property(PROP_CODICEPOSTEGGIO), "CODICEPOSTEGGIO");
	//
	plist.add(Projections.property("larghezza"), "LARGHEZZA");
	plist.add(Projections.property("lunghezza"), "LUNGHEZZA");
	plist.add(Projections.property("superficie"), "SUPERFICIE");
	plist.add(Projections.property(PROP_DISABILITATO), "DISABILITATO");
	plist.add(Projections.property("_stradario.id.codice"), "STRADARIODTO_ID_CODICE");
	plist.add(Projections.property("_stradario.prefisso"), "STRADARIODTO_PREFISSO");
	plist.add(Projections.property("_stradario.descrizione"), "STRADARIODTO_DESCRIZIONE");
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatiDDTO.class));
	return criteria;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findByCodiceByMercato(Integer codicemercato, PosteggiEnum posteggiEnum) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias(PROP_MERCATI, "_mercati");
	criteria.add(Restrictions.eq("_mercati.id.codice", codicemercato));
	if (posteggiEnum.equals(PosteggiEnum.ACTIVE)) {
	    criteria.add(Restrictions.or(Restrictions.eq(PROP_DISABILITATO, false), Restrictions.isNull(PROP_DISABILITATO)));
	}
	if (posteggiEnum.equals(PosteggiEnum.DISABLED)) {
	    criteria.add(Restrictions.eq(PROP_DISABILITATO, true));
	}
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"));
	criteria.setProjection(plist);
	List<Integer> l = (List<Integer>) getHibernateTemplate().findByCriteria(criteria);
	return l;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PosteggioMercatoBean> findByIdGiornata(Integer idGiornata) {

	StringBuilder sql = new StringBuilder();
	sql.append("SELECT ");
	sql.append("CASE WHEN FK_AUTORIZZAZIONI_ID IS NULL THEN 0 ELSE 1 END AS OCCUPATO, ");
	sql.append("MERCATI_D.IDPOSTEGGIO AS ID, ");
	sql.append("MERCATI_D.CODICEPOSTEGGIO AS NUMERO, ");
	sql.append("MERCATI_D.COORDINATE ");
	sql.append("FROM MERCATIPRESENZE_D INNER JOIN MERCATI_D ON ");
	sql.append("MERCATI_D.IDCOMUNE=MERCATIPRESENZE_D.IDCOMUNE AND ");
	sql.append("MERCATI_D.IDPOSTEGGIO=MERCATIPRESENZE_D.FKIDPOSTEGGIO ");
	sql.append("WHERE MERCATIPRESENZE_D.IDCOMUNE=? ");
	sql.append("AND MERCATIPRESENZE_D.FKIDTESTATA = ? ");
	sql.append("ORDER BY MERCATI_D.CODICEPOSTEGGIO ASC");
	SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
	sqlQuery.addScalar("occupato", Hibernate.BOOLEAN);
	sqlQuery.addScalar("id", Hibernate.INTEGER);
	sqlQuery.addScalar("numero", Hibernate.STRING);
	sqlQuery.addScalar("coordinate", Hibernate.STRING);
	sqlQuery.setString(0, ORMHelper.getIdcomune());
	sqlQuery.setInteger(1, idGiornata);
	sqlQuery.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(PosteggioMercatoBean.class));
	return sqlQuery.list();
    }
}
