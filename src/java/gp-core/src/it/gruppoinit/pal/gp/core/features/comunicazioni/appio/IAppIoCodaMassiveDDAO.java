package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveD;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveDId;

public interface IAppIoCodaMassiveDDAO extends BaseDAO<AppIoCodaMassiveD, AppIoCodaMassiveDId> {

    public List<AppIoCodaMassiveD> findByIdDettaglioMassiveD(int idDettaglioComunicazione);
}
