package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeFilter;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.filters.TipoRicercaEnum;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component("upgrUpdateAnagrafeStoricoTask")
public class UpdateAnagrafeStoricoTask extends BaseJavaTask {

    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IstanzerichiedentiService istanzerichiedentiService;

    /**
     * 
     * 
     * 
     * 
     * parametri ammessi:
     * 
     * @param runFromDate
     *            (opzionale, Formato: dd/MM/yyyy - HH:mm:ss) inizia l'esecuzione dalla data/ora. ES:<br />
     *            <b>&lt;param name="runFromDate" value="18/07/2011 - 16:06:01"&gt;&lt;/param&gt;</b>
     * @param runToDate
     *            (opzionale, Formato: dd/MM/yyyy - HH:mm:ss) termina l'esecuzione alla data/ora. ES:<br />
     *            <b>&lt;param name="runToDate" value="18/07/2011 - 16:07:30"&gt;&lt;/param&gt;</b>
     * @param ordinamentoIstanzeData
     *            (opzionale, Valori ammessi ASC, DESC) determina se la bonifica delle istanze debba procedere per
     *            datapresentazione(ISTANZE.DATA) crescente o descrescente
     * @param resetStoricoIstanze
     *            (opzionale, Valori ammessi true, false, default true) se true allora verranno resettati i riferimenti
     *            dei richiedenti storici eseguendo le query: <br/>
     *            update istanze set fk_richiedentestorico_id=null, fk_titolarelegalestorico_id = null,
     *            fk_professionistastorico_id=null ; <br/>
     *            update istanzerichiedenti set fk_richiedentestorico_id=null, fk_anagrafecollstorico_id = null,
     *            fk_procuratorestorico_id=null; <br/>
     * @param cancellaSoloAnagrafiche
     *            (opzionale, Valori true, false, default false) se true esegue solamente la cancellazione delle
     *            anagrafiche disabilitate
     * @param eseguiBloccoAutorizzazioni
     *            (opzionale, Valori true, false) se true esegue il blocco di bonifica delle autorizzazioni (Default
     *            true)
     * @param eliminaStoriciNonUtilizzati
     *            (opzionale Valori true, false, default false) Se true esegue la routine di cancellazione degli storici
     *            non utilizzati per le istanze
     * 
     *            <pre>
     *  &lt;java-task id="UPGR_ANAGRAFESTORICO" 
     *  		spring-bean-id="upgrUpdateAnagrafeStoricoTask" 
     *  		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateAnagrafeStoricoTask" 
     * 			fail-on-error="false">
     *         &lt;param name="runFromDate" value="26/01/2012 - 09:00:01">&lt;/param>
     *         &lt;param name="runToDate" value="26/01/2012 - 17:10:30">&lt;/param>
     *         &lt;param name="ordinamentoIstanzeData" value="ASC">&lt;/param>
     *         &lt;param name="resetStoricoIstanze" value="true">&lt;/param>
     *         &lt;param name="cancellaSoloAnagrafiche" value="false">&lt;/param>
     *         &lt;param name="eseguiBloccoAutorizzazioni" value="true">&lt;/param>
     * 	       &lt;param name="eliminaStoriciNonUtilizzati" value="false">&lt;/param>     
     *  &lt;/java-task>
     * </pre>
     * 
     * 
     */
    @Override
    @SuppressWarnings("unchecked")
    public int run(Session session) throws SetupRunException {

	String origIdcomune = ORMHelper.getIdcomune();
	String runFromDate = getParameterValue("runFromDate");
	String runToDate = getParameterValue("runToDate");
	String resetStoricoIstanze = getParameterValue("resetStoricoIstanze");
	String cancellaSoloAnagrafiche = getParameterValue("cancellaSoloAnagrafiche");
	String eseguiBloccoAutorizzazioni = getParameterValue("eseguiBloccoAutorizzazioni");
	boolean cancellaSoloAnagraficheVal = false;
	if (StringUtils.defaultIfEmpty(cancellaSoloAnagrafiche, "false").equalsIgnoreCase("true")) {
	    cancellaSoloAnagraficheVal = true;
	}
	boolean resetIstanze = false;
	if (StringUtils.defaultIfEmpty(resetStoricoIstanze, "true").equalsIgnoreCase("true")) {
	    resetIstanze = true;
	}
	boolean eseguiBloccoAutorizzazioniVal = false;
	if (StringUtils.defaultIfEmpty(eseguiBloccoAutorizzazioni, "true").equalsIgnoreCase("true")) {
	    eseguiBloccoAutorizzazioniVal = true;
	}
	String eliminaStoriciNonUtilizzati = getParameterValue("eliminaStoriciNonUtilizzati");
	boolean eliminaStoriciNonUtilizzatiVal = false;
	if (StringUtils.defaultIfEmpty(eliminaStoriciNonUtilizzati, "false").equalsIgnoreCase("true")) {
	    eliminaStoriciNonUtilizzatiVal = true;
	}
	String ordinamentoIstanzeData = StringUtils.defaultIfEmpty(getParameterValue("ordinamentoIstanzeData"), "ASC");
	Date runFrom = null;
	Date runTo = null;
	ServiceValidationRules serviceValidationRules = new ServiceValidationRules();
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(), false);
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doEntityValidation.name(), false);
	SigeproBusinessRules.setClassRules(ServiceValidationRules.class, serviceValidationRules);
	boolean run = true;
	boolean executeWithTime = false;
	if (runFromDate != null && runToDate != null) {
	    SimpleDateFormat sdf2 = new SimpleDateFormat(WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN);
	    try {
		runFrom = sdf2.parse(runFromDate);
		runTo = sdf2.parse(runToDate);
	    } catch (ParseException e) {
		String errMsg = MessageFormat.format("Il parametro 'runFromDate' o 'runToDate' non è formattato correttamente [{0},{1}]",
			new Object[] { runFromDate, runToDate });
		throw new RuntimeException(errMsg);
	    }
	    executeWithTime = true;
	}
	if (runFromDate != null) {
	    while (true) {
		if (runTo != null) {
		    if (runTo.getTime() <= new Date().getTime()) {
			activityLogInfo("Terminato il tempo di esecuzione del task impostato a " + runTo);
			return 0;
		    }
		}
		if (runFrom.getTime() <= new Date().getTime()) {
		    break;
		}
	    }
	}
	try {
	    int pageSize = 200;
	    int pageNumber = 0;
	    Integer count = 0;
	    Criteria crit = null;
	    List<Integer> counts = null;
	    List<Anagrafe> anagrafes = null;
	    activityLogInfo("UpdateAnagrafeStoricoTask: Inizio aggiornamento anagrafe");
	    if (cancellaSoloAnagraficheVal == false) {
		// Setto flagDisabilitato=0 per tutte le anagrafiche con flagDisabilitato == null 
		try {
		    String hql = "update Anagrafe anagrafe set anagrafe.flagDisabilitato=? where anagrafe.flagDisabilitato is null";
		    Query query = session.createQuery(hql);
		    query.setInteger(0, 0);
		    query.executeUpdate();
		} catch (Exception e3) {
		    handleErrorCondition(e3, "Errore durante l'aggiornamento di anagrafe.flagDisabilitato = 0 quando vale null.");
		}
		if (resetIstanze) {
		    try {
			String hql = "update Istanze i set i.richiedentestoricoId=null, i.titolarelegalestoricoId=null, i.professionistastoricoId=null";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			session.flush();
		    } catch (Exception e3) {
			handleErrorCondition(e3, "Errore durante l'annullamento dei riferimenti storici delle istanze.");
		    }
		    try {
			String hql = "update Istanzerichiedenti i set i.richiedentestoricoId=null, i.anagrafeCollegatastoricoId=null, i.procuratorestoricoId=null";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			session.flush();
		    } catch (Exception e3) {
			handleErrorCondition(e3, "Errore durante l'annullamento dei riferimenti storici di istanze richiedenti.");
		    }
		}
		// Ciclo le istanze
		String hql = "select this_.id.codice, this_.id.idcomune, this_.software.codice from Istanze this_ "
			+ " where this_.richiedentestoricoId is null and not this_.tipoMovimentoAvvioId is null "
			+ " order by this_.id.idcomune,this_.data " + ordinamentoIstanzeData + ", this_.software.codice";
		Query query = session.createQuery(hql);
		activityLogInfo("Inizio la bonifica delle istanze");
		// boolean aggiornaIstanza = false;
		boolean aggiornaIstanzeRichiedenti = false;
		List<Object[]> listi = query.list();
		String hqlUpdateIstanze = "update Istanze i set i.richiedentestoricoId=?, i.titolarelegalestoricoId=?, i.professionistastoricoId=?,"
			+ "i.richiedenteId=?, i.titolarelegaleId=?, i.professionistaId=? where i.id.idcomune=? and i.id.codice=?";
		for (Object valoriQuery : listi) {
		    if (run) {
			if (!checkexecutionTime(runTo, executeWithTime)) {
			    return 0;
			}
			Object[] result = (Object[]) valoriQuery;
			Integer codiceIstanza = (Integer) result[0];
			String idcomune = (String) result[1];
			String software = (String) result[2];
			// devo settare l'idcomune per forza su ORMHelper
			ORMHelper.setIdcomune(idcomune);
			ORMHelper.setSoftware(software);
			Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
			if (istanze == null) {
			    handleErrorCondition("Non è stata trova l'istanza con codice: [" + codiceIstanza + "], idcomune:[" + idcomune
				    + "], software:[" + software + "]");
			} else {
			    // aggiornaIstanza = false;
			    activityLogInfo("Bonifico l'istanza: {}", new Object[] { istanze.getId() });
			    Date data = istanze.getData();
			    // ISTANZE.RICHIEDENTE
			    Anagrafe richiedente = istanze.getRichiedente();
			    session.evict(richiedente);
			    richiedente = anagrafeService.findById(richiedente.getId());
			    // aggiorno l'istanza solamente se richiedente storico è nullo altrimenti è stata già processata
			    if (istanze.getRichiedentestorico() == null) {
				if (richiedente != null) {
				    richiedente = gestAnagrafe(richiedente, data, session);
				    // istanze.setRichiedente(richiedente);
				}
				// ISTANZE.TITOLARELEGALE
				Anagrafe titolarelegale = istanze.getTitolarelegale();
				if (titolarelegale != null) {
				    session.evict(titolarelegale);
				    titolarelegale = anagrafeService.findById(titolarelegale.getId());
				    titolarelegale = gestAnagrafe(titolarelegale, data, session);
				    // istanze.setTitolarelegale(titolarelegale);
				}
				// ISTANZE.PROFESSIONISTA
				Anagrafe professionista = istanze.getProfessionista();
				if (professionista != null) {
				    session.evict(professionista);
				    professionista = anagrafeService.findById(professionista.getId());
				    professionista = gestAnagrafe(professionista, data, session);
				}
				activityLogInfo("Aggiorno l'istanza {}", new Object[] { istanze.getId() });
				session.flush();
				try {
				    Anagrafestorico richiedenteStorico = null;
				    if (EntityUtils.getNestedProperty(richiedente, "id.codice") != null) {
					richiedenteStorico = anagrafeService.findStoricoId(richiedente, istanze.getData());
					if (richiedenteStorico != null) {
					    session.evict(richiedenteStorico);
					}
					// istanze.setRichiedentestorico(richiedenteStorico);
				    }
				    Anagrafestorico titolareLegaleStorico = null;
				    if (EntityUtils.getNestedProperty(titolarelegale, "id.codice") != null) {
					titolareLegaleStorico = anagrafeService.findStoricoId(titolarelegale, istanze.getData());
					if (titolareLegaleStorico != null) {
					    session.evict(titolareLegaleStorico);
					}
					// istanze.setTitolarelegalestorico(titolareLegaleStorico);
				    }
				    Anagrafestorico professionistaStorico = null;
				    if (EntityUtils.getNestedProperty(professionista, "id.codice") != null) {
					professionistaStorico = anagrafeService.findStoricoId(professionista, istanze.getData());
					if (professionistaStorico != null) {
					    session.evict(professionistaStorico);
					}
					// istanze.setProfessionistastorico(professionistaStorico);
				    }
				    query = session.createQuery(hqlUpdateIstanze);
				    Integer richiedenteStoricoId = (Integer) EntityUtils.getNestedProperty(richiedenteStorico, "id.codice");
				    Integer titolareLegaleStoricoId = (Integer) EntityUtils.getNestedProperty(titolareLegaleStorico, "id.codice");
				    Integer professionistastoricoId = (Integer) EntityUtils.getNestedProperty(professionistaStorico, "id.codice");
				    Integer richiedenteId = (Integer) EntityUtils.getNestedProperty(richiedente, "id.codice");
				    Integer titolareLegaleId = (Integer) EntityUtils.getNestedProperty(titolarelegale, "id.codice");
				    Integer professionistaId = (Integer) EntityUtils.getNestedProperty(professionista, "id.codice");
				    query.setParameter(0, richiedenteStoricoId, new IntegerType());
				    query.setParameter(1, titolareLegaleStoricoId, new IntegerType());
				    query.setParameter(2, professionistastoricoId, new IntegerType());
				    query.setParameter(3, richiedenteId, new IntegerType());
				    query.setParameter(4, titolareLegaleId, new IntegerType());
				    query.setParameter(5, professionistaId, new IntegerType());
				    query.setString(6, istanze.getId().getIdcomune());
				    query.setInteger(7, istanze.getId().getCodice());
				    query.executeUpdate();
				    session.flush();
				    this.commitTransaction();
				    session.flush();
				} catch (Exception e) {
				    handleErrorWithBusinessValidationErrorMessage(e, "Errore nell'aggiornamento dell'istanza " + istanze.getId());
				}
				List<Istanzerichiedenti> istanzerichiedentis = new ArrayList<Istanzerichiedenti>();
				try {
				    istanzerichiedentis = istanzerichiedentiService.findByIstanza(istanze);
				    activityLogInfo("ciclo i richiedenti dell'istanza {}", new Object[] { istanze.getId() });
				} catch (Exception e) {
				    handleErrorWithBusinessValidationErrorMessage(e,
					    "Errore nella lettura dei richiedenti per l'istanza " + istanze.getId());
				}
				for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
				    Anagrafe richiedenteSc = istanzerichiedenti.getRichiedente();
				    aggiornaIstanzeRichiedenti = false;
				    if (richiedenteSc != null) {
					aggiornaIstanzeRichiedenti = true;
					//if (istanzerichiedenti.getRichiedentestorico() == null) {
					session.evict(richiedenteSc);
					richiedenteSc = anagrafeService.findById(richiedenteSc.getId());
					richiedenteSc = gestAnagrafe(richiedenteSc, data, session);
					istanzerichiedenti.setRichiedente(richiedenteSc);
				    }
				    // ISTANZE.TITOLARELEGALE
				    Anagrafe titolarelegaleSc = istanzerichiedenti.getAnagrafeCollegata();
				    if (titolarelegaleSc != null) {
					aggiornaIstanzeRichiedenti = true;
					// if (istanzerichiedenti.getAnagrafeCollegatastorico() == null) {
					session.evict(titolarelegaleSc);
					titolarelegaleSc = anagrafeService.findById(titolarelegaleSc.getId());
					titolarelegaleSc = gestAnagrafe(titolarelegaleSc, data, session);
					istanzerichiedenti.setAnagrafeCollegata(titolarelegaleSc);
				    }
				    // ISTANZE.PROFESSIONISTA
				    Anagrafe professionistaSc = istanzerichiedenti.getProcuratore();
				    if (professionistaSc != null) {
					aggiornaIstanzeRichiedenti = true;
					// if (istanzerichiedenti.getProcuratorestorico() == null) {
					session.evict(professionistaSc);
					professionistaSc = anagrafeService.findById(professionistaSc.getId());
					professionistaSc = gestAnagrafe(professionistaSc, data, session);
					istanzerichiedenti.setProcuratore(professionistaSc);
				    }
				    if (aggiornaIstanzeRichiedenti) {
					activityLogInfo("Aggiorno i soggetti collegati dell'istanza {}", new Object[] { istanze.getId() });
					try {
					    istanzerichiedentiService.update(istanzerichiedenti);
					    session.flush();
					} catch (Exception e) {
					    handleErrorWithBusinessValidationErrorMessage(e,
						    "Errore nell'aggiornamento dei soggetti collegati dell'istanza " + istanze.getId());
					}
				    }
				}
				session.flush();
				this.commitTransaction();
				activityLogInfo("Terminata la Bonifica dell'istanza: {}", new Object[] { istanze.getId() });
				session.clear();
			    }
			}
		    }
		}
		activityLogInfo("Terminata la bonifica delle istanze");
		if (eseguiBloccoAutorizzazioniVal) {
		    eseguiBloccoAutorizzazioni(session, runTo, executeWithTime);
		}
		if (eliminaStoriciNonUtilizzatiVal) {
		    // effettuo la bonifica di anagrafe storico (elimina se ci sono record in anagrafe storico inutilizzati)
		    activityLogInfo("Bonifico la tabella anagrafica storico per eliminare le righe non utilizzate");
		    bonificaAnagrafeStorico(session, runTo, executeWithTime);
		}
		// ciclo le anagrafiche abilitate e che non hanno un record in anagrafestorico per bonificarle
		bonificaAnagraficheAttiveSenzaStorico(session, runTo, run, executeWithTime, pageSize);
	    } // END CANCELLASOLOANAGRAFICHE DISABILITATE
	      // provo a cancellare le anagrafiche disabilitate. Se c'è un errore allora significa che non deve essere cancellata perché usata in altre relazioni
	    eliminaAnagraficheDisabilitate(session, runTo, executeWithTime);
	    activityLogInfo("Aggiornamento anagrafica completato");
	} catch (Exception e) {
	    handleErrorCondition(e);
	} finally {
	    ORMHelper.setIdcomune(origIdcomune);
	    SigeproBusinessRules.buildDefaultRules();
	}
	return 0;
    }

    private void eseguiBloccoAutorizzazioni(Session session, Date runTo, boolean executeWithTime) {

	String hql = null;
	Query query = null;
	List<Object[]> listi = null;
	////// AUTORIZZAZIONI
	hql = "select this_.id.codice, this_.id.idcomune, this_.anagrafeId,this_.autorizdata from Autorizzazioni this_ where this_.anagrafeId is not null"
		+ " order by this_.id.idcomune, this_.id.codice";
	query = session.createQuery(hql);
	activityLogInfo("Inizio la bonifica delle Autorizzazioni");
	listi = query.list();
	String hqlUpdateAutorizzazioni = "update Autorizzazioni i set i.anagrafeId=? where i.id.idcomune=? and i.id.codice=?";
	for (Object valoriQuery : listi) {
	    if (!checkexecutionTime(runTo, executeWithTime)) {
		return;
	    }
	    Object[] result = (Object[]) valoriQuery;
	    Integer codiceAutorizzazione = (Integer) result[0];
	    String idcomune = (String) result[1];
	    Integer codiceAnagrafe = (Integer) result[2];
	    Date autorizdata = (Date) result[3];
	    // devo settare l'idcomune per forza su ORMHelper		
	    ORMHelper.setIdcomune(idcomune);
	    if (codiceAnagrafe != null) {
		if (autorizdata == null) {
		    autorizdata = Calendar.getInstance().getTime();
		}
		Anagrafe titolare = anagrafeService.findById(new PkId(codiceAnagrafe));
		titolare = gestAnagrafe(titolare, autorizdata, session);
		if (titolare != null) {
		    if (!titolare.getId().getCodice().equals(codiceAnagrafe)) {
			try {
			    query = session.createQuery(hqlUpdateAutorizzazioni);
			    query.setParameter(0, titolare.getId().getCodice(), new IntegerType());
			    query.setString(1, idcomune);
			    query.setInteger(2, codiceAutorizzazione);
			    query.executeUpdate();
			    activityLogInfo("Bonifico l'autorizzazione: {}-{} dal richiedente {} al richiedente {}", new Object[] {
				    codiceAutorizzazione, idcomune, codiceAnagrafe, titolare.getId().getCodice() });
			} catch (Exception e) {
			    handleErrorWithBusinessValidationErrorMessage(e, "Errore nell'aggiornamento dell'autorizzazione " + codiceAutorizzazione
				    + "-" + idcomune);
			}
			session.flush();
			this.commitTransaction();
			activityLogInfo("Terminata la Bonifica dell'autorizzazione: {}-{}", new Object[] { codiceAutorizzazione, idcomune });
		    }
		}
	    }
	    session.clear();
	}
	activityLogInfo("Terminata la bonifica delle autorizzazioni");
	//////
	////// AUTORIZZAZIONI_SUBENTRI
	hql = "select this_.id.codice, this_.id.idcomune, this_.anagrafeId,this_.autorizdata from AutorizzazioniSubentri this_ where this_.anagrafeId is not null"
		+ " order by this_.id.idcomune, this_.id.codice";
	query = session.createQuery(hql);
	activityLogInfo("Inizio la bonifica dei subentri delle Autorizzazioni");
	// boolean aggiornaIstanza = false;	   
	listi = query.list();
	String hqlUpdateAutorizzazioniSubentri = "update AutorizzazioniSubentri i set i.anagrafeId=? where i.id.idcomune=? and i.id.codice=?";
	for (Object valoriQuery : listi) {
	    if (!checkexecutionTime(runTo, executeWithTime)) {
		return;
	    }
	    Object[] result = (Object[]) valoriQuery;
	    Integer codiceSubentro = (Integer) result[0];
	    String idcomune = (String) result[1];
	    Integer codiceAnagrafe = (Integer) result[2];
	    Date autorizdata = (Date) result[3];
	    // devo settare l'idcomune per forza su ORMHelper		
	    ORMHelper.setIdcomune(idcomune);
	    if (codiceAnagrafe != null) {
		if (autorizdata == null) {
		    autorizdata = Calendar.getInstance().getTime();
		}
		Anagrafe titolare = anagrafeService.findById(new PkId(codiceAnagrafe));
		titolare = gestAnagrafe(titolare, autorizdata, session);
		if (titolare != null) {
		    if (!titolare.getId().getCodice().equals(codiceAnagrafe)) {
			try {
			    query = session.createQuery(hqlUpdateAutorizzazioniSubentri);
			    query.setParameter(0, titolare.getId().getCodice(), new IntegerType());
			    query.setString(1, idcomune);
			    query.setInteger(2, codiceSubentro);
			    query.executeUpdate();
			    activityLogInfo("Bonifico il subentro: {}-{} dal richiedente {} al richiedente {}", new Object[] { codiceSubentro,
				    idcomune, codiceAnagrafe, titolare.getId().getCodice() });
			} catch (Exception e) {
			    handleErrorWithBusinessValidationErrorMessage(e, "Errore nell'aggiornamento del subentro " + codiceSubentro + "-"
				    + idcomune);
			}
			session.flush();
			this.commitTransaction();
			activityLogInfo("Terminata la Bonifica del subentro: {}-{}", new Object[] { codiceSubentro, idcomune });
		    }
		}
	    }
	    session.clear();
	}
	activityLogInfo("Terminata la bonifica dei subentri");
	////// AUTORIZZAZIONI_SUBENTRI
    }

    private void bonificaAnagraficheAttiveSenzaStorico(Session session, Date runTo, boolean run, boolean executeWithTime, int pageSize) {

	int pageNumber = 0;
	Integer count = 0;
	Criteria crit = null;
	List<Integer> counts = null;
	List<Anagrafe> anagrafes = null;
	activityLogInfo("ciclo le anagrafiche abilitate e che non hanno un record in anagrafestorico per bonificarle");
	try {
	    crit = session.createCriteria(Anagrafe.class);
	    crit.add(Restrictions.eq("flagDisabilitato", Integer.valueOf(0)));
	    crit.add(Restrictions.isEmpty("anagrafestoricos"));
	    crit.setProjection(Projections.rowCount());
	    counts = crit.list();
	    count = counts.get(0);
	} catch (Exception e1) {
	    handleErrorCondition(e1, "Errore nella lettura delle anagrafiche abilitate.");
	}
	if (count > 0) {
	    if (count < pageSize) {
		pageNumber = 1;
	    } else {
		pageNumber = count / pageSize;
	    }
	    for (int i = 0; i < pageNumber; i++) {
		if (run) {
		    if (!checkexecutionTime(runTo, executeWithTime)) {
			return;
		    }
		    anagrafes = new ArrayList<Anagrafe>();
		    try {
			crit = session.createCriteria(Anagrafe.class);
			crit.add(Restrictions.eq("flagDisabilitato", Integer.valueOf(0)));
			crit.add(Restrictions.isEmpty("anagrafestoricos"));
			crit.addOrder(Order.asc("id.idcomune"));
			crit.setFirstResult(i * pageSize);
			crit.setMaxResults(pageSize);
			anagrafes = crit.list();
		    } catch (Exception e) {
			handleErrorCondition(e, "Errore nella lettura delle anagrafiche abilitate.");
		    }
		    for (Anagrafe anagrafe : anagrafes) {
			String idcomune = anagrafe.getId().getIdcomune();
			ORMHelper.setIdcomune(idcomune);
			activityLogInfo("Aggiorno l'anagrafe [{}], {}", new Object[] { anagrafe.getId(), anagrafe.getDescrizioneRichiedente() });
			try {
			    anagrafeService.update(anagrafe);
			} catch (Exception e) {
			    String message = MessageFormat.format("Errore nell'aggiornamento dell'anagrafe {0}, {1}", new Object[] {
				    anagrafe.getId(), anagrafe.getDescrizioneRichiedente() });
			    handleErrorWithBusinessValidationErrorMessage(e, message);
			}
		    }
		    session.flush();
		    activityLogInfo("Bonificate {} anagrafiche abilitate su {}.", new Object[] { (i + 1) * pageSize, count });
		    this.commitTransaction();
		    session.clear();
		}
	    }
	}
    }

    private void bonificaAnagrafeStorico(Session session, Date runTo, boolean executeWithTime) {

	try {
	    String sql = "select anagrafestorico.id as id, anagrafestorico.idcomune as idcomune from anagrafestorico left join vw_anagrafestorico_utilizzo on anagrafestorico.idcomune=vw_anagrafestorico_utilizzo.idcomune and "
		    + " anagrafestorico.id=vw_anagrafestorico_utilizzo.idstorico where vw_anagrafestorico_utilizzo.idstorico is null and datafinevalidita is not null";
	    SQLQuery query = session.createSQLQuery(sql);
	    query.addScalar("id", Hibernate.INTEGER);
	    query.addScalar("idcomune", Hibernate.STRING);
	    List list = query.list();
	    String sqlDelete = "delete from Anagrafestorico where idcomune=? and id=?";
	    for (Object valori : list) {
		if (!checkexecutionTime(runTo, executeWithTime)) {
		    return;
		}
		Object[] result = (Object[]) valori;
		Integer idStorico = (Integer) result[0];
		String idcomune = (String) result[1];
		try {
		    // per ogni riga che trovo eseguo la cancellazione
		    query = session.createSQLQuery(sqlDelete);
		    query.addScalar("idcomune", Hibernate.STRING);
		    query.addScalar("id", Hibernate.INTEGER);
		    query.setString(0, idcomune);
		    query.setInteger(1, idStorico);
		    query.executeUpdate();
		    this.commitTransaction();
		    session.flush();
		    session.clear();
		} catch (Exception e3) {
		    handleErrorCondition(e3, "disabilitaAnagrafe: Errore durante la procedura di bonificaAnagrafeStorico");
		}
	    }
	} catch (Exception e3) {
	    handleErrorCondition(e3, "disabilitaAnagrafe: Errore durante la procedura di bonificaAnagrafeStorico");
	}
    }

    private void eliminaAnagraficheDisabilitate(Session session, Date runTo, boolean executeWithTime) {

	String hql = "select this_.id.codice, this_.id.idcomune from Anagrafe this_ where this_.flagDisabilitato<>? order by this_.id.idcomune";
	Query query = session.createQuery(hql);
	query.setInteger(0, 0);
	activityLogInfo("Inizio la cancellazione delle anagrafiche disabilitate");
	// boolean aggiornaIstanza = false;
	List<Object[]> listi = query.list();
	for (Object valoriQuery : listi) {
	    if (!checkexecutionTime(runTo, executeWithTime)) {
		return;
	    }
	    session.clear();
	    Object[] result = (Object[]) valoriQuery;
	    Integer codiceAnagrafe = (Integer) result[0];
	    String idcomune = (String) result[1];
	    // devo settare l'idcomune per forza su ORMHelper
	    ORMHelper.setIdcomune(idcomune);
	    Anagrafe anagrafe = null;
	    try {
		anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
		activityLogInfo("Provo a cancellare l'anagrafe [{}], {}", new Object[] { anagrafe.getId(), anagrafe.getDescrizioneRichiedente() });
		anagrafeService.delete(anagrafe);
		session.flush();
		this.commitTransaction();
		session.flush();
		activityLogInfo("Anagrafica Cancellata");
	    } catch (DataAccessException e) {
		if (anagrafe != null) {
		    errorLogError("Non è possibile cancellare l'anagrafica[{}] a causa di  {}", new Object[] { anagrafe.getDescrizioneRichiedente(),
			    e.getMessage() });
		    String errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + anagrafe.getId() + "], "
			    + anagrafe.getDescrizioneRichiedente();
		    handleErrorWithBusinessValidationErrorMessage(e, errMsg);
		} else {
		    errorLogError("Non è possibile cancellare l'anagrafica[{}-{}] a causa di  {}",
			    new Object[] { codiceAnagrafe, idcomune, e.getMessage() });
		    String errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + codiceAnagrafe + "-" + idcomune + "] ";
		    handleErrorWithBusinessValidationErrorMessage(e, errMsg);
		}
	    } catch (BusinessValidationException e) {
		if (anagrafe != null) {
		    errorLogError("Non è possibile cancellare l'anagrafica[{}] a causa di  {}", new Object[] { anagrafe.getDescrizioneRichiedente(),
			    e.getMessage() });
		    String errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + anagrafe.getId() + "], "
			    + anagrafe.getDescrizioneRichiedente();
		    handleErrorWithBusinessValidationErrorMessage(e, errMsg);
		} else {
		    errorLogError("Non è possibile cancellare l'anagrafica[{}-{}] a causa di  {}",
			    new Object[] { codiceAnagrafe, idcomune, e.getMessage() });
		    String errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + codiceAnagrafe + "-" + idcomune + "] ";
		    handleErrorWithBusinessValidationErrorMessage(e, errMsg);
		}
	    } catch (Exception e) {
		String errMsg = "";
		if (anagrafe != null) {
		    errorLogError("Non è possibile cancellare l'anagrafica[{}] a causa di  {}", new Object[] { anagrafe.getDescrizioneRichiedente(),
			    e.getMessage() });
		    errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + anagrafe.getId() + "], "
			    + anagrafe.getDescrizioneRichiedente();
		} else {
		    errorLogError("Non è possibile cancellare l'anagrafica {}-{} a causa di  {}",
			    new Object[] { codiceAnagrafe, idcomune, e.getMessage() });
		    errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + codiceAnagrafe + "-" + idcomune + "]";
		}
		handleErrorCondition(e, errMsg);
	    }
	}
    }

    protected boolean checkexecutionTime(Date runTo, boolean executeWithTime) {

	boolean run = true;
	if (executeWithTime) {
	    if (runTo.getTime() <= new Date().getTime()) {
		run = false;
		activityLogInfo("Terminato il tempo di esecuzione del task impostato a " + runTo);
	    }
	}
	return run;
    }

    protected Anagrafe gestAnagrafe(Anagrafe anagrafe, Date dataValidita, Session session) {

	Anagrafe result = anagrafe;
	if (anagrafe != null) {
	    String descrizioneRichiedente = anagrafe.getDescrizioneRichiedente();
	    PkId idAnagrafe = anagrafe.getId();
	    activityLogInfo("gestAnagrafe: {}, [{}]", new Object[] { descrizioneRichiedente, idAnagrafe });
	    Integer codiceAnagrafe = anagrafe.getId().getCodice();
	    Integer disabilitato = anagrafe.getFlagDisabilitato() == null ? 0 : anagrafe.getFlagDisabilitato();
	    if (disabilitato.intValue() == 0) {
		// SE ANAGRAFE E' ABILITATA
		activityLogInfo("gestAnagrafe: [{}] è abilitato, controllo se è l'unica anagrafe abilitata.", new Object[] { idAnagrafe });
		// SE NON E' UNICA ANAGRAFE ABILITATA E NON COINCIDE CON QUESTA PASSATA ALLORA DISABILITO QUESTA
		String tipoAnagrafe = StringUtils.defaultIfEmpty(anagrafe.getTipoanagrafe(), WebConstants.PERSONA_FISICA);
		boolean checkAnagrafe = true;
		if (tipoAnagrafe.equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		    if (StringUtils.isBlank(StringUtils.defaultIfEmpty(anagrafe.getCodicefiscale(), "").trim())) {
			checkAnagrafe = false;
		    }
		} else {
		    if (StringUtils.isBlank(StringUtils.defaultIfEmpty(anagrafe.getPartitaiva(), "").trim())
			    && StringUtils.isBlank(StringUtils.defaultIfEmpty(anagrafe.getCodicefiscale(), "").trim())) {
			checkAnagrafe = false;
		    }
		}
		if (checkAnagrafe) {
		    boolean disabilitaAnagrafe = false;
		    // controllo l'anagrafe solamente se TIPOANAGRAFE=F e codicefiscale settato o TIPOANAGRAFE=G e piva settata o cf settato
		    List<Anagrafe> anagrafes = findAnagrafeAbilitataByFilter(anagrafe);
		    for (Anagrafe anagrafe2 : anagrafes) {
			if (!anagrafe2.getId().getCodice().equals(codiceAnagrafe)) {
			    disabilitaAnagrafe = true;
			}
			break;
		    }
		    if (disabilitaAnagrafe) {
			activityLogInfo("gestAnagrafe: [{}], non è l'unica abilitata la disabilito", new Object[] { idAnagrafe });
			// disabilito questa anagrafica e chiamo gestanagrafeabilitata
			disabilitaAnagrafe(anagrafe, session);
			activityLogInfo("gestAnagrafe: [{}], non è l'unica abilitata chiamo gestAnagrafeAbilitata", new Object[] { idAnagrafe });
			result = gestAnagrafeAbilitata(anagrafe, dataValidita, session);
		    } else {
			activityLogInfo("gestAnagrafe: è l'unica abilitata aggiorno l'anagrafica [{}], {}",
				new Object[] { result.getId(), result.getDescrizioneRichiedente() });
			try {
			    anagrafeService.update(result);
			} catch (Exception e) {
			    String errMsg = MessageFormat.format("Errore nell'aggiornamento dell'anagrafica [{0}], {1}",
				    new Object[] { result.getId(), result.getDescrizioneRichiedente() });
			    handleErrorWithBusinessValidationErrorMessage(e, errMsg);
			}
		    }
		}
	    } else {
		activityLogInfo("gestAnagrafe: [{}] è disabilitato chiamo gestAnagrafeAbilitata.", new Object[] { anagrafe.getId() });
		result = gestAnagrafeAbilitata(anagrafe, dataValidita, session);
	    }
	}
	return result;
    }

    private Anagrafe gestAnagrafeAbilitata(Anagrafe anagrafe, Date dataValidita, Session session) {

	activityLogDebug("gestAnagrafeAbilitata: cerco una anagrafica abilitata per {}.", new Object[] { anagrafe.getDescrizioneRichiedente() });
	AnagrafeFilter filtro = new AnagrafeFilter();
	filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
	Anagrafe datiAnagrafe = new Anagrafe();
	datiAnagrafe.setTipoanagrafe(anagrafe.getTipoanagrafe());
	datiAnagrafe.setCodicefiscale(anagrafe.getCodicefiscale());
	datiAnagrafe.setPartitaiva(anagrafe.getPartitaiva());
	datiAnagrafe.setFlagDisabilitato(Integer.valueOf(0));
	datiAnagrafe.setNominativo(anagrafe.getNominativo());
	datiAnagrafe.setNome(anagrafe.getNome());
	datiAnagrafe.setTipologia(anagrafe.getTipologia());
	filtro.setDatiAnagrafe(datiAnagrafe);
	filtro.setOrderAscDesc(new OrderTypeEnum[] { OrderTypeEnum.DESC });
	filtro.setOrderBy(new String[] { "id.codice" });
	// 1. cerco l'anagrafe abilitata e se la trovo tramite codicefiscale, partita iva, nominativo, nome, tipologia
	List<Anagrafe> anagrafes = anagrafeService.findByFilter(filtro);
	Anagrafe result = null;
	if (anagrafes.size() > 0) {
	    result = anagrafes.get(0);
	    activityLogDebug("gestAnagrafeAbilitata: Trovata l'anagrafe abilitata {}.", new Object[] { result.getDescrizioneRichiedente() });
	}
	if (result == null) {
	    //  CASO PERSONA FISICA
	    if (StringUtils.defaultIfEmpty(anagrafe.getTipoanagrafe(), WebConstants.PERSONA_FISICA).equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
		if (StringUtils.isNotBlank(anagrafe.getCodicefiscale())) {
		    // CERCO ANCHE PER TIPOLOGIA
		    datiAnagrafe = new Anagrafe();
		    datiAnagrafe.setTipoanagrafe(anagrafe.getTipoanagrafe());
		    datiAnagrafe.setCodicefiscale(anagrafe.getCodicefiscale());
		    datiAnagrafe.setFlagDisabilitato(Integer.valueOf(0));
		    datiAnagrafe.setTipologia(anagrafe.getTipologia());
		    filtro.setDatiAnagrafe(datiAnagrafe);
		    anagrafes = anagrafeService.findByFilter(filtro);
		    if (anagrafes.size() > 0) {
			result = anagrafes.get(0);
			activityLogDebug("gestAnagrafeAbilitata: Trovata l'anagrafe abilitata {} per codicefiscale {}, tipologia {}", new Object[] {
				result.getDescrizioneRichiedente(), anagrafe.getCodicefiscale(), anagrafe.getTipologia() });
		    }
		    // SE NON TROVATO CERCO PER PF SENZA TIPOLOGIA
		    if (result == null) {
			datiAnagrafe = new Anagrafe();
			if (StringUtils.isNotBlank(anagrafe.getCodicefiscale())) {
			    datiAnagrafe.setTipoanagrafe(anagrafe.getTipoanagrafe());
			    datiAnagrafe.setCodicefiscale(anagrafe.getCodicefiscale());
			    datiAnagrafe.setFlagDisabilitato(Integer.valueOf(0));
			    datiAnagrafe.setTipologia(null);
			    filtro.setDatiAnagrafe(datiAnagrafe);
			    anagrafes = anagrafeService.findByFilter(filtro);
			    if (anagrafes.size() > 0) {
				result = anagrafes.get(0);
				activityLogDebug("gestAnagrafeAbilitata: Trovata l'anagrafe abilitata {} per codicefiscale {}.", new Object[] {
					result.getDescrizioneRichiedente(), anagrafe.getCodicefiscale() });
			    }
			}
		    }
		}
	    } else {
		//  CASO PERSONA GIURIDICA
		if (StringUtils.isNotBlank(anagrafe.getPartitaiva()) || StringUtils.isNotBlank(anagrafe.getCodicefiscale())) {
		    anagrafes = bindPersonaGiuridica(anagrafe);
		    if (anagrafes.size() > 0) {
			result = anagrafes.get(0);
			activityLogDebug("gestAnagrafeAbilitata: Trovata l'anagrafe abilitata {} per partita iva {}.",
				new Object[] { result.getDescrizioneRichiedente(), anagrafe.getPartitaiva() });
		    }
		}
	    }
	}
	// se non trovo nessun bind con i dati passati allora riabilito l'anagrafica 
	if (result == null) {
	    if (StringUtils.defaultIfEmpty(anagrafe.getTipoanagrafe(), "F").equals("F")) {
		if (StringUtils.isNotBlank(anagrafe.getCodicefiscale())) {
		    anagrafe.setFlagDisabilitato(Integer.valueOf(0));
		}
	    } else {
		if (StringUtils.isNotBlank(anagrafe.getPartitaiva())) {
		    anagrafe.setFlagDisabilitato(Integer.valueOf(0));
		}
	    }
	    result = anagrafe;
	}
	// 1.1 bonifico anagrafe storico
	activityLogInfo("gestAnagrafeAbilitata: Aggiorno l'anagrafica [{}], {}", new Object[] { result.getId(), result.getDescrizioneRichiedente() });
	try {
	    anagrafeService.update(result);
	} catch (Exception e) {
	    String errMsg = MessageFormat.format("Errore nell'aggiornamento dell'anagrafica [{0}], {1}",
		    new Object[] { result.getId(), result.getDescrizioneRichiedente() });
	    handleErrorWithBusinessValidationErrorMessage(e, errMsg);
	}
	// 1.2 inserisco un'anagrafe storico con la data validità presa dall'istanza
	activityLogInfo("gestAnagrafeAbilitata: inserisco un'anagrafe storico con la data validità presa dall'istanza [{}]",
		new Object[] { dataValidita });
	try {
	    anagrafeService.insertNewStoricoFromAnagrafe(result, anagrafe, dataValidita);
	} catch (Exception e) {
	    handleErrorWithBusinessValidationErrorMessage(e, "Errore nell'inserimento di un record in ANAGRAFESTORICO.");
	}
	session.flush();
	this.commitTransaction();
	return result;
    }

    protected void handleErrorWithBusinessValidationErrorMessage(Exception e, String errorMessage) {

	StringBuilder sbErr = new StringBuilder();
	if (errorMessage == null && e != null) {
	    errorMessage = e.getMessage();
	}
	if (errorMessage != null) {
	    sbErr.append(errorMessage);
	}
	if (e instanceof BusinessValidationException || e instanceof EntityValidationException) {
	    List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
	    sbErr.append(". BusinessValidationError: ");
	    for (InvalidValue invalidValue : ivs) {
		sbErr.append(invalidValue.getMessage()).append(",[");
		sbErr.append(invalidValue.getBeanClass()).append("],[");
		sbErr.append(invalidValue.getPropertyPath()).append("],[");
		sbErr.append(invalidValue.getPropertyName()).append("],[");
		sbErr.append(invalidValue.getValue()).append("]\r\n");
	    }
	}
	handleErrorCondition(e, sbErr.toString());
    }

    @Override
    public void initialize() throws SetupRunException {

    }

    private List<Anagrafe> findAnagrafeAbilitataByFilter(Anagrafe anagrafe) {

	String tipoAnagrafe = StringUtils.defaultIfEmpty(anagrafe.getTipoanagrafe(), WebConstants.PERSONA_FISICA);
	if (tipoAnagrafe.equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
	    return bindPersonaFisica(anagrafe);
	} else {
	    return bindPersonaGiuridica(anagrafe);
	}
    }

    /**
     * Nel caso di persona giuridica Cerco prima per Partita IVA. Se non trovato per codiceFiscale.
     * 
     * @param anagrafe
     * @return
     */
    private List<Anagrafe> bindPersonaGiuridica(Anagrafe anagrafe) {

	AnagrafeFilter filtro = new AnagrafeFilter();
	filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
	Anagrafe datiAnagrafe = new Anagrafe();
	datiAnagrafe.setTipoanagrafe(anagrafe.getTipoanagrafe());
	// Se Piva Settata cerco solo per Piva altrimenti per CodiceFiscale
	if (StringUtils.isNotBlank(anagrafe.getPartitaiva())) {
	    datiAnagrafe.setPartitaiva(anagrafe.getPartitaiva());
	    // ordino per l'ultimo inserimento descrescente (codiceanagrafe desc)
	    filtro.setOrderBy(new String[] { "id.codice" });
	    filtro.setOrderAscDesc(new OrderTypeEnum[] { OrderTypeEnum.DESC });
	} else {
	    datiAnagrafe.setCodicefiscale(anagrafe.getCodicefiscale());
	    filtro.setOrderBy(new String[] { "partitaiva", "id.codice" });
	    filtro.setOrderAscDesc(new OrderTypeEnum[] { OrderTypeEnum.ASC, OrderTypeEnum.DESC });
	}
	datiAnagrafe.setFlagDisabilitato(Integer.valueOf(0));
	filtro.setDatiAnagrafe(datiAnagrafe);
	List<Anagrafe> anagrafes = anagrafeService.findByFilter(filtro);
	return anagrafes;
    }

    private List<Anagrafe> bindPersonaFisica(Anagrafe anagrafe) {

	AnagrafeFilter filtro = new AnagrafeFilter();
	filtro.setTipoRicercaEnum(TipoRicercaEnum.EQUALSIGNORECASE);
	Anagrafe datiAnagrafe = new Anagrafe();
	datiAnagrafe.setTipoanagrafe(anagrafe.getTipoanagrafe());
	datiAnagrafe.setCodicefiscale(anagrafe.getCodicefiscale());
	datiAnagrafe.setTipologia(anagrafe.getTipologia());
	datiAnagrafe.setFlagDisabilitato(Integer.valueOf(0));
	filtro.setDatiAnagrafe(datiAnagrafe);
	// ordino per l'ultimo inserimento descrescente (codiceanagrafe desc)
	filtro.setOrderBy(new String[] { "id.codice" });
	filtro.setOrderAscDesc(new OrderTypeEnum[] { OrderTypeEnum.DESC });
	List<Anagrafe> anagrafes = anagrafeService.findByFilter(filtro);
	return anagrafes;
    }

    private void disabilitaAnagrafe(Anagrafe anagrafe, Session session) {

	try {
	    String hql = "update Anagrafe anagrafe set anagrafe.flagDisabilitato=?, dataDisabilitato=? where anagrafe.id.idcomune=? and anagrafe.id.codice=?";
	    Query query = session.createQuery(hql);
	    query.setInteger(0, 1);
	    query.setDate(1, Calendar.getInstance().getTime());
	    query.setString(2, anagrafe.getId().getIdcomune());
	    query.setInteger(3, anagrafe.getId().getCodice());
	    query.executeUpdate();
	    this.commitTransaction();
	    session.flush();
	} catch (Exception e3) {
	    handleErrorCondition(e3, "disabilitaAnagrafe: Errore durante la disabilitazione di anagrafe " + anagrafe.getId());
	}
    }

    protected void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    protected void setIstanzerichiedentiService(IstanzerichiedentiService istanzerichiedentiService) {

	this.istanzerichiedentiService = istanzerichiedentiService;
    }
}
