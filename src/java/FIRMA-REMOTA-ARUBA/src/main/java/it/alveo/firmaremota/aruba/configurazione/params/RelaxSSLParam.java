package it.alveo.firmaremota.aruba.configurazione.params;

public class RelaxSSLParam extends BaseParam {

    @Override
    public String getChiave() {

	return "RELAX_SSL";
    }

    @Override
    public String getDescrizione() {

	return "Può assumere i valori 0 o 1. Se 0 all'interno della JVM usata dal tomcat dovranno essere caricati i certificati associati all'indirizzo del endpoint per permettere al sistema di scaricare il wsdl per le chiamate ai servizi esposti. Se 1, il wsdl viene caricato dal file (ArubaSignService.wsdl) presente all'interno del backoffice e non è necessaria la configurazione dei certificati nella JVM";
    }

    public static RelaxSSLParam newParam() {

	return new RelaxSSLParam("0");
    }

    public RelaxSSLParam(String valore) {

	super(valore);
    }
}