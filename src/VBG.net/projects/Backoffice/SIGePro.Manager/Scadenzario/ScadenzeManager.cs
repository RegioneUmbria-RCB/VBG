using System;
using System.Collections.Specialized;
using System.Data;
using System.Text;
using Init.SIGePro.Authentication;
using Init.SIGePro.Collection;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.Utils;
using PersonalLib2.Data;

namespace Init.SIGePro.Scadenzario
{
	// ATTENZIONE! il 07/03/2012 è stata rimossa la proprietà "password" dalle classi DatiAmministrazione, PartitaIva
	// e CodiceFiscale. Il codice per la lettura delle scadenze è stato modificato in modo da riflettere queste modifiche

	/// <summary>
	/// Descrizione di riepilogo per ScadenzarioManager.
	/// </summary>
	public class ScadenzeManager
	{
		DataBase	m_database;
		string		m_idComune;

		public ScadenzeManager(AuthenticationInfo authInfo):this(authInfo.CreateDatabase(),authInfo.IdComune)
		{
		}

		public ScadenzeManager(DataBase database , string idComune)
		{
			m_database = database;
			m_idComune = idComune;
		}

		/// <summary>
		/// Ottiene una lista di scadenze a partire dai filtri passati
		/// </summary>
		/// <param name="filtri">Filtri di selezione delle scadenze</param>
		/// <returns>Lista di scadenze</returns>
		public ListaScadenze GetListaScadenze( RichiestaListaScadenze filtri)
		{
			ListaScadenze retVal = new ListaScadenze();
			retVal.Scadenza = new ElementoListaScadenzeCollection();

			bool opencnn = false;
			try
			{
				string sql = GeneraQuery( filtri );

				if ( m_database.Connection.State == ConnectionState.Closed )
				{
					opencnn = true;
					m_database.Connection.Open();
				}

				using( IDbCommand cmd = m_database.CreateCommand( sql ) )
					using( IDataReader dr = cmd.ExecuteReader() )
						while (dr.Read())
							retVal.Scadenza.Add( PopolaScadenzaDaDataReader(dr) );

				return retVal;
			}
			catch( Exception ex )
			{
				throw;
			}
			finally
			{
				if( opencnn )
					m_database.Connection.Close();
			}
		}


		/// <summary>
		/// Ottiene i dati di una scadenza a partire dall'id univoco della stessa
		/// </summary>
		/// <param name="codiceScadenza">Id univoco della scadenza</param>
		/// <returns>Scadenza</returns>
		public ElementoListaScadenze GetScadenza( int codiceScadenza )
		{
			bool opencnn = false;
			try
			{
				var sql = String.Format("select * from vw_scadenzario where idcomune={0} and CODICESCADENZA ={1}",
										 m_database.Specifics.QueryParameterName("idComune"),
										 m_database.Specifics.QueryParameterName("codiceScadenza"));

				if ( m_database.Connection.State == ConnectionState.Closed )
				{
					opencnn = true;
					m_database.Connection.Open();
				}

				using (IDbCommand cmd = m_database.CreateCommand(sql.ToString()))
				{
					cmd.Parameters.Add( m_database.CreateParameter( "idComune" , m_idComune ));
					cmd.Parameters.Add( m_database.CreateParameter( "codiceScadenza" , codiceScadenza ));

					using (IDataReader dr = cmd.ExecuteReader())
						if (dr.Read())
							return PopolaScadenzaDaDataReader(dr);
				}
				return null;
			}
			finally
			{
				if( opencnn )
					m_database.Connection.Close();
			}			
		}


