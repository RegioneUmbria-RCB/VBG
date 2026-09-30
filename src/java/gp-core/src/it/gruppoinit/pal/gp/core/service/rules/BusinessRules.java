package it.gruppoinit.pal.gp.core.service.rules;

public abstract class BusinessRules implements ManagingRules {

    private boolean insert;
    private boolean update;

    public BusinessRules() {

    }

    /**
     * Ogni classe che estende questa deve implementare questo metodo nel quale vengono impostati i valori di default
     * per insert e update e vengono costruite le regole custom (qualora ve ne fossero).
     * 
     */
    protected abstract void buildCustomRules();

    /**
     * Se dopo avere effettuato il bind dell'oggetto e l'oggetto non è stato trovato va tentata una insert
     * 
     * @return
     */
    public boolean isInsert() {

	return this.insert;
    }

    /**
     * Setta la proprietà che decide se dopo avere effettuato il bind dell'oggetto e l'oggetto non è stato trovato va
     * tentata una insert
     * 
     * @param insert
     */
    public void setInsert(boolean insert) {

	this.insert = insert;
    }

    /**
     * Se dopo avere effettuato il bind dell'oggetto e l'oggetto è stato trovato va tentata una update dei dati
     * 
     * @return
     */
    public boolean isUpdate() {

	return this.update;
    }

    /**
     * Setta il parametro che decide se dopo avere effettuato il bind dell'oggetto e l'oggetto è stato trovato va
     * tentata una update dei dati
     * 
     * @param update
     */
    public void setUpdate(boolean update) {

	this.update = update;
    }

    /**
     * Ogni Classe che estende questa classe astratta può definirsi una serie di regole aggiuntive oltre a quelle di
     * insert e update
     * 
     * @param rule
     *            Il nome della regola custom che si vuole recuperare
     * @return
     */
    public abstract boolean getCustomRule(String rule);

    /**
     * Setta una nuova regola con il nome ed il valore specificati Il nome della regola dovrebbe essere preso da una
     * enumeration definita a livello di classe che estende questa classe astratta
     * 
     * @param rule
     * @param value
     */
    public abstract void setCustomRule(String rule, boolean value);
}
