package it.gruppoinit.pal.gp.pay.service.helper;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaWsInType;

public class PosizioneAccorpataParserHelper {

    private BigInteger numeroRata;
    private ImportoPagamentoWsInType importo;
    private PosizioneDebitoriaWsInType rata;

    private PosizioneAccorpataParserHelper() {

	// costruttore privato per farlo instanziare da metodo preciso
    }

    public static PosizioneAccorpataParserHelper fromDati(ImportoPagamentoWsInType imp, PosizioneDebitoriaWsInType rata) {

	PosizioneAccorpataParserHelper ret = new PosizioneAccorpataParserHelper();
	ret.numeroRata = rata.getNumeroRata();
	ret.importo = imp;
	ret.rata = rata;
	return ret;
    }

    public XMLGregorianCalendar getDataScadenzaRata() {

	return rata.getDataScadenza();
    }

    public BigInteger getNumeroRata() {

	return numeroRata;
    }

    public Set<String> getRiferimentiClient() {

	Set<String> riferimentiClient = new HashSet<>();
	rata.getRiferimentiClient().forEach((val) -> {
	    riferimentiClient.add(val);
	});
	return riferimentiClient;
    }

    /**
     * Se defaultOggettoPosizione!=null allora prende quello altrimenti rata.getDescrizione()
     * 
     * @param defaultOggettoPosizione
     * @return
     */
    public String getDescrizioneRata(String defaultOggettoPosizione) {

	if (StringUtils.isNotBlank(defaultOggettoPosizione)) {
	    return defaultOggettoPosizione;
	}
	return rata.getDescrizione();
    }

    public void getMappaImporti(Map<String, ImportoPagamentoWsInType> importiPerCodiceMappatura) {

	String key = importo.getCodiceMappatura();
	ImportoPagamentoWsInType importoPagamentoWsInType = importiPerCodiceMappatura.get(key);
	if (importoPagamentoWsInType == null) {
	    importoPagamentoWsInType = new ImportoPagamentoWsInType();
	    importoPagamentoWsInType.setCodiceMappatura(key);
	    importoPagamentoWsInType.setImporto(importo.getImporto());
	} else {
	    importoPagamentoWsInType.getImporto().add(importo.getImporto());
	}
	importiPerCodiceMappatura.put(key, importoPagamentoWsInType);
    }
}
