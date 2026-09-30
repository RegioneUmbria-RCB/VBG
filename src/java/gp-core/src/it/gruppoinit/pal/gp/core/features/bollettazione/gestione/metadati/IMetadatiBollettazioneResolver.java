package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;

public interface IMetadatiBollettazioneResolver {

    public List<MetadatiBean> get();
}
