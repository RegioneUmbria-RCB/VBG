package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.odftoolkit.simple.common.navigation.TextSelection;

public class OdtTextSubstitution implements IOdtSubstitution {

    private String value;

    public OdtTextSubstitution(String value) {

	this.value = value;
	if (this.value == null) {
	    this.value = "";
	}
    }

    @Override
    public void applyTo(TextSelection selection) {

	try {
	    selection.replaceWith(this.value);
	} catch (InvalidNavigationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public void applyTo(org.odftoolkit.simple.text.list.List odtList, int indiceSegnaposto) {

	odtList.addItem(this.value);
	odtList.removeItem(indiceSegnaposto);
    }
}
