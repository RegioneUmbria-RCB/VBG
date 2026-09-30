/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class MercatiConfigurazioneDAOImpl extends BaseDAOImpl<MercatiConfigurazione, MercatiConfigurazioneId> implements MercatiConfigurazioneDAO {

    @Override
    public Class<MercatiConfigurazione> getEntityClass() {

	return MercatiConfigurazione.class;
    }

    @Override
    public MercatiConfigurazione findConfigurazione() {

	MercatiConfigurazioneId id = new MercatiConfigurazioneId(ORMHelper.getSoftware());
	MercatiConfigurazione mercatiConfigurazione = this.findById(id);
	return mercatiConfigurazione;
    }
}
