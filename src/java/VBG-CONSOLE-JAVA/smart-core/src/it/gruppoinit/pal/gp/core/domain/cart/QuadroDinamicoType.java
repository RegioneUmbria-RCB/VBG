/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.cart;

import it.eng.suap.xengine.model.modulistica.QuadroType;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;

/**
 * Sottoclasse di QuadroType per inserire nell'elenco dei quadri di un modulo definito con l'XML di RFC186 anche dei
 * quadri che derivano dalla definizione delle schede dinamiche
 * 
 * @author francol
 *
 */
public class QuadroDinamicoType extends QuadroType {

    private Integer idModello;
    private String idComune;
    private ModellidinamiciHelper quadroHelper;

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public Integer getIdModello() {

	return idModello;
    }

    public void setIdModello(Integer idModello) {

	this.idModello = idModello;
    }

    public ModellidinamiciHelper getQuadroHelper() {

	return quadroHelper;
    }

    public void setQuadroHelper(ModellidinamiciHelper quadroHelper) {

	this.quadroHelper = quadroHelper;
    }

    @Override
    public String getCodice() {

	return quadroHelper != null ? quadroHelper.getTitolo() : super.getCodice();
    }
    
    
}
