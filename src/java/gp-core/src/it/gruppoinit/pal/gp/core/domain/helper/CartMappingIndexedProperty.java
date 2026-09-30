/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Collection;

/**
 * @author francol
 * 
 */
public class CartMappingIndexedProperty {

    private String propertyPath;
    private Collection<Object> propertyData;

    public CartMappingIndexedProperty() {

    }

    public CartMappingIndexedProperty(String propertyPath, Collection<Object> propertyData) {

	this.propertyPath = propertyPath;
	this.propertyData = propertyData;
    }

    public String getPropertyPath() {

	return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {

	this.propertyPath = propertyPath;
    }

    public Collection<Object> getPropertyData() {

	return propertyData;
    }

    public void setPropertyData(Collection<Object> propertyData) {

	this.propertyData = propertyData;
    }
}
