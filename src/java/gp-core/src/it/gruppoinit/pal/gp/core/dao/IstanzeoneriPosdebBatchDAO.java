package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatch;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatchId;

/**
 * 
 * @author
 */
public interface IstanzeoneriPosdebBatchDAO extends BaseDAO<IstanzeoneriPosdebBatch, IstanzeoneriPosdebBatchId> {

    public List<IstanzeoneriPosdebBatch> findAll(Integer firstResult, Integer maxResult);

    public int contaByOnereWherePosizioneDebitoriaDiversaDa(Integer onereId, Integer idDettPosizioneDebitoria);

    public void deleteByIdOnere(Integer onereId);

    public int contaByOnereWherePosizioneDebitoriaUgualeA(Integer onereId, Integer idDettPosizioneDebitoria);

    public List<Integer> findCodiciIstanzaPosizioniDaPreparare(int counter);
    
    public List<IstanzeoneriPosdebBatch> trovaIdOnerePerIstanzaECausaleDaElaborare(Integer codiceIstanza, Integer codiceCausaleOnere, int daElaborare);
}
