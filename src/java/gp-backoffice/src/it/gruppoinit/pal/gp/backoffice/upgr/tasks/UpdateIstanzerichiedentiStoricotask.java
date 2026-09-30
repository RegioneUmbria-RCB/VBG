package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpdateIstanzerichiedentiStoricotask")
public class UpdateIstanzerichiedentiStoricotask extends BaseJavaTask {

    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IstanzerichiedentiService istanzerichiedentiService;

    @Override
    public void initialize() throws SetupRunException {

    }

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
     * 
     *            <pre>
     *  &lt;java-task id="UPGR_ISTANZERICHIEDENTISTORICO" 
     *  		spring-bean-id="upgrUpdateIstanzerichiedentiStoricotask" 
     *  		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateIstanzerichiedentiStoricotask" 
     * 			fail-on-error="false">
     *         &lt;param name="runFromDate" value="26/01/2012 - 09:00:01">&lt;/param>
     *         &lt;param name="runToDate" value="26/01/2012 - 17:10:30">&lt;/param>
     *  &lt;/java-task>
     * </pre>
     * 
     * 
     */
    @Override
    public int run(Session session) throws SetupRunException {

	String origIdcomune = ORMHelper.getIdcomune();
	String runFromDate = getParameterValue("runFromDate");
	String runToDate = getParameterValue("runToDate");
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
	String hql = "select this_.id.codice, this_.id.idcomune, software.codice, istanza.data from Istanzerichiedenti this_ inner join this_.istanza istanza inner join istanza.software software "
		+ " where this_.richiedentestoricoId is null order by this_.id.idcomune, software.codice";
	Query query = session.createQuery(hql);
	activityLogInfo("Inizio la bonifica di istanzerichiedenti");
	// boolean aggiornaIstanza = false;	
	List<Object[]> listi = query.list();
	UpdateAnagrafeStoricoTask task = createTask(this);
	boolean aggiornaIstanzeRichiedenti = false;
	int i = 0;
	String hqlUpdateIstanze = "update Istanzerichiedenti i set i.richiedentestoricoId=?, i.anagrafeCollegatastoricoId=?, i.procuratorestoricoId=?,"
		+ "i.richiedenteId=?, i.anagrafeCollegataId=?, i.procuratoreId=? where i.id.idcomune=? and i.id.codice=?";
	for (Object valoriQuery : listi) {
	    i++;
	    if (run) {
		if (!task.checkexecutionTime(runTo, executeWithTime)) {
		    return 0;
		}
		Object[] result = (Object[]) valoriQuery;
		Integer codiceIstanzerichiedenti = (Integer) result[0];
		String idcomune = (String) result[1];
		String software = (String) result[2];
		Date dataIstanza = (Date) result[3];
		// devo settare l'idcomune per forza su ORMHelper
		ORMHelper.setIdcomune(idcomune);
		ORMHelper.setSoftware(software);
		Istanzerichiedenti istanzerichiedenti = istanzerichiedentiService.findById(new PkId(codiceIstanzerichiedenti));
		if (istanzerichiedenti != null) {
		    Anagrafe richiedenteSc = istanzerichiedenti.getRichiedente();
		    aggiornaIstanzeRichiedenti = false;
		    if (richiedenteSc != null) {
			aggiornaIstanzeRichiedenti = true;
			session.evict(richiedenteSc);
			richiedenteSc = anagrafeService.findById(richiedenteSc.getId());
			richiedenteSc = task.gestAnagrafe(richiedenteSc, dataIstanza, session);
		    }
		    // ISTANZE.TITOLARELEGALE
		    Anagrafe anagrafecollegataSc = istanzerichiedenti.getAnagrafeCollegata();
		    if (anagrafecollegataSc != null) {
			aggiornaIstanzeRichiedenti = true;
			session.evict(anagrafecollegataSc);
			anagrafecollegataSc = anagrafeService.findById(anagrafecollegataSc.getId());
			anagrafecollegataSc = task.gestAnagrafe(anagrafecollegataSc, dataIstanza, session);
		    }
		    // ISTANZE.PROFESSIONISTA
		    Anagrafe procuratoreSc = istanzerichiedenti.getProcuratore();
		    if (procuratoreSc != null) {
			aggiornaIstanzeRichiedenti = true;
			session.evict(procuratoreSc);
			procuratoreSc = anagrafeService.findById(procuratoreSc.getId());
			procuratoreSc = task.gestAnagrafe(procuratoreSc, dataIstanza, session);
		    }
		    if (aggiornaIstanzeRichiedenti) {
			activityLogInfo("Aggiorno i soggetti collegati di istanzerichiedenti {}", new Object[] { istanzerichiedenti.getId() });
			try {
			    Anagrafestorico richiedenteStorico = null;
			    if (EntityUtils.getNestedProperty(richiedenteSc, "id.codice") != null) {
				richiedenteStorico = anagrafeService.findStoricoId(richiedenteSc, dataIstanza);
				if (richiedenteStorico != null) {
				    session.evict(richiedenteStorico);
				}
			    }
			    Anagrafestorico anagrafecollegatastoricoStorico = null;
			    if (EntityUtils.getNestedProperty(anagrafecollegataSc, "id.codice") != null) {
				anagrafecollegatastoricoStorico = anagrafeService.findStoricoId(anagrafecollegataSc, dataIstanza);
				if (anagrafecollegatastoricoStorico != null) {
				    session.evict(anagrafecollegatastoricoStorico);
				}
			    }
			    Anagrafestorico procuratoreStorico = null;
			    if (EntityUtils.getNestedProperty(procuratoreSc, "id.codice") != null) {
				procuratoreStorico = anagrafeService.findStoricoId(procuratoreSc, dataIstanza);
				if (procuratoreStorico != null) {
				    session.evict(procuratoreStorico);
				}
			    }
			    query = session.createQuery(hqlUpdateIstanze);
			    Integer richiedenteStoricoId = (Integer) EntityUtils.getNestedProperty(richiedenteStorico, "id.codice");
			    Integer anagrafecollegataStoricoId = (Integer) EntityUtils
				    .getNestedProperty(anagrafecollegatastoricoStorico, "id.codice");
			    Integer procuratorestoricoId = (Integer) EntityUtils.getNestedProperty(procuratoreStorico, "id.codice");
			    Integer richiedenteId = (Integer) EntityUtils.getNestedProperty(richiedenteSc, "id.codice");
			    Integer titolareLegaleId = (Integer) EntityUtils.getNestedProperty(anagrafecollegataSc, "id.codice");
			    Integer professionistaId = (Integer) EntityUtils.getNestedProperty(procuratoreSc, "id.codice");
			    query.setParameter(0, richiedenteStoricoId, new IntegerType());
			    query.setParameter(1, anagrafecollegataStoricoId, new IntegerType());
			    query.setParameter(2, procuratorestoricoId, new IntegerType());
			    query.setParameter(3, richiedenteId, new IntegerType());
			    query.setParameter(4, titolareLegaleId, new IntegerType());
			    query.setParameter(5, professionistaId, new IntegerType());
			    query.setString(6, idcomune);
			    query.setInteger(7, codiceIstanzerichiedenti);
			    query.executeUpdate();
			    session.flush();
			    this.commitTransaction();
			    session.flush();
			} catch (Exception e) {
			    task.handleErrorWithBusinessValidationErrorMessage(e,
				    "Errore nell'aggiornamento dei soggetti collegati di istanzerichiedenti " + istanzerichiedenti.getId());
			}
		    }
		}
	    }
	    if (i == 100) {
		i = 0;
		session.flush();
		session.clear();
	    }
	}
	this.commitTransaction();
	session.flush();
	session.clear();
	ORMHelper.setIdcomune(origIdcomune);
	return 0;
    }

    private UpdateAnagrafeStoricoTask createTask(BaseJavaTask parent) {

	UpdateAnagrafeStoricoTask task = new UpdateAnagrafeStoricoTask();
	task.setAnagrafeService(this.anagrafeService);
	task.setIstanzeService(this.istanzeService);
	task.setIstanzerichiedentiService(this.istanzerichiedentiService);
	task.setTaskDefinition(parent.getTaskDefinition());
	return task;
    }
}
