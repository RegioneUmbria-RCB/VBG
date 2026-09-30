package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

public interface IParametriProtocolloPerEnteHelper {

    IdentificativoDescrizioneBean getAmmMittente();

    void setAmmMittente(IdentificativoDescrizioneBean ammMittente);

    String getClassifica();

    void setClassifica(String classifica);

    String getTipodocumento();

    void setTipodocumento(String tipodocumento);

    CodiceDescrizioneBean getComune();

    void setComune(CodiceDescrizioneBean comune);

    List<CodiceDescrizioneBean> getListaClassifiche();

    void setListaClassifiche(List<CodiceDescrizioneBean> listaClassifiche);

    List<CodiceDescrizioneBean> getListaTipiDocumento();

    void setListaTipiDocumento(List<CodiceDescrizioneBean> listaTipiDocumento);

    List<IdentificativoDescrizioneBean> getListaAmministrazioni();

    void setListaAmministrazioni(List<IdentificativoDescrizioneBean> listaAmministrazioni);

    List<MetadatiBean> getMetadati();

    void setMetadati(List<MetadatiBean> metadati);
}