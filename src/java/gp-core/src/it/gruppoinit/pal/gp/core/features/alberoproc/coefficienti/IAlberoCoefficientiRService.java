package it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiR;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface IAlberoCoefficientiRService extends BaseService<AlberoCoefficientiR, PkId> {

    List<AlberoCoefficientiR> findAllRigheByTestata(Integer codice);
}
