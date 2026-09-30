package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;

public class DatiProtocolloReaderService extends AbstractMetadatiReaderCompositoService {

    public DatiProtocolloReaderService(Istanze istanza, Movimenti movimento) {

	if (istanza == null && movimento == null) {
	    return;
	}
	if (movimento != null) {
	    this.getReaders().add(new NumeroProtocolloReader(movimento));
	    this.getReaders().add(new DataProtocolloReader(movimento));
	} else {
	    this.getReaders().add(new NumeroProtocolloReader(istanza));
	    this.getReaders().add(new DataProtocolloReader(istanza));
	}
    }
}
