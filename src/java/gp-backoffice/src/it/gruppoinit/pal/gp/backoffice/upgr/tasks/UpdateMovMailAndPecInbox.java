package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpdateMovMailAndPecInbox")
public class UpdateMovMailAndPecInbox extends BaseJavaTask {

    @Autowired
    private MailConfigService mailConfigService;

    /**
     * <pre>
     * 
     * 	UPGR MOVIMENTIEMAIL --> POPOLAMENTO CAMPO ACCOUNTI_ID   
     *  	1. Recupero le coppie [idcomune,software] presenti in movimentimail (usando distinc)
     *  	2. Per ogni coppia cerco la configurazione principale su MAIL_CONFIG( se non c'è per il software cerco per idcoumne e TT)
     *  	3. Utilizzo il campo MAIL_CONFIG.ID per popolare il campo MOVIMENTIEMAIL.ID_ACCOUNT per l'idcomune e software in esame
     *  
     *  UPGR PEC_INBOX --> POPOLAMENTO CAMPO ACCOUNTI_ID   
     *  	1. Recupero le coppie [idcomune,software] presenti in PEC_INBOX (usando distinc)
     *  	2. Per ogni coppia cerco la configurazione principale su MAIL_CONFIG( se non c'è per il software cerco per idcoumne e TT)
     *  	3. Utilizzo il campo MAIL_CONFIG.ID per popolare il campo PEC_INBOX.ID_ACCOUNT per l'idcomune e software in esame
     * 
     * </pre>
     * 
     */
    @Override
    public int run(Session session) throws SetupRunException {

	try {
	    activityLogInfo("upgrUpdateMovMailAndPecInbox.run: inizio aggiornamento");
	    activityLogInfo("upgrUpdateMovMailAndPecInbox.run: inizio aggiornamento movimenti email");
	    StringBuffer _QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE = new StringBuffer(
		    "SELECT ID FROM MAIL_CONFIG WHERE IDCOMUNE= ? AND SOFTWARE= ? AND FLAG_PRINCIPALE = 1");
	    Query QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE = session.createSQLQuery(_QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE
		    .toString());
	    updateMovimentiEmail(session, QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE);
	    activityLogInfo("upgrUpdateMovMailAndPecInbox.run: inizio aggiornamento pecinbox");
	    updatePecInbox(session, QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE);
	} catch (Exception e) {
	    activityLogInfo(e.getMessage());
	}
	this.commitTransaction();
	activityLogInfo("UpdateMovMailAndPecInbox.run: Fine aggiornamento");
	return 1;
    }

    private void updateMovimentiEmail(Session session, Query qUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE) {

	Query QUERY_COMMIT = session.createSQLQuery("COMMIT");
	// ///////////////////////////////////	SEZIONE QUERY //////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////// //////////////////////////////////////////////////
	// LA QUERY RECUPERA LE COPPIE [IDCOMUNE, SOFTWARE] PRESENTI SU MOVIMENTIEMAIL
	//.1
	StringBuffer _QUERY_RECUPERA_COPPIE_IDCOMUNE_SOFTWARE_MOVIMENTI_EMAIL = new StringBuffer(
		"SELECT  idcomune, software fROM istanze group by idcomune,software  order by IDCOMUNE");
	// LA QUERY RECUPERA I RECORD DI MOVIMENTIEMAIL FILTRANDO PER SOFTWARE E IDCOMUNE
	//.2
	StringBuffer _QUERY_UPDATE_MOV_EMAIL = new StringBuffer(
		"UPDATE MOVIMENTIMAIL SET  ACCOUNT_ID = ? WHERE  IDCOMUNE = ? AND codicemovimento in (select codicemovimento from istanze where idcomune=? and software=?) AND ACCOUNT_ID IS NULL ");
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////LOGICA AGGIORNAMENTO////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////// //////////////////////////////////////////////////
	// Recupero le coppie [idcomune,software] per tutti le email dei movimenti
	Query QUERY_RECUPERA_COPPIE_IDCOMUNE_SOFTWARE_MOVIMENTI_EMAIL = session
		.createSQLQuery(_QUERY_RECUPERA_COPPIE_IDCOMUNE_SOFTWARE_MOVIMENTI_EMAIL.toString());
	List coppieIdComuneAndSoftwareMovEmailList = QUERY_RECUPERA_COPPIE_IDCOMUNE_SOFTWARE_MOVIMENTI_EMAIL.list();
	Query QUERY_UPDATE_MOV_EMAIL = session.createSQLQuery(_QUERY_UPDATE_MOV_EMAIL.toString());
	for (Object object : coppieIdComuneAndSoftwareMovEmailList) {
	    Object[] coppiaIdComuneAndSoftwareMovEmail = (Object[]) object;
	    String idcomune = (String) coppiaIdComuneAndSoftwareMovEmail[0];
	    String software = (String) coppiaIdComuneAndSoftwareMovEmail[1];
	    BigDecimal idAccount = findIdAccountPrincipaleMailConfig(idcomune, software, session, qUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE);
	    if (idAccount == null) {
		idAccount = findIdAccountPrincipaleMailConfig(idcomune, WebConstants.SOFTWARE_TT, session,
			qUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE);
	    }
	    if (idAccount != null) {
		QUERY_UPDATE_MOV_EMAIL.setInteger(0, new Integer(idAccount.intValue()));
		QUERY_UPDATE_MOV_EMAIL.setString(1, idcomune);
		QUERY_UPDATE_MOV_EMAIL.setString(2, idcomune);
		QUERY_UPDATE_MOV_EMAIL.setString(3, software);
		QUERY_UPDATE_MOV_EMAIL.executeUpdate();
	    }
	    QUERY_COMMIT.executeUpdate();
	    session.flush();
	    session.clear();
	}
    }

