package it.gruppoinit.pal.gp.core.features.segnaposto.v2.registro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.infrastructure.packages.IPackageScannerService;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.ISegnaposto;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.CfgMetadati;

@Service
public class RegistroSegnapostoServiceImpl implements IRegistroSegnaposto {

    private Map<ChiaveRegistro, Class<? extends ISegnaposto>> mappaSegnaposto = new HashMap<ChiaveRegistro, Class<? extends ISegnaposto>>();
    // private ISegnaposto segnapostoDefault = new SegnapostoDefault();
    private final Logger log = LoggerFactory.getLogger(RegistroSegnapostoServiceImpl.class);
    private boolean inizializzato = false;

    @Override
    public ISegnaposto getSegnaposto(String nome, boolean haArgomenti) {

	ChiaveRegistro chiave = new ChiaveRegistro(nome, haArgomenti);
	if (this.mappaSegnaposto.containsKey(chiave)) {
	    Class<? extends ISegnaposto> segnapostoClass = this.mappaSegnaposto.get(chiave);
	    try {
		return segnapostoClass.newInstance();
	    } catch (Exception e) {
		log.error("Non è stato possibile istanziare la classe del segnaposto {} " + //
			  "il segnaposto non verrà sostituito: {}",
			segnapostoClass.getName(), e);
	    }
	} else if (StringUtils.contains(nome, "CFG_")) {
	    try {
		CfgMetadati cfgMetadati = new CfgMetadati();
		cfgMetadati.setName(nome);
		return cfgMetadati;
	    } catch (Exception e) {
		log.error("Non è stato possibile istanziare la classe del segnaposto {} " + //
			  "il segnaposto non verrà sostituito: {}",
			CfgMetadati.class.getName(), e);
	    }
	}
	if (!haArgomenti) {
	    return getSegnaposto(nome, true);
	}
	// Per ora restituisce null in modo da poter utilizzare il vecchio flusso di sostituzione segnaposto
	// Quando il codice verrà rinormalizzato dovrà restituire il segnaposto di default
	return null; // segnapostoDefault ;
    }

    public void aggiungiSegnaposto(String nome, boolean haArgomenti, ISegnaposto segnaposto) {

	ChiaveRegistro chiave = new ChiaveRegistro(nome, haArgomenti);
	this.mappaSegnaposto.put(chiave, segnaposto.getClass());
    }

    public void inizializza(IPackageScannerService packageScanner) {

	List<Class<? extends ISegnaposto>> scanResult = packageScanner.scan(ISegnaposto.class);
	for (Class<? extends ISegnaposto> segnapostoClass : scanResult) {
	    try {
		ISegnaposto cls = segnapostoClass.newInstance();
		ChiaveRegistro chiave = new ChiaveRegistro(cls.getNome(), cls.haArgomenti());
		this.mappaSegnaposto.put(chiave, segnapostoClass);
	    } catch (Exception e) {
		log.error("Non è stato possibile istanziare la classe del segnaposto {} " + //
			  "il segnaposto non verrà sostituito: {}",
			segnapostoClass.getName(), e);
	    }
	}
	this.inizializzato = true;
    }

    @Override
    public int getNumeroSegnapostoRegistrati() {

	return this.mappaSegnaposto.size();
    }

    @Override
    public boolean isInizializzato() {

	return inizializzato;
    }
}
