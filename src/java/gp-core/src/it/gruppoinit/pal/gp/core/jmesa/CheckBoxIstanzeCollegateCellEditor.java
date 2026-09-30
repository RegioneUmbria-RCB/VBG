package it.gruppoinit.pal.gp.core.jmesa;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractCellEditor;

public class CheckBoxIstanzeCollegateCellEditor extends AbstractCellEditor {

    private String cssClassName;

    public CheckBoxIstanzeCollegateCellEditor(String cssClassName) {

	this.cssClassName = cssClassName;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	Object parametro = null;
	parametro = UtilityJmesa.getParametro(oggetto, "codiceistanza");
	String valueItem = "";
	valueItem = "<input type=\"checkbox\" data-codiceistanza=\"" + parametro + "\" class=\"" + cssClassName + "\" />";
	return valueItem;
    }
}
