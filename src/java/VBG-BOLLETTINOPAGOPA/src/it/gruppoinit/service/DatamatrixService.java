package it.gruppoinit.service;

public interface DatamatrixService {

    public String generaStringaDatamatrixPoste(String codiceAvviso, String conto, String importo, String codiceFiscaleEnte, String cf_pi,
	    String ragSoc_nomeCognome, String causale);
}
