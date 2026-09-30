package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

public class DatiLocalizzazionePrimariaReaderService extends AbstractMetadatiReaderCompositoService {

    public DatiLocalizzazionePrimariaReaderService(Istanze istanza) {

	Istanzestradario localizzazione = istanza.getLocalizzazionePrimaria();
	if (localizzazione != null) {
	    this.getReaders().add(new CivicoReader(localizzazione));
	    this.getReaders().add(new PrefissoReader(localizzazione.getStradario()));
	    this.getReaders().add(new ViaReader(localizzazione.getStradario()));
	    this.getReaders().add(new InternoReader(localizzazione));
	    //disabilitato perchè a Narni salvano questa informazione su due parti:
	    //1. fabbricato della localizzazione primaria 
	    //2. posizione archivio
	    //ma quello attendibile è quello su posizione archivio
	    //se si riabilita va pensato a come gestire questo caso
	    //this.readers.add(new CodiceEdificioReader(localizzazione));
	}
    }
}