package it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeDMassive;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTMassive;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class MercatipresenzeTMassiveDAOImpl extends BaseDAOImpl<MercatipresenzeTMassive, PkId> implements IMercatipresenzeTMassiveDAO {

    private IMercatipresenzeDMassiveDAO mercatipresenzeDMassiveDAO;

    @Autowired
    public void setMercatipresenzeDMassiveDAO(IMercatipresenzeDMassiveDAO mercatipresenzeDMassiveDAO) {

	this.mercatipresenzeDMassiveDAO = mercatipresenzeDMassiveDAO;
    }

    @Override
    public Class<MercatipresenzeTMassive> getEntityClass() {

	return MercatipresenzeTMassive.class;
    }

    @Override
    public boolean exists(Integer idTestata) {

	if (idTestata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione per le manifestazioni senza passare l'id della comunicazione");
	}
	String sql = "select count(id) as conteggio from mercatipresenze_t_massive where idcomune = ? and fkid_massive_testata = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MercatipresenzeTMassive.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idTestata);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return new Integer(query.uniqueResult().toString()) > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ISoftwareComuneData> getSoftwareAndComuneDaDettaglioComunicazione(int idDettaglioComunicazione) {

	String sql = "select " + //
		     " mercati.software," + //
		     " mercati.codicecomune as codiceComune" + //
		     "from" + //
		     " mercatipresenze_d_massive" + //
		     "  inner join mercatipresenze_d on " + //
		     "   mercatipresenze_d_massive.idcomune = mercatipresenze_d.idcomune and" + //
		     "   mercatipresenze_d_massive.fkid_mercatipresenze_d = mercatipresenze_d.id" + //
		     "  inner join mercatipresenze_t on " + //
		     "   mercatipresenze_d.idcomune = mercatipresenze_t.idcomune and" + //
		     "   mercatipresenze_d.fkidtestata = mercatipresenze_t.id" + "  inner join mercati on " + //
		     "   mercatipresenze_t.idcomune = mercati.idcomune and" + //
		     "   mercatipresenze_t.fkcodicemercato = mercati.codicemercato " + //
		     "where" + //
		     " mercatipresenze_d_massive.idcomune = ? and" + //
		     " mercatipresenze_d_massive.fkid_massive_dettaglio = ?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDettaglioComunicazione);
	q.setInteger(2, 1); // validata
	q.setResultTransformer(Transformers.aliasToBean(SoftwareComuneDataBean.class));
	return q.list();
    }

    @Override
    public MercatipresenzeTMassive findByIdTestata(int idTestata) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("massiveTestata.id.codice", idTestata, Integer.class));
	ft.addRestriction(fr);
	List<MercatipresenzeTMassive> massive = this.findByFilterTable(ft);
	if (massive == null || massive.isEmpty()) {
	    return new MercatipresenzeTMassive();
	}
	if (massive.size() > 1) {
	    throw new RuntimeException(
		    "Caso anomalo: la comunicazione massiva con id " + idTestata + " è collegata a " + massive.size() + " giornate di calendario");
	}
	return massive.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean comunicazioneCancellabile(Integer idGiornata, String statoComunicazioneCompletata) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione da poter cancellare senza passare la giornata di riferimento");
	}
	if (StringUtils.isBlank(statoComunicazioneCompletata)) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione da poter cancellare senza passare qual'è lo stato conclusivo della comunicazione");
	}
	String sql = "select" + //
		     " count(massive_dettaglio.id) as conteggio " + //
		     "from" + //
		     " mercatipresenze_t_massive" + //
		     "   inner join mercatipresenze_d on" + //
		     "     mercatipresenze_t_massive.idcomune = mercatipresenze_d.idcomune and" + //
		     "     mercatipresenze_t_massive.fkid_mercatipresenze_t = mercatipresenze_d.fkidtestata" + //
		     "   inner join mercatipresenze_d_massive on" + //
		     "     mercatipresenze_d.idcomune = mercatipresenze_d_massive.idcomune and" + //
		     "     mercatipresenze_d.id = mercatipresenze_d_massive.fkid_mercatipresenze_d" + //
		     "   left join massive_dettaglio on" + //
		     "     mercatipresenze_d_massive.idcomune = massive_dettaglio.idcomune and" + //
		     "     mercatipresenze_d_massive.fkid_massive_dettaglio = massive_dettaglio.id and" + //
		     "     massive_dettaglio.ultimo_stato_completato = ? " + //
		     "where" + //
		     " mercatipresenze_t_massive.idcomune = ? and" + //
		     " mercatipresenze_t_massive.fkid_mercatipresenze_t = ? " + //
		     "group by" + //
		     " mercatipresenze_t_massive.id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettaglio.class);
	q.addScalar("conteggio", Hibernate.INTEGER);
	q.setString(0, statoComunicazioneCompletata);
	q.setString(1, ORMHelper.getIdcomune());
	q.setInteger(2, idGiornata);
	List<Integer> recordTrovati = q.list();
	if (recordTrovati.isEmpty()) {
	    return false; //non esiste nessuna comunicazione per la giornata
	}
	if (recordTrovati.size() > 1) {
	    throw new IllegalArgumentException("Sono state trovate più comunicazioni per la giornata con codice " + idGiornata);
	}
	return recordTrovati.get(0).compareTo(0) == 0;
    }

    @Override
    public List<MercatipresenzeTMassive> findByIdGiornata(Integer idGiornata) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException("Impossibile recuperare le comunicazioni della giornata senza passare la giornata di riferimento");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatipresenzeT.id.codice", idGiornata, Integer.class));
	ft.addRestriction(fr);
	List<MercatipresenzeTMassive> massive = this.findByFilterTable(ft);
	if (massive == null) {
	    return new ArrayList<MercatipresenzeTMassive>();
	}
	return massive;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdMercatiPresenzeDMassive(Integer idTestata) {

	if (idTestata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile recuperare gli id di mercatipresenze_d_massive senza passare l'id di mercatipresenze_t_massive");
	}
	String sql = "select" + //
		     " mercatipresenze_d_massive.id " + "from" + //
		     " mercatipresenze_t_massive" + //
		     "   inner join mercatipresenze_d on" + //
		     "     mercatipresenze_t_massive.idcomune = mercatipresenze_d.idcomune and" + //
		     "     mercatipresenze_t_massive.fkid_mercatipresenze_t = mercatipresenze_d.fkidtestata" + //
		     "   inner join mercatipresenze_d_massive on" + //
		     "     mercatipresenze_d.idcomune = mercatipresenze_d_massive.idcomune and" + //
		     "     mercatipresenze_d.id = mercatipresenze_d_massive.fkid_mercatipresenze_d " + //
		     "where" + //
		     " mercatipresenze_t_massive.idcomune = ? and" + //
		     " mercatipresenze_t_massive.fkid_massive_testata = ?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MercatipresenzeDMassive.class)
		.addSynchronizedEntityClass(MercatipresenzeTMassive.class).addSynchronizedEntityClass(MercatipresenzeD.class);
	q.addScalar("id", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	return q.list();
    }

    @Override
    public void deleteByIdTestata(Integer idTestata) {

	if (idTestata == null) {
	    throw new IllegalArgumentException("Impossibile cancellare un record da mercatipresenze_t_massive senza passare l'id di riferimento");
	}
	List<Integer> dettagli = this.findIdMercatiPresenzeDMassive(idTestata);
	for (Integer id : dettagli) {
	    this.mercatipresenzeDMassiveDAO.delete(new MercatipresenzeDMassive(id));
	}
	this.delete(this.findByIdTestata(idTestata));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdComunicazioniNonCompletate(String statoComunicazioneCompletata) {

	if (StringUtils.isBlank(statoComunicazioneCompletata)) {
	    throw new IllegalArgumentException(
		    "Impossibile recuperare le comunicazioni non completate senza indicare qual'è lo stato conclusivo della comunicazione");
	}
	String sql = "select" + //
		     " mercatipresenze_t_massive.fkid_massive_testata " + "from" + //
		     " mercatipresenze_t_massive" + //
		     "  inner join mercatipresenze_d on" + //
		     "   mercatipresenze_t_massive.idcomune = mercatipresenze_d.idcomune and" + //
		     "   mercatipresenze_t_massive.fkid_mercatipresenze_t = mercatipresenze_d.fkidtestata" + //
		     "  inner join mercatipresenze_d_massive on" + //
		     "   mercatipresenze_d.idcomune = mercatipresenze_d_massive.idcomune and" + //
		     "   mercatipresenze_d.id = mercatipresenze_d_massive.fkid_mercatipresenze_d" + //
		     "  left join massive_dettaglio on" + //
		     "   mercatipresenze_d_massive.idcomune = massive_dettaglio.idcomune and" + //
		     "   mercatipresenze_d_massive.fkid_massive_dettaglio = massive_dettaglio.id and" + //
		     "   massive_dettaglio.ultimo_stato_completato != ? " + //
		     "where" + //
		     " mercatipresenze_t_massive.idcomune = ? " + //
		     "group by" + //
		     " mercatipresenze_t_massive.fkid_massive_testata";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettaglio.class);
	q.addScalar("fkid_massive_testata", Hibernate.INTEGER);
	q.setString(0, statoComunicazioneCompletata);
	q.setString(1, ORMHelper.getIdcomune());
	return q.list();
    }

    @Override
    public int countComunicazioniPerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione per le manifestazioni senza passare l'id della comunicazione");
	}
	String sql = "select count(*) as conteggio from mercatipresenze_t_massive inner join massive_parametri on massive_parametri.idcomune=mercatipresenze_t_massive.idcomune and massive_parametri.fkid_testata=mercatipresenze_t_massive.fkid_massive_testata where mercatipresenze_t_massive.idcomune=:idcomune and exists (select 1 from massive_parametri mp where mp.idcomune=mercatipresenze_t_massive.idcomune and mp.fkid_testata=mercatipresenze_t_massive.fkid_massive_testata and mp.chiave=:chiave_id_giornata and mp.valore=:id_giornata) and massive_parametri.chiave=:tipo_comunicazione and massive_parametri.VALORE=:tipo_comunicazione_valore";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MercatipresenzeTMassive.class);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setString("chiave_id_giornata", ConfigurazioneComunicazioniManifestazioni.ID_GIORNATA_PARAM);
	query.setString("id_giornata", String.valueOf(idGiornata));
	query.setString("tipo_comunicazione", ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE_PARAM);
	query.setString("tipo_comunicazione_valore", tipoComunicazione.name());
	query.addScalar("conteggio", Hibernate.INTEGER);
	List<Integer> list = query.list();
	if (list.isEmpty()) {
	    return 0;
	}
	return list.get(0);
    }

    @Override
    public Integer recuperaPrimaComunicazionePerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione per le manifestazioni senza passare l'id della comunicazione");
	}
	String sql = "select fkid_massive_testata as idtestata from mercatipresenze_t_massive inner join massive_parametri on massive_parametri.idcomune=mercatipresenze_t_massive.idcomune and massive_parametri.fkid_testata=mercatipresenze_t_massive.fkid_massive_testata where mercatipresenze_t_massive.idcomune=:idcomune and mercatipresenze_t_massive.fkid_mercatipresenze_t = :idgiornata  and massive_parametri.chiave=:tipo_comunicazione and massive_parametri.VALORE=:tipo_comunicazione_valore group by fkid_massive_testata";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MercatipresenzeTMassive.class);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("idgiornata", idGiornata);
	query.setString("tipo_comunicazione", ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE_PARAM);
	query.setString("tipo_comunicazione_valore", tipoComunicazione.name());
	query.addScalar("idtestata", Hibernate.INTEGER);
	List<Integer> list = query.list();
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public boolean comunicazioneCancellabilePerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione,
	    String statoComunicazioneCompletata) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione da poter cancellare senza passare la giornata di riferimento");
	}
	if (StringUtils.isBlank(statoComunicazioneCompletata)) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione da poter cancellare senza passare qual'è lo stato conclusivo della comunicazione");
	}
	String sql = "select " + //
		     " count(massive_dettaglio.id) as conteggio " + //
		     " from" + //
		     " mercatipresenze_t_massive" + //
		     "   inner join mercatipresenze_d on" + //
		     "     mercatipresenze_t_massive.idcomune = mercatipresenze_d.idcomune and" + //
		     "     mercatipresenze_t_massive.fkid_mercatipresenze_t = mercatipresenze_d.fkidtestata" + //
		     "   inner join mercatipresenze_d_massive on" + //
		     "     mercatipresenze_d.idcomune = mercatipresenze_d_massive.idcomune and" + //
		     "     mercatipresenze_d.id = mercatipresenze_d_massive.fkid_mercatipresenze_d" + //
		     "   left join massive_dettaglio on" + //
		     "     mercatipresenze_d_massive.idcomune = massive_dettaglio.idcomune and" + //
		     "     mercatipresenze_d_massive.fkid_massive_dettaglio = massive_dettaglio.id and" + //
		     "     massive_dettaglio.ultimo_stato_completato = :ultimo_stato " + //
		     " where " + //
		     " mercatipresenze_t_massive.idcomune = :idcomune and" + //
		     " mercatipresenze_t_massive.fkid_mercatipresenze_t = :idgiornata " + //
		     " and exists (select 1 from massive_parametri mp where mp.idcomune=mercatipresenze_t_massive.idcomune and mp.fkid_testata=mercatipresenze_t_massive.fkid_massive_testata and mp.chiave=:tipo_comunicazione and mp.valore=:tipo_comunicazione_valore) " + //
		     " group by " + //
		     " mercatipresenze_t_massive.id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettaglio.class);
	q.addScalar("conteggio", Hibernate.INTEGER);
	q.setString("ultimo_stato", statoComunicazioneCompletata);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idgiornata", idGiornata);
	q.setString("tipo_comunicazione", ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE_PARAM);
	q.setString("tipo_comunicazione_valore", tipoComunicazione.name());
	List<Integer> recordTrovati = q.list();
	if (recordTrovati.isEmpty()) {
	    return false; //non esiste nessuna comunicazione per la giornata
	}
	return recordTrovati.get(0) == 0;
    }

    @Override
    public List<Integer> recuperaComunicazioniPerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione) {

	return recuperaComunicazioniPerTipologiaEGiornataEPresenza(idGiornata, tipoComunicazione, null);
    }

    @Override
    public boolean comunicazioneCancellabilePerTipologiaEPResenza(Integer idGiornata, String name, TIPO_COMUNICAZIONE tipoComunicazione,
	    Integer idMercatipresenzeD, String statoComunicazioneCompletata) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione da poter cancellare senza passare la giornata di riferimento");
	}
	if (StringUtils.isBlank(statoComunicazioneCompletata)) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione da poter cancellare senza passare qual'è lo stato conclusivo della comunicazione");
	}
	String sql = "select " + //
		     " count(massive_dettaglio.id) as conteggio " + //
		     " from" + //
		     " mercatipresenze_t_massive" + //
		     "   inner join mercatipresenze_d on" + //
		     "     mercatipresenze_t_massive.idcomune = mercatipresenze_d.idcomune and" + //
		     "     mercatipresenze_t_massive.fkid_mercatipresenze_t = mercatipresenze_d.fkidtestata" + //
		     "   inner join mercatipresenze_d_massive on" + //
		     "     mercatipresenze_d.idcomune = mercatipresenze_d_massive.idcomune and" + //
		     "     mercatipresenze_d.id = mercatipresenze_d_massive.fkid_mercatipresenze_d" + //
		     "   left join massive_dettaglio on" + //
		     "     mercatipresenze_d_massive.idcomune = massive_dettaglio.idcomune and" + //
		     "     mercatipresenze_d_massive.fkid_massive_dettaglio = massive_dettaglio.id and" + //
		     "     massive_dettaglio.ultimo_stato_completato = :ultimo_stato " + //
		     " where " + //
		     " mercatipresenze_t_massive.idcomune = :idcomune and" + //
		     " mercatipresenze_t_massive.fkid_mercatipresenze_t = :idgiornata and " + //
		     " mercatipresenze_d_massive.fkid_mercatipresenze_d = :idpresenza and " + //
		     " exists (select 1 from massive_parametri mp where mp.idcomune=mercatipresenze_t_massive.idcomune and mp.fkid_testata=mercatipresenze_t_massive.fkid_massive_testata and mp.chiave=:tipo_comunicazione and mp.valore=:tipo_comunicazione_valore) " + //
		     " group by " + //
		     " mercatipresenze_t_massive.id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettaglio.class);
	q.addScalar("conteggio", Hibernate.INTEGER);
	q.setString("ultimo_stato", statoComunicazioneCompletata);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idgiornata", idGiornata);
	q.setInteger("idpresenza", idMercatipresenzeD);
	q.setString("tipo_comunicazione", ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE_PARAM);
	q.setString("tipo_comunicazione_valore", tipoComunicazione.name());
	List<Integer> recordTrovati = q.list();
	if (recordTrovati.isEmpty()) {
	    return false; //non esiste nessuna comunicazione per la giornata
	}
	return recordTrovati.get(0) == 0;
    }

    @Override
    public List<Integer> recuperaComunicazioniPerTipologiaEGiornataEPresenza(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione,
	    Integer idMercatipresenzeD) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione da poter cancellare senza passare la giornata di riferimento");
	}
	String sql = "select " + //
		     " mercatipresenze_t_massive.fkid_massive_testata as id " + //
		     " from" + //
		     " mercatipresenze_t_massive" + //
		     "   inner join mercatipresenze_d on" + //
		     "     mercatipresenze_t_massive.idcomune = mercatipresenze_d.idcomune and" + //
		     "     mercatipresenze_t_massive.fkid_mercatipresenze_t = mercatipresenze_d.fkidtestata" + //
		     "   inner join mercatipresenze_d_massive on" + //
		     "     mercatipresenze_d.idcomune = mercatipresenze_d_massive.idcomune and" + //
		     "     mercatipresenze_d.id = mercatipresenze_d_massive.fkid_mercatipresenze_d" + //
		     "   left join massive_dettaglio on" + //
		     "     mercatipresenze_d_massive.idcomune = massive_dettaglio.idcomune and" + //
		     "     mercatipresenze_d_massive.fkid_massive_dettaglio = massive_dettaglio.id " + //
		     " where " + //
		     " mercatipresenze_t_massive.idcomune = :idcomune and" + //
		     " mercatipresenze_t_massive.fkid_mercatipresenze_t = :idgiornata and ";
	if (idMercatipresenzeD != null) {
	    sql += " mercatipresenze_d_massive.fkid_mercatipresenze_d = :idpresenza and ";
	}
	sql += " exists (select 1 from massive_parametri mp where mp.idcomune=mercatipresenze_t_massive.idcomune and mp.fkid_testata=mercatipresenze_t_massive.fkid_massive_testata and mp.chiave=:tipo_comunicazione and mp.valore=:tipo_comunicazione_valore) " + //
	       " group by " + //
	       " mercatipresenze_t_massive.id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettaglio.class);
	q.addScalar("id", Hibernate.INTEGER);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idgiornata", idGiornata);
	if (idMercatipresenzeD != null) {
	    q.setInteger("idpresenza", idMercatipresenzeD);
	}
	q.setString("tipo_comunicazione", ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE_PARAM);
	q.setString("tipo_comunicazione_valore", tipoComunicazione.name());
	return q.list();
    }

    @Override
    public List<Integer> findIdMercatiPresenzeDMassiveByTestataAndPresenza(Integer idTestata, Integer idMercatipresenzeD) {

	if (idTestata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile recuperare gli id di mercatipresenze_d_massive senza passare l'id di mercatipresenze_t_massive");
	}
	String sql = "select" + //
		     " mercatipresenze_d_massive.id " + //
		     " from " + //
		     " mercatipresenze_t_massive" + //
		     "   inner join mercatipresenze_d on" + //
		     "     mercatipresenze_t_massive.idcomune = mercatipresenze_d.idcomune and" + //
		     "     mercatipresenze_t_massive.fkid_mercatipresenze_t = mercatipresenze_d.fkidtestata" + //
		     "   inner join mercatipresenze_d_massive on" + //
		     "     mercatipresenze_d.idcomune = mercatipresenze_d_massive.idcomune and" + //
		     "     mercatipresenze_d.id = mercatipresenze_d_massive.fkid_mercatipresenze_d " + //
		     "where" + //
		     " mercatipresenze_t_massive.idcomune = :idcomune and" + //
		     " mercatipresenze_t_massive.fkid_massive_testata = :idtestata and " + //
		     " mercatipresenze_d_massive.fkid_mercatipresenze_d = :idpresenza ";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MercatipresenzeDMassive.class)
		.addSynchronizedEntityClass(MercatipresenzeTMassive.class).addSynchronizedEntityClass(MercatipresenzeD.class);
	q.addScalar("id", Hibernate.INTEGER);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idtestata", idTestata);
	q.setInteger("idpresenza", idMercatipresenzeD);
	return q.list();
    }
}
