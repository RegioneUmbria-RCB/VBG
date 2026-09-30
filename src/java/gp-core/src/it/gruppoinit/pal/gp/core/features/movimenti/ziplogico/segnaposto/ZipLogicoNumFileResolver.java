package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoResolver;

public class ZipLogicoNumFileResolver implements ISegnapostoResolver {

    private MovimentiZipLogicoService service;
    public static final String TAG = "ZIPLOGICO_NUM_FILE";
    private Integer codiceMovimento;

    public ZipLogicoNumFileResolver(MovimentiZipLogicoService service, Integer codiceMovimento) {

	if (service == null) {
	    throw new IllegalArgumentException("Impossibile richiamare ZipLogicoNumFileResolver senza passare il service per la sostituzione");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("Impossibile richiamare ZipLogicoNumFileResolver senza passare il codiceMovimento");
	}
	this.service = service;
	this.codiceMovimento = codiceMovimento;
    }

    @Override
    public String sostituisci() {

	Integer conteggio = this.service.contaDocumenti(this.codiceMovimento);
	return conteggio.toString();
    }
}
