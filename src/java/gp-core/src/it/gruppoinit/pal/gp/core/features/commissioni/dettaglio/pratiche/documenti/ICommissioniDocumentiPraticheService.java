package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti;

import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.RiferimentiDocumentiSelezionati;

public interface ICommissioniDocumentiPraticheService {

    CommissioniDettaglioDocumentiPratica getByIdCommissioneRIdPratica(int idCommissioneR, int codiceIstanza);

    // Aggiorna i documenti selezionati per una specifica commissione e istanza
    void impostaDocumentiSelezionati(int idCommissioneR, int codiceIstanza, RiferimentiDocumentiSelezionati documentiSelezionati);

    // Esegue il conteggio di tutti i documenti aggiunti alla discussione di quella riga di commissione
    int countDocumentiDellaCommissione(int idCommissioneR);

    boolean existsByMovimentiAllegati(int idMovimentiAllegati);

    boolean existsByDocumentiIstanza(int idDocumentiIstanza);

    boolean existsByIstanzeallegati(int idIstanzeAllegati);
}
