package it.gruppoinit.pal.gp.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface IFileHelper {

    public void put(InputStream inStream, String nomeFile, String pathRelativo) throws IOException;

    public void put(File file, String pathRelativo) throws IOException;

    public void mPut(Set<InputStream> inputStream, Set<String> nomeFile, String pathRelativo) throws IOException;

    public InputStream get(String nomeFile, String pathRelativo) throws IOException;

    public Set<InputStream> mGet(Set<String> nomeFile, String pathRelativo) throws IOException;

    public void sposta(String nomeFile, String inputPath, String outputPath) throws IOException;

    public void sposta(Set<String> nomeFile, String inputPath, String outputPath) throws IOException;

    public void spostaContenutoCartella(String inputPath, String outputPath) throws IOException;

    public void delete(String nomeFile, String pathRelativo) throws IOException;

    public void rmdir(String pathRelativo) throws IOException;

    public void mkdir(String pathRelativo) throws IOException;

    public void setProxy(String proxyHost, Integer proxyPort) throws IOException;

    public void open() throws IOException;

    public void close() throws IOException;
}
