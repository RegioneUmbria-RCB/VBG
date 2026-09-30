package it.gruppoinit.pal.gp.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSystemHelper implements IFileHelper {

    private String userName;
    private String password;
    private String percorso;
    private String uri;
    private static final Integer CONNECTION_TIMEOUT = 240000;
    private static final Logger log = LoggerFactory.getLogger(FileSystemHelper.class);

    public FileSystemHelper(String uri) throws IOException {

	this.uri = uri;
	verificheIniziali();
    }

    public FileSystemHelper(String uri, String userName, String password) throws IOException {

	this.uri = uri;
	log.info("FileSystemHelper: uri {}", this.uri);
	this.userName = userName;
	this.password = password;
	verificheIniziali();
    }

    private void verificheIniziali() throws IOException {

	if (StringUtils.isEmpty(this.uri)) {
	    throw new IOException("Impossibile scrivere su FS senza valorizzare il path di scrittura");
	}
	URI u;
	try {
	    u = new URI(this.uri);
	    this.percorso = u.getPath();
	    log.info("FileSystemHelper: percorso {}", this.percorso);
	} catch (URISyntaxException e) {
	    throw new IOException(e);
	}
    }

    @Override
    public void put(InputStream inStream, String nomeFile, String pathRelativo) throws IOException {

	if (inStream != null && nomeFile != null) {
	    File destFile = this.getNewFile(nomeFile, pathRelativo);
	    if (destFile == null) {
		throw new IOException(
			String.format("Impossibile scrivere il file %s nel percorso %s della root %s", nomeFile, pathRelativo, this.percorso));
	    }
	    IOUtils.copyStreamToFile(inStream, destFile);
	}
    }

    @Override
    public void put(File file, String pathRelativo) throws IOException {

	if (file != null) {
	    try (FileInputStream inStream = new FileInputStream(file)) {
		this.put(inStream, file.getName(), pathRelativo);
	    }
	}
    }

    @Override
    public void put(File sendMe) throws IOException {

	this.put(sendMe, "");
    }

    @Override
    public void mPut(Set<InputStream> inputStream, Set<String> nomeFile, String pathRelativo) throws IOException {

	if (inputStream != null && !inputStream.isEmpty()) {
	    if (nomeFile != null && !nomeFile.isEmpty()) {
		if (inputStream.size() == nomeFile.size()) {
		    List<InputStream> inputList = new ArrayList<>(inputStream);
		    List<String> nomiList = new ArrayList<>(nomeFile);
		    for (int i = 0; i < inputList.size(); i++) {
			put(inputList.get(i), nomiList.get(i), pathRelativo);
		    }
		}
	    }
	}
    }

    @Override
    public InputStream get(String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotBlank(nomeFile)) {
	    File file = this.getFile(nomeFile, pathRelativo);
	    if (file != null && file.canRead()) {
		return new FileInputStream(file);
	    } else {
		throw new IOException("Impossibile leggere il file " + nomeFile + " al path " + pathRelativo);
	    }
	}
	return null;
    }

    @Override
    public Set<InputStream> mGet(Set<String> nomeFile, String pathRelativo) throws IOException {

	if (nomeFile != null && !nomeFile.isEmpty()) {
	    Set<InputStream> outResult = new HashSet<>();
	    for (String file : nomeFile) {
		outResult.add(get(file, pathRelativo));
	    }
	    return outResult;
	}
	return null;
    }

    @Override
    public void mkdir(String pathRelativo) throws IOException {

	if (StringUtils.isNotBlank(pathRelativo)) {
	    File nuovaCartella = this.getFolder(pathRelativo);
	    if (nuovaCartella != null) {
		throw new IOException(String.format("Operazione fallita, la cartella %s che si sta creando sul percorso %s esiste già.", pathRelativo,
			this.percorso));
	    }
	    nuovaCartella = new File(this.getPath(pathRelativo));
	    nuovaCartella.mkdir();
	}
    }

    @Override
    public void sposta(String nomeFile, String inputPath, String outputPath) throws IOException {

	if (StringUtils.isNotEmpty(nomeFile) && (StringUtils.isNotEmpty(inputPath) || StringUtils.isNotEmpty(outputPath))) {
	    File origine = this.getFile(nomeFile, inputPath);
	    if (origine == null) {
		throw new IOException(String.format("Il file %s non è stato trovato nel percorso %s", nomeFile, inputPath));
	    }
	    File destinazione = this.getFolder(outputPath);
	    if (destinazione == null) {
		//se la cartella di destinazione non esiste la creo
		this.mkdir(outputPath);
		destinazione = this.getFolder(outputPath);
		//throw new IOException(String.format("Impossibile trovare la cartella %s di destinazione", outputPath));
	    }
	    Path source = Paths.get(origine.getPath());
	    Path target = Paths.get(destinazione.getPath() + File.separator + nomeFile);
	    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
	}
    }

    @Override
    public void sposta(Set<String> nomeFile, String inputPath, String outputPath) throws IOException {

	if (nomeFile != null && !nomeFile.isEmpty()) {
	    if (StringUtils.isNotBlank(inputPath) || StringUtils.isNotBlank(outputPath)) {
		for (String nome : nomeFile) {
		    sposta(nome, inputPath, outputPath);
		}
	    }
	}
    }

    @Override
    public void spostaContenutoCartella(String inputPath, String outputPath) throws IOException {

	if (StringUtils.isNotEmpty(inputPath) && StringUtils.isNotEmpty(outputPath)) {
	    File folderOrigine = this.getFolder(inputPath);
	    for (final File origine : folderOrigine.listFiles()) {
		this.sposta(origine.getName(), inputPath, outputPath);
	    }
	}
    }

    @Override
    public void delete(String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotBlank(nomeFile)) {
	    File file = this.getFile(nomeFile, pathRelativo);
	    if (file != null && file.exists()) {
		Files.delete(file.toPath());
	    }
	}
    }

    @Override
    public void rmdir(String pathRelativo) throws IOException {

	if (StringUtils.isNotBlank(pathRelativo)) {
	    File folder = this.getFolder(pathRelativo);
	    if (folder != null) {
		File[] list = folder.listFiles();
		if (list != null) {
		    for (File target : list) {
			if (target.isDirectory()) {
			    this.rmdir(target.getName());
			} else {
			    target.delete();
			}
		    }
		}
		folder.delete();
	    }
	}
    }

    @Override
    public void setProxy(String proxyHost, Integer proxyPort) throws IOException {

	//implementazione non richiesta
    }

    @Override
    public void open() throws IOException {

	//implementazione non richiesta
    }

    @Override
    public void close() throws IOException {

	//implementazione non richiesta
    }

    private File getFolder(String pathRelativo) {

	File retVal = new File(this.getPath(pathRelativo));
	return retVal.exists() && retVal.isDirectory() ? retVal : null;
    }

    private File getFile(String fileName, String pathRelativo) {

	File cartellaIniziale = this.getFolder(pathRelativo);
	if (cartellaIniziale != null) {
	    String path = cartellaIniziale.getPath() + File.separator + fileName;
	    File retVal = new File(path);
	    return retVal.exists() && !retVal.isDirectory() ? retVal : null;
	}
	return null;
    }

    private File getNewFile(String fileName, String pathRelativo) throws IOException {

	File cartellaIniziale = this.getFolder(pathRelativo);
	if (cartellaIniziale == null) {
	    throw new IOException(String.format("Il percorso passato [%s] non è stato trovato come percorso relativo [%s] per il file [%s] ",
		    pathRelativo, this.getPath(pathRelativo), fileName));
	}
	String path = cartellaIniziale.getPath() + File.separator + fileName;
	return new File(path);
    }

    private String getPath(String pathRelativo) {

	String path = this.percorso;
	if (!path.endsWith("/") && !path.endsWith("\\")) {
	    path += File.separator;
	}
	if (StringUtils.isNotBlank(pathRelativo)) {
	    String nuovoPathRelativo = pathRelativo;
	    nuovoPathRelativo = this.sistemaPartenzaPath(nuovoPathRelativo);
	    nuovoPathRelativo = this.sistemaFinalePath(nuovoPathRelativo);
	    path += nuovoPathRelativo;
	}
	log.info("getPath: path {}", path);
	return path;
    }

    private String sistemaPartenzaPath(String pathRelativo) {

	String newPath = pathRelativo;
	if (StringUtils.isNotBlank(newPath)) {
	    if (newPath.startsWith("\\")) {
		newPath = newPath.replaceFirst("\\\\", "");
	    }
	    if (pathRelativo.startsWith("/")) {
		newPath = newPath.replaceFirst("/", "");
	    }
	}
	return newPath;
    }

    private String sistemaFinalePath(String pathRelativo) {

	String newPath = pathRelativo;
	if (StringUtils.isNotBlank(newPath)) {
	    if (newPath.endsWith("\\")) {
		newPath = newPath.substring(0, newPath.length() - 1) + File.separator;
	    }
	    if (newPath.endsWith("/")) {
		newPath = newPath.substring(0, newPath.length() - 1) + File.separator;
	    }
	    if (!newPath.endsWith("/") && !newPath.endsWith("\\")) {
		newPath += File.separator;
	    }
	}
	return newPath;
    }

    @Override
    public Set<InputStream> mGetByFilter(String pattern, String pathRelativo) throws IOException {

	File cartellaIniziale = this.getFolder(pathRelativo);
	Set<InputStream> fileStreams = new HashSet<>();
	if (cartellaIniziale != null) {
	    DirectoryStream<Path> dirStream = Files.newDirectoryStream(cartellaIniziale.toPath(), pattern);
	    Iterator<Path> dirIter = dirStream.iterator();
	    while (dirIter.hasNext()) {
		Path path = (Path) dirIter.next();
		InputStream is = this.get(path.toFile().getName(), pathRelativo);
		fileStreams.add(is);
	    }
	}
	return fileStreams;
    }

    @Override
    public Set<File> mGetFilesByFilter(String pattern, String pathRelativo) throws IOException {

	File cartellaIniziale = this.getFolder(pathRelativo);
	log.info("mGetFilesByFilter: a partire dal pathrelativo {} trovo la cartella iniziale {} e verifico se presenti file con pattern {}",
		pathRelativo, cartellaIniziale, pattern);
	Set<File> files = new HashSet<>();
	if (cartellaIniziale != null) {
	    DirectoryStream<Path> dirStream = Files.newDirectoryStream(cartellaIniziale.toPath(), pattern);
	    Iterator<Path> dirIter = dirStream.iterator();
	    while (dirIter.hasNext()) {
		Path path = (Path) dirIter.next();
		log.info("mGetFilesByFilter: trovato il file {}", path.toFile());
		files.add(path.toFile());
	    }
	}
	return files;
    }
}
