package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("upgrUpdateFoVisuraCampiTask")
public class UpdateFoVisuraCampiTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateFoVisuraCampiTask.class);
    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	String origIdComune = ORMHelper.getIdcomune();
	activityLogInfo("UpdateFoVisuraCampiTask.run: inizio aggiornamento");
	/*
	 * 
	 */
	String sql = "delete from fo_configurazione where software not in (select codice from software)";
	Query query = session.createSQLQuery(sql);
	query.executeUpdate();
	this.commitTransaction();
	sql = "SELECT idcomune, software, fk_idconfigurazionebase, etichetta, fk_contesto, valore "
		+ " FROM fo_configurazione, fo_configurazionebase WHERE "
		+ " fo_configurazionebase.codice = fo_configurazione.fk_idconfigurazionebase and fk_idconfigurazionebase is not null "
		+ " and valore is not null order by idcomune, software";
	query = session.createSQLQuery(sql);
	List rs = query.list();
	if (rs.size() > 0) {
	    // 2.
	    for (Object object : rs) {
		Object[] result = (Object[]) object;
		String idcomune = (String) result[0];
		String software = (String) result[1];
		BigDecimal tipoCampo = (BigDecimal) result[2];
		String etichetta = (String) result[3];
		String contesto = (String) result[4];
		String valore = (String) result[5];
		String campo = decodeCampoFromTipo(tipoCampo);
		if (StringUtils.isNotBlank(campo)) {
		    int posizione = decodePosizioneFromValore(valore);
		    String contestoStr = decodeContesto(contesto);
		    if (StringUtils.isNotBlank(contestoStr)) {
			ORMHelper.setIdcomune(idcomune);
			ORMHelper.setSoftware(software);
			query = session
				.createSQLQuery("Select fkidcampo From Fo_Visura_Campi fvc where fvc.idcomune=? and fvc.software=? and fvc.fkidcontesto=? and fvc.fkidcampo=?");
			query.setString(0, idcomune);
			query.setString(1, software);
			query.setString(2, contestoStr);
			query.setString(3, campo);
			List rs1 = query.list();
			if (rs1.size() == 0) {
			    try {
				sql = "insert into FO_VISURA_CAMPI (IDCOMUNE,SOFTWARE,POSIZIONE,FKIDCAMPO,FKIDCONTESTO) values (?,?,?,?,?)";
				query = session.createSQLQuery(sql);
				query.setString(0, idcomune);
				query.setString(1, software);
				query.setInteger(2, posizione);
				query.setString(3, campo);
				query.setString(4, contestoStr);
				query.executeUpdate();
			    } catch (Exception e) {
				handleErrorCondition(e, "Errore durante l'inserimento in FO_VISURA_CAMPI [idcomune: " + idcomune + ", software:"
					+ software + ", posizione:" + posizione + ", campo:" + campo + ", contesto:" + contestoStr + " ]");
			    }
			}
		    }
		}
	    }
	    this.commitTransaction();
	}
	ORMHelper.setIdcomune(origIdComune);
	activityLogInfo("UpdateFoVisuraCampiTask.run: Fine aggiornamento");
	return 0;
    }

    private String decodeContesto(String contesto) {

	if (contesto == null) {
	    throw new RuntimeException("Contesto è nullo");
	}
	if (contesto.equalsIgnoreCase("AIP-LIS")) {
	    return "ARCHIVIO_LISTA";
	}
	if (contesto.equalsIgnoreCase("ISI-LIS")) {
	    return "VISURA_LISTA";
	}
	if (contesto.equalsIgnoreCase("AIP-FIL")) {
	    return "ARCHIVIO_FILTRI";
	}
	if (contesto.equalsIgnoreCase("ISI-FIL")) {
	    return "VISURA_FILTRI";
	}
	return null;
    }

    private int decodePosizioneFromValore(String valore) {

	int result = 0;
	try {
	    result = Integer.parseInt(valore);
	} catch (Exception e) {
	}
	return result;
    }

    private String decodeCampoFromTipo(BigDecimal tipoCampo) {

	if (tipoCampo == null) {
	    throw new RuntimeException("tipoCampo è nullo");
	}
	switch (tipoCampo.intValue()) {
	case 1:
	    return "DATA_ISTANZA";
	case 2:
	    return "RESPONSABILE_PROCEDIMENTO";
	case 3:
	    return "RICHIEDENTE";
	case 4:
	    return "INTERVENTO";
	case 6:
	    return "OGGETTO";
	case 7:
	    return "DATI_CATASTALI";
	case 10:
	    return "STATO_ISTANZA";
	case 14:
	    return "CODICE_ISTANZA";
	case 16:
	    return "INDIRIZZO";
	case 17:
	    return "DATA_ISTANZA";
	case 18:
	    return "RESPONSABILE_PROCEDIMENTO";
	case 19:
	    return "RICHIEDENTE";
	case 20:
	    return "INTERVENTO";
	case 22:
	    return "OGGETTO";
	case 25:
	    return "INDIRIZZO";
	case 27:
	    return "CODICE_ISTANZA";
	case 31:
	    return "STATO_ISTANZA";
	case 33:
	    return "CODICE_ISTANZA";
	case 34:
	    return "INTERVENTO";
	case 35:
	    return "DATA_ISTANZA";
	case 37:
	    return "STATO_ISTANZA";
	case 38:
	    return "INDIRIZZO";
	case 39:
	    return "RICHIEDENTE";
	case 40:
	    return "OGGETTO";
	case 41:
	    return "CODICE_ISTANZA";
	case 42:
	    return "INTERVENTO";
	case 43:
	    return "DATA_ISTANZA";
	case 45:
	    return "STATO_ISTANZA";
	case 46:
	    return "INDIRIZZO";
	case 47:
	    return "RICHIEDENTE";
	case 48:
	    return "OGGETTO";
	case 53:
	    return "NUMERO_PROTOCOLLO";
	case 54:
	    return "DATA_PROTOCOLLO";
	case 55:
	    return "NUMERO_PROTOCOLLO";
	case 56:
	    return "DATA_PROTOCOLLO";
	case 57:
	    return "NUMERO_PROTOCOLLO";
	case 58:
	    return "DATA_PROTOCOLLO";
	case 59:
	    return "NUMERO_PROTOCOLLO";
	case 60:
	    return "DATA_PROTOCOLLO";
	case 65:
	    return "NUMERO_AUTORIZZAZIONE";
	case 66:
	    return "NUMERO_AUTORIZZAZIONE";
	default:
	    return "";
	}
    }
}
