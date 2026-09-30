package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CommedilizieTipopareriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAllegati;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.CommediliziePareriTmov;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedettId;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

@SuppressWarnings("rawtypes")
@Repository
public class UpgrFromCdsDAOImpl extends BaseDAOImpl implements UpgrFromCdsDAO {

    private static final String DESCRIZIONE_TIPOLOGIA_CDS = "CDS-Upg";
    private static final String PARTECIPANTE_ALLA_CDS_UPGR = "Partecipante alla CDS-UPGR";
    @Autowired
    private CommedilizieTipopareriDAO commedilizieTipopareriDAO;

    @Override
    public Class getEntityClass() {

	return Clpermmenu.class;
    }

    @Override
    public UpgrDaMigrareBean findCdsDaMigrare() {

	UpgrDaMigrareBean ret = new UpgrDaMigrareBean();
	Session session = getSession();
	String sql = "select " + //
		     "  cds.idcomune, " + //
		     "  cds.id, " + //
		     "  cds.codiceistanza, " + //
		     "  cds.codicemovimento, " + //
		     "  cds.odg, " + //
		     "  cds.note, " + //
		     "  cds.dataconvocazione, " + //
		     "  istanze.numeroistanza, " + //
		     "  istanze.software, " + //
		     "  movimenti.codicemovimento as movesistente " + //
		     " from " + //
		     "  cds " + //
		     "  inner join istanze on istanze.idcomune = cds.idcomune " + //
		     "  and istanze.codiceistanza = cds.codiceistanza " + //
		     "  left join commissioniedilizie_r on commissioniedilizie_r.idcomune = cds.idcomune " + //
		     "  and commissioniedilizie_r.codicemovimento = cds.codicemovimento " + //
		     "  left join movimenti on movimenti.idcomune = cds.idcomune " + //
		     "  and movimenti.codicemovimento = cds.codicemovimento " + //
		     " where  "; //
	sql += "  commissioniedilizie_r.codicemovimento is null " + //
	       " order by " + //
	       "  cds.idcomune, " + //
	       "  cds.codiceistanza, " + //
	       "  coalesce(movimenti.codicemovimento,-91000000) desc, " + //
	       "  cds.id";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class)
		.addSynchronizedEntityClass(CommissioniedilizieT.class);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("codicemovimento", Hibernate.INTEGER);
	q.addScalar("odg", Hibernate.STRING);
	q.addScalar("note", Hibernate.STRING);
	q.addScalar("numeroistanza", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("dataconvocazione", Hibernate.DATE);
	q.addScalar("movesistente", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(CdsDaMigrareBean.class));
	// POTREBBERO TORNARE PIù RECORD DI CDS PER ISTANZA il metodo SET rosolve l'univocità
	ret.setCdsDaMigrare(q.list());
	// ES UNA CON MOVIMENTO NULLO E UNA CON MOVIMENTO POPOLATO ES UMBRIA ASSISI PER ISTANZA 15276 / 190/2007/DIA
	Map<String, List<CdsTipologieInserite>> tip = inserisciTipologieCommissioni(session);
	ret.setTipologieCarichePerComune(inserisciCariche(tip, session));
	ret.setTipologieCreatePerIdcomune(tip);
	return ret;
    }

