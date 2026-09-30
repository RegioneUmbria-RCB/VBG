package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellitId;

public interface Movimentidyn2modellitService extends BaseService<Movimentidyn2modellit, Movimentidyn2modellitId> {

    List<Movimentidyn2modellit> findByCodiceMovimento(Integer codicemovimento);
}
