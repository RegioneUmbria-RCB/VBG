package it.gruppoinit.pal.gp.core.features.segnaposto.configurazionimetadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.ConfigurazioniMetadati;

public interface IConfigurazioniMetadatiService {

    public void insertOrUpdate(ConfigurazioniMetadati entity);

    public void delete(ConfigurazioniMetadati entity);

    public List<ConfigurazioniMetadati> findByIdComuniAssociatiSoftware(Integer idComuniAssociatiSoftware);

    public List<ConfigurazioniMetadatiBean> getConfigurazioniMetadatiBean(Integer idComuniAssociatiSoftware);

    public ConfigurazioniMetadati findByChiaveAndIdcomune(String chiave);

    public List<String> findByCategoireDistinct();

    public int updateCategoria(String categoriaOrginale, String nuovaCategoria);
}
