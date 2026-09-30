package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MailConfigId;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpdateMailConfigTask")
public class UpdateMailConfigTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateMailConfigTask.class);
    @Autowired
    private MailConfigService mailConfigService;

    @Override
    public int run(Session session) throws SetupRunException {

	String origIdComune = ORMHelper.getIdcomune();
	activityLogInfo("UpdateMailConfigTask.run: inizio aggiornamento");
	/*
	 * 
	Nella tabella MAIL_CONFIG è stata aggiunta la colonna SOFTWARE e la tabella
	MAIL_CONFIGSW è stata dismessa.
	Bisogna che UPGR faccia:
	1-metta a TT i record che hanno la colonna software=null
	2-sposti i record da MAIL_CONFIGSW a MAIL_CONFIG aggiornando i campi che
	rimangono a null con quelli presenti nella riga  TT (che era l'unica riga
	presente in MAIL_CONFIG prima dell'upgr.
	 */
	// String idComune = ORMHelper.getIdcomune();
	// 1.
	String hql = "update MailConfig mc set mc.id.software = :newSoftware where mc.id.software is null";
	Query query = session.createQuery(hql);
	query.setString("newSoftware", WebConstants.SOFTWARE_TT);
	int rowCount = query.executeUpdate();
	activityLogInfo("1) messi a 'TT' i record di Mail_Config che hanno la colonna software=null. Righe aggiornate: {}", new Object[] { rowCount });
	query = session.createSQLQuery("SELECT SENDERADDRESS, SOFTWARE,IDCOMUNE FROM MAIL_CONFIGSW");
	List mailconfigwss = query.list();
	if (mailconfigwss.size() > 0) {
	    // 2.
	    for (Object object : mailconfigwss) {
		Object[] result = (Object[]) object;
		String idcomune = (String) result[2];
		ORMHelper.setIdcomune(idcomune);
		query = session.createQuery("From MailConfig mc where mc.id.idcomune= :idcomune and mc.id.software = :softwareTT");
		query.setString("idcomune", idcomune);
		query.setString("softwareTT", WebConstants.SOFTWARE_TT);
		List<MailConfig> mailConfigs = query.list();
		for (MailConfig mailConfig : mailConfigs) {
		    String senderaddres = (String) result[0];
		    String software = (String) result[1];
		    MailConfig nuovoConfig = new MailConfig();
		    MailConfigId id = new MailConfigId(idcomune, software);
		    // TODO - non funziona più perchè dalla 2.63 non esiste più la pkid [idcomune,software]
		    //nuovoConfig.setId(id);
		    nuovoConfig.setSenderaddress(senderaddres);
		    nuovoConfig.setLoginname(mailConfig.getLoginname());
		    nuovoConfig.setLoginpass(mailConfig.getLoginpass());
		    nuovoConfig.setMailserver(mailConfig.getMailserver());
		    nuovoConfig.setPort(mailConfig.getPort());
		    nuovoConfig.setUseauthentication(mailConfig.getUseauthentication());
		    nuovoConfig.setUsessl(mailConfig.getUsessl());
		    mailConfigService.insert(nuovoConfig);
		    session.flush();
		    break;
		}
	    }
	    this.commitTransaction();
	}
	ORMHelper.setIdcomune(origIdComune);
	activityLogInfo("UpdateMailConfigTask.run: Fine aggiornamento");
	return rowCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
