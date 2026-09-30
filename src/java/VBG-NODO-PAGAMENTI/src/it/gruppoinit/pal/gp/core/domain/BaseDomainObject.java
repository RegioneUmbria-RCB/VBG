/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

/**
 * @author francol
 *
 */
public abstract class BaseDomainObject {

    @Override
    public boolean equals(Object obj) {

	if(obj == null) {
	    return false;
	}
	if(IdAwareDomainObject.class.isAssignableFrom(obj.getClass())) {
		return ((IdAwareDomainObject)this).getId().equals(((IdAwareDomainObject)obj).getId());
	}
	else {
	    return false;
	}
    }

    @Override
    public int hashCode() {

	if(this instanceof IdAwareDomainObject<?>) {
	    Object id = ((IdAwareDomainObject)this).getId();
	    return id.hashCode();
	}
	return super.hashCode();
    }
    
    
}
