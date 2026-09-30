package it.gruppoinit.pal.gp.core.filters;

import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;

public class FilterOrder<E> {

    private FilterField<E> filterField;
    private OrderTypeEnum sort;
    private FunctionsEnum orderFunction;
    private String[] orderFunctionParams;

    private FilterOrder() {

	this.sort = OrderTypeEnum.ASC;
	this.orderFunction = FunctionsEnum.NONE_FUNCTION;
    }

    public FilterOrder(FilterField<E> filterField, FunctionsEnum orderFunction, String... orderFunctionParams) {

	this(filterField);
	this.orderFunction = orderFunction;
	this.orderFunctionParams = orderFunctionParams;
    }

    public FilterOrder(FilterField<E> filterField) {

	this();
	this.filterField = filterField;
	this.orderFunction = FunctionsEnum.NONE_FUNCTION;
    }

    public FilterOrder(FilterField<E> filterField, OrderTypeEnum sort) {

	this.filterField = filterField;
	this.sort = sort;
	this.orderFunction = FunctionsEnum.NONE_FUNCTION;
    }

    public FilterOrder(FilterField<E> filterField, OrderTypeEnum sort, FunctionsEnum orderFunction, String... orderFunctionParams) {

	this(filterField, sort);
	this.orderFunction = orderFunction;
	this.orderFunctionParams = orderFunctionParams;
    }

    public FilterField<E> getFilterField() {

	return filterField;
    }

    public void setFilterField(FilterField<E> filterField) {

	this.filterField = filterField;
    }

    public OrderTypeEnum getSort() {

	return sort;
    }

    public void setSort(OrderTypeEnum sort) {

	this.sort = sort;
    }

    public FunctionsEnum getOrderFunction() {

	return orderFunction;
    }

    public String[] getOrderFunctionParams() {

	return orderFunctionParams;
    }
}