package com.seda.payer.ext;

import org.apache.log4j.Logger;


import com.seda.payer.ext.util.Messages;
import com.seda.payer.ext.util.SedaExtException;
import com.seda.payer.ext.util.SedaExtLogger;

public class Client {

	private CoreCS coreCS;
	private String _encryptIV;
	private String _encryptKey;
	private String _codicePortale;

	private static Logger _logger = null;	
	
	/**
	 * Costruttore dell'oggetto Client con logger di default
	 * @param encryptIV Chiave primaria per la generazione dell'Hash
	 * @param encryptKey Chiave secondaria per la generazione dell'Hash
	 * @param codicePortale Codice identificativo del portale esterno da utilizzare nei buffer di scambio
	 * @throws SedaExtException
	 */
	public Client(String encryptIV, String encryptKey, String codicePortale) throws SedaExtException
	{
		_logger = SedaExtLogger.getLogger("com.seda.payer.ext.Client");
		initClass(encryptIV, encryptKey, codicePortale);
	}
	
	/**
	 * Costruttore dell'oggetto Client con logger custom
	 * @param encryptIV Chiave primaria per la generazione dell'Hash
	 * @param encryptKey Chiave secondaria per la generazione dell'Hash
	 * @param codicePortale Codice identificativo del portale esterno da utilizzare nei buffer di scambio
	 * @param logger Istanza esterna di un oggetto log4j per il logging delle informazioni
	 * @throws SedaExtException
	 */
	public Client(String encryptIV, String encryptKey, String codicePortale, Logger logger) throws SedaExtException
	{
		if (logger == null)
			throw new SedaExtException(Messages.INVALID_PARAMETER_VALUE.format("logger"));
		
		_logger = logger;
		initClass(encryptIV, encryptKey, codicePortale);
	}
	
	private void initClass(String encryptIV, String encryptKey, String codicePortale) throws SedaExtException
	{
		//controllo parametri input
		if (encryptIV == null || encryptIV.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("encryptIV"));
		if (encryptKey == null || encryptKey.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("encryptKey"));
		if (codicePortale == null || codicePortale.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("codicePortale"));
		
		coreCS = new CoreCS();
		
		_encryptIV = encryptIV;
		_encryptKey = encryptKey;
		_codicePortale = codicePortale;
		
		_logger.info(Messages.INITIALIZATION_SUCCESS.format(encryptIV, encryptKey, codicePortale));
	}
	
	/**
	 * Costruisce l'xml del &lt;Buffer&gt; da utilizzare per lo scambio S2S con il Server
	 * @param bufferDati Stringa xml del &lt;PaymentRequest&gt; che verrà codificato e innestato all'interno del tag &lt;BufferDati&gt; del buffer finale
	 * @return Stringa xml del &lt;Buffer&gt; costruito
	 * @throws SedaExtException
	 */
	public String getBufferPaymentRequest(String bufferDati) throws SedaExtException
	{
		_logger.info(Messages.METHOD_START_PARAMETER.format("getBufferPaymentRequest", "bufferDati", bufferDati));
		
		//controllo parametri input
		if (bufferDati == null || bufferDati.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("bufferDati"));
		
		return coreCS.creaBuffer(bufferDati, _encryptIV, _encryptKey, _codicePortale, _logger);
	}

	
	/**
	 * Costruisce l'xml del &lt;Buffer&gt; da utilizzare per il redirect al Server
	 * @param rID Request ID che verrà codificato e innestato all'interno del tag &lt;BufferDati&gt; del buffer finale
	 * @return Stringa xml del &lt;Buffer&gt; costruito
	 * @throws SedaExtException
	 */
	public String getBufferRID(String rID) throws SedaExtException
	{
		_logger.info(Messages.METHOD_START_PARAMETER.format("getBufferRID", "rID", rID));
		
		//controllo parametri input
		if (rID == null || rID.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("rID"));
		
		return coreCS.creaBuffer(rID, _encryptIV, _encryptKey, _codicePortale, _logger);
	}
	
	/**
	 * Costruisce l'xml del &lt;Buffer&gt; da utilizzare per lo scambio S2S con il Server
	 * @param pID Payment ID che verrà codificato e innestato all'interno del tag &lt;BufferDati&gt; del buffer finale
	 * @return Stringa xml del &lt;Buffer&gt; costruito
	 * @throws SedaExtException
	 */
	public String getBufferPID(String pID) throws SedaExtException
	{
		_logger.info(Messages.METHOD_START_PARAMETER.format("getBufferPID", "pID", pID));
		
		//controllo parametri input
		if (pID == null || pID.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("pID"));
		
		return coreCS.creaBuffer(pID, _encryptIV, _encryptKey, _codicePortale, _logger);
	}
	
	/**
	 * Effettua i controlli di validità dei dati ricevuti ed estrae l'xml del &lt;PaymentData&gt; dal &lt;Buffer&gt;
	 * @param buffer Stringa xml del &lt;Buffer&gt; ricevuto
	 * @param window_minutes Finestra tenporale entro la quale ritenere valido il messaggio ricevuto
	 * @return Stringa xml del &lt;PaymentData&gt;
	 * @throws SedaExtException
	 */
	public String getPaymentData(String buffer, int window_minutes) throws SedaExtException
	{
		_logger.info(Messages.METHOD_START_2_PARAMETERS.format("getPaymentData", "buffer", buffer, "window_minutes", window_minutes));
		
		//controllo parametri input
		if (buffer == null || buffer.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("buffer"));
		
		return coreCS.decodeBuffer(buffer, window_minutes, _encryptIV, _encryptKey, _logger);
	}
}
