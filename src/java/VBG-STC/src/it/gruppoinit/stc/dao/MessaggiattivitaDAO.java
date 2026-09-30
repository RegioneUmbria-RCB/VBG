package it.gruppoinit.stc.dao;

import java.util.List;

import it.gruppoinit.stc.domain.Messaggiattivita;
import it.gruppoinit.stc.service.MessaggiattivitaService.TIPO_COLLEGAMENTO;

public interface MessaggiattivitaDAO extends BaseDAO<Messaggiattivita, Integer> {

    List<Messaggiattivita> findByIdAttivita(int idAttivita, TIPO_COLLEGAMENTO collegamento);
}
