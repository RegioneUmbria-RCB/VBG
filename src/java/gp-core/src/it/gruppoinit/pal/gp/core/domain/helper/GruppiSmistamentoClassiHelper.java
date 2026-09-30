package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GruppiSmistamentoClassiHelper {

    /**
     * contiene la mappa con il numero dei gruppi configurati (chiave es: 1, 2 o 3) e la lista dei codici di configurazione
     * 
     */
    private Map<Integer, Set<Integer>> mappaGruppiClassi = new HashMap<Integer, Set<Integer>>();
    /**
     * Contiene le informazioni del codiceGruppo (Chiave) e dei vari GruppiendoprocedimentiT del gruppo (valore)
     */
    private Map<Integer, List<GruppiSmistHelper>> mappaGruppiConf = new HashMap<Integer, List<GruppiSmistHelper>>();

    public GruppiSmistamentoClassiHelper(List<GruppiSmistHelper> conf) {

	this();
	populateMap(conf);
    }

    private GruppiSmistamentoClassiHelper() {

	super();
    }

    private void populateMap(List<GruppiSmistHelper> conf) {

	for (GruppiSmistHelper gsh : conf) {
	    Integer codiceGruppo = gsh.getId();
	    List<GruppiSmistHelper> list = mappaGruppiConf.get(codiceGruppo);
	    if (list == null) {
		list = new ArrayList<GruppiSmistHelper>();
	    }
	    list.add(gsh);
	    mappaGruppiConf.put(codiceGruppo, list);
	}
	for (Entry<Integer, List<GruppiSmistHelper>> kv : mappaGruppiConf.entrySet()) {
	    Integer numGruppo = kv.getKey();
	    Integer size = kv.getValue().size();
	    if (mappaGruppiClassi.get(size) == null) {
		Set<Integer> s = new HashSet<Integer>();
		s.add(numGruppo);
		mappaGruppiClassi.put(size, s);
	    } else {
		Set<Integer> val = mappaGruppiClassi.get(size);
		val.add(numGruppo);
		mappaGruppiClassi.put(size, val);
	    }
	}
    }

    public Map<Integer, Set<Integer>> getMappaGruppiClassi() {

	return mappaGruppiClassi;
    }

    public Map<Integer, List<GruppiSmistHelper>> getMappaGruppiConf() {

	return mappaGruppiConf;
    }

    public boolean checkGruppiConCodici(Set<Integer> codiciGruppo, Set<Integer> codiciGruppoEndoProcedimentiTs) {

	for (Integer c : codiciGruppo) {
	    List<GruppiSmistHelper> list = mappaGruppiConf.get(c);
	    Set<Integer> codiciGruppoEndoProcedimentiT = new HashSet<Integer>();
	    for (GruppiSmistHelper gruppiSmistHelper : list) {
		codiciGruppoEndoProcedimentiT.add(gruppiSmistHelper.getGruppo());
	    }
	    if (codiciGruppoEndoProcedimentiT.containsAll(codiciGruppoEndoProcedimentiTs)) {
		return true;
	    }
	}
	return false;
    }

    public static ArrayList<Integer[]> getPermPossibiliInClasseK3(Integer[] insiemeDiInteri) {

	ArrayList<int[]> arrayList = new ArrayList<int[]>();
	// int N = 5;
	int K = 3;
	// creo n array a partire da quello di base andando a togliere 
	// ogni volta il primo elemento.
	// Creo tanti array finche non arrivo a quello con dimensione
	// K-1
	for (int i = 0; i < insiemeDiInteri.length; i++) {
	    int[] arrayCom = new int[insiemeDiInteri.length - (i + 1)];
	    if (arrayCom.length + 1 >= K) {
		int p = 0;
		for (int n = i + 1; n < insiemeDiInteri.length; n++) {
		    arrayCom[p] = insiemeDiInteri[n];
		    p++;
		}
		arrayList.add(arrayCom);
	    }
	}
	int numerazione = 1;
	int indiceInsieme = 0;
	// per ogni array creato genero tutte le coppie possibili, ad ogni coppia dell'array
	//aggiungo l'elemento tolto nelle fase precedente.
	//Es. Array partenza [1,2,3,4] genare 2 aray [2,3,4],[3,4]
	// per il primo array creo le coppie:
	// [2,3],[2,4],[3,4] ad ognuno di queste coppie aggiungo l'elemento tolto per generare l'array di partenza [1,2,3],[1,2,4],[1,3,4]
	ArrayList<Integer[]> ris = new ArrayList<Integer[]>();
	for (int[] is : arrayList) {
	    int indice = 1;
	    //int k = K + indice > is.length ? is.length : K + indice;
	    int k = is.length;
	    for (int i = 0; i < is.length; i++) {
		for (int j = indice; j < k; j++) {
		    Integer[] risArray = new Integer[3];
		    risArray[0] = insiemeDiInteri[indiceInsieme];
		    risArray[1] = is[i];
		    risArray[2] = is[j];
		    ris.add(risArray);
		    numerazione++;
		}
		indice++;
	    }
	    indiceInsieme++;
	}
	return ris;
    }
}
