/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

/**
 * @author francol
 *
 */
public interface IdAwareDomainObject<F extends Serializable> {
    
    public F getId();
    
    public void setId(F id);
    
}
