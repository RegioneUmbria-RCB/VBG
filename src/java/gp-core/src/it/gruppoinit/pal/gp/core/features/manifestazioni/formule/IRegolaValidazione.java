package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

public interface IRegolaValidazione {

    public String getErroriValidazione();

    public boolean valida(IToken lastToken, IToken currToken);
}
