package it.gruppoinit.pal.gp.core.features.autorizzazioni;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniScadenzeAutorizzazioniConcessioniException;

public interface IScadenzeAutorizzazioniConcessioniService {

    void sistemaScadenzeDelleAutorizzazioniPerAlias(String alias) throws OperazioniScadenzeAutorizzazioniConcessioniException;
}
