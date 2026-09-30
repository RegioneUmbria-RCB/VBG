package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

public class DatiRispostaProtocollo {

    private String anno;
    private String dataProtocolloRi;
    private String numeroProtocolloRi;
    private String ufficioRi;

    public String getAnno() {

	return anno;
    }

    public void setAnno(String anno) {

	this.anno = anno;
    }

    public String getDataProtocolloRi() {

	return dataProtocolloRi;
    }

    public void setDataProtocolloRi(String dataProtocolloRi) {

	this.dataProtocolloRi = dataProtocolloRi;
    }

    public String getNumeroProtocolloRi() {

	return numeroProtocolloRi;
    }

    public void setNumeroProtocolloRi(String numeroProtocolloRi) {

	this.numeroProtocolloRi = numeroProtocolloRi;
    }

    public String getUfficioRi() {

	return ufficioRi;
    }

    public void setUfficioRi(String ufficioRi) {

	this.ufficioRi = ufficioRi;
    }

    public static DatiRispostaProtocollo fromREAProtocollo(Protocollo protocollo) {

	if (protocollo == null) {
	    return null;
	}
	DatiRispostaProtocollo ret = new DatiRispostaProtocollo();
	ret.setNumeroProtocolloRi(protocollo.getNumeroProtocollo());
	ret.setAnno(protocollo.getAnnoProtocollo());
	ret.setUfficioRi(protocollo.getUfficioRi());
	ret.setDataProtocolloRi(protocollo.getDtProtocolloRi());
	return ret;
    }

    public static DatiRispostaProtocollo fromREAStdProtocollo(ProtocolloRI protocollo) {

	if (protocollo == null) {
	    return null;
	}
	DatiRispostaProtocollo ret = new DatiRispostaProtocollo();
	ret.setNumeroProtocolloRi(protocollo.getNumeroProtocolloRi());
	ret.setAnno(protocollo.getAnno());
	ret.setUfficioRi(protocollo.getUfficioRi());
	ret.setDataProtocolloRi(protocollo.getDataProtocolloRi());
	return ret;
    }
}
