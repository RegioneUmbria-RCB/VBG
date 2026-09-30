package it.gruppoinit.pal.gp.core.filters;

/**
 * 
 * Contiene la lista delle operazione che un campo può usare per 'applicazione dei filtri
 * <ul>
 * <li><b>BETWEEN</b> - Usato per confrontare date o range di valori. Applica una condizione
 * <code>nomecampo between valore1 and valore2</code>
 * <li><b>CONTAINS</b> - Usato per confrontare stringhe. Applica una condizione
 * <code>lower(nomecampo) like lower('%valore%')</code></li>
 * <li><b>EQ</b> - Usato per tutti i confronti. Applica una condizione <code>nomecampo=valore)</code></li>
 * <li><b>EQIGNORECASE</b> - Usato per i confronti tra stringhe. Applica una condizione
 * <code>uppper(nomecampo) like upper(valore)</code></li>
 * <li><b>EXISTS</b> - Applica una condizione <code>exist</code></li>
 * <li><b>GE</b> - Usato per tutti i confronti. Applica una condizione <code>nomecampo&gt;=valore)</code></li>
 * <li><b>GT</b> - Usato per tutti i confronti. Applica una condizione <code>nomecampo&gt;valore)</code></li>
 * <li><b>IN</b> - Usato per tutti i confronti. Applica una condizione <code>nomecampo in (valore,valore1))</code></li>
 * <li><b>ISEMPTY</b> - Usato per valutare che una collezione di oggetti sia vuota. Applica una condizione
 * <code>oggetto.collectionproperty.size()=0</code></li>
 * <li><b>ISNOTEMPTY</b> -Usato per valutare che una collezione di oggetti non sia vuota. Applica una condizione
 * <code>oggetto.collectionproperty.size()>0</code></li>
 * <li><b>ISNOTNULL</b> - Usato per tutti i confronti. Applica una condizione <code>not  nomecampo isnull</code></li>
 * <li><b>ISNULL</b> - Usato per tutti i confronti. Applica una condizione <code>nomecampo isnull</code></li>
 * <li><b>LE</b> - Usato per tutti i confronti. Applica una condizione <code>nomecampo&lt;=valore </code></li>
 * <li><b>LT</b> - Usato per tutti i confronti. Applica una condizione <code>nomecampo&lt;valore </code></li>
 * <li><b>NE</b> - Usato per tutti i confronti. Applica una condizione <code>nomecampo&lt;&gt;valore </code></li>
 * <li><b>NOTEXISTS</b> - Applica una condizione <code>notexist</code></li>
 * <li><b>NOTIN</b> - Usato per tutti i confronti. Applica una condizione
 * <code>nomecampo not in (valore,valore1))</code></li>
 * <li><b>STARTSWITH</b> - Usato per confrontare stringhe. Applica una condizione
 * <code>lower(nomecampo) like lower('valore%')</code></li>
 * <li><b>NOTSTARTSWITH</b> - Usato per confrontare stringhe. Applica una condizione
 * <code>lower(nomecampo) not like lower('valore%')</code></li>
 * <li><b>ENDSWITH</b> - Usato per confrontare stringhe. Applica una condizione
 * <code>lower(nomecampo) like lower('%valore')</code></li>
 * <li><b>NOTENDSWITH</b> - Usato per confrontare stringhe. Applica una condizione
 * <code>lower(nomecampo) not like lower('%valore')</code></li>
 * </ul>
 */
public enum FieldOperationsEnum {
    BETWEEN(""), CONTAINS("è simile"), EQ("è uguale a"), EQIGNORECASE(""), EXISTS(""), EXISTS_LIKE(""), GE(
	    "è maggiore uguale a"), GT("è maggiore a"), IN(""), ISEMPTY(""), ISNOTEMPTY(""), ISNOTNULL(""), ISNULL(""), LE("è minore uguale a"), LT(
	    "è minore a"), NE("non è uguale a"), NOTEXISTS(""), NOTIN(""), STARTSWITH("inizia per"), NOTSTARTSWITH("non inizia per"), ENDSWITH("finisce per"), NOTENDSWITH("non finisce per");

    private String statusCode;

    private FieldOperationsEnum(String s) {

	statusCode = s;
    }

    public String getStatusCode() {

	return statusCode;
    }
}
