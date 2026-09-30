package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DestinatariHelper {

    private Integer codice;
    private String descrizione;
    private Boolean selezionato;

    public DestinatariHelper() {

	this.selezionato = Boolean.FALSE;
	;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Boolean getSelezionato() {

	return selezionato;
    }

    public void setSelezionato(Boolean selezionato) {

	this.selezionato = selezionato;
    }

    public static List<DestinatariHelper> getTipiDestinatari(String codiciDestinatari) {

	List<DestinatariHelper> destHs = null;
	if (StringUtils.isBlank(codiciDestinatari)) {
	    destHs = popolateListDestinatari();
	} else {
	    destHs = popolateListDestinatari(codiciDestinatari);
	}
	return destHs;
    }

    private static List<DestinatariHelper> popolateListDestinatari() {

	String[] desc = new String[] { "Richiedente (Pec)", "Operatore", "Responsabile", "Istruttore", "Amministrazione del movimento",
		"Domicilio Elettronico (Istanza)", "Operatori attivi sul comune (1*)" };
	List<DestinatariHelper> destHs = new ArrayList<DestinatariHelper>();
	DestinatariHelper dest = null;
	for (int i = 0; i < desc.length; i++) {
	    dest = new DestinatariHelper();
	    dest.setCodice(i);
	    dest.setDescrizione(desc[i]);
	    dest.setSelezionato(false);
	    destHs.add(dest);
	}
	return destHs;
    }

    private static List<DestinatariHelper> popolateListDestinatari(String codici) {

	String[] listCodici = codici.split(",");
	List<String> _listCodici = Arrays.asList(listCodici);
	String[] desc = new String[] { "Richiedente (Pec)", "Operatore", "Responsabile", "Istruttore", "Amministrazione del movimento",
		"Domicilio Elettronico (Istanza)", "Operatori attivi sul comune (1*)" };
	List<DestinatariHelper> destHs = new ArrayList<DestinatariHelper>();
	DestinatariHelper dest = null;
	for (int i = 0; i < desc.length; i++) {
	    dest = new DestinatariHelper();
	    dest.setCodice(i);
	    dest.setDescrizione(desc[i]);
	    if (_listCodici.contains(new Integer(i).toString())) {
		dest.setSelezionato(true);
	    } else {
		dest.setSelezionato(false);
	    }
	    destHs.add(dest);
	}
	return destHs;
    }
}
