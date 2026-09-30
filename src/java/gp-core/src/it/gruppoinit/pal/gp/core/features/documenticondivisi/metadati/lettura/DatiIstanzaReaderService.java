package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;

public class DatiIstanzaReaderService extends AbstractMetadatiReaderCompositoService {

    public DatiIstanzaReaderService(Istanze istanza) {

	if (istanza != null) {
	    this.getReaders().add(new CodiceIstanzaReader(istanza));
	    this.getReaders().add(new NumeroIstanzaReader(istanza));
	    this.getReaders().add(new DataIstanzaReader(istanza));
	    this.getReaders().add(new TipoProcedimentoReader(istanza));
	    this.getReaders().add(new DescrizioneLavoriReader(istanza));
	    this.getReaders().add(new ComuneReader(istanza));
	    this.getReaders().add(new ArchivioPraticheReader(istanza));
	}
    }
}
