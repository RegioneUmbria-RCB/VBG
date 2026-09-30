package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;

public interface IParametriProtocolloHelperComunicazioniService {

    List<IParametriProtocolloPerEnteHelper> popolaParametri(List<ISoftwareComuneData> softwareAndComuneForBollettazione);
}