    @Override
    public CdsReport migraCDS(CdsDaMigrareBean cds, List<CdsTipologieInserite> codiceTipologia, List<CdsCaricheInserite> cariche) {

	CdsReport ret = new CdsReport(cds);
	Session session = getSession();
	if (!cds.esisteMovimento()) {
	    // 
	    Integer codiceMovimentoDaSostituire = trovaMovimentoDaSostituire(cds, session);
	    if (codiceMovimentoDaSostituire == null) {
		ret.addErrore("Non è stato trovato il movimento e non è possibile importare la cds con dati " + cds.toString());
		return ret;
	    }
	    cds.setCodicemovimento(codiceMovimentoDaSostituire);
	    cds.setMovesistente(codiceMovimentoDaSostituire);
	}
	CommissioniedilizieT commissioneEdilizia = new CommissioniedilizieT();
	if (codiceTipologia != null && codiceTipologia.size() > 0) {
	    Integer codiceTip = null;
	    for (CdsTipologieInserite cdsTipo : codiceTipologia) {
		if (cdsTipo.getSoftware().equalsIgnoreCase(cds.getSoftware())) {
		    codiceTip = cdsTipo.getCodicetipologia();
		    break;
		}
	    }
	    CommedilizieTipologie tipologia = new CommedilizieTipologie();
	    tipologia.setId(new PkId(codiceTip));
	    commissioneEdilizia.setCommedilizieTipologie(tipologia);
	}
	// commissioneEdilizia.setData(); Sistemata in inserisci convocazioni
	commissioneEdilizia.setDescrizione(cds.getNumeroistanza());
	commissioneEdilizia.setOdg(cds.getOdg());
	commissioneEdilizia.setNote(cds.getNote());
	commissioneEdilizia.setNumprotocollo(cds.getNumeroistanza());
	// commissioneEdilizia.setFlagaperta(commissione.isAperta()); Lo so dopo aver inserito le righe Se presente il contromovimento allora so che è chiusa
	commissioneEdilizia.setFlagSincrona(false);
	// commissioneEdilizia.setIdconvocazione(commissione.getIdConvocazione()); Salvato in inserisciConvocazioni
	commissioneEdilizia.setOdg(cds.getOdg());
	this.saveEntity(commissioneEdilizia);
	inserisciCommissioniEdilizieR(cds, commissioneEdilizia, session);
	inserisciAppello(cds, commissioneEdilizia, session, cariche);
	inserisciAllegati(cds, commissioneEdilizia, session);
	inserisciConvocazioni(cds, commissioneEdilizia, session);
	this.commit();
	this.flush();
	return ret;
    }

    private Map<String, List<CdsCaricheInserite>> inserisciCariche(Map<String, List<CdsTipologieInserite>> tip, Session session) {

	// TODO INSERISCI SOLO SE NON INSERITE
	Map<String, List<CdsCaricheInserite>> ret = new HashMap<String, List<CdsCaricheInserite>>();
	for (Entry<String, List<CdsTipologieInserite>> tips : tip.entrySet()) {
	    String idcomune = tips.getKey();
	    ORMHelper.setIdcomune(idcomune);
	    List<CdsCaricheInserite> ca = ret.get(idcomune);
	    if (ca == null) {
		ca = new ArrayList<CdsCaricheInserite>();
	    }
	    for (CdsTipologieInserite cdsTipologieBean : tips.getValue()) {
		ORMHelper.setSoftware(cdsTipologieBean.getSoftware());
		CommedilizieCarica c = new CommedilizieCarica();
		c.setDirittovoto(Boolean.FALSE);
		c.setDescrizione(PARTECIPANTE_ALLA_CDS_UPGR);
		c.setOrdinamento(Short.valueOf("0"));
		// TODO SOFTWARE
		cdsTipologieBean.getSoftware();
		saveEntity(c);
		CdsCaricheInserite caricaInserita = new CdsCaricheInserite(c.getId().getCodice(), cdsTipologieBean.getIdcomune(),
			cdsTipologieBean.getSoftware());
		ca.add(caricaInserita);
	    }
	    ret.put(idcomune, ca);
	}
	this.flush();
	return ret;
    }

