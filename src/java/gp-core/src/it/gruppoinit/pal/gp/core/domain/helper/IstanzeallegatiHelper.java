package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author gianpaolot Classe utilizzata per poter ragguppare le informazioni da visulualizzare su una lista
 * 
 */
public class IstanzeallegatiHelper {

    // campi per poter realizzare un raggruppemento secondo la logica:
    // Ogni oggetto Helper deve contenere un oggetto Inventarioprocedimenti e una lista di
    // Istanzeallegati in cui esso è presente
    private Inventarioprocedimenti inventarioprocedimenti;
    private List<Istanzeallegati> istanzeAllegatis = new ArrayList<Istanzeallegati>();

    public IstanzeallegatiHelper() {

	this.inventarioprocedimenti = new Inventarioprocedimenti();
    }

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
    }

    public List<Istanzeallegati> getIstanzeAllegatis() {

	return istanzeAllegatis;
    }

    public void setIstanzeAllegatis(List<Istanzeallegati> istanzeAllegatis) {

	this.istanzeAllegatis = istanzeAllegatis;
    }
}
