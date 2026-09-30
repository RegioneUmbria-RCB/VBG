package it.gruppoinit.pal.gp.areariservata.domain;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ProcedimentiAltriHelper {

    private List<Inventarioprocedimenti> list;
    private SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>> procedimentiAltri;

    public ProcedimentiAltriHelper(List<Inventarioprocedimenti> list) {

	this.list = list;
	procedimentiAltri = new TreeMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>>();
    }

    public void elabora() {

	for (Inventarioprocedimenti current : list) {
	    ProcedimentoHelper ph = new ProcedimentoHelper(current);
	    this.addToMap(procedimentiAltri, ph);
	}
    }

    private void addToMap(SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>> m,
	    ProcedimentoHelper ph) {

	if (m.containsKey(ph.getFamigliaKey())) {
	    if (m.get(ph.getFamigliaKey()).containsKey(ph.getCategoriaKey())) {
		m.get(ph.getFamigliaKey()).get(ph.getCategoriaKey()).put(ph.getKey(), ph);
	    } else {
		SortedMap<ProcedimentoKey, ProcedimentoHelper> procedimentiMap = new TreeMap<ProcedimentoKey, ProcedimentoHelper>();
		procedimentiMap.put(ph.getKey(), ph);
		m.get(ph.getFamigliaKey()).put(ph.getCategoriaKey(), procedimentiMap);
	    }
	} else {
	    SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>> categorieMap = new TreeMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>();
	    SortedMap<ProcedimentoKey, ProcedimentoHelper> procedimentiMap = new TreeMap<ProcedimentoKey, ProcedimentoHelper>();
	    procedimentiMap.put(ph.getKey(), ph);
	    categorieMap.put(ph.getCategoriaKey(), procedimentiMap);
	    m.put(ph.getFamigliaKey(), categorieMap);
	}
    }

    public SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>> getProcedimentiAltri() {

	return procedimentiAltri;
    }
}
