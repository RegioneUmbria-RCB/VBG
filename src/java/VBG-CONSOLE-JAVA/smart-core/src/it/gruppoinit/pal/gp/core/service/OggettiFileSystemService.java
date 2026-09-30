package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemStatusBean;

public interface OggettiFileSystemService extends BaseService<Oggetti, PkId> {

    public int spostaBlobSuFileSystem(boolean setBlobNull);
    public int ottimizzaFileSystem();
    
    public int countDocumentsAtRoot();
    
    public int countDocumentsInBlob();
    
    public String getFileRepositoryPath();
    
    public OggettiFileSystemStatusBean getStatus();
}
