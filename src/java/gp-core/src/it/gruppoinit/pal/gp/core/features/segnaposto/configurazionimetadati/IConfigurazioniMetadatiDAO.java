package it.gruppoinit.pal.gp.core.features.segnaposto.configurazionimetadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioniMetadati;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioniMetadatiId;

public interface IConfigurazioniMetadatiDAO extends BaseDAO<ConfigurazioniMetadati, ConfigurazioniMetadatiId> {

    public <T> void save(T entity);

    public List<ConfigurazioniMetadati> findByIdComuniAssociatiSoftware(Integer idComuniAssociatiSoftware);

    public ConfigurazioniMetadati findByChiaveAndIdcomune(String chiave, boolean isCercaInTT);

    public List<String> findByCategoireDistinct();

    public int updateCategoria(String categoriaOrginale, String nuovaCategoria);
}
