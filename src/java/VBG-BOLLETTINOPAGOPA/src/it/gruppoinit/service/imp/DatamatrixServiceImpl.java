package it.gruppoinit.service.imp;

import it.gruppoinit.constants.DatamatrixCostanti;
import it.gruppoinit.service.DatamatrixService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DatamatrixServiceImpl implements DatamatrixService {

    private static final Logger log = LoggerFactory.getLogger(DatamatrixServiceImpl.class);

    @Override
    public String generaStringaDatamatrixPoste(String codiceAvviso, String conto, String importo, String codiceFiscaleEnte, String cf_pi,
	    String ragSoc_nomeCognome, String causale) {

	log.debug("generaAvvisatuaPagoPa# Start...");
	String datamatrix = DatamatrixCostanti.PATTERN_DATAMATRIX;
	log.debug("generaStringaDatamatrixPoste# pattern datamatrix = {}");
	try {
	    Integer codline_lunghezza = DatamatrixCostanti.DATAMATRIX_CODELINE_LUNGHEZZA_CODICE;
	    Integer codline_lunghezzaconto = DatamatrixCostanti.DATAMATRIX_CODELINE_LUNGHEZZA_CONTO;
	    //
	    if (StringUtils.isNotEmpty(codiceAvviso) && codiceAvviso.length() > codline_lunghezza) {
		log.debug("generaStringaDatamatrixPoste# Lunghezza codice avviso > {} caratteri", codline_lunghezza);
		throw new RuntimeException("Lunghezza codice avviso >" + codline_lunghezza + "caratteri o parametro non passato");
	    }
	    if (StringUtils.isNotEmpty(conto) && conto.length() > codline_lunghezzaconto) {
		log.debug("generaStringaDatamatrixPoste# Lunghezza codice conto > {} caratteri", codline_lunghezzaconto);
		throw new RuntimeException("Lunghezza codice conto > " + codline_lunghezzaconto
			+ " caratteri o parametro non passato o parametro non passato");
	    }
	    if (StringUtils.isNotEmpty(codiceFiscaleEnte) && codiceFiscaleEnte.length() != DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_ENTE) {
		log.debug("generaStringaDatamatrixPoste# Lunghezza codice fiscale ente diverso {} caratteri o parametro non passato",
			DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_ENTE);
		throw new RuntimeException("Lunghezza codice fiscale ente  diverso " + DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_ENTE
			+ " caratteri o parametro non passato");
	    }
	    if (StringUtils.isNotEmpty(cf_pi) && cf_pi.length() > DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE) {
		log.debug("generaStringaDatamatrixPoste# Lunghezza codice fiscale  > {} caratteri o parametro non passato",
			DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE);
		throw new RuntimeException("Lunghezza codice fiscale fiscale  > " + DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE
			+ " caratter o parametro non passatoi");
	    }
	    if (StringUtils.isNotEmpty(ragSoc_nomeCognome)
		    && ragSoc_nomeCognome.length() > DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE) {
		log.debug("generaStringaDatamatrixPoste# Lunghezza nome cognome  > {} caratteri o parametro non passato",
			DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE);
		throw new RuntimeException("Lunghezza  nome cognome > " + DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE
			+ " caratteri o parametro non passato");
	    }
	    if (StringUtils.isNotEmpty(causale) && causale.length() > DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE) {
		log.debug("generaStringaDatamatrixPoste# Lunghezza causale  > {} caratteri o parametro non passato",
			DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE);
		throw new RuntimeException("Lunghezza causale > " + DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE
			+ " caratteri o parametro non passato");
	    }
	    String _codiceFiscaleEnte = codiceFiscaleEnte;
	    String _conto = StringUtils.leftPad(conto, DatamatrixCostanti.DATAMATRIX_CODELINE_LUNGHEZZA_CONTO, "0");
	    String _importo = StringUtils.leftPad(importo, DatamatrixCostanti.DATAMATRIX_CODELINE_LUNGHEZZA_IMPORTO, "0");
	    String codeline = DatamatrixCostanti.PATTERN_CODELINE;
	    codiceAvviso = StringUtils.replace(codiceAvviso, " ", "");
	    codeline = codeline.replace("{0}", codiceAvviso);
	    codeline = codeline.replace("{1}", _conto);
	    codeline = codeline.replace("{2}", _importo);
	    String _codiceFiscale_pi = StringUtils.rightPad(cf_pi, DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CF_DEBITORE, " ");
	    String _cognome_rag_soc = StringUtils.rightPad(ragSoc_nomeCognome, DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_ANAGRAFICA_DEBITORE
		    - ragSoc_nomeCognome.length(), " ");
	    String _causaleVersamento = StringUtils.rightPad(causale, DatamatrixCostanti.DATAMATRIX_LUNGHEZZA_CAMPO_CAUSALE, " ");
	    String _filler = StringUtils.rightPad("", DatamatrixCostanti.DATAMATRIX_NUM_FILLER, " ");
	    //"codfase=NBPA;{0}1P1{1}{2}{3}{4}{5}A";
	    datamatrix = datamatrix.replace("{0}", codeline);
	    datamatrix = datamatrix.replace("{1}", _codiceFiscaleEnte);
	    datamatrix = datamatrix.replace("{2}", _codiceFiscale_pi);
	    datamatrix = datamatrix.replace("{3}", _cognome_rag_soc);
	    datamatrix = datamatrix.replace("{4}", _causaleVersamento);
	    datamatrix = datamatrix.replace("{5}", _filler);
	    log.debug("generaStringaDatamatrixPoste# Bonifico il datamatrix dai caratteri non ammessi");
	    datamatrix = bonificaStringDataMatrix(datamatrix);
	} catch (Exception e) {
	    log.error("generaStringaDatamatrixPoste# Errore properties mancante.  {}", e);
	    throw new RuntimeException(e);
	}
	return datamatrix;
    }

    public static String bonificaStringDataMatrix(String datamatrix) {

	String[] caratteriAmmessi = DatamatrixCostanti.DATAMATRIX_CARATTERI_AMMESSI;
	log.debug("bonificaStringDataMatrix# {}. Stringa --> char[]", datamatrix);
	StringBuffer _datamatrix = new StringBuffer();
	char[] ch = datamatrix.toCharArray();
	for (int i = 0; i < ch.length; i++) {
	    if (i < 13) {
		_datamatrix = _datamatrix.append(ch[i]);
	    } else {
		log.debug("bonificaStringDataMatrix# verifico se '{}' è ammesso", ch[i]);
		boolean trovato = false;
		for (String carattere : caratteriAmmessi) {
		    trovato = false;
		    if (carattere.equals(Character.toString(ch[i]))) {
			_datamatrix = _datamatrix.append(ch[i]);
			trovato = true;
			break;
		    }
		}
		if (!trovato) {
		    log.debug("bonificaStringDataMatrix# carattere '{}', non ammesso", ch[i]);
		    _datamatrix = _datamatrix.append(" ");
		}
	    }
	}
	return _datamatrix.toString();
    }

    public static void main(String[] args) {

	String str = "codfase=NBPA;183011883400000643561200001014811210000000327038961P191001750073CNICLD64M29A326ACINà CLAUDIO Ticket Sanitario 2 A";
	System.out.println(str);
	String d = bonificaStringDataMatrix(str);
	System.out.println(d);
    }
}
