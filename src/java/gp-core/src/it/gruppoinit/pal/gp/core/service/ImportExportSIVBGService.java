package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.web.ImportExportSIVBGCommand;

public interface ImportExportSIVBGService {

    public void exportSIVBG(String url, ImportExportSIVBGCommand command);

    public void exportSIVBGendo(String url, ImportExportSIVBGCommand command);

    public void importSIVBG(String url, ImportExportSIVBGCommand command);

    public void getListaVersioni(String url, ImportExportSIVBGCommand command);
}
