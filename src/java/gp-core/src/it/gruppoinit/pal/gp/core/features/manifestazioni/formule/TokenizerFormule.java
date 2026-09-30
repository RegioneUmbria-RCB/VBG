package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Blocco;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Numero;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Operatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Spaziatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Variabile;

public class TokenizerFormule {

    String formula;
    int curPos = 0;
    List<IRegolaValidazione> regoleValidazione = new ArrayList<IRegolaValidazione>();

    public TokenizerFormule(List<IRegolaValidazione> regoleValidazione) {

	this.regoleValidazione = regoleValidazione;
    }

    public AlberoSintattico analizza(String formula) throws ValidazioneFormulaFallitaException {

	if (StringUtils.isEmpty(formula) || formula.replace(" ", "").length() == 0) {
	    return new AlberoSintattico();
	}
	this.formula = formula;
	this.curPos = 0;
	List<IToken> tokenList = new ArrayList<IToken>();
	IToken currToken = this.getNext();
	IToken lastToken = null;
	while (currToken != null) {
	    if (currToken.getTipo() != TipoToken.SPAZIATORE) {
		applicaRegole(lastToken, currToken);
		tokenList.add(currToken);
		lastToken = tokenList.size() == 0 ? null : tokenList.get(tokenList.size() - 1);
	    }
	    currToken = this.getNext();
	}
	applicaRegole(lastToken, null);
	return new AlberoSintattico(tokenList, formula);
    }

    private void applicaRegole(IToken lastToken, IToken currToken) throws ValidazioneFormulaFallitaException {

	for (IRegolaValidazione iRegolaValidazione : regoleValidazione) {
	    if (!iRegolaValidazione.valida(lastToken, currToken)) {
		throw new ValidazioneFormulaFallitaException(iRegolaValidazione.getErroriValidazione());
	    }
	}
    }

    private IToken getNext() throws ValidazioneFormulaFallitaException {

	try {
	    if (curPos == this.formula.length()) {
		return null;
	    }
	    int endPos = curPos;
	    String token = "";
	    while (endPos != this.formula.length()) {
		char carattere = this.formula.charAt(endPos);
		// Verifica spaziatori
		boolean isSpazio = this.isSpaziatore(carattere);
		if (isSpazio) {
		    curPos = endPos + 1;
		    return new Spaziatore();
		}
		// Variabili nel formato [NOME_VARIABILE]
		boolean parentesiQuadraAperta = this.isParentesiQuadraAperta(carattere);
		if (parentesiQuadraAperta) {
		    token = this.parseFinoAParentesiQuadraChiusa(endPos);
		    // TODO: verificare se errore (il metodo ritorna null se non trova il separatore di parentesi chiusa)
		    if (token == null) {
			throw new RuntimeException("Parentesi quadra no chiusa, controlla la formula");
		    } else {
			curPos = endPos + token.length();
			return new Variabile(token);
		    }
		}
		// Numeri nel formato 1.324
		boolean isNumero = this.isNumero(carattere);
		if (isNumero) {
		    token = this.parseFinoAFineNumero(endPos);
		    curPos = endPos + token.length();
		    return new Numero(token);
		}
		// Operatori (+-/*)
		boolean isOperatore = this.isOperatore(carattere);
		if (isOperatore) {
		    token = this.formula.substring(curPos, endPos + 1);
		    curPos = endPos + 1;
		    return new Operatore(token);
		}
		//  implementare la gestione delle parentesi
		boolean isParentesiTonda = this.isParentesiTonda(carattere);
		if (isParentesiTonda) {
		    token = this.parseParentesiChiusa(endPos);
		    if (StringUtils.isNotEmpty(token)) {
			curPos = endPos + token.length() + 2;
			return new Blocco(new TokenizerFormule(this.regoleValidazione).analizza(token));
		    }
		}
		throw new TokenSintatticoNonValidoException(
			"Token non valido nella formula: " + carattere + " nella posizione " + new Integer(curPos).toString());
	    }
	    // TODO: sollevare eccezione, abbiamo iniziato a parsare un token senza trovarne la fine
	    // valutare il tipo di eccezione da sollevare
	    curPos = this.formula.length();
	    return null;
	} catch (TokenSintatticoNonValidoException ex) {
	    throw new RuntimeException("Errore alla posizione " + new Integer(curPos).toString() + ": " + ex.getMessage());
	}
    }

    private String parseFinoAFineNumero(int startPos) {

	int endPos = startPos;
	while (endPos < this.formula.length()) {
	    char carattere = this.formula.charAt(endPos);
	    boolean isFineNumero = this.isFineNumero(carattere);
	    if (isFineNumero) {
		return this.formula.substring(startPos, endPos);
	    }
	    endPos++;
	}
	return this.formula.substring(startPos);
    }

    private boolean isSpaziatore(char carattere) {

	return carattere == ' ' || carattere == '\t';
    }

    private boolean isNumero(char carattere) {

	return ".1234567890".indexOf(carattere) != -1;
    }

    private boolean isFineNumero(char carattere) {

	return !this.isNumero(carattere);
    }

    private String parseFinoAParentesiQuadraChiusa(int startPos) {

	int endPos = startPos;
	while (endPos < this.formula.length()) {
	    char carattere = this.formula.charAt(endPos);
	    boolean isParantesiChiusa = this.isParentesiQuadraChiusa(carattere);
	    if (isParantesiChiusa) {
		return this.formula.substring(startPos, endPos + 1);
	    }
	    endPos++;
	}
	return null;
    }

    private boolean isParentesiQuadraChiusa(char carattere) {

	return carattere == ']';
    }

    private boolean isParentesiQuadraAperta(char carattere) {

	return carattere == '[';
    }

    private boolean isSeparatore(char carattere) {

	return Character.isWhitespace(carattere);
    }

    private boolean isOperatore(char carattere) {

	return "+-*/".indexOf(carattere) > -1;
    }

    private boolean isParentesiTonda(char carattere) {

	return "()".indexOf(carattere) > -1;
    }

    private boolean isParentesiTondaChiusa(char carattere) {

	return carattere == ')';
    }

    private boolean isParentesiTondaAperta(char carattere) {

	return carattere == '(';
    }

    private String parseParentesiChiusa(int startPos) {

	int counter = 0;
	int endPos = startPos;
	while (endPos < this.formula.length()) {
	    char carattere = this.formula.charAt(endPos);
	    boolean isParentesiAperta = isParentesiTondaAperta(carattere);
	    if (isParentesiAperta) {
		counter++;
	    } else {
		boolean isParentesiChiusa = isParentesiTondaChiusa(carattere);
		if (isParentesiChiusa) {
		    counter--;
		    if (counter == 0 && startPos != endPos) {
			return this.formula.substring(startPos + 1, endPos);
		    }
		}
	    }
	    endPos++;
	}
	if (counter != 0) {
	    throw new RuntimeException("Parentesi non bilanciate, controlla la formula");
	}
	return null;
    }
}
