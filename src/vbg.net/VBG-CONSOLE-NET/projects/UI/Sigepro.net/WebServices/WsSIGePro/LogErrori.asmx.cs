using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Utils;
using Init.SIGePro.Exceptions.Token;

namespace SIGePro.Net.WebServices.WsSIGePro
{
	/// <summary>
	/// Descrizione di riepilogo per LogErrori.
	/// </summary>
	[WebService(Namespace="http://init.sigepro.it")]
	public class LogErrori : System.Web.Services.WebService
	{
		public LogErrori()
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

		// ESEMPIO DI SERVIZIO WEB
		// Il servizio di esempio HelloWorld() restituisce la stringa Hello World.
		// Per generare, rimuovere i commenti dalle righe seguenti, quindi salvare e generare il progetto.
		// Per verificare il servizio Web, premere F5.

		[WebMethod(Description="Logga un errore nel log di sigepro")]
		public void Log(string token , string codiceErrore , string modulo , string descrizioneEstesa)
		{
			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
                throw new InvalidTokenException(token);

			Logger.LogEvent( authInfo ,modulo , descrizioneEstesa , codiceErrore);
		}
	}
}
