using System;
using Init.Utils;
using System.Collections.Specialized;

namespace Parser
{
	/// <summary>
	/// E' una classe non istanziabile al di fuori del progetto e serve da base alle classi <see cref='SelectParser'/>,
	/// <see cref='InsertParser'/>, <see cref='DeleteParser'/>, <see cref='UpdateParser'/>
	/// </summary>
	internal class BaseParser
	{
		protected int apiceCounter = 0;			 //contatore degli apici: diventa 1 se trovo un apice e ritorna 0 all'apice successivo
		protected int functionCounter = 0;		 //contatore delle parentesi: viene incrementato se trovo "(" e decrementato se trovo ")"
		protected int currentIndex = 0;			 //viene incrementato dalla posizione corrente del carattere all'interno della stringa che sto parsando
		protected string _query = String.Empty;	 //contiene la query da esaminare
				
		/// <summary>
		/// Proprietà pubblica per impostare la query da parsare
		/// </summary>
		public string query
		{
			get { return _query; }
			set { _query = value; }
		}

		/// <summary>
		/// La funzione controlla la variabile "apiceCounter" che, se valorizzata "1" significa che sto esaminando un carattere
		/// all'interno di un commento, se valorizzata "0" non sono dentro un commento 
		/// </summary>
		/// <returns>True/False</returns>
		protected bool isInComment()
		{
			return ( apiceCounter == 1 );
		}
		
		/// <summary>
		/// La funzione controlla la variabile "functionCounter" che, se >0 significa che sto esaminando un carattere
		/// all'interno di una funzione, se valorizzata "0" non sono dentro una funzione
		/// </summary>
		/// <returns>True/False</returns>
		protected bool isInFunction()
		{
			return ( functionCounter > 0 );
		}

		/// <summary>
		/// La funzione controlla se il carattere passato è un spazio
		/// </summary>
		/// <param name="pChar">Carattere da esaminare</param>
		/// <returns>True/False se il carattere è o no uno spazio</returns>
		protected bool isSpace( char pChar )
		{
			return ( pChar == Convert.ToChar(" ") );
		}

		/// <summary>
		/// La funzione controlla se il carattere passato è un apice
		/// </summary>
		/// <param name="pChar">Carattere da esaminare</param>
		/// <returns>True/False se il carattere è o no un apice</returns>
		protected bool isApice( char pChar )
		{
			return ( pChar == Convert.ToChar("'") );
		}

		/// <summary>
		/// Legge un numero definito di caratteri a partire dalla posizione corrente della stringa passata
		/// </summary>
		/// <param name="text">Stringa da esaminare</param>
		/// <param name="charNum">Numero di caratteri da leggere</param>
		/// <returns>La stringa letta a partire dalla posizione corrente fino al numero di caratteri specificati</returns>
		protected string ReadChars( string text, int charNum )
		{
			string retVal = String.Empty;

			if( ! StringChecker.IsStringEmpty( text ) )
			{
				retVal = text;
				if ( text.Length >= charNum )
					retVal = text.Substring(currentIndex,charNum);
			}

			return retVal;
		}
		
		/// <summary>
		/// Ritorna il carattere alla posizione corrente della stringa passata
		/// </summary>
		/// <param name="text">Stringa da esaminare</param>
		/// <returns>Il carattere situato nella posizione corrente della stringa passata</returns>
		protected char CurrentChar( string text )
		{
			return text[currentIndex];
		}

		/// <summary>
		/// Funzione che controlla se una colonna ha un alias oppure no
		/// </summary>
		/// <param name="column">Nome della colonna da esaminare</param>
		/// <returns>Ritorna l'alias della colonna ( se presente ) altrimenti il nome stesso</returns>
		protected string getColumnAlias( string column )
		{
			string retVal = column;

			try
			{
				if ( ! StringChecker.IsStringEmpty( retVal ) )
				{
					int lPos = retVal.LastIndexOf(" AS ");

					if ( lPos > -1 )
						retVal = retVal.Substring(lPos+4);
				}
			}
			catch( System.Exception Ex )
			{
				throw Ex;
			}

			return retVal.Trim();
		}

	
		/// <summary>
		/// Funzione che controlla se una tabella ha un alias oppure no
		/// </summary>
		/// <param name="table">Nome della tabella da esaminare</param>
		/// <returns>Ritorna l'alias della tabella ( se presente ) altrimenti il nome stesso</returns>
		protected string getTableAlias( string table )
		{
			string retVal = table.Trim();

			try
			{
				string pTable = String.Empty;
				for( int i=retVal.Length-1; i>=0; i-- )
				{
					if ( retVal[i] == Convert.ToChar(" ") )
						break;
					else
						pTable = retVal[i].ToString() + pTable;
				}

				retVal = pTable;
			}
			catch( System.Exception Ex )
			{
				throw Ex;
			}

			return retVal;
		}


		/// <summary>
		/// La funzione aggiunge una serie di colonne ( prendendole dalla collection di stringhe passata ) ad una struttura di tipo
		/// <see cref='Query'/>
		/// </summary>
		/// <param name="query">Classe di tipo <see cref='Query'/> di cui valorizzare la proprietà Columns</param>
		/// <param name="cols">Collection di stringe che rappresentano i nomi delle colonne</param>
		protected void addCols( Query query, StringCollection cols )
		{
			for( int i=0; i<cols.Count; i++ )
			{
				query.Columns.Add( cols[i], String.Empty );
			}
		}
	}
}
