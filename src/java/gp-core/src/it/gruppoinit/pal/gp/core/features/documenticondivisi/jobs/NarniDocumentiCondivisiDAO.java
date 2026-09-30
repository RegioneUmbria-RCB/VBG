package it.gruppoinit.pal.gp.core.features.documenticondivisi.jobs;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.IDocumentiReader;

public interface NarniDocumentiCondivisiDAO extends BaseDAO, IDocumentiReader {

    public Integer getNumeroMassimoDocumenti();

    public void setNumeroMassimoDocumenti(Integer numeroMassimoDocumenti);
}
