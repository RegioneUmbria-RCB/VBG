package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Movimenti;

public class DatiCommisioneEdiliziaReaderService extends AbstractMetadatiReaderCompositoService {

    public DatiCommisioneEdiliziaReaderService(Movimenti movimento) {

	if (movimento != null) {
	    this.getReaders().add(new NumeroCommissioneEdiliziaReader(movimento));
	    this.getReaders().add(new DataCommissioneEdiliziaReader(movimento));
	    this.getReaders().add(new EsitoCommissioneEdiliziaReader(movimento));
	}
    }
}
