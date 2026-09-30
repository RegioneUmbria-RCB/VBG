package it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface TipologiaregistriService extends BaseService<Tipologiaregistri, PkId> {

    public enum TIPO_RICERCA {
	TUTTI,
	SOLO_MANIFESTAZIONI,
	ESCLUDI_MANIFESTAZIONI
    };

    public List<Tipologiaregistri> findByDescrizione(Tipologiaregistri entity, TIPO_RICERCA tipoRicerca);

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

    public boolean checkSeNumeratoreEsterno(int codiceRegistro);

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

    public List<Tipologiaregistri> findByDescrizioneAndComune(Tipologiaregistri tipologiaregistri, String codicecomune, TIPO_RICERCA tipoRicerca);
}
