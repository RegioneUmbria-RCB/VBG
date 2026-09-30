package it.gruppoinit.pal.gp.core.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.springframework.orm.hibernate3.HibernateTemplate;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * 
 * Interfaccia che contiene le operazioni che i DAO devono implementare. <br />
 * Questa interfaccia espone i principali metodi CRUD che un DAO dovrebbe avere di default
 * 
 * @param <E>
 *            L'entity associata alla tabella su cui operare
 * @param <F>
 *            Il tipo della proprietà dell'entity che rappresenta la chiave primaria della tabella associata
 * @author Riccardo Bocci
 * @author Fabrizio Corsetti
 */
public interface BaseDAO<E, F extends Serializable> {

    /**
     * Inserisce un record nella tabella associata all'entity con le informazioni presenti nell'entity.<br />
     * vedi {@link HibernateTemplate#merge(Object)}
     * 
     * @param entity
     *            l'entity da inserire
     */
    public void insert(E entity);

    /**
     * Aggiorna un record della tabella associata all'entity con le informazioni presenti nell'entity.<br />
     * vedi {@link HibernateTemplate#merge(Object)}
     * 
     * @param entity
     *            l'entity da aggiornare
     */
    public void update(E entity);

    /**
     * Questo metodo esegue il seguente iter:<br>
     * Se è presente la chiave primaria dell'entity verifica se esiste su db, se esiste allora esegue l'aggiornamento se
     * il parametro update=true, se non esiste inserisce utilizzando la chiave primaria presente. Se la chiave primaria
     * non è presente allora inserisce utilizzando una nuova chiave fornita dal PkIdGenerator
     * 
     * @param entity
     * @param id
     * @param isUpdate
     */
    public void insertOrUpdate(E entity, F id, boolean isUpdate);

    /**
     * Cancella un record dalla tabella associata all'entity tramite l'uso della proprietà dell'entity che rappresenta
     * la chiave primaria della tabella.<br />
     * vedi {@link HibernateTemplate#delete(Object)}
     * 
     * @param entity
     *            l'entity relativa al record da cancellare
     */
    public void delete(E entity);

    /**
     * vedi {@link HibernateTemplate#evict(Object)}
     * 
     * @param entity
     */
    public void evict(E entity);

