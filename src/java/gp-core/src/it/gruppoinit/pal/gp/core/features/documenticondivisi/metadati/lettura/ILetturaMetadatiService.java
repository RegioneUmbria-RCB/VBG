package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.documenticondivisi.DocumentiCondivisiHelper;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;

public interface ILetturaMetadatiService {

    List<DocumentiCondivisiMetadato> read(DocumentiCondivisiHelper documento);
}
