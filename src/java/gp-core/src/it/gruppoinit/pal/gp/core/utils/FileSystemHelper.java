package it.gruppoinit.pal.gp.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class FileSystemHelper implements IFileHelper {

    private String percorso;
    private String uri;

    public FileSystemHelper(String uri) throws IOException {

	this.uri = uri;
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
	} catch (URISyntaxException e) {
	    throw new IOException(e);
	}
    }

    @Override
    public void put(InputStream inStream, String nomeFile, String pathRelativo) throws IOException {

	if (inStream == null || nomeFile == null) {
	    return;
	}
	File destFile = this.getNewFile(nomeFile, pathRelativo);
	FileOutputStream os = null;
	try {
	    os = new FileOutputStream(destFile);
	    int read = 0;
	    byte[] bytes = new byte[1024];
	    while ((read = inStream.read(bytes)) != -1) {
		os.write(bytes, 0, read);
	    }
	} finally {
	    if (os != null) {
		os.close();
	    }
	}
    }

    @Override
    public void put(File file, String pathRelativo) throws IOException {

	if (file != null) {
	    FileInputStream inStream = new FileInputStream(file);
	    this.put(inStream, file.getName(), pathRelativo);
	    inStream.close();
	}
    }

    @Override
    public void mPut(Set<InputStream> inputStream, Set<String> nomeFile, String pathRelativo) throws IOException {

	if (inputStream == null || inputStream.isEmpty()) {
	    return;
	}
	if (nomeFile == null || nomeFile.isEmpty()) {
	    return;
	}
	if (inputStream.size() != nomeFile.size()) {
	    return;
	}
	List<InputStream> inputList = new ArrayList<InputStream>(inputStream);
	List<String> nomiList = new ArrayList<String>(nomeFile);
	for (int i = 0; i < inputList.size(); i++) {
	    put(inputList.get(i), nomiList.get(i), pathRelativo);
	}
    }

    @Override
    public InputStream get(String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotBlank(nomeFile)) {
	    File file = this.getFile(nomeFile, pathRelativo);
	    if (file != null) {
		return new FileInputStream(file);
	    }
	}
	return null;
    }

    @Override
    public Set<InputStream> mGet(Set<String> nomeFile, String pathRelativo) throws IOException {

	if (nomeFile != null && !nomeFile.isEmpty()) {
	    Set<InputStream> outResult = new HashSet<InputStream>();
	    for (String file : nomeFile) {
		outResult.add(get(file, pathRelativo));
	    }
	    return outResult;
	}
	return new HashSet<InputStream>(0);
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
		throw new IOException(String.format("Impossibile trovare la cartella %s di destinazione", outputPath));
	    }
	    File fileDest = new File(destinazione + File.separator + origine.getName());
	    origine.renameTo(fileDest);
	}
    }

    @Override
    public void sposta(Set<String> nomeFile, String inputPath, String outputPath) throws IOException {

	if (nomeFile == null || nomeFile.isEmpty()) {
	    return;
	}
	if (StringUtils.isBlank(inputPath) && StringUtils.isBlank(outputPath)) {
	    return;
	}
	for (String nome : nomeFile) {
	    sposta(nome, inputPath, outputPath);
	}
    }

    @Override
    public void spostaContenutoCartella(String inputPath, String outputPath) throws IOException {

	if (StringUtils.isEmpty(inputPath) && StringUtils.isEmpty(outputPath)) {
	    return;
	}
	File folderOrigine = this.getFolder(inputPath);
	if (folderOrigine == null) {
	    return;
	}
	for (final File origine : folderOrigine.listFiles()) {
	    this.sposta(origine.getName(), inputPath, outputPath);
	}
    }

    @Override
    public void delete(String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotBlank(nomeFile)) {
	    File file = this.getFile(nomeFile, pathRelativo);
	    if (file != null && file.exists()) {
		file.delete();
	    }
	}
    }

    @Override
    public void rmdir(String pathRelativo) throws IOException {

	if (StringUtils.isBlank(pathRelativo)) {
	    return;
	}
	File folder = this.getFolder(pathRelativo);
	if (folder == null) {
	    return;
	}
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
	    throw new IOException(String.format("Si sta cercando di creare il file %s in un percorso che non esiste %s ", fileName, pathRelativo));
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
}
