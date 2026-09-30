package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ComuniassociatisoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpdateComuniassociatiTask")
public class UpdateComuniassociatiTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateComuniassociatiTask.class);
    private ComuniassociatisoftwareDAO comuniassociatisoftwareDAO;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;

    @Autowired
    public void setComuniassociatisoftwareDAO(ComuniassociatisoftwareDAO comuniassociatisoftwareDAO) {

	this.comuniassociatisoftwareDAO = comuniassociatisoftwareDAO;
    }

    @Override
    public int run(Session session) throws SetupRunException {

	String origIdcomune = ORMHelper.getIdcomune();
	activityLogInfo("UpdateComuniassociatiTask.run: inizio aggiornamento");
	int rowCount = 0;
	Integer codice = null;
	String hql = "From Comuniassociati";
	Query query = session.createQuery(hql);
	List<Comuniassociati> comuniassociatis = query.list();
	for (Comuniassociati comuniassociati : comuniassociatis) {
	    if (EntityUtils.getNestedProperty(comuniassociati, "oggetti.id.codice") != null || comuniassociati.getSiIntestazione1() != null
		    || comuniassociati.getSiIntestazione2() != null || comuniassociati.getSiIntestazione3() != null
		    || comuniassociati.getSiPdp1() != null || comuniassociati.getSiPdp2() != null) {
		String idcomune = comuniassociati.getId().getIdcomune();
		ORMHelper.setIdcomune(idcomune);
		// Verificare se esiste già un record in Comuniassociatisoftware con stessi idcomune, codicecomune e software TT
		String hqlVerifica = "From Comuniassociatisoftware where id.idcomune = :idcomune and "
			+ "comuni.codicecomune = :codicecomune and software=:software";
		Query queryVerifica = session.createQuery(hqlVerifica);
		queryVerifica.setString("idcomune", comuniassociati.getId().getIdcomune());
		queryVerifica.setString("codicecomune", comuniassociati.getId().getCodicecomune());
		queryVerifica.setString("software", WebConstants.SOFTWARE_TT);
		List<Comuniassociati> comuniassociatiVerificas = queryVerifica.list();
		if (comuniassociatiVerificas == null || comuniassociatiVerificas.isEmpty()) {
		    // Select sequence
		    String sql = "SELECT MAX(CURRVAL) VAL FROM SEQUENCETABLE WHERE SEQUENCENAME = ? and IDCOMUNE=?";
		    Query queryid = session.createSQLQuery(sql);
		    queryid.setString(0, "COMUNIASSOCIATISOFTWARE.ID");
		    queryid.setString(1, comuniassociati.getId().getIdcomune());
		    List<BigDecimal> max = queryid.list();
		    for (BigDecimal bigDecimal : max) {
			if (bigDecimal != null) {
			    codice = bigDecimal.intValue();
			}
		    }
		    if (codice == null) {
			codice = 1;
		    }
		    // Insert comuniassociatisoftware		    
		    Comuniassociatisoftware comuniassociatisoftware = new Comuniassociatisoftware();
		    comuniassociatisoftware.setId(new PkId(comuniassociati.getId().getIdcomune(), codice));
		    comuniassociatisoftware.setComuni(comuniService.findById(comuniassociati.getComune().getCodicecomune()));
		    comuniassociatisoftware.setComuniassociati(comuniassociatiService.findById(comuniassociati.getId()));
		    comuniassociatisoftware.setSoftware(WebConstants.SOFTWARE_TT);
		    if (EntityUtils.getNestedProperty(comuniassociati, "oggetti.id.codice") != null) {
			comuniassociatisoftware.setOggetti(oggettiService.findById(new PkId(comuniassociati.getId().getIdcomune(), comuniassociati
				.getOggetti().getId().getCodice())));
		    }
		    if (comuniassociati.getSiIntestazione1() != null) {
			comuniassociatisoftware.setSiIntestazione1(comuniassociati.getSiIntestazione1());
		    }
		    if (comuniassociati.getSiIntestazione2() != null) {
			comuniassociatisoftware.setSiIntestazione2(comuniassociati.getSiIntestazione2());
		    }
		    if (comuniassociati.getSiIntestazione3() != null) {
			comuniassociatisoftware.setSiIntestazione3(comuniassociati.getSiIntestazione3());
		    }
		    if (comuniassociati.getSiPdp1() != null) {
			comuniassociatisoftware.setSiPdp1(comuniassociati.getSiPdp1());
		    }
		    if (comuniassociati.getSiPdp2() != null) {
			comuniassociatisoftware.setSiPdp2(comuniassociati.getSiPdp2());
		    }
		    comuniassociatisoftwareDAO.insert(comuniassociatisoftware);
		    activityLogDebug("Inserito comuniassociatisoftware relativo a COMUNIASSOCIATI(IDCOMUNE, CODICECOMUNE) : ("
			    + comuniassociati.getId().getIdcomune() + "," + comuniassociati.getId().getCodicecomune() + ")");
		    // Update Sequence
		    String sbCurrVal = "UPDATE SEQUENCETABLE SET CURRVAL = ? WHERE SEQUENCENAME = ? and IDCOMUNE=?";
		    SQLQuery qCurrVal = session.createSQLQuery(sbCurrVal);
		    qCurrVal.setInteger(0, ++codice);
		    qCurrVal.setString(1, "COMUNIASSOCIATISOFTWARE.ID");
		    qCurrVal.setString(2, comuniassociati.getId().getIdcomune());
		    rowCount = qCurrVal.executeUpdate();
		    activityLogDebug("sequenza aggiornata");
		    // Update Comuniassociati
		    String hqlCom = "update Comuniassociati ca set ca.oggetti.id.codice = null, ca.siIntestazione1 = null, ca.siIntestazione2 = null, ca.siIntestazione3 = null, ca.siPdp1 = null, ca.siPdp2 = null where ca.id.idcomune = :idcomune and ca.id.codicecomune = :codicecomune";
		    Query queryCom = session.createQuery(hqlCom);
		    queryCom.setString("idcomune", comuniassociati.getId().getIdcomune());
		    queryCom.setString("codicecomune", comuniassociati.getId().getCodicecomune());
		    rowCount = queryCom.executeUpdate();
		    activityLogDebug("Comuniassociati aggiornato {}", new Object[] { comuniassociati.getId().getIdcomune() });
		}
	    }
	}
	ORMHelper.setIdcomune(origIdcomune);
	activityLogDebug("UpdateComuniassociatiTask.run: Fine aggiornamento");
	session.flush();
	this.commitTransaction();
	session.flush();
	return rowCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
