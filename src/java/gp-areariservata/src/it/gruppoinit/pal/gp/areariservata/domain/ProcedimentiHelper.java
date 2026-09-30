package it.gruppoinit.pal.gp.areariservata.domain;

import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.BooleanUtils;

public class ProcedimentiHelper {

    private List<AlberoprocEndo> list;
    private ProcedimentoHelper procedimentoPrincipale;
    private SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>> procedimentiProposti;
    private SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>> procedimentiAttivabili;

    public ProcedimentiHelper(List<AlberoprocEndo> list) {

	this.list = list;
	procedimentiProposti = new TreeMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>>();
	procedimentiAttivabili = new TreeMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>>();
    }

    public void elabora() {

	for (AlberoprocEndo current : list) {
	    ProcedimentoHelper ph = new ProcedimentoHelper(current.getInventarioprocedimento());
	    if (BooleanUtils.isTrue(current.getFlagPrincipale())) {
		ph.getProcedimento().setPrincipale(true);
		procedimentoPrincipale = ph;
	    } else if (BooleanUtils.isTrue(current.getFlagRichiesto())) {
		this.addToMap(procedimentiProposti, ph);
	    } else {
		this.addToMap(procedimentiAttivabili, ph);
	    }
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

    public ProcedimentoHelper getProcedimentoPrincipale() {

	return procedimentoPrincipale;
    }

    public SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>> getProcedimentiProposti() {

	return procedimentiProposti;
    }

    public SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, SortedMap<ProcedimentoKey, ProcedimentoHelper>>> getProcedimentiAttivabili() {

	return procedimentiAttivabili;
    }
}
