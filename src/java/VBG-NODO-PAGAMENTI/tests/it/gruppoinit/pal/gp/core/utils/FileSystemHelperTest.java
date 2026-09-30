package it.gruppoinit.pal.gp.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileSystemHelperTest {

    private String tmpDir = "file:///" + System.getProperty("java.io.tmpdir").replace("\\", "/");
    private static final String TMPDIRTEMPLATE = "template/";
    private static final String FILENAME1 = "fakefile1.txt";
    private static final String FILENAME2 = "fakefile2.txt";
    private static final String ORIGINE = "origine";
    private static final String DESTINAZIONE = "destinazione";
    private static final String SPOSTATI = "spostati";
    private static final String DASPOSTARE = "da_spostare";

    @Before()
    public void SetUP() throws URISyntaxException, IOException {

	URI u = new URI(tmpDir);
	File cartella = new File(u.getPath() + TMPDIRTEMPLATE);
	if (!cartella.exists()) {
	    cartella.mkdir();
	}
	File file = new File(cartella.getPath() + File.separator + FILENAME1);
	try (FileOutputStream fos = new FileOutputStream(file)) {
	    String messaggio = "Fake input string 1";
	    fos.write(messaggio.getBytes());
	}
	File file2 = new File(cartella.getPath() + File.separator + FILENAME2);
	try (FileOutputStream fos = new FileOutputStream(file2)) {
	    String messaggio = "Fake input string 2";
	    fos.write(messaggio.getBytes());
	}
    }

    @After()
    public void TearDown() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	fsh.rmdir(TMPDIRTEMPLATE);
	fsh.rmdir(ORIGINE);
	fsh.rmdir(DESTINAZIONE);
	fsh.rmdir(SPOSTATI);
	fsh.rmdir(DASPOSTARE);
	fsh.delete(FILENAME1, "");
	fsh.delete(FILENAME2, "");
    }

    private Set<String> getFakeFileNames() {

	Set<String> retVal = new TreeSet<>();
	retVal.add(FILENAME1);
	retVal.add(FILENAME2);
	return retVal;
    }

    private FileInputStream getFakeInputStream() throws IOException, URISyntaxException {

	URI u = new URI(tmpDir);
	File cartella = new File(u.getPath() + TMPDIRTEMPLATE);
	File file = new File(cartella.getPath() + File.separator + FILENAME1);
	return new FileInputStream(file);
    }

    private FileInputStream getFakeInputStream2() throws IOException, URISyntaxException {

	URI u = new URI(tmpDir);
	File cartella = new File(u.getPath() + TMPDIRTEMPLATE);
	File file = new File(cartella.getPath() + File.separator + FILENAME2);
	return new FileInputStream(file);
    }

    private Set<InputStream> getFakeInputStreams() throws IOException, URISyntaxException {

	Set<InputStream> retVal = new HashSet<>();
	retVal.add(this.getFakeInputStream());
	retVal.add(this.getFakeInputStream2());
	return retVal;
    }

    private File getFakeFile1() throws URISyntaxException {

	URI u = new URI(tmpDir + TMPDIRTEMPLATE);
	return new File(u.getPath() + FILENAME1);
    }

    private File getFakeFile2() {

	URI u;
	try {
	    u = new URI(tmpDir + TMPDIRTEMPLATE);
	    return new File(u.getPath() + FILENAME2);
	} catch (URISyntaxException e) {
	}
	return null;
    }

    @Test(expected = IOException.class)
    public void costruttureNullTornaIOException() throws IOException {

	String msg = "Impossibile scrivere su FS senza valorizzare il path di scrittura";
	try {
	    new FileSystemHelper(null);
	} catch (IOException e) {
	    Assert.assertEquals("Il messaggio tornato è " + msg, msg, e.getMessage());
	    throw e;
	}
    }

    @Test
    public void costruttureConTempDirValorizzatoNonGeneraErrori() throws IOException {

	new FileSystemHelper(tmpDir);
	Assert.assertTrue("Nessun errore di inizializzazione", true);
    }

    @Test(expected = IOException.class)
    public void costruttureEstesoNullTornaIOException() throws IOException {

	String msg = "Impossibile scrivere su FS senza valorizzare il path di scrittura";
	try {
	    new FileSystemHelper(null, null, null);
	} catch (IOException e) {
	    Assert.assertEquals("Il messaggio tornato è " + msg, msg, e.getMessage());
	    throw e;
	}
    }

    @Test()
    public void costruttureEstesoValorizzatoNonGeneraErrori() throws IOException {

	new FileSystemHelper(tmpDir, null, null);
	Assert.assertTrue("Nessun errore di inizializzazione", true);
    }

    @Test()
    public void putConInputStreamNullNonSchianta() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	InputStream inStream = null;
	String nomeFile = "pippo.txt";
	String pathRelativo = null;
	fsh.put(inStream, nomeFile, pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata put con inputstream null", true);
    }

    @Test()
    public void putConNomeFileNullNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	try (InputStream inStream = this.getFakeInputStream()) {
	    String nomeFile = null;
	    String pathRelativo = null;
	    fsh.put(inStream, nomeFile, pathRelativo);
	    Assert.assertTrue("Nessun errore nella chiamata put con nomefile null", true);
	}
    }

    @Test()
    public void putConPathRelativoNullNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String nomeFile = FILENAME1;
	String pathRelativo = null;
	try (InputStream inStream = this.getFakeInputStream()) {
	    fsh.put(inStream, nomeFile, pathRelativo);
	    Assert.assertTrue("Nessun errore nella chiamata put con inputstream null", true);
	}
    }

    @Test()
    public void putConFileNullNonSchianta() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	File file = null;
	String pathRelativo = null;
	fsh.put(file, pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata put con File null", true);
    }

    @Test()
    public void putConFileBuonoEPathRelativoNullNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	File file = this.getFakeFile2();
	String pathRelativo = "";
	fsh.put(file, pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata put con pathRelativo null", true);
    }

    @Test()
    public void mputConElenchiNullNonSchianta() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = "";
	Set<InputStream> inputStream = null;
	Set<String> nomeFile = null;
	fsh.mPut(inputStream, nomeFile, pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata mput con inputstream e nomeFile null", true);
    }

    @Test()
    public void mputConInputStreamNullNonSchianta() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = "";
	Set<InputStream> inputStream = null;
	Set<String> nomeFile = this.getFakeFileNames();
	fsh.mPut(inputStream, nomeFile, pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata mput con inputstream null", true);
    }

    @Test()
    public void mputConNomeFileNullNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = "";
	Set<InputStream> inputStream = this.getFakeInputStreams();
	Set<String> nomeFile = null;
	fsh.mPut(inputStream, nomeFile, pathRelativo);
	for (Iterator<InputStream> i = inputStream.iterator(); i.hasNext();) {
	    i.next().close();
	}
	Assert.assertTrue("Nessun errore nella chiamata mput con nomeFile null", true);
    }

    @Test()
    public void mputValorizzatoCorrettamenteNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = "";
	Set<InputStream> inputStream = this.getFakeInputStreams();
	Set<String> nomeFile = this.getFakeFileNames();
	fsh.mPut(inputStream, nomeFile, pathRelativo);
	for (Iterator<InputStream> i = inputStream.iterator(); i.hasNext();) {
	    i.next().close();
	}
	Assert.assertTrue("Nessun errore nella chiamata mput valorizzata correttamente", true);
    }

    @Test()
    public void getConValoriNullNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = "";
	try (InputStream inStream = this.getFakeInputStream()) {
	    fsh.put(inStream, FILENAME1, pathRelativo);
	    fsh.get(null, null);
	}
	Assert.assertTrue("Nessun errore nella chiamata get con nomeFile e pathRelativo null", true);
    }

    @Test()
    public void getConNomeFileEsistenteTornaIlFile() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = "";
	try (FileInputStream inStream1 = this.getFakeInputStream()) {
	    fsh.put(inStream1, FILENAME1, pathRelativo);
	}
	fsh.get(FILENAME1, pathRelativo).close();
	File file1 = this.getFakeFile1();
	URI u = new URI(tmpDir + pathRelativo);
	File file2 = new File(u.getPath() + FILENAME1);
	Assert.assertTrue("La chiamata corretta a get torna il file aspettato", FileUtils.contentEquals(file1, file2));
    }

    @Test()
    public void mgetConValoriNullNonSchianta() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	Set<String> nomeFile = null;
	String pathRelativo = null;
	fsh.mGet(nomeFile, pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata mGet con nomeFile e pathRelativo null", true);
    }

    @Test()
    public void mgetConSetNomeFileTornaDueElementiRichiesti() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	Set<String> nomeFile = this.getFakeFileNames();
	String pathRelativo = null;
	Set<InputStream> inStreams = this.getFakeInputStreams();
	fsh.mPut(inStreams, this.getFakeFileNames(), pathRelativo);
	for (Iterator<InputStream> i = inStreams.iterator(); i.hasNext();) {
	    i.next().close();
	}
	Set<InputStream> outStreams = fsh.mGet(nomeFile, pathRelativo);
	Integer size = outStreams.size();
	Integer expexted = 2;
	for (Iterator<InputStream> i = outStreams.iterator(); i.hasNext();) {
	    i.next().close();
	}
	Assert.assertEquals("Ritornato un set con due elementi", expexted, size);
    }

    @Test()
    public void spostaConValoriNullNonSchianta() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String nomeFile = null;
	String inputPath = null;
	String outputPath = null;
	fsh.sposta(nomeFile, inputPath, outputPath);
	Assert.assertTrue("Nessun errore nella chiamata move con tutti i parametri null", true);
    }

    @Test()
    public void spostaConNomeFileNullNonSchianta() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String nomeFile = null;
	fsh.sposta(nomeFile, ORIGINE, DESTINAZIONE);
	Assert.assertTrue("Nessun errore nella chiamata move con nomeFile null", true);
    }

    @Test()
    public void rmdirConParametriNullNonSchianta() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = null;
	fsh.rmdir(pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata rmdir con pathRelativo null", true);
    }

    @Test()
    public void rmdirConCartellaInesistenteNonGeneraErrore() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = "asddadasd";
	fsh.rmdir(pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata rmdir con pathinesistente null", true);
    }

    @Test()
    public void mkdirConParametriNullNonTornaErrore() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String pathRelativo = null;
	fsh.mkdir(pathRelativo);
	Assert.assertTrue("Nessun errore nella chiamata mkdir con pathRelativo null", true);
    }

    @Test()
    public void mkdirCreaLaCartellaRichiesta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	fsh.mkdir(ORIGINE);
	Path path = Paths.get(new URI(tmpDir + ORIGINE));
	Assert.assertTrue(String.format("Nessun errore nella chiamata mkdir, la cartella {} è stata creata", ORIGINE),
		Files.exists(path, LinkOption.NOFOLLOW_LINKS));
    }

    @Test(expected = IOException.class)
    public void spostaUnFileCheNonEsisteTornaIOException() throws IOException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String nomeFile = FILENAME1;
	String msg = String.format("Il file {} non è stato trovato nel percorso {}", nomeFile, ORIGINE);
	fsh.sposta(nomeFile, ORIGINE, DESTINAZIONE);
    }

    @Test()
    public void spostaUnFileCheEsisteNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	String nomeFile = FILENAME2;
	fsh.mkdir(ORIGINE);
	fsh.mkdir(DESTINAZIONE);
	fsh.put(this.getFakeFile2(), ORIGINE);
	fsh.sposta(nomeFile, ORIGINE, DESTINAZIONE);
	Assert.assertTrue("Chiamata a sposta di un file esistente terminata correttamente", true);
    }

    @Test()
    public void spostaPiuFileInsiemeNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	Set<String> nomeFile = new HashSet<>();
	String inputPath = null;
	fsh.mkdir(SPOSTATI);
	fsh.put(this.getFakeFile1(), "");
	fsh.put(this.getFakeFile2(), "");
	nomeFile.add(FILENAME1);
	nomeFile.add(FILENAME2);
	fsh.sposta(nomeFile, inputPath, SPOSTATI);
	Assert.assertTrue("Il file sono stati spostati con successo", true);
    }

    @Test()
    public void spostaContenutoCartellaEsistenteNonSchianta() throws IOException, URISyntaxException {

	FileSystemHelper fsh = new FileSystemHelper(tmpDir);
	Set<String> nomeFile = new HashSet<>();
	fsh.mkdir(DASPOSTARE);
	fsh.mkdir(SPOSTATI);
	fsh.put(this.getFakeFile1(), DASPOSTARE);
	fsh.put(this.getFakeFile2(), DASPOSTARE);
	nomeFile.add(FILENAME1);
	nomeFile.add(FILENAME2);
	fsh.sposta(nomeFile, DASPOSTARE, SPOSTATI);
	Assert.assertTrue("Il file sono stati spostati con successo", true);
    }
}
