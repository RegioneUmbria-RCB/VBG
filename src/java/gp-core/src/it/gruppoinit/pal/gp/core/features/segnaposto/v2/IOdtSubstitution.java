package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

import org.odftoolkit.simple.common.navigation.TextSelection;

public interface IOdtSubstitution {

    void applyTo(TextSelection selection);

    void applyTo(org.odftoolkit.simple.text.list.List odtList, int indiceSegnaposto);
}
