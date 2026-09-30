package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CfgMetadatiCmis;
import it.gruppoinit.pal.gp.core.domain.CfgMetadatiCmisId;

import java.util.List;

public interface CfgMetadatiCmisDAO extends BaseDAO<CfgMetadatiCmis, CfgMetadatiCmisId> {

    List<CfgMetadatiCmis> findByIdComune(String idcomune);

    boolean existConfigurazioneByIdComune(String idcomune);
}
