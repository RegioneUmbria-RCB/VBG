package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.service.helper.CvsRecordsHelper;

public interface ExportCsvService {

    public byte[] writeCsvFile(String fileName, CvsRecordsHelper<?> cvsRecordsHelper) throws Exception;
}
