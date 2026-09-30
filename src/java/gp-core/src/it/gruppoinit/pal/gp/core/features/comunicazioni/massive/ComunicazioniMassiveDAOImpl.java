package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollMassiveT;
import it.gruppoinit.pal.gp.core.domain.CommedilizieMassiveT;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MassiveParametri;
import it.gruppoinit.pal.gp.core.domain.MassiveTAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveTFirmatari;
import it.gruppoinit.pal.gp.core.domain.MassiveTLettere;
import it.gruppoinit.pal.gp.core.domain.MassiveTProtMetadati;
import it.gruppoinit.pal.gp.core.domain.MassiveTProtocollo;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametroConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettaglioLetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ResocontoOperazioniMassive;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@SuppressWarnings("rawtypes")
@Repository
public class ComunicazioniMassiveDAOImpl extends BaseDAOImpl implements IComunicazioniMassiveDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @SuppressWarnings("unchecked")
    public <T /*extends HasPkId*/> void save(T entity) {

	_validateEntityForInsertOrUpdate2(entity);
	getHibernateTemplate().merge(entity);
    }

    @Override
    public void insert(MassiveTestata testata) {

	this.save(testata);
    }

    @Override
    public void insert(MassiveParametri parametro) {

	this.save(parametro);
    }

    @Override
    public void insertAllegatoFisso(int idTestata, int codiceOggetto) {

	MassiveTAllegati massiveTAllegati = new MassiveTAllegati();
	MassiveTestata massiveTestata = (MassiveTestata) getById(MassiveTestata.class, idTestata);
	massiveTAllegati.setMassiveTestata(massiveTestata);
	Oggetti oggetti = (Oggetti) this.getById(Oggetti.class, codiceOggetto);
	massiveTAllegati.setOggetti(oggetti);
	this.save(massiveTAllegati);
    }

    @Override
    public void insertLettera(int idTestata, int codiceLettera) {

	MassiveTLettere massiveTLettere = new MassiveTLettere();
	MassiveTestata massiveTestata = (MassiveTestata) getById(MassiveTestata.class, idTestata);
	massiveTLettere.setMassiveTestata(massiveTestata);
	Letteretipo letteretipo = (Letteretipo) getById(Letteretipo.class, codiceLettera);
	massiveTLettere.setLetteretipo(letteretipo);
	this.save(massiveTLettere);
    }

    @Override
    public void insertSoggettoFirmatario(int idTestata, int idSoggetto) {

	MassiveTFirmatari massiveTFirmatari = new MassiveTFirmatari();
	MassiveTestata massiveTestata = (MassiveTestata) getById(MassiveTestata.class, idTestata);
	massiveTFirmatari.setMassiveTestata(massiveTestata);
	Responsabili responsabili = (Responsabili) getById(Responsabili.class, idSoggetto);
	massiveTFirmatari.setResponsabili(responsabili);
	this.save(massiveTFirmatari);
    }

    @Override
    public MassiveTestata getTestataById(int idTestata) {

	return (MassiveTestata) getById(MassiveTestata.class, new PkId(idTestata));
    }

    @Override
    public List<ParametroConfigurazioneComunicazione> getParametriByIdTestata(int idTestata) {

	String sql = "select chiave,valore from massive_parametri where idcomune=? and fkid_testata=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveParametri.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("chiave", Hibernate.STRING);
	q.addScalar("valore", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(ParametroConfigurazioneComunicazione.class));
	return (List<ParametroConfigurazioneComunicazione>) q.list();
    }

    @Override
    public List<AllegatoComunicazione> getAllegatiFissiByIdTestata(int idTestata) {

	String sql = "select codiceOggetto from massive_t_allegati where idcomune=? and fkid_testata=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveTAllegati.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("codiceOggetto", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(AllegatoComunicazione.class));
	return (List<AllegatoComunicazione>) q.list();
    }

    @Override
    public List<LetteraComunicazione> getLettereComunicazioneByIdTestata(int idTestata) {

	// HQL QUERY
	// TODO attenzione quando siamo qui che lo dobbiamo rivedere
	//	SELECT codiceoggetto from MASSIVE_T_LETTERE inner join 
	//	letteretipo on MASSIVE_T_LETTERE.idcomune=letteretipo.idcomune and
	//	MASSIVE_T_LETTERE.fkid_lettera_tipo=LETTERETIPO.CODICELETTERA
	//	WHERE MASSIVE_T_LETTERE.IDCOMUNE='E256' aND MASSIVE_T_LETTERE.FKID_TESTATA=1
	// massive _LETTERE
	String sql = "select codicelettera as codiceLettera from massive_t_lettere inner join letteretipo" +
		" on massive_t_lettere.idcomune=letteretipo.idcomune and massive_t_lettere.fkid_lettera_tipo=letteretipo.codicelettera " +
		"where massive_t_lettere.idcomune=? and massive_t_lettere.fkid_testata=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveTLettere.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("codiceLettera", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(LetteraComunicazione.class));
	return (List<LetteraComunicazione>) q.list();
    }

    @Override
    public List<DettaglioLetteraComunicazione> getDettaglioLettereComunicazioneByIdTestata(int idTestata) {

	String sql = "select " +
		" letteretipo.descrizione as descrizioneLettera," +
		" letteretipo.codiceoggetto as codiceOggetto, " +
		" letteretipo.codicelettera as codiceLettera " +
		" from " +
		" massive_t_lettere " +
		"  inner join letteretipo on " +
		"   massive_t_lettere.idcomune = letteretipo.idcomune and " +
		"   massive_t_lettere.fkid_lettera_tipo = letteretipo.codicelettera " +
		"where " +
		" massive_t_lettere.idcomune=? and " +
		" massive_t_lettere.fkid_testata=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveTLettere.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("descrizioneLettera", Hibernate.STRING);
	q.addScalar("codiceOggetto", Hibernate.INTEGER);
	q.addScalar("codiceLettera", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(DettaglioLetteraComunicazione.class));
	return (List<DettaglioLetteraComunicazione>) q.list();
    }

    @Override
    public List<Integer> getFirmatariByIdTestata(int idTestata) {

	String sql = "select codiceresponsabile from massive_t_firmatari where idcomune=? and fkid_testata=? order by codiceresponsabile";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveTFirmatari.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("codiceresponsabile", Hibernate.INTEGER);
	return (List<Integer>) q.list();
    }

    @Override
    public List<Integer> findByIdBollettazione(Integer idBollettazione) {

	String sql = "select boll_massive_t.fkid_testata " +
		"from boll_massive_t " +
		"inner join massive_testata on massive_testata.idcomune = boll_massive_t.idcomune and massive_testata.id = boll_massive_t.fkid_testata " +
		"where boll_massive_t.idcomune = ? " +
		"and boll_massive_t.fkid_bollettazione = ? " +
		"and massive_testata.data_cancellazione is null " +
		"order by massive_testata.data_comunicazione desc ";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollMassiveT.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idBollettazione);
	q.addScalar("fkid_testata", Hibernate.INTEGER);
	return (List<Integer>) q.list();
    }

    @Override
    public List<ResocontoOperazioniMassive> findResocontoOperazioniMassiveById(Integer idTestata) {

	String sql = "select massive_dettaglio.ultimo_stato_completato as titoloresoconto, " +
		"count(massive_dettaglio.id) as totaleoperazionieseguite " +
		"from massive_testata " +
		"inner join massive_dettaglio on massive_dettaglio.idcomune = massive_testata.idcomune and " +
		"massive_dettaglio.fkid_testata = massive_testata.id " +
		"where massive_testata.idcomune = ? and massive_testata.id = ? " +
		"group by massive_dettaglio.ultimo_stato_completato";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveTestata.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("titoloResoconto", Hibernate.STRING);
	q.addScalar("totaleOperazioniEseguite", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(ResocontoOperazioniMassive.class));
	List<ResocontoOperazioniMassive> list = new ArrayList<ResocontoOperazioniMassive>();
	list = (List<ResocontoOperazioniMassive>) q.list();
	int size = list.size();
	if (size > 0) {
	    for (ResocontoOperazioniMassive rom : list) {
		rom.setTotaleOperazioniRichieste(size);
	    }
	}
	return list;
    }

    @Override
    public List<String> getDescrizioneFirmatariByIdTestata(int idTestata) {

	String sql = "select " +
		" responsabili.responsabile " +
		"from " +
		" massive_t_firmatari " +
		"  inner join responsabili on " +
		"    massive_t_firmatari.idcomune = responsabili.idcomune and " +
		"    massive_t_firmatari.codiceresponsabile = responsabili.codiceresponsabile " +
		"where " +
		" massive_t_firmatari.idcomune=? and " +
		" massive_t_firmatari.fkid_testata=? " +
		"order by " +
		" responsabili.responsabile asc";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveTFirmatari.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("responsabile", Hibernate.STRING);
	return (List<String>) q.list();
    }

    @Override
    public void insertParametriProtocollo(MassiveTProtocollo parametriProtocollo) {

	//1. Estrapolo la lista dei metadati
	Set<MassiveTProtMetadati> metadati = new HashSet<MassiveTProtMetadati>(0);
	if (parametriProtocollo.getMetadati() != null && !parametriProtocollo.getMetadati().isEmpty()) {
	    metadati.addAll(parametriProtocollo.getMetadati());
	    parametriProtocollo.setMetadati(null);
	}
	this.save(parametriProtocollo);
	//2. Salvo la lista dei metadati
	if (!metadati.isEmpty()) {
	    for (MassiveTProtMetadati metadato : metadati) {
		metadato.setMassiveTestata(parametriProtocollo.getMassiveTestata());
		metadato.setMassiveTProtocollo(parametriProtocollo);
		this.save(metadato);
	    }
	}
    }

    @Override
    public List<ParametriProtocolloPerEnte> getParametriProtocollazione(int idTestata) {

	String hql = "from MassiveTProtocollo md where md.id.idcomune=? and md.massiveTestata.id.codice=?";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	List<MassiveTProtocollo> list = q.list();
	List<ParametriProtocolloPerEnte> ret = new ArrayList<ParametriProtocolloPerEnte>();
	for (MassiveTProtocollo m : list) {
	    ret.add(ParametriProtocolloPerEnte.fromMassiveTProtocollo(m));
	}
	return ret;
    }

    @Override
    public void eliminaMassiva(int idTestata, Integer codiceResponsabile, Date dataCancellazione) {

	if (codiceResponsabile == null) {
	    throw new IllegalArgumentException("Responsabile non può essere nullo");
	}
	Responsabili r = (Responsabili) getById(Responsabili.class, codiceResponsabile);
	if (r == null) {
	    throw new IllegalArgumentException("Responsabile con codice " + codiceResponsabile + " non trovato");
	}
	MassiveTestata t = (MassiveTestata) getById(MassiveTestata.class, idTestata);
	t.setDataCancellazione(dataCancellazione);
	t.setResponsabileCancellazione(r);
	this.save(t);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findByIdCommissioni(Integer idCommissioni) {

	String sql = "select commedilizie_massive_t.fkid_testata from commedilizie_massive_t " +
		"inner join massive_testata on massive_testata.idcomune = commedilizie_massive_t.idcomune and massive_testata.id = commedilizie_massive_t.fkid_testata " +
		"where commedilizie_massive_t.idcomune = ? and commedilizie_massive_t.fkid_commedt_id  = ? " +
		"and massive_testata.data_cancellazione is null order by massive_testata.data_comunicazione desc ";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveT.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idCommissioni);
	q.addScalar("fkid_testata", Hibernate.INTEGER);
	return (List<Integer>) q.list();
    }

    @Override
    public ContestoComunicazioneEnum getContestoMassiva(int idTestata) {

	if (contaPerCommissioni(idTestata) > 0) {
	    return ContestoComunicazioneEnum.COMMISSIONI;
	} else if (contaPerBollettazione(idTestata) > 0) {
	    return ContestoComunicazioneEnum.BOLLETTAZIONE;
	} else if (contaPerIstanze(idTestata) > 0){
	    return ContestoComunicazioneEnum.ISTANZE;
	} else if (contaPerMercati(idTestata) > 0){
	    return ContestoComunicazioneEnum.MERCATI;
	}
	throw new BusinessValidationException("La comunicazione massiva con MASSIVE_TESTATA.id " + idTestata + " non è legata ad un contesto valido");
    }

    private int contaPerCommissioni(int idTestata) {

	String sql = "select count(*) as conta from commedilizie_massive_t where commedilizie_massive_t.idcomune=? and commedilizie_massive_t.fkid_testata=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveT.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("conta", Hibernate.INTEGER);
	List list = q.list();
	if (!list.isEmpty()) {
	    return ((Integer) list.get(0)).intValue();
	}
	return 0;
    }

    private int contaPerBollettazione(int idTestata) {

	String sql = "select count(*) as conta from boll_massive_t where boll_massive_t.idcomune=? and boll_massive_t.fkid_testata=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveT.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("conta", Hibernate.INTEGER);
	List list = q.list();
	if (!list.isEmpty()) {
	    return ((Integer) list.get(0)).intValue();
	}
	return 0;
    }
    
    private int contaPerIstanze(int idTestata) {

	String sql = "select count(*) as conta from istanze_massive_t where istanze_massive_t.idcomune=? and istanze_massive_t.fkid_testata=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveT.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("conta", Hibernate.INTEGER);
	List list = q.list();
	if (!list.isEmpty()) {
	    return ((Integer) list.get(0)).intValue();
	}
	return 0;
    }
    
    private int contaPerMercati(int idTestata) {

	String sql = "select count(*) as conta from mercati_massive_t where mercati_massive_t.idcomune=? and mercati_massive_t.fkid_testata=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveT.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.addScalar("conta", Hibernate.INTEGER);
	List list = q.list();
	if (!list.isEmpty()) {
	    return ((Integer) list.get(0)).intValue();
	}
	return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findByIdMercato(Integer codicemercato) {

	String sql = "select " + //
		" mercatipresenze_t_massive.fkid_massive_testata " + //
		"from" + //
		" mercatipresenze_t_massive" + //
		"  inner join massive_testata on" + //
		"   mercatipresenze_t_massive.idcomune = massive_testata.idcomune and" + //
		"   mercatipresenze_t_massive.fkid_massive_testata = massive_testata.id" + //
		"  inner join mercatipresenze_t on" + //
		"   mercatipresenze_t_massive.idcomune = mercatipresenze_t.idcomune and" + //
		"   mercatipresenze_t_massive.fkid_mercatipresenze_t = mercatipresenze_t.id and " + //
		"   mercatipresenze_t.fkcodicemercato = ? " + //
		"where" + //
		" mercatipresenze_t_massive.idcomune = ? and" + //
		" massive_testata.data_cancellazione is null " + //
		"order by" +
		" massive_testata.data_comunicazione desc";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveT.class);
	q.setInteger(0, codicemercato);
	q.setString(1, ORMHelper.getIdcomune());
	q.addScalar("fkid_massive_testata", Hibernate.INTEGER);
	return (List<Integer>) q.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdTestataByGen(String sql, Object[] params, String fkscalar) {
	SQLQuery q = getSession().createSQLQuery(sql);
	for(int i=0; i<params.length; i++) {
	    q.setParameter(i, params[i]);
	}
	q.addScalar(fkscalar, Hibernate.INTEGER);
	return (List<Integer>) q.list();
    }
    
    
}
