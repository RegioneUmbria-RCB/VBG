package com.seda.payer.ext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.seda.payer.ext.util.Messages;
import com.seda.payer.ext.util.SedaExtException;
import com.seda.payer.ext.util.Utilities;

public class CoreCS {

	
	
	protected String creaBuffer(String bufferDati, String encryptIV, String encryptKey, String codicePortale, Logger logger) throws SedaExtException
	{
		String buffer = null;
		
		try {
			/*TripleDESChryptoService cryptoService = new TripleDESChryptoService();
			cryptoService.setIv(encryptIV);
			cryptoService.setKeyValue(encryptKey);*/
			
			String sTagOrario = Utilities.getTagOrario();
			String hash = Utilities.getMD5Hash(encryptIV + bufferDati + encryptKey + sTagOrario);
			String bufferDatiCrypt = new String(org.apache.commons.codec.binary.Base64.encodeBase64(bufferDati.getBytes()));
			// String bufferDatiCrypt = Base64.encode(bufferDati.getBytes()); //URLEncoder.encode(cryptoService.encryptBASE64(bufferDati), "UTF-8");
			
			/*cryptoService.destroy();
			cryptoService = null;*/
			
			buffer = "<Buffer>" +
	        	"<TagOrario>" + sTagOrario + "</TagOrario>" + 
	        	"<CodicePortale>" + codicePortale + "</CodicePortale>" +
	        	"<BufferDati>" + bufferDatiCrypt + "</BufferDati>" + 
	        	"<Hash>" + hash + "</Hash>" +
	    	"</Buffer>";
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SedaExtException(Messages.HASH_CREATION_ERROR.format(), e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new SedaExtException(Messages.HASH_CREATION_ERROR.format(), e);
		} 

		logger.info(Messages.BUFFER_CREATED.format(buffer));
        return buffer;
	}
	
	protected String decodeBuffer(String buffer, int window_minutes, String encryptIV, String encryptKey, Logger logger) throws SedaExtException
	{
		try
		{
			//verifica dati buffer
	        Document doc = Utilities.getXmlDocumentFromString(buffer);
	        String sTagOrario = Utilities.getElementValue("/Buffer/TagOrario", doc);
	        String sBufferDatiCrypt = Utilities.getElementValue("/Buffer/BufferDati", doc);
	        String sHashRicevuto = Utilities.getElementValue("/Buffer/Hash", doc);
	        
	        if (sTagOrario.equals(""))
				throw new SedaExtException(Messages.ERROR_XML_NODE.format("TagOrario"));
			if (sBufferDatiCrypt.equals(""))
				throw new SedaExtException(Messages.ERROR_XML_NODE.format("BufferDati"));
			if (sHashRicevuto.equals(""))
				throw new SedaExtException(Messages.ERROR_XML_NODE.format("Hash"));
			
			//verifica finestra temporale
	        verificaFinestraTemporale(sTagOrario, window_minutes);
	        logger.info(Messages.TIME_WINDOW_VERIFIED.format());
	        
	        //decodifica buffer Base64
	        String bufferDati = decodificaBuffer(sBufferDatiCrypt);
	        
	        //verifica hash
	        verificaHash(sHashRicevuto, bufferDati, encryptIV, encryptKey, sTagOrario);
	        logger.info(Messages.HASH_VERIFIED.format());
	       	        
	        logger.info(Messages.DATA_BUFFER.format(bufferDati));
	        return bufferDati;
	        
		}  catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new SedaExtException(Messages.XML_EXCEPTION.format(), e);
		} catch (SAXException e) {
			e.printStackTrace();
			throw new SedaExtException(Messages.XML_EXCEPTION.format(), e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SedaExtException(Messages.XML_EXCEPTION.format(), e);
		}
	}
	
	private void verificaFinestraTemporale(String sTagOrario, int window_minutes) throws SedaExtException
	{
	    long longTagOrario = 0L;
	    try
	    {
	        Calendar calReceived = Calendar.getInstance();
	        String sAnno = sTagOrario.substring(0, 4);
	        String sMese = sTagOrario.substring(4, 6);
	        String sGiorno = sTagOrario.substring(6, 8);
	        String sOra = sTagOrario.substring(8, 10);
	        String sMinuti = sTagOrario.substring(10, 12);
	        
	        calReceived.set(Calendar.YEAR, Integer.parseInt(sAnno));
	        calReceived.set(Calendar.MONTH, Integer.parseInt(sMese) - 1);
	        calReceived.set(Calendar.DATE, Integer.parseInt(sGiorno));
	        calReceived.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sOra));
	        calReceived.set(Calendar.MINUTE, Integer.parseInt(sMinuti));
	        
	        longTagOrario = calReceived.getTimeInMillis(); 
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	throw new SedaExtException(Messages.INVALID_PARAMETER_VALUE.format("TagOrario"), e);
	    }
	    
	    Calendar calNow = Calendar.getInstance();
	
	    long longActualDate = calNow.getTimeInMillis(); 
	    long lMinutiDiff = Math.abs((longActualDate - longTagOrario) / (long)60000);
	    
	    if(lMinutiDiff > (long)window_minutes)
	    {
	    	throw new SedaExtException(Messages.TIME_WINDOW_EXPIRED.format());
	    }
	}
	
	private String decodificaBuffer(String sBufferDatiCrypt) 
	{	    
	    String bufferDati = new String(org.apache.commons.codec.binary.Base64.decodeBase64(sBufferDatiCrypt)); 
	    return bufferDati;
	}
	
	private void verificaHash(String sHashRicevuto, String bufferDati, String encryptIV, String encryptKey, String sTagOrario) throws SedaExtException
	{
		String hashCalcolato = null;
		try {
			hashCalcolato = Utilities.getMD5Hash(encryptIV + bufferDati + encryptKey + sTagOrario);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new SedaExtException(Messages.HASH_CREATION_ERROR.format(), e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SedaExtException(Messages.HASH_CREATION_ERROR.format(), e);
		}
	    if(hashCalcolato == null || hashCalcolato.equals(""))
	    	throw new SedaExtException(Messages.HASH_CREATION_ERROR.format());

	    if(!hashCalcolato.equalsIgnoreCase(sHashRicevuto))
	    	throw new SedaExtException(Messages.HASH_CREATION_ERROR.format(sHashRicevuto, hashCalcolato));
	}
}
