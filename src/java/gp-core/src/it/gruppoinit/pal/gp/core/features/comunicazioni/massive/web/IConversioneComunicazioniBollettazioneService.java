package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public interface IConversioneComunicazioniBollettazioneService {

    public ComunicazioneBollettazioneDetail popolaCommandDettaglioByIdTestata(Integer idTestataMassive);

    public void popolaCommand(BollGestTestata gestTestata, ConfigurazioneComunicazioniBollettazione confComBoll);

    public ConfigurazioneComunicazioniBollettazione popolaConfigurazioneComunicazioniBollettazione(
	    ComunicazioniBollettazioneCommand bollettazioneCommand);

    public void validaCommand(ComunicazioniBollettazioneCommand bollettazioneCommand) throws BusinessValidationException;

    public List<IParametriProtocolloPerEnteHelper> popolaParametriPerProtocolloCommand(
	    ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand);
}
