using PersonalLib2.Sql;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per IManager.
	/// </summary>
	public interface IManager
	{
		/// <summary>
		/// L'implementazione dovrà tenere conto di :
		/// 1) Recuperare i dati mancanti, leggendoli dalla configurazione dove possibile
		/// 2) Verificare la presenza dei dati obbligatori e la congruenza delle chiavi esterne (Validate)
		/// 3) Invocare la PersonalLib per l'inserimento nel db dell'oggetto
		/// 4) Invocare il metodo "Insert" dei rispettivi manager per tutti gli oggetti ( generic class ) collegati
		/// 5) Ritornare una classe dello stesso tipo di quella passata completa dei dati aggiornati
		/// </summary>
		/// <param name="pClass">Istanza di DataClass da cui recuperare i dati</param>
		/// <returns>Istanza di DataClass completa dei dati aggiornati</returns>
		// DataClass Insert( DataClass pClass );
		/// <summary>
		/// L'implementazione dovrà tenere conto di :
		/// 1) Verificare la presenza dei dati obbligatori presenti nella classe passata
		/// 2) Verificare la congruenza delle chiavi esterne presenti nella classe passata
		/// </summary>
		/// <param name="pClass">Istanza di DataClass su cui effettuare la validazione</param>
		/// <returns>True se la classe è validata altrimenti Folse e in più generano un eccezione</returns>
		bool RequiredFieldValidate(DataClass pClass, AmbitoValidazione ambitoValidazione);
		/// <summary>
		/// L'implementazione dovrà tenere conto di :
		/// 1) Verificare la presenza dei dati obbligatori e la congruenza delle chiavi esterne (Validate)
		/// 2) Invocare la PersonalLib per l'aggiornamento nel db dell'oggetto
		/// 3) Ritornare una classe dello stesso tipo di quella passata completa dei dati aggiornati
		/// </summary>
		/// <param name="pClass">Istanza di DataClass da cui recuperare i dati</param>
		/// <returns>Istanza di DataClass completa dei dati aggiornati</returns>
		DataClass Update(  DataClass pClass );
		/// <summary>
		/// L'implementazione dovrà tenere conto di :
		/// 1) integrare le classi figli con i dati mancanti prendendoli dalla classe padre passata
		/// </summary>
		/// <param name="pClass">Istanza di DataClass da cui recuperare i dati</param>
		/// <returns>Istanza di DataClass completa dei dati integrati</returns>
		DataClass ChildDataIntegrations( DataClass pClass );
	}
}
