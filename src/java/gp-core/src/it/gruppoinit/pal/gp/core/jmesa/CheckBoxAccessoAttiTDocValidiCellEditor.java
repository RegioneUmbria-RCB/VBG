package it.gruppoinit.pal.gp.core.jmesa;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractCellEditor;

public class CheckBoxAccessoAttiTDocValidiCellEditor extends AbstractCellEditor {

    private String valoreZeroLabel;
    private String valoreUnoLabel;
    private String valoreDueLabel;

    public CheckBoxAccessoAttiTDocValidiCellEditor(String valoreZeroLabel, String valoreUnoLabel, String valoreDueLabel) {

	super();
	this.valoreZeroLabel = valoreZeroLabel;
	this.valoreUnoLabel = valoreUnoLabel;
	this.valoreDueLabel = valoreDueLabel;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	Object parametro = null;
	parametro = UtilityJmesa.getParametro(oggetto, "codiceistanza");
	String valueItem = "";
	//valueItem = "<input type=\"checkbox\" value=\"" + parametro + "\" id=\"flg_visualizza_doc_validi_id" + parametro + "" +
	//	    "\" class=\"lista_flg_visualizza_doc_validi_cls\" onclick=\"selectFlgVisualizzaDocValidi();\"/>";	
	valueItem = "<select id=\"flg_visualizza_doc_validi_id" + parametro +
		    "\" class=\"lista_flg_visualizza_doc_validi_cls\" onchange='selectFlgVisualizzaDocValidi()'>"; //
	valueItem += "<option value='" + parametro + "-0'>" + valoreZeroLabel + "</option>"; //
	valueItem += "<option value='" + parametro + "-2'>" + valoreDueLabel + "</option>"; //
	valueItem += "<option value='" + parametro + "-1'>" + valoreUnoLabel + "</option>"; //														
	valueItem += "</select>";//;
	return valueItem;
    }
}
