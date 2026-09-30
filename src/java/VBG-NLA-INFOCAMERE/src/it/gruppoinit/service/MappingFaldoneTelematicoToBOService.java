package it.gruppoinit.service;

import it.gruppoinit.domain.nla.Allegato;
import it.gruppoinit.faldonetelematico.model.ValoreCampo;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.RichiedenteType;

public interface MappingFaldoneTelematicoToBOService {

    public void populatePersonaGiuridicaType(ValoreCampo personaGiuridicaFaldoneTelematico, PersonaGiuridicaType personaGiuridicaType);

    public void populatePersonaFisicaType(ValoreCampo personaFisicaFaldoneTelematico, PersonaFisicaType personaFisicaType);

    public void populateDocumentiType(Allegato allegato, DocumentiType documentiType);

    public void populateComuneType(ValoreCampo modulo, ComuneType comuneType);

    public void populateRichiedenteType(ValoreCampo titolare, RichiedenteType richiedenteType);
}
