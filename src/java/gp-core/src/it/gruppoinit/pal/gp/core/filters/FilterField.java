package it.gruppoinit.pal.gp.core.filters;

/**
 * Rappresenta un campo da filtrare <br />
 * Un campo è composto da:
 * <ul>
 * <li>Una proprietà (nome della property sulla quale verrà applicato il filtro) ad es.: <b>"id.tipomovimento"</b></li>
 * <li>Un path di associazione (se la property è di una entità diversa da quella di base allora deve essere indicata la
 * lista degli alias per i quali arrivare alla property) ad. Esempio se la proterty appartiene a tipimovimento e
 * l'entity principale è istanze allora l'association Path è <b>"istanzemovimentis.tipomovimento"</b></li>
 * <li>Un tipo di operazione (indica il tipo di confronto sul quale applicare il filtro in relazione al valore passato)
 * vedi {@link FieldOperationsEnum}</li>
 * <li>Il valore o una lista di valori da confrontare</li>
 * </ul>
 * La classe viene inizializzata con il riferimento ad un generico che indica il tipo di valore aspettato (String,
 * Double, Numeric, ecc..) <br/>
 * Due casi particolari meritano le operazioni {@link FieldOperationsEnum#EXISTS} e
 * {@link FieldOperationsEnum#NOTEXISTS} Queste due condizioni generano una sottoquery per verificare la condizione. Ad
 * esempio supponiamo che volessimo trovare le istanze che non abbiano eseguito un tipo determinato movimento (es
 * CO7001). Non sarebbe sufficiente indicare una condizione <b>istanzemovimentis.tipomovimento.it.tipomovimento
 * {@link FieldOperationsEnum#NE} CO7001</b>. Infatti la query così costruita troverebbe comunque le istanze che oltre a
 * quel movimento ne abbiano eseguiti altri. <br>
 * In questo caso è necessario creare una sottoquery che verifichi la condizione <b>not exists</b> ad esempio in SQL
 * <code>
 * <pre>
 * not exists 
 *  (select tmp587TableAlias_.TIPOMOVIMENTO as y0_ 
 * 	from 
 * 	   SIGEPRO.MOVIMENTI tmp587TableAlias_ 
 * 	where 
 * 	   (
 		tmp587TableAlias_.CODICEISTANZA=this_.CODICEISTANZA and 
		tmp587TableAlias_.IDCOMUNE=this_.IDCOMUNE
 * 	    ) 
 * 	    and tmp587TableAlias_.TIPOMOVIMENTO=?
 *  )
 * </pre>
 * </code> dove <b>this_</b> è il riferimento alla tabella principale <br>
 * In questi casi vanno specificati per il FilterField anche le proprietà setExistsParentEntityId e
 * setExistsChildEntityId che specificano le condizioni di join per l'esistenza dei records.<br>
 * Ad esempio nel costruire un filter field che debba verificare che le pratiche tornate non abbiano eseguito un
 * determinato tipo di movimento avremo: <code>   <pre>
FilterRestriction tipoMovimento = new FilterRestriction();
String hierarchyMovimento = "istanzemovimentis";
FilterField<String> existsTipoMovimento = new FilterField<String>("tipomovimento.id.tipomovimento", hierarchyMovimento,
FieldOperationsEnum.NOTEXISTS, new String[] { filter.getTipoMovimento().getId().getTipomovimento() }, Movimenti.class);
existsTipoMovimento.setExistsChildEntityId("istanza.id");
existsTipoMovimento.setExistsParentEntityId("id");
tipoMovimento.addFilterField(existsTipoMovimento);
</pre>
</code> dove <b>existsTipoMovimento.setExistsChildEntityId("istanza.id")</b>
 * rappresenta la relazione di join tra la tabella movimenti con istanze (istanza.id appunto) e
 * <b>existsTipoMovimento.setExistsParentEntityId("id")</b> rappresenta l'identificativo della tabella specificata da
 * getEntityClass()e che genera i detached criteria (nel nostro caso Istanze) TODO documentare inverseJoinChain
 * 
 * @author riccardob
 * 
 */
public class FilterField<T> {

