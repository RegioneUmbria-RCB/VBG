package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.helper.OneriPerCausaleHelper;

import java.net.URLEncoder;
import java.util.Map;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class ImportiLocalizzatiOnereCellEditor extends AbstractCellEditor {

    private Inventarioprocedimenti procediemnto;

    @SuppressWarnings("rawtypes")
    @Override
    public Object getValue(Object item, String property, int rowcount) {

	OneriPerCausaleHelper oggetto = (OneriPerCausaleHelper) item;
	Object retVal = oggetto != null ? oggetto.getOneriPerComune() : null;
	return retVal;
    }
}
