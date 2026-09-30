package it.gruppoinit.pal.gp.core.features.alfresco.model.types;

public enum ECMMetadataTypes {

    NAME("cm:name"),
    CREATOR("cm:creator"),
    AUTOVERSION("cm:autoVersion"),
    TITLE("cm:title"),
    MODIFIER("cm:modifier"),
    INITIALVERSION("cm:initialVersion"),
    CREATED("cm:created"),
    VERSIONLABEL("cm:versionLabel"),
    VERSIONTYPE("cm:versionType"),
    DESCRIPTION("cm:description"),
    //VBG
    COMUNE_PRATICA("vbg:comunePratica"),
    SPORTELLO_PRATICA("vbg:sportelloPratica"),
    NUMERO_PRATICA("vbg:dataPresentazioneIstanza"),
    DATA_PROTOCOLLO_ISTANZA("vbg:dataProtocolloIstanza"),
    NUMERO_PROTOCOLLO_ISTANZA("vbg:numeroProtocolloIstanza"),
    TIPO_INTERVENTO("vbg:tipoIntervento"),
    RICHIEDENTE("vbg:richiedente"),
    RICHIEDENTE_CF("vbg:richiedenteCf"),
    RICHIEDENTE_PIVA("vbg:richiedentePiva"),
    AZIENDA("vbg:azienda"),
    AZIENDA_CF("vbg:aziendaCf"),
    INTERMEDIARIO("vbg:intermediario"),
    INTERMEDIARIO_CF("vbg:intermediarioCf"),
    INTERMEDIARIO_PIVA("vbg:intermediarioPiva"),
    OGGETTO_PRATICA("vbg:oggettoPratica"),
    DESCRIZIONE_FASE("vbg:descrizioneFase"),
    DATA_FASE("vbg:dataFase"),
    NUMERO_PROTOCOLLO_FASE("vbg:numeroProtocolloFase"),
    DATA_PROTOCOLLO_FASE("vbg:dataProtocolloFase");

    private String value = null;

    private ECMMetadataTypes(String value) {

	this.value = value;
    }

    public String getValue() {

	return value;
    }
}
