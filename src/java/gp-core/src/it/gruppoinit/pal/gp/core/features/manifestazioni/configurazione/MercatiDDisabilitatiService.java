package it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MercatiDDisabilitati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface MercatiDDisabilitatiService extends BaseService<MercatiDDisabilitati, PkId> {

    List<Integer> findByIdGiornata(Integer idGiornata);

    void impostaStatoPosteggio(ImpostaStatoPosteggioFlyweight request);
}
