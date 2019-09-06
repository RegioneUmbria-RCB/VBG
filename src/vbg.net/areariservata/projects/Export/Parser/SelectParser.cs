using System;
using Init.Utils;
using System.Collections.Specialized;

namespace Parser
{
	/// <summary>
	/// E' una classe non istanziabile al di fuori del progetto e serve a Parsare le query di tipo SELECT
	/// </summary>
	internal class SelectParser : BaseParser
	{
		/// <summary>
		/// Esegue il parser della query precedentemente impostata tramite la proprietà pubblica "query".
		/// </summary>
		/// <param name="qs">
		/// Struttura di tipo <see cref='Query'/> alla quale aggiungere i vari elementi 
		/// estrapolati dal parse della query
		/// </param>
		public void Parse( Query qs )
		{
			try
			{
				string pCols  = String.Empty;
				string pWhere = String.Empty;

				StringCollection tabs = new StringCollection();

				int posStart = 7;
				int posFrom = _query.IndexOf(" FROM ");

				#region 1. Estrapolo le colonne della select
				StringCollection cols = new StringCollection();
				string pSelCols = _query.Substring( posStart, posFrom - posStart ).Trim();
				
				string colAlias = "";
						
				for( currentIndex = 0; currentIndex<pSelCols.Length; currentIndex++ )
				{
					char curChar = CurrentChar( pSelCols );

					switch( curChar.ToString() )
					{
						case "'": 
						{	
							if ( isInComment() )
								apiceCounter -= 1;
							else
								apiceCounter += 1;

							colAlias += curChar.ToString();

							break;
						}
						case "(": 
						{
							if ( ! isInComment() )
								functionCounter += 1;

							colAlias += curChar.ToString();

							break;
						}
						case ")": 
						{
							if ( ! isInComment() )
								functionCounter -= 1;
									
							colAlias += curChar.ToString();

							break;
						}
						case " ": 
						{
							colAlias += curChar.ToString();
							break;
						}

						case ",":
						{
							if( ! isInComment() && ! isInFunction() )
							{
								cols.Add( getColumnAlias( colAlias ) );
								colAlias = String.Empty;
							}
							else
							{
								colAlias += curChar.ToString();
							}
							break;
						}

						default:
						{
							colAlias += curChar.ToString();
							break;
						}
					}
				}
						
				if ( colAlias.Length > 0 )
				{
					cols.Add( getColumnAlias( colAlias ) );
					colAlias = String.Empty;
				}
				
				addCols( qs, cols );
				#endregion

				#region 2. Estrapolo le tabelle dalla from
				string pFrom = _query.Substring( posFrom + 6 );

				qs.Tables = ParseTables( pFrom );
				#endregion

				#region 3. Estrapolo la condizione where
				qs.Where = ParseWhere( pFrom );
				#endregion
			}
			catch( System.Exception Ex )
			{
				throw Ex;
			}
		}


		/// <summary>
		/// Estrapola le tabelle della SELECT
		/// </summary>
		/// <param name="query">Query da parsare</param>
		/// <returns>Una collection di stringhe in cui ogni elemento è il nome della tabella</returns>
		protected StringCollection ParseTables( string query )
		{
			StringCollection retVal = new StringCollection();

			try
			{
				string pQuery = query;
				string tabAlias = String.Empty;

				currentIndex = -1;

				foreach( char c in pQuery )
				{
					currentIndex += 1;

					switch( c.ToString() )
					{
						case "(": 
						{
							functionCounter += 1;
							tabAlias += c.ToString();
							break;
						}
						case ")":
						{
							functionCounter -= 1;
							tabAlias += c.ToString();
							break;
						}
						case ",":
						{
							if ( ! isInFunction() )
							{
								retVal.Add( getTableAlias( tabAlias ) );
								tabAlias = String.Empty;
							}
							else
							{
								tabAlias += c.ToString();
							}
							break;
						}
						case " ":
						{
							if ( ! isInFunction() )
							{
								string pWhere = ReadChars( pQuery, 7 ); 
								if ( pWhere == " WHERE " )
								{
									//	currentIndex += 7;

									if ( ! StringChecker.IsStringEmpty( tabAlias ) )
										retVal.Add( getTableAlias( tabAlias ) );
									
									return retVal;
								}
								else
								{
									tabAlias += c.ToString();
								}
							}
							else
							{
								tabAlias += c.ToString();
							}

							break;
						}
						default:
						{
							tabAlias += c.ToString();
							break;
						}
					}
				}
			}
			catch( System.Exception Ex )
			{
				throw Ex;
			}

			return retVal;
		}

		

		/// <summary>
		/// 
		/// </summary>
		/// <param name="query"></param>
		/// <returns></returns>
		protected string ParseWhere( string query )
		{
            if ( query.Length >= currentIndex + 7)
    			return ( query.Substring( currentIndex + 7 ) );

            return null;
		}
	}
}
