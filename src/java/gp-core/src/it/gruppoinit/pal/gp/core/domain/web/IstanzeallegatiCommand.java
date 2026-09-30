package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiHelper;

import java.util.ArrayList;
import java.util.List;

public class IstanzeallegatiCommand extends BaseCommand {

    private Istanzeallegati entity;
    private Allegati allegati;
    private List<IstanzeallegatiHelper> istanzeallegatiHelpers = new ArrayList<IstanzeallegatiHelper>();

    public IstanzeallegatiCommand() {

	this.entity = new Istanzeallegati();
	this.allegati = new Allegati();
    }

    public Istanzeallegati getEntity() {

	return entity;
    }

    public void setEntity(Istanzeallegati entity) {

	this.entity = entity;
    }

    public Allegati getAllegati() {

	return allegati;
    }

    public void setAllegati(Allegati allegati) {

	this.allegati = allegati;
    }

    public List<IstanzeallegatiHelper> getIstanzeallegatiHelpers() {

	return istanzeallegatiHelpers;
    }

    public void setIstanzeallegatiHelpers(List<IstanzeallegatiHelper> istanzeallegatiHelpers) {

	this.istanzeallegatiHelpers = istanzeallegatiHelpers;
    }
}
