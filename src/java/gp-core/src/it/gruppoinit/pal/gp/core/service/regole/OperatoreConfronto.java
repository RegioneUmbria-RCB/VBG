package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;

/**
 * Intefaccia implementata da tutti gli operatori di confronto
 * 
 * @author francol
 * 
 */
public interface OperatoreConfronto {
    
    public static final String VALORE_CONFRONTO_TODAY = "TODAY";

    public Boolean confronta(Object value, String compareTo) throws Dyn2RegoleSyntaxError;
}
