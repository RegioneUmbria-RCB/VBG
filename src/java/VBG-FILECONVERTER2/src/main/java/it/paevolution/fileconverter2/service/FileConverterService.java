package it.paevolution.fileconverter2.service;

import java.io.InputStream;

import it.gruppoinit.fileconverter.ConvertResponse;
import it.paevolution.fileconverter2.service.BaseService.ConversionsSupportedEnum;
import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;

public interface FileConverterService {

    /**
     * Effettua una conversione a partire da un array di bytes
     * 
     * @param binaryData
     * @param tipoFile
     * @param tipoConversione
     * @return
     */
    public ConvertResponse convertBinaryContent(byte[] binaryData, FileInTypesEnum tipoFile, ConversionsSupportedEnum tipoConversione);

    /**
     * Effettua una conversione a partire da una stringa che rappresenta il documento (RTF, TXT, HTML, ECC...)
     * 
     * @param content
     * @param contentType
     * @param conversionType
     * @return
     */
    public ConvertResponse convertStringContent(String content, FileInTypesEnum contentType, ConversionsSupportedEnum conversionType);

    /**
     * Effettua una conversione a partire da una pagina html in pdf
     * 
     * @param in
     * @param nomeFile
     * @param contentType
     * 
     * @return
     */
    public ConvertResponse convertToObjectUsingGotenberg(InputStream file, FileInTypesEnum contentType, String outputExtension);
}
