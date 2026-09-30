package it.gruppoinit.pal.gp.core.scheduler.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IstanzaDaElaborareDTO {

    private String IDCOMUNE;
    private String CODICECOMUNE;
    private String CODICEPRATICATEL;
    private String CODICEISTANZA;
    private String NUMEROISTANZA;
    private String DATAISTANZA;
    //protocollo
    private String NUMEROPROTOCOLLO;
    private String DATAPROTOCOLLO;
    //richiedente
    private String NOMERICHIEDENTE;
    private String NOMINATIVORICHIEDENTE;
    private String CFRICHIEDENTE;
    private String PIVARICHIEDENTE;
    //autorizzazione
    private String AUTORIZNUMERO;
    private String AUTORIZDATA;
    // dati scheda
    private String DENOMINAZIONE;
    private String COMUNE_SVOLGIMENTO;
    private String LUOGO_SVOLGIMENTO;
    private String CLASSIFICAZIONE;
    private String QUALIFICA;
    private String TIPOLOGIA;
    private List<Periodo> PERIODI = new ArrayList<Periodo>();
    private List<String> MERCEOLOGIE = new ArrayList<String>();

    public String getIDCOMUNE() {

	return IDCOMUNE;
    }

    public void setIDCOMUNE(String iDCOMUNE) {

	IDCOMUNE = iDCOMUNE;
    }

    public String getCODICEPRATICATEL() {

	return CODICEPRATICATEL;
    }

    public void setCODICEPRATICATEL(String cODICEPRATICATEL) {

	CODICEPRATICATEL = cODICEPRATICATEL;
    }

    public String getCODICEISTANZA() {

	return CODICEISTANZA;
    }

    public void setCODICEISTANZA(String cODICEISTANZA) {

	CODICEISTANZA = cODICEISTANZA;
    }

    public String getNUMEROISTANZA() {

	return NUMEROISTANZA;
    }

    public void setNUMEROISTANZA(String nUMEROISTANZA) {

	NUMEROISTANZA = nUMEROISTANZA;
    }

    public String getDATAISTANZA() {

	return DATAISTANZA;
    }

    public void setDATAISTANZA(String dATAISTANZA) {

	DATAISTANZA = dATAISTANZA;
    }

    public String getNUMEROPROTOCOLLO() {

	return NUMEROPROTOCOLLO;
    }

    public void setNUMEROPROTOCOLLO(String nUMEROPROTOCOLLO) {

	NUMEROPROTOCOLLO = nUMEROPROTOCOLLO;
    }

    public String getDATAPROTOCOLLO() {

	return DATAPROTOCOLLO;
    }

    public void setDATAPROTOCOLLO(String dATAPROTOCOLLO) {

	DATAPROTOCOLLO = dATAPROTOCOLLO;
    }

    public String getNOMERICHIEDENTE() {

	return NOMERICHIEDENTE;
    }

    public void setNOMERICHIEDENTE(String nOMERICHIEDENTE) {

	NOMERICHIEDENTE = nOMERICHIEDENTE;
    }

    public String getNOMINATIVORICHIEDENTE() {

	return NOMINATIVORICHIEDENTE;
    }

    public void setNOMINATIVORICHIEDENTE(String nOMINATIVORICHIEDENTE) {

	NOMINATIVORICHIEDENTE = nOMINATIVORICHIEDENTE;
    }

    public String getCFRICHIEDENTE() {

	return CFRICHIEDENTE;
    }

    public void setCFRICHIEDENTE(String cFRICHIEDENTE) {

	CFRICHIEDENTE = cFRICHIEDENTE;
    }

    public String getAUTORIZNUMERO() {

	return AUTORIZNUMERO;
    }

    public void setAUTORIZNUMERO(String aUTORIZNUMERO) {

	AUTORIZNUMERO = aUTORIZNUMERO;
    }

    public String getAUTORIZDATA() {

	return AUTORIZDATA;
    }

    public void setAUTORIZDATA(String aUTORIZDATA) {

	AUTORIZDATA = aUTORIZDATA;
    }

    public String getDENOMINAZIONE() {

	return DENOMINAZIONE;
    }

    public void setDENOMINAZIONE(String dENOMINAZIONE) {

	DENOMINAZIONE = dENOMINAZIONE;
    }

    public String getCOMUNE_SVOLGIMENTO() {

	return COMUNE_SVOLGIMENTO;
    }

    public void setCOMUNE_SVOLGIMENTO(String cOMUNE_SVOLGIMENTO) {

	COMUNE_SVOLGIMENTO = cOMUNE_SVOLGIMENTO;
    }

    public String getLUOGO_SVOLGIMENTO() {

	return LUOGO_SVOLGIMENTO;
    }

    public void setLUOGO_SVOLGIMENTO(String lUOGO_SVOLGIMENTO) {

	LUOGO_SVOLGIMENTO = lUOGO_SVOLGIMENTO;
    }

    public String getCLASSIFICAZIONE() {

	return CLASSIFICAZIONE;
    }

    public void setCLASSIFICAZIONE(String cLASSIFICAZIONE) {

	CLASSIFICAZIONE = cLASSIFICAZIONE;
    }

    public String getQUALIFICA() {

	return QUALIFICA;
    }

    public void setQUALIFICA(String qUALIFICA) {

	QUALIFICA = qUALIFICA;
    }

    public List<Periodo> getPERIODI() {

	return PERIODI;
    }

    public void setPERIODI(List<Periodo> pERIODI) {

	PERIODI = pERIODI;
    }

    public List<String> getMERCEOLOGIE() {

	return MERCEOLOGIE;
    }

    public void setMERCEOLOGIE(List<String> mERCEOLOGIE) {

	MERCEOLOGIE = mERCEOLOGIE;
    }

    public String getCODICECOMUNE() {

	return CODICECOMUNE;
    }

    public void setCODICECOMUNE(String cODICECOMUNE) {

	CODICECOMUNE = cODICECOMUNE;
    }

    public String getTIPOLOGIA() {

	return TIPOLOGIA;
    }

    public void setTIPOLOGIA(String tIPOLOGIA) {

	TIPOLOGIA = tIPOLOGIA;
    }

    public String getPIVARICHIEDENTE() {

	return PIVARICHIEDENTE;
    }

    public void setPIVARICHIEDENTE(String pIVARICHIEDENTE) {

	PIVARICHIEDENTE = pIVARICHIEDENTE;
    }
}

class Periodo {

    private Date DAL;
    private Date AL;

    public Periodo(Date DAL, Date AL) {

	this.DAL = DAL;
	this.AL = AL;
    }

    public Date getDAL() {

	return DAL;
    }

    public void setDAL(Date dAL) {

	DAL = dAL;
    }

    public Date getAL() {

	return AL;
    }

    public void setAL(Date aL) {

	AL = aL;
    }
}
