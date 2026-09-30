package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 * viene eseguita la query che sta nella tabella task_elaborazione_istanze i campi tirati fuori in ordine saranno
 * codiceistanza,idcomune,software
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_COPIA_OGGETTI_TRA_COMUNI" spring-bean-id="upgrUpdateCopiaOggettiTraComuni"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateCopiaOggettiTraComuni"
 * 			fail-on-error="true" autocommit="true"&gt;
 * 			&lt;param name="pathSorgente" value="/mnt/sporvic3/B455"&gt;&lt;/param&gt;
 * 			&lt;param name="pathDestinazione" value="/mnt/sporvic3/G628" /&gt;
 * 			&lt;param name="idComuneDestinazione" value="G628" /&gt;
 *           		&lt;param name="ultimoCodiceOggetto" value="1">&lt;/param>
 *         		&lt;param name="runFromDate" value="26/01/2012 - 09:00:01">&lt;/param>
 *         		&lt;param name="runToDate" value="26/01/2012 - 17:10:30">&lt;/param>			
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpdateCopiaOggettiTraComuni")
public class UpdateCopiaOggettiTraComuni extends BaseJavaTask {

    @Override
    public int run(Session session) throws SetupRunException {

	String pathSorgente = getParameterValue("pathSorgente");
	String pathDestinazione = getParameterValue("pathDestinazione");
	String idComuneDestinazione = getParameterValue("idComuneDestinazione");
	String runFromDate = getParameterValue("runFromDate");
	String runToDate = getParameterValue("runToDate");
	String ultimoCodiceOggetto = getParameterValue("ultimoCodiceOggetto");
	Integer ultimoCodice = null;
	if (Utilities.isInteger(ultimoCodiceOggetto)) {
	    ultimoCodice = Integer.parseInt(ultimoCodiceOggetto);
	}
	activityLogInfo("UpdateCopiaOggettiTraComuni.run: inizio aggiornamento");
	Date runFrom = null;
	Date runTo = null;
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
	int rowCount = 0;
	String sql = "Select codiceoggetto From oggetti where idcomune=? and oggetto is null ";
	if (ultimoCodice != null) {
	    sql = sql + " and codiceoggetto>?";
	}
	sql = sql + " order by codiceoggetto asc";
	SQLQuery query = session.createSQLQuery(sql);
	query.addScalar("codiceoggetto", Hibernate.INTEGER);
	query.setString(0, idComuneDestinazione);
	if (ultimoCodice != null) {
	    query.setInteger(1, ultimoCodice);
	}
	Integer codiceOggetto = null;
	// Recupero id
	// Insert mappature
	List oggetti = query.list();
	SQLQuery DETTAGLIO_OGGETTO = session.createSQLQuery("SELECT NOMEFILE,PERCORSO FROM OGGETTI WHERE idcomune=? AND codiceoggetto=?");
	query.addScalar("idcomune", Hibernate.STRING);
	query.addScalar("codiceoggetto", Hibernate.INTEGER);
	if (oggetti.size() > 0) {
	    for (Object object : oggetti) {
		if (!checkexecutionTime(runTo, executeWithTime)) {
		    return 0;
		}
		// Object[] row = (Object[]) object;
		codiceOggetto = (Integer) object;
		activityLogInfo("UpdateCopiaOggettiTraComuni.run: processo il codice oggetto " + codiceOggetto);
		DETTAGLIO_OGGETTO.setString(0, idComuneDestinazione);
		DETTAGLIO_OGGETTO.setInteger(1, codiceOggetto);
		List o = DETTAGLIO_OGGETTO.list();
		String percorso = null;
		String nomeFile = null;
		if (o != null && !o.isEmpty()) {
		    for (Object ob : o) {
			Object[] rowOb = (Object[]) ob;
			nomeFile = (String) rowOb[0];
			percorso = (String) rowOb[1];
		    }
		}
		if (StringUtils.defaultString(percorso).toLowerCase().indexOf("workspace:") >= 0) {
		    continue;
		}
		String srcDir = pathSorgente + "/" + percorso + "/";
		String percorsoFileSrc = srcDir + nomeFile;
		activityLogInfo("File sorgente " + percorsoFileSrc);
		String percorsoFileDest = pathDestinazione + "/" + percorso + "/" + nomeFile;
		activityLogInfo("File destinazione " + percorsoFileDest);
		File src = new File(percorsoFileSrc);
		File dest = new File(percorsoFileDest);
		boolean existsDest = dest.exists();
		boolean existsSrc = src.exists();
		activityLogInfo("File destinazione esiste ? " + existsDest + ", sorgente esiste? " + existsSrc);
		if (!existsSrc) {
		    src = getContentFromFileWithNonAsciiChars(nomeFile, new File(srcDir));
		    existsSrc = src == null ? false : src.exists();
		    if (!existsSrc) {
			throw new RuntimeException("Il file codice codice oggetto: " + codiceOggetto + ", nomefile: " + nomeFile + ", percorso: "
				+ percorso + " non esiste");
		    }
		}
		if (!existsDest) {
		    activityLogInfo("Creo le directory ");
		    dest.getParentFile().mkdirs();
		    try {
			FileInputStream fis = new FileInputStream(src);
			FileOutputStream fos = new FileOutputStream(dest);
			try {
			    activityLogInfo("copio i file le directory ");
			    IOUtils.copy(fis, fos);
			} catch (Exception e) {
			    throw new RuntimeException("Errore nella copia del file codice codice oggetto: " + codiceOggetto + ", nomefile: "
				    + nomeFile + " da " + src + " a " + dest);
			}
			try {
			    fis.close();
			} catch (Exception e) {
			}
			try {
			    fos.close();
			} catch (Exception e) {
			}
			fis = new FileInputStream(src);
			String md5ValSrc = DigestUtils.md5Hex(fis);
			try {
			    fis.close();
			} catch (Exception e) {
			}
			fis = new FileInputStream(dest);
			String md5ValDesc = DigestUtils.md5Hex(fis);
			try {
			    fis.close();
			} catch (Exception e) {
			}
			if (!md5ValDesc.equalsIgnoreCase(md5ValSrc)) {
			    throw new RuntimeException("Il file codice codice oggetto: " + codiceOggetto + ", nomefile: " + nomeFile
				    + " è stato copiato ma ha md5 differente da " + src + " a " + dest);
			}
		    } catch (Exception e) {
			handleErrorCondition(e, e.getMessage());
		    }
		}
	    }
	}
	activityLogInfo("UpdateCopiaOggettiTraComuni.run: Fine aggiornamento");
	return rowCount;
    }

