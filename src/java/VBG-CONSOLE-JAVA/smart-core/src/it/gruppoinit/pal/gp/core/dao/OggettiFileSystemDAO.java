package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemStatusBean;

public interface OggettiFileSystemDAO extends BaseDAO<Oggetti, PkId> {

    public int spostaBlobSuFileSystem(String rootPathOggetti, boolean setBlobNull, OggettiFileSystemStatusBean status);
    
    public int ottimizzaFileSystem(String rootPathOggetti, OggettiFileSystemStatusBean status);
    
    public int verificaIncongruenze(OggettiFileSystemStatusBean status);
    
    public int countDocumentsAtRoot();

    public int countDocumentsInBlob();
}
