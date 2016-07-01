using System;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Authentication;
using System.ComponentModel;
using PersonalLib2.Data;
using SIGePro.Net.WebServices.WsSIGePro;
using Init.SIGePro.Utils;
using Init.SIGePro.Data;
using System.IO;
using System.Xml.Serialization;
using Init.Utils;
using PersonalLib2.Sql;
using System.Configuration;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Protocollo;
using System.ServiceModel;
using log4net;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Manager;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Summary description for Istanze
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
	[ServiceContract(Namespace = "http://init.sigepro.it")]
    public class IstanzeWs : System.Web.Services.WebService
    {
		ILog _log = LogManager.GetLogger(typeof(IstanzeWs));


        const int ERR_AUTHENTICATION_FAILED = 58001;
        const int ERR_INSERT_FAILED = 58002;
        const int ERR_PROT_FAILED = 58003;
        const int ERR_FASC_FAILED = 58004;

        public IstanzeWs()
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


        #region Gestione dell'inserimento di un'istanza

		[WebMethod]
		[OperationContract]
		public Init.SIGePro.Data.Istanze GetDettaglioPratica(string token, int codiceIstanza )
		{
			AuthenticationInfo ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
				throw new InvalidTokenException(token);


			try
			{
				Istanze istanza = new IstanzeMgr(ai.CreateDatabase()).GetById(ai.IdComune, codiceIstanza, useForeignEnum.Recoursive);// VisuraPraticheManager(ai).GetDettaglioPratica(request);

				return istanza;
			}
			catch (Exception ex)
			{
				Logger.LogEvent(ai, "GetDettaglioPratica", ex.Message, "ERR_DETTAGLIO");
				throw new Exception(ex.Message);
			}
		}

		[WebMethod]
		[OperationContract]
		public void AggiungiAllegatoAIstanza(string token, int codiceIstanza,string descrizione, int codiceOggetto)
		{
			_log.Debug("Invocazione di AggiungiAllegatoAIstanza");

			AuthenticationInfo ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
				throw new InvalidTokenException(token);

			try
			{
				using(DataBase db = ai.CreateDatabase() )
				{
					new DocumentiIstanzaMgr(db).Insert(new DocumentiIstanza{
						IDCOMUNE	  = ai.IdComune,
						CODICEISTANZA = codiceIstanza.ToString(),
						DOCUMENTO	  = descrizione,
						DATA		  = DateTime.Now,
						CODICEOGGETTO = codiceOggetto.ToString(),
						PRESENTE	  = "1",
						FlgDaModelloDinamico = 0,
					});
				}
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore su AggiungiAllegatoAIstanza: {0}", ex.ToString());

				Logger.LogEvent(ai, "IstanzeWs.AggiungiAllegatoAIstanza", ex.ToString(), "");
				throw ex;
			}
		}

	    /// <summary>
	    /// Rappresenta una richiesta di istanza
	    /// </summary>
	    public class IstanzeRequest
	    {
		    public Init.SIGePro.Data.Istanze Istanza;
			public string NominativoRichiedente;
            public bool InserisciAnagrafe;
            public bool AggiornaAnagrafe;
            public bool GeneraAutomaticamenteDocumentiIstanza;
            public bool GeneraAutomaticamenteOneriIstanza;
            public bool GeneraAutomaticamentePermessiIstanza;
            public bool EscludiControlliSuAnagraficheDisabilitate;
		    
	    }

	    /// <summary>
	    /// Risultato dell'importazione di un'istanza
	    /// </summary>
	    public class IstanzeResponse
	    {
		    public int ErrorCode = 0;
		    public string ErrorMessage = "";
		    public string ExtendedErrorMessage = "";

		    public string CodiceIstanza;
		    public string NumeroIstanza;
	    }

        #endregion

		#region Metodi accessori

		#endregion
	}
}
