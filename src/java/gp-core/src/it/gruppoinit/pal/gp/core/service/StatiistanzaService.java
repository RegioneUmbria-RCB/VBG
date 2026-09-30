package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.StatiistanzaDAO;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

public interface StatiistanzaService extends BaseService<Statiistanza, StatiistanzaId> {

    /**
     * Metodo che per la ricerca delle configurazioni per software
     * 
     * @param software
     * @return
     */
    public List<Statiistanza> findBySoftware(String software);

    /**
     * @see StatiistanzaDAO#findByStatocomportamentoChiuse()
     * 
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoChiuse();

    /**
     * @see StatiistanzaDAO#findByStatocomportamentoAperte()
     * 
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoAperte();

    /**
     * @see StatiistanzaDAO#findByFilterTable(FilterTable)
     * @param filterTable
     * @return
     */
    public List<Statiistanza> findByFilterTable(FilterTable filterTable);

    /**
     * @see StatiistanzaDAO#findByStatocomportamentoChiuse(boolean)
     * 
     */
    public List<Statiistanza> findByStatocomportamentoChiuse(boolean tuttiSoftware);

    /**
     * @see StatiistanzaDAO#findByStatocomportamentoAperte(boolean)
     * 
     * @return
     */
    public List<Statiistanza> findByStatocomportamentoAperte(boolean tuttiSoftware);

    /**
     * Cerca tutti i record per il software corrente ordinati per la colonna ordine, stato (descrizione) asc
     */
    @Override
    public List<Statiistanza> findAll(Integer firstResult, Integer maxResult);

    /**
     * controlla se usato in tipimovimento
     * 
     * @param statiistanza
     * @return
     */
    public boolean isUsedByTipimovimento(Statiistanza statiistanza);

    /**
     * controlla se usato in istanze
     * 
     * @param statiistanza
     * @return
     */
    public boolean isUsedByIstanze(Statiistanza statiistanza);

    public List<Statiistanza> findStati(String software);
}
