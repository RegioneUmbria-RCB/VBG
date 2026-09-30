/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipibandocampigraduatDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandocampigraduat;
import it.gruppoinit.pal.gp.core.service.TipibandocampigraduatService;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class TipibandocampigraduatServiceImpl extends BaseServiceImpl<Tipibandocampigraduat, PkId> implements TipibandocampigraduatService {

    private TipibandocampigraduatDAO tipibandocampigraduatDAO;

    @Autowired
    public void setTipibandocampigraduatDAO(TipibandocampigraduatDAO tipibandocampigraduatDAO) {

	this.tipibandocampigraduatDAO = tipibandocampigraduatDAO;
    }

    @Override
    protected Class<Tipibandocampigraduat> getEntityClass() {

	return Tipibandocampigraduat.class;
    }

    @Override
    public void delete(Tipibandocampigraduat entity) {

	// §§§BEGIN§§§
	tipibandocampigraduatDAO.delete(entity);
	updateOrDeleteOrder(entity, "delete");
	// §§§END§§§
    }

    @Override
    public List<Tipibandocampigraduat> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return tipibandocampigraduatDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Tipibandocampigraduat findById(PkId id) {

	// §§§BEGIN§§§
	return tipibandocampigraduatDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Tipibandocampigraduat entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    this.insertOrder(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(Tipibandocampigraduat entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    this.updateOrDeleteOrder(entity, "update");
	}
	// §§§END§§§
    }

    @Override
    public List<Tipibandocampigraduat> findByTipigraduatoriet(Tipibandocampigraduat tipibandocampigraduat) {

	// §§§BEGIN§§§
	return tipibandocampigraduatDAO.findByTipigraduatoriet(tipibandocampigraduat);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * 
     * Metodo che aggiorna il campo ordine sugli oggetti Tipibandocampigraduat in caso di upadte o delete. In caso di
     * "update" viene aggiornato il campo ordine di tutti gli oggetti che hanno il valore dell'ordine >= a quello
     * dell'oggetto che è stato modificato. In caso di "update" viene passato l'oggetto aggioranto e questo metodo si
     * occuperà di aggiornare il valore nel database. In caso di "delete" viene aggiornato il campo ordine di tutti gli
     * oggetti che hanno il valore del campo ordine > a quello dell'oggetto che stato eliminato.L'oggetto viene prima
     * eliminato e poi chiamato questo metodo.
     * 
     * @author francescop
     * 
     * @param list
     *            Lista di oggetti Tipibandocampigraduat filtrati per Tipobando
     * 
     * @param tipibandocampigraduat
     *            In caso di "update" è l'oggetto che vogliamo aggiornare. In caso "delete" è l'oggetto che è stato
     *            eliminato(prima viene eliminato l'oggetto e poi viene chiamato questo metodo).
     * @param updateOrDelete
     *            Tipo di metodo che deve essere chiamato. Le possibilità sono "update" o "delete". In caso di "update"
     *            viene modificato il valore dell'ordine e aggiornati tutti gli altri campi. In caso di "delete" vengono
     *            aggiornati tutti gli ordini.
     * @param result
     *            Utilizzato per effetuare la validazioni dei campi che stiamo aggiornando
     * 
     */
    private void updateOrDeleteOrder(Tipibandocampigraduat tipibandocampigraduat, String updateOrDelete) {

	// §§§BEGIN§§§
	List<Tipibandocampigraduat> list = this.findByTipigraduatoriet(tipibandocampigraduat);
	Integer order = tipibandocampigraduat.getOrdine();
	Integer orderForward = 0;
	Integer codiceTipo1 = tipibandocampigraduat.getId().getCodice();
	if (updateOrDelete.equals("update")) {
	    // controllo se è stato inserito un valore nel campo ordine del form
	    if (order != null) {
		String mode = "";
		// scorro la lista per verificare se il il nuovo valore dell'ordine è maggiore o minore rispetto al
		// precedente
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		    Tipibandocampigraduat tipibandocampigraduat3 = (Tipibandocampigraduat) iterator.next();
		    Integer codice2 = tipibandocampigraduat3.getId().getCodice();
		    if (codice2.compareTo(codiceTipo1) == 0) {
			// verifico se il valore dell'ordine che è stato modificato è minore del precedente.
			// Se è così dovrò implementare una determinata logica per aggiornare tutti i criteri
			// successivi
			if (tipibandocampigraduat.getOrdine() < tipibandocampigraduat3.getOrdine()) {
			    mode = "back";
			    order = order + 1;
			    break;
			}
			// verifico se il valore dell'ordine che è stato modificato è maggiore del precedente.
			// Se è così dovrò implementare una determinata logica per aggiorarnare tutti i criteri
			// precedenti
			if (tipibandocampigraduat.getOrdine() > tipibandocampigraduat3.getOrdine()) {
			    mode = "forward";
			    orderForward = tipibandocampigraduat3.getOrdine();
			    break;
			}
			// verifico se il valore dell'ordine che è stato modificato è uguale al precedente.
			// Se è così non viene fatto nulla e si ritorna al metodo chiamante
			if (tipibandocampigraduat.getOrdine().intValue() == tipibandocampigraduat3.getOrdine().intValue()) {
			    mode = "none";
			    break;
			}
		    }
		}
		if (!mode.equals("none")) {
		    // Scorro la lista che deve essere filtrata per tipibando
		    for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
			Tipibandocampigraduat tipibandocampigraduat2 = (Tipibandocampigraduat) iterator2.next();
			Integer codiceTipo2 = tipibandocampigraduat2.getId().getCodice();
			// Controllo per poter aggiornare il valore del campo ordine dell'oggetto inserito
			if (codiceTipo2.compareTo(codiceTipo1) == 0) {
			    tipibandocampigraduatDAO.update(tipibandocampigraduat);
			} else {
			    if (mode.equals("back")) {
				// aggiorno il valore dell'ordine degli oggetti già inseriti e che hanno ordine maggiore
				// o uguale a quello inserito
				if (tipibandocampigraduat.getOrdine() <= tipibandocampigraduat2.getOrdine()) {
				    // Verifico che l'ordine è diverso da 9 che è il valore massimo accettato
				    if (!((tipibandocampigraduat2.getOrdine()).compareTo(9) == 0)) {
					tipibandocampigraduat2.setOrdine(order);
					tipibandocampigraduatDAO.update(tipibandocampigraduat2);
					order++;
				    }
				}
			    }
			    if (mode.equals("forward")) {
				// aggiorno il valore dell'ordine degli oggetti già inseriti e che hanno ordine minore o
				// uguale a quello inserito
				if (tipibandocampigraduat.getOrdine() >= tipibandocampigraduat2.getOrdine()
					&& tipibandocampigraduat2.getOrdine() > orderForward) {
				    tipibandocampigraduat2.setOrdine(order);
				    tipibandocampigraduatDAO.update(tipibandocampigraduat2);
				    order = order + 1;
				}
			    }
			}
		    }
		}
	    } else {
		// aggiorno l'oggetto con ordine uguale a null.
		// L'oggetto viene validato.
		tipibandocampigraduatDAO.update(tipibandocampigraduat);
	    }
	}
	if (updateOrDelete.equals("delete")) {
	    order += 1;
	    // scorro la lista
	    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		Tipibandocampigraduat tipibandocampigraduat2 = (Tipibandocampigraduat) iterator.next();
		// aggiorno il valore dell'ordine degli oggetti che hanno il valore dell'ordine maggiore a quello
		// dell'oggetto inserito.
		if (tipibandocampigraduat.getOrdine() < tipibandocampigraduat2.getOrdine()) {
		    order = tipibandocampigraduat2.getOrdine();
		    tipibandocampigraduat2.setOrdine(order - 1);
		    tipibandocampigraduatDAO.update(tipibandocampigraduat2);
		    order--;
		}
	    }
	}
	// §§§END§§§
    }

    /**
     * Metodo per l'inserimento di un nuovo oggetto Tipibandocampigraduat. Se il valore del campo ordine è diverso
     * rispetto a quelli presenti, l'oggetto viene semplicememtne inserito altrimenti oltre all'inserimento viene
     * aggiornato il campo ordine di tutti gli oggetti che hanno il valore dell'ordine >= a quello dell'oggetto
     * inserito.
     * 
     * @author francescop
     * 
     * @param list
     *            Lista di Tipibandocampigraduat non filtrata per Tipobando. Se la lista passata fosse filtrata funziona
     *            ugualamente il metodo.
     * @param tipibandocampigraduat
     *            Oggetto Tipibandocampigraduat che vogliamo inserire.
     * @param result
     *            Utilizzato per effetuare la validazioni dei campi che stiamo aggiornando
     */
    private void insertOrder(Tipibandocampigraduat tipibandocampigraduat) {

	// §§§BEGIN§§§
	List<Tipibandocampigraduat> list = this.findByTipigraduatoriet(tipibandocampigraduat);
	// controllo se la lista non è vuota altrimenti inserisco l'oggetto senza fare controlli
	if (!list.isEmpty()) {
	    if (list.size() < 9) {
		Integer order = tipibandocampigraduat.getOrdine();
		// verifico che il valore del campo ordine è diverso da null
		if (order != null) {
		    // controllo se il valore del campo ordine inserito è minore alla dimensione della lista di
		    // Tipibandocampigraduat
		    if (list.size() >= order) {
			// Aggiungo l'oggetto nella posizione corretta nella lista.La lista deve essere ordinata in modo
			// ascendente
			list.add(tipibandocampigraduat.getOrdine() - 1, tipibandocampigraduat);
			tipibandocampigraduatDAO.insert(tipibandocampigraduat);
			order += 1;
			// scorro la lista per aggiornare il valore del campo ordine degli oggetti Tipibandocampigraduat
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			    Tipibandocampigraduat tipibandocampigraduat2 = (Tipibandocampigraduat) iterator.next();
			    // controllo se l'ordine dell'oggetto inserito è minore dell'ordine deglio oggetti già
			    // presenti
			    // e aggiorno il valore dell'ordine
			    if (tipibandocampigraduat.getId().getCodice().intValue() != tipibandocampigraduat2.getId().getCodice().intValue()
				    && tipibandocampigraduat2.getOrdine() >= tipibandocampigraduat.getOrdine()) {
				if (order.compareTo(9) <= 0) {
				    tipibandocampigraduat2.setOrdine(order);
				    tipibandocampigraduatDAO.update(tipibandocampigraduat2);
				    order++;
				}
			    }
			}
		    } else {
			Integer correctOrder = 1;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			    Tipibandocampigraduat tipibandocampigraduat3 = (Tipibandocampigraduat) iterator.next();
			    // verifico se è presente un altro oggetto con lo stesso ordine , in tal caso lo aggiorno
			    if (tipibandocampigraduat3.getOrdine().compareTo(tipibandocampigraduat.getOrdine()) <= 0) {
				tipibandocampigraduat3.setOrdine(correctOrder);
				tipibandocampigraduatDAO.update(tipibandocampigraduat3);
			    }
			    correctOrder++;
			}
			tipibandocampigraduatDAO.insert(tipibandocampigraduat);
		    }
		} else {
		    // inserisco direttamente l'oggetto.
		    // L'oggetto viene validato.
		    tipibandocampigraduatDAO.insert(tipibandocampigraduat);
		}
	    } else {
		throw new RuntimeException("Attenzione: Non è possibile inserire più di 9 criteri di ordinamento ");
	    }
	} else {
	    // inserisco direttamente l'oggetto poichè la lista è vuota
	    tipibandocampigraduatDAO.insert(tipibandocampigraduat);
	}
	// §§§END§§§
    }
}
