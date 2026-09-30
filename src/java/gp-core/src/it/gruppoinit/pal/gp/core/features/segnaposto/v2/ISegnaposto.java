package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

import org.odftoolkit.simple.TextDocument;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;

public interface ISegnaposto {

    public String getNome();

    public boolean haArgomenti();

    public IOdtSubstitution sostituisciOdt(TextDocument currentDocument, String[] argomenti, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData);

    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException;

    public IRtfSubstitution sostituisciRtf(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData);
}
