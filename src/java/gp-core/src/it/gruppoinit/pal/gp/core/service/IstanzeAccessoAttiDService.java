package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiDDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeAccessoAttiDService extends BaseService<IstanzeAccessoAttiD, PkId> {

    /**
     * @see IstanzeAccessoAttiDDAO#findAll(Integer, Integer)
     */
    public List<IstanzeAccessoAttiD> findAll(Integer firstResult, Integer maxResult);

    public List<IstanzeAccessoAttiD> findByIstanzeAccessoAttiT(Integer codice);

    /**
     * Prende due array di codici istanza accesso atti t, e per ogni codice in arrayCodiceIstanza verifica se è presente
     * in arrayCodiceIstanzaMostraDocValidi se è presente inserisce l'istanza accesso atti t in ISTANZE_ACCESSO_ATTI_D
     * con il flag mostra doc validi == true
     * 
     * @param arrayCodiceIstanza
     * @param arrayCodiceIstanzaMostraDocValidi
     * @param codiceIstanzaAccessoAtti
     */
    public void insert(String[] arrayCodiceIstanza, String[] arrayCodiceIstanzaMostraDocValidi, Integer codiceIstanzaAccessoAtti);

    /**
     * Prende come parametri un codiceIstanza (codice dell'istanza accesso atti d che si vuole cercare) e un
     * codiceIstanzaAccessoAttiT (codice dell'istanza di accesso atti a cui l'istanza accesso atti d appartiene)
     * 
     * @param codiceIstanza
     * @param codiceIstanzaAccessoAtti
     * @return Se presente un oggetto di tipo IstanzeAccessoAttiD con codice = codiceIstanza e una property
     *         istanzeAccessoAttiT con codice = codiceIstanzaAccessoAtti, lo restituisce.
     */
    public IstanzeAccessoAttiD findByIstanzaAndAttiT(Integer codiceIstanza, Integer codiceIstanzaAccessoAtti);

    public List<IstanzeAccessoAttiD> findByIstanza(Integer codice);
}
