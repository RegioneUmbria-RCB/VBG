package it.gruppoinit.pal.gp.core.features.decodifiche;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Decodifiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface DecodificheService extends BaseService<Decodifiche, PkId> {

    ListaDecodifiche findByTabella(String tabella);

    List<String> findDistinctTabelle();

    ListaDecodifiche findByTabellaAndRaggruppamento(String tabella, String raggruppamento);
}
