package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.utils.CryptoUtils;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrEncryptMailconfigTask")
public class EncryptMailConfigTask extends BaseJavaTask {

    @Autowired
    private MailConfigService mailConfigService;

    /**
     * <pre>
     * Recupero tutti i record di MAIL_CONFIG dell'installazione e per ogni record crittografo i campi password: LOGINPASS e IN_LOGINPASS
     * </pre>
     */
    @Override
    public int run(Session session) throws SetupRunException {

	//String origIdComune = ORMHelper.getIdcomune();
	activityLogInfo("upgrEncryptMailconfigTask.run: inizio aggiornamento");
	CryptoUtils crypto = new CryptoUtils();
	Query query = session.createSQLQuery("SELECT IDCOMUNE, ID, LOGINPASS, IN_LOGINPASS FROM MAIL_CONFIG");
	List mailconfigs = query.list();
	int encryptedCount = 0;
	for (Object object : mailconfigs) {
	    boolean needEncryption = false;
	    Object[] result = (Object[]) object;
	    BigDecimal id = (BigDecimal) result[1];
	    String pwd = (String) result[2];
	    if (StringUtils.isNotEmpty(pwd)) {
		try {
		    crypto.decrypt(CryptoUtils.DEFAULT_SECRET_KEY, pwd);
		    activityLogInfo("upgrEncryptMailconfigTask.run: MAIL_CFG.ID = " + id.intValue() + ", LOGINPASS già criptato");
		} catch (Exception e) {
		    //se da errore la decriptazione significa che non è ancora criptato e quindi lo devo criptare
		    pwd = crypto.encrypt(CryptoUtils.DEFAULT_SECRET_KEY, pwd);
		    needEncryption = true;
		    activityLogInfo("upgrEncryptMailconfigTask.run: MAIL_CFG.ID = " + id.intValue() + ", criptato LOGINPASS");
		}
	    }
	    String inPwd = (String) result[3];
	    if (StringUtils.isNotEmpty(inPwd)) {
		try {
		    crypto.decrypt(CryptoUtils.DEFAULT_SECRET_KEY, inPwd);
		    activityLogInfo("upgrEncryptMailconfigTask.run: MAIL_CFG.ID = " + id.intValue() + ", IN_LOGINPASS già criptato");
		} catch (Exception e) {
		    //se da errore la decriptazione significa che non è ancora criptato e quindi lo devo criptare
		    inPwd = crypto.encrypt(CryptoUtils.DEFAULT_SECRET_KEY, inPwd);
		    needEncryption = true;
		    activityLogInfo("upgrEncryptMailconfigTask.run: MAIL_CFG.ID = " + id.intValue() + ", criptato LOGINPASS");
		}
	    }
	    if (needEncryption) {
		StringBuffer sql = new StringBuffer("UPDATE MAIL_CONFIG SET ");
		sql.append(" LOGINPASS = ?,");
		sql.append(" IN_LOGINPASS = ?");
		sql.append(" WHERE IDCOMUNE = ? AND ID= ?");
		Query queryid = session.createSQLQuery(sql.toString());
		queryid.setString(0, StringUtils.defaultString(pwd));
		queryid.setString(1, StringUtils.defaultString(inPwd));
		// CONDIZIONI
		queryid.setString(2, (String) result[0]);
		queryid.setBigDecimal(3, id);
		queryid.executeUpdate();
		session.flush();
		encryptedCount++;
	    }
	}
	this.commitTransaction();
	activityLogInfo("upgrEncryptMailconfigTask.run - Fine: crittografate le password di " + encryptedCount + " record in MAIL_CONFIG");
	return encryptedCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
