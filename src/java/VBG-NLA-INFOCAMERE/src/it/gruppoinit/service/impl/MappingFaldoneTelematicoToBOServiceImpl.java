package it.gruppoinit.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.gruppoinit.domain.nla.Allegato;
import it.gruppoinit.faldonetelematico.model.FaldoneTelematicoValoreCampoEnum;
import it.gruppoinit.faldonetelematico.model.ValoreCampo;
import it.gruppoinit.service.MappingFaldoneTelematicoToBOService;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.CittadinanzaType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.IscrizioneRegistroType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.RegistroREAType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RuoloType;

@Service
public class MappingFaldoneTelematicoToBOServiceImpl implements MappingFaldoneTelematicoToBOService {

    public static final Logger log = LoggerFactory.getLogger(MappingFaldoneTelematicoToBOServiceImpl.class);

    @Override
    public void populatePersonaGiuridicaType(ValoreCampo personaGiuridicaFaldoneTelematico, PersonaGiuridicaType personaGiuridicaType) {

	List<ValoreCampo> campiPersonaGiuridica = personaGiuridicaFaldoneTelematico.getSubValoreCampo();
	LocalizzazioneType localizzazioneType = new LocalizzazioneType();
	IscrizioneRegistroType iscrizioneCCIAA = new IscrizioneRegistroType();
	RegistroREAType iscrizioneREA = new RegistroREAType();
	for (ValoreCampo campoPersonaGiuridica : campiPersonaGiuridica) {
	    switch (FaldoneTelematicoValoreCampoEnum.fromValue(campoPersonaGiuridica.getNome())) {
	    case DENOMINAZIONE:
		personaGiuridicaType.setRagioneSociale(campoPersonaGiuridica.getValore());
		break;
	    case COMUNESEDE:
		ComuneType comuneType = new ComuneType();
		comuneType.setComune(campoPersonaGiuridica.getValore());
		localizzazioneType.setComune(comuneType);
		break;
	    case VIASEDE:
		localizzazioneType.setIndirizzo(campoPersonaGiuridica.getValore());
	    case CAPSEDE:
		localizzazioneType.setCap(campoPersonaGiuridica.getValore());
		break;
	    case EMAILPEC:
		personaGiuridicaType.setPec(campoPersonaGiuridica.getValore());
		break;
	    case ATTRIBUTI_CCIAANUMERO:
		iscrizioneCCIAA.setNumero(campoPersonaGiuridica.getValore());
		break;
	    case ATTRIBUTI_REANUMERO:
		iscrizioneREA.setNumero(campoPersonaGiuridica.getValore());
		break;
	    case ATTRIBUTI_REAPROV:
		iscrizioneREA.setSiglaProvincia(campoPersonaGiuridica.getValore());
		break;
	    case PI:
		personaGiuridicaType.setPartitaIva(campoPersonaGiuridica.getValore());
		break;
	    case CFPI:
		personaGiuridicaType.setCodiceFiscale(campoPersonaGiuridica.getValore());
		break;
	    case TELEFONOSEDE:
		personaGiuridicaType.setTelefono(campoPersonaGiuridica.getValore());
		break;
	    case PROVINCIASEDE:
		localizzazioneType.setProvincia(campoPersonaGiuridica.getValore());
		//	    case PARTITAIVA:
		//		personaGiuridicaType.setPartitaIva(campoPersonaGiuridica.getValore());
		//		break;
	    default:
		break;
	    }
	}
	personaGiuridicaType.setIscrizioneCCIAA(iscrizioneCCIAA);
	personaGiuridicaType.setIscrizioneREA(iscrizioneREA);
	personaGiuridicaType.setSedeLegale(localizzazioneType);
    }

    @Override
    public void populatePersonaFisicaType(ValoreCampo personaFisicaFaldoneTelematico, PersonaFisicaType personaFisicaType) {

	List<ValoreCampo> campiPersonaFisica = personaFisicaFaldoneTelematico.getSubValoreCampo();
	LocalizzazioneType localizzazioneType = new LocalizzazioneType();
	for (ValoreCampo campoPersonaFisica : campiPersonaFisica) {
	    switch (FaldoneTelematicoValoreCampoEnum.fromValue(campoPersonaFisica.getNome())) {
	    case CODICEFISCALE:
		personaFisicaType.setCodiceFiscale(campoPersonaFisica.getValore());
		break;
	    case CAPRESIDENZA:
		localizzazioneType.setCap(campoPersonaFisica.getValore());
		break;
	    case CITTADINANZA:
		CittadinanzaType cittadinanzaType = new CittadinanzaType();
		cittadinanzaType.setDescrizione(campoPersonaFisica.getValore());
		personaFisicaType.setCittadinanza(cittadinanzaType);
		break;
	    case CIVICORESIDENZA:
		localizzazioneType.setCivico(campoPersonaFisica.getValore());
		break;
	    case DATANASCITA:
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataNascita;
		try {
		    dataNascita = sdf.parse(campoPersonaFisica.getValore());
		    personaFisicaType.setDataNascita(Utilities.getXMLGregorianCalendar(dataNascita));
		} catch (ParseException e) {
		    log.error("Errore nel parsing della data di nascità: {}", e.getMessage());
		}
		break;
	    case NOME:
		personaFisicaType.setNome(campoPersonaFisica.getValore());
		break;
	    case COGNOME:
		personaFisicaType.setCognome(campoPersonaFisica.getValore());
		break;
	    case SESSO:
		personaFisicaType.setSesso(campoPersonaFisica.getValore());
		break;
	    case LUOGONASCITA:
		ComuneType comuneType = new ComuneType();
		comuneType.setComune(campoPersonaFisica.getValore());
		personaFisicaType.setComuneNascita(comuneType);
		break;
	    case VIARESIDENZA:
		localizzazioneType.setIndirizzo(campoPersonaFisica.getValore());
		break;
	    case EMAILPEC:
		personaFisicaType.setPec(campoPersonaFisica.getValore());
		break;
	    default:
		break;
	    }
	}
	personaFisicaType.setResidenza(localizzazioneType);
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
	    //documentiType.setData(value);
	    documentiType.setDocumento(allegato.getDescrizione());
	    documentiType.setId(StringUtils.defaultIfEmpty(allegato.getNomeFileOriginale(), allegato.getNomeFile()));
	} catch (Exception e) {
	    log.error("getDocumentiType# {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	}
    }

    @Override
    public void populateComuneType(ValoreCampo modulo, ComuneType comuneType) {

	for (ValoreCampo campoModulo : modulo.getSubValoreCampo()) {
	    switch (FaldoneTelematicoValoreCampoEnum.fromValue(campoModulo.getNome())) {
	    case BELFIORE:
		String belFiore = StringUtils.split(campoModulo.getValore(), "_")[1].toUpperCase();
		comuneType.setCodiceCatastale(belFiore);
		break;
	    case LUOGO:
		comuneType.setComune(campoModulo.getValore());
	    default:
		break;
	    }
	}
    }

    public void populateRichiedenteType(ValoreCampo titolare, RichiedenteType richiedenteType) {

	for (ValoreCampo campo : titolare.getSubValoreCampo()) {
	    if (StringUtils.equalsIgnoreCase(campo.getNome(), "Ruolo")) {
		RuoloType ruoloType = new RuoloType();
		ruoloType.setRuolo(campo.getValore());
		richiedenteType.setRuolo(ruoloType);
		break;
	    }
	}
    }
}