		/// <summary>
		/// Popola i dati di una struttura <see cref="ElementoListaScadenze"/> a partire dai dati 
		/// di un datareader letto dalla tabella WV_SCADENZARIO
		/// </summary>
		/// <param name="dr">Datareader letto dalla tabella WV_SCADENZARIO</param>
		/// <returns>Struttura <see cref="ElementoListaScadenze"/></returns>
		private static ElementoListaScadenze PopolaScadenzaDaDataReader(IDataReader dr)
		{
			ElementoListaScadenze els = new ElementoListaScadenze();
	
			els.AmmAmministrazione	= dr["AMM_AMMINISTRAZIONE"].ToString();
			els.AmmPartitaiva		= dr["AMM_PARTITAIVA"].ToString();
			els.AzCodiceFiscale		= dr["AZ_CODICEFISCALE"].ToString();
			els.AzNominativo		= dr["AZ_NOMINATIVO"].ToString();
			els.AzPartitaIva		= dr["AZ_PARTITAIVA"].ToString();
			els.CodEnte				= dr["IDCOMUNE"].ToString();
			els.CodiceIstanza		= dr["CODICEISTANZA"].ToString();
			els.CodMovimento		= dr["CODMOVIMENTO"].ToString();
			els.CodMovimentoDaFare	= dr["CODMOVIMENTODAFARE"].ToString();
			els.CodSportello		= dr["SOFTWARE"].ToString();
			els.CodStatoIstanza		= dr["CODSTATOISTANZA"].ToString();
			els.DataMovimento		= dr["DATAMOVIMENTO"].ToString();
			els.DataProtocollo		= dr["DATAPROTOCOLLO"].ToString();
			els.DataScadenza		= dr["DATASCADENZASTR"].ToString();
			els.DescrMovimento		= dr["DESCRMOVIMENTO"].ToString();
			els.DescrMovimentoDaFare= dr["DESCRMOVIMENTODAFARE"].ToString();
			els.DescrStatoIstanza	= dr["DESCRSTATOISTANZA"].ToString();
			els.ModuloSoftware		= dr["MODULOSOFTWARE"].ToString();
			els.NumeroIstanza		= dr["NUMEROISTANZA"].ToString();
			els.NumeroProtocollo	= dr["NUMEROPROTOCOLLO"].ToString();
			els.Procedimento		= dr["PROCEDIMENTO"].ToString();
			els.Procedura			= dr["PROCEDURA"].ToString();
			els.Responsabile		= dr["RESPONSABILE"].ToString();
			els.RicCap				= dr["RIC_CAP"].ToString();
			els.RicCitta			= dr["RIC_CITTA"].ToString();
			els.RicCodiceFiscale	= dr["RIC_CODICEFISCALE"].ToString();
			els.RicIndirizzo		= dr["RIC_INDIRIZZO"].ToString();
			els.RicLocalita			= dr["RIC_LOCALITA"].ToString();
			els.RicNominativo		= dr["RIC_NOMINATIVO"].ToString();
			els.RicPartitaIva		= dr["RIC_PARTITAIVA"].ToString();
			els.RicProvincia		= dr["RIC_PROVINCIA"].ToString();
			els.TecCodiceFiscale	= dr["TEC_CODICEFISCALE"].ToString();
			els.TecNominativo		= dr["TEC_NOMINATIVO"].ToString();
			els.TecPartitaIva		= dr["TEC_PARTITAIVA"].ToString();
			els.CodiceAmministrazione = dr["CODICEAMMINISTRAZIONE"].ToString();
			els.CodiceInventario	= dr["CODICEINVENTARIO"].ToString();
			els.CodScadenza			= dr["CODICESCADENZA"].ToString();


			return els;
		}





		/// <summary>
		/// Genera la query di selezione della lista scadenze
		/// </summary>
		/// <param name="filtri">Filtri della ricerca</param>
		/// <returns>Query di selezione della lista scadenze</returns>
		private string GeneraQuery(RichiestaListaScadenze filtri)
		{
			if( StringChecker.IsStringEmpty( filtri.CodSportello) )
				throw new Exception( "Codice sportello non specificato" );

			StringBuilder sql = new StringBuilder();

			sql.Append("select * from vw_scadenzario where idcomune='");
			sql.Append( SafeString(m_idComune));
			sql.Append( "' and SOFTWARE ='" );
			sql.Append( SafeString( filtri.CodSportello ) );
			sql.Append( "' " );

			if ( !StringChecker.IsStringEmpty( filtri.IdPratica ) )
				sql.AppendFormat( " and CODICEISTANZA ='{0}' " , SafeString( filtri.IdPratica ) );

			if ( !StringChecker.IsStringEmpty( filtri.NumeroPratica ) )
				sql.AppendFormat( " and NUMEROISTANZA ='{0}' " , SafeString( filtri.NumeroPratica ) );

			if ( !StringChecker.IsStringEmpty( filtri.NumeroProtocollo ) )
				sql.AppendFormat( " and NUMEROPROTOCOLLO ='{0}' " , SafeString( filtri.NumeroProtocollo ) );

			if ( !StringChecker.IsStringEmpty( filtri.Stato ) )
				sql.AppendFormat( " and CODSTATOISTANZA ='{0}' " , SafeString( filtri.Stato ) );

			if ( filtri.DatiAmministrazione != null )
			{
				sql.AppendFormat( " and AMM_PARTITAIVA ='{0}' " , SafeString( filtri.DatiAmministrazione.PartitaIva ) );
				//sql.AppendFormat( " and AMM_PASSWORD ='{0}' " , SafeString( filtri.DatiAmministrazione.Password ) );
			}

			if ( !StringChecker.IsStringEmpty( filtri.Filtro_Fo_SoggettiEsterni ) )
				sql.AppendFormat( " and FK_FO_SOGGETTIESTERNI='{0}' " , SafeString( filtri.Filtro_Fo_SoggettiEsterni ) );

			sql.Append( GetSqlSoggetto( filtri ) );

			return sql.ToString();
		}



