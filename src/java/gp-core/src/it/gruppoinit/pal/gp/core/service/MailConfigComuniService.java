package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MailConfigComuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.helper.MailConfigComuniDTO;

public interface MailConfigComuniService extends BaseService<MailConfigComuni, PkId> {

    List<Comuni> findComuniByMailConfigId(Integer id);

    List<MailConfigComuniDTO> findByMailConfigId(Integer id);

    List<Comuni> findComuneByDescrizione(String descComune);

    void insert(MailConfig mailConfig);

    void delete(Integer codice);

    void deleteByMailConfigId(Integer id);

    List<MailConfig> findAccountByResposabile(Responsabili responsabili);
}
