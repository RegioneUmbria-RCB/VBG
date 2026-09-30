package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpdateSoftwareAttiviTask")
public class UpdateSoftwareAttiviTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateSoftwareAttiviTask.class);
    @Autowired
    private SoftwareattiviService softwareattiviService;
    @Autowired
    private SoftwareService softwareService;

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateSoftwareAttiviTask.run: Inizio aggiornamento");
	String origIdcomune = ORMHelper.getIdcomune();
	//	Da comuni security vanno spostati nella tabella softwareattivi i campi
	//	cs_softwareattivi e cs_softwareattivifo.
	String sqlQuery = "SELECT CS_CODICEISTAT,CS_IDCOMUNE,CS_SOFTWAREATTIVI,CS_SOFTWAREATTIVIFO FROM COMUNISECURITY order by CS_CODICEISTAT";
	// String sqlDelete = "DELETE FROM COMUNISECURITY WHERE CS_CODICEISTAT = :idcomunealias";
	Query query = session.createSQLQuery(sqlQuery);
	List rs = query.list();
	String idcomuneAlias = "";
	String idcomune = "";
	String softwareAttivi = "";
	String softwareAttiviFo = "";
	boolean attivoFO = false;
	for (Object object : rs) {
	    Object[] result = (Object[]) object;
	    idcomuneAlias = (String) result[0];
	    idcomune = (String) result[1];
	    softwareAttivi = (String) result[2];
	    softwareAttiviFo = (String) result[3];
	    // SE CS_IDCOMUNE È VUOTO ALLORA IDCOMUNE = CS_CODICEISTAT
	    idcomune = StringUtils.isBlank(idcomune) ? idcomuneAlias : idcomune;
	    ORMHelper.setIdcomune(idcomune);
	    // SE CS_SOFTWAREATTIVIFO È VUOTO ALLORA CS_SOFTWAREATTIVITFO=CS_SOFTWAREATTIVI
	    softwareAttiviFo = StringUtils.isBlank(softwareAttiviFo) ? softwareAttivi : softwareAttiviFo;
	    activityLogDebug("trovata la riga di comunisecurity: idcomunealias [{}], idcomune [{}], softwareattivi [{}], softwareattivifo [{}]",
		    new Object[] { idcomuneAlias, idcomune, softwareAttivi, softwareAttiviFo });
	    if (StringUtils.isNotBlank(softwareAttivi)) {
		String[] softwareAttivati = softwareAttivi.split(",");
		for (String modulo : softwareAttivati) {
		    SoftwareattiviId id = new SoftwareattiviId(idcomune, modulo);
		    Softwareattivi softwareAttivo = softwareattiviService.findById(id);
		    activityLogDebug("cerco se presente nella tabella il record {}-{}", new Object[] { idcomune, modulo });
		    if (softwareAttivo == null) {
			activityLogDebug("Non è presente lo inserisco");
			softwareAttivo = new Softwareattivi();
			softwareAttivo.setId(id);
			Software software = softwareService.findById(modulo);
			softwareAttivo.setSoftware(software);
			// è attivo nel frontoffice se il modulo è contenuto in softwareAttiviFO
			attivoFO = softwareAttiviFo.indexOf(modulo) >= 0;
			softwareAttivo.setAttivoFo(attivoFO);
			softwareattiviService.insert(softwareAttivo);
			session.flush();
		    }
		}
	    }
	    this.commitTransaction();
	    //	    // ELIMINO LA RIGA DA COMUNISECURITY
	    //	    query = session.createSQLQuery(sqlDelete);
	    //	    query.setString("idcomunealias", idcomuneAlias);
	    //	    query.executeUpdate();
	}
	ORMHelper.setIdcomune(origIdcomune);
	return 0;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
