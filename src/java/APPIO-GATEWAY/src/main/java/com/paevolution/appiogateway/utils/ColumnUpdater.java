package com.paevolution.appiogateway.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Classe di utilità per la gestione della persistenza degli oggetti di dominio.
 * 
 * @author simone.vernata
 *
 */
public class ColumnUpdater {

    /**
     * Restituisce l'array dei nomi delle property che sono null dell'oggetto source
     * 
     * @param source
     * @return
     */
    public static String[] getNullPropertiesName(Object source) {

	final BeanWrapper src = new BeanWrapperImpl(source);
	PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();
	Set<String> emptyNames = new HashSet<String>();
	for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
	    Object srcValue = src.getPropertyValue(propertyDescriptor.getName());
	    if (srcValue == null)
		emptyNames.add(propertyDescriptor.getName());
	}
	String[] result = new String[emptyNames.size()];
	return emptyNames.toArray(result);
    }

    public static String[] getPropertiesToUpdate(Object source, Object destination) {

	BeanWrapper srcBean = new BeanWrapperImpl(source);
	BeanWrapper destBean = new BeanWrapperImpl(destination);
	PropertyDescriptor[] sourcePDs = srcBean.getPropertyDescriptors();
	PropertyDescriptor[] destPDs = destBean.getPropertyDescriptors();
	Set<String> propertiesNameToUpdate = new HashSet<String>();
	for (PropertyDescriptor sourcePD : sourcePDs) {
	    for (PropertyDescriptor destPD : destPDs) {
		if (sourcePD.getName().equalsIgnoreCase(destPD.getName())) {
		    Object destValue = destPD.getValue(destPD.getName());
		    Object sourceValue = sourcePD.getValue(sourcePD.getName());
		    if (sourceValue != null && destValue == null) {
			propertiesNameToUpdate.add(destPD.getName());
		    }
		}
	    }
	}
	String[] result = new String[propertiesNameToUpdate.size()];
	return propertiesNameToUpdate.toArray(result);
    }
}
