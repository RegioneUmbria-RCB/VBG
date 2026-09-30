package it.gruppoinit.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import it.gruppoinit.service.GestioneAllegatoPraticaService;
import it.gruppoinit.service.MappingElementICToBOService;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.CampoDinamicoType;
import it.init.sigepro.rte.types.CampoSchedaType;
import it.init.sigepro.rte.types.CittadinanzaType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ElementoValoreCampoDinamicoType;
import it.init.sigepro.rte.types.FrazioneType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.RegistroREAType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.RuoloType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.ValoreCampoDinamicoType;

@Service
public class MappingElementICToBOServiceImpl implements MappingElementICToBOService {

    public static final Logger log = LoggerFactory.getLogger(MappingElementICToBOServiceImpl.class);
    private GestioneAllegatoPraticaService gestioneAllegatoPraticaService;

    @Autowired
    public void setGestioneAllegatoPraticaService(GestioneAllegatoPraticaService gestioneAllegatoPraticaService) {

	this.gestioneAllegatoPraticaService = gestioneAllegatoPraticaService;
    }

    @Override
    public void populateRiferimentoCatastaleType(String tipo, String sezione, String foglio, String mappale, String sub,
	    RiferimentoCatastaleType riferimentoCatastaleType) {

	if (riferimentoCatastaleType == null) {
	    riferimentoCatastaleType = new RiferimentoCatastaleType();
	}
	log.debug("populateRiferimentoCatastaleType# Tipo = {}, Sezione = {}, Foglio = {}, Particella = {}, Sub = {}", tipo, sezione, foglio, mappale,
		sub);
	riferimentoCatastaleType.setTipoCatasto("F");
	if ("terreni".equals(tipo)) {
	    riferimentoCatastaleType.setTipoCatasto("T");
	}
	riferimentoCatastaleType.setSezione(sezione);
	riferimentoCatastaleType.setFoglio(foglio);
	riferimentoCatastaleType.setParticella(mappale);
	riferimentoCatastaleType.setSub(sub);
    }

    @Override
    public void populateLocalizzazioneNelComuneType(Indirizzo indirizzo, LocalizzazioneNelComuneType indirizzoPratica) {

	if (indirizzoPratica == null) {
	    indirizzoPratica = new LocalizzazioneNelComuneType();
	}
	String denominazione = StringUtils.defaultIfEmpty(indirizzo.getToponimo(), "") + " " +
			       StringUtils.defaultIfEmpty(indirizzo.getDenominazioneStradale(), "");
	String civico = StringUtils.defaultIfEmpty(indirizzo.getNumeroCivico(), "");
	log.debug("populateRiferimentoCatastaleType# Denominazine = {}, Civico = {}", denominazione, civico);
	if (StringUtils.isNotBlank(civico)) {
	    String[] campicivico = StringUtils.split(civico, "/");
	    String civico_ = "";
	    try {
		Integer _civico = Integer.parseInt(campicivico[0]);
		civico_ = String.valueOf(_civico);
	    } catch (NumberFormatException nfe) {
		civico_ = campicivico[0];
		log.error("populateLocalizzazioneNelComuneType# Errore durante la conversione del civico in intero");
	    }
	    if (campicivico.length == 1) {
		indirizzoPratica.setCivico(civico_);
	    } else if (campicivico.length == 2) {
		indirizzoPratica.setCivico(civico_);
		indirizzoPratica.setColore(campicivico[1]);
	    } else if (campicivico.length == 3) {
		indirizzoPratica.setCivico(civico_);
		indirizzoPratica.setEsponente(campicivico[1]);
		indirizzoPratica.setColore(campicivico[2]);
	    }
	}
	indirizzoPratica.setCap(StringUtils.defaultIfEmpty(indirizzo.getCap(), ""));
	indirizzoPratica.setDenominazione(denominazione);
	FrazioneType f = new FrazioneType();
	f.setDescrizione(StringUtils.defaultIfEmpty(indirizzo.getFrazione(), ""));
	indirizzoPratica.setFrazione(f);
    }

