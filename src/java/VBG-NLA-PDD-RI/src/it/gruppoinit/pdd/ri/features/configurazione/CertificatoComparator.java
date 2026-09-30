package it.gruppoinit.pdd.ri.features.configurazione;

import java.util.Comparator;

public class CertificatoComparator implements Comparator<Certificato> {

    @Override
    public int compare(Certificato c1, Certificato c2) {

	return c1.getCodiceCatastale().compareToIgnoreCase(c2.getCodiceCatastale());
    }
}
