package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.MovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.IMovimentiMetadatiDAO;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologieService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Repository
public class MovimentiDAOImpl extends BaseDAOImpl<Movimenti, PkId> implements MovimentiDAO {

    private SoftwareDAO softwareDAO;
    private ComuniassociatiService comuniassociatiService;
    private AlberoprocDAO alberoprocDAO;
    private CommedilizieTipologieService commedilizieTipologieService;
    @Autowired
    private IMovimentiMetadatiDAO movimentiMetadatiDAO;

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////ATTENZIONE I MOVIMENTI EFFETTUATI SONO QUELLI///////////////////
    //////////////////////////////// CON DATA NOT NULL /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setAlberoprocDAO(AlberoprocDAO alberoprocDAO) {

	this.alberoprocDAO = alberoprocDAO;
    }

    @Autowired
    public void setSoftwareDAO(SoftwareDAO softwareDAO) {

	this.softwareDAO = softwareDAO;
    }

    @Autowired
    public void setCommedilizieTipologieService(CommedilizieTipologieService commedilizieTipologieService) {

	this.commedilizieTipologieService = commedilizieTipologieService;
    }

    @Override
    public Class<Movimenti> getEntityClass() {

	return Movimenti.class;
    }

    @Override
    public void insert(Movimenti entity) {

	super.insert(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movimenti> findByCriteria(DetachedCriteria criteria) {

	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Movimenti findMovimentiByTipoMovimento(Integer codiceistanza, String tipimovimento) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("istanzaId", codiceistanza));
	criteria.add(Restrictions.eq("tipomovimentoId", tipimovimento));
	criteria.add(Restrictions.isNotNull("data"));
	criteria.addOrder(Order.desc("data"));
	criteria.addOrder(Order.desc("ordineInserimento"));
	List<Movimenti> list = getHibernateTemplate().findByCriteria(criteria, 0, 1);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movimenti> findMovimentiSimo(Calendar fromDate, Calendar toDate, Alberoproc alberoproc) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.add(Restrictions.eq("_istanza.alberoproc", alberoproc));
	criteria.add(Restrictions.isNotNull("data"));
	//	criteria.add(Restrictions.between("data", fromDate.getTime(), toDate.getTime()));
	criteria.add(Restrictions.between("datainserimento", fromDate.getTime(), toDate.getTime()));
	criteria.addOrder(Order.asc("id.codice"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movimenti> findMovimentiDaAssociareAllaCommissione(Date filtroData, CommissioniedilizieT commissioniedilizieT, Integer firstResult,
	    Integer maxResult) {

	// recupero la lista di tipi movimento che definiscono i movimenti che devono essere mandati in commissione
	List<String> list = new ArrayList<String>(0);
	CommedilizieTipologie commedilizieTipologie = commedilizieTipologieService.findById(commissioniedilizieT.getCommedilizieTipologie().getId());
	for (CommedilizieTipologiedett commedilizieTipologiedett : commedilizieTipologie.getCommedilizieTipologiedetts()) {
	    list.add(commedilizieTipologiedett.getTipimovimento().getId().getTipomovimento());
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	if (filtroData != null) {
	    criteria.add(Restrictions.ge("data", filtroData));
	}
	criteria.add(Restrictions.isNotNull("data"));
	criteria.add(Restrictions.in("tipomovimento.id.tipomovimento", list));
	criteria.add(Restrictions.isEmpty("commedilizieMovimento"));
	criteria.addOrder(Order.desc("data"));
	if (null != firstResult && null != maxResult) {
	    return (List<Movimenti>) getHibernateTemplate().findByCriteria(criteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Movimenti>) getHibernateTemplate().findByCriteria(criteria);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public Movimenti findMovimentoAvvioIstanza(Integer codiceistanza) {

	String hql = "select this_ from Movimenti this_ inner join this_.istanza _istanza where _istanza.id.idcomune=? " +
		" and _istanza.id.codice=? and this_.tipomovimentoId=_istanza.tipoMovimentoAvvioId and this_.data is not null order by this_.data asc, this_.id.codice asc";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), codiceistanza };
	List<Movimenti> result = getHibernateTemplate().find(hql, values);
	if (!result.isEmpty()) {
	    return result.get(0);
	}
	return null;
    }

    @Override
    public int findProgressivoInserimento(Integer codiceistanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceistanza, Integer.TYPE));
	ft.addRestriction(fr);
	DetachedCriteria criteria = getCriteriaForFilter(ft, true);
	criteria.setProjection(Projections.max("ordineInserimento"));
	Integer ris = ((Integer) getHibernateTemplate().findByCriteria(criteria).get(0));
	if (ris != null) {
	    return ris.intValue() + 1;
	}
	return 0;
    }

    @Override
    public void updateAmministrazioniStc(Integer codiceMovimento, Integer codiceAmministrazioneStc) {

	if (codiceMovimento == null) {
	    throw new RuntimeException("updateAmministrazioniStc: il parametro codiceMovimento passato è nullo");
	}
	String hql = "update Movimenti set amministrazioniStcId = ? where id.idcomune = ? and id.codice=?";
	int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { codiceAmministrazioneStc, ORMHelper.getIdcomune(), codiceMovimento });
	if (i != 1) {
	    throw new RuntimeException("La query di aggiornamento delle amministrazioni: [" +
		    codiceMovimento +
		    "] con amministrazioneStc [" +
		    codiceAmministrazioneStc +
		    "] ha influito su " +
		    i +
		    " record");
	}
    }

    @Override
    public List<MovimentiDTO> findMovimentiDTOSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer maxResults) {

	DetachedCriteria criteria = getCriteriaForFilter(batchScadenzarioFilter, false);
	criteria.add(Restrictions.eq("_tipomovimento.flagStc", Boolean.TRUE));
	criteria.add(Restrictions.eq("inviatoConStc", MovimentiService.STC_NON_INVIATO));
	criteria.add(Restrictions.isNotNull("data"));
	ProjectionList plist = getProjectionForMovimentiDTO();
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MovimentiDTO.class));
	if (null != firstResult && null != maxResults) {
	    List<MovimentiDTO> list = (List<MovimentiDTO>) getHibernateTemplate().findByCriteria(criteria, firstResult.intValue(),
		    maxResults.intValue());
	    return list;
	} else {
	    List<MovimentiDTO> list = (List<MovimentiDTO>) getHibernateTemplate().findByCriteria(criteria);
	    return list;
	}
    }

    @Override
    public List<MovimentiDTO> findMovimentiDTODaLeggere(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getCriteriaForFilter(batchScadenzarioFilter, false);
	criteria.add(Restrictions.eq("flagDaLeggere", Boolean.TRUE));
	criteria.add(Restrictions.isNotNull("data"));
	ProjectionList plist = getProjectionForMovimentiDTO();
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MovimentiDTO.class));
	if (null != firstResult && null != maxResult) {
	    List<MovimentiDTO> list = (List<MovimentiDTO>) getHibernateTemplate().findByCriteria(criteria, firstResult.intValue(),
		    maxResult.intValue());
	    return list;
	} else {
	    List<MovimentiDTO> list = (List<MovimentiDTO>) getHibernateTemplate().findByCriteria(criteria);
	    return list;
	}
    }

    /***
     * per chi referenzia questo metodo deve fare attenzione a referenziare gli alias delle varie tabelle
     * 
     * @return
     */
    private ProjectionList getProjectionForMovimentiDTO() {

	// ##################################################################################################
	// ##### ATTENZIONE!! SE SI MODIFICA QUESTO METODO VERIFICARE GLI ALIAS SUI METODI CHE LO RICHIAMANO
	// ##################################################################################################
	ProjectionList plist = Projections.projectionList();
	//	    /////////////////// Definisco condizioni di JOIN  //////////////////////////////////////////////////////
	////////////////////////////////////Condizioni di SELECT///////////////////////////////////////////////////
	plist.add(Projections.property("id.codice"), "codicemovimento");
	plist.add(Projections.property("id.idcomune"), "idcomune");
	plist.add(Projections.property("_software.codice"), "softwarecodice");
	plist.add(Projections.property("_software.descrizione"), "softwaredescrizione");
	plist.add(Projections.property("_istanza.id.codice"), "codiceistanza");
	plist.add(Projections.property("_istanza.numeroistanza"), "numeroistanza");
	plist.add(Projections.property("_istanza.data"), "dataistanza");
	plist.add(Projections.property("_comune.comune"), "comune");
	plist.add(Projections.property("_istanza.numeroprotocollo"), "numeroprotocolloistanza");
	plist.add(Projections.property("_istanza.dataprotocollo"), "dataprotocolloistanza");
	plist.add(Projections.property("_istanza.posizionearchivio"), "posizionearchivio");
	plist.add(Projections.property("_procedura.procedura"), "procedura");
	//..
	plist.add(Projections.property("_istruttore.responsabile"), "istruttore");
	plist.add(Projections.property("_richiedente.id.codice"), "codicerichiedente");
	plist.add(Projections.property("_richiedente.nominativo"), "richiedentenominativo");
	plist.add(Projections.property("_richiedente.nome"), "richiedentenome");
	plist.add(Projections.property("_richiedente.codicefiscale"), "richiedentecodicefiscale");
	plist.add(Projections.property("_richiedente.partitaiva"), "richiedentepartitaiva");
	plist.add(Projections.property("_richiedentestorico.id.codice"), "richstoricoid");
	plist.add(Projections.property("_richiedentestorico.nominativo"), "richstoriconominativo");
	plist.add(Projections.property("_richiedentestorico.nome"), "richstoriconome");
	plist.add(Projections.property("_richiedentestorico.codicefiscale"), "richstoricocodicefiscale");
	plist.add(Projections.property("_richiedentestorico.partitaiva"), "richstoricopartitaiva");
	plist.add(Projections.property("_azienda.id.codice"), "codiceazienda");
	plist.add(Projections.property("_azienda.nominativo"), "aziendanominativo");
	plist.add(Projections.property("_azienda.nome"), "aziendanome");
	plist.add(Projections.property("_azienda.codicefiscale"), "aziendacodicefiscale");
	plist.add(Projections.property("_azienda.partitaiva"), "aziendapartitaiva");
	plist.add(Projections.property("_aziendastorico.id.codice"), "aziendastoricoid");
	plist.add(Projections.property("_aziendastorico.nominativo"), "aziendastoriconominativo");
	plist.add(Projections.property("_aziendastorico.nome"), "aziendastoriconome");
	plist.add(Projections.property("_aziendastorico.codicefiscale"), "aziendastoricocodicefiscale");
	plist.add(Projections.property("_aziendastorico.partitaiva"), "aziendastoricopartitaiva");
	plist.add(Projections.property("_statoistanza.id.codicestato"), "codicestato");
	plist.add(Projections.property("_statoistanza.stato"), "stato");
	plist.add(Projections.property("_intervento.scDescrizione"), "intervento");
	plist.add(Projections.property("_tipomovimento.id.tipomovimento"), "tipomovimento");
	plist.add(Projections.property("_tipomovimento.movimento"), "tipomovimentodescrizione");
	plist.add(Projections.property("_endoprocedimento.id.codice"), "endoprocedimentocodice");
	plist.add(Projections.property("_endoprocedimento.procedimento"), "endoprocedimentodescrizione");
	plist.add(Projections.property("_amministrazione.id.codice"), "amministrazionicodice");
	plist.add(Projections.property("_amministrazione.amministrazione"), "amministrazionidescrizione");
	plist.add(Projections.property("_responsabile.id.codice"), "responsabilecodice");
	plist.add(Projections.property("_responsabile.responsabile"), "responsabiledescrizione");
	plist.add(Projections.property("data"), "datamovimento");
	plist.add(Projections.property("parere"), "pareremovimento");
	plist.add(Projections.property("esito"), "esitomovimento");
	plist.add(Projections.property("note"), "notemovimento");
	plist.add(Projections.property("pubblica"), "pubblicamovimento");
	plist.add(Projections.property("numeroprotocollo"), "numeroprotocollomovimento");
	plist.add(Projections.property("dataprotocollo"), "dataprotocollomovimento");
	plist.add(Projections.property("fkidprotocollo"), "fkidprotocollomovimento");
	plist.add(Projections.property("datainserimento"), "datainserimentomovimento");
	plist.add(Projections.property("movimento"), "movimentodescrizione");
	plist.add(Projections.property("pubblicaparere"), "pubblicapareremovimento");
	plist.add(Projections.property("creatoDaStc"), "creatodastcmovimento");
	plist.add(Projections.property("inviatoConStc"), "inviatoconstcmovimento");
	plist.add(Projections.property("inviatoACamcom"), "inviatoacamcommovimento");
	plist.add(Projections.property("flagDaLeggere"), "flagdaleggeremovimento");
	plist.add(Projections.property("dataScadenza"), "datascadenzamovimento");
	plist.add(Projections.property("flagDisabilitato"), "flagdisabilitatomovimento");
	plist.add(Projections.property("flagCmovObblig"), "flagcmovobbligmovimento");
	plist.add(Projections.property("ordineInserimento"), "ordineinserimentomovimento");
	plist.add(Projections.property("numprotMittente"), "numprotmittentemovimento");
	plist.add(Projections.property("dataProtMittente"), "dataprotmittentemovimento");
	plist.add(Projections.property("_tipisoggetto.tiposoggetto"), "tiposoggetto");
	plist.add(Projections.property("_istanza.descrsoggetto"), "descrizionesoggetto");
	plist.add(Projections.property("_istanza.descrsoggetto"), "descrizionesoggetto");
	plist.add(Projections.property("_istanzetempistica.datafine"), "datafine");
	return plist;
    }

    private DetachedCriteria getCriteriaForFilter(BatchScadenzarioFilter filter, boolean consideraLaDataScadenza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("_istanza.software", "_software");
	criteria.createAlias("_istanza.richiedente", "_richiedente");
	criteria.createAlias("_istanza.richiedentestorico", "_richiedentestorico", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegale", "_azienda", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegalestorico", "_aziendastorico", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.alberoproc", "_alberoproc");
	criteria.createAlias("_alberoproc.vwAlberoproc", "_intervento");
	criteria.createAlias("_istanza.chiusura", "_statoistanza");
	criteria.createAlias("_istanza.tipisoggetto", "_tipisoggetto", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.comune", "_comune", Criteria.LEFT_JOIN);
	criteria.createAlias("amministrazioni", "_amministrazione", Criteria.LEFT_JOIN);
	criteria.createAlias("tipomovimento", "_tipomovimento");
	criteria.createAlias("endoprocedimento", "_endoprocedimento", Criteria.LEFT_JOIN);
	criteria.createAlias("responsabile", "_responsabile", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.istanzeTempistica", "_istanzetempistica", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.procedura", "_procedura", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.istruttore", "_istruttore", Criteria.LEFT_JOIN);
	if (consideraLaDataScadenza) {
	    if (filter.getDallaData() != null && filter.getAllaData() != null) {
		// mostrare solo le scadenze dell'intervallo
		criteria.add(Restrictions.between("dataScadenza", filter.getDallaData(), filter.getAllaData()));
	    } else {
		if (filter.getDallaData() != null) {
		    // mostrare solo le scadenze dalla data in poi
		    criteria.add(Restrictions.ge("dataScadenza", filter.getDallaData()));
		}
		if (filter.getAllaData() != null) {
		    // mostrare solo le scadenze fino alla data
		    criteria.add(Restrictions.le("dataScadenza", filter.getDallaData()));
		}
	    }
	}
	if (StringUtils.isNotBlank(filter.getNumeroIstanza())) {
	    criteria.add(Restrictions.ilike("_istanza.numeroistanza", filter.getNumeroIstanza(), MatchMode.EXACT));
	}
	if (filter.getMovimentiFilter() != null && StringUtils.isNotBlank(filter.getMovimentiFilter().getNumeroistanza())) {
	    criteria.add(Restrictions.ilike("_istanza.numeroistanza", filter.getMovimentiFilter().getNumeroistanza(), MatchMode.EXACT));
	}
	// TODO: verificare se descrizione movimento nel template corrisponde alla property movimento di tipomovimento
	if (filter.getMovimentiFilter() != null && StringUtils.isNotBlank(filter.getMovimentiFilter().getTipomovimento())) {
	    criteria.add(Restrictions.ilike("_tipomovimento.movimento", filter.getMovimentiFilter().getTipomovimento(), MatchMode.EXACT));
	}
	// TODO: verificare che descrizione del richiedente nel template corrisponde alla property nominativo di angrafe (tipo di richiedente in istanze)
	if (filter.getMovimentiFilter() != null && StringUtils.isNotBlank(filter.getMovimentiFilter().getRichiedentenominativo())) {
	    Criterion nominativo = getCriterionForSplittableString(filter.getMovimentiFilter().getRichiedentenominativo(), "_richiedente.nominativo",
		    "_richiedente.nome");
	    if (nominativo != null) {
		criteria.add(nominativo);
	    }
	}
	boolean isAmministratore = false;
	boolean isAmministratoreSoftware = false;
	boolean isOperatoreSettato = false;
	Responsabili responsabile = null;
	//	TODO
	// 	if (filter.getUtenteLoggato().getId().getCodice() != null) {
	//	    responsabile = responsabiliService.findById(new PkId(filter.getUtenteLoggato().getId().getCodice()));
	//	    if (responsabile != null) {
	//		isOperatoreSettato = true;
	//		isAmministratore = StringUtils.defaultIfEmpty(responsabile.getAmministratore(), "0").equalsIgnoreCase("1") ? true : false;
	//		isAmministratoreSoftware = StringUtils.defaultIfEmpty(responsabile.getAmministratoresoftware(), "0").equalsIgnoreCase("1") ? true
	//			: false;
	//		if (!(isAmministratore || isAmministratoreSoftware)) {
	//		    FilterRestriction responsabileRestriction = new FilterRestriction();
	//		    responsabileRestriction.setAndOrRestriction(AndOrRestriction.OR);
	//		    FilterField<Integer> existsPermistanzeOperatore = new FilterField<Integer>("id.codiceresponsabile", istanzePrefix
	//			    + ".permistanzes", FieldOperationsEnum.EXISTS, new Integer[] { responsabile.getId().getCodice() }, Permistanze.class);
	//		    existsPermistanzeOperatore.setExistsChildEntityId("istanze.id");
	//		    existsPermistanzeOperatore.setExistsParentEntityId(istanzePrefix + ".id");
	//		    responsabileRestriction.addFilterField(existsPermistanzeOperatore);
	//		    FilterField<Integer> existsRuoloOperatore = new FilterField<Integer>("id.codiceresponsabile", istanzePrefix
	//			    + ".vwIstanzeOperatoriRuolis", FieldOperationsEnum.EXISTS, new Integer[] { responsabile.getId().getCodice() },
	//			    VwIstanzeOpeRuoli.class);
	//		    existsRuoloOperatore.setExistsChildEntityId("istanza.id");
	//		    existsRuoloOperatore.setExistsParentEntityId(istanzePrefix + ".id");
	//		    responsabileRestriction.addFilterField(existsRuoloOperatore);
	//		    ft.addRestriction(responsabileRestriction);
	//		}
	//	    }
	//	}
	if (EntityUtils.getNestedProperty(filter.getResponsabile(), "id.codice") != null) {
	    Criterion ope = Restrictions.eq("_istanza.responsabileId", filter.getResponsabile().getId().getCodice());
	    Criterion resp = Restrictions.eq("_istanza.responsabileProcedimentoId", filter.getResponsabile().getId().getCodice());
	    Criterion orresponsabil = Restrictions.or(ope, resp);
	    Criterion istr = Restrictions.eq("_istanza.istruttoreId", filter.getResponsabile().getId().getCodice());
	    Criterion orresponsabi2 = Restrictions.or(orresponsabil, istr);
	    criteria.add(orresponsabi2);
	}
	String filtroSoftware = EntityUtils.getNestedProperty(filter.getScadSoftware(), "codice") == null ? null
		: filter.getScadSoftware().getCodice();
	if (!StringUtils.defaultIfEmpty(filtroSoftware, WebConstants.SOFTWARE_TT).equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    criteria.add(Restrictions.eq("_software.codice", filter.getScadSoftware().getCodice()));
	} else {
	    if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
		// BOCCI 2011-11-16 SE SPECIFICATO L'OPERATORE DEVO RICERCARE NON IN TUTTI I SOFTWARE ATTIVI X IL COMUNE
		// MA IN QUELLI ABILITATI PER L'OPERATORE
		// se la chiamata arriva da TT allora recupero le istanze per tutti i software attivi
		List<Software> softwareAttiviList = new ArrayList<Software>();
		if (isOperatoreSettato && responsabile != null) {
		    softwareAttiviList = softwareDAO.findSoftwareAbilitati(responsabile, false);
		} else {
		    softwareAttiviList = softwareDAO.findSoftwareAttivi(false);
		}
		if (!softwareAttiviList.isEmpty()) {
		    String[] softwareAttivi = new String[softwareAttiviList.size()];
		    for (int i = 0; i < softwareAttiviList.size(); i++) {
			Software softwareAttivo = (Software) softwareAttiviList.get(i);
			softwareAttivi[i] = softwareAttivo.getCodice();
		    }
		    criteria.add(Restrictions.in("_software.codice", softwareAttivi));
		}
	    } else {
		criteria.add(Restrictions.eq("_software.codice", ORMHelper.getSoftware()));
	    }
	}
	if (filter.getIntervento().getId().getCodice() != null) {
	    Alberoproc alberoproc = alberoprocDAO.findById(new PkId(filter.getIntervento().getId().getCodice()));
	    criteria.add(Restrictions.like("_alberoproc.scCodice", alberoproc.getScCodice(), MatchMode.START));
	}
	if (StringUtils.isNotBlank(filter.getTipoMovimentoFatto().getId().getTipomovimento())) {
	    criteria.add(Restrictions.eq("tipomovimentoId", filter.getTipoMovimentoFatto().getId().getTipomovimento()));
	}
	if (filter.getSoloScadenzeImportanti() != null) {
	    if (filter.getSoloScadenzeImportanti().booleanValue()) {
		List<String> tmavvs = new ArrayList<String>();
		if (filter.getTipimovimentoAvv() != null) {
		    for (ResponsabiliTmAvv tma : filter.getTipimovimentoAvv()) {
			if (BooleanUtils.isFalse(tma.getFlagEsclude())) {
			    tmavvs.add(tma.getId().getTipomovimento());
			}
		    }
		}
		List<String> tmavvs_esclude = new ArrayList<String>();
		if (filter.getTipimovimentoAvv() != null) {
		    for (ResponsabiliTmAvv tma2 : filter.getTipimovimentoAvv()) {
			if (BooleanUtils.isTrue(tma2.getFlagEsclude())) {
			    tmavvs_esclude.add(tma2.getId().getTipomovimento());
			}
		    }
		}
		// TODO
		// SOLO IN CASO di movimenti da visionare
		if (tmavvs.size() > 0) {
		    criteria.add(Restrictions.in("tipomovimentoId", tmavvs.toArray()));
		}
		if (tmavvs_esclude.size() > 0) {
		    criteria.add(Restrictions.not(Restrictions.in("tipomovimentoId", tmavvs_esclude.toArray())));
		}
	    }
	}
	List<Statiistanza> statiIstanza = filter.getStatiIstanza();
	if (statiIstanza != null && !statiIstanza.isEmpty()) {
	    String[] stati = new String[statiIstanza.size()];
	    for (int i = 0; i < statiIstanza.size(); i++) {
		Statiistanza statoIstanza = (Statiistanza) statiIstanza.get(i);
		stati[i] = statoIstanza.getId().getCodicestato();
	    }
	    criteria.add(Restrictions.in("_istanza.chiusuraId", stati));
	}
	if (filter.getScadComportamento() != null) {
	    if (filter.getScadComportamento().equals(Integer.valueOf(0))) {
		criteria.add(Restrictions.eq("_statoistanza.staticomportamento.codcomportamento", 0));
	    } else {
		criteria.add(Restrictions.in("_statoistanza.staticomportamento.codcomportamento", new Integer[] { 1, -1 }));
	    }
	}
	// Controllo se l'istanza è multi comune, nel saco devo aggiungere i filtro per codice comune dell'istanza.I record dovranno essere
	// filtrati per i soli comuni abilitati all'operatore
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) {
	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		String[] codiceComune = new String[responsabilicomunis.size()];
		int i = 0;
		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
		    codiceComune[i] = responsabilicomuni.getId().getCodicecomune();
		    i++;
		}
		criteria.add(Restrictions.in("_istanza.comune.codicecomune", codiceComune));
	    }
	}
	//ORDINAMENTI
	if (filter.getOrdinamentoScadenze() != null) {
	    switch (filter.getOrdinamentoScadenze()) {
	    case ASC:
		criteria.addOrder(Order.asc("data"));
		criteria.addOrder(Order.asc("ordineInserimento"));
		break;
	    default:
		criteria.addOrder(Order.desc("data"));
		criteria.addOrder(Order.desc("ordineInserimento"));
		break;
	    }
	} else {
	    criteria.addOrder(Order.desc("data"));
	    criteria.addOrder(Order.desc("ordineInserimento"));
	}
	criteria.addOrder(Order.asc("_software.descrizione"));
	String[] padNumeroistanza = new String[] { "20", "' '" };
	criteria.addOrder(OrderBySqlFormula.asc("_istanza.numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
	return criteria;
    }

    @Override
    public List<CodiceDescrizioneBean> findMetadatiMovimento(Integer codiceMovimento) {

	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	if (codiceMovimento == null) {
	    return result;
	}
	MovimentiDTO mov = this.findMovimentiDTODaLeggere(codiceMovimento);
	if (mov != null) {
	    //   INITMD_MOV_DESCRIZIONE
	    String descrizioneMovimento = StringUtils.defaultString(mov.getMovimentodescrizione());
	    if (StringUtils.isBlank(descrizioneMovimento)) {
		descrizioneMovimento = mov.getTipomovimentodescrizione();
	    }
	    if (StringUtils.isNotBlank(descrizioneMovimento)) {
		CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_MOV_DESCRIZIONE");
		cdb.setDescrizione(descrizioneMovimento);
		result.add(cdb);
	    }
	    //	    INITMD_MOV_DATA
	    if (mov.getDatamovimento() != null) {
		String data = Utilities.formatDate(mov.getDatamovimento(), false);
		CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_MOV_DATA");
		cdb.setDescrizione(data);
		result.add(cdb);
	    }
	    //	    INITMD_MOV_DATAPROT
	    if (mov.getDataprotocollomovimento() != null) {
		String data = Utilities.formatDate(mov.getDataprotocollomovimento(), false);
		CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_MOV_DATAPROT");
		cdb.setDescrizione(data);
		result.add(cdb);
	    }
	    if (StringUtils.isNotBlank(mov.getNumeroprotocollomovimento())) {
		CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
		cdb.setCodice("INITMD_MOV_NUMPROT");
		cdb.setDescrizione(mov.getNumeroprotocollomovimento());
		result.add(cdb);
	    }
	}
	return result;
    }

    private MovimentiDTO findMovimentiDTODaLeggere(Integer codiceMovimento) {

	if (codiceMovimento == null) {
	    return null;
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("_istanza.software", "_software");
	criteria.createAlias("_istanza.comune", "_comune");
	criteria.createAlias("_istanza.richiedente", "_richiedente");
	criteria.createAlias("_istanza.richiedentestorico", "_richiedentestorico", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegale", "_azienda", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegalestorico", "_aziendastorico", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.alberoproc", "_alberoproc");
	criteria.createAlias("_alberoproc.vwAlberoproc", "_intervento");
	criteria.createAlias("_istanza.chiusura", "_statoistanza");
	criteria.createAlias("_istanza.tipisoggetto", "_tipisoggetto", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.procedura", "_procedura", Criteria.LEFT_JOIN);
	criteria.createAlias("amministrazioni", "_amministrazione", Criteria.LEFT_JOIN);
	criteria.createAlias("tipomovimento", "_tipomovimento");
	criteria.createAlias("endoprocedimento", "_endoprocedimento", Criteria.LEFT_JOIN);
	criteria.createAlias("responsabile", "_responsabile", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.istanzeTempistica", "_istanzetempistica", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.istruttore", "_istruttore", Criteria.LEFT_JOIN);
	criteria.add(Restrictions.eq("id.codice", codiceMovimento));
	criteria.add(Restrictions.isNotNull("data"));
	ProjectionList plist = getProjectionForMovimentiDTO();
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MovimentiDTO.class));
	List<MovimentiDTO> list = (List<MovimentiDTO>) getHibernateTemplate().findByCriteria(criteria);
	if (list.size() == 1) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<ChiaveValoreBean<String, Integer>> countMovimentiSTCConAnomalie() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("_istanza.software", "_software");
	criteria.add(Restrictions.eq("_software.codice", ORMHelper.getSoftware()));
	criteria.add(Restrictions.isNotNull("idAttDest"));
	criteria.add(Restrictions.isNotNull("data"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.groupProperty("statoAttDest"), "statoAttDest");
	plist.add(Projections.count("id.codice"));
	criteria.setProjection(plist);
	List list = (List) getHibernateTemplate().findByCriteria(criteria);
	List<ChiaveValoreBean<String, Integer>> result = new ArrayList<ChiaveValoreBean<String, Integer>>();
	for (Object o : list) {
	    if (o instanceof Object[]) {
		Object[] r = (Object[]) o;
		String stato = (String) r[0];
		Integer count = (Integer) r[1];
		if (count != null && count.intValue() > 0) {
		    stato = StringUtils.defaultString(stato, "IN_ATTESA");
		    if (stato.equalsIgnoreCase("IN_ATTESA") || stato.equalsIgnoreCase("KO")) {
			ChiaveValoreBean<String, Integer> cvb = new ChiaveValoreBean<String, Integer>();
			cvb.setChiave(stato);
			cvb.setValore(count.intValue());
			result.add(cvb);
		    }
		}
	    }
	}
	return result;
    }

    @Override
    public String getUuid(Integer codiceMovimento) {

	return movimentiMetadatiDAO.getUuid(codiceMovimento);
    }
}
