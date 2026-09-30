package it.gruppoinit.pal.gp.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.ChannelSftp.LsEntrySelector;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTPHelper implements IFileHelper {

    private static final Logger log = LoggerFactory.getLogger(SFTPHelper.class);
    private ParamsSFTP parametri;
    private JSch jsch = null;
    private ChannelSftp channelSftp = null;
    private Session jschSession = null;
    private static final String SFTP_CHANNEL_TYPE = "sftp";
    private String proxyHost;
    private Integer proxyPort;
    private int timeout = 120000;

    public SFTPHelper(ParamsSFTP parametri) {

	this.parametri = parametri;
	this.jsch = new JSch();
    }

    public static void main(String[] args) throws IOException {

	ParamsSFTP p = new ParamsSFTP("", "", "", 22, false);
	SFTPHelper sftp = new SFTPHelper(p);
	sftp.open();
	Set<File> files = sftp.mGetFilesByFilter(".xml", "/07502350965/upload");
	for (File file : files) {
	    String name = file.getName();
	    System.out.println(name);
	    System.out.println(file.getParent());
	    System.out.println(sftp.lpwd());
	    InputStream inputStream = sftp.get(name, "");
	    String nomeFileScaricato = "tracciato-" + System.currentTimeMillis() + ".xml";
	    File fScaricato = new File("C:/temp/pes", nomeFileScaricato);
	    FileOutputStream fos = new FileOutputStream(fScaricato);
	    IOUtils.copy(inputStream, fos);
	    fos.close();
	}
	sftp.close();
    }

    @Override
    public void put(InputStream inStream, String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotEmpty(nomeFile) && inStream != null) {
	    internalOpen();
	    try {
		channelSftp.put(inStream, pathRelativo + nomeFile);
	    } catch (SftpException e) {
		throw new IOException("Impossibile scrivere il file " + nomeFile + " nel percorso " + pathRelativo, e);
	    }
	    internalClose();
	}
    }

    public String lpwd() throws IOException {

	internalOpen();
	try {
	    return channelSftp.lpwd();
	} catch (Exception e) {
	    throw new IOException(e);
	} finally {
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
    public void put(File sendMe) throws IOException {

	this.put(sendMe, "");
    }

    @Override
    public void mPut(Set<InputStream> inputStream, Set<String> nomeFile, String pathRelativo) throws IOException {

	if (inputStream != null && !inputStream.isEmpty() && (nomeFile != null && !nomeFile.isEmpty()) && (inputStream.size() == nomeFile.size())) {
	    List<InputStream> inputList = new ArrayList<>(inputStream);
	    List<String> nomiList = new ArrayList<>(nomeFile);
	    for (int i = 0; i < inputList.size(); i++) {
		put(inputList.get(i), nomiList.get(i), pathRelativo);
	    }
	}
    }

    @Override
    public InputStream get(String nomeFile, String pathRelativo) throws IOException {

	if (StringUtils.isNotEmpty(nomeFile)) {
	    internalOpen();
	    try {
		InputStream is = channelSftp.get(pathRelativo + nomeFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copyLarge(is, baos);
		return new ByteArrayInputStream(baos.toByteArray());
	    } catch (SftpException e) {
		throw new IOException("Impossibile leggere il file " + nomeFile + " nel percorso " + pathRelativo, e);
	    } finally {
		internalClose();
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
	return new HashSet<>(0);
    }

    @Override
    public Set<InputStream> mGetByFilter(String pattern, String pathRelativo) throws IOException {

	throw new UnsupportedOperationException("Funzionalità di ricerca dei files non implementata su SFTP");
    }

    /**
     * Attenzione!! la procedura ritorna i nomi dei file che è possibile ricercare sul sistema esterno. Il percorso non
     * va preso per scaricare l'eventuale file
     */
    @Override
    public Set<File> mGetFilesByFilter(String pattern, String pathRelativo) throws IOException {

	internalOpen();
	Set<File> ret = new HashSet<>();
	try {
	    List<LsEntry> ls = listEntries(pathRelativo, pattern);
	    for (LsEntry lsEntry : ls) {
		ret.add(new File(pathRelativo, lsEntry.getFilename()));
	    }
	    return ret;
	} catch (SftpException e) {
	    throw new IOException(e);
	} finally {
	    internalClose();
	}
    }

    private List<LsEntry> listEntries(String cartella, final String pattern) throws SftpException {

	final List<LsEntry> result = new ArrayList<>();
	LsEntrySelector selector = new LsEntrySelector() {

	    public int select(LsEntry entry) {

		if (entry.getFilename().toLowerCase().endsWith(pattern)) {
		    result.add(entry);
		}
		return CONTINUE;
	    }
	};
	channelSftp.ls(cartella, selector);
	return result;
    }

    @Override
    public void sposta(String nomeFile, String inputPath, String outputPath) throws IOException {

	if (StringUtils.isNotEmpty(nomeFile) && StringUtils.isNotEmpty(outputPath)) {
	    internalOpen();
	    String from = StringUtils.isNotBlank(inputPath) ? inputPath + nomeFile : nomeFile;
	    try {
		SftpATTRS attrs = channelSftp.stat(from);
		if (attrs.getSize() == 0) {
		    throw new IOException("il file " + from + " ha contenuto 0 bytes");
		}
		String to = outputPath + "/" + nomeFile;
		channelSftp.rename(from, to);
	    } catch (SftpException e) {
		throw new IOException("Impossibile leggere il file " + nomeFile + " nel percorso " + outputPath, e);
	    } finally {
		internalClose();
	    }
	}
    }

    @Override
    public void sposta(Set<String> nomeFile, String inputPath, String outputPath) throws IOException {

	if (nomeFile != null && !nomeFile.isEmpty() && (StringUtils.isNotEmpty(outputPath))) {
	    for (String nome : nomeFile) {
		sposta(nome, inputPath, outputPath);
	    }
	}
    }

    @Override
    public void spostaContenutoCartella(String inputPath, String outputPath) throws IOException {

	throw new UnsupportedOperationException("Funzionalità di spostaContenutoCartella dei files non implementata su SFTP");
    }

    @Override
    public void delete(String nomeFile, String pathRelativo) throws IOException {

	internalOpen();
	String file = StringUtils.isNotBlank(pathRelativo) ? pathRelativo + nomeFile : nomeFile;
	try {
	    channelSftp.rm(file);
	} catch (SftpException e) {
	    throw new IOException("Impossibile eliminare il file " + file, e);
	} finally {
	    internalClose();
	}
    }

    @Override
    public void rmdir(String pathRelativo) throws IOException {

	internalOpen();
	try {
	    channelSftp.rmdir(pathRelativo);
	} catch (SftpException e) {
	    throw new IOException("Impossibile eliminare la cartella " + pathRelativo, e);
	} finally {
	    internalClose();
	}
    }

    @Override
    public void mkdir(String cartella) throws IOException {

	internalOpen();
	SftpATTRS attrs = null;
	try {
	    attrs = channelSftp.stat(cartella);
	} catch (SftpException e) {
	    log.warn("La Directory " + cartella + " esiste ", e);
	}
	try {
	    if (attrs == null) {
		channelSftp.mkdir(cartella);
		return;
	    }
	    if (!attrs.isDir()) {
		throw new IOException("Il percorso " + cartella + " non è una directory");
	    }
	} catch (Exception e) {
	    throw new IOException("Impossibile creare la cartella " + cartella, e);
	} finally {
	    internalClose();
	}
    }

    @Override
    public void setProxy(String proxyHost, Integer proxyPort) throws IOException {

	this.proxyHost = proxyHost;
	this.proxyPort = proxyPort;
    }

    @Override
    public void open() throws IOException {

	try {
	    // jsch.setKnownHosts("c:/temp/pes/known_hosts");
	    jschSession = jsch.getSession(parametri.getUsername(), parametri.getRemoteHost(), parametri.getRemotePort());
	    jschSession.setPassword(parametri.getPassword());
	    Properties properties = new Properties();
	    properties.setProperty("StrictHostKeyChecking", "no");
	    jschSession.setConfig(properties);
	    jschSession.setTimeout(timeout);
	    jschSession.connect();
	    if (usaProxy()) {
		jschSession.setProxy(new ProxyHTTP(proxyHost, proxyPort));
	    }
	    this.channelSftp = (ChannelSftp) jschSession.openChannel(SFTP_CHANNEL_TYPE);
	    this.channelSftp.connect(timeout);
	    if (!this.channelSftp.isConnected()) {
		channelSftp.exit();
		throw new IOException("Non è stato possibile connetersi al sito SFTP " + parametri.getRemoteHost());
	    }
	} catch (JSchException e) {
	    throw new IOException(e);
	}
    }

    @Override
    public void close() throws IOException {

	channelSftp.exit();
	jschSession.disconnect();
    }

    private void internalClose() throws IOException {

	if (parametri.isGestisciConnessione()) {
	    close();
	}
    }

    private void internalOpen() throws IOException {

	if (parametri.isGestisciConnessione()) {
	    open();
	}
    }

    private boolean usaProxy() {

	return !StringUtils.isEmpty(proxyHost);
    }
}