    @Override
    public void populatePersonaGiuridicaType(AnagraficaImpresa anagraficaImpresa, PersonaGiuridicaType personaGiuridicaType) {

	if (personaGiuridicaType == null) {
	    personaGiuridicaType = new PersonaGiuridicaType();
	}
	log.debug("populatePersonaGiuridicaType# Popolo riferimenti azienda (RagioneSociale, CodiceFiscale,PartitaIva)");
	personaGiuridicaType.setRagioneSociale(StringUtils.defaultIfEmpty(anagraficaImpresa.getRagioneSociale(), ""));
	personaGiuridicaType.setCodiceFiscale(StringUtils.defaultIfEmpty(anagraficaImpresa.getCodiceFiscale(), ""));
	personaGiuridicaType.setPartitaIva(StringUtils.defaultIfEmpty(anagraficaImpresa.getPartitaIva(), ""));
	if (anagraficaImpresa.getIndirizzo() != null) {
	    log.debug("populatePersonaGiuridicaType# Popolo recapti email ,fax, pec");
	    if (anagraficaImpresa.getIndirizzo().getEMail() != null && !anagraficaImpresa.getIndirizzo().getEMail().isEmpty()) {
		log.debug("populatePersonaGiuridicaType# Email..");
		personaGiuridicaType.setEmail(StringUtils.defaultIfEmpty(anagraficaImpresa.getIndirizzo().getEMail().get(0).getValue(), ""));
	    }
	    log.debug("populatePersonaGiuridicaType# Pec (non gestito)"); // verificare se l'oggetto email può avere tipo="Pec"
	    personaGiuridicaType.setPec("");
	    log.debug("populatePersonaGiuridicaType# Telefono cellulare (non gestito)"); // verificare se l'oggetto telefono può avere tipo="cellulare"
	    personaGiuridicaType.setTelefonoCellulare("");
	    if (anagraficaImpresa.getIndirizzo() != null && !anagraficaImpresa.getIndirizzo().getTelefono().isEmpty()) {
		log.debug("populatePersonaGiuridicaType# Email..");
		personaGiuridicaType.setTelefono(StringUtils.defaultIfEmpty(anagraficaImpresa.getIndirizzo().getTelefono().get(0).getValue(), ""));
	    }
	    log.debug("populatePersonaGiuridicaType# Fax (non gestito)"); // verificare se l'oggetto telefono può avere tipo="fax"
	    personaGiuridicaType.setFax("");
	}
	log.debug("populatePersonaGiuridicaType# Popolo DataInizioAttivita (ATTENZIONE NON GESTITA)");
	//personaGiuridicaType.setDataInizioAttivita(value);
	log.debug("populatePersonaGiuridicaType# Popolo DatiCassaEdile, DatiInail, DatiInps, DatiIscrizioneAlbo (ATTENZIONE NON GESTITI)");
	//personaGiuridicaType.setDatiCassaEdile(value);
	//personaGiuridicaType.setDatiInail(value);
	//personaGiuridicaType.setDatiInps(value);
	//personaGiuridicaType.setDatiIscrizioneAlbo(value);
	log.debug("populatePersonaGiuridicaType# IscrizioneCCIAA (ATTENZIONE NON GESTITA)");
	//personaGiuridicaType.setIscrizioneCCIAA(null);
	log.debug("populatePersonaGiuridicaType# popolo IscrizioneREA ");
	if (anagraficaImpresa.getCodiceREA() != null && anagraficaImpresa.getCodiceREA().getDataIscrizione() != null) {
	    RegistroREAType reaType = new RegistroREAType();
	    populateRegistroREAType(anagraficaImpresa.getCodiceREA().getProvincia(), anagraficaImpresa.getCodiceREA().getDataIscrizione(),
		    anagraficaImpresa.getCodiceREA().getValue(), new RegistroREAType());
	    personaGiuridicaType.setIscrizioneREA(reaType);
	}
	//
	if (anagraficaImpresa.getIndirizzo() != null) {
	    LocalizzazioneType localizzazioneType = new LocalizzazioneType();
	    populateLocalizzazioneType(anagraficaImpresa.getIndirizzo(), localizzazioneType);
	    log.debug("populatePersonaGiuridicaType# popolo indirizzo corrispondenza");
	    personaGiuridicaType.setIndirizzoCorrispondenza(localizzazioneType);
	    log.debug("populatePersonaGiuridicaType# popolo indirizzo sede legale");
	    personaGiuridicaType.setSedeLegale(localizzazioneType);
	}
	//
	if (anagraficaImpresa.getFormaGiuridica() != null) {
	    log.debug("populatePersonaGiuridicaType# popolo natura giuridica = {},", anagraficaImpresa.getFormaGiuridica().getValue());
	    personaGiuridicaType.setNaturaGiuridica(StringUtils.defaultIfEmpty(anagraficaImpresa.getFormaGiuridica().getValue(), ""));
	}
	//	log.debug("populatePersonaGiuridicaType# Popolo legale rappresentante");
	//	PersonaFisicaType personaFisicaType = new PersonaFisicaType();
	//	populatePersonaFisicaTypeAnagraficaRappresentante(anagraficaImpresa.getLegaleRappresentante(), personaFisicaType);
	//	personaGiuridicaType.setLegaleRappresentante(personaFisicaType);
    }

