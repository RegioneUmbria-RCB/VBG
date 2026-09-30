package it.gruppoinit.faldonetelematico.model;

import org.apache.commons.lang.StringUtils;

public enum FaldoneTelematicoValoreCampoEnum {

    CODICEFISCALE("CodiceFiscale"),
    TITOLO("Titolo"),
    COGNOME("Cognome"),
    NOME("Nome"),
    DATANASCITA("DataNascita"),
    SESSO("Sesso"),
    LUOGONASCITA("LuogoNascita"),
    CITTADINANZA("Cittadinanza"),
    PROVINCIARESIDENZA("ProvinciaResidenza"),
    COMUNERESIDENZA("ComuneResidenza"),
    VIARESIDENZA("ViaResidenza"),
    CIVICORESIDENZA("CivicoResidenza"),
    CAPRESIDENZA("CAPResidenza"),
    TELEFONO("Telefono"),
    EMAILPEC("EMailPEC"),
    PROVINCIASEDE("ProvinciaSede"),
    COMUNESEDE("ComuneSede"),
    VIASEDE("ViaSede"),
    CIVICOSEDE("CivicoSede"),
    CAPSEDE("CAPSede"),
    CFPI("CFPI"),
    PI("PI"),
    TELEFONOSEDE("TelefonoSede"),
    EMAILPECSEDE("EMailPECSede"),
    PROVINCIASTUDIO("ProvinciaStudio"),
    COMUNESTUDIO("ComuneStudio"),
    VIASTUDIO("ViaStudio"),
    CIVICOSTUDIO("CivicoStudio"),
    CAPSTUDIO("CAPStudio"),
    TELEFONOSTUDIO("TelefonoStudio"),
    OGGETTO("Oggetto"),
    ENTE("Ente"),
    BELFIORE("BelFiore"),
    UFFICIODESTINATARIO("UfficioDestinatario"),
    DOMICILIODIGITALE("domiciliodigitale"),
    LUOGO("Luogo"),
    DATA("Data"),
    CATCOMUNE("CatComune"),
    CATFOGLIO("CatFoglio"),
    CATNUMERO("CatNumero"),
    PROVINCIA("Provincia"),
    COMUNE("Comune"),
    VIA("Via"),
    CIVICO("Civico"),
    CAP("CAP"),
    DENOMINAZIONE("Denominazione"),
    ATTRIBUTI_CCIAAPROV("Attributi_CCIAAProv"),
    ATTRIBUTI_CCIAANUMERO("Attributi_CCIAANumero"),
    ATTRIBUTI_REAPROV("Attributi_REAProv"),
    ATTRIBUTI_REANUMERO("Attributi_REANumero"),
    VALORE_NON_INCLUSO("avlore non incluso nel enum"),
    PARTITAIVA("PartitaIVA"),
    RUOLO("Ruolo"),
    ATTRIBUTI_ALBO("Attributi_Albo"),
    ATTRIBUTI_DELLAPROVINCIA("Attributi_DellaProvincia"),
    Attributi_AlNumero("Attributi_AlNumero");

    private String value;

    FaldoneTelematicoValoreCampoEnum(String value) {

	this.value = value;
    }

    public String value() {

	return value;
    }

    public static FaldoneTelematicoValoreCampoEnum fromValue(String value) {

	for (FaldoneTelematicoValoreCampoEnum v : FaldoneTelematicoValoreCampoEnum.values()) {
	    if (StringUtils.equals(v.value(), value)) {
		return v;
	    }
	}
	return FaldoneTelematicoValoreCampoEnum.VALORE_NON_INCLUSO;
    }
}
