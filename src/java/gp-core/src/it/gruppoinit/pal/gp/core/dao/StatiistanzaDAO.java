package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;

public interface StatiistanzaDAO extends BaseDAO<Statiistanza, StatiistanzaId> {

    /**
     * Metodo che per la ricerca delle configurazioni per software
     * 
     * @param software
     * @return
     */
    public List<Statiistanza> findBySoftware(String software);

    /**
     * torna tutti i record di statiistanza del modulo software corrente che indicano le istanze come chiuse ossia i
     * record Chiuse positivamente (comportamento = 1) e chiuse negativamente (Comportamento = -1)
     * 
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoChiuse();

    /**
     * torna tutti i record di statiistanza del modulo software corrente che indicano le istanze come aperte ossia i
     * record con comportamento = 0
     * 
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoAperte();

    /**
     * torna tutti i record di statiistanza che indicano le istanze come chiuse ossia i record Chiuse positivamente
     * (comportamento = 1) e chiuse negativamente (Comportamento = -1)
     * 
     * @param tuttiSoftware
     *            se true allora torna gli statiistanza di un idcomune indipendentemente dal software
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoChiuse(boolean tuttiSoftware);

    /**
     * torna tutti i record di statiistanza dche indicano le istanze come aperte ossia i record con comportamento = 0
     * 
     * @param tuttiSoftware
     *            se true allora torna gli statiistanza di un idcomune indipendentemente dal software
     * 
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoAperte(boolean tuttiSoftware);

    /**
     * torna tutti i record di statiistanza del modulo software corrente che indicano le istanze come chiuse ossia i
     * chiuse negativamente (Comportamento = -1)
     * 
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoChiuseNegativamente();

    /**
     * torna tutti i record di statiistanza del modulo software corrente che indicano le istanze come chiuse ossia i
     * record Chiuse positivamente (comportamento = 1)
     * 
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoChiusePositivamente();

    /**
     * torna la lista
     * 
     * @return
     */
    public List<Statiistanza> findStatiInWarning();

    /**
     * Ritorna la lista degli stati di una istanza
     * 
     * @param software
     * @return
     */
    public List<Statiistanza> findStati(String software);
}
