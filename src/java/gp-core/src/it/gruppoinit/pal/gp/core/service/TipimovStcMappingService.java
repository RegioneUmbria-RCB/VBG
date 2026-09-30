package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.TipiMovimentoStcMappingProtocollo;

public interface TipimovStcMappingService extends BaseService<TipimovStcMapping, PkId> {

    public enum TipoNotificaAutomatica {
	NESSUNA_NOTIFICA_AUTOMATICA,
	NOTIFICA_AUTOMATICA,
	NOTIFICA_AUTOMATICA_INSERIMENTO
    };

    /**
     * Trova una lista di configurazioni altri dati per il tipomovimento specificato, ordinati per descrizione
     * amministrazione desc
     * 
     * @param tipimovimentoId
     *            la chiave che identifica il tipo movimento
     * @return List&lt;TipimovStcMapping&gt;
     */
    public List<TipimovStcMapping> findByTipimovimento(TipimovimentoId tipimovimentoId);

    /**
     * Recupera le informazioni di un mapping per un movimento ed una amministrazione
     * 
     * @param codiceAmministrazioneStc
     * @param codiceAmministrazioneStc
     * @return null se non trovato
     */
    public TipimovStcMapping findByTipimovimentoAndAmministrazione(String tipoMovimento, Integer codiceAmministrazioneStc);

    /**
     * Torna la lista delle TipimovStcMapping di un'Amministrazione (TIPIMOV_STC_MAPPING.CODICEAMMINISTRAZIONE)
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<TipimovStcMapping> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    /**
     * Torna la lista delle TipimovStcMapping di un'Amministrazione (TIPIMOV_STC_MAPPING.PROTOCOLLO_MITTENTE)
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<TipimovStcMapping> findByAmministrazioneMittente(Integer codice, Integer firstResult, Integer maxResult);

    public TipoNotificaAutomatica decodeTipoNotifica(TipimovStcMapping tipimovStcMapping);

    public TipimovStcMapping findNotificheAutomaticheByTipimovimentoAndAmministrazione(String tipoMovimento, Integer codiceAmministrazione);

    public TipiMovimentoStcMappingProtocollo findDatiProtocolloByTipoMovimento(String tipomovimento);
}