    private Map<String, List<CdsTipologieInserite>> inserisciTipologieCommissioni(Session session) {

	// TODO INSERISCI SOLO SE NON INSERITE
	Map<String, List<CdsTipologieInserite>> ret = new HashMap<String, List<CdsTipologieInserite>>();
	String sql = "select  " + //
		     "   movimenti.idcomune, " + //
		     "   movimenti.tipomovimento,    " + //
		     "   cmov.codiceamministrazione, " + //
		     "   count(*) as conteggio," + // 
		     "   istanze.software " + //
		     "  from  " + //
		     "   cds  " + //
		     "   inner join istanze on istanze.idcomune = cds.idcomune  " + //
		     "   and istanze.codiceistanza = cds.codiceistanza  " + //
		     "   left join commissioniedilizie_r on commissioniedilizie_r.idcomune = cds.idcomune  " + //
		     "   and commissioniedilizie_r.codicemovimento = cds.codicemovimento  " + //
		     "   inner join movimenti on movimenti.idcomune = cds.idcomune  " + //
		     "   and movimenti.codicemovimento = cds.codicemovimento  " + //
		     "   left join movimenti_contromovimenti on  " + //
		     "   movimenti_contromovimenti.idcomune=movimenti.idcomune and  " + //
		     "   movimenti_contromovimenti.codicemovimento=movimenti.codicemovimento " + //
		     "   left join movimenti cmov on  " + //
		     "   cmov.idcomune=movimenti_contromovimenti.idcomune and " + //
		     "   cmov.codicemovimento=movimenti_contromovimenti.codicecontromovimento " + //
		     "  where  ";//
	sql += "   commissioniedilizie_r.codicemovimento is null  " + //
	       "  group by  " + //
	       "     movimenti.idcomune, " + //
	       "     movimenti.tipomovimento, " + //
	       "     cmov.codiceamministrazione, " + //
	       "     istanze.software " + //
	       "  order by	 " + //
	       "      movimenti.idcomune," + //
	       "      istanze.software," + //		      
	       "     count(*) desc," + // 
	       "     cmov.codiceamministrazione," + // 
	       "     movimenti.tipomovimento";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieTipologie.class);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("tipomovimento", Hibernate.STRING);
	q.addScalar("codiceamministrazione", Hibernate.INTEGER);
	q.addScalar("conteggio", Hibernate.INTEGER);
	q.addScalar("software", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(CdsTipologieBean.class));
	List<CdsTipologieBean> list = q.list();
	Map<String, Set<String>> listaMovPerIdcomuneAndSoftware = new HashMap<String, Set<String>>();
	Map<String, Integer> mappaAmministrazioni = new HashMap<String, Integer>();
	for (CdsTipologieBean cdb : list) {
	    String idComuneSoftwareKey = getIdComuneSoftwareKey(cdb.getIdcomune(), cdb.getSoftware());
	    Set<String> movimenti = listaMovPerIdcomuneAndSoftware.get(idComuneSoftwareKey);
	    if (movimenti == null) {
		movimenti = new HashSet<String>();
	    }
	    movimenti.add(cdb.getTipomovimento());
	    listaMovPerIdcomuneAndSoftware.put(idComuneSoftwareKey, movimenti);
	    Integer codiceAmministrazione = cdb.getCodiceamministrazione();
	    if (!mappaAmministrazioni.containsKey(idComuneSoftwareKey)) { // ci metto solo la prima valida che sono ordinati per conteggio dell'uso 
		if (codiceAmministrazione != null && cdb.getConteggio() != null) {
		    mappaAmministrazioni.put(idComuneSoftwareKey, codiceAmministrazione);
		}
	    }
	}
	for (Entry<String, Set<String>> tip : listaMovPerIdcomuneAndSoftware.entrySet()) {
	    String idcomuneAndSoftware = tip.getKey();
	    Integer codiceamministrazione = mappaAmministrazioni.get(idcomuneAndSoftware);
	    String idcomuneFromKey = getIdcomuneFromKey(idcomuneAndSoftware);
	    String softwareFromKey = getSoftwareFromKey(idcomuneAndSoftware);
	    ORMHelper.setIdcomune(idcomuneFromKey);
	    ORMHelper.setSoftware(softwareFromKey);
	    CommedilizieTipologie tipologia = new CommedilizieTipologie();
	    tipologia.setDescrizione(DESCRIZIONE_TIPOLOGIA_CDS);
	    tipologia.setFlagUploadDocParere(Boolean.TRUE); // Per l'inserimento del parere è necessario inserire un documento firmato. 
	    // È il caso, ad esempio, delle CDS dove le amministrazioni sono tenute ad esprimere il parere inoltrandolo mediante un file firmato.
	    Amministrazioni amm = null;
	    if (codiceamministrazione != null) {
		amm = new Amministrazioni();
		amm.setId(new PkId(codiceamministrazione));
	    }
	    tipologia.setAmministrazione(amm);
	    tipologia.setFlagDisabilita(Boolean.TRUE);
	    this.saveEntity(tipologia);
	    Set<String> tipimov = tip.getValue();
	    for (String tm : tipimov) {
		CommedilizieTipologiedett dett = new CommedilizieTipologiedett();
		dett.setCommedilizieTipologie(tipologia);
		Tipimovimento tmov = new Tipimovimento();
		tmov.getId().setTipomovimento(tm);
		CommedilizieTipologiedettId id = new CommedilizieTipologiedettId(idcomuneFromKey, tipologia.getId().getCodice(), tm);
		dett.setId(id);
		dett.setTipimovimento(tmov);
		this.saveEntity(dett);
	    }
	    List<CdsTipologieInserite> lst = ret.get(idcomuneFromKey);
	    if (lst == null) {
		lst = new ArrayList<CdsTipologieInserite>();
	    }
	    lst.add(new CdsTipologieInserite(tipologia.getId().getCodice(), idcomuneFromKey, softwareFromKey));
	    ret.put(idcomuneFromKey, lst);
	}
	this.flush();
	return ret;
    }

    private String getSoftwareFromKey(String idcomuneAndSoftwareKey) {

	return idcomuneAndSoftwareKey.substring(idcomuneAndSoftwareKey.indexOf("-") + 1);
    }

    private String getIdcomuneFromKey(String idcomuneAndSoftwareKey) {

	return idcomuneAndSoftwareKey.substring(0, idcomuneAndSoftwareKey.indexOf("-"));
    }

    private String getIdComuneSoftwareKey(String idComune, String software) {

	return idComune + "-" + software;
    }

    private Integer trovaMovimentoDaSostituire(CdsDaMigrareBean cds, Session session) {

	String sql = "select  " + //
		     "  codicemovimento, " + //
		     "  tipo " + //
		     "from " + //
		     "  ( " + //
		     "    select " + //
		     "      movimenti.codicemovimento, " + //
		     "      '01-movimenti' as tipo, " + //
		     "      movimenti.data " + //
		     "    from " + //
		     "      movimenti " + //
		     "      inner join tipimovimento on tipimovimento.idcomune = movimenti.idcomune " + //
		     "      and tipimovimento.tipomovimento = movimenti.tipomovimento " + //
		     "    where " + //
		     "      movimenti.idcomune = ? " + //
		     "      and movimenti.codiceistanza = ? " + //
		     "      and tipimovimento.flag_cds = ? " + //
		     "    union all " + //
		     "    select " + //
		     "      movimenti.codicemovimento, " + //
		     "      '02-tipiprocedure' as tipo ," + //
		     "      movimenti.data " + //
		     "    from " + //
		     "      movimenti " + //
		     "      inner join istanze on istanze.idcomune = movimenti.idcomune " + //
		     "      and istanze.codiceistanza = movimenti.codiceistanza " + //
		     "      inner join tipiprocedure on istanze.idcomune = tipiprocedure.idcomune " + //
		     "      and istanze.codiceprocedura = tipiprocedure.codiceprocedura " + //
		     "    where " + //
		     "      movimenti.idcomune = ? " + //
		     "      and movimenti.codiceistanza = ? " + //
		     "      and movimenti.tipomovimento = tipiprocedure.idcomuncds " + //
		     "    union all " + //
		     "    select " + //
		     "      movimenti.codicemovimento, " + //
		     "      '03-configurazione' as tipo, " + //
		     "      movimenti.data " + //
		     "    from " + //
		     "      movimenti " + //
		     "      inner join configurazione on configurazione.idcomune = movimenti.idcomune " + //
		     "      and configurazione.software = ? " + //
		     "    where " + //
		     "      movimenti.idcomune = ? " + //
		     "      and movimenti.codiceistanza = ? " + //
		     "      and movimenti.tipomovimento = configurazione.idcdsvpr " + //
		     "  ) temp_movimento order by tipo, data";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(Movimenti.class);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("tipo", Hibernate.STRING);
	q.addScalar("codicemovimento", Hibernate.BOOLEAN);
	q.setString(0, cds.getIdcomune());
	q.setInteger(1, cds.getCodiceistanza());
	q.setInteger(2, 1);
	// 
	q.setString(3, cds.getIdcomune());
	q.setInteger(4, cds.getCodiceistanza());
	// 
	q.setString(5, "TT");
	q.setString(6, cds.getIdcomune());
	q.setInteger(7, cds.getCodiceistanza());
	//
	q.setResultTransformer(Transformers.aliasToBean(CdsMovDaSostituireBean.class));
	List<CdsMovDaSostituireBean> list = q.list();
	if (list.isEmpty()) {
	    return null;
	}
	return list.iterator().next().getCodicemovimento();
    }

    private void inserisciConvocazioni(CdsDaMigrareBean cds, CommissioniedilizieT commissioneEdilizia, Session session) {

	//
	String sql = "select dataconvocazione,oraconvocazione,flag_effettiva as effettiva from cdsconvocazioni where idcomune=? and fkidcds=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class)
		.addSynchronizedEntityClass(CommissioniedilizieT.class);
	q.addScalar("dataconvocazione", Hibernate.DATE);
	q.addScalar("oraconvocazione", Hibernate.STRING);
	q.addScalar("effettiva", Hibernate.BOOLEAN);
	q.setString(0, cds.getIdcomune());
	q.setInteger(1, cds.getId());
	q.setResultTransformer(Transformers.aliasToBean(CdsConvocazioniBean.class));
	List<CdsConvocazioniBean> list = q.list();
	Integer idConvocazione = null;
	Integer idConvocazioneEffettiva = null;
	Date dataConvocazione = null;
	Date dataConvocazioneEffettiva = null;
	for (CdsConvocazioniBean conv : list) {
	    CommedilizieConvocazioni c = new CommedilizieConvocazioni();
	    c.setCommissioniedilizieT(commissioneEdilizia);
	    c.setDataconvocazione(conv.getDataconvocazione());
	    c.setOraconvocazione(StringUtils.defaultString(conv.getOraconvocazione(), "09:00"));
	    this.saveEntity(c);
	    idConvocazione = c.getId().getCodice();
	    dataConvocazione = c.getDataconvocazione();
	    if (BooleanUtils.toBoolean(conv.getEffettiva())) {
		idConvocazioneEffettiva = c.getId().getCodice();
		dataConvocazioneEffettiva = c.getDataconvocazione();
	    }
	}
	if (idConvocazioneEffettiva != null) {
	    commissioneEdilizia.setIdconvocazione(idConvocazioneEffettiva);
	    if (dataConvocazioneEffettiva != null) {
		commissioneEdilizia.setData(dataConvocazioneEffettiva);
	    }
	    this.saveEntity(commissioneEdilizia);
	} else {
	    commissioneEdilizia.setIdconvocazione(idConvocazione);
	    if (dataConvocazione != null) {
		commissioneEdilizia.setData(dataConvocazione);
	    }
	    this.saveEntity(commissioneEdilizia);
	}
	this.flush();
    }

    private void inserisciCommissioniEdilizieR(CdsDaMigrareBean cds, CommissioniedilizieT commissioneEdilizia, Session session) {

	CommissioniedilizieR riga = new CommissioniedilizieR();
	riga.setCommissioniedilizieT(commissioneEdilizia);
	Movimenti m = new Movimenti();
	m.getId().setCodice(cds.getCodicemovimento());
	riga.setMovimento(m);
	Movimenti cmov = trovaContromovimento(session, cds);
	riga.setMovimentoRientro(cmov);
	riga.setOrdine(0);
	this.saveEntity(riga);
	commissioneEdilizia.setFlagaperta(cmov == null ? Boolean.TRUE : Boolean.FALSE);
	this.saveEntity(commissioneEdilizia);
	this.flush();
    }

    private Movimenti trovaContromovimento(Session session, CdsDaMigrareBean cds) {

	String sql = " select " + //
		     "   cmov.codicemovimento ," + //
		     "   cmov.data" + //
		     " from" + //
		     "   movimenti_contromovimenti" + //
		     "   left join movimenti cmov on cmov.idcomune = movimenti_contromovimenti.idcomune" + //
		     "   and cmov.codicemovimento = movimenti_contromovimenti.codicecontromovimento" + //
		     " where" + //
		     "   movimenti_contromovimenti.idcomune = :idcomune" + //
		     "   and movimenti_contromovimenti.codicemovimento = :codicemovimento " + //
		     "   and cmov.data is not null";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(MovimentiContromovimenti.class);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("codicemovimento", Hibernate.INTEGER);
	q.setString("idcomune", cds.getIdcomune());
	q.setInteger("codicemovimento", cds.getMovesistente());
	q.setResultTransformer(Transformers.aliasToBean(CdsCmovBean.class)); //
	List<CdsCmovBean> list = q.list();
	Movimenti mov = null;
	for (CdsCmovBean cdsCmovBean : list) {
	    mov = new Movimenti();
	    mov.getId().setCodice(cdsCmovBean.getCodicemovimento());
	    break;
	}
	return mov;
    }

    private void inserisciAppello(CdsDaMigrareBean cds, CommissioniedilizieT commissioneEdilizia, Session session, List<CdsCaricheInserite> cariche) {

	String sql = "select  " + //
		     "   idcomune, " + //
		     "   codicesoggetto, " + //
		     "   note, " + //
		     "   tipo " + //
		     " from " + //
		     "   ( " + //
		     "     select " + //
		     "       idcomune, " + //
		     "       codiceamministrazione as codicesoggetto, " + //
		     "       note, " + //
		     "       'amministrazioni' as tipo " + //
		     "     from " + //
		     "       cdsinvitati " + //
		     "     where " + //
		     "       idcomune = :idcomune1 " + //
		     "       and fkidcds = :cds1 " + //
		     "     union all " + //
		     "     select " + //
		     "       idcomune, " + //
		     "       codiceanagrafe as codicesoggetto, " + //
		     "       note, " + //
		     "       'anagrafe' as tipo " + //
		     "     from " + //
		     "       cdsinvitati2 " + //
		     "     where " + //
		     "       idcomune = :idcomune2 " + //
		     "       and fkidcds = :cds2 " + //
		     "   ) invitati";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class)
		.addSynchronizedEntityClass(CommissioniedilizieT.class);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codicesoggetto", Hibernate.INTEGER);
	q.addScalar("note", Hibernate.STRING);
	q.addScalar("tipo", Hibernate.STRING);
	q.setString("idcomune1", cds.getIdcomune());
	q.setInteger("cds1", cds.getId());
	q.setString("idcomune2", cds.getIdcomune());
	q.setInteger("cds2", cds.getId());
	q.setResultTransformer(Transformers.aliasToBean(CdsAppelloBean.class));
	List<CdsAppelloBean> list = q.list();
	CommedilizieCarica carica = new CommedilizieCarica();
	carica.getId().setIdcomune(cds.getIdcomune());
	boolean caricaTrovata = false;
	for (CdsCaricheInserite c : cariche) {
	    if (c.getSoftware().equalsIgnoreCase(cds.getSoftware())) {
		carica.getId().setCodice(c.getCodice());
		caricaTrovata = true;
		break;
	    }
	}
	for (CdsAppelloBean cdsAppelloBean : list) {
	    CommedilizieAppello ap = new CommedilizieAppello();
	    ap.setCommissioniedilizieT(commissioneEdilizia);
	    ap.setPresente(true);
	    if (cdsAppelloBean.isAmministrazioni()) {
		Amministrazioni amm = new Amministrazioni();
		amm.setId(new PkId(cdsAppelloBean.getCodicesoggetto()));
		ap.setAmministrazioni(amm);
	    } else {
		Anagrafe anagrafe = new Anagrafe();
		anagrafe.setId(new PkId(cdsAppelloBean.getCodicesoggetto()));
		ap.setAnagrafe(anagrafe);
	    }
	    if (caricaTrovata) {
		ap.setCommedilizieCarica(carica);
	    }
	    this.saveEntity(ap);
	}
	this.flush();
    }

    private void inserisciAllegati(CdsDaMigrareBean cds, CommissioniedilizieT commissioneEdilizia, Session session) {

	String sql = "select cdsatti.data,cdsatti.ora,cdsatti.note,cdsatti.codiceoggetto,oggetti.nomefile " + //
		     " from cdsatti left join oggetti on " + // 
		     "	oggetti.idcomune=cdsatti.idcomune and oggetti.codiceoggetto=cdsatti.codiceoggetto" + // 
		     " where cdsatti.idcomune=? and cdsatti.fkidcds=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class)
		.addSynchronizedEntityClass(CommissioniedilizieT.class);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("ora", Hibernate.STRING);
	q.addScalar("note", Hibernate.STRING);
	q.addScalar("codiceoggetto", Hibernate.INTEGER);
	q.addScalar("nomefile", Hibernate.STRING);
	q.setString(0, cds.getIdcomune());
	q.setInteger(1, cds.getId());
	q.setResultTransformer(Transformers.aliasToBean(CdsAttiBean.class));
	List<CdsAttiBean> list = q.list();
	for (CdsAttiBean a : list) {
	    CommedilizieAllegati all = new CommedilizieAllegati();
	    all.setCommissioniedilizieT(commissioneEdilizia);
	    all.setFlagPubblica(false);
	    all.setNote(a.getNote());
	    all.setDescrizione(a.getNomefile() == null ? "Allegato" : a.getNomefile());
	    all.setDataregistrazione(a.getData());
	    if (a.getCodiceoggetto() != null) {
		Oggetti o = new Oggetti();
		o.getId().setCodice(a.getCodiceoggetto());
		all.setOggetti(o);
	    }
	    this.saveEntity(all);
	}
	this.flush();
    }

    @Override
    public void migraMenuSuSoftwareTT() {

	Session session = getSession();
	String sql = "SELECT clpermmenu.idcomune,clpermmenu.codiceresponsabile,clpermmenu.fkidmenu  " + //
		     " FROM clpermmenu WHERE fkidmenu IN (750,751,752,780) and software<>'TT' GROUP BY clpermmenu.idcomune,clpermmenu.codiceresponsabile,clpermmenu.fkidmenu" + //
		     " ORDER BY clpermmenu.idcomune,clpermmenu.codiceresponsabile,clpermmenu.fkidmenu";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(Clpermmenu.class).addSynchronizedEntityClass(Clpermmenu.class);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codiceresponsabile", Hibernate.INTEGER);
	q.addScalar("fkidmenu", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(UpgrPermMenuResponsabileBean.class));
	List<UpgrPermMenuResponsabileBean> list = q.list();
	Software s = new Software();
	s.setCodice(WebConstants.SOFTWARE_TT);
	for (UpgrPermMenuResponsabileBean bean : list) {
	    if (StringUtils.isNotBlank(bean.getIdcomune())) {
		ORMHelper.setIdcomune(bean.getIdcomune());
		Clpermmenu p = new Clpermmenu();
		p.setResponsabile((Responsabili) getById(Responsabili.class, bean.getCodiceresponsabile()));
		Clmenu m = new Clmenu();
		m.setId(bean.getFkidmenu());
		p.setMenu(m);
		p.setSoftware(s);
		saveEntity(p);
	    }
	}
	flush();
	clear();
	q = session.createSQLQuery("delete from clpermmenu where fkidmenu in (750,751,752,780) and software <> 'TT'");
	q.executeUpdate();
	flush();
    }

    @Override
    public void migraCommediTipopareriMov() {

	String sql = "SELECT " + //
		     "  commedilizie_tipopareri.idcomune as idcomune " + //
		     ", commedilizie_tipopareri.codice as codice " + //
		     ", commedilizie_tipopareri.tipomovimento as tipomovimento " + //
		     " FROM " + //
		     "  commedilizie_tipopareri " + //
		     "  LEFT JOIN " + //
		     "    commedilizie_pareri_tmov " + //
		     "    ON " + //
		     "      commedilizie_pareri_tmov.idcomune  =commedilizie_tipopareri.idcomune " + //
		     "      AND commedilizie_pareri_tmov.fk_commedpareri_id=commedilizie_tipopareri.codice " + //
		     " WHERE " + //
		     "  commedilizie_pareri_tmov.idcomune IS NULL " + //
		     "  AND commedilizie_tipopareri.tipomovimento IS NOT NULL " + //
		     " ORDER BY " + //
		     "  idcomune " + //
		     ", commedilizie_tipopareri.codice";
	Session session = getSession();
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommediliziePareriTmov.class)
		.addSynchronizedEntityClass(CommedilizieTipopareri.class);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codice", Hibernate.INTEGER);
	q.addScalar("tipomovimento", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(UpgrCommediPareriTmov.class));
	List<UpgrCommediPareriTmov> list = q.list();
	String idcomune = ORMHelper.getIdcomune();
	for (UpgrCommediPareriTmov tmov : list) {
	    ORMHelper.setIdcomune(tmov.getIdcomune());
	    commedilizieTipopareriDAO.insertMovimentoPerSoftware(tmov.getCodice(), WebConstants.SOFTWARE_TT, tmov.getTipomovimento());
	}
	ORMHelper.setIdcomune(idcomune);
	flush();
    }
}
