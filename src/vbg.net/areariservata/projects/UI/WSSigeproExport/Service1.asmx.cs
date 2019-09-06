using System;
using System.Globalization;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Web;
using System.Web.Services;
using System.Xml;
using Export.Data;
using Init.SIGeProExport.Manager;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System.Configuration;
using System.IO;
using Export;
using Export.Collection;
using WSSigeproExport.login;

namespace WebServiceSigeproExp
{
	/// <summary>
	/// Descrizione di riepilogo per Service1.
	/// </summary>
	[WebService(Namespace="http://init.sigepro.it")]
	public class CWSSigeproExp : System.Web.Services.WebService
	{
		public CWSSigeproExp()
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
		protected override void Dispose( bool disposing )
		{
			if(disposing && components != null)
			{
				components.Dispose();
			}
			base.Dispose(disposing);		
		}
		
		#endregion


		[WebMethod(Description="Metodo usato per ottenere la lista delle esportazioni",EnableSession=false)]
		public CListaEsportazione ListExp(string sToken)
		{
            return ListExpContext(sToken, null);
		}

        [WebMethod(Description = "Metodo usato per ottenere la lista delle esportazioni appartenenti ad uno specifico contesto", EnableSession = false)]
        public CListaEsportazione ListExpContext(string sToken, string sContext)
        {
            CListaEsportazione pList = null;
            Export.CExport exp = new Export.CExport();
            try
            {
                Login pLogin = new Login();
                pLogin.Url = ConfigurationSettings.AppSettings["LOGIN"].ToString();
                AuthenticationInfo authInfo = pLogin.GetTokenInfo(sToken, TipiAmbiente.DOTNET.ToString());

                if (authInfo == null)
                    throw new ApplicationException("Token non valido!!");

                exp.IdComune = authInfo.IdComune;
                
                pList = exp.GetListExp(sContext);

            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                exp.Dispose();
            }

            return pList;
        }

		[WebMethod(Description="Metodo usato per ottenere i dettagli di una specifica esportazione",EnableSession=false)]
		public CEsportazione GetEsportazione(int iIDExp, string idComuneExport )
		{
            CEsportazione pExp = null;
            Export.CExport exp = new Export.CExport();
            try
            {
                //Solo per la validazione
                exp.IdEsportazione =  iIDExp;
                exp.IdComune = idComuneExport;
                pExp = exp.GetExpDetail();
            }
            catch ( Exception ex )
            {
                throw ex;
            }
            finally
            {
                exp.Dispose();
            }

            return pExp;
		}

        [WebMethod(Description = "Metodo usato per effettuare l'esportazione utilizzando una lista di parametri:il metodo potrebbe inviare la mail e comprimere il file di esportazione", EnableSession = false)]
        public byte[] ExportMail(string sToken, string sXmlFile, int iIDExp, string sIdComune, ParametriCollection pParColl, string sMailDest, bool bZip)
        {
            byte[] bytes;
            Export.CExport exp = new Export.CExport();
            try
            {
                Login pLogin = new Login();
                pLogin.Url = ConfigurationSettings.AppSettings["LOGIN"].ToString();
                AuthenticationInfo authInfo = pLogin.GetTokenInfo(sToken, TipiAmbiente.DOTNET.ToString());

                if (authInfo == null)
                    throw new ApplicationException("Token non valido!!");
                exp.Debug = Convert.ToBoolean( ConfigurationSettings.AppSettings["DEBUG"].ToString() );
                exp.ParametriCollection = pParColl;
                exp.Token = sToken;
                exp.DataBase = CreateDatabase(authInfo);
                exp.DataBase.Connection.Open();
                //exp.IdComune = authInfo.IdComune;
                exp.IdComune = sIdComune;
                exp.IdEsportazione = iIDExp;
                exp.MailDestinatario = sMailDest;
                exp.Zip = bZip;

                bytes = exp.Run(sXmlFile);
            }
            catch (Exception ex)
            {
                throw new Exception("Token [" + sToken + "] Idcomune [" + sIdComune + "], IdEsportazione [" + iIDExp.ToString() + "] -> " + ex.Message, ex);
            }
            finally
            {
                exp.Dispose();
            }

            return bytes;
        }

		[WebMethod(Description="Metodo usato per effettuare l'esportazione utilizzando una lista di parametri:il metodo non invia la mail e non comprime il file di esportazione",EnableSession=false)]	
		public byte[] Export(string sToken, string sXmlFile, int iIDExp, string sIdComune, ParametriCollection pParColl)
		{
            return ExportMail(sToken, sXmlFile, iIDExp, sIdComune, pParColl, string.Empty, false);
		}

        [WebMethod(Description = "Metodo usato per effettuare l'esportazione utilizzando una lista di parametri:il metodo non invia la mail e comprime il file di esportazione", EnableSession = false)]
        public byte[] ExportZip(string sToken, string sXmlFile, int iIDExp, string sIdComune, ParametriCollection pParColl)
        {
            return ExportMail(sToken, sXmlFile, iIDExp, sIdComune, pParColl, string.Empty, true);
        }

        private DataBase CreateDatabase(AuthenticationInfo authInfo)
        {
            ProviderType initialProviderType = (ProviderType)Enum.Parse(typeof(ProviderType), authInfo.Provider, true);
            string sConnection;
            if (authInfo.ConnectionString.EndsWith(";"))
                sConnection = authInfo.ConnectionString + "User Id=" + authInfo.DBUser + ";Password=" + authInfo.DBPassword;
            else
                sConnection = authInfo.ConnectionString + ";User Id=" + authInfo.DBUser + ";Password=" + authInfo.DBPassword;

            DataBase db = new DataBase(sConnection, initialProviderType);
            db.ConnectionDetails.Token = authInfo.Token;

            return db;
        }

        #region Enum per i tipi di ambiente
        public enum TipiAmbiente { DOTNET, JAVA, DEFAULT }
        #endregion
	}
}