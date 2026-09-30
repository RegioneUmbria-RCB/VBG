package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class ModificheScambioPosteggioHelper {

    private List<Integer> presenzeDaModificare;
    private List<MercatipresenzeD> presenzeConErrore;
    private AutorizzazioniConcessioni autConc;
    private Date dataCessazione;
    private boolean errore;

    public ModificheScambioPosteggioHelper() {

	super();
    }

    public ModificheScambioPosteggioHelper(List<Integer> presenzeDaModificare, List<MercatipresenzeD> presenzeConErrore,
	    AutorizzazioniConcessioni autConc, Date dataCessazione, boolean isErrore) {

	this();
	this.presenzeDaModificare = presenzeDaModificare;
	this.presenzeConErrore = presenzeConErrore;
	this.autConc = autConc;
	this.dataCessazione = dataCessazione;
	this.errore = isErrore;
    }

    public static ModificheScambioPosteggioHelper empty() {

	return new ModificheScambioPosteggioHelper();
    }

    public List<Integer> getPresenzeDaModificare() {

	if (presenzeDaModificare == null) {
	    presenzeDaModificare = new ArrayList<Integer>();
	}
	return presenzeDaModificare;
    }

    private List<MercatipresenzeD> getPresenzeConErrore() {

	if (presenzeConErrore == null) {
	    presenzeConErrore = new ArrayList<MercatipresenzeD>();
	}
	return presenzeConErrore;
    }

    public String getErrori() {

	if (errore) {
	    StringBuilder errori = new StringBuilder();
	    errori.append("Attenzione!<br/>Sono state registrate delle presenze per la concessione <b>");
	    errori.append(autConc.getTransientEstremiConcessione());
	    errori.append("</b> sul mercato a partire dalla data <b>");
	    errori.append(Utilities.formatDate(dataCessazione, false));
	    errori.append("</b>.<br/> Per procedere è necessario eliminare la registrazione delle seguenti giornate:<br/> ");
	    errori.append(descrizionePresenze(getPresenzeConErrore()));
	    return errori.toString();
	}
	return null;
    }

    private String descrizionePresenze(List<MercatipresenzeD> presenze) {

	StringBuilder sb = new StringBuilder("<ul>");
	for (MercatipresenzeD presenza : presenze) {
	    sb.append("<li>Presenza ").append(" del <b>").append(Utilities.formatDate(presenza.getMercatiPresenzeT().getDataRegistrazione(), false))
		    .append("</b> su manifestazione <b>").append(presenza.getMercatiPresenzeT().getDescrizione()).append("</b>");
	    if (presenza.getPosteggio() != null) {
		sb.append(" su posteggio <b>").append(presenza.getPosteggio().getCodiceposteggio()).append("</b>");
	    } else {
		sb.append(". La presenza è senza assegnazione posteggio");
	    }
	    sb.append("</li>");
	}
	sb.append("</ul>");
	return sb.toString();
    }

    public boolean isErrore() {

	return errore;
    }
}