    @Override
    public void popolateRichiedenteType(AnagraficaPersona anagraficaPersona, RichiedenteType richiedenteType) {

	if (richiedenteType == null) {
	    richiedenteType = new RichiedenteType();
	}
	PersonaFisicaType personaFisicaType = new PersonaFisicaType();
	populatePersonaFisicaTypeDaAnagraficaPersona(anagraficaPersona, personaFisicaType);
	richiedenteType.setAnagrafica(personaFisicaType);
	//richiedenteType.setRuolo(value);
    }

    @Override
    public void populatePersonaFisicaTypeByEstrimiDichiarante(EstremiDichiarante estremiDichiarante, PersonaFisicaType personaFisicaType) {

	if (personaFisicaType == null) {
	    personaFisicaType = new PersonaFisicaType();
	}
	Anagrafica anagrafica = (Anagrafica) estremiDichiarante;
	populatePersonaFisicaTypeDaAnagrafica(anagrafica, personaFisicaType);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo titolo (ATTENZIONE NON GESTITO)");
	personaFisicaType.setTitolo("");
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo sesso (ATTENZIONE NON GESTITO)");
	personaFisicaType.setSesso("");
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo comune nascita (ATTENZIONE NON GESTITO)");
	personaFisicaType.setComuneNascita(null);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo data nascita (ATTENZIONE NON GESTITO)");
	personaFisicaType.setDataNascita(null);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo residenza (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setResidenza(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo corrispondenza (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setCorrispondenza(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo data iscrizione albo  (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setDatiIscrizioneAlbo(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo email (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setEmail(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo pec ");
	personaFisicaType.setPec(StringUtils.defaultIfEmpty(estremiDichiarante.getPec(), ""));
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo telefono");
	personaFisicaType.setTelefono(StringUtils.defaultIfEmpty(estremiDichiarante.getTelefono(), ""));
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo telefono cellulare (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setTelefonoCellulare(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo procura (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setProcura(value);
    }

    private void populatePersonaFisicaTypeDaAnagraficaPersona(AnagraficaPersona anagraficaPersona, PersonaFisicaType personaFisicaType) {

	if (personaFisicaType == null) {
	    personaFisicaType = new PersonaFisicaType();
	}
	Anagrafica anagrafica = (Anagrafica) anagraficaPersona;
	populatePersonaFisicaTypeDaAnagrafica(anagrafica, personaFisicaType);
	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo titolo (ATTENZIONE NON GESTITO)");
	personaFisicaType.setTitolo("");
	if (anagraficaPersona.getSesso() != null) {
	    log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo sesso");
	    personaFisicaType.setSesso(StringUtils.defaultIfEmpty(anagraficaPersona.getSesso().value(), ""));
	}
	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo comune nascita (ATTENZIONE NON GESTITO)");
	personaFisicaType.setComuneNascita(null);
	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo data nascita (ATTENZIONE NON GESTITO)");
	personaFisicaType.setDataNascita(null);
	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo residenza (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setResidenza(value);
	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo corrispondenza (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setCorrispondenza(value);
	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo data iscrizione albo  (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setDatiIscrizioneAlbo(value);
	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo email (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setEmail(value);
	//	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo pec ");
	//	personaFisicaType.setPec(StringUtils.defaultIfEmpty(estremiDichiarante.getPec(), ""));
	//	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo telefono");
	//	personaFisicaType.setTelefono(StringUtils.defaultIfEmpty(estremiDichiarante.getTelefono(), ""));
	//	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo telefono cellulare (ATTENZIONE NON GESTITO)");
	//	//personaFisicaType.setTelefonoCellulare(value);
	//	log.debug("populatePersonaFisicaTypeDaAnagraficaPersona# Popolo procura (ATTENZIONE NON GESTITO)");
	//	//personaFisicaType.setProcura(value);
	//	    @XmlElement(required = true)
	//	    protected AnagraficaPersona.Nascita nascita;
	//	    protected Indirizzo residenza;
	//	    @XmlElement(name = "e-mail")
	//	    protected List<EMail> eMail;
	//	    protected List<Telefono> telefono;
	//	    @XmlElement(name = "sito-web")
	//	    @XmlSchemaType(name = "anyURI")
	//	    protected List<String> sitoWeb;
	//	
	//	
	//	anagraficaPersona.ge
    }

    @Override
    public void populatePersonaFisicaTypeDaAnagrafica(Anagrafica anagrafica, PersonaFisicaType personaFisicaType) {

	if (personaFisicaType == null) {
	    personaFisicaType = new PersonaFisicaType();
	}
	if (anagrafica != null) {
	    log.debug("populatePersonaFisicaTypeDaAnagrafica# Popolo nome");
	    personaFisicaType.setNome(StringUtils.defaultIfEmpty(anagrafica.getNome(), ""));
	    log.debug("populatePersonaFisicaTypeDaAnagrafica# Popolo cognome");
	    personaFisicaType.setCognome(StringUtils.defaultIfEmpty(anagrafica.getCognome(), ""));
	    log.debug("populatePersonaFisicaTypeDaAnagrafica# Popolo codice fiscale ");
	    personaFisicaType.setCodiceFiscale(StringUtils.defaultIfEmpty(anagrafica.getCodiceFiscale(), ""));
	    if (anagrafica.getNazionalita() != null) {
		log.debug("populatePersonaFisicaTypeDaAnagrafica# Popolo  cittadinanza ");
		CittadinanzaType cittadinanzaType = new CittadinanzaType();
		populateCittadinanzaType(anagrafica.getNazionalita(), cittadinanzaType);
		personaFisicaType.setCittadinanza(cittadinanzaType);
	    }
	}
    }

    public void populatePersonaFisicaTypeAnagraficaRappresentante(AnagraficaRappresentante anagraficaRappresentante,
	    PersonaFisicaType personaFisicaType) {

	if (personaFisicaType == null) {
	    personaFisicaType = new PersonaFisicaType();
	}
	Anagrafica anagrafica = (Anagrafica) anagraficaRappresentante;
	populatePersonaFisicaTypeDaAnagrafica(anagrafica, personaFisicaType);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo titolo (ATTENZIONE NON GESTITO)");
	personaFisicaType.setTitolo("");
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo sesso (ATTENZIONE NON GESTITO)");
	personaFisicaType.setSesso("");
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo comune nascita (ATTENZIONE NON GESTITO)");
	personaFisicaType.setComuneNascita(null);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo data nascita (ATTENZIONE NON GESTITO)");
	personaFisicaType.setDataNascita(null);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo residenza (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setResidenza(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo corrispondenza (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setCorrispondenza(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo data iscrizione albo  (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setDatiIscrizioneAlbo(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo email (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setEmail(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo pec (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setPec(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo telefono (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setTelefono(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo telefono cellulare (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setTelefonoCellulare(value);
	log.debug("populatePersonaFisicaTypeAnagraficaRappresentante# Popolo procura (ATTENZIONE NON GESTITO)");
	//personaFisicaType.setProcura(value);
    }

    @Override
    public void populateAnagrafeTypeFisica(PersonaFisicaType personaFisicaType, AnagrafeType anagrafeType) {

	populateAnagrafeType(personaFisicaType, null, anagrafeType);
    }

    @Override
    public void populateAnagrafeTypeGiuridica(PersonaGiuridicaType personaGiuridicaType, AnagrafeType anagrafeType) {

	populateAnagrafeType(null, personaGiuridicaType, anagrafeType);
    }

    @Override
    public void populateAnagrafeType(PersonaFisicaType personaFisicaType, PersonaGiuridicaType personaGiuridicaType, AnagrafeType anagrafeType) {

	if (anagrafeType == null) {
	    anagrafeType = new AnagrafeType();
	}
	if (personaFisicaType != null) {
	    anagrafeType.setPersonaFisica(personaFisicaType);
	}
	if (personaGiuridicaType != null) {
	    anagrafeType.setPersonaGiuridica(personaGiuridicaType);
	}
    }

    @Override
    public void populateCittadinanzaType(Stato stato, CittadinanzaType cittadinanzaType) {

	if (cittadinanzaType == null) {
	    cittadinanzaType = new CittadinanzaType();
	}
	if (stato != null) {
	    log.debug("populateCittadinanzaType# Codice catastale = {}, Nazione = {}", stato.getCodiceCatastale(), stato.getValue());
	    cittadinanzaType.setCodiceCatastale(stato.getCodiceCatastale());
	    cittadinanzaType.setDescrizione(stato.getValue());
	    cittadinanzaType.setId(stato.getCodiceCatastale());
	}
    }

    @Override
    public void populateRegistroREAType(String siglaProv, XMLGregorianCalendar dataIscrizione, String numero, RegistroREAType reaType) {

	if (reaType == null) {
	    reaType = new RegistroREAType();
	}
	log.debug("populateRegistroREAType# popolo data= {}, numero = {}, sigla prov = {}", dataIscrizione, numero, siglaProv);
	reaType.setData(dataIscrizione);
	reaType.setNumero(numero);
	reaType.setSiglaProvincia(siglaProv);
    }

    @Override
    public void populateLocalizzazioneType(IndirizzoConRecapiti indirizzoConRecapiti, LocalizzazioneType localizzazioneType) {

	if (localizzazioneType == null) {
	    localizzazioneType = new LocalizzazioneType();
	}
	log.debug("populateLocalizzazioneType# popolo Cap");
	localizzazioneType.setCap(StringUtils.defaultIfEmpty(indirizzoConRecapiti.getCap(), ""));
	log.debug("populateLocalizzazioneType# popolo Civico");
	localizzazioneType.setCivico(StringUtils.defaultIfEmpty(indirizzoConRecapiti.getNumeroCivico(), ""));
	log.debug("populateLocalizzazioneType# popolo frazione");
	localizzazioneType.setLocalita(StringUtils.defaultIfEmpty(indirizzoConRecapiti.getFrazione(), ""));
	log.debug("populateLocalizzazioneType# popolo Indirizzo");
	localizzazioneType.setIndirizzo(StringUtils.defaultIfEmpty(indirizzoConRecapiti.getToponimo(), "") + " " +
					StringUtils.defaultIfEmpty(indirizzoConRecapiti.getDenominazioneStradale(), ""));
	if (indirizzoConRecapiti.getProvincia() != null) {
	    log.debug("populateLocalizzazioneType# popolo sigla provincia");
	    localizzazioneType.setProvincia(StringUtils.defaultIfEmpty(indirizzoConRecapiti.getProvincia().getSigla(), ""));
	}
	ComuneType cType = new ComuneType();
	if (indirizzoConRecapiti.getComune() != null) {
	    log.debug("populateLocalizzazioneType# popolo Comune");
	    populateComuneType(indirizzoConRecapiti.getComune().getCodiceIstat(), indirizzoConRecapiti.getComune().getCodiceCatastale(),
		    indirizzoConRecapiti.getComune().getValue(), cType);
	    localizzazioneType.setComune(cType);
	}
	//TOLTO: LOGICA SBAGLIATO SOVRASCRIVE I DATI DEL COMUNE CON QUELLI DELLO STATO
	//	if (indirizzoConRecapiti.getStato() != null) {
	//	    ComuneType cStatoType = new ComuneType();
	//	    log.debug("populateLocalizzazioneType# popolo Comune con stato estero");
	//	    populateComuneType(indirizzoConRecapiti.getStato().getCodiceIstat(), indirizzoConRecapiti.getStato().getCodiceCatastale(),
	//		    indirizzoConRecapiti.getStato().getValue(), cStatoType);
	//	    localizzazioneType.setComune(cStatoType);
	//	    localizzazioneType.setLocalita(StringUtils.defaultIfEmpty(indirizzoConRecapiti.getCittaStraniera(), ""));
	//	}
    }

    @Override
    public void populateComuneType(String codiceIstat, String codiceCatastale, String nome, ComuneType comuneType) {

	if (comuneType == null) {
	    comuneType = new ComuneType();
	}
	log.debug("populateComuneType# popolo codicecatastale= {}, codiceistat = {}, comune = {}", codiceCatastale, codiceIstat, nome);
	comuneType.setCodiceCatastale(codiceCatastale);
	comuneType.setCodiceIstat(codiceIstat);
	comuneType.setComune(nome);
    }

    @Override
    public void populateComuneType(Comune comune, ComuneType comuneType) {

	if (comuneType == null) {
	    comuneType = new ComuneType();
	}
	comuneType.setCodiceCatastale(StringUtils.defaultIfEmpty(comune.getCodiceCatastale(), ""));
	comuneType.setCodiceIstat(StringUtils.defaultIfEmpty(comune.getCodiceIstat(), ""));
	comuneType.setComune(StringUtils.defaultIfEmpty(comune.getValue(), ""));
    }

    @Override
    public void populateRichiedenteType(Anagrafica anagrafica, Carica carica, RichiedenteType richiedenteType) {

	if (richiedenteType == null) {
	    richiedenteType = new RichiedenteType();
	}
	PersonaFisicaType personaFisicaType = new PersonaFisicaType();
	populatePersonaFisicaTypeDaAnagrafica(anagrafica, personaFisicaType);
	richiedenteType.setAnagrafica(personaFisicaType);
	if (carica != null) {
	    log.debug("populateRichiedenteType# popolo carica (RuoloType)...");
	    RuoloType ruoloType = new RuoloType();
	    ruoloType.setIdRuolo(StringUtils.defaultString(carica.getValue(), ""));
	    ruoloType.setRuolo(StringUtils.defaultString(carica.getValue(), ""));
	    richiedenteType.setRuolo(ruoloType);
	}
    }

    @Override
    public void populateDocumentiType(Allegato allegato, DocumentiType documentiType) {

	if (documentiType == null) {
	    documentiType = new DocumentiType();
	}
	try {
	    AllegatiType allegatiType = new AllegatiType();
	    allegatiType.setAllegato(StringUtils.defaultIfEmpty(allegato.getNomeFileOriginale(), allegato.getNomeFile()));
	    allegatiType.setId(StringUtils.defaultIfEmpty(allegato.getNomeFileOriginale(), allegato.getNomeFile()));
	    AllegatoBinarioType allegatoBinarioType = new AllegatoBinarioType();
	    if (allegato.getEmbeddedFileRef() != null) {
		//.
		allegatoBinarioType.setBinaryData(allegato.getEmbeddedFileRef());
		allegatoBinarioType.setFileName(StringUtils.defaultIfEmpty(allegato.getNomeFileOriginale(), allegato.getNomeFile()));
		allegatoBinarioType.setMimeType(StringUtils.defaultIfEmpty(allegato.getMime(), allegato.getMimeBase()));
		allegatiType.setFile(allegatoBinarioType);
	    }
	    documentiType.setAllegati(allegatiType);
	    documentiType.setDocumento(allegato.getDescrizione());
	    documentiType.setId(StringUtils.defaultIfEmpty(allegato.getNomeFileOriginale(), allegato.getNomeFile()));
	} catch (Exception e) {
	    log.error("getDocumentiType# {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	}
    }

    @Override
    public void populateSchedeType(List<Allegato> listAllegati, List<SchedaType> schedaTypes) {

	if (schedaTypes == null) {
	    schedaTypes = new ArrayList<SchedaType>();
	}
	List<Allegato> allegatos = gestioneAllegatoPraticaService.findFileMDAFILE_XML_From_Allegati(listAllegati);
	for (Allegato allegato : allegatos) {
	    log.debug("populateSchedaType# Allegato = {}", StringUtils.defaultIfEmpty(allegato.getNomeFile(), allegato.getNomeFileOriginale()));
	    InputStream inXml;
	    try {
		inXml = allegato.getEmbeddedFileRef().getInputStream();
		Map<String, String> m = this.normalizzaFileXml(inXml);
		SchedaType schedaType = new SchedaType();
		populateSchedaType(m, schedaType);
		schedaTypes.add(schedaType);
	    } catch (IOException e) {
		log.error("populateSchedaType# Errore durante la conversione da datHandler -> Inputstream. File = {}",
			StringUtils.defaultIfEmpty(allegato.getNomeFile(), allegato.getNomeFileOriginale()));
	    }
	}
    }

    private void populateSchedaType(Map<String, String> m, SchedaType schedaTypes) {

	if (schedaTypes == null) {
	    schedaTypes = new SchedaType();
	}
	String nomeScheda = m.get(Utilities.ROOT);
	// nome scheda
	schedaTypes.setCodice(nomeScheda);
	schedaTypes.setDescrizione(nomeScheda);
	schedaTypes.setNome(nomeScheda);
	// SEZIONE CAMPI
	for (Map.Entry<String, String> entry : m.entrySet()) {
	    if (entry.getValue() != Utilities.ROOT) {
		log.trace("populateSchedaType# codice = {}, descrizione campo = {}", entry.getKey(), entry.getValue());
		CampoSchedaType campoSchedaType = new CampoSchedaType();
		campoSchedaType.setCodice(entry.getKey());
		campoSchedaType.setDescrizione(entry.getValue());
		// SEZIONE TIPO CAMPO DINAMICO
		CampoDinamicoType campoDinamicoType = new CampoDinamicoType();
		log.trace("populateSchedaType# campo PROPRIETA' non gestita...");
		ValoreCampoDinamicoType valoreCampoDinamicoType = new ValoreCampoDinamicoType();
		valoreCampoDinamicoType.setNome(entry.getKey());
		// 
		ElementoValoreCampoDinamicoType elementoValoreCampoDinamicoType = new ElementoValoreCampoDinamicoType();
		String valore = "";
		String valoredecodificato = "";
		if (StringUtils.isNotBlank(entry.getValue())
			&& StringUtils.contains(entry.getValue(), Utilities.SEPARATORE_VALORE_VALORE_DECODIFICATO)) {
		    // AD OGGI NON GESTITO IL VALORE DECODIFICATO LO VADO A TOGLIERE
		    valoredecodificato = StringUtils.substringAfter(entry.getValue(), Utilities.SEPARATORE_VALORE_VALORE_DECODIFICATO);
		    valore = StringUtils.substringBefore(entry.getValue(), Utilities.SEPARATORE_VALORE_VALORE_DECODIFICATO);
		} else {
		    valore = entry.getValue();
		    valoredecodificato = entry.getValue();
		}
		// Controllo se è campo di tipo data, traformo il pattern aaaa-mm-dd in 
		// 1. aaaammdd (valore)
		// 2. dd/mm/aaaa (valore decodificato)
		if (StringUtils.isNotBlank(entry.getValue()) && Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d", entry.getValue())) {
		    valore = StringUtils.remove(entry.getValue(), "-");
		    String[] campi = StringUtils.split(entry.getValue(), "-");
		    valoredecodificato = campi[2] + "/" + campi[1] + "/" + campi[0];
		    elementoValoreCampoDinamicoType.setCodice(valore);
		    elementoValoreCampoDinamicoType.setDescrizione(valoredecodificato);
		}
		elementoValoreCampoDinamicoType.setCodice(valore);
		elementoValoreCampoDinamicoType.setDescrizione(valoredecodificato);
		elementoValoreCampoDinamicoType.setIndice(0);
		elementoValoreCampoDinamicoType.setIndiceMolteplicita(0);
		valoreCampoDinamicoType.getValore().add(elementoValoreCampoDinamicoType);
		campoDinamicoType.setValoreUtente(valoreCampoDinamicoType);
		campoSchedaType.setCampoDinamico(campoDinamicoType);
		schedaTypes.getCampi().add(campoSchedaType);
	    }
	}
    }

    /**
     * private CampoSchedaType createCampoSchedaTypePerInserimentoDiretto(Dyn2CampiType dyn2CampiType,
     * List<CampoDinamico> listCampoDinamico, Scheda schedaCompilata) {
     * 
     * CampoSchedaType campoSchedaType = null; List<FvgDataSet.CampoDinamico> campiDinamiciFvgSet =
     * verificaPresenzaCampoInFVGSet(dyn2CampiType, listCampoDinamico); // Creo la scheda if
     * (!campiDinamiciFvgSet.isEmpty()) { log.debug( "createCampoSchedaTypePerInserimentoDiretto# Campo {} per la scheda
     * {} trovato sull'oggetto FVG_DATASET passato dal SOL" , dyn2CampiType.getNomeCampo(), schedaCompilata.getNome());
     * campoSchedaType = new CampoSchedaType(); campoSchedaType.setCodice(dyn2CampiType.getIdCampo().toString());
     * campoSchedaType.setDescrizione(dyn2CampiType.getEtichetta()); // Creo il campo dinamico type da settare alla
     * scheda CampoDinamicoType campoDinamicoType = new CampoDinamicoType(); // Creo il valore da associare al
     * campoDinamicoType ValoreCampoDinamicoType valoreCampoDinamicoType = new ValoreCampoDinamicoType();
     * valoreCampoDinamicoType.setNome(dyn2CampiType.getNomeCampo()); // Creo l'ElementoValoreCampoDinamicoType da
     * associare al ValoreCampoDinamicoType ElementoValoreCampoDinamicoType elementoValoreCampoDinamicoType = null; for
     * (CampoDinamico campoDinamicoFvgSet : campiDinamiciFvgSet) { elementoValoreCampoDinamicoType =
     * createElementoValoreCampoDinamicoType(campoDinamicoFvgSet);
     * valoreCampoDinamicoType.getValore().add(elementoValoreCampoDinamicoType); }
     * campoDinamicoType.setValoreUtente(valoreCampoDinamicoType); campoSchedaType.setCampoDinamico(campoDinamicoType);
     * } return campoSchedaType; }
     * 
     * private ElementoValoreCampoDinamicoType createElementoValoreCampoDinamicoType(CampoDinamico campoDinamico) {
     * 
     * log.debug( "createElementoValoreCampoDinamicoType# creo ElementoValoreCampoDinamicoType per il campo dinamico {},
     * molteplicità {}" , campoDinamico.getNome(), campoDinamico.getIndiceMolteplicita());
     * ElementoValoreCampoDinamicoType elementoValoreCampoDinamicoType = new ElementoValoreCampoDinamicoType(); // Il
     * ElementoValoreCampoDinamicoType.codice in stc indica il valore del campo dinamico (in istanza)
     * elementoValoreCampoDinamicoType.setCodice(campoDinamico.getValore()); // Il
     * ElementoValoreCampoDinamicoType.descrizione in stc indica il valore decodificato del campo dinamico (in istanza)
     * elementoValoreCampoDinamicoType.setDescrizione(campoDinamico.getValoreDecodificato()); try { Integer
     * valoreMolteplicitaInt = Integer.parseInt(campoDinamico.getIndiceMolteplicita());
     * elementoValoreCampoDinamicoType.setIndiceMolteplicita(valoreMolteplicitaInt); } catch (NumberFormatException ne)
     * { log.error("createElementoValoreCampoDinamicoType# indice molteplicità non è un valoro numerico: {}",
     * campoDinamico.getIndiceMolteplicita()); } return elementoValoreCampoDinamicoType; }
     */
    /**
     * <pre>
     *  	 Il metodo ricorsivamente scorre tutta la struttura del xml per creare una mappa chiave valore entrambe di tipo stringa.
     *    Logica:
     *           1. La root dell'xml sarà inserito con chiave root e valore il nome della struttura
     *           2. Ogni chiave avrà questa forma #padre#figlio1#figlio2#foglia#
     *           3. Il valore di ogni key sarà il valore associato al nodo foglia
     *  
     *  Es.
     *  <ModuloCommercioIngrosso>
     *  	<schedaAnagrafica>
     *  	 <datiDichiarante>
     *  	  <cognome>TODINI</cognome>
     * 	           <nome>GIANPAOLO</nome>
     * 		<nato>
     *    	  <a>
     * 		     <comune>VITERBO</comune>
     *    	  </a>
     * 		</nato>	
     *  		</datiDichiarante>
     *   </schedaAnagrafica>
     *  </ModuloCommercioIngrosso>
     *  
     *  L'xml sarà normalizzato in una mappa di così:
     *  
     *   [#ModuloCommercioIngrosso#schedaAnagrafica#datiDichiarante#cognome,TODINI]
     *   [#ModuloCommercioIngrosso#schedaAnagrafica#datiDichiarante#nome,GIANPAOLO]
     *   [#ModuloCommercioIngrosso#schedaAnagrafica#datiDichiarante#nato#a#comune,VITERBO]
     *   [#ModuloCommercioIngrosso#schedaAnagrafica#datiDichiarante#nato#data,1981-07-04]
     * </pre>
     * 
     * @param o
     * @param depth
     * @param nomevariabile
     * @param mappaNomeVariabileValore
     * @param valueTemp
     */
    @Override
    public Map<String, String> normalizzaFileXml(InputStream xml) {

	Map<String, String> m = new LinkedHashMap<String, String>();
	SAXBuilder builder = new SAXBuilder();
	Document doc;
	try {
	    // cessazione
	    //doc = builder.build("C://Temp//TDNGPL81L04M082M-26092019-1018.001.MDA.xml");
	    // apertura
	    //doc = builder.build("C://Temp//TDNGPL81L04M082M-03102019-1715.001.MDA.XML");
	    doc = builder.build(xml);
	    Utilities.listNodes(doc, 0, "", m, "");
	} catch (IOException e) {
	    log.error("normalizzaFileXml# Errore durante il caricamento del file xml da normalizzare. E = {} ", e);
	} catch (JDOMException e) {
	    log.error("normalizzaFileXml# Errore durante la normalizzazione del file xml. E = {} ", e);
	}
	for (Map.Entry<String, String> entry : m.entrySet()) {
	    log.trace("normalizzaFileXml# [Key,Valore] = [{},{}]", entry.getKey(), entry.getValue());
	}
	return m;
    }
    //    @Override
    //    public Map<String, String> normalizzaFileXml(File xml) {
    //
    //	Map<String, String> m = new LinkedHashMap<String, String>();
    //	SAXBuilder builder = new SAXBuilder();
    //	Document doc;
    //	try {
    //	    // cessazione
    //	    //doc = builder.build("C://Temp//TDNGPL81L04M082M-26092019-1018.001.MDA.xml");
    //	    // apertura
    //	    //doc = builder.build("C://Temp//TDNGPL81L04M082M-03102019-1715.001.MDA.XML");
    //	    doc = builder.build(xml);
    //	    Utilities.listNodes(doc, 0, "", m, "");
    //	} catch (IOException e) {
    //	    log.error("normalizzaFileXml# Errore durante il caricamento del file xml da normalizzare. E = {} ", e);
    //	} catch (JDOMException e) {
    //	    log.error("normalizzaFileXml# Errore durante la normalizzazione del file xml. E = {} ", e);
    //	}
    //	for (Map.Entry<String, String> entry : m.entrySet()) {
    //	    log.error("normalizzaFileXml# [Key,Valore] = [{},{}]", entry.getKey(), entry.getValue());
    //	}
    //	return m;
    //    }
}
