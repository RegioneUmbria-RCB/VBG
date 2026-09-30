package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti;

import java.util.Collection;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.RiferimentiDocumentiSelezionati;

public interface ICommissioniDocumentiPraticheDAO {

    /*
     * Restituisce un oggetto Istanze a partire dal relativo codiceIstanza
     */
    Istanze getIstanzaById(int codiceIstanza);

    /*
     * Restituisce un oggetto CommissioniedilizieT a partire dal relativo id
     */
    CommissioniedilizieT getCommissioneByIdCommissioneR(int idCommissioneR);

    Collection<CommissioniDettaglioDocumentiPratica.RiferimentiDocumento> getDocumentiIstanzaByCodiceIstanza(int codiceIstanza);

    Collection<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> getIstanzeAllegatiByCodiceIstanza(int codiceIstanza);

    Collection<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> getMovimentiAllegatiByCodiceIstanza(int codiceIstanza);

    RiferimentiDocumentiSelezionati getDocumentiSelezionatiByIdCommissioneRCodiceIstanza(int idCommissioneR, int codiceIstanza);

    void impostaDocumentiSelezionati(int idCommissioneR, int codiceIstanza, RiferimentiDocumentiSelezionati documentiSelezionati);

    int countDocumentiDellaCommissione(int idCommissioneR);

    void eliminaDocumenti(int idCommissioneR);

    boolean existsByMovimentiAllegati(int idMovimentiAllegati);

    boolean existsByDocumentiIstanza(int idDocumentiIstanza);

    boolean existsByIstanzeallegati(int idIstanzeAllegati);
}
