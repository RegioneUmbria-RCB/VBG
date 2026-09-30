package it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.IstanzeoneriPosdebBatchDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatch;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatchId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.oneri.DocumentiDaGenerare;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author
 */
public interface IstanzeoneriPosdebBatchService extends BaseService<IstanzeoneriPosdebBatch, IstanzeoneriPosdebBatchId> {

    /**
     * @see IstanzeoneriPosdebBatchDAO#findAll(Integer, Integer)
     */
    public List<IstanzeoneriPosdebBatch> findAll(Integer firstResult, Integer maxResult);

    void generaRichiestaDocumentiSeNonEsiste(Integer onereId, Integer posizioneDebitoriaId, DocumentiDaGenerare documentiDaGenerare);

    public boolean esisteConAltraPosizioneDebitoria(Integer onereId, Integer posizioneDebitoriaId);

    public void eliminaDaIdOnere(Integer onereId);

    public void updateProcessaDocumentiNonCompleti(int numeroRecordDaElaborare) throws FunzioneBusinessRemotaException;
}
