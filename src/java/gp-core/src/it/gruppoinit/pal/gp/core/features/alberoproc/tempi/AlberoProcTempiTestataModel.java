package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import java.util.TreeSet;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.AlberoprocTempi;
import it.gruppoinit.pal.gp.core.domain.TempiFoD;

public class AlberoProcTempiTestataModel {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "titolo")
    private String titolo;
    @XmlElement(name = "dettaglio")
    private TreeSet<AlberoProcTempiDettaglioModel> dettaglio;

    public static AlberoProcTempiTestataModel FromAlberoprocTempi(AlberoprocTempi tempo) {

	if (tempo == null) {
	    return new AlberoProcTempiTestataModel();
	}
	AlberoProcTempiTestataModel model = new AlberoProcTempiTestataModel();
	model.setId(tempo.getTempiFO().getId().getCodice());
	model.setTitolo(tempo.getTempiFO().getTitolo());
	model.dettaglio = new TreeSet<AlberoProcTempiDettaglioModel>(new AlberoProcTempiDettaglioModelComparator());
	for (TempiFoD dettaglio : tempo.getTempiFO().getDettaglio()) {
	    model.dettaglio.add(AlberoProcTempiDettaglioModel.FromTempiFoD(dettaglio));
	}
	return model;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public TreeSet<AlberoProcTempiDettaglioModel> getDettaglio() {

	return dettaglio;
    }

    public void setDettaglio(TreeSet<AlberoProcTempiDettaglioModel> dettaglio) {

	this.dettaglio = dettaglio;
    }
}
