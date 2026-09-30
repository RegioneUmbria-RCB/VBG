package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;

import java.util.ArrayList;
import java.util.List;

public class TempirispostaHelper extends Tempirisposta {

    private static final long serialVersionUID = -5148714640372375206L;
    private Tipiprocedure tipiprocedure;
    private List<AmministrazioniHelper> amministrazionis = new ArrayList<AmministrazioniHelper>();

    public Tipiprocedure getTipiprocedure() {

	return tipiprocedure;
    }

    public void setTipiprocedure(Tipiprocedure tipiprocedure) {

	this.tipiprocedure = tipiprocedure;
    }

    public List<AmministrazioniHelper> getAmministrazionis() {

	return amministrazionis;
    }

    public void setAmministrazionis(List<AmministrazioniHelper> amministrazionis) {

	this.amministrazionis = amministrazionis;
    }
}
