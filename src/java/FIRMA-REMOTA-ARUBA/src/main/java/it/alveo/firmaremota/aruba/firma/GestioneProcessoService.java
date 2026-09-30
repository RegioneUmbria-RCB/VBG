package it.alveo.firmaremota.aruba.firma;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.alveo.firmaremota.aruba.BaseDelegate;
import it.alveo.firmaremota.aruba.api.ProcessiApiDelegate;
import it.alveo.firmaremota.aruba.client.ArubaClient;
import it.alveo.firmaremota.aruba.configurazione.ConfigurazioneBean;
import it.alveo.firmaremota.aruba.configurazione.ConfigurazioneService;
import it.alveo.firmaremota.aruba.exception.FirmaException;
import it.alveo.firmaremota.aruba.exception.GenericException;
import it.alveo.firmaremota.aruba.model.AvviaProcessoRequest;
import it.alveo.firmaremota.aruba.model.FirmaRequest;
import it.alveo.firmaremota.aruba.model.InfoDocumentiProcesso;
import it.alveo.firmaremota.aruba.model.InfoProcesso;
import it.alveo.firmaremota.aruba.model.SessionId;
import it.alveo.firmaremota.aruba.model.StatoProcesso;
import it.arubapec.arubasignservice.CredentialsType;

@Service
public class GestioneProcessoService extends BaseDelegate implements ProcessiApiDelegate {

    private static final Logger logger = LoggerFactory.getLogger(GestioneProcessoService.class);
    private ConfigurazioneService configService;

    public GestioneProcessoService(ConfigurazioneService configService) {

	this.configService = configService;
    }

