/**
 * 
 */
package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.renderer.HtmlRowRendererImpl;


/**
 * Rowrenderer di jMesa utilizzato per evidenziare la PEC selezionata nella tabella di PEC inbox.
 * @author francol
 *
 */
public class PECInboxSelectedRowRenderer extends HtmlRowRendererImpl {
    
    public static final String SELECTED_PEC_SESSION_ATTR = "SELECTED_PEC";
    
    private String selectedRowClass;

    @Override
    public Object render(Object item, int rowcount) {

        HtmlBuilder html = new HtmlBuilder();
        html.tr(1);
        html.id(getCoreContext().getLimit().getId() + "_row" + rowcount);
        html.style(getStyle());
        String rowstyle = getStyleClass(rowcount);
        String tempHighlight = getHighlightClass();
        //String tempOdd = getOddClass();
        boolean selected = false;
        if (StringUtils.isNotBlank(getSelectedRowClass())) {
	    //verifico se la riga che sto renderizzando è quella della PEC selzionata
	    Object selectedPec = getWebContext().getSessionAttribute(SELECTED_PEC_SESSION_ATTR);
	    if (null != selectedPec) {
		String selectedId = (String) selectedPec;
		PECMessageHelper pec = (PECMessageHelper) item;
		if (selectedId.equals(pec.getIdentificativo())) {
		    selected = true;
		    rowstyle = getStyleClass();
		    setStyleClass(getSelectedRowClass());
		    setHighlightClass(getSelectedRowClass());
		}
	    }
	}
	html.styleClass(getStyleClass(rowcount));

        html.append(getRowEvents(item, rowcount));
        
        if(selected){
            /*
            setOddClass(tempOdd);
            setEvenClass(tempEven);
            */
            setStyleClass(rowstyle);
            setHighlightClass(tempHighlight);
        }
	
        html.close();

        return html.toString();
    }

    
    public String getSelectedRowClass() {
	
	if(StringUtils.isBlank(selectedRowClass)){
	    return getHighlightClass();
	}
    
        return selectedRowClass;
    }

    
    public void setSelectedRowClass(String selectedRowClass) {
    
        this.selectedRowClass = selectedRowClass;
    }
    
    
}
