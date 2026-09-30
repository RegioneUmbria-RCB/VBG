package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.Dyn2Massive;
import it.gruppoinit.pal.gp.core.domain.Dyn2MassiveFiltri;
import it.gruppoinit.pal.gp.core.domain.Dyn2Massiveschede;

@XmlRootElement
public class TestataDettagliataModel extends TestataModel {

    public TestataDettagliataModel() {

    }

    @XmlElement(name = "modelli")
    private Set<SchedaDinamicaModel> modelli = new TreeSet<SchedaDinamicaModel>(new SchedaDinamicaModelComparator());
    @XmlElement(name = "filtri")
    private Set<FiltriRicercaTestataModel> filtri = new TreeSet<FiltriRicercaTestataModel>(new FiltriRicercaTestataModelComparator());
    @XmlElement(name = "righe")
    private Set<RigaElaborazioneModel> righe = new TreeSet<RigaElaborazioneModel>(new RigaElaborazioneModelComparator());

    public Set<RigaElaborazioneModel> getRighe() {

	return righe;
    }

    public Set<FiltriRicercaTestataModel> getFiltri() {

	return filtri;
    }

    public Set<SchedaDinamicaModel> getModelli() {

	return modelli;
    }

    public static TestataDettagliataModel build(Dyn2Massive d, List<Dyn2Massiveschede> schedeByElaborazione,
	    List<Dyn2MassiveFiltri> filtriByElaborazione, Set<RigaElaborazioneModel> istanzeByElaborazione) {

	TestataDettagliataModel t = new TestataDettagliataModel();
	t.setId(d.getId().getCodice());
	t.setDescrizione(d.getDescrizione());
	t.setDataInizio(d.getDataInizio());
	t.setDataFine(d.getDataFine());
	if (!(schedeByElaborazione == null || schedeByElaborazione.isEmpty())) {
	    for (Dyn2Massiveschede s : schedeByElaborazione) {
		t.getModelli().add(SchedaDinamicaModel.fromMassiveSchede(s));
	    }
	}
	if (!(filtriByElaborazione == null || filtriByElaborazione.isEmpty())) {
	    for (Dyn2MassiveFiltri s : filtriByElaborazione) {
		t.getFiltri().add(FiltriRicercaTestataModel.fromMassiveFiltri(s));
	    }
	}
	if (!(istanzeByElaborazione == null || istanzeByElaborazione.isEmpty())) {
	    t.getRighe().addAll(istanzeByElaborazione);
	}
	return t;
    }
}
