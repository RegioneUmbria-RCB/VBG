package org.jmesa.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExportTableHelper implements Serializable {

    private static final long serialVersionUID = 3173530502458970505L;
    private String tableCaption;
    private List<String> colonne;
    private List<String[]> righe;

    public List<String[]> getRighe() {

	if (this.righe == null) {
	    this.righe = new ArrayList<String[]>();
	}
	return righe;
    }

    public void setRighe(List<String[]> righe) {

	this.righe = righe;
    }

    public List<String> getColonne() {

	if (this.colonne == null) {
	    this.colonne = new ArrayList<String>();
	}
	return colonne;
    }

    public void setColonne(List<String> colonne) {

	this.colonne = colonne;
    }

    public void setTableCaption(String tableCaption) {

	this.tableCaption = tableCaption;
    }

    public String getTableCaption() {

	return tableCaption;
    }
}
