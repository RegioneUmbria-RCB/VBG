/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.autocompiler;

import it.eng.suap.xengine.model.modulistica.CampoType;
import it.eng.suap.xengine.model.modulistica.FileType;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico;

import java.util.List;

/**
 * @author francol Interfaccia che definisce le API esposte da tutti i
 *         validatori custom configurabili per i campi a completamento
 *         automatico
 */
public interface IAutocompilerValidator {

    /**
     * Metodo invocato per la validazione dei campi di tipo {@link CampoType}.
     * Il metodo deve occuparsi di recuperare il valore del campo dai dati della
     * domanda passati come argomento. Nel caso di campi indicizzati
     * (all'interno di tabelle) il metodo viene invocato una volta per ogni riga
     * della tabella ogni volta con un valore di rowIndex pari all'indice della
     * riga. Nel caso di campi non indicizzati il metodo viene invocato una
     * volta sola con rowIndex = -1
     * 
     * @param campo
     *            campo da validare
     * @param refModulo
     *            riferimento del modulo necessario per recuperare il
     *            {@link ValoreIdSemantico}
     * @param idQuadro
     *            id del quadro che si sta validando
     * @param idModulo
     *            id del modulo di apprtenenza del quadro
     * @param rowIndex
     *            indice della riga che si sta validando (a partire da 0), -1
     *            per campi non indicizzati
     * @param userData
     *            struttura dati che contine tutti i dati della domanda già
     *            compilati, compresi quelli che stiamo validando
     * @param configOptions
     *            parametri di configurazione del validator il cui nome e
     *            utilizzo dipende dal validator
     * @return {@link List} di {@link ErroreValidazione}. E' possibile
     *         restituire oggetti {@link ErroreValidazione} che riguardano anche
     *         campi diversi da quello a cui è associata la validazione
     */
    public List<ErroreValidazione> validaValoreCampo(CampoType campo, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions);

    /**
     * Metodo invocato per la validazione dei campi fileupload di tipo
     * {@link FileType}. Il metodo deve occuparsi di recuperare il valore del
     * campo dai dati della domanda passati come argomento. Nel caso di campi
     * indicizzati (all'interno di tabelle) il metodo viene invocato una volta
     * per ogni riga della tabella ogni volta con un valore di rowIndex pari
     * all'indice della riga. Nel caso di campi non indicizzati il metodo viene
     * invocato una volta sola con rowIndex = -1
     * 
     * @param campo
     *            campo file da validare
     * @param refModulo
     *            riferimento del modulo necessario per recuperare il
     *            {@link ValoreIdSemantico}
     * @param idQuadro
     *            id del quadro che si sta validando
     * @param idModulo
     *            id del modulo di apprtenenza del quadro
     * @param rowIndex
     *            indice della riga che si sta validando 
     * @param userData
     *            struttura dati che contine tutti i dati della domanda già
     *            compilati, compresi quelli che stiamo validando
     * @param configOptions
     *            parametri di configurazione del validator il cui nome e
     *            utilizzo dipende dal validator
     * @return {@link List} di {@link ErroreValidazione}. E' possibile
     *         restituire oggetti {@link ErroreValidazione} che riguardano anche
     *         campi diversi da quello a cui è associata la validazione
     */
    public List<ErroreValidazione> validaValoreFile(FileType campo, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions);

}
