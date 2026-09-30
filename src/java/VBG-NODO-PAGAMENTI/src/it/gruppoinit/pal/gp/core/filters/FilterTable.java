package it.gruppoinit.pal.gp.core.filters;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;

import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class FilterTable {

    private Set<FilterRestriction> restrictions;
    private Set<FilterOrder> orderings;
    private DAOEnum defaultWhere;

    private FilterTable() {

	this.restrictions = new LinkedHashSet<FilterRestriction>();
	this.orderings = new LinkedHashSet<FilterOrder>();
    }

    public FilterTable(DAOEnum defaultWhere) {

	this();
	this.defaultWhere = defaultWhere;
    }

    public void setRestrictions(Set<FilterRestriction> restrictions) {

	this.restrictions = restrictions;
    }

    public Set<FilterRestriction> getRestrictions() {

	return this.restrictions;
    }

    public void setOrderings(Set<FilterOrder> orderings) {

	this.orderings = orderings;
    }

    public Set<FilterOrder> getOrderings() {

	return this.orderings;
    }

    public void addRestriction(FilterRestriction restriction) {

	if (getRestrictions() == null) {
	    this.restrictions = new LinkedHashSet<FilterRestriction>();
	}
	this.restrictions.add(restriction);
    }

    public void addOrder(FilterOrder ordering) {

	if (getOrderings() == null) {
	    this.orderings = new LinkedHashSet<FilterOrder>();
	}
	this.orderings.add(ordering);
    }

    public DAOEnum getDefaultWhere() {

	return defaultWhere;
    }

    public void setDefaultWhere(DAOEnum defaultWhere) {

	this.defaultWhere = defaultWhere;
    }
}