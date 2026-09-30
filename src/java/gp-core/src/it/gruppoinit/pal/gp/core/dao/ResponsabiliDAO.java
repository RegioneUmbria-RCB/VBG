package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.List;

public interface ResponsabiliDAO extends BaseDAO<Responsabili, PkId> {

    public Responsabili findByUserid(String userid);

    public List<Responsabili> findByFilter(Responsabili responsabili);

    public List<Responsabili> findAllByAbilitati();

    public List<Responsabili> findResponsabiliProcedimento(Responsabili responsabili);

    public List<Responsabili> findResponsabiliIstruttoria(Responsabili responsabili);

    /**
     * Una lista di Responsabili filtrata per idcomune e ordinata per responsabile asc
     */
    public List<Responsabili> findAll(Integer firstResult, Integer maxResult);

    /**
     * La funzione controlla se tra gli operatori censiti ce ne sia almeno uno con il flag flagBloccaOneri settato a
     * true
     * 
     * @return
     */
    public boolean isAbilitaBloccaOneri();

    public CodiceDescrizioneBean findDescrizioneById(Integer codiceOperatore);
}