    /**
     * Ricerca tutti i record di una determinata tabella filtrati per idcomune<br />
     * <b>Se si esegue l'override di tale metodo, inserire nell'interfaccia la segnatura del metodo ed il relativo
     * javadoc</b>
     * 
     * @param firstResult
     *            il primo record da recuperare, partendo da 0 ( può essere nullo )
     * @param maxResult
     *            il numero massimo di records da recuperare ( può essere nullo )
     * @return una <code>java.util.List</code> di entity
     */
    public List<E> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ricerca tutti i record di una determinata tabella e consente di specificare quali campi devono obbligatoriamente
     * apparire nella clausola where della query sql prodotta ed il campo e tipo di ordinamento per i record recuperati
     * 
     * 
     * @param firstResult
     *            il primo record da recuperare, partendo da 0 ( può essere nullo )
     * @param maxResult
     *            il numero massimo di records da recuperare ( può essere nullo )
     * @param whereClauseMandatoryFields
     *            valori ammessi: vedi {@link DAOEnum} ( obbligatorio )
     * @param orderProperty
     *            proprietà dell'entity rispetto alla quale ordinare ( può essere nullo )
     * @param orderType
     *            criterio di ordinamento: vedi {@link DAOOrderTypeEnum} ( può essere nullo )
     * @return una <code>java.util.List</code> di entity
     */
    public List<E> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType);

    /**
     * Recupera un record della tabella associata all'entity.<br />
     * vedi {@link HibernateTemplate#get(Class, Serializable)}
     * 
     * @param id
     *            la chiave per la quale recuperare l'oggetto di dominio
     * @return l'entity
     */
    public E findById(F id);

    /**
     * Metodo astratto che le classi che estendono <code>BaseServiceImpl</code> devono implementare per identificare
     * l'entity sulla quale eseguire i metodi
     * 
     * @return la classe dell'entity
     */
    public Class<E> getEntityClass();

    /**
     * Ricerca tutti i record di una determinata tabella e consente di specificare i filtri di selezione mediante
     * {@link FilterTable}
     * 
     * @param filterTable
     * @return
     */
    public List<E> findByFilterTable(FilterTable filterTable);

    /**
     * Ricerca tutti i record di una determinata tabella e consente di specificare i filtri di selezione mediante
     * {@link FilterTable}
     * 
     * @param filterTable
     *            La tabella di filtro
     * @param firstResult
     *            l'indice del primo record da visualizzare
     * @param maxResult
     *            quanti record visualizzare a partire da firstResult
     * @return
     */
    public List<E> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    /**
     * Torna se nella tabella ci sono records. Se il parametro filter table è nullo di default ricerca i record per
     * {@link DAOEnum#FIND_BY_IDCOMUNE}
     * 
     * @param filterTable
     *            Condizioni aggiuntive specificate da una FilterTable
     * @return
     */
    public boolean existsRecords(FilterTable filterTable);

    /**
     * Torna il numero di record presenti nella tabella filtrati per FilterTable .
     * 
     * @param filterTable
     *            Condizioni aggiuntive specificate da una FilterTable
     * @return
     */
    public int countRecord(FilterTable filterTable);

    /**
     * Restituisce la max(propertyName) dei record presenti nella tabella filtrati per FilterTable .
     * 
     * @param filterTable
     *            Condizioni aggiuntive specificate da una FilterTable
     * @return
     */
    public Object max(FilterTable filterTable, String propertyName);

    /**
     * Flush all pending saves, updates and deletes to the database. Only invoke this for selective eager flushing, for
     * example when JDBC code needs to see certain changes within the same transaction. Else, it is preferable to rely
     * on auto-flushing at transaction completion.
     */
    public void flush();

    /**
     * Remove all objects from the org.hibernate.Session cache, and cancel all pending saves, updates and deletes.
     */
    public void clear();

    /**
     * Recupera un nuovo identificativo della classe che si passa come argomento sfruttando il generator della classe
     * specificato nei tag <b>@GeneratedValue(generator = "pkGenerator")</b>.<br />
     * Nel caso delle entità con PkId ne recupera una nuova prendendo l'identificativo ( campo codice ) dalla sequence
     * table e idcomune da ORMHElper.getIdcomune(). <br />
     * La funzionalità riutilizza il codice del PkIdGenerator.<br />
     * Da usare solamente per le entity che hanno come identificativo PkId.class.
     * <h2><b><i>ATTENZIONE!! È stato testato per le classi con generator PkIdGenerator</i></b></h2>
     * 
     * @param entity
     * @return
     */
    public F newIdFromSequence(E entity);

    /**
     * Esegue una commit sulla transazione attiva
     */
    public void commit();

    public void commitFlush();

    /**
     * 
     * @param id
     * @param dynaClass
     * @return
     */
    public DynaBean findDynaBeanById(String idcomune, Integer id, DynaClass dynaClass, Class daoEntityClass);

    /**
     * 
     * @param ft
     * @param dynaClass
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<DynaBean> findDynaBeanByFilterTable(FilterTable ft, DynaClass dynaClass, Integer firstResult, Integer maxResult,
	    Class daoEntityClass);

    public <T> T getById(Class<T> cls, Integer id);

    public <T> T getById(Class<T> cls, PkId id);

    public <T, I> T getByIdCustom(Class<T> cls, I id);

    public <T> void saveEntity(T entity);

    public void refreshEntity(Object entity);

    int aggiornaSequenza(String sequenceName, String tableName, String idColumn, int totaleRecordDaInserire);
}