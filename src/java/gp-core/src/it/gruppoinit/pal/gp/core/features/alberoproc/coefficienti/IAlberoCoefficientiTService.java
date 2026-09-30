package it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface IAlberoCoefficientiTService extends BaseService<AlberoCoefficientiT, PkId> {

    List<AlberoCoefficientiT> findAllByResponsabile(Integer codiceResponsabile);

    boolean responsabileHaPermessiSuConfigurazione(Integer codiceResponsabile, Integer codiceTestata);
}
