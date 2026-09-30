package it.gruppoinit.pdd.ri.features.configurazione;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;

@Service
public class ConfigurazioneServiceImpl implements ConfigurazioneService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurazioneServiceImpl.class);
    private static final String PATH = System.getProperty("catalina.home") + File.separator + "config_files" + File.separator + "nla-pdd-ri"+ File.separator;
    private static final String CONFIG_FILE_NAME = "nlapddri.config.json";
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;

    @Autowired
    public void setSigeproSecurityWebServiceClient(SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient) {

	this.sigeproSecurityWebServiceClient = sigeproSecurityWebServiceClient;
    }

    @Override
    public List<Ente> elencoCertificati() throws JsonParseException, JsonMappingException, IOException {

	return this.caricaConfigurazione().getEnti();
    }

    @Override
    public Configurazione caricaConfigurazione() throws JsonParseException, JsonMappingException, IOException {

	Configurazione config = new Configurazione();
	File configFile = getConfigFile();
	if (configFile.isFile()) {
	    ObjectMapper mapper = new ObjectMapper();
	    config = mapper.readValue(Paths.get(configFile.toURI()).toFile(), Configurazione.class);
	}
	return config;
    }

    @Override
    public Ente findByCodiceCatastale(String codiceCatastale) {

	try {
	    List<Ente> certificati = this.elencoCertificati();
	    for (Ente certificato : certificati) {
		if (certificato.getCodiceCatastale().equalsIgnoreCase(codiceCatastale)) {
		    return certificato;
		}
	    }
	} catch (Exception e) {
	}
	return null;
    }

    @Override
    public List<Ente> addRange(String[] codiciCatastali, MultipartFile certificato, String password, String alias) {

	List<Ente> aggiunti = new ArrayList<>();
	try {
	    this.salvaCertificato(certificato);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
	for (String codiceCatastale : codiciCatastali) {
	    try {
		aggiunti.add(this.registraCertificato(codiceCatastale, certificato.getOriginalFilename(), password, alias));
	    } catch (Exception e) {
		logger.error("Si è verificato un errore durante la configurazione di più enti :" + e.getMessage());
	    }
	}
	return aggiunti;
    }

    @Override
    public Ente add(String codiceCatastale, MultipartFile certificato, String password, String alias) {

	try {
	    this.salvaCertificato(certificato);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
	try {
	    return this.registraCertificato(codiceCatastale, certificato.getOriginalFilename(), password, alias);
	} catch (JAXBException | IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public List<String> findComuniAssociati(String idComuneAlias) {

	String sql = "select codicecomune from comuniassociati where idcomune = ? order by codicecomune asc";
	String idComune = sigeproSecurityWebServiceClient.getConnectionProperties(idComuneAlias).getIdComune();
	Connection conn = this.sigeproSecurityWebServiceClient.getConnection(idComuneAlias);
	try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	    pstmt.setString(1, idComune);
	    ResultSet rs = pstmt.executeQuery();
	    List<String> codiciComune = new ArrayList<>();
	    while (rs.next()) {
		codiciComune.add(rs.getString("codicecomune"));
	    }
	    return codiciComune;
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    private Ente registraCertificato(String codiceCatastale, String nomeCertificato, String password, String alias)
	    throws JAXBException, JsonParseException, JsonMappingException, IOException {

	//1. Verifico esistenza della configurazione
	Configurazione config = this.caricaConfigurazione();
	//2. Verifico la presenza dell'ente tra gli enti censiti
	for (Ente ente : config.getEnti()) {
	    if (ente.getCodiceCatastale().equalsIgnoreCase(codiceCatastale)) {
		throw new RuntimeException("Esiste già un certificato per l'ente " + codiceCatastale);
	    }
	}
	//3. Aggiungo l'ente
	Ente nuovoEnte = new Ente(codiceCatastale, nomeCertificato);
	config.getEnti().add(nuovoEnte);
	ObjectMapper mapper = new ObjectMapper();
	mapper.writeValue(getConfigFile(), config);
	//4. Verifico la presenza del certificato
	for (Certificato certificato : config.getCertificati()) {
	    if (certificato.getNomeFile().equalsIgnoreCase(nomeCertificato)) {
		return nuovoEnte;
	    }
	}
	//4. Aggiungo il certificato
	config.getCertificati().add(new Certificato(PATH, nomeCertificato, password, alias));
	mapper.writeValue(getConfigFile(), config);
	return nuovoEnte;
    }

    private void salvaCertificato(MultipartFile certificato) throws IOException {

	String fileName = certificato.getOriginalFilename();
	OutputStream out = null;
	InputStream filecontent = null;
	try {
	    File folderConfig = new File(PATH);
	    if (!folderConfig.exists()) {
		folderConfig.mkdirs();
	    }
	    File fout = new File(folderConfig, fileName);
	    if (fout.isFile()) {
		logger.info("Il certificato {} è già presente pertanto non verrà sostituito", fileName);
		return;
	    }
	    out = new FileOutputStream(fout);
	    filecontent = certificato.getInputStream();
	    int read = 0;
	    final byte[] bytes = new byte[1024];
	    while ((read = filecontent.read(bytes)) != -1) {
		out.write(bytes, 0, read);
	    }
	    logger.info("File {} being uploaded to {}", fileName, PATH);
	} catch (IOException e) {
	    logger.error("Problems during file upload. Error: {}" + e.getMessage(), e);
	} finally {
	    if (out != null) {
		out.close();
	    }
	    if (filecontent != null) {
		filecontent.close();
	    }
	}
    }

    @Override
    public void elimina(String codiceCatastale) throws JsonParseException, JsonMappingException, IOException {

	//1. Carico la configurazione
	Configurazione config = this.caricaConfigurazione();
	if (config.getEnti().isEmpty()) {
	    return;
	}
	//2. Rimuovo l'ente dalla configurazione
	List<Ente> enti = new ArrayList<>();
	enti.addAll(config.getEnti());
	for (Ente ente : config.getEnti()) {
	    if (ente.getCodiceCatastale().equalsIgnoreCase(codiceCatastale)) {
		enti.remove(ente);
	    }
	}
	config.setEnti(enti);
	//3. Salvo la configurazione
	ObjectMapper mapper = new ObjectMapper();
	mapper.writeValue(getConfigFile(), config);
    }

    @Override
    public Certificato findByNome(String nomeCertificato) {

	try {
	    //1. Carico la configurazione
	    Configurazione config = this.caricaConfigurazione();
	    //2. Cerco il certificato
	    for (Certificato certificato : config.getCertificati()) {
		if (certificato.getNomeFile().equalsIgnoreCase(nomeCertificato)) {
		    return certificato;
		}
	    }
	    //3. Certificato non presente
	    return null;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    private File getConfigFile() {

	File folderConfig = new File(PATH);
	if (!folderConfig.exists()) {
	    folderConfig.mkdirs();
	}
	return new File(folderConfig, CONFIG_FILE_NAME);
    }
}
