package it.gruppoinit.pal.gp.core.features.datidinamici.metadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Dyn2Metadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface Dyn2MetadatiService extends BaseService<Dyn2Metadati, PkId> {

    public List<Dyn2MetadatiRestBean> findByDyn2MetadatiContesto(String dyn2MetadatiContesto);

    public List<Dyn2Metadati> findByIdDyn2MetadatoContesto(Integer dy2MetadatoContesto);
}
