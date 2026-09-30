package it.gruppoinit.domain.helper;

import java.util.ArrayList;
import java.util.List;

public class VerticalizzazioniHelper {

    private String PSW_WS;
    private String URL_WS;
    private String USER_WS;
    private String INFO_SCHEMA_VERSIONE;
    //
    private String CODICE_AMMINISTRAZIONE;
    private String CODICE_AOO;
    private String IDENTIFICATIVO_SUAP;
    private String DESCRIZIONE_SUAP;
    //
    private String CODICE_AMMINISTRAZIONE_DEST;
    private String CODICE_AOO_DEST;
    private String IDENTIFICATIVO_SPORTELLO_DEST;
    private String PEC_SPORTELLO_DEST;
    //
    private String COD_MOV_RIENTRO_INTEGRAZ_DOC;
    private String COD_MOV_PRATICA_CONFORME;
    private String COD_MOV_PRATICA_NON_CONFORME;
    private String SALVA_XML_COMUNICAZIONE;
    private List<it.gruppoinit.sigepro.schemas.messages.regole.ParametroType> parametri = new ArrayList<it.gruppoinit.sigepro.schemas.messages.regole.ParametroType>();

    public String getPSW_WS() {

	return PSW_WS;
    }

    public void setPSW_WS(String pSW_WS) {

	PSW_WS = pSW_WS;
    }

    public String getURL_WS() {

	return URL_WS;
    }

    public void setURL_WS(String uRL_WS) {

	URL_WS = uRL_WS;
    }

    public String getUSER_WS() {

	return USER_WS;
    }

    public void setUSER_WS(String uSER_WS) {

	USER_WS = uSER_WS;
    }

    public String getINFO_SCHEMA_VERSIONE() {

	return INFO_SCHEMA_VERSIONE;
    }

    public void setINFO_SCHEMA_VERSIONE(String iNFO_SCHEMA_VERSIONE) {

	INFO_SCHEMA_VERSIONE = iNFO_SCHEMA_VERSIONE;
    }

    public String getCODICE_AMMINISTRAZIONE() {

	return CODICE_AMMINISTRAZIONE;
    }

    public void setCODICE_AMMINISTRAZIONE(String cODICE_AMMINISTRAZIONE) {

	CODICE_AMMINISTRAZIONE = cODICE_AMMINISTRAZIONE;
    }

    public String getCODICE_AOO() {

	return CODICE_AOO;
    }

    public void setCODICE_AOO(String cODICE_AOO) {

	CODICE_AOO = cODICE_AOO;
    }

    public String getIDENTIFICATIVO_SUAP() {

	return IDENTIFICATIVO_SUAP;
    }

    public void setIDENTIFICATIVO_SUAP(String iDENTIFICATIVO_SUAP) {

	IDENTIFICATIVO_SUAP = iDENTIFICATIVO_SUAP;
    }

    public String getDESCRIZIONE_SUAP() {

	return DESCRIZIONE_SUAP;
    }

    public void setDESCRIZIONE_SUAP(String dESCRIZIONE_SUAP) {

	DESCRIZIONE_SUAP = dESCRIZIONE_SUAP;
    }

    //
    public List<it.gruppoinit.sigepro.schemas.messages.regole.ParametroType> getParametri() {

	return parametri;
    }

    public String getCODICE_AMMINISTRAZIONE_DEST() {

	return CODICE_AMMINISTRAZIONE_DEST;
    }

    public void setCODICE_AMMINISTRAZIONE_DEST(String cODICE_AMMINISTRAZIONE_DEST) {

	CODICE_AMMINISTRAZIONE_DEST = cODICE_AMMINISTRAZIONE_DEST;
    }

    public String getCODICE_AOO_DEST() {

	return CODICE_AOO_DEST;
    }

    public void setCODICE_AOO_DEST(String cODICE_AOO_DEST) {

	CODICE_AOO_DEST = cODICE_AOO_DEST;
    }

    public String getIDENTIFICATIVO_SPORTELLO_DEST() {

	return IDENTIFICATIVO_SPORTELLO_DEST;
    }

    public void setIDENTIFICATIVO_SPORTELLO_DEST(String iDENTIFICATIVO_SPORTELLO_DEST) {

	IDENTIFICATIVO_SPORTELLO_DEST = iDENTIFICATIVO_SPORTELLO_DEST;
    }

    public String getPEC_SPORTELLO_DEST() {

	return PEC_SPORTELLO_DEST;
    }

    public void setPEC_SPORTELLO_DEST(String pEC_SPORTELLO_DEST) {

	PEC_SPORTELLO_DEST = pEC_SPORTELLO_DEST;
    }

    public String getCOD_MOV_RIENTRO_INTEGRAZ_DOC() {

	return COD_MOV_RIENTRO_INTEGRAZ_DOC;
    }

    public void setCOD_MOV_RIENTRO_INTEGRAZ_DOC(String cOD_MOV_RIENTRO_INTEGRAZ_DOC) {

	COD_MOV_RIENTRO_INTEGRAZ_DOC = cOD_MOV_RIENTRO_INTEGRAZ_DOC;
    }

    public String getCOD_MOV_PRATICA_CONFORME() {

	return COD_MOV_PRATICA_CONFORME;
    }

    public void setCOD_MOV_PRATICA_CONFORME(String cOD_MOV_PRATICA_CONFORME) {

	COD_MOV_PRATICA_CONFORME = cOD_MOV_PRATICA_CONFORME;
    }

    public String getCOD_MOV_PRATICA_NON_CONFORME() {

	return COD_MOV_PRATICA_NON_CONFORME;
    }

    public void setCOD_MOV_PRATICA_NON_CONFORME(String cOD_MOV_PRATICA_NON_CONFORME) {

	COD_MOV_PRATICA_NON_CONFORME = cOD_MOV_PRATICA_NON_CONFORME;
    }

    public String getSALVA_XML_COMUNICAZIONE() {

	return SALVA_XML_COMUNICAZIONE;
    }

    public void setSALVA_XML_COMUNICAZIONE(String sALVA_XML_COMUNICAZIONE) {

	SALVA_XML_COMUNICAZIONE = sALVA_XML_COMUNICAZIONE;
    }

    public void setParametri(List<it.gruppoinit.sigepro.schemas.messages.regole.ParametroType> parametri) {

	this.parametri = parametri;
    }
}
