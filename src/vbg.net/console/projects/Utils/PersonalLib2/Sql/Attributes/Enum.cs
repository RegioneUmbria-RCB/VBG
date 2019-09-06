using System;

namespace PersonalLib2.Sql.Attributes
{
	/// <summary>
	/// Tipo di join utilizzata negli attributi di tipo ForeignKeyAttribute
	/// </summary>
	[Serializable]
	public enum LinkType
	{
		Join,
		Leftjoin
	} ;

	/// <summary>
	/// Enumerazione associata all'attributo SetMode delle proprietà di tipo BaseFieldAttribute:
	/// setType.always = il metodo set della proprietà è sempre ammesso;
	/// setType.SetFromControl = il metodo set della proprietà è ammesso se si utilizza il metodo Engine.GetClass;
	/// setType.SetFromDB = il metodo set della proprietà è ammesso se si utilizza il metodo DataBase.GetClass;
	/// setType.None = il metodo set della proprietà NON è ammesso se si utilizza il metodo Engine.GetClass o DataBase.GetClass;
	/// Es. se una proprietà ha l'attributo SetMode = setType.SetFromControl durante l'utilizzo del metodo 
	///		Engine.GetClass questa proprietà viene settata, mentre durante il metodo DataBase.GetClass non viene
	///		modificata dal valore letto dal db. 
	/// </summary>
	[Serializable]
	public enum SetType
	{
		Always,
		SetFromControl,
		SetFromDB,
		None
	}

	/// <summary>
	/// Indica a quale scopo è utilizzato il campo che contiene questo attributo.
	/// Le query costruite con la classe SqlEngine aggiungono condizioni in base a questo attributo.
	/// Select indica che la proprietà con questo attibuto è utilizzata nelle query di selezione ma NON viene utilizzata per i filtri where;
	///		Es. può servire per leggere un dato dal Db con la certezza che non sia mai modificato in update o scritto in insert
	/// Insert indica che la proprietà con questo attibuto è utilizzata nelle query di insert (se contiene un valore);
	///		Es. Può servire per scrivere nel db la data di inserimento di un record che in update non deve essere modificata.
	/// Update indica che la proprietà con questo attibuto è utilizzata nelle query di update (se contiene un valore), NON viene utilizzata per i filtri where;
	///		Es. Può servire per scrivere nel db la data di modifica di un record che in insert non deve essere impostata.
	///	Where indica che la proprietà con questo attibuto è utilizzata solo come condizione Where (se contiene un valore);
	///		Es. Un proprietà DALLADATA che è legata alla colonna DATA del database utilizzata solo per eventuali
	///		selezioni dal giorno al giorno, DALLADATA è una proprietà che fisicamente non esiste nel db (è virtuale).
	///		
	///	I valori sopra elencati possono essere utilizzati assieme, ad esempio per default un attributo di tipo
	///	BaseFieldAttribute ha impostata la proprietà DbScope = BaseFieldScope.Select+BaseFieldScope.Insert+BaseFieldScope.Update+BaseFieldScope.Delete
	/// </summary>
	public struct BaseFieldScope
	{
		public const int Select = 1;
		public const int Insert = 2;
		public const int Update = 4;
		public const int Where = 8;
	}
}