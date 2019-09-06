namespace PersonalLib2.Data.Providers
{
	/// <summary>
	/// Interfaccia che definisce le proprietà e i metodi associati ad uno specifico provider.
	/// </summary>
	public interface IProvider
	{
		/// <summary>
		/// Utilizzato per ritornare quale è il nome del segnaposto da utilizare come
		/// parametro nelle query parametriche.
		/// Es. in Oracle "Select * From TableName Where ColumnName=:par1"
		/// </summary>
		/// <param name="columnName">
		/// E' il nome del parametro o il nome della colonna alla quale è associato il parametro.
		/// </param>
		/// <returns>
		/// Il prefisso utilizzato dal provider scelto + il nome del parametro columnName.
		/// Es. 
		///		Provider Oracle=	":columnName"
		///		Provider OleDb=		"?"
		///		Provider Sql Server="@columnName"
		/// </returns>
		string QueryParameterName(string columnName);

		/// <summary>
		/// Ritorna il nome del parametro da utilizzare negli oggetti command.
		/// </summary>
		/// <param name="columnName">E' il nome del parametro o il nome della colonna alla quale è associato il parametro.</param>
		/// <returns>
		/// Il prefisso utilizzato dal provider scelto + il nome del parametro columnName.
		/// Es. 
		///		Provider Oracle=	":columnName"
		///		Provider OleDb=		"columnName"
		///		Provider Sql Server="@columnName"
		/// </returns>
		string ParameterName(string columnName);

		/// <summary>
		/// Utilizzato per ritornare la sintassi della funzione che permette di 
		/// rendere UCase una colonna del database.
		/// </summary>
		/// <param name="columnName">Nome della colonna da convertire in maiuscolo.</param>
		/// <returns>
		/// Es. 
		///		Oracle=		UPPER(columnName)
		///		Sql Server=	UPPER(columnName)
		/// </returns>
		string UCaseFunction(string columnName);

		/// <summary>
		/// Utilizzato per ritornare la sintassi della funzione che permette di 
		/// ritornare il Max di una colonna.
		/// </summary>
		/// <param name="columnName">Nome della colonna della quale calcolare il Max.</param>
		/// <returns>
		/// Es. 
		///		Oracle=		MAX(columnName)
		///		Sql Server=	MAX(columnName)
		/// </returns>
		string MaxFunction(string columnName);

		/// <summary>
		/// Utilizzato per ritornare la sintassi della funzione che permette di 
		/// sostituire un valore null.
		/// </summary>
		/// <param name="columnName">Nome della colonna della quale sostituire eventuali valori null.</param>
		/// <param name="value">valore da sostituire a null.</param>
		/// <returns>
		/// Es. 
		///		Oracle=		NVL(columnName,0)
		///		Sql Server=	ISNULL(columnName,0)
		/// </returns>
		string NvlFunction(string columnName, object value);

		/// <summary>
		/// Utilizzato per ritornare la sintassi della funzione che permette di 
		/// estrarre una sottostringa.
		/// Es. in oracle equivale a SUBSTR(stringa,start,length)
		/// </summary>
		/// <param name="stringValue">Stringa o colonna dalla quale estrarre la sottostringa. Se si tratta di stringa vanno indicati anche i caratteri terminatori della stringa. Es. 'pippo'.</param>
		/// <param name="start">Posizione iniziale.</param>
		/// <param name="length">Numero di caratteri da estrarre.</param>
		/// <returns>
		/// Es. 
		///		Oracle=		SUBSTR('testo',2,3) --> est
		///		Sql Server=	SUBSTRING('testo',2,3) --> est
		/// </returns>
		string SubstrFunction(string stringValue, int start, int length);

		/// <summary>
		/// Utilizzato per togliere gli spazi presenti alla destra della stringa passata
		/// </summary>
		/// <param name="columnName">stringa dalla quale eliminare gli spazi</param>
		/// <returns></returns>
		string RTrimFunction(string columnName);

		/// <summary>
		/// Utilizzato per convertire un valore in una stringa testuale
		/// </summary>
		/// <param name="columnName">Nome della colonna contenente il valore da convertire in stringa</param>
		/// <returns></returns>
		string ToCharFunction(string columnName);

		/// <summary>
		/// Utilizzato per convertire un valore in un numero
		/// </summary>
		/// <param name="columnName">Nome della colonna contenente il valore da convertire in numero</param>
		/// <returns></returns>
		string ToIntegerFunction(string columnName);



		/// <summary>
		/// Utilizzato per effettuare una somma tra colonne
		/// </summary>
		/// <param name="columnName">Nome della colonna</param>
		/// <returns></returns>
		string SumFunction(string columnName);

		/// <summary>
		/// Utilizzato per leggere la lunghezza del valore di una colonna
		/// </summary>
		/// <param name="columnName"></param>
		/// <returns></returns>
		string LengthFunction(string columnName);
		


		/// <summary>
		/// Effettua una like in un campo clob
		/// </summary>
		/// <param name="columnName">Nome della colonna su cui effettuare il confronto like</param>
		/// <param name="valoreDaCercare">nome del parametro che conterrà il valore con cui fare il confronto. il parametro viene già convertito nel formato del db</param>
		/// <param name="confrontoUCase">true se occorre utilizzare un confronto su testo tutto maiuscolo</param>
		/// <returns></returns>
		string ClobLike(string columnName, string nomeParametro, bool confrontoUCase);

		/// <summary>
		/// Ritorna l'enumaratore che rappresenta la codifica del database al quale la stringa di connessione fa riferimento.
		///(ORACLE,SQL,MYSQL,POSTGRESQL)
		/// </summary>
		Provider DBMSName();
	}
}