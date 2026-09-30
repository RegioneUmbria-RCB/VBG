package it.gruppoinit.pdfutils.service;

import it.gruppoinit.pdfutils.domain.PDFMappature;

import java.util.List;

public interface PDFMappingService {

    public List<PDFMappature> findAll(String alias, Integer firstResult, Integer maxResult);

    void insert(String alias, PDFMappature newconfigurazione);

    void update(String alias, PDFMappature configurazione);

    void delete(String alias, String label);
}
