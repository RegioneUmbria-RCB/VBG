/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.helper;

import java.io.Serializable;

import org.hibernate.engine.spi.SessionImplementor;

import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * @author francol
 *
 */
public class IntegerIdGenerator extends PkIdGenerator {

    @Override
    public Serializable generate(SessionImplementor session, Object obj) {

	PkId pk = (PkId)super.generate(session, obj);
	return pk.getCodice();
    }
    
    
}
