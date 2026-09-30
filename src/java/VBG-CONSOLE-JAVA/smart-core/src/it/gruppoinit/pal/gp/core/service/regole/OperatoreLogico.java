package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;

/**
 * Intefaccia implementata da tutti gli operatori di confronto
 * 
 * @author francol
 * 
 */
public interface OperatoreLogico {
    
    public Boolean eseguiOperazioneLogica(EspressioneBooleana expr1, EspressioneBooleana expr2) throws Dyn2RegoleSyntaxError;
}
