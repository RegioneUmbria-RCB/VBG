//using System;
//using System.Data;
//using Init.SIGePro.Authentication;
//using Init.SIGePro.Utils;
//using PersonalLib2.Data;
//// Modello statico
//
//namespace Init.SIGePro.Data
//{
//
//	/// <summary>
//	/// Rappresenta una connessione ad una base dati.
//	/// il campo IDCOMUNE rappresenta l'idcomune tradotto da utilizzare per le query nelle tabelle sigepro.
//	/// </summary>
//	public class SigeproDatabase : DataBase
//	{
//		private string m_idComune;
//		private string m_origIdComune;
//
//
//		/// <summary>
//		/// L'idcomune tradotto da utilizzare per le query nei campi.
//		/// ATTENZIONE: Non può essere utilizzato per istanziare un nuovo <see cref="SigeproDatabase"/>
//		/// </summary>
//		public string IdComune
//		{
//			get { return m_idComune; }
//		}
//
//		/// <summary>
//		/// L'idcomune con il quale è stato istanziato l'oggetto
//		/// ATTENZIONE: Utilizzare questo campo per istanziare un nuovo <see cref="SigeproDatabase"/>
//		/// </summary>
//		public string OrigIdComune
//		{
//			get { return m_origIdComune; }
//		}
//
////		public SigeproDatabase( string idComune , DataBase db ) : base( db.ConnectionDetails.ConnectionString, db.ConnectionDetails.ProviderType )
////		{
////
////			m_idComune = idComune;
////		}
////
////		public SigeproDatabase( string idComune , IDbTransaction transaction ) : base( transaction )
////		{
////			m_idComune = idComune;
////		}
//
//		public SigeproDatabase( string idComune ) : base( ConnectionStringReader.Read( idComune ) , ProviderType.OleDb )
//		{
//			ComuniSecurity cs	= ComuniSecurityReader.Read( idComune );
//			m_idComune			= cs.CS_IDCOMUNE;
//			m_origIdComune		= idComune;
//		}
//
//		internal SigeproDatabase( AuthenticationInfo authInfo ) : base( authInfo.ConnectionString , ProviderType.OleDb )
//		{
//			m_idComune			= authInfo.IdComune;
//			// TODO: come risalire all'idcomune originale? Valutare se salvarlo nelle authentication info
//			m_origIdComune		= null;
//		}
//
//		[Obsolete("Utilizzare questo costruttore solo per i test")]
//		internal SigeproDatabase( string idComune , string connectionString ) : base( connectionString , ProviderType.OleDb )
//		{
//			m_idComune			= idComune;
//			// TODO: come risalire all'idcomune originale? Valutare se salvarlo nelle authentication info
//			m_origIdComune		= null;
//		}
//	}
//}
