package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipologiaregistriDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.helper.TipologiaregistriConfigurazioneEnum;

import java.util.List;

public interface TipologiaregistriService extends BaseService<Tipologiaregistri, PkId> {

    public List<Tipologiaregistri> findByDescrizione(Tipologiaregistri entity);

    /**
     * @see TipologiaregistriDAO#findAll(Integer, Integer)
     */
    public List<Tipologiaregistri> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param token
     * 
     * @return Restituisce la lista delle classifiche
     * 
     *         public List<ListaTipiClassificaClassificaType> findListaClassifica(String token);
     * 
     *         /**
     * 
     * @param token
     * @return Restituisce la lista dei tipi documento
     * 
     *         public List<ListaTipiDocumentoDocumentoType> findListaTipiDocumenti(String token);
     * 
     *         /**
     * 
     * @param codiceTipoRegistro
     * @param progressivoUtente
     * @throws Exception
     */
    public void scriviProgressivoRegistro(int codiceTipoRegistro, String progressivoUtente);

    /**
     * 
     * @param codiceRegistro
     * @return
     */
    public boolean checkSeRegistroProtocolla(int codiceRegistro);

    /**
     * metodo per il recupero delle impostazioni del registro in merito alla numerazione
     * 
     * @param codiceRegistro
     * @return enumeration
     */
    public TipologiaregistriConfigurazioneEnum getImpostazioniRegistro(int codiceRegistro);

    /**
     * Calcola un progressivo a partire da due numeri
     * 
     * @param progressivoPresente
     * @param progressivoUtente
     * @return
     */
    public String calcolaNuovoProgressivo(String progressivoPresente, String progressivoUtente);

    void resetObjectCached();

    public List<ChiaveValoreBean<String, String>> findAllDocer();
}
