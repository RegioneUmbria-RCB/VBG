package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;

import java.util.ArrayList;
import java.util.List;

public class IstanzecollegateCommand extends BaseCommand {

    private Istanzecollegate entity;
    private List<IstanzecollegateHelper> istanzecollegateHelpers = new ArrayList<IstanzecollegateHelper>();

    public Istanzecollegate getEntity() {

	return entity;
    }

    public void setEntity(Istanzecollegate entity) {

	this.entity = entity;
    }

    public List<IstanzecollegateHelper> getIstanzecollegateHelpers() {

	return istanzecollegateHelpers;
    }

    public void setIstanzecollegateHelpers(List<IstanzecollegateHelper> istanzecollegateHelpers) {

	this.istanzecollegateHelpers = istanzecollegateHelpers;
    }
}
