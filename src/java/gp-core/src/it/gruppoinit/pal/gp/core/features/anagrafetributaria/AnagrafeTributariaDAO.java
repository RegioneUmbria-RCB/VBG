package it.gruppoinit.pal.gp.core.features.anagrafetributaria;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AtEsitiErroreTracciato;
import it.gruppoinit.pal.gp.core.domain.AtEsitoErrori;
import it.gruppoinit.pal.gp.core.domain.AtEsitoGruppo;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ListaEsitiTracciatoBean;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.json.AnTribDettaglioRigheTracciato;
import it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser.IATParserProvvedimento;

public interface AnagrafeTributariaDAO extends BaseDAO {

    int countGruppiPerTestata(Integer idTestata);

    List<Integer> cercaIstanze(IATParserProvvedimento datiProvvedimento);

    List<ListaEsitiTracciatoBean> findEsitiSalvati(Integer offset, Integer limit);

    void eliminaEsito(Integer codice);

    List<AtEsitoErrori> findByAtEsitoGruppi(int fkidAtEsitoGruppo);

    List<AtEsitiErroreTracciato> findByAtEsitoErrori(int fkidAtesitoErrori);

    List<AnTribDettaglioRigheTracciato> findRigheTracciatoByAtEsitoGruppi(int fkidAtEsitoGruppo);

    List<AtEsitoGruppo> findByAtTestata(int idTestata, Integer offset, Integer limit);
}
