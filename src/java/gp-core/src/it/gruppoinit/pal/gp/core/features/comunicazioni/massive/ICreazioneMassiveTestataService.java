package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ResocontoOperazioniMassive;

public interface ICreazioneMassiveTestataService {

    public int insert(IConfigurazioneComunicazione configurazioneComunicazione);

    public List<Integer> findByIdBollettazione(Integer idBollettazione);

    public List<ResocontoOperazioniMassive> findResocontoOperazioniMassiveById(Integer idTestata);

    public List<Integer> findByIdCommissioni(Integer idCommissioni);

    public List<Integer> findByIdMercato(Integer idMercato);

    List<Integer> findIdTestataByGen(String sql, Object[] params, String fkscalar);
}
