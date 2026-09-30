package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface IBorsellinoMovimentiService extends BaseService<BorsellinoMovimenti, PkId> {

    List<BorsellinoMovimenti> findByBorsellino(Integer idBorsellino);

    List<BorsellinoMovimenti> findByBorsellino(Integer idBorsellino, Date dalladata, Date alladata, Integer firstResult, Integer maxResult, List<TipoEnum> tipoenums);
}
