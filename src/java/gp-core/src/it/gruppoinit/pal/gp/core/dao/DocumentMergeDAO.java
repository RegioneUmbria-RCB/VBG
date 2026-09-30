package it.gruppoinit.pal.gp.core.dao;

import org.odftoolkit.simple.TextDocument;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;

public interface DocumentMergeDAO extends BaseDAO {

    public String eseguiSostituzioniConQuery(String fileIn, String tipoFile, DocumentMergeHelper mergeHelper);

    public TextDocument eseguiSostituzioniConQuery(TextDocument fileIn, String tipoFile, DocumentMergeHelper mergeHelper);
}
