package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RuoliProtocollo;
import it.gruppoinit.pal.gp.core.service.helper.RuoliProtocolloHelper;

public interface RuoliProtocolloService extends BaseService<RuoliProtocollo, PkId> {

    List<RuoliProtocollo> findByIdRuolo(Integer idRuolo);

    List<RuoliProtocollo> findByIdRuoloAndComuneAndSoftware(Integer idRuolo, String codiceComune, String software);

    List<RuoliProtocolloHelper> findHelperByRuolo(Integer idRuolo);

    void insertRuoliProtocollo(Integer idRuolo, List<RuoliProtocollo> rps);
}
