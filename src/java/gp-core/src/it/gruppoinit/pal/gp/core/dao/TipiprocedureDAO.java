package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;

import java.util.List;

public interface TipiprocedureDAO extends BaseDAO<Tipiprocedure, PkId> {

    /**
     * Restituisce le Tipiprocedure (filtrando per idcomune e software) ordinandole per il campo procedura
     */
    public List<Tipiprocedure> findAll(Integer firstResult, Integer maxResult);

    /**
     * Metodo che restituisce i tipi procedure filtrate per Idcomune, per il software corrente e per il softrware TT
     * 
     * @return
     */
    public List<Tipiprocedure> findAllBySoftwareAndTT();

    /**
     * Torna una lista di tipiprocedure filtrate per procedura ordinate per procedura dalla A alla Z
     * 
     * @param descrizione
     * @return
     */
    public List<Tipiprocedure> findByDescrizione(String descrizione);

    /**
     * Torna una lista di tipiprocedure filtrate per SoftwareCorrente o Software=TT, per campo procedura (ilike) se il
     * parametro text è una stringa o per campo codice (eq) se è un numero. La lista è ordinata per Software.ordine asc,
     * procedura asc
     * 
     * @param text
     *            codice o descrizione
     * @param includiDisabilitate
     *            se true verranno recuperati anche i record disabilitati
     * @param soloConMovimentoAvvio
     *            se true verranno considerate solamente le procedure con movimenti di avvio
     * @return
     */
    public List<Tipiprocedure> findByCodiceODescrizione(String text, boolean includiDisabilitate, boolean soloConMovimentoAvvio);
}
