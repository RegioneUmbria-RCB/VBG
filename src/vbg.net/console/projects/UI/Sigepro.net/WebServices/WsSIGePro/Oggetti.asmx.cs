using System;
using System.ComponentModel;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System.IO;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Utils;

namespace SIGePro.Net.WebServices.WsSIGePro
{
	/// <summary>
	/// Gestisce la lettura di oggetti binari dal database
	/// </summary>
	[WebService(Namespace="http://init.sigepro.it")]
	public class Oggetti : WebService
	{
        const int ERR_UPLOAD_FAILED = 58001;

		public class OggettoBackoffice
		{
			private string m_mimeType;
			private string m_fileName;
			private byte[] m_binaryData;

			public string MimeType
			{
				get { return m_mimeType; }
				set { m_mimeType = value; }
			}

			public byte[] BinaryData
			{
				get { return m_binaryData; }
				set { m_binaryData = value; }
			}

			public string FileName
			{
				get { return m_fileName; }
				set { m_fileName = value; }
			}

			public OggettoBackoffice()
			{
			}

			public OggettoBackoffice(string mimeType, string fileName, byte[] binaryData)
			{
				m_mimeType = mimeType;
				m_fileName = fileName;
				m_binaryData = binaryData;
			}

		}

		public Oggetti()
		{
			//CODEGEN: chiamata richiesta da Progettazione servizi Web ASP.NET.
			InitializeComponent();
		}

		#region Codice generato da Progettazione componenti

		//Richiesto da Progettazione servizi Web 
		private IContainer components = null;

		/// <summary>
		/// Metodo necessario per il supporto della finestra di progettazione. Non modificare
		/// il contenuto del metodo con l'editor di codice.
		/// </summary>
		private void InitializeComponent()
		{
		}

		/// <summary>
		/// Pulire le risorse in uso.
		/// </summary>
		protected override void Dispose(bool disposing)
		{
			if (disposing && components != null)
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#endregion

		/// <summary>
		/// Legge un oggetto binario dal database.
		/// </summary>
		/// <param name="token">Token ottenuto tramite autenticazione</param>
		/// <param name="codiceoggetto">Id dell'oggetto da leggere</param>
		/// <returns></returns>
		[WebMethod(Description="Permette di leggere un oggetto binario salvato all'interno di SIGePro")]
		public OggettoBackoffice GetOggetto(string token, string codiceOggetto)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
                throw new InvalidTokenException(token);

			using (DataBase db = authInfo.CreateDatabase())
			{
				return LeggiOggettoBackoffice(db, authInfo.IdComune, Convert.ToInt32(codiceOggetto));
			}
		}

		public string GetNomeFile(string token, int codiceOggetto)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			using (DataBase db = authInfo.CreateDatabase())
			{
				return new OggettiMgr( db ).GetNomeFile( authInfo.IdComune , codiceOggetto );
			}

		}

		private OggettoBackoffice LeggiOggettoBackoffice(DataBase db, string idComune, int codiceOggetto)
		{
			OggettiMgr oggettiManager = new OggettiMgr(db);
			Init.SIGePro.Data.Oggetti oggetto = oggettiManager.GetById(idComune , codiceOggetto);

			if ( oggetto == null )
				throw new ArgumentException( "Codice oggetto non valido: " + codiceOggetto );

			string ctType = oggettiManager.GetContentType(oggetto);

			if ( String.IsNullOrEmpty( ctType ) )
				throw new InvalidOperationException("Content type non trovato per il file " + oggetto.NOMEFILE + " (IdOggetto=" + oggetto.CODICEOGGETTO + ", IdComune=" + oggetto.IDCOMUNE + ")");

			return new OggettoBackoffice(ctType, oggetto.NOMEFILE, oggetto.OGGETTO);
		}

        /// <summary>
        /// Carica un oggetto binario nel database
        /// </summary>
        /// <param name="sToken">Token ottenuto tramite autenticazione</param>
        /// <param name="sFileName">Nome del file da caricare</param>
        /// <param name="aFile">Contenuto del file</param>
        /// <returns></returns>
        [WebMethod(Description = "Metodo usato per caricare un oggetto binario nel db", EnableSession = false)]
        public int UploadOggetto(string sToken, string sFileName, byte[] aFile)
        {
            DataBase db = null;
            AuthenticationInfo authInfo = null;
            try
            {
                authInfo = AuthenticationManager.CheckToken(sToken);

                if (authInfo == null)
                    throw new InvalidTokenException(sToken);

                db = authInfo.CreateDatabase();

                Init.SIGePro.Data.Oggetti obj = new Init.SIGePro.Data.Oggetti();
                obj.IDCOMUNE = authInfo.IdComune;
                obj.NOMEFILE = Path.GetFileName( sFileName );
                obj.OGGETTO = aFile;

                OggettiMgr objMgr = new OggettiMgr(db);
                return int.Parse( objMgr.Insert(obj).CODICEOGGETTO );
            }
            catch (Exception ex)
            {
                if (authInfo != null)
                    Logger.LogEvent(authInfo, "UPLOAD_OGGETTO", ex.ToString(), ERR_UPLOAD_FAILED.ToString());

                throw ex;
            }
            finally
            {
                if (db != null && db.Connection != null)
                    db.Connection.Close();
            }
        }


		/// <summary>
		/// Legge un'immagine o un oggetto del frontoffice
		/// </summary>
		/// <param name="token"></param>
		/// <param name="idRisorsaOggetto"></param>
		/// <returns></returns>
		[WebMethod(Description = "Metodo usato per leggere un oggetto o un'immagine del frontoffice", EnableSession = false)]
		public OggettoBackoffice GetRisorsaFrontoffice(string token, string idRisorsaOggetto)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				throw new InvalidTokenException(token);

			using (DataBase db = authInfo.CreateDatabase())
			{
				StiliFrontOfficeMgr sfoMgr = new StiliFrontOfficeMgr(db);
				int? codiceOggetto = sfoMgr.GetCodiceOggettoRisorsaFrontoffice(authInfo.IdComune, idRisorsaOggetto);

				if (!codiceOggetto.HasValue)
					throw new ArgumentException("Id risorsa " + idRisorsaOggetto + " non trovato");

				return LeggiOggettoBackoffice(db, authInfo.IdComune, codiceOggetto.Value);
			}
		}
	}


}