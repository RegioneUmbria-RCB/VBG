package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class RegolaValidazioneVariabile extends AbstractRegolaValidazione {

    private List<String> varGlobalELivelliservizi;

    public RegolaValidazioneVariabile(List<String> varGlobalELivelliservizi) {

	this.varGlobalELivelliservizi = varGlobalELivelliservizi;
    }

    @Override
    public boolean valida(IToken lastToken, IToken currToken) {

	if (currToken == null) {
	    return true;
	}
	if (currToken.getTipo() == TipoToken.VARIABILE) {
	    if (varGlobalELivelliservizi != null) {
		for (String gls : varGlobalELivelliservizi) {
		    if (currToken.getValore().equals(gls)) {
			return true;
		    }
		}
		super.setMessaggio("Il segnaposto " + currToken.getValore() + " non è valido");
		return false;
	    }
	}
	return true;
    }
}
