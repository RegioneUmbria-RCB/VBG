package it.gruppoinit.pal.gp.core.features.anagrafetributaria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AtEsitiErroreTracciato;
import it.gruppoinit.pal.gp.core.domain.AtEsitoErrori;
import it.gruppoinit.pal.gp.core.domain.AtEsitoGruppo;
import it.gruppoinit.pal.gp.core.domain.AtTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ListaEsitiTracciatoBean;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnTribDettaglioRigheTracciato;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser.IATParserProvvedimento;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@SuppressWarnings("rawtypes")
@Repository
public class AnagrafeTributariaDAOImpl extends BaseDAOImpl implements AnagrafeTributariaDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public int countGruppiPerTestata(Integer idTestata) {

	Query query = getSession()
		.createQuery("select count(*) from AtEsitoGruppo ate where ate.id.idcomune=:idcomune and ate.fkidAtTestata=:idtestata");
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("idtestata", idTestata);
	Long count = (Long) query.uniqueResult();
	return count == null ? 0 : count.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> cercaIstanze(IATParserProvvedimento datiProvvedimento) {

	if (datiProvvedimento == null || StringUtils.isBlank(datiProvvedimento.getNumeroProvvedimento())) {
	    return new ArrayList<Integer>(0);
	}
	if (datiProvvedimento.getDataProvvedimento() != null) {
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	DialettoEnum d = DialettoEnum.fromHibernateDialect(sessimpl.getDialect().toString());
	// AUTORIZZAZIONE / DATA
	List<Integer> ret = cercaPerNumeroAutorizzazione(datiProvvedimento.getNumeroProvvedimento(), datiProvvedimento.getDataProvvedimento(), d,
		false);
	if (!ret.isEmpty()) {
	    return ret;
	}
	ret = cercaPerNumeroAutorizzazione(datiProvvedimento.getNumeroProvvedimento(), datiProvvedimento.getDataProvvedimento(), d, true);
	if (!ret.isEmpty()) {
	    return ret;
	}
	// NUMEROPROTOCOLLO / DATA
	ret = cercaPerNumeroIstanza(datiProvvedimento.getNumeroProvvedimento(), datiProvvedimento.getDataProvvedimento(), d, true);
	if (!ret.isEmpty()) {
	    return ret;
	} // NUMEROISTANZA / DATA
	ret = cercaPerNumeroIstanza(datiProvvedimento.getNumeroProvvedimento(), datiProvvedimento.getDataProvvedimento(), d, false);
	if (ret.isEmpty() && StringUtils.isNotBlank(datiProvvedimento.getCfPivaSoggetto())) {
	    // cerco tra i richiedenti dell'istanza
	    // su piva e cf
	    String sql = "select codiceistanza from istanze inner join anagrafe richiedente on "; //
	    sql += " richiedente.idcomune = istanze.idcomune and "; //
	    sql += " richiedente.codiceanagrafe= istanze.codicerichiedente "; //
	    sql += " where "; //
	    sql += " istanze.idcomune=:idcomune and istanze.software=:software and "; //
	    sql += " ( upper(richiedente.codicefiscale)=:cfrich or upper(richiedente.partitaiva)=:pivarich  ) "; //
	    sql += " group by codiceistanza"; //
	    sql += " union all"; //
	    sql += " select codiceistanza from istanze inner join anagrafe titolarelegale on "; //
	    sql += " titolarelegale.idcomune = istanze.idcomune and "; //
	    sql += " titolarelegale.codiceanagrafe = istanze.codicetitolarelegale"; //
	    sql += " where "; //
	    sql += " istanze.idcomune=:idcomuneaz and istanze.software=:softwareaz and"; //
	    sql += " ( upper(titolarelegale.codicefiscale)=:cfaz or upper(titolarelegale.partitaiva)=:pivaaz )"; //
	    sql += " group by codiceistanza";
	    SQLQuery query = getSession().createSQLQuery(sql);
	    query.setString("idcomune", ORMHelper.getIdcomune());
	    query.setString("software", ORMHelper.getSoftware());
	    query.setString("cfrich", datiProvvedimento.getCfPivaSoggetto());
	    query.setString("pivarich", datiProvvedimento.getCfPivaSoggetto());
	    query.setString("idcomuneaz", ORMHelper.getIdcomune());
	    query.setString("softwareaz", ORMHelper.getSoftware());
	    query.setString("cfaz", datiProvvedimento.getCfPivaSoggetto());
	    query.setString("pivaaz", datiProvvedimento.getCfPivaSoggetto());
	    query.addScalar("codiceistanza", Hibernate.INTEGER);
	    return query.list();
	}
	return ret;
    }

    @SuppressWarnings("unchecked")
    private List<Integer> cercaPerNumeroAutorizzazione(String numero, Date data, DialettoEnum dialetto, boolean isSubentro) {

	String table = "autorizzazioni";
	if (isSubentro) {
	    table = "autorizzazioni_subentri";
	}
	boolean dataSettata = false;
	String dataFragment = "";
	if (data != null) {
	    switch (dialetto) {
		case ORACLE:
		case MYSQL:
		    dataSettata = true;
		    dataFragment += " and " + BaseQueryHelper.dateToString_DDMMYYYY("aut.autorizdata", dialetto) + " = :data";
		    break;
		default:
		    throw new NotImplementedException();
	    }
	}
	String sql = "select distinct aut.fkidistanza as codiceistanza from " +
		table +
		" aut inner join istanze on istanze.idcomune=aut.idcomune and istanze.codiceistanza=aut.fkidistanza " + //
		"where aut.idcomune = :idcomune and aut.autoriznumero=:numero " +
		dataFragment +
		" and istanze.software=:software";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setString("numero", numero);
	query.setString("software", ORMHelper.getSoftware());
	if (dataSettata) {
	    query.setString("data", Utilities.formatDate(data, false));
	}
	query.addScalar("codiceistanza", Hibernate.INTEGER);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    private List<Integer> cercaPerNumeroIstanza(String numero, Date data, DialettoEnum dialetto, boolean cercaPerProtocollo) {

	String campoNumero = "numeroistanza";
	String campoData = "data";
	if (cercaPerProtocollo) {
	    campoNumero = "numeroprotocollo";
	    campoData = "dataprotocollo";
	}
	boolean dataSettata = false;
	String dataFragment = "";
	if (data != null) {
	    switch (dialetto) {
		case ORACLE:
		case MYSQL:
		    dataSettata = true;
		    dataFragment += " and " + BaseQueryHelper.dateToString_DDMMYYYY(campoData, dialetto) + " = :data";
		    break;
		default:
		    throw new NotImplementedException();
	    }
	}
	String sql = "select distinct codiceistanza from istanze where istanze.idcomune = :idcomune and software=:software and " +
		campoNumero +
		"=:numero " +
		dataFragment;
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setString("software", ORMHelper.getSoftware());
	query.setString("numero", numero);
	if (dataSettata) {
	    query.setString("data", Utilities.formatDate(data, false));
	}
	query.addScalar("codiceistanza", Hibernate.INTEGER);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ListaEsitiTracciatoBean> findEsitiSalvati(Integer offset, Integer limit) {

	String sql = "select id,descrizione,data_inserimento as data,responsabili.responsabile as responsabile from at_testata inner join responsabili on responsabili.IDCOMUNE = at_testata.IDCOMUNE and responsabili.CODICERESPONSABILE = at_testata.CODICERESPONSABILE where at_testata.idcomune=:idcomune AND at_testata.SOFTWARE=:software order by data_inserimento";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setString("software", ORMHelper.getSoftware());
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("responsabile", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(ListaEsitiTracciatoBean.class));
	if (offset != null) {
	    q.setFirstResult(offset);
	}
	if (limit != null) {
	    q.setMaxResults(limit);
	}
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void eliminaEsito(Integer codice) {

	List<AtEsitoGruppo> gruppi = findByAtTestata(codice, null, null);
	for (AtEsitoGruppo g : gruppi) {
	    List<AtEsitoErrori> errori = findByAtEsitoGruppi(g.getId().getCodice());
	    for (AtEsitoErrori errore : errori) {
		List<AtEsitiErroreTracciato> tracciati = findByAtEsitoErrori(errore.getId().getCodice());
		for (AtEsitiErroreTracciato t : tracciati) {
		    // cancella AtEsitiErroreTracciato
		    delete(t);
		}
		// cancella AtEsitoErrori
		delete(errore);
	    }
	    //	cancella AtEsitoGruppo
	    delete(g);
	}
	//	cancella AtTestata
	delete(getById(AtTestata.class, new PkId(codice)));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AtEsitiErroreTracciato> findByAtEsitoErrori(int fkidAtesitoErrori) {

	String hql = "from AtEsitiErroreTracciato a where a.id.idcomune=:idcomune and a.fkidAtesitoErrori=:iderrori order by a.posNelTracciato";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("iderrori", fkidAtesitoErrori);
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AtEsitoErrori> findByAtEsitoGruppi(int fkidAtEsitoGruppo) {

	String hql = "from AtEsitoErrori a where a.id.idcomune=:idcomune and a.fkidAtEsitoGruppo=:idgruppo order by a.id.codice";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idgruppo", fkidAtEsitoGruppo);
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AtEsitoGruppo> findByAtTestata(int idTestata, Integer offset, Integer limit) {

	String hql = "from AtEsitoGruppo a where a.id.idcomune=:idcomune and a.fkidAtTestata=:idtestata order by a.ordine";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idtestata", idTestata);
	if (null != offset) {
	    q.setFirstResult(offset);
	}
	if (null != limit) {
	    q.setMaxResults(limit);
	}
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnTribDettaglioRigheTracciato> findRigheTracciatoByAtEsitoGruppi(int fkidAtEsitoGruppo) {

	String sql = "select a.pos_nel_tracciato as posizione,a.tracciato_record as riga from at_esito_errori e " + //
		" inner join at_esiti_errore_tracciato a on a.idcomune=e.idcomune and a.fkid_atesito_errori=e.id " + //
		" where e.idcomune=:idcomune and e.fkid_at_esito_gruppo=:idgruppo group by pos_nel_tracciato ,tracciato_record " + //
		" order by a.pos_nel_tracciato";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	SQLQuery q = s.createSQLQuery(sql).addSynchronizedEntityClass(AtEsitiErroreTracciato.class);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idgruppo", fkidAtEsitoGruppo);
	q.addScalar("posizione", Hibernate.INTEGER);
	q.addScalar("riga", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(AnTribDettaglioRigheTracciato.class));
	return q.list();
    }
}
