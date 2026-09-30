using System;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Collections;

namespace PersonalLib2.Exceptions
{
	/// <summary>
	/// Eccezione generica generata durante le operazioni all' interno dell' oggetto <see cref="DataBase"/>.
	/// Quando possibile viene specificata l'ultima query eseguita.
	/// </summary>
	public class DatabaseException : Exception
	{
		/// <summary>
		/// Costruttore.
		/// </summary>
		/// <param name="inner">Eccezione intercettata.</param>
		public DatabaseException(Exception inner) : base(inner.Message, inner)
		{
		}

		/// <summary>
		/// Costruttore.
		/// </summary>
		/// <param name="message">Messaggio di testo da inviare.</param>
		public DatabaseException(string message)
			: base(message)
		{
		}

		/// <summary>
		/// Costruttore.
		/// </summary>
		public DatabaseException()
		{
		}

		/// <summary>
		/// Costruttore.
		/// </summary>
		/// <param name="lastQuery">Ultima query che si è provato ad eseguire.</param>
		/// <param name="inner">Eccezione intercettata.</param>
		public DatabaseException(string lastQuery, Exception inner) : base(inner.Message + ": " + lastQuery, inner)
		{
		}
	}

	/// <summary>
	/// Eccezione specifica che è generata nel caso in cui ci si aspetta un solo record
	/// dal command eseguito invece ritornano più record.
	/// </summary>
	public class SingleRowException : Exception
	{
		/// <summary>
		/// Costruttore con il messaggio di errore standard.
		/// </summary>
		public SingleRowException() : base("La query ha restituito più di un record.")
		{
		}

		/// <summary>
		/// Costruttore.
		/// </summary>
		/// <param name="message">Messaggio di errore.</param>
		public SingleRowException(string message) : base(message)
		{
		}
	}

	/// <summary>
	/// Eccezione che è generata dal non corretto utilizzo delle classi di tipo
	/// <see cref="DataClass"/> e <see cref="DataClassCollection"/>
	/// </summary>
	public class DataClassException : Exception
	{
		/// <summary>
		/// Costruttore.
		/// </summary>
		/// <param name="message">Messaggio di errore.</param>
		public DataClassException(string message) : base(message)
		{
		}
	}
}