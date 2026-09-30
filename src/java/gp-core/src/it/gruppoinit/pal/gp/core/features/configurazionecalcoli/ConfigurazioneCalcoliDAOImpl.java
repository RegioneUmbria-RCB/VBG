package it.gruppoinit.pal.gp.core.features.configurazionecalcoli;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneCalcoli;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class ConfigurazioneCalcoliDAOImpl extends BaseDAOImpl<ConfigurazioneCalcoli, PkId> implements IConfigurazioneCalcoliDAO {

    @Override
    public Class<ConfigurazioneCalcoli> getEntityClass() {

	return ConfigurazioneCalcoli.class;
    }
}
