/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Dyn2Espressioni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;

import java.util.List;

/**
 * @author francol
 * 
 */
public interface Dyn2RegoleService extends BaseService<Dyn2Regole, PkId> {
    
    public static final char PARENTESI_APERTA = '(';
    public static final char PARENTESI_CHIUSA = ')';

    public enum OperatoreConfrontoEnum {
	EQ, NOT_EQ, GT, GT_EQ, LT, LT_EQ, IS_NULL, NOT_IS_NULL, IN, NOT_IN, MATCHES, NOT_MATCHES
    }

    public enum OperatoreLogicoEnum {
	OR, AND, NOT
    }

    /**
     * Restituisce tutte le regole che effettuano delle verifiche sul valore del campo dinamico passato come argomento
     * 
     * @param campo
     * @return
     */
    public List<Dyn2Regole> findRegoleDipendentiDaCampoInModello(Integer idCampo, Integer idModellot);

    /**
     * Effettua la validazione della sintassi delle espressioni booleane che compongono la regola dinamica. Viene
     * restituita una {@link List} di {@link Dyn2RegoleSyntaxError}, ciascuno di questi oggetti rappresenta un errore di
     * validazione. Se il metodo restituisce null o una lista vuota significa che non ci sono errori di validazione.
     * 
     * @param regola
     * @return
     */
    public List<Dyn2RegoleSyntaxError> validateExpressionSyntax(Dyn2Regole regola);

    /**
     * Cancella le espressioni eliminate e aggiorna quelle inserite
     * 
     * @param entity
     * @param espressioniDaCancellare
     */
    public void deleteAndupdate(Dyn2Regole entity, List<Dyn2Espressioni> espressioniDaCancellare);

    /**
     * Ricerca le regole filtrando per descrizione (ilike) e software
     * 
     * @param textToSearch
     * @param _codicesoftware
     * @return
     */
    public List<Dyn2Regole> findByDescrizioneAndSoftware(String textToSearch, String codicesoftware);
}
