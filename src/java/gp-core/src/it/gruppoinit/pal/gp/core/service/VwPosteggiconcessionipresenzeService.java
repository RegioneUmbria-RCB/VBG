package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessionipresenze;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessionipresenzeId;

public interface VwPosteggiconcessionipresenzeService extends BaseService<VwPosteggiconcessionipresenze, VwPosteggiconcessionipresenzeId> {

    public List<VwPosteggiconcessionipresenze> findPosteggiConcessioniPresenze(MercatipresenzeT giorno);
}
