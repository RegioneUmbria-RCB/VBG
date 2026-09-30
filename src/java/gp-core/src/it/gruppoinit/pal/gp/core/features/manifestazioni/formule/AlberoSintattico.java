package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.util.ArrayList;
import java.util.List;

public class AlberoSintattico {

    List<IToken> tokens = new ArrayList<IToken>();
    private String formula;

    public AlberoSintattico() {

    }

    public AlberoSintattico(List<IToken> tokens, String formula) {

	this.tokens = tokens;
	this.formula = formula;
    }

    public IToken tokenAt(int index) {

	return this.tokens.get(index);
    }

    public int contaToken() {

	return this.tokens.size();
    }

    public int totaleTokenInclusiAnnidati() {

	int contatore = 0;
	for (IToken iToken : tokens) {
	    contatore += iToken.getValoreConteggio();
	}
	return contatore;
    }

    public String getFormula() {

	return formula;
    }
}
