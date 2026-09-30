package it.gruppoinit.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gruppoinit.constants.WebConstants;
import it.gruppoinit.domain.nla.Allegato;
import it.gruppoinit.service.GestioneAllegatoPraticaService;
import it.gruppoinit.utilities.Utilities;

@Service
public class GestioneAllegatoPraticaServiceImpl implements GestioneAllegatoPraticaService {

    private static final Logger log = LoggerFactory.getLogger(GestioneAllegatoPraticaServiceImpl.class);

    @Override
    public Allegato findFileSUAP_XML(List<AllegatoCooperazione> allegatiCooperazione, String prefisso) throws Exception {

	Allegato allegato = findFile(allegatiCooperazione, prefisso, WebConstants.PREFIX_ALLEGATO_SUAP, WebConstants.ESTENSIONE_XML);
	return allegato;
    }

    @Override
    public Allegato findFile(List<AllegatoCooperazione> allegatiCooperazione, String prefisso, String suffisso, String estesione) throws Exception {

	Allegato a = null;
	String nomeFileConfronto = prefisso + "." + suffisso + "." + estesione;
	for (AllegatoCooperazione allegatoCooperazione : allegatiCooperazione) {
	    String nomeFile = allegatoCooperazione.getNomeFile();
	    if (nomeFile.toUpperCase().equals(nomeFileConfronto.toUpperCase())) {
		a = new Allegato();
		try {
		    BeanUtilsBean2.getInstance().copyProperties(a, allegatoCooperazione);
		} catch (IllegalAccessException e) {
		    log.error("findFile# 1. errore durante la copia da AllegatoCooperazione a Allegato: {}", e);
		    throw e;
		} catch (InvocationTargetException e) {
		    log.error("findFile# 2. errore durante la copia da AllegatoCooperazione a Allegato: {}", e);
		    throw e;
		}
		break;
	    }
	}
	return a;
    }

    @Override
    public Allegato findFileSUAP_XML_From_Allegati(List<Allegato> allegati, String prefisso) {

	String nomeFileConfronto = prefisso + "." + WebConstants.PREFIX_ALLEGATO_SUAP + "." + WebConstants.ESTENSIONE_XML;
	for (Allegato allegato : allegati) {
	    String nomeFile = allegato.getNomeFile();
	    if (nomeFile.toUpperCase().equals(nomeFileConfronto.toUpperCase())) {
		return allegato;
	    }
	}
	return null;
    }

    @Override
    public List<Allegato> loadFileS(List<AllegatoCooperazione> allegatiCooperazione) throws Exception {

	Allegato a = null;
	List<Allegato> l = new ArrayList<Allegato>();
	for (AllegatoCooperazione allegatoCooperazione : allegatiCooperazione) {
	    a = new Allegato();
	    try {
		BeanUtilsBean2.getInstance().copyProperties(a, allegatoCooperazione);
		l.add(a);
	    } catch (IllegalAccessException e) {
		log.error("findFile# 1. errore durante la copia da AllegatoCooperazione a Allegato: {}", e);
		throw e;
	    } catch (InvocationTargetException e) {
		log.error("findFile# 2. errore durante la copia da AllegatoCooperazione a Allegato: {}", e);
		throw e;
	    }
	}
	return l;
    }

    @Override
    public List<Allegato> findFileMDAFILE_XML_From_Allegati(List<Allegato> allegati) {

	List<Allegato> list = new ArrayList<Allegato>();
	for (Allegato allegato : allegati) {
	    String nomeFile = allegato.getNomeFile();
	    if (nomeFile.toUpperCase().contains("MDA.XML")) {
		list.add(allegato);
	    }
	}
	return list;
    }

    @Override
    public File generateFilComunicazioneSuapEnte(String comunicazioneSuapEnteStringa, CooperazioneSUAPEnte cooperazioneSUAPEnte) {

	File fileComunicazione = null;
	try {
	    fileComunicazione = File.createTempFile("temp", "-comunicazione-" + cooperazioneSUAPEnte.getIntestazione().getCodicePratica() + ".xml");
	    BufferedWriter writer = new BufferedWriter(new FileWriter(fileComunicazione));
	    writer.write(comunicazioneSuapEnteStringa);
	    writer.close();
	} catch (IOException e) {
	    log.error("inviaSUAPEnte# Errore durante la creazione del file xml della comunicazione tipo {}, della pratica = {} ",
		    cooperazioneSUAPEnte.getIntestazione().getOggettoComunicazione().getTipoCooperazione(),
		    cooperazioneSUAPEnte.getIntestazione().getCodicePratica());
	}
	return fileComunicazione;
    }

    @Override
    public String saveComunicazioneSuapEnteInString(CooperazioneSUAPEnte cooperazioneSUAPEnte) {

	String comunicazione = "";
	CooperazioneSUAPEnte copiaComunicazione = null;
	try {
	    // duplico la chiamata perchè non si riesce  a fare il marshall degli allegato con il dataHandler popolato
	    copiaComunicazione = duplicaChiamataConAllegatiSenzaFile(cooperazioneSUAPEnte);
	    comunicazione = (String) Utilities.marshallObject(copiaComunicazione);
	} catch (Exception e) {
	    log.error("inviaSUAPEnte# Errore nel marshalling della comunicazione tipo {}, della pratica = {} ",
		    cooperazioneSUAPEnte.getIntestazione().getOggettoComunicazione().getTipoCooperazione(),
		    cooperazioneSUAPEnte.getIntestazione().getCodicePratica());
	}
	return comunicazione;
    }

    private CooperazioneSUAPEnte duplicaChiamataConAllegatiSenzaFile(CooperazioneSUAPEnte cooperazioneSUAPEnte) {

	CooperazioneSUAPEnte copia = new CooperazioneSUAPEnte();
	try {
	    BeanUtils.copyProperties(copia, cooperazioneSUAPEnte);
	    List<AllegatoCooperazione> origin = cooperazioneSUAPEnte.getAllegato();
	    for (AllegatoCooperazione allegatoCooperazione : origin) {
		AllegatoCooperazione target = new AllegatoCooperazione();
		BeanUtils.copyProperties(target, allegatoCooperazione);
		copia.getAllegato().add(target);
	    }
	} catch (IllegalAccessException e) {
	    log.error("duplicaChiamataConAllegatiSenzaFile# ");
	} catch (InvocationTargetException e) {
	    log.error("duplicaChiamataConAllegatiSenzaFile# ");
	}
	List<AllegatoCooperazione> l = copia.getAllegato();
	for (AllegatoCooperazione allegatoCooperazione : l) {
	    allegatoCooperazione.setEmbeddedFileRef(null);
	}
	return copia;
    }
}
