package it.gruppoinit.pal.gp.core.filters;

import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class FilterRestriction {

    private Set<FilterField> filterFields;
    private AndOrRestriction andOrRestriction;

    public FilterRestriction() {

	this.filterFields = new LinkedHashSet<FilterField>();
	this.setAndOrRestriction(AndOrRestriction.AND);
    }

    public FilterRestriction(Set<FilterField> filterFields, AndOrRestriction andOrRestriction) {

	this.filterFields = filterFields;
	this.andOrRestriction = andOrRestriction;
    }

    public void setFilterFields(Set<FilterField> filterFields) {

	this.filterFields = filterFields;
    }

    public Set<FilterField> getFilterFields() {

	return this.filterFields;
    }

    public void addFilterField(FilterField filterField) {

	if (getFilterFields() == null) {
	    this.filterFields = new LinkedHashSet<FilterField>();
	}
	this.filterFields.add(filterField);
    }

    public void setAndOrRestriction(AndOrRestriction andOrRestriction) {

	this.andOrRestriction = andOrRestriction;
    }

    public AndOrRestriction getAndOrRestriction() {

	return andOrRestriction;
    }
}