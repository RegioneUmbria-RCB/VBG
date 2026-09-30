package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Comparator;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Software;

public class VerticalizzazioniconfigurazioniHelper implements Comparator<VerticalizzazioniconfigurazioniHelper> {

    private String modulo;
    private String parametro;
    private String valore;
    private Comuni comune;
    private Software software;

    public String getModulo() {

	return modulo;
    }

    public void setModulo(String modulo) {

	this.modulo = modulo;
    }

    public String getParametro() {

	return parametro;
    }

    public void setParametro(String parametro) {

	this.parametro = parametro;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    @Override
    public int compare(VerticalizzazioniconfigurazioniHelper o1, VerticalizzazioniconfigurazioniHelper o2) {

	// Controlla che gli oggetti non siano vuoti
	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String comune1 = "";
	String comune2 = "";
	if (o1.getComune() != null) {
	    comune1 = o1.getComune().getComune();
	}
	if (o2.getComune() != null) {
	    comune2 = o2.getComune().getComune();
	}
	int risultato = 0;
	if (comune1.compareTo(comune2) == 0) {
	    //non riesco a ordinare per  ordine allora uso la descriozne per descrizione
	    String software1 = "";
	    String software2 = "";
	    software1 = o1.getSoftware().getDescrizione();
	    software2 = o2.getSoftware().getDescrizione();
	    risultato = software1.compareTo(software2);
	} else {
	    risultato = comune1.compareTo(comune2);
	}
	return risultato;
    }
}
