using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using SIGePro.Net.WebServices.WsSIGePro;
using Init.SIGePro.Scadenzario;
using Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice;

namespace Sigepro.net.WebServices.WsAreaRiservata
{




	/// <summary>
	/// Summary description for MovimentiFrontofficeService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class MovimentiFrontofficeSvc : SigeproWebService
	{
        [Serializable]
        [XmlRoot]
        public class ConfigurazioneMovimentoDaEffettuare
        {
            [XmlElement(Order=0)]
            public bool PermetteSostituzioneDocumentale { get; set; }
            [XmlElement(Order=1)]
            public bool RichiedeFirmaDocumenti { get; set; }
        }

		/// <summary>
		/// Legge i dati relativi ad un movimento
		/// </summary>
		/// <param name="token">Token ottenuto con l'autenticazione</param>
		/// <param name="strCodiceMovimento">Codice del movimento di cui occorre leggere i dati</param>
		/// <returns></returns>
		[WebMethod(Description = "Permette di leggere i dati di un movimento effettuato")]
        public DatiMovimentoDaEffettuareDto GetMovimento(string token, string strCodiceMovimento)
		{
			var authInfo = CheckToken(token);

            return new MovimentiFrontofficeService(authInfo).GetById(Convert.ToInt32(strCodiceMovimento));
		}

        [WebMethod(Description="Restituisce la lista dei documenti sostituibili")]
        public DocumentiIstanzaSostituibili GetDocumentiSostituibili(string token, int codiceMovimentodaeffettuare)
        {
            var authInfo = CheckToken(token);

            return new MovimentiFrontofficeService(authInfo).GetDocumentiSostituibili(codiceMovimentodaeffettuare);
        }

		[WebMethod(Description = "Permette di leggere le scadenze delle pratiche in base a una serie di filtri")]
		public ListaScadenze GetListaScadenze(string token, RichiestaListaScadenze richiesta)
		{
			var authInfo = CheckToken(token);

			ScadenzeManager scadMgr = new ScadenzeManager(authInfo);
			return scadMgr.GetListaScadenze(richiesta);
		}

		[WebMethod(Description = "Permette di leggere i dati di una scadenza in base al suo id univoco")]
		public ElementoListaScadenze GetScadenza(string token, int codiceScadenza)
		{
			var authInfo = CheckToken(token);

			ScadenzeManager scadMgr = new ScadenzeManager(authInfo);
			return scadMgr.GetScadenza(codiceScadenza);
		}

        [WebMethod(Description="Restituisce i parrametri di configurazione del movimento da effettuare")]
        public ConfigurazioneMovimentoDaEffettuare GetConfigurazioneMovimento(string token, int codiceMovimento)
        {
            var authInfo = CheckToken(token);
            var config = new MovimentiFrontofficeService(authInfo).GetFlagsConfigurazioneDaidMovimento(codiceMovimento);

            return new ConfigurazioneMovimentoDaEffettuare
            {
                PermetteSostituzioneDocumentale = config.TipoSostituzioneDocumentale != TipoSostituzioneDocumentaleEnum.NessunaSostituzione,
                RichiedeFirmaDocumenti = config.RichiedeFirmaDigitale
            };
        }

		/// <summary>
		/// Legge i dati json del movimento identificato dall'id univoco specificato
		/// </summary>
		/// <param name="token">Token</param>
		/// <param name="idMovimento">Identificativo del movimento</param>
		/// <returns>Dati in formato json del movimento o null se il movimento non esiste nella base dati</returns>
		[WebMethod(Description = "Restituisce i dati di un movimento parzialmente compilato dall'utente")]
		public string GetJsonMovimentoFrontoffice(string token, int idMovimento)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
				return new FoMovimentiMgr(db).GetDati(authInfo.IdComune, idMovimento);
		}

		/// <summary>
		/// Salva i dati in formato json di un movimento del frontoffice identificato dall'identificativo univoco specificato
		/// </summary>
		/// <param name="token">Token</param>
		/// <param name="idMovimento">Identificativo del movimento</param>
		/// <param name="datiJson">Dati in formato json del movimento</param>
		[WebMethod(Description = "Salva i dati di un movimento parzialmente compilato dall'utente")]
		public void SalvaJsonMovimentoFrontoffice(string token, int idMovimento, string datiJson)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
				new FoMovimentiMgr(db).SalvaDati(authInfo.IdComune, idMovimento, datiJson);
		}

		/// <summary>
		/// Imposta il movimento frontoffice come inviato
		/// </summary>
		/// <param name="token">Token</param>
		/// <param name="idMovimento">Identificativo del movimento</param>
		[WebMethod(Description = "Imposta un movimento effettuato dall'utente come inviato")]
		public void ImpostaFlagTrasmesso(string token, int idMovimento)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
				new FoMovimentiMgr(db).ImpostaMovimentoComeTrasmesso(authInfo.IdComune, idMovimento);
		}


		/// <summary>
		/// Contrassegna un movimento effettuato dal frontoffice come inviato
		/// </summary>
		/// <param name="token">token</param>
		/// <param name="idMovimento">Identificativo del movimento</param>
		[WebMethod(Description = "Marca un movimento compilato compilato dall'utente come compilato")]
		public void MarcaMovimentoFrontofficeComeInviato(string token, int idMovimento)
		{
			throw new NotImplementedException();
		}
	}
}
