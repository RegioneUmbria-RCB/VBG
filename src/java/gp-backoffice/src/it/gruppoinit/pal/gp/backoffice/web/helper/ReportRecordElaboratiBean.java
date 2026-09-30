package it.gruppoinit.pal.gp.backoffice.web.helper;

import java.util.ArrayList;
import java.util.List;

public class ReportRecordElaboratiBean {

    private int totaleRecord = 0;
    private int recordElaborati = 0;
    private List<String> errori;

    public ReportRecordElaboratiBean(int totaleRecord, int recordElaborati) {

	this.totaleRecord = totaleRecord;
	this.recordElaborati = recordElaborati;
    }

    public int getTotaleRecord() {

	return totaleRecord;
    }

    public void setTotaleRecord(int totaleRecord) {

	this.totaleRecord = totaleRecord;
    }

    public int getRecordElaborati() {

	return recordElaborati;
    }

    public void setRecordElaborati(int recordElaborati) {

	this.recordElaborati = recordElaborati;
    }

    public List<String> getErrori() {

	if (this.errori == null) {
	    this.errori = new ArrayList<String>();
	}
	return errori;
    }
}
