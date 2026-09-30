package it.gruppoinit.domain.helper;

public class PraticaXmlHelper<T> {

    private String nomeFilePratica;
    private String praticaXml;
    private T oggettoPratica;

    public PraticaXmlHelper(String nomeFilePratica, String praticaXml, T oggettoPratica) {

	super();
	this.nomeFilePratica = nomeFilePratica;
	this.praticaXml = praticaXml;
	this.oggettoPratica = oggettoPratica;
    }

    public String getNomeFilePratica() {

	return nomeFilePratica;
    }

    public String getPraticaXml() {

	return praticaXml;
    }

    public T getOggettoPratica() {

	return oggettoPratica;
    }
}
