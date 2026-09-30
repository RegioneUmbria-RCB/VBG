package it.gruppoinit.pal.gp.pay.features.interfaccia;

public class PayConnectorConfigValuesHelper {

    private Integer id;
    private String idcomune;
    private String configparam;
    private String valore;
    private String descrizione;
    private String codiceconnettore;

    public PayConnectorConfigValuesHelper() {

    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getConfigPara() {

	return configparam;
    }

    public void setConfigPara(String configPara) {

	this.configparam = configPara;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getCodiceConnettore() {

	return codiceconnettore;
    }

    public void setCodiceConnettore(String codiceConnettore) {

	this.codiceconnettore = codiceConnettore;
    }
}
