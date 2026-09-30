package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.StatiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Dyn2Massive;
import it.gruppoinit.pal.gp.core.domain.Dyn2MassiveFiltri;
import it.gruppoinit.pal.gp.core.domain.Dyn2MassiveFiltriId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Massiveschede;
import it.gruppoinit.pal.gp.core.domain.Dyn2MassiveschedeId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.RigaElaborazioneModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.SchedaDinamicaModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.TestataModel;

@SuppressWarnings("rawtypes")
@Repository
public class ElaborazioneMassivaDAOImpl extends BaseDAOImpl implements IElaborazioneMassivaDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Autowired
    private StatiistanzaDAO statiistanzaDAO;
    @Autowired
    private AlberoprocDAO alberoprocDAO;

    @Override
    public List<TestataModel> findAll() {

	String sqlQuery = "select id, descrizione, data_inizio as datainizio, data_fine as datafine,flg_eliminata as flgeliminata from dyn2_massive where idcomune=? and software=? ";
	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(
		((SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory()).getDialect().toString());
	switch (dialetto) {
	    case MYSQL:
		sqlQuery = sqlQuery + " order by COALESCE(data_fine, STR_TO_DATE('2099-12-31','%Y-%m-%d')) desc, descrizione, id";
		break;
	    case ORACLE:
		sqlQuery = sqlQuery + " order by COALESCE(data_fine, TO_DATE('2099-12-31','YYYY-MM-DD')) desc, descrizione, id";
		break;
	    default:
		sqlQuery = sqlQuery + " order by descrizione, id";
		break;
	}
	SQLQuery q = getSession().createSQLQuery(sqlQuery);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("datafine", Hibernate.TIMESTAMP);
	q.addScalar("datainizio", Hibernate.TIMESTAMP);
	q.addScalar("flgeliminata", Hibernate.BOOLEAN);
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(TestataModel.class));
	return q.list();
    }

    @Override
    public Dyn2Massive findMassivaById(Integer id) {

	return (Dyn2Massive) getById(Dyn2Massive.class, id);
    }

    @Override
    public List<Dyn2Massiveschede> getSchedeByElaborazione(int idElaborazione) {

	String hql = "from Dyn2Massiveschede d2m where d2m.id.idcomune=? and d2m.id.fkidelaborazione=? order by d2m.ordine asc";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idElaborazione);
	return q.list();
    }

    @Override
    public List<Dyn2MassiveFiltri> getFiltriByElaborazione(int idElaborazione) {

	String hql = "from Dyn2MassiveFiltri d2m where d2m.id.idcomune=? and d2m.id.fkidelaborazione=? order by d2m.id.filtro asc";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idElaborazione);
	return q.list();
    }

    @Override
    public Set<RigaElaborazioneModel> getRigheForElaborazione(int idElaborazione, Integer firstResult, Integer maxResults) {

	DialettoEnum dialetto = DialettoEnum.fromHibernateDialect(
		((SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory()).getDialect().toString());
	String richiedente = "";
	switch (dialetto) {
	    case MYSQL:
		richiedente = "concat_ws(' ', richiedente.nominativo , richiedente.nome) ";
		break;
	    case ORACLE:
		richiedente = "richiedente.nominativo || ' ' || richiedente.nome";
		break;
	    case SQLSERVER:
	    case POSTGRES:
		richiedente = "concat(richiedente.nominativo,' ', richiedente.nome)";
		break;
	}
	String sql = "select" + //
		" istanze.codiceistanza," + //
		" istanze.numeroistanza," + //
		" istanze.data as dataistanza," + //
		richiedente +
		" as richiedente," + //
		" azienda.nominativo as azienda," + //
		" comuni.comune," + //
		" alberoproc.descrizione_completa as  intervento," + //
		" dyn2_massiveistanze.statoesecuzione as statoelaborazione," + //
		" tipisoggetto.tiposoggetto as inqualita," + //
		" dyn2_massiveistanze.log " + //
		"from " +
		" dyn2_massiveistanze" + //
		"  inner join istanze on " + //
		"    istanze.idcomune = dyn2_massiveistanze.idcomune and" + //
		"    istanze.codiceistanza = dyn2_massiveistanze.codiceistanza" + //
		"  inner join anagrafe richiedente on " + //
		"    richiedente.idcomune = istanze.idcomune and" + //
		"    richiedente.codiceanagrafe = istanze.codicerichiedente" + //
		"  left  join anagrafe azienda on " + //
		"    azienda.idcomune = istanze.idcomune and" + //
		"    azienda.codiceanagrafe = istanze.codicetitolarelegale" + //
		"  inner join alberoproc on" + //
		"    istanze.idcomune = alberoproc.idcomune and" + //
		"    istanze.codiceinterventoproc = alberoproc.sc_id" + //
		"  inner join comuni on " + //
		"    istanze.codicecomune = comuni.codicecomune" + //
		"  left join tipisoggetto on " + // 
		"    tipisoggetto.idcomune=istanze.idcomune and " + // 
		"    tipisoggetto.codicetiposoggetto=istanze.fkcodicesoggetto " + // 
		"where " + //
		" dyn2_massiveistanze.idcomune=? and " + //
		" dyn2_massiveistanze.fkidelaborazione=? " + //
		"order by " + //
		" istanze.data desc";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idElaborazione);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("numeroistanza", Hibernate.STRING);
	q.addScalar("dataistanza", Hibernate.TIMESTAMP);
	q.addScalar("richiedente", Hibernate.STRING);
	q.addScalar("azienda", Hibernate.STRING);
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("intervento", Hibernate.STRING);
	q.addScalar("statoelaborazione", Hibernate.STRING);
	q.addScalar("inqualita", Hibernate.STRING);
	q.addScalar("log", Hibernate.STRING);
	if (null != firstResult) {
	    q.setFirstResult(firstResult);
	}
	if (null != maxResults) {
	    q.setMaxResults(maxResults);
	}
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(RigaElaborazioneModel.class));
	return new HashSet<RigaElaborazioneModel>(q.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public int creaElaborazione(CreaTestataRequest request) {

	Dyn2Massive t = new Dyn2Massive();
	t.setDescrizione(request.getDescrizione());
	Software s = new Software();
	s.setCodice(ORMHelper.getSoftware());
	t.setSoftware(s);
	this.saveEntity(t);
	this.flush();
	this.commit();
	int idElaborazione = t.getId().getCodice();
	salvafiltri(request, idElaborazione);
	this.flush();
	this.commit();
	creaRighePerElaborazione(idElaborazione, request);
	this.flush();
	this.commit();
	return idElaborazione;
    }

    private void creaRighePerElaborazione(int idElaborazione, CreaTestataRequest request) {

	QueryFillDyn2MassiveIstanzeHelper qih = new QueryFillDyn2MassiveIstanzeHelper(alberoprocDAO, request, statiistanzaDAO, idElaborazione);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	//..	
	qih.setFilterValues(q);
	q.executeUpdate();
	//qih.setScalarProperties(q);
	//q.setResultTransformer(Transformers.aliasToBean(IstanzeListHelper.class));
	//return (List<Integer>) q.list();
	/*
	List<Integer> codiciIstanza = findRighePerElaborazione(request);
	for (Integer ci : codiciIstanza) {
	    Dyn2Massiveistanze d2 = new Dyn2Massiveistanze();
	    Dyn2MassiveistanzeId id = new Dyn2MassiveistanzeId(idElaborazione, ci);
	    d2.setId(id);
	    d2.setStatoesecuzione(EsitoElaborazioneMassivaSchedeEnum.PRONTA_PER_ELABORAZIONE.value());
	    saveEntity(d2);
	}
	*/
    }

    private void salvafiltri(CreaTestataRequest request, int idElaborazione) {

	salvaInterventi(request, idElaborazione);
	salvaRegistri(request, idElaborazione);
	salvaStato(request, idElaborazione);
	salvaSchede(request, idElaborazione);
    }

    private void salvaSchede(CreaTestataRequest request, int idElaborazione) {

	Set<SchedaDinamicaModel> schedeDaElaborare = request.getSchedeDaElaborare();
	for (SchedaDinamicaModel schedaDinamicaModel : schedeDaElaborare) {
	    Dyn2Massiveschede s = new Dyn2Massiveschede();
	    Dyn2MassiveschedeId id = new Dyn2MassiveschedeId(idElaborazione, schedaDinamicaModel.getIdScheda());
	    s.setId(id);
	    s.setOrdine(schedaDinamicaModel.getOrdine());
	    saveEntity(s);
	}
    }

    private void salvaStato(CreaTestataRequest request, int idElaborazione) {

	String statoIstanza = request.getStatoIstanza();
	if (StringUtils.defaultString(request.getStatoIstanza()).length() == 2) {
	    StatiistanzaId sid = new StatiistanzaId(ORMHelper.getIdcomune(), ORMHelper.getSoftware(), statoIstanza);
	    Statiistanza s = statiistanzaDAO.findById(sid);
	    if (s != null) {
		statoIstanza = s.getStato();
	    }
	}
	if (StringUtils.isNotBlank(statoIstanza)) {
	    Dyn2MassiveFiltri filtroStatoIstanza = new Dyn2MassiveFiltri();
	    Dyn2MassiveFiltriId idFiltro = new Dyn2MassiveFiltriId(idElaborazione, ElaborazioniMassiveFiltriEnum.STATO_ISTANZA.name());
	    filtroStatoIstanza.setId(idFiltro);
	    filtroStatoIstanza.setValore(StringUtils.left(statoIstanza, 4000));
	    saveEntity(filtroStatoIstanza);
	}
    }

    private void salvaRegistri(CreaTestataRequest request, int idElaborazione) {

	Set<Integer> registri = request.getRegistri();
	String[] registriDesc = new String[registri.size()];
	int i = 0;
	for (Integer scId : registri) {
	    Tipologiaregistri r = (Tipologiaregistri) getById(Tipologiaregistri.class, scId);
	    registriDesc[i] = r.getTrDescrizione();
	    i++;
	}
	int a = 1;
	if (registriDesc.length > 0) {
	    String valore = StringUtils.join(registriDesc, ',');
	    Dyn2MassiveFiltri filtroRegistri = new Dyn2MassiveFiltri();
	    Dyn2MassiveFiltriId idFiltro = new Dyn2MassiveFiltriId(idElaborazione, ElaborazioniMassiveFiltriEnum.REGISTRI.name() + "-" + a++);
	    filtroRegistri.setId(idFiltro);
	    filtroRegistri.setValore(StringUtils.left(valore, 4000));
	    saveEntity(filtroRegistri);
	}
    }

    private void salvaInterventi(CreaTestataRequest request, int idElaborazione) {

	Set<Integer> interventi = request.getInterventi();
	String[] interventiDesc = new String[interventi.size()];
	String[] interventiCod = new String[interventi.size()];
	int i = 0;
	for (Integer scId : interventi) {
	    Alberoproc ap = (Alberoproc) getById(Alberoproc.class, scId);
	    interventiDesc[i] = StringUtils.defaultIfEmpty(ap.getDescrizioneCompleta(), ap.getScDescrizione());
	    interventiCod[i] = String.valueOf(scId);
	    i++;
	}
	int a = 1;
	if (interventiDesc.length > 0) {
	    String valore = StringUtils.join(interventiDesc, ',');
	    Dyn2MassiveFiltri filtroInterventi = new Dyn2MassiveFiltri();
	    Dyn2MassiveFiltriId idFiltro = new Dyn2MassiveFiltriId(idElaborazione, ElaborazioniMassiveFiltriEnum.INTERVENTI.name() + "-" + a++);
	    filtroInterventi.setId(idFiltro);
	    filtroInterventi.setValore(StringUtils.left(valore, 4000));
	    saveEntity(filtroInterventi);
	    valore = StringUtils.join(interventiCod, ',');
	    filtroInterventi = new Dyn2MassiveFiltri();
	    idFiltro = new Dyn2MassiveFiltriId(idElaborazione, ElaborazioniMassiveFiltriEnum.CODICI_INTERVENTO.name());
	    filtroInterventi.setId(idFiltro);
	    filtroInterventi.setValore(StringUtils.left(valore, 4000));
	    saveEntity(filtroInterventi);
	}
    }

    @Override
    public List<Integer> findRighePerElaborazione(CreaTestataRequest request) {

	QueryRicercaRigheHelper qih = new QueryRicercaRigheHelper(alberoprocDAO, request, statiistanzaDAO);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	//..	
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	//q.setResultTransformer(Transformers.aliasToBean(IstanzeListHelper.class));
	return (List<Integer>) q.list();
    }

    @Override
    public void eliminaElaborazioniMassiveRiga(Integer idElaborazione) {

	String sqlQuery = "update dyn2_massive set flg_eliminata=? where idcomune=? and software=? and id=? ";
	SQLQuery q = getSession().createSQLQuery(sqlQuery);
	q.setInteger(0, 1);
	q.setString(1, ORMHelper.getIdcomune());
	q.setString(2, ORMHelper.getSoftware());
	q.setInteger(3, idElaborazione);
	q.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IdentificativoDescrizioneBean> findSchedeDinamiche(CreaTestataRequest filtri) {

	List<Integer> istanze = this.findRighePerElaborazione(filtri);
	if (istanze.isEmpty()) {
	    return new ArrayList<IdentificativoDescrizioneBean>();
	}
	String sql = "select  " + //
		"dyn2_modellit.id as id, " + // 
		"dyn2_modellit.descrizione  as descrizione " + //
		" from istanzedyn2modellit inner join  " + //
		"dyn2_modellit on  " + //
		"dyn2_modellit.idcomune=istanzedyn2modellit.idcomune and " + //
		"dyn2_modellit.id=istanzedyn2modellit.fk_d2mt_id  " + //
		" where istanzedyn2modellit.idcomune=? " + //
		"  and CONDIZIONE_IN " + //
		" group by dyn2_modellit.id,dyn2_modellit.descrizione " + //
		" order by dyn2_modellit.descrizione";
	String qMarks = "";
	int num = istanze.size();
	if (num < 1000) {
	    String qm = StringUtils.repeat("?,", num);
	    qm = qm.substring(0, qm.length() - 1);
	    qMarks = " istanzedyn2modellit.codiceistanza in  (" + qm + ")";
	} else {
	    Double cicli = Double.valueOf(num) / 1000;
	    int cicliDaMille = cicli.intValue();
	    int resto = num - (cicliDaMille * 1000);
	    qMarks += " ( 1=2 ";
	    for (int i = 0; i < cicliDaMille; i++) {
		String qm = StringUtils.repeat("?,", 1000);
		qm = qm.substring(0, qm.length() - 1);
		qMarks += " or istanzedyn2modellit.codiceistanza in  (" + qm + ")";
	    }
	    if (resto > 0) {
		String qm = StringUtils.repeat("?,", resto);
		qm = qm.substring(0, qm.length() - 1);
		qMarks += " or istanzedyn2modellit.codiceistanza in (" + qm + ")";
	    }
	    qMarks += ")";
	}
	sql = sql.replace("CONDIZIONE_IN", qMarks);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	for (Integer codiceistanza : istanze) {
	    q.setInteger(pos++, codiceistanza);
	}
	q.setResultTransformer(Transformers.aliasToBean(IdentificativoDescrizioneBean.class));
	return q.list();
    }

    @Override
    public List<Dyn2MassiveFiltri> getFiltriByElaborazione(int idTestata, ElaborazioniMassiveFiltriEnum tipoFiltro) {

	String hql = "from Dyn2MassiveFiltri d2m where d2m.id.idcomune=? and d2m.id.fkidelaborazione=? and d2m.id.filtro=? order by d2m.id.filtro asc";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.setString(2, tipoFiltro.name());
	return q.list();
    }
}
