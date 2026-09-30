package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;

public class DatiCatastaliPrimariReaderService extends AbstractMetadatiReaderCompositoService {

    public DatiCatastaliPrimariReaderService(Istanze istanza) {

	Istanzemappali mappale = istanza.getMappalePrimario();
	if (mappale != null) {
	    this.getReaders().add(new SezioneReader(mappale));
	    this.getReaders().add(new CatastoReader(mappale));
	    this.getReaders().add(new FoglioReader(mappale));
	    this.getReaders().add(new ParticellaReader(mappale));
	    this.getReaders().add(new SubReader(mappale));
	}
    }
}
