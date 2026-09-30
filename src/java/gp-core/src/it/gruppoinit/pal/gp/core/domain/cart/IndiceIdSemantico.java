/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.cart;

import it.eng.suap.xengine.model.modulistica.TabellaType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.apache.commons.lang.mutable.MutableInt;

/**
 * Questa classe serve per la gestione degli indici per l'accesso ai dati di una
 * matrice ad n dimensioni. In pratica ogni istanza memorizza n numeri interi,
 * dove ciascuno rappresenta un indice per una delle dimensioni della matrice.
 * 
 * E' utilizzata per la gestione delle tabelle annidate durante la ricorsione
 * sulle maschere della modulistica CART sia per la lettura dei dati trasmessi
 * sia per il rendering delle maschere stesse.
 * 
 * L'oggetto viene inizialmente creato con zero livelli e quidi memorizza una lista vuota di indici.
 * con il metodo incrmentaLivello(Object) viene aggiunto un nuovo indice alla lista degli indici inizialmente impostato a zero.
 * L'oggetto passato come argomento viene messo in uno stack ed associato al nuovo livello di indicizzazione che si è appena aggiunto all'indice.
 * Nel caso delle maschere CART l'oggetto messo nello stack è un'istanza di tipo {@link TabellaType} che rappresenta la tabella della modulistica
 * per cui si introduce un nuovo livello di indicizzazione nei dati associati agli id semantici.
 * 
 * Con il metodo decrementaLivello() si elimina l'ultimo indice dalla lista degli indici, inoltre l'ultimo oggetto che era stato aggiunto 
 * allo stack dei livelli viene tolto dallo stack e restituito dal metodo. Dopo questa chiamata l'istanza gestirà un livello in meno di indicizzazione.
 * 
 * Il livello corrente dell'istanza è sempre l'ultimo che è stato aggiunto e non ancora rimosso (quello incima allo stack).
 * L'indice corrente è sempre quello associato al livello corrente.
 * 
 * Al termine dell'elaborazione di ogni tabella si elimina l'ultimo livello che era stato aggiunto all'inizio dell'elaborazione e 
 * l'indice corrente torna ad essere quello delle righe della tabella contenitrice, che diventa il livello corrente.
 * 
 * Quando si esce dall'ultima tabella i dati associati agli id semantici non saranno più insdicizzati e infatti
 *  l'istanza torna ad avere una lista vuota di indici e uno stack vuoto di livelli.
 * 
 * @author francol
 *
 */
public class IndiceIdSemantico {

    private List<Integer> indexes;
    private Deque<Object> levels;

    public IndiceIdSemantico() {
	indexes = new ArrayList<Integer>();
	levels = new ArrayDeque<Object>();
    }

    /**
     * Restituisce l'indice per il livello di annidamento passato come argomento
     * 
     * @return
     */
    public int getIndicePerLivello(int livello) {
	int retVal = -1;
	if (livello > -1 && livello < indexes.size()) {
	    retVal = indexes.get(livello);
	}
	return retVal;
    }

    /**
     * Restituisce l'indice per il livello di annidamento corrente
     * 
     * @return
     */
    public int getIndice() {
	return getIndicePerLivello(indexes.size() - 1);
    }

    public void incrementaLivello(Object level) {
	indexes.add(0);
	levels.push(level);
    }

    public Object decrementaLivello() {
	indexes.remove(indexes.size() - 1);
	return levels.pop();
    }

    public Object getLivello() {
	return levels.peek();
    }

    public Integer[] vettoreIndici() {
	return indexes.toArray(new Integer[indexes.size()]);
    }

    public List getLivelli() {
	List levelsStack = new ArrayList<Object>(this.levels); 
	Collections.reverse(levelsStack);
	return levelsStack;
    }

    public int contaLivelli() {
	return indexes.size();
    }

    public void incrementaIndiceDi(int increment) {
	int idx = indexes.get(indexes.size() - 1);
	indexes.set(indexes.size() - 1, idx + increment);
    }

    public void decrementaIndiceDi(int increment) {
	incrementaIndiceDi(-increment);
    }

    public void incrementaIndice() {
	incrementaIndiceDi(1);
    }

    public void decrementaIndice() {
	decrementaIndiceDi(1);
    }
    
    public void resettaIndice(){
	indexes.set(indexes.size() - 1, 0);
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder("[");
	if (indexes.size() > 0) {
	    for (int i = 0; i < indexes.size(); i++) {
		sb.append(indexes.get(i)).append(",");
	    }
	    sb.deleteCharAt(sb.length() - 1);
	}
	sb.append("]");
	return sb.toString();
    }
    
}