    public static void main(String[] args) throws Exception {

	File src = new File("C:/temp/pratica-backoffice-1576842771734.zip");
	File dest = new File("C:/temp/pratica-backoffice-1576842771734_01.zip");
	FileInputStream fis = new FileInputStream(src);
	FileOutputStream fos = new FileOutputStream(dest);
	IOUtils.copy(fis, fos);
	try {
	    fis.close();
	} catch (Exception e) {
	}
	try {
	    fos.close();
	} catch (Exception e) {
	}
	fis = new FileInputStream(src);
	String md5ValSrc = DigestUtils.md5Hex(fis);
	try {
	    fis.close();
	} catch (Exception e) {
	}
	fis = new FileInputStream(dest);
	String md5ValDEsc = DigestUtils.md5Hex(fis);
	try {
	    fis.close();
	} catch (Exception e) {
	}
	System.out.println(md5ValDEsc + "=" + md5ValSrc);
	if (md5ValDEsc.equalsIgnoreCase(md5ValSrc)) {
	    System.out.println("ok");
	} else {
	    System.out.println("ko");
	}
    }

    @Override
    public void initialize() throws SetupRunException {

    }

    private boolean checkexecutionTime(Date runTo, boolean executeWithTime) {

	boolean run = true;
	if (executeWithTime) {
	    if (runTo.getTime() <= new Date().getTime()) {
		run = false;
		activityLogInfo("Terminato il tempo di esecuzione del task impostato a " + runTo);
	    }
	}
	return run;
    }

    private File getContentFromFileWithNonAsciiChars(String nomeFile, File searchDir) {

	if (searchDir != null && searchDir.isDirectory()) {
	    activityLogInfo("cerco il file " + nomeFile + " nella directory: " + searchDir);
	    String fileSenzaCUTF8 = Utilities.eliminaCaratteriNonAscii(nomeFile);
	    activityLogInfo("nome file file ripulito " + fileSenzaCUTF8);
	    if (!nomeFile.equalsIgnoreCase(fileSenzaCUTF8)) {
		File[] files = searchDir.listFiles();
		for (File file : files) {
		    if (!file.isDirectory()) {
			String name = file.getName();
			activityLogInfo("\tvaluto il file " + name);
			name = Utilities.eliminaCaratteriNonAscii(name);
			activityLogInfo("\tfile ripulito " + name);
			if (name.equalsIgnoreCase(fileSenzaCUTF8)) {
			    activityLogInfo("E' stato trovato ed associato il seguente file " + name + " provo a ");
			    return file;
			}
		    }
		}
	    }
	}
	return null;
    }
}
