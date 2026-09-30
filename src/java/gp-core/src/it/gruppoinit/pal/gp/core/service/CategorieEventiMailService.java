package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.CategorieEventiMail;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface CategorieEventiMailService extends BaseService<CategorieEventiMail, PkId> {

    public enum TIPO_DESTINATARIO {
	OPERATORE, RESPONSABILEPROC, ISTRUTTORE
    }

    public List<CategorieEventiMail> findByIstanzeEventi(Istanzeeventi input);
}
