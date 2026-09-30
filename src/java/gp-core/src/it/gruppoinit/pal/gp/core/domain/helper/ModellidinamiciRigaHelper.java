package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;

import java.util.ArrayList;
import java.util.List;

public class ModellidinamiciRigaHelper {

    private int numRiga;
    private List<ModellidinamiciColonnaHelper> colonne = new ArrayList<ModellidinamiciColonnaHelper>();

    private ModellidinamiciRigaHelper() {

	super();
	this.numRiga = -1;
    }

    public ModellidinamiciRigaHelper(int numRiga) {

	this();
	this.numRiga = numRiga;
    }

    public int getNumRiga() {

	return numRiga;
    }

    public void setNumRiga(int numRiga) {

	this.numRiga = numRiga;
    }

    public List<ModellidinamiciColonnaHelper> getColonne() {

	return colonne;
    }

    public Dyn2Regole getRegolaAttivazioneUnica() {

	Dyn2Regole sameRule = null;
	Dyn2Regole tempRule = null;
	for (ModellidinamiciColonnaHelper colonna : colonne) {
	    tempRule = colonna.getRegolaAttivazione();
	    if (tempRule == null) {
		return null;
	    } else {
		if (sameRule == null) {
		    sameRule = tempRule;
		} else {
		    if (!sameRule.getId().getCodice().equals(tempRule.getId().getCodice())) {
			return null;
		    }
		}
	    }
	}
	return sameRule;
    }

    /*
    public void setColonne(List<ModellidinamiciColonnaHelper> colonne) {

    this.colonne = colonne;
    }
    */
    public int contaColonneCampo() {

	int count = 0;
	for (ModellidinamiciColonnaHelper colonna : colonne) {
	    if (colonna.getCampo() != null) {
		count++;
	    }
	}
	return count;
    }
}