		private string GetSqlSoggetto(RichiestaListaScadenze filtri)
		{
			if ( filtri.Item == null ) return "";

			StringBuilder ret = new StringBuilder();

			ret.Append( "and (");

			if( filtri.Item.GetType() == typeof( PartitaIva ) )
			{
				PartitaIva piva = (PartitaIva) filtri.Item;

				StringCollection subFilters = new StringCollection();

				if ( piva.cercaComeRichiedente )
					subFilters.Add( CreaFiltroPerCodiceFiscalePartitaIva( piva.Value , /*piva.password , "RIC_PASSWORD" ,*/  "RIC_PARTITAIVA" , "RIC_CODICEFISCALE" , piva.cercaAncheComeCodiceFiscale));

				if ( piva.cercaComeTecnico )
					subFilters.Add( CreaFiltroPerCodiceFiscalePartitaIva( piva.Value , /*piva.password , "TEC_PASSWORD" ,*/ "TEC_PARTITAIVA", "TEC_CODICEFISCALE" , piva.cercaAncheComeCodiceFiscale ) );

				// Ricerca come azienda
				if ( piva.cercaComeAzienda )
					subFilters.Add( CreaFiltroPerCodiceFiscalePartitaIva( piva.Value , /*piva.password , "AZ_PASSWORD" ,*/ "AZ_PARTITAIVA", "AZ_CODICEFISCALE" , piva.cercaAncheComeCodiceFiscale ) );
		
				// Unisco i filtri
				if ( subFilters.Count > 0 )
					ret.Append( JoinFilters(subFilters , "OR") );

			}
			else if (filtri.Item.GetType() == typeof( CodFiscale ))
			{
				CodFiscale cf = (CodFiscale) filtri.Item;

				StringCollection subFilters = new StringCollection();

				if ( cf.cercaComeRichiedente )
					subFilters.Add( CreaFiltroPerCodiceFiscalePartitaIva( cf.Value , /*cf.password , "RIC_PASSWORD" ,*/  "RIC_CODICEFISCALE" ,"RIC_PARTITAIVA" , cf.cercaAncheComePartitaIva ));

				if ( cf.cercaComeTecnico )
					subFilters.Add( CreaFiltroPerCodiceFiscalePartitaIva( cf.Value , /*cf.password , "TEC_PASSWORD" ,*/ "TEC_CODICEFISCALE" ,"TEC_PARTITAIVA", cf.cercaAncheComePartitaIva ) );

				// Ricerca come azienda
				if ( cf.cercaComeAzienda )
					subFilters.Add( CreaFiltroPerCodiceFiscalePartitaIva( cf.Value , /*cf.password , "AZ_PASSWORD" ,*/ "AZ_CODICEFISCALE" ,"AZ_PARTITAIVA", cf.cercaAncheComePartitaIva ) );
		
				// Unisco i filtri
				if ( subFilters.Count > 0 )
					ret.Append( JoinFilters(subFilters , "OR") );
			}

			ret.Append( " )");

			return ret.ToString();
		}


		/// <summary>
		/// Unisce una lista di filtri utilizzando l'operatore specificato come secondo parametro
		/// </summary>
		/// <param name="subFilters">Lista di filtri</param>
		/// <param name="operation">operatore da utilizzare per unire i filtri (senza spazi)</param>
		/// <returns>stringa che rappresenta la lista dei filtri</returns>
		protected string JoinFilters( StringCollection subFilters , string operation)
		{
			string strOperation = " " + operation + " ";
			// Unisco i filtri
			if ( subFilters.Count > 0 )
			{
				StringBuilder cfSb = new StringBuilder();

				cfSb.Append( "(" );

				foreach(string s in subFilters)
				{
					cfSb.Append( s );
					cfSb.Append( strOperation );
				}

				cfSb.Remove( cfSb.Length - strOperation.Length , strOperation.Length );

				cfSb.Append( ")" );

				return cfSb.ToString();
			}

			return "";
		}


		protected string CreaFiltroPerCodiceFiscalePartitaIva( string valore , /*string password , string campoPassword,*/ string colonna1 , string colonna2 , bool cercaInEntrambi )
		{
			StringBuilder sb = new StringBuilder();

			sb.Append( " (" );

			sb.Append( "( " );
			sb.Append( colonna1 );
			sb.Append( "='" );
			sb.Append( SafeString( valore ) );
			//sb.Append( "' and " );
			//sb.Append( campoPassword );
			//sb.Append( "='" );
			//sb.Append( SafeString( password ) );
			sb.Append( "')" );

			if ( cercaInEntrambi )
			{
				sb.Append( " OR ( " );
				sb.Append( colonna2 );
				sb.Append( "='" );
				sb.Append( SafeString( valore ) );
				//sb.Append( "' and " );
				//sb.Append( campoPassword );
				//sb.Append( "='" );
				//sb.Append( SafeString( password ) );
				sb.Append( "')" );
			}

			sb.Append( ") " );
			return sb.ToString();
		}



		/// <summary>
		/// Ripulisce una stringa per utilizzarla come valore in una queri (x es sostituisce l'apice con il doppio apice)
		/// </summary>
		/// <param name="inVal">valore da ripulire</param>
		/// <returns>Stringa "ripulita"</returns>
		protected string SafeString( string inVal )
		{
			if (inVal == null) return"";
			return inVal.Trim().Replace("'","''");
		}


	}
}
