package it.gruppoinit.pal.gp.core.features.segnaposto;

import org.odftoolkit.simple.table.Table;

public class TableODT {

    private Table table;
    private boolean bordi;

    public TableODT(Table table, boolean bordi) {

	super();
	this.table = table;
	this.bordi = bordi;
    }

    public Table getTable() {

	return table;
    }

    public boolean isBordi() {

	return bordi;
    }
}
