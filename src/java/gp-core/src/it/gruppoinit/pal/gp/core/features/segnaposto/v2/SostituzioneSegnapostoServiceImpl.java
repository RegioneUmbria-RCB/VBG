package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

import org.odftoolkit.simple.TextDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.infrastructure.packages.PackageScannerService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.registro.IRegistroSegnaposto;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.registro.RegistroSegnapostoServiceImpl;

@Service
public class SostituzioneSegnapostoServiceImpl implements ISostituzioneSegnapostoService {

    private final Logger log = LoggerFactory.getLogger(SostituzioneSegnapostoServiceImpl.class);
    private static final String PACKAGE_PATH = "it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni";
    private IRegistroSegnaposto registro = new RegistroSegnapostoServiceImpl(); // Potrebbe essere iniettato
    private IOCKernel kernel;

    @Autowired
    public SostituzioneSegnapostoServiceImpl(IOCKernel kernel) {

	this.kernel = kernel;
    }

    @Override
    public IOdtSubstitution sostituisciOdt(TextDocument currentDocument, StrutturaSegnaposto struttura, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	ISegnaposto segnaposto = this.risolviSegnaposto(struttura);
	if (segnaposto == null) {
	    return null;
	}
	// Inizializzo il segnaposto con i servizi necessari
	return segnaposto.sostituisciOdt(currentDocument, struttura.getArgomenti(), data, userData);
    }

    @Override
    public IRtfSubstitution sostituisciRtf(StrutturaSegnaposto strutturaSegnaposto, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	ISegnaposto segnaposto = this.risolviSegnaposto(strutturaSegnaposto);
	if (segnaposto == null) {
	    return null;
	}
	return segnaposto.sostituisciRtf(strutturaSegnaposto.getArgomenti(), data, userData);
    }

    private ISegnaposto risolviSegnaposto(StrutturaSegnaposto struttura) {

	if (!registro.isInizializzato()) {
	    this.registro.inizializza(new PackageScannerService(PACKAGE_PATH));
	}
	ISegnaposto segnaposto = registro.getSegnaposto(struttura.getNome(), struttura.getArgomenti().length > 0);
	if (segnaposto != null) {
	    try {
		segnaposto.inizializzaServizi(kernel);
	    } catch (Exception e) {
		log.error(String.format("Impossibile inizializzare i servizi della classe %s per il segnaposto %s: %s", // 
			segnaposto.getClass().getName(), //
			struttura.toStringaSegnaposto(), //
			e.toString()));
		return null;
	    }
	}
	return segnaposto;
    }
}
