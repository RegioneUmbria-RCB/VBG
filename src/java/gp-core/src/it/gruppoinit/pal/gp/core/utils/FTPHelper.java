package it.gruppoinit.pal.gp.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.AccountFtp;

public class FTPHelper implements IFileHelper {

    private boolean gestisciConnessione;
    private String userName;
    private String password;
    private String host;
    private Integer port;
    private FTPClient client;
    private String proxyHost;
    private Integer proxyPort;
    private String pathRelativo;
    private static final Integer CONNECTION_TIMEOUT = 2400000;
    private static final Logger log = LoggerFactory.getLogger(FTPHelper.class);

    public FTPHelper(AccountFtp accountFTP) throws MalformedURLException {

	if (accountFTP == null) {
	    throw new IllegalArgumentException("Impossibile invocare il costruttore FTPHelper(AccountFtp accountFTP) se accountFTP è null");
	}
	this.verificheIniziali(accountFTP.getIndirizzo(), accountFTP.getUtente(), accountFTP.getPassword());
	this.gestisciConnessione = true;
	URL risorsaURL = new URL(accountFTP.getIndirizzo());
	this.host = risorsaURL.getHost();
	if (risorsaURL.getPort() > 0) {
	    this.port = risorsaURL.getPort();
	}
	this.pathRelativo = this.extractPathRelativo(risorsaURL);
	this.userName = accountFTP.getUtente();
	this.password = accountFTP.getPassword();
    }

    public FTPHelper(Boolean gestisciConnessione, String url, String userName, String password) throws MalformedURLException {

	this.verificheIniziali(url, userName, password);
	this.gestisciConnessione = gestisciConnessione;
	URL risorsaURL = new URL(url);
	this.host = risorsaURL.getHost();
	if (risorsaURL.getPort() > 0) {
	    this.port = risorsaURL.getPort();
	}
	this.pathRelativo = this.extractPathRelativo(risorsaURL);
	this.userName = userName;
	this.password = password;
    }

    public FTPHelper(String url, String userName, String password) throws MalformedURLException {

	this.verificheIniziali(url, userName, password);
	this.gestisciConnessione = true;
	URL risorsaURL = new URL(url);
	this.host = risorsaURL.getHost();
	if (risorsaURL.getPort() > 0) {
	    this.port = risorsaURL.getPort();
	}
	this.pathRelativo = this.extractPathRelativo(risorsaURL);
	this.userName = userName;
	this.password = password;
    }

    private String extractPathRelativo(URL url) {

	if (url == null) {
	    return null;
	}
	String retVal = url.getPath();
	if (retVal.startsWith("/")) {
	    retVal = retVal.substring(1);
	}
	if (retVal.endsWith("/")) {
	    retVal = retVal.substring(0, retVal.length() - 1);
	}
	return retVal;
    }

    private void verificheIniziali(String url, String userName, String password) throws MalformedURLException {

	if (StringUtils.isEmpty(url)) {
	    throw new MalformedURLException("Impossibile scrivere su FTP senza valorizzare la url");
	}
	if (StringUtils.isEmpty(userName)) {
	    throw new MalformedURLException("Impossibile scrivere su FTP senza valorizzare l'utente");
	}
	if (StringUtils.isEmpty(password)) {
	    throw new MalformedURLException("Impossibile scrivere su FTP senza valorizzare la password");
	}
    }

