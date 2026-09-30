package it.gruppoinit.pal.gp.core.filters;

import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;

/**
 * Classe di utilità per creare velocemente campi di filtro e ordinamento
 * 
 * @author riccardob
 * 
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class FilterUtils {

    public static FilterField<?> endsWith(String property, String valore) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.ENDSWITH, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> endsWith(String property, String valore, String associationPath) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.ENDSWITH, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> notEndsWith(String property, String valore) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.NOTENDSWITH, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> notEndsWith(String property, String valore, String associationPath) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.NOTENDSWITH, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> equals(String property, Object valore, Class<?> type) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.EQ, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> equals(String property, Object valore, String associationPath, Class<?> type) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.EQ, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> equalsIgnoreCase(String property, String valore) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.EQIGNORECASE, new Object[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> equalsIgnoreCase(String property, String valore, String associationPath) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.EQIGNORECASE, new Object[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> greater(String property, Object valore, String associationPath, Class<?> type) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.GT, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> greaterEqual(String property, Object valore, Class<?> type) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.GE, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> greaterEqual(String property, Object valore, String associationPath, Class<?> type) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.GE, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> greater(String property, Object valore, Class<?> type) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.GT, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> in(String property, Object[] valore, Class<?> type) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.IN, valore, type);
	return result;
    }

    public static FilterField<?> in(String property, Object[] valore, String associationPath, Class<?> type) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.IN, valore, type);
	return result;
    }

    public static FilterField<?> like(String property, String valore) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.CONTAINS, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> like(String property, String valore, String associationPath) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.CONTAINS, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> isEmpty(String collectionProperty) {

	FilterField<?> result = new FilterField(collectionProperty, FieldOperationsEnum.ISEMPTY, null, null);
	return result;
    }

    public static FilterField<?> isEmpty(String collectionProperty, String associationPath) {

	FilterField<?> result = new FilterField(collectionProperty, associationPath, FieldOperationsEnum.ISEMPTY, null, null);
	return result;
    }

    public static FilterField<?> isNotEmpty(String collectionProperty) {

	FilterField<?> result = new FilterField(collectionProperty, FieldOperationsEnum.ISNOTEMPTY, null, null);
	return result;
    }

    public static FilterField<?> isNotEmpty(String collectionProperty, String associationPath) {

	FilterField<?> result = new FilterField(collectionProperty, associationPath, FieldOperationsEnum.ISNOTEMPTY, null, null);
	return result;
    }

    public static FilterField<?> isNull(String property) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.ISNULL, null, null);
	return result;
    }

    public static FilterField<?> isNull(String property, String associationPath) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.ISNULL, null, null);
	return result;
    }

    public static FilterField<?> isNotNull(String property) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.ISNOTNULL, null, null);
	return result;
    }

    public static FilterField<?> isNotNull(String property, String associationPath) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.ISNOTNULL, null, null);
	return result;
    }

    public static FilterField<?> notEquals(String property, Object valore, Class<?> type) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.NE, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> notEquals(String property, Object valore, String associationPath, Class<?> type) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.NE, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> smaller(String property, Object valore, Class<?> type) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.LT, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> smaller(String property, Object valore, String associationPath, Class<?> type) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.LT, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> smallerEqual(String property, Object valore, Class<?> type) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.LE, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> smallerEqual(String property, Object valore, String associationPath, Class<?> type) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.LE, new Object[] { valore }, type);
	return result;
    }

    public static FilterField<?> startsWith(String property, String valore) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.STARTSWITH, new String[] { valore }, String.class);
	return result;
    }
    
    public static FilterField<?> startsWith(String property, String valore, String associationPath) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.STARTSWITH, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> notStartsWith(String property, String valore) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.NOTSTARTSWITH, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> notStartsWith(String property, String valore, String associationPath) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.NOTSTARTSWITH, new String[] { valore }, String.class);
	return result;
    }

    public static FilterField<?> between(String property, Object valoreBasso, Object valoreAlto, Class<?> type) {

	FilterField<?> result = new FilterField(property, FieldOperationsEnum.BETWEEN, new Object[] { valoreBasso, valoreAlto }, type);
	return result;
    }

    public static FilterField<?> between(String property, Object valoreBasso, Object valoreAlto, String associationPath, Class<?> type) {

	FilterField<?> result = new FilterField(property, associationPath, FieldOperationsEnum.BETWEEN, new Object[] { valoreBasso, valoreAlto },
		type);
	return result;
    }

    // ///////////////////////// UTILITIES PER GLI ORDINAMENTI
    public static FilterOrder<?> order(String property, OrderTypeEnum ordinamento) {

	FilterField<?> filterField = new FilterField(property, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, ordinamento);
	return filterOrder;
    }

    public static FilterOrder<?> order(String property, String associationPath, OrderTypeEnum ordinamento) {

	FilterField<?> filterField = new FilterField(property, associationPath, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, ordinamento);
	return filterOrder;
    }

    public static FilterOrder<?> orderAsc(String property) {

	FilterField<?> filterField = new FilterField(property, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, OrderTypeEnum.ASC);
	return filterOrder;
    }

    public static FilterOrder<?> orderAsc(String property, String associationPath) {

	FilterField<?> filterField = new FilterField(property, associationPath, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, OrderTypeEnum.ASC);
	return filterOrder;
    }

    public static FilterOrder<?> orderDesc(String property) {

	FilterField<?> filterField = new FilterField(property, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, OrderTypeEnum.DESC);
	return filterOrder;
    }

    public static FilterOrder<?> orderDesc(String property, String associationPath) {

	FilterField<?> filterField = new FilterField(property, associationPath, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, OrderTypeEnum.DESC);
	return filterOrder;
    }

    public static FilterOrder<?> orderAsc(String property, FunctionsEnum orderByFunction, String... orderFunctionParams) {

	FilterField<?> filterField = new FilterField(property, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, OrderTypeEnum.ASC, orderByFunction, orderFunctionParams);
	return filterOrder;
    }

    public static FilterOrder<?> orderAsc(String property, String associationPath, FunctionsEnum orderByFunction, String... orderFunctionParams) {

	FilterField<?> filterField = new FilterField(property, associationPath, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, OrderTypeEnum.ASC, orderByFunction, orderFunctionParams);
	return filterOrder;
    }

    public static FilterOrder<?> orderDesc(String property, FunctionsEnum orderByFunction, String... orderFunctionParams) {

	FilterField<?> filterField = new FilterField(property, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, OrderTypeEnum.DESC, orderByFunction, orderFunctionParams);
	return filterOrder;
    }

    public static FilterOrder<?> orderDesc(String property, String associationPath, FunctionsEnum orderByFunction, String... orderFunctionParams) {

	FilterField<?> filterField = new FilterField(property, associationPath, null, null);
	FilterOrder<?> filterOrder = new FilterOrder(filterField, OrderTypeEnum.DESC, orderByFunction, orderFunctionParams);
	return filterOrder;
    }
}
