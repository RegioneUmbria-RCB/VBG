using System;
using System.ComponentModel;
using System.Web.Services;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze;

namespace Init.Sigepro.FrontEnd.WebServices
{
	/// <summary>
	/// Descrizione di riepilogo per NotificaAcquisizioneIstanza.
	/// </summary>
	[WebService(Namespace="http://init.sigepro.it")]
	public class NotificaAcquisizioneIstanza : WebService
	{
		public NotificaAcquisizioneIstanza()
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

		// ESEMPIO DI SERVIZIO WEB
		// Il servizio di esempio HelloWorld() restituisce la stringa Hello World.
		// Per generare, rimuovere i commenti dalle righe seguenti, quindi salvare e generare il progetto.
		// Per verificare il servizio Web, premere F5.

		[WebMethod]
		public bool IstanzaAcquisita(string idPratica)
		{/*
			// il codice della pratica deve essere nel formato "E256_CO_chcndr70a24e256i_0000_0000000"
			string[] chunks = idPratica.Split('_');

			PresentazioneIstanzaDataKey key = PresentazioneIstanzaDataKey.New(chunks[0], chunks[1], chunks[2].ToUpper(), Convert.ToInt32(chunks[3]));

			IndexFileManager.DeleteMarked(key);

			return true;*/

			throw new Exception("Supporto a docarea interrotto");
		}
	}
}