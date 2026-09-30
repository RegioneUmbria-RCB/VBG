package it.gruppoinit.pal.gp.core.dao;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.MailConfigComuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

public interface MailConfigComuniDAO extends BaseDAO<MailConfigComuni, PkId> {

    List<MailConfigComuni> findByMailConfigId(Integer id);

    Set<MailConfigComuni> findListaByResponsabile(Responsabili responsabili);
}
