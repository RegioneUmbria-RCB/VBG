package it.gruppoinit.pal.gp.core.features.database;

public enum DialettoEnum {

    ORACLE,
    SQLSERVER,
    POSTGRES,
    MYSQL;

    public static DialettoEnum fromHibernateDialect(String hibernateDialect) {

	DialettoEnum dialetto = null;
	if (hibernateDialect.indexOf("Oracle") > 0) {
	    dialetto = DialettoEnum.ORACLE;
	} else if (hibernateDialect.indexOf("MySQL") > 0) {
	    dialetto = DialettoEnum.MYSQL;
	} else if (hibernateDialect.indexOf("PostgreSQL") > 0) {
	    dialetto = DialettoEnum.POSTGRES;
	} else if (hibernateDialect.indexOf("SQLServer") > 0) {
	    dialetto = DialettoEnum.SQLSERVER;
	} else {
	    throw new RuntimeException("Attenzione!! Il dialetto [" + hibernateDialect + "] non è supportato da questa funzione");
	}
	return dialetto;
    }
}