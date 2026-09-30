package it.gruppoinit.pal.gp.core.features.autorizzazioni.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public enum MercatoGiorniSettimanaEnum {

    LUNEDI("Lunedì", 1, "LUN"),
    MARTEDI("Martedì", 2, "MAR"),
    MERCOLEDI("Mercoledì", 3, "MER"),
    GIOVEDI("Giovedì", 4, "GIO"),
    VENERDI("Venerdì", 5, "VEN"),
    SABATO("Sabato", 6, "SAB"),
    DOMENICA("Domenica", 7, "DOM"),
    NESSUNO("Nessuno", 8, "NESS");

    private String valore;
    private int ordine;
    private String valMin;

    public String getValore() {

	return valore;
    }

    public int getOrdine() {

	return ordine;
    }

    void setValore(String valore) {

	this.valore = valore;
    }

    void setValMin(String valMin) {

	this.valMin = valMin;
    }

    public String getValMin() {

	return valMin;
    }

    private MercatoGiorniSettimanaEnum(String valore, int ordine, String valMin) {

	this.valore = valore;
	this.ordine = ordine;
	this.valMin = valMin;
    }

    // se passo una stringa tipo LUN mi ritorna l'ordine di lunedì
    public static int getOrdineDaValore(String val) {

	if (val == null || val.isEmpty()) {
	    return NESSUNO.ordine;
	}
	MercatoGiorniSettimanaEnum[] s = MercatoGiorniSettimanaEnum.values();
	for (MercatoGiorniSettimanaEnum e : s) {
	    if (e.name().toUpperCase().indexOf(val.toUpperCase()) >= 0) {
		return e.ordine;
	    }
	}
	return 0;
    }

    private static MercatoGiorniSettimanaEnum fromValMin(String valMin) {

	MercatoGiorniSettimanaEnum[] s = MercatoGiorniSettimanaEnum.values();
	for (MercatoGiorniSettimanaEnum e : s) {
	    if (e.valMin.toUpperCase().equalsIgnoreCase(valMin)) {
		return e;
	    }
	}
	return NESSUNO;
    }

    public static String ordinaGiorniETornaStringa(Set<String> giorni, String separator) {

	String ret = "";
	Set<MercatoGiorniSettimanaEnum> s = new HashSet<MercatoGiorniSettimanaEnum>();
	for (String g : giorni) {
	    if (StringUtils.isNotBlank(g)) {
		MercatoGiorniSettimanaEnum t = fromValMin(StringUtils.left(g.toUpperCase(), 3));
		s.add(t);
	    }
	}
	Map<Integer, MercatoGiorniSettimanaEnum> map = new HashMap<Integer, MercatoGiorniSettimanaEnum>();
	for (MercatoGiorniSettimanaEnum mg : s) {
	    map.put(mg.getOrdine(), mg);
	}
	List<Integer> sortedKeys = new ArrayList<Integer>(map.keySet());
	Collections.sort(sortedKeys);
	for (Integer ordineLocal : sortedKeys) {
	    ret += map.get(ordineLocal).valore + separator;
	}
	if (ret.endsWith(separator)) {
	    ret = ret.substring(0, ret.length() - 2);
	}
	return ret;
    }
}
