using System;
using System.Collections;
using System.Collections.Specialized;
using Parser.Collections;
using Init.Utils;


namespace Parser
{
	/// <summary>
	/// Descrizione di riepilogo per QueryParser.
	/// </summary>
	public class QueryParser 
	{
		/// <summary>
		/// E' il metodo che effettua il Parse delle query.
		/// </summary>
		/// <param name="query">Query da parsare</param>
		/// <returns>Ritorna una struttura di tipo <see cref='Query'/></returns>
		public Query Parse(string query)
		{
			Query qs = new Query();

			if ( ! StringChecker.IsStringEmpty( query ) )
			{
				string pQuery = query.ToUpper();

				qs.Type = setQueryType( pQuery );

				switch( qs.Type )
				{
					case queryType.Select:
					{ 
						SelectParser sp = new SelectParser();
						sp.query = replaceChars(pQuery);
						sp.Parse( qs );
						break;
					}
					case queryType.Insert:
					{
						InsertParser ip = new InsertParser();
						ip.query = replaceChars(pQuery);
						ip.Parse( qs );
						break;
					}
					case queryType.Update:
					{
						UpdateParser up = new UpdateParser();
						up.query = replaceChars(pQuery);
						up.Parse( qs );
						break;
					}
					case queryType.Delete: 
					{
						DeleteParser dp = new DeleteParser();
						dp.query = replaceChars(pQuery);
						dp.Parse( qs );
						break;
					}
					default: break;
				}
			}

			return qs;
		}


		protected queryType setQueryType( string pQuery )
		{
			queryType retVal = queryType.Invalid;
			
			try
			{
				if ( ! StringChecker.IsStringEmpty( pQuery ) )
				{
					if( pQuery.StartsWith("SELECT") )
						retVal = queryType.Select;
					else if(  pQuery.StartsWith("UPDATE") )
						retVal = queryType.Update;
					else if(  pQuery.StartsWith("DELETE") )
						retVal = queryType.Delete;
					else if(  pQuery.StartsWith("INSERT") )
						retVal = queryType.Insert;
				}
			}
			catch( System.Exception Ex )
			{
				throw Ex;
			}

			return retVal;
		}

		protected string replaceChars( string pQuery )
		{
			string retVal = pQuery;

			if ( ! StringChecker.IsStringEmpty( pQuery ) )
			{
				retVal = retVal.Replace("\r\n"," ");
			}

			return retVal;
		}

	}
}