    private void updatePecInbox(Session session, Query qUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE) {

	Query QUERY_COMMIT = session.createSQLQuery("COMMIT");
	// ///////////////////////////////////	SEZIONE QUERY //////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////// //////////////////////////////////////////////////
	// LA QUERY RECUPERA LE COPPIE [IDCOMUNE, SOFTWARE] PRESENTI SU MOVIMENTIEMAIL
	//.1
	StringBuffer _QUERY_RECUPERA_COPPIE_IDCOMUNE_SOFTWARE_PEC_INBOX = new StringBuffer(
		"SELECT DISTINCT IDCOMUNE,SOFTWARE FROM PEC_INBOX  ORDER BY IDCOMUNE");
	//3.
	StringBuffer _QUERY_UPDATE_PEC_INBOX = new StringBuffer("UPDATE PEC_INBOX SET  ACCOUNT_ID = ? ");
	_QUERY_UPDATE_PEC_INBOX.append(" WHERE IDCOMUNE = ? AND SOFTWARE= ? AND ACCOUNT_ID IS NULL");
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////LOGICA AGGIORNAMENTO////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////// //////////////////////////////////////////////////
	// Recupero le coppie [idcomune,software] per tutti I RECORD DI PEC INBOX
	Query QUERY_RECUPERA_COPPIE_IDCOMUNE_SOFTWARE_PEC_INBOX = session.createSQLQuery(_QUERY_RECUPERA_COPPIE_IDCOMUNE_SOFTWARE_PEC_INBOX
		.toString());
	List coppieIdComuneAndSoftwarePecInboxList = QUERY_RECUPERA_COPPIE_IDCOMUNE_SOFTWARE_PEC_INBOX.list();
	Query QUERY_UPDATE_PEC_INBOX = session.createSQLQuery(_QUERY_UPDATE_PEC_INBOX.toString());
	for (Object object : coppieIdComuneAndSoftwarePecInboxList) {
	    Object[] coppiaIdComuneAndSoftwarePecInbox = (Object[]) object;
	    String idcomune = (String) coppiaIdComuneAndSoftwarePecInbox[0];
	    String software = (String) coppiaIdComuneAndSoftwarePecInbox[1];
	    BigDecimal idAccount = findIdAccountPrincipaleMailConfig(idcomune, software, session, qUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE);
	    if (idAccount == null) {
		idAccount = findIdAccountPrincipaleMailConfig(idcomune, WebConstants.SOFTWARE_TT, session,
			qUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE);
	    }
	    if (idAccount != null) {
		QUERY_UPDATE_PEC_INBOX.setInteger(0, new Integer(idAccount.intValue()));
		QUERY_UPDATE_PEC_INBOX.setString(1, idcomune);
		QUERY_UPDATE_PEC_INBOX.setString(2, software);
		QUERY_UPDATE_PEC_INBOX.executeUpdate();
	    }
	    QUERY_COMMIT.executeUpdate();
	    session.flush();
	    session.clear();
	}
    }

    private BigDecimal findIdAccountPrincipaleMailConfig(String idcomune, String software, Session session,
	    Query QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE) {

	QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE.setString(0, idcomune);
	QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE.setString(1, software);
	List mailcongifidcomuneAndSoftwareList = QUERY_RECUPERA_MAIL_CONFIG_BY_IDCOMUNE_AND_SOFTWARE.list();
	if (mailcongifidcomuneAndSoftwareList != null && !mailcongifidcomuneAndSoftwareList.isEmpty()) {
	    if (mailcongifidcomuneAndSoftwareList.get(0) != null) {
		return (BigDecimal) mailcongifidcomuneAndSoftwareList.get(0);
	    }
	}
	return null;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
