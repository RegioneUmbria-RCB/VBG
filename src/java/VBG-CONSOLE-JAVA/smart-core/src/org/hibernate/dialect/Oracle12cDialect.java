package org.hibernate.dialect;

public class Oracle12cDialect extends Oracle10gDialect {

    public Oracle12cDialect() {

	super();
    }

    @Override
    public boolean useMaxForLimit() {

	return false;
    }

    @Override
    public boolean bindLimitParametersInReverseOrder() {

	return false;
    }

    @Override
    public String getLimitString(String sql, boolean hasOffset) {

	sql = sql.trim();
	boolean isForUpdate = false;
	if (sql.toLowerCase().endsWith(" for update")) {
	    sql = sql.substring(0, sql.length() - 11);
	    isForUpdate = true;
	}
	StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
	pagingSelect.append(sql);
	if (hasOffset) {
	    pagingSelect.append(" offset ? rows fetch next ? rows only");
	} else {
	    pagingSelect.append(" fetch first ? rows only");
	}
	if (isForUpdate) {
	    pagingSelect.append(" for update");
	}
	return pagingSelect.toString();
    }
}
