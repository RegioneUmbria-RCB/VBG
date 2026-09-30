package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Sdecomuniassociati;
import it.gruppoinit.pal.gp.core.domain.SdecomuniassociatiId;

public interface SdecomuniassociatiService extends BaseService<Sdecomuniassociati, SdecomuniassociatiId> {

    List<Sdecomuniassociati> findByIdente(String idente);

    List<Sdecomuniassociati> findByCodiceCatastale(String codicecatastale);
}