    @Override
    public void put(InputStream inStream, String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotEmpty(nomeFile) && inStream != null) {
	    internalOpen();
	    if (StringUtils.isNotEmpty(pathRelativo)) {
		client.changeWorkingDirectory(pathRelativo);
	    }
	    client.setFileType(FTP.BINARY_FILE_TYPE);
	    if (!client.storeFile(nomeFile, inStream)) {
		throw new IOException("Impossibile scrivere il file " + nomeFile + " nel percorso " + pathRelativo);
	    }
	    internalClose();
	}
    }

    @Override
    public void put(File file, String pathRelativo) throws IOException {

	if (file != null) {
	    String nomeFile = file.getName();
	    InputStream inStream = new FileInputStream(file);
	    put(inStream, nomeFile, pathRelativo);
	    inStream.close();
	}
    }

    @Override
    public void mPut(Set<InputStream> inputStream, Set<String> nomeFile, String pathRelativo) throws IOException {

	if (inputStream != null && !inputStream.isEmpty()) {
	    if (nomeFile != null && !nomeFile.isEmpty()) {
		if (inputStream.size() == nomeFile.size()) {
		    List<InputStream> inputList = new ArrayList<InputStream>(inputStream);
		    List<String> nomiList = new ArrayList<String>(nomeFile);
		    for (int i = 0; i < inputList.size(); i++) {
			put(inputList.get(i), nomiList.get(i), pathRelativo);
		    }
		}
	    }
	}
    }

    @Override
    public InputStream get(String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotEmpty(nomeFile)) {
	    internalOpen();
	    if (StringUtils.isNotEmpty(pathRelativo)) {
		client.changeWorkingDirectory(pathRelativo);
	    }
	    client.setFileType(FTP.BINARY_FILE_TYPE);
	    String tmpFileName = System.getProperty("java.io.tmpdir") + nomeFile;
	    FileOutputStream out = new FileOutputStream(tmpFileName);
	    if (!client.retrieveFile(nomeFile, out)) {
		throw new IOException("Impossibile scaricare il file " + nomeFile + " dal percorso " + pathRelativo);
	    }
	    out.close();
	    internalClose();
	    FileInputStream fis = new FileInputStream(tmpFileName);
	    new File(tmpFileName).delete();
	    return fis;
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
	return null;
    }

    @Override
    public void sposta(String nomeFile, String inputPath, String outputPath) throws IOException {

	if (StringUtils.isNotEmpty(nomeFile) && StringUtils.isNotEmpty(inputPath) && StringUtils.isNotEmpty(outputPath)) {
	    internalOpen();
	    String from = StringUtils.isNotBlank(inputPath) ? inputPath + File.separator + nomeFile : nomeFile;
	    FTPFile[] origine = client.listFiles(from);
	    if (origine == null || origine.length == 0) {
		throw new IOException("Il file " + nomeFile + " non è stato trovato nel percorso " + inputPath);
	    }
	    String to = outputPath + File.separator + nomeFile;
	    if (!client.rename(from, to)) {
		throw new IOException("Impossibile copiare il file " + nomeFile + " da " + inputPath + " a " + outputPath);
	    }
	    internalClose();
	}
    }

    @Override
    public void sposta(Set<String> nomeFile, String inputPath, String outputPath) throws IOException {

	if (nomeFile != null && !nomeFile.isEmpty()) {
	    if (StringUtils.isNotEmpty(outputPath)) {
		for (String nome : nomeFile) {
		    sposta(nome, inputPath, outputPath);
		}
	    }
	}
    }

    @Override
    public void spostaContenutoCartella(String inputPath, String outputPath) throws IOException {

	if (StringUtils.isNotEmpty(inputPath) && StringUtils.isNotEmpty(outputPath)) {
	    internalOpen();
	    Set<String> nomiFile = getContenutoCartella(inputPath);
	    sposta(nomiFile, inputPath, outputPath);
	    internalClose();
	}
    }

    private Set<String> getContenutoCartella(String inputPath) throws IOException {

	internalOpen();
	FTPFile[] files = client.listFiles(inputPath);
	Set<String> outFiles = new HashSet<String>();
	for (FTPFile file : files) {
	    outFiles.add(file.getName());
	}
	internalClose();
	return outFiles;
    }

    @Override
    public void delete(String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotEmpty(nomeFile)) {
	    internalOpen();
	    String pathFile = StringUtils.isNotBlank(pathRelativo) ? pathRelativo + nomeFile : nomeFile;
	    if (!client.deleteFile(pathFile)) {
		throw new IOException("Impossibile cancellare il file " + nomeFile + " dal percorso " + pathFile);
	    }
	    internalClose();
	}
    }

    @Override
    public void rmdir(String pathRelativo) throws IOException {

	if (StringUtils.isNotEmpty(pathRelativo)) {
	    internalOpen();
	    String parentDir = client.printWorkingDirectory();
	    String currentDir = pathRelativo;
	    this.rmDir(false, parentDir, currentDir);
	    internalClose();
	}
    }

    private void rmDir(Boolean gestConnessione, String parentDir, String currentDir) throws IOException {

	String dirToList = parentDir;
	if (!currentDir.equals("")) {
	    dirToList += "/" + currentDir;
	}
	Boolean deleted = false;
	if (gestConnessione) {
	    internalOpen();
	}
	FTPFile[] subFiles = client.listFiles(dirToList);
	if (subFiles != null && subFiles.length > 0) {
	    for (FTPFile aFile : subFiles) {
		String currentFileName = aFile.getName();
		if (currentFileName.equals(".") || currentFileName.equals("..")) {
		    // skip parent directory and the directory itself
		    continue;
		}
		String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
		if (currentDir.equals("")) {
		    filePath = parentDir + "/" + currentFileName;
		}
		if (aFile.isDirectory()) {
		    // remove the sub directory
		    this.rmDir(gestConnessione, dirToList, currentFileName);
		} else {
		    deleted = client.deleteFile(filePath);
		    if (!deleted) {
			String msg = "Impossibile cancellare la directory " +
				currentDir +
				" in quanto non è possibile cancellare il file " +
				currentFileName +
				" in esso contenuto";
			throw new IOException(msg);
		    }
		}
	    }
	}
	deleted = client.removeDirectory(dirToList);
	if (!deleted) {
	    String msg = "Impossibile cancellare la directory " + currentDir;
	    throw new IOException(msg);
	}
	if (gestConnessione) {
	    internalClose();
	}
    }

    @Override
    public void mkdir(String pathRelativo) throws IOException {

	if (StringUtils.isNotBlank(pathRelativo)) {
	    internalOpen();
	    client.mkd(pathRelativo);
	    internalClose();
	}
    }

    private void internalOpen() throws IOException {

	if (gestisciConnessione) {
	    open();
	}
    }

    private void internalClose() throws IOException {

	if (gestisciConnessione) {
	    close();
	}
    }

    @Override
    public void setProxy(String proxyHost, Integer proxyPort) {

	this.proxyHost = proxyHost;
	this.proxyPort = proxyPort;
    }

    private boolean usaProxy() {

	return !StringUtils.isEmpty(proxyHost);
    }

    @Override
    public void open() throws IOException {

	if (client == null) {
	    if (usaProxy()) {
		client = new FTPHTTPClient(proxyHost, proxyPort);
	    } else {
		client = new FTPClient();
	    }
	    client.enterLocalPassiveMode();
	    client.setConnectTimeout(CONNECTION_TIMEOUT);
	}
	if (port != null) {
	    client.connect(host, port);
	} else {
	    client.connect(host);
	}
	if (!client.login(userName, password)) {
	    throw new IOException("Impossibile effettuare la login all'indirizzo FTP " + host);
	}
	if (StringUtils.isNotBlank(pathRelativo) && !client.changeWorkingDirectory(pathRelativo)) {
	    throw new IOException("Impossibile impostare la directory FTP di lavoro su " + pathRelativo);
	}
    }

    @Override
    public void close() throws IOException {

	if (client != null) {
	    client.logout();
	    client.disconnect();
	}
    }
}
