package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.utils;

import java.util.Collection;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica;

public interface IFunzioneRaggruppamentoDocumenti<T> {

    Collection<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> getGroup(List<T> values);
}
