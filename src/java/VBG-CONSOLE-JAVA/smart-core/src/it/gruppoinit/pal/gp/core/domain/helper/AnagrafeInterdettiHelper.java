package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.web.AnagrafeInterdettiCommand;

import java.util.ArrayList;
import java.util.List;

public class AnagrafeInterdettiHelper {

    private List<AnagrafeInterdettiCommand> listaAnagrafeInterdettiDaImportare = new ArrayList<AnagrafeInterdettiCommand>();
    private List<AnagrafeInterdettiCommand> listaAnagrafeInterdettiIncongruenti = new ArrayList<AnagrafeInterdettiCommand>();
    private Integer totaleRecord;
    private Integer recordImportati;
    private Integer recordScartati;
    private Integer recordNonAggiornati;

    public AnagrafeInterdettiHelper() {

	this.recordImportati = Integer.valueOf(0);
	this.totaleRecord = Integer.valueOf(0);
	this.recordScartati = Integer.valueOf(0);
	this.recordNonAggiornati = Integer.valueOf(0);
    }

    public List<AnagrafeInterdettiCommand> getListaAnagrafeInterdettiDaImportare() {

	return listaAnagrafeInterdettiDaImportare;
    }

    public void setListaAnagrafeInterdettiDaImportare(List<AnagrafeInterdettiCommand> listaAnagrafeInterdettiDaImportare) {

	this.listaAnagrafeInterdettiDaImportare = listaAnagrafeInterdettiDaImportare;
    }

    public List<AnagrafeInterdettiCommand> getListaAnagrafeInterdettiIncongruenti() {

	return listaAnagrafeInterdettiIncongruenti;
    }

    public void setListaAnagrafeInterdettiIncongruenti(List<AnagrafeInterdettiCommand> listaAnagrafeInterdettiIncongruenti) {

	this.listaAnagrafeInterdettiIncongruenti = listaAnagrafeInterdettiIncongruenti;
    }

    public Integer getTotaleRecord() {

	return totaleRecord;
    }

    public void setTotaleRecord(Integer totaleRecord) {

	this.totaleRecord = totaleRecord;
    }

    public Integer getRecordImportati() {

	return recordImportati;
    }

    public void setRecordImportati(Integer recordImportati) {

	this.recordImportati = recordImportati;
    }

    public Integer getRecordScartati() {

	return recordScartati;
    }

    public void setRecordScartati(Integer recordScartati) {

	this.recordScartati = recordScartati;
    }

    public Integer getRecordNonAggiornati() {

	return recordNonAggiornati;
    }

    public void setRecordNonAggiornati(Integer recordNonAggiornati) {

	this.recordNonAggiornati = recordNonAggiornati;
    }
}
