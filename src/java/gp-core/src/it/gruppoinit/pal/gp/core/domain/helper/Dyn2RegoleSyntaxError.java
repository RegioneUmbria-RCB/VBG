/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un errore di validazione della sintassi nelle espressioni che costituiscono una regola
 * dinamica. Può anche essere lanciata come eccezione durante la valutazione a runtime delle espressioni.
 * 
 * @author francol
 * 
 */
public class Dyn2RegoleSyntaxError extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int expressionIndex = -1;
    private List<String> wrongExpressionProperties = new ArrayList<String>();

    public Dyn2RegoleSyntaxError() {

	super();
    }
    
    

    public Dyn2RegoleSyntaxError(String message, Throwable cause) {

	super(message, cause);
    }



    public Dyn2RegoleSyntaxError(String message) {

	super(message);
    }



    public Dyn2RegoleSyntaxError(Throwable cause) {

	super(cause);
    }



    public int getExpressionIndex() {

	return expressionIndex;
    }

    public void setExpressionIndex(int expressionIndex) {

	this.expressionIndex = expressionIndex;
    }

    public List<String> getWrongExpressionProperties() {

	return this.wrongExpressionProperties;
    }

    public void addWrongExpressionProperty(String expressionProperty) {

	this.wrongExpressionProperties.add(expressionProperty);
    }
}
