package it.gruppoinit.stc.service;

import java.util.List;

import it.gruppoinit.stc.domain.Messaggiattivita;

public interface MessaggiattivitaService extends BaseService<Messaggiattivita, Integer> {

    public enum TIPO_COLLEGAMENTO {
	RICHIESTA,
	RISPOSTA
    }

    public void deleteCollegamento();

    List<Messaggiattivita> findByIdAttivita(int idAttivita, TIPO_COLLEGAMENTO collegamento);
}