    @Override
    public ResponseEntity<InfoProcesso> processiSessionidGet(String sessionid) {

	logger.debug("Richiesta stato avanzamento processo di firma {}", sessionid);
	//1. Recupero il file con le info del processo
	var processFile = Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessFileName()).toFile();
	var mapper = new ObjectMapper();
	ProcessoBean infoProcesso;
	try {
	    infoProcesso = mapper.readValue(processFile, ProcessoBean.class);
	} catch (Exception e) {
	    throw new GenericException(e.getCause());
	}
	var info = new InfoProcesso();
	info.stato(StatoProcesso.valueOf(infoProcesso.getStato()));
	var infoDoc = new InfoDocumentiProcesso();
	infoDoc.caricati(infoProcesso.getDocumenti().size());
	infoDoc.firmati((int) infoProcesso.getDocumenti().stream().filter(d -> d.isFirmato()).count());
	info.documenti(infoDoc);
	logger.debug("Fine richiesta stato avanzamento processo di firma {} ", sessionid);
	return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SessionId> processiPost(AvviaProcessoRequest avviaProcessoRequest) {

	logger.debug("Inizializzazione del processo di firma");
	var sessionid = UUID.randomUUID().toString();
	var cfg = this.configService.getConfigurazione(sessionid, avviaProcessoRequest.getConfigurazione());
	//1. Creo la cartella temporanea
	logger.debug("Creazione della cartella temporanea");
	Paths.get(super.arubaParams.getTempPath(), sessionid).toFile().mkdirs();
	//2 Creo la cartella processati dentro la temporanea
	logger.debug("Creazione della cartella dei file processati");
	Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessatiPath()).toFile().mkdirs();
	//3. Inizializzo le info del processso
	logger.debug("Serializzazione delle info di processo");
	var infoProcesso = new ProcessoBean();
	infoProcesso.setSessionId(sessionid);
	infoProcesso.setConfigurazione(cfg);
	infoProcesso.setStato(StatoProcesso.AVVIATO.toString());
	//4. Salvo le info del processo
	try {
	    var filePath = Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessFileName());
	    try (var fileWriter = new FileWriter(filePath.toFile(), false)) {
		var mapper = new ObjectMapper();
		var json = mapper.writeValueAsString(infoProcesso);
		fileWriter.write(json);
	    }
	} catch (IOException e) {
	    throw new GenericException(e.getCause());
	}
	//5. Inizializzo il processo di firma
	this.avviaprocesso(cfg);
	//6. Preparo la risposta
	SessionId s = new SessionId();
	s.sessionid(sessionid);
	logger.debug("Inizializzazione del processo di firma completata, sessionid {}", sessionid);
	return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> processiSessionidDocumentiPut(String sessionid, String guid, String nome, MultipartFile content) {

	logger.debug("Inizio caricamento file {} ( {} ) per la sessione di firma : {}", nome, guid, sessionid);
	super.validateSession(sessionid);
	if (content == null || content.isEmpty()) {
	    throw new GenericException("Non sono stati passati i file da caricare nel processo di firma");
	}
	//1. Recupero il file con le info del processo
	var processFile = Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessFileName()).toFile();
	var mapper = new ObjectMapper();
	ProcessoBean infoProcesso;
	try {
	    infoProcesso = mapper.readValue(processFile, ProcessoBean.class);
	} catch (Exception e) {
	    throw new GenericException(e.getCause());
	}
	//2.0 Recupero l'estensione del file
	var estensione = nome.substring(nome.lastIndexOf("."));
	var nuovoNome = guid + estensione;
	//2.1 Salvo il documento nella cartella temporanea
	var filePath = Paths.get(super.arubaParams.getTempPath(), sessionid, nuovoNome);
	try (InputStream is = content.getInputStream()) {
	    var file = filePath.toFile();
	    if (!file.getParentFile().exists()) {
		throw new GenericException("La sessione di firma non è stata inizializzata correttamente");
	    }
	    Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
	} catch (IOException e) {
	    throw new GenericException(e.getCause());
	}
	//2.2 Aggiungo il file alle info del processo se non è già presente
	var infoDoc = infoProcesso.getDocumenti().stream().filter(d -> d.getGuid().equalsIgnoreCase(guid)).findFirst().orElse(null);
	if (infoDoc == null) {
	    var docBean = new DocumentoBean();
	    docBean.setGuid(guid);
	    docBean.setNome(nome);
	    docBean.setFirmato(false);
	    docBean.setNuovoNome(nuovoNome);
	    infoProcesso.getDocumenti().add(docBean);
	} else {
	    infoDoc.setNuovoNome(nuovoNome);
	    infoDoc.setNome(nome);
	    infoDoc.setFirmato(false);
	}
	try {
	    try (var fileWriter = new FileWriter(processFile, false)) {
		var json = mapper.writeValueAsString(infoProcesso);
		fileWriter.write(json);
	    }
	} catch (IOException e) {
	    throw new GenericException(e.getCause());
	}
	logger.debug("Fine caricamento file {} ( {} ) per la sessione di firma : {}", nome, guid, sessionid);
	return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> processiSessionidFirmaPost(String sessionid, FirmaRequest firmaRequest) {

	logger.debug("Inizio firma per la sessione {}", sessionid);
	//1. Verifica parametri passati
	super.validateSession(sessionid);
	//2. Verifica dei documenti
	this.verificaDocumenti(sessionid, firmaRequest.getDocumenti());
	//3. Imposto lo stato del processo in corso
	var processFile = Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessFileName()).toFile();
	var mapper = new ObjectMapper();
	ProcessoBean infoProcesso;
	try {
	    infoProcesso = mapper.readValue(processFile, ProcessoBean.class);
	} catch (Exception e) {
	    throw new GenericException(e.getCause());
	}
	infoProcesso.setStato(StatoProcesso.FIRMA_IN_CORSO.toString());
	try (var fileWriter = new FileWriter(processFile, false)) {
	    var json = mapper.writeValueAsString(infoProcesso);
	    fileWriter.write(json);
	} catch (IOException e) {
	    throw new GenericException(e.getCause());
	}
	logger.debug("Stato del processo di firma {} impostato a {}", sessionid, StatoProcesso.FIRMA_IN_CORSO);
	this.firma(sessionid, firmaRequest);
	try {
	    infoProcesso = mapper.readValue(processFile, ProcessoBean.class);
	} catch (Exception e) {
	    throw new GenericException(e.getCause());
	}
	infoProcesso.setStato(StatoProcesso.FIRMA_CONCLUSA.toString());
	try (var fileWriter = new FileWriter(processFile, false)) {
	    var json = mapper.writeValueAsString(infoProcesso);
	    fileWriter.write(json);
	} catch (IOException e) {
	    throw new GenericException(e.getCause());
	}
	logger.debug("Stato del processo di firma {} impostato a {}", sessionid, StatoProcesso.FIRMA_CONCLUSA);
	logger.debug("Fine firma per la sessione {}", sessionid);
	return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource> processiSessionidDocumentiGuidGet(String sessionid, String guid) {

	try {
	    logger.debug("Richiesta file firmato con guid {} per la sessione {}", guid, sessionid);
	    //1. Verifica parametri passati
	    super.validateSession(sessionid);
	    //2. Recupero il processo
	    var processFile = Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessFileName()).toFile();
	    //3. Recupero il file firmato
	    var mapper = new ObjectMapper();
	    ProcessoBean infoProcesso = mapper.readValue(processFile, ProcessoBean.class);
	    //4. Recupero il documento
	    DocumentoBean docBean = infoProcesso.getDocumenti().stream().filter(doc -> doc.getGuid().equalsIgnoreCase(guid)).findFirst().orElse(null);
	    if (docBean == null) {
		throw new GenericException("Non esiste un file firmato per il guid " + guid);
	    }
	    var pathDocFirmato = Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessatiPath(),
		    docBean.getNomeFileFirmato());
	    //5. Calcolo il nome del file
	    var nome = docBean.getNomeFileFirmato().replace(docBean.getNuovoNome(), docBean.getNome());
	    //5. Preparo la struttura di ritorno
	    var headers = new HttpHeaders();
	    headers.addAll(new ResponseEntity<>(HttpStatus.OK).getHeaders());
	    headers.add("X-Nome-File", nome);
	    headers.add("X-GUID", docBean.getGuid());
	    headers.add("X-Tipo-Firma", infoProcesso.getConfigurazione().getTipoFirma());
	    logger.debug("Fine richiesta file firmato con guid {} per la sessione {}", guid, sessionid);
	    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new InputStreamResource(new FileInputStream(pathDocFirmato.toFile())));
	} catch (IOException e) {
	    throw new GenericException(e);
	}
    }

    private void avviaprocesso(ConfigurazioneBean config) {

	if (Boolean.FALSE.equals(config.getRichiediOTP()) || StringUtils.isNotBlank(config.getOtp())) {
	    return;
	}
	ArubaClient client = new ArubaClient(this.arubaParams, config);
	//1. Apro la sessione
	logger.debug("Apertura della sessione presso ARUBA");
	CredentialsType ct = CredentialsType.ARUBACALL;
	if ("SMS".equalsIgnoreCase(config.getTypeSendOTP())) {
	    ct = CredentialsType.SMS;
	}
	var wsResponse = client.sendCredential(ct);
	if (!"OK".equalsIgnoreCase(wsResponse.getStatus())) {
	    throw new GenericException(wsResponse.getReturnCode() + ": " + wsResponse.getDescription());
	}
    }

    private void verificaDocumenti(String sessionid, List<String> documenti) {

	if (documenti == null || documenti.isEmpty()) {
	    throw new FirmaException("E' stata richiesta la firma remota senza specificare i documenti da firmare");
	}
	var processFile = Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessFileName()).toFile();
	var mapper = new ObjectMapper();
	try {
	    var infoProcesso = mapper.readValue(processFile, ProcessoBean.class);
	    var guidDocCaricati = infoProcesso.getDocumenti().stream().map(d -> d.getGuid()).toList();
	    var guidDocDaFirmare = new ArrayList<>(documenti.stream().toList());
	    guidDocDaFirmare.removeAll(guidDocCaricati);
	    if (!guidDocDaFirmare.isEmpty()) {
		throw new FirmaException(
			"E' stata richiesta la firma di alcuni file non caricati precedentemente! La lista dei guid " + guidDocDaFirmare.toString());
	    }
	} catch (Exception e) {
	    throw new GenericException(e.getMessage());
	}
    }

    private void firma(String sessionid, FirmaRequest firmaRequest) {

	logger.info("Inizio metodo firma");
	//1. Rimappo la configurazione
	var config = this.configService.getConfigurazione(sessionid, firmaRequest.getConfigurazione());
	//2. Aggiorno il file di processo
	var processFile = Paths.get(super.arubaParams.getTempPath(), sessionid, super.arubaParams.getProcessFileName()).toFile();
	var mapper = new ObjectMapper();
	ProcessoBean infoProcesso;
	try {
	    infoProcesso = mapper.readValue(processFile, ProcessoBean.class);
	} catch (Exception e) {
	    throw new GenericException(e.getCause());
	}
	infoProcesso.setConfigurazione(config);
	try (var fileWriter = new FileWriter(processFile, false)) {
	    var json = mapper.writeValueAsString(infoProcesso);
	    fileWriter.write(json);
	} catch (IOException e) {
	    throw new GenericException(e.getCause());
	}
	//2. Inizializzo il service di firma
	var service = new FirmaService(logger, arubaParams);
	//3. Firmo di documenti
	service.firma(sessionid);
	logger.info("Fine metodo firma");
    }
}
