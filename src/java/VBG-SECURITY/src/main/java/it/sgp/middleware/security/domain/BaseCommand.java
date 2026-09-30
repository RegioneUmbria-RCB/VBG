package it.sgp.middleware.security.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * classe base che devono estendere i command per incorporare le funzionalità di base legate alla visualizzazione su JSP
 * 
 * @author fabrizioc
 * 
 */
public abstract class BaseCommand<E> {

    /**
     * modalità inserimento dati
     */
    public static final int NEW = 0;
    /**
     * modalità visualizzazione dati
     */
    public static final int VIEW = 1;
    /**
     * modalità modifica dati
     */
    public static final int EDIT = 2;
    /**
     * modalità lista dati
     */
    public static final int LIST = 3;
    /**
     * modalità ricerca dati
     */
    public static final int SEARCH = 4;
    /**
     * modalità cancellazione effettuata
     */
    public static final int DELETED = 5;
    /**
     * modalità readonly sulla pagina
     */
    public static final Integer READONLY = 6;
    private int displayMode;

    /**
     * metodo per settare la modalità di visualizzazione per la JSP
     * 
     * @param displayMode
     *            (vedi campi statici dell'oggetto)
     */
    public void setDisplayMode(int displayMode) {

	this.displayMode = displayMode;
    }

    /**
     * metodo per recuperare la modalità di visualizzazione corrente
     * 
     * @return (vedi campi statici dell'oggetto)
     */
    public int getDisplayMode() {

	return displayMode;
    }

    /**
     * Mappa da utilizzare nelle jsp con EL esempio <code>oggettoDiDominio.displayConstants.NEW</code>
     * 
     * @return la mappa con i valori delle costanti di diplay
     */
    public Map<String, Integer> getDisplayConstants() {

	Map<String, Integer> constants = new HashMap<String, Integer>();
	constants.put("VIEW", VIEW);
	constants.put("NEW", NEW);
	constants.put("EDIT", EDIT);
	constants.put("LIST", LIST);
	constants.put("SEARCH", SEARCH);
	constants.put("DELETED", DELETED);
	constants.put("READONLY", READONLY);
	return constants;
    }

    public abstract E getEntity();

    public abstract void setEntity(E entity);
}
