package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.OneriPerCausaleHelper;

public class OneriCommand extends StarBaseCommand {

    private List<OneriPerCausaleHelper> oneri = new ArrayList<OneriPerCausaleHelper>();
    private Inventarioprocedimentioneri onere = new Inventarioprocedimentioneri();
    private Inventarioprocedimenti endo = new Inventarioprocedimenti();
    private Tipicausalioneri causale = new Tipicausalioneri();

    public Tipicausalioneri getCausale() {

	return causale;
    }

    public void setCausale(Tipicausalioneri causale) {

	this.causale = causale;
    }

    public Inventarioprocedimenti getEndo() {

	return endo;
    }

    public void setEndo(Inventarioprocedimenti endo) {

	this.endo = endo;
    }

    public List<OneriPerCausaleHelper> getOneri() {

	return oneri;
    }

    public List<OneriPerCausaleHelper> getOneri(int start, int limit) {

	int end = Math.min(start + limit, getOneri().size());
	return getOneri().subList(start, end);
    }

    public Inventarioprocedimentioneri getOnere() {

	return onere;
    }

    public void setOnere(Inventarioprocedimentioneri onere) {

	this.onere = onere;
	this.causale = onere.getTipicausalioneri();
	this.endo = onere.getInventarioprocedimenti();
    }

    public boolean getDisplayComuniResponsable() {

	return super.getDisplayComuniResponsable() && getOnere().getId().getCodice() == null;
    }

    public boolean getDisplayComuneLocalizzazione() {

	return super.getDisplayComuneLocalizzazione() && getOnere() != null;
    }
}
