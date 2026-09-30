package it.gruppoinit.pal.gp.core.scheduler.task;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.gestionecalendari.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ImportTask {

    private static final Logger log = LoggerFactory.getLogger(ImportTask.class);
    protected Properties dbProps = new Properties();
    protected Properties deployProps = new Properties();

    public ImportTask() {

	try {
	    dbProps.load(JdbcConnection.class.getClassLoader().getResourceAsStream("db.properties"));
	    deployProps.load(JdbcConnection.class.getClassLoader().getResourceAsStream("deploy.properties"));
	} catch (IOException e) {
	    log.error("errore durante il caricamento dei file di properties", e);
	    throw new RuntimeException(e);
	}
    }

    public abstract void process(List<Comuniassociati> listComuniImportAutomatico);

    protected boolean isListed(List<Comuniassociati> listComuniImportAutomatico, String codicecomune) {

	boolean isListed = false;
	for (Comuniassociati comuniassociati : listComuniImportAutomatico) {
	    if (codicecomune.equals(comuniassociati.getId().getCodicecomune())) {
		isListed = true;
		break;
	    }
	}
	return isListed;
    }

    protected String formatOrganizzatore(String nominativo, String nome, String cf, String piva) {

	return Utils.formatOrganizzatore(nominativo, nome, cf, piva);
    }
}
