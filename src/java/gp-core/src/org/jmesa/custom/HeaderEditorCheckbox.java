/**
 * 
 */
package org.jmesa.custom;

import org.jmesa.view.editor.AbstractHeaderEditor;

/**
 * @author francescop
 * 
 */
public class HeaderEditorCheckbox extends AbstractHeaderEditor {

    @Override
    public Object getValue() {

	return "<input type=\"checkbox\" checked=\"checked\" onclick=\"changeStatus();\">";
    }
}
