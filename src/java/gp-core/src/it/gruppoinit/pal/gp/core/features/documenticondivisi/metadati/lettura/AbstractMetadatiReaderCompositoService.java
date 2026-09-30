package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public abstract class AbstractMetadatiReaderCompositoService implements IMetadatiReaderCompositoService {

    private List<IMetadatiReader> readers = new ArrayList<IMetadatiReader>(0);

    public List<IMetadatiReader> getReaders() {

	if (this.readers == null) {
	    this.readers = new ArrayList<IMetadatiReader>(0);
	}
	return readers;
    }

    @Override
    public List<DocumentiCondivisiMetadato> get() {

	List<DocumentiCondivisiMetadato> lista = new ArrayList<DocumentiCondivisiMetadato>();
	for (IMetadatiReader reader : readers) {
	    lista.add(reader.get());
	}
	return lista;
    }
}
