package com.seda.payer.ext;

import org.apache.log4j.Logger;

import com.seda.payer.ext.util.Messages;
import com.seda.payer.ext.util.SedaExtException;
import com.seda.payer.ext.util.SedaExtLogger;

public class Server {
	
	private CoreCS coreCS;
	private String _encryptIV;
	private String _encryptKey;
	private String _codicePortale;
	
	private static Logger _logger = null;	
	
	/**
	 * Costruttore dell'oggetto Server con logger di default
	 * @param encryptIV Chiave primaria per la generazione dell'Hash
	 * @param encryptKey Chiave secondaria per la generazione dell'Hash
	 * @param codicePortale Codice identificativo del portale esterno da utilizzare nei buffer di scambio
	 * @throws SedaExtException
	 */
	public Server(String encryptIV, String encryptKey, String codicePortale) throws SedaExtException
	{
		_logger = SedaExtLogger.getLogger("com.seda.payer.ext.Server");
		initClass(encryptIV, encryptKey, codicePortale);
	}
	
	/**
	 * Costruttore dell'oggetto Server con logger custom
	 * @param encryptIV Chiave primaria per la generazione dell'Hash
	 * @param encryptKey Chiave secondaria per la generazione dell'Hash
	 * @param codicePortale Codice identificativo del portale esterno da utilizzare nei buffer di scambio
	 * @param logger Istanza esterna di un oggetto log4j per il logging delle informazioni
	 * @throws SedaExtException
	 */
	public Server(String encryptIV, String encryptKey, String codicePortale, Logger logger) throws SedaExtException
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
	 * Effettua i controlli di validità dei dati ricevuti ed estrae l'xml del &lt;PaymentRequest&gt; dal &lt;Buffer&gt;
	 * @param buffer Stringa xml del &lt;Buffer&gt; ricevuto
	 * @param window_minutes Finestra tenporale entro la quale ritenere valido il messaggio ricevuto
	 * @return Stringa xml del &lt;PaymentRequest&gt;
	 * @throws SedaExtException
	 */
	public String getPaymentRequest(String buffer, int window_minutes) throws SedaExtException
	{
		_logger.info(Messages.METHOD_START_2_PARAMETERS.format("getPaymentRequest", "buffer", buffer, "window_minutes", window_minutes));
		
		//controllo parametri input
		if (buffer == null || buffer.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("buffer"));
		
		return coreCS.decodeBuffer(buffer, window_minutes, _encryptIV, _encryptKey, _logger);
	}

	/**
	 * Effettua i controlli di validità dei dati ricevuti ed estrae il Request ID dal &lt;Buffer&gt;
	 * @param buffer Stringa xml del &lt;Buffer&gt; ricevuto
	 * @param window_minutes Finestra tenporale entro la quale ritenere valido il messaggio ricevuto
	 * @return Request ID
	 * @throws SedaExtException
	 */
	public String getRID(String buffer, int window_minutes) throws SedaExtException
	{
		_logger.info(Messages.METHOD_START_2_PARAMETERS.format("getRID", "buffer", buffer, "window_minutes", window_minutes));
		
		//controllo parametri input
		if (buffer == null || buffer.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("buffer"));
		
		return coreCS.decodeBuffer(buffer, window_minutes, _encryptIV, _encryptKey, _logger);
	}
	
	/**
	 * Effettua i controlli di validità dei dati ricevuti ed estrae il Payment ID dal &lt;Buffer&gt;
	 * 
	 * @param buffer
	 *            Stringa xml del &lt;Buffer&gt; ricevuto
	 * @param window_minutes
	 *            Finestra tenporale entro la quale ritenere valido il messaggio ricevuto
	 * @return Payment ID
	 * @throws SedaExtException
	 */
	public String getPID(String buffer, int window_minutes) throws SedaExtException
	{
		_logger.info(Messages.METHOD_START_2_PARAMETERS.format("getPID", "buffer", buffer, "window_minutes", window_minutes));
		
		//controllo parametri input
		if (buffer == null || buffer.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("buffer"));
		
		return coreCS.decodeBuffer(buffer, window_minutes, _encryptIV, _encryptKey, _logger);
	}
	
	/**
	 * Costruisce l'xml del &lt;Buffer&gt; da utilizzare per gli scambi S2S e redirect con il Client
	 * @param bufferDati Stringa xml del &lt;PaymentData&gt; che verr� codificato e innestato all'interno del tag &lt;BufferDati&gt; del buffer finale
	 * @return Stringa xml del &lt;Buffer&gt; costruito
	 * @throws SedaExtException
	 */
	public String getBufferPaymentData(String bufferDati) throws SedaExtException
	{
		_logger.info(Messages.METHOD_START_PARAMETER.format("getBufferPaymentData", "bufferDati", bufferDati));
		
		//controllo parametri input
		if (bufferDati == null || bufferDati.equals(""))
			throw new SedaExtException(Messages.EMPTY_PARAMETER.format("bufferDati"));
		
		return coreCS.creaBuffer(bufferDati, _encryptIV, _encryptKey, _codicePortale, _logger);
	}
}
