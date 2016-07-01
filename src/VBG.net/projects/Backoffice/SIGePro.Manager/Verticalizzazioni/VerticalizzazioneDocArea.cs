using System;
using System.Collections.Specialized;
using Init.SIGePro.Data;
using Init.Utils;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Verticalizzazioni
{
	/// <summary>
	/// Rappresenta la verticalizzazione DOC_AREA
	/// </summary>
	public partial class VerticalizzazioneDocArea: Verticalizzazione
	{
		public const int HB_USE_CURRENT_USER_FOR_DATIDOC = 1;
		public const int HB_USE_CURRENT_USER_FOR_DATIENDO = 2;

		List<Pair<string>> m_parametriDatiDoc = null;
		List<Pair<string>> m_parametriDatiEndo = null;

		StringCollection m_utentiDatiDoc = null;
		StringCollection m_utentiDatiEndo = null;

		/// <summary>
		/// Nome della libreria da utilizzare in Hummingbird
		/// </summary>
		public string HbLibraryName
		{
			get{ return GetString( "HB_LIBRARYNAME" ); }
			set{ SetString("HB_LIBRARYNAME",value); }
		}


	

		/// <summary>
		/// Nome dell'utente da utilizzare in Hummingbird
		/// </summary>
		public string HbUserName
		{
			get{ return GetString( "HB_USERNAME" ); }
			set{ SetString("HB_USERNAME" ,value );}
		}










		/// <summary>
		/// Ritorna una lista di coppie chiave/valore che rappresentano i parametri da utilizzare 
		/// per la ricerca del file DATIDOC
		/// </summary>
		public List<Pair<string>> HbDatiDocParams
		{
			get
			{
				if ( m_parametriDatiDoc == null )
				{
					m_parametriDatiDoc = BuildParameterList( GetString( "HB_DATIDOC_PARAMS") );
				}

				return m_parametriDatiDoc;
			}
		}


		/// <summary>
		/// Ritorna una lista di coppie chiave/valore che rappresentano i parametri da utilizzare 
		/// per la ricerca del file DATIENDO
		/// </summary>
		public List<Pair<string>> HbDatiEndoParams
		{
			get
			{
				if ( m_parametriDatiEndo == null )
				{
					m_parametriDatiEndo = BuildParameterList( GetString( "HB_DATIENDO_PARAMS") );
				}

				return m_parametriDatiEndo;
			}
		}



		/// <summary>
		/// Lista degli utenti su cui effettuare la ricerca per il file DATIDOC
		/// </summary>
		public StringCollection HbDatiDocUsers
		{
			get
			{
				if ( m_utentiDatiDoc == null )
				{
					m_utentiDatiDoc = new StringCollection();
					m_utentiDatiDoc.AddRange( GetString( "HB_DATIDOC_USERS" ).Split(',') );
				}

				return m_utentiDatiDoc;
			}
		}

		/// <summary>
		/// Lista dei gruppi su cui effettuare la ricerca per il file DATIDOC
		/// </summary>
		public StringCollection HbDatiDocGroups
		{
			get
			{
				if ( m_utentiDatiDoc == null )
				{
					m_utentiDatiDoc = new StringCollection();
					m_utentiDatiDoc.AddRange( GetString( "HB_DATIDOC_GROUPS" ).Split(',') );
				}

				return m_utentiDatiDoc;
			}
		}


		/// <summary>
		/// Lista degli utenti su cui effettuare la ricerca per il file DATIENDO
		/// </summary>
		public StringCollection HbDatiEndoUsers
		{
			get
			{
				if ( m_utentiDatiEndo == null )
				{
					m_utentiDatiEndo = new StringCollection();
					m_utentiDatiEndo.AddRange( GetString( "HB_DATIENDO_USERS" ).Split(',') );
				}

				return m_utentiDatiEndo;
			}
		}


		/// <summary>
		/// Lista degli utenti su cui effettuare la ricerca per il file DATIENDO
		/// </summary>
		public StringCollection HbDatiEndoGroups
		{
			get
			{
				if ( m_utentiDatiEndo == null )
				{
					m_utentiDatiEndo = new StringCollection();
					m_utentiDatiEndo.AddRange( GetString( "HB_DATIENDO_GROUPS" ).Split(',') );
				}

				return m_utentiDatiEndo;
			}
		}


		/// <summary>
		/// Specifica se includere l'utente correntemente loggato negli utenti utilizzati nella ricerca di un documento. Può assumere una combinazione dei seguenti valori: 0 - No , 1 - Usa utente per ricerche di DATIDOC , 2 - Usa utente per ricerche di DATIENDO 
		/// </summary>
        //public int IntHbIncludeCurrentUser
        //{
        //    get { return GetInt("HB_INCLUDE_CURRENT_USER"); }
        //}

        //public bool UseCurrentUserForDatiDoc
        //{
        //    get
        //    {
        //        return (IntHbIncludeCurrentUser & HB_USE_CURRENT_USER_FOR_DATIDOC) > 0;
        //    }
        //}
		
        //public bool UseCurrentUserForDatiEndo
        //{
        //    get
        //    {
        //        return (IntHbIncludeCurrentUser & HB_USE_CURRENT_USER_FOR_DATIENDO) > 0;
        //    }
        //}

		public bool BoolHbDynamicLogin
		{
			get { return GetBool("HB_DYNAMIC_LOGIN"); }
		}


		/// <summary>
		/// Genera la lista di coppie chiave/valore che rappresentano i parametri di una ricerca
		/// </summary>
		/// <param name="paramsString"></param>
		/// <returns></returns>
		private static List<Pair<string>> BuildParameterList(string paramsString)
		{
			List<Pair<string>> coll = new List<Pair<string>>();
			string[] pars = paramsString.Split(';');
	
			for ( int i=0;i<pars.Length ;i++ )
			{
				if ( pars[i].IndexOf( '=' ) != -1 )
				{
					string[] str = pars[i].Split('=');

					coll.Add( new Pair<string>( str[0] , str[1] ) );
				}
			}

			return coll;
		}

	}
}