    private String propertyName;
    private String associationPath;
    private FieldOperationsEnum operationType;
    private T[] valori;
    private Class<?> entityClass;
    private String existsParentEntityId;
    private String existsChildEntityId;
    private String[] inverseJoinChain;
    private FilterField[] otherRestrictions;

    private FilterField() {

	this.operationType = FieldOperationsEnum.EQ;
	this.existsParentEntityId = "";
	this.existsChildEntityId = "";
	this.otherRestrictions = null;
    }

    /**
     * Nuovo campo per l'entity con operazione settata a EQ e aliasHierarchy vuota
     * 
     * @param propertyName
     * @param valori
     */
    public FilterField(String propertyName, T[] valori, Class<?> entityClass) {

	this();
	this.propertyName = propertyName;
	this.valori = valori;
	this.entityClass = entityClass;
    }

    /**
     * Nuovo campo per l'entity con operazione settata a EQ
     * 
     * @param propertyName
     * @param associationPath
     * @param valori
     */
    public FilterField(String propertyName, String associationPath, T[] valori, Class<?> entityClass) {

	this();
	this.propertyName = propertyName;
	this.associationPath = associationPath;
	this.valori = valori;
	this.entityClass = entityClass;
    }

    /**
     * Nuovo campo per l'entity con operazione settata a EQ
     * 
     * @param propertyName
     * @param operationType
     * @param valori
     */
    public FilterField(String propertyName, FieldOperationsEnum operationType, T[] valori, Class<?> entityClass) {

	this();
	this.propertyName = propertyName;
	this.operationType = operationType;
	this.valori = valori;
	this.entityClass = entityClass;
    }

    public FilterField(String propertyName, String associationPath, FieldOperationsEnum operationType, T[] valori, Class<?> entityClass) {

	this();
	this.propertyName = propertyName;
	this.associationPath = associationPath;
	this.operationType = operationType;
	this.valori = valori;
	this.entityClass = entityClass;
    }

    public FilterField(String propertyName, String associationPath, FieldOperationsEnum operationType, T[] valori, Class<?> entityClass,
	    FilterField<T>[] otherRestrictions) {

	this();
	this.propertyName = propertyName;
	this.associationPath = associationPath;
	this.operationType = operationType;
	this.valori = valori;
	this.entityClass = entityClass;
	this.otherRestrictions = otherRestrictions;
    }

    /**
     * 
     * @param propertyName
     * @param associationPath
     * @param operationType
     * @param valori
     * @param entityClass
     * @param inverseJoinChain
     */
    public FilterField(String propertyName, String associationPath, FieldOperationsEnum operationType, T[] valori, Class<?> entityClass,
	    String[] inverseJoinChain) {

	this();
	this.propertyName = propertyName;
	this.associationPath = associationPath;
	this.operationType = operationType;
	this.valori = valori;
	this.entityClass = entityClass;
	this.inverseJoinChain = inverseJoinChain;
    }

    public String getPropertyName() {

	return propertyName;
    }

    public String getAssociationPath() {

	return associationPath;
    }

    public FieldOperationsEnum getOperationType() {

	return operationType;
    }

    public T[] getValori() {

	return this.valori;
    }

    public T getValoreSingolo() {

	T[] valori = getValori();
	if (valori != null) {
	    for (T valoreSingolo : valori) {
		return valoreSingolo;
	    }
	}
	return null;
    }

    public Class<?> getEntityClass() {

	return this.entityClass;
    }

    public String getExistsParentEntityId() {

	return this.existsParentEntityId;
    }

    public void setExistsParentEntityId(String existsRightId) {

	this.existsParentEntityId = existsRightId;
    }

    public String getExistsChildEntityId() {

	return this.existsChildEntityId;
    }

    public void setExistsChildEntityId(String existsLeftId) {

	this.existsChildEntityId = existsLeftId;
    }

    // TODO documentare inverseJoinChain
    public String[] getInverseJoinChain() {

	return inverseJoinChain;
    }

    public void setInverseJoinChain(String[] inverseJoinChain) {

	this.inverseJoinChain = inverseJoinChain;
    }

    public FilterField<T>[] getOtherRestrictions() {

	return otherRestrictions;
    }
}