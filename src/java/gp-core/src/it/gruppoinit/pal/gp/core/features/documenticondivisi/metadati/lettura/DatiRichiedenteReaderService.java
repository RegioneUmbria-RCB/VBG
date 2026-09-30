package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;

public class DatiRichiedenteReaderService extends AbstractMetadatiReaderCompositoService {

    public DatiRichiedenteReaderService(Istanze istanza) {

	if (istanza != null) {
	    Anagrafe richiedente = istanza.getRichiedente();
	    if (richiedente != null) {
		this.getReaders().add(new RichiedenteReader(richiedente));
		this.getReaders().add(new CodiceFiscaleReader(richiedente));
		this.getReaders().add(new PartitaIvaReader(richiedente));
	    }
	}
    }
}
