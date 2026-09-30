package it.gruppoinit.service;

import it.gov.impresainungiorno.schema.base.Comune;
import it.gov.impresainungiorno.schema.base.Indirizzo;
import it.gov.impresainungiorno.schema.base.IndirizzoConRecapiti;
import it.gov.impresainungiorno.schema.base.Stato;
import it.gov.impresainungiorno.schema.suap.pratica.Anagrafica;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaPersona;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaRappresentante;
import it.gov.impresainungiorno.schema.suap.pratica.Carica;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiDichiarante;
import it.gruppoinit.domain.nla.Allegato;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.CittadinanzaType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.RegistroREAType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.SchedaType;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

public interface MappingElementICToBOService {

    public void populateRiferimentoCatastaleType(String tipo, String sezione, String foglio, String mappale, String sub,
	    RiferimentoCatastaleType riferimentoCatastaleType);

    public void populateLocalizzazioneNelComuneType(Indirizzo indirizzo, LocalizzazioneNelComuneType indirizzoPratica);

    public void populatePersonaGiuridicaType(AnagraficaImpresa anagraficaImpresa, PersonaGiuridicaType personaGiuridicaType);

    public void populatePersonaFisicaTypeByEstrimiDichiarante(EstremiDichiarante estremiDichiarante, PersonaFisicaType personaFisicaType);

    public void populatePersonaFisicaTypeDaAnagrafica(Anagrafica anagrafica, PersonaFisicaType personaFisicaType);

    public void populatePersonaFisicaTypeAnagraficaRappresentante(AnagraficaRappresentante anagraficaRappresentante,
	    PersonaFisicaType personaFisicaType);

    public void populateAnagrafeTypeFisica(PersonaFisicaType personaFisicaType, AnagrafeType anagrafeType);

    public void populateAnagrafeTypeGiuridica(PersonaGiuridicaType personaGiuridicaType, AnagrafeType anagrafeType);

    public void populateAnagrafeType(PersonaFisicaType personaFisicaType, PersonaGiuridicaType personaGiuridicaType, AnagrafeType anagrafeType);

    public void populateCittadinanzaType(Stato stato, CittadinanzaType cittadinanzaType);

    public void populateRegistroREAType(String siglaProv, XMLGregorianCalendar dataIscrizione, String numero, RegistroREAType reaType);

    public void populateLocalizzazioneType(IndirizzoConRecapiti indirizzoConRecapiti, LocalizzazioneType localizzazioneType);

    public void populateComuneType(String codiceIstat, String codiceCatastale, String nome, ComuneType comuneType);

    public void populateComuneType(Comune comune, ComuneType comuneType);

    public void populateRichiedenteType(Anagrafica anagrafica, Carica carica, RichiedenteType richiedenteType);

    public void populateDocumentiType(Allegato allegato, DocumentiType documentiType);

    public void popolateRichiedenteType(AnagraficaPersona anagraficaPersona, RichiedenteType richiedenteType);

    public void populateSchedeType(List<Allegato> listAllegati, List<SchedaType> schedaTypes);

    public Map<String, String> normalizzaFileXml(InputStream xml);
    //public Map<String, String> normalizzaFileXml(File xml);
}
