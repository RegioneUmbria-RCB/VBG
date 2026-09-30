package it.gruppoinit.pal.gp.core.features.attivita.istanze.query;

public class QueryRecuperaAttivaAllaData {

    public QueryRecuperaAttivaAllaData() {

	super();
    }

    public String getSql() {

	return "SELECT COUNT(*) as id, azione as descrizione FROM " + //
		"   istanze " + //
		"INNER JOIN STATIISTANZA ON  " + //
		"     statiistanza.idcomune =istanze.idcomune and statiistanza.codicestato=istanze.chiusura and statiistanza.software =istanze.software " + //
		"WHERE " + //
		"    istanze.idcomune = ? AND   fk_idi_attivita = ? " + //
		"    AND   datavalidita <= ? " + //
		//"    AND   COALESCE(datavalidita, " + getSQLDataValidita() + ") <= ? " + //
		"    AND ISTANZE.AZIONE <> ? " +
		"    AND STATIISTANZA.FKCODCOMPORTAMENTO>? " + //
		"GROUP BY azione ORDER BY    COUNT(*)";
    }
}
