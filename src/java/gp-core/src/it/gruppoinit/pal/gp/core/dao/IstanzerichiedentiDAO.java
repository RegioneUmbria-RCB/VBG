package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CfRichiedentiBean;

/**
 * 
 * @author francescop
 */
public interface IstanzerichiedentiDAO extends BaseDAO<Istanzerichiedenti, PkId> {

    public List<Istanzerichiedenti> findByIstanza(Istanze istanza);

    public List<CfRichiedentiBean> findBeanByCodiceIstanza(Integer codiceIstanza);
}
