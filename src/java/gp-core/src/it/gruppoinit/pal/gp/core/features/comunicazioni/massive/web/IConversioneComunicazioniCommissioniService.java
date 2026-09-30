package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public interface IConversioneComunicazioniCommissioniService {

    public ConfigurazioneComunicazioniCommissioni popolaConfigurazioneComunicazioniCommissioni(ComunicazioniCommissioniCommand command);

    public void validaCommand(ComunicazioniCommissioniCommand command) throws BusinessValidationException;

    public List<IParametriProtocolloPerEnteHelper> popolaParametriPerProtocolloCommand(ComunicazioniCommissioniCommand cmd);

    public ComunicazioneCommissioneDetail popolaCommandDettaglioByIdTestata(Integer codiceTestata);
}
