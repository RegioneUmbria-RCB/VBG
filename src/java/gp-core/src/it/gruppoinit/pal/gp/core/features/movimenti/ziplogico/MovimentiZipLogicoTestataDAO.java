package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestataId;

public interface MovimentiZipLogicoTestataDAO extends BaseDAO<MovimentiZipLogicoTestata, MovimentiZipLogicoTestataId> {

    /**
     * La funzionalità elimina tutti i record di MOVIMENTI_ZIP_LOGICO_TESTATA per il codicemovimento passato dopo aver
     * invocato la cancellazione dei record di MOVIMENTI_ZIP_LOGICO
     * 
     * @param codiceMovimento
     */
    public void eliminaZipLogicoByCodiceMovimento(Integer codiceMovimento);

    Boolean isZipLogicoExistInMovimento(Integer codicemovimento);

    Boolean isZipLogicoDocumentoAllegato(Integer codicemovimento);
}
