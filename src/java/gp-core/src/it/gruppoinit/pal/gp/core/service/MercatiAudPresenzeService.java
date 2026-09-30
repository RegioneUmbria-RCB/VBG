package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MercatiAudPresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface MercatiAudPresenzeService extends BaseService<MercatiAudPresenze, PkId>{
    
    public void spostaPresenze(Integer idsorgente, Integer iddestinazione);
    
}
