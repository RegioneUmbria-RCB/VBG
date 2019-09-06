// -----------------------------------------------------------------------
// <copyright file="ScriptService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.ServerScriptService
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Globalization;
	using System.Threading;
	using System.Web;
	using Init.SIGePro.DatiDinamici.WebControls;
	using System.Collections.Concurrent;
using log4net;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class D2ScriptService
	{
		static ConcurrentDictionary<string, object> mutexMap = new ConcurrentDictionary<string, object>();

		ILog _log = LogManager.GetLogger(typeof(D2ScriptService));

		public D2ScriptService()
		{
			AssicuraFormatoNumerico();
		}

		/// <summary>
		/// ATTENZIONE! Deve essere sempre la prima chiamata di un webmethod!
		/// Altrimenti c'è il rischio che venga mostrato l'errore "Il campo XXXXX contiene un numero non valido" 
		/// se il formato numerico della macchina non è impostato correttamente
		/// </summary>
		private void AssicuraFormatoNumerico()
		{
			CultureInfo uiCulture = new CultureInfo("it-IT");
			uiCulture.NumberFormat.NumberDecimalSeparator = ",";
			uiCulture.NumberFormat.NumberGroupSeparator = ".";
			uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
			uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

			Thread.CurrentThread.CurrentUICulture = uiCulture;
			Thread.CurrentThread.CurrentCulture = uiCulture;

			HttpContext.Current.Response.Cache.SetCacheability(HttpCacheability.NoCache);
			HttpContext.Current.Response.Cache.SetNoServerCaching();
			HttpContext.Current.Response.Cache.SetNoStore();
			HttpContext.Current.Response.Cache.SetExpires(DateTime.Now.AddDays(-1));
		}

		private object GetThreadLockObject()
		{
			if (HttpContext.Current == null || HttpContext.Current.Session == null)
			{
				_log.ErrorFormat("Tentativo di ottenere un lock basato sulla sessione senza una sessione attiva (è una brutta cosa)");

				return new object();
			}

			var sessionId = HttpContext.Current.Session.SessionID;
		
			var obj = mutexMap.GetOrAdd(sessionId, key => new object());

			return obj;
		}

		public string ValoreDecodificaModificato(string idCampo, int indice, string valoreDecodifica)
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return "";

			var campo = ModelloDinamicoRenderer.DataSourceAttuale.TrovaCampoDaId(Convert.ToInt32(idCampo));
			campo.ListaValori[indice].ValoreDecodificato = valoreDecodifica;

			return "ok";
		}

		public StatoModello CampoModificato(string idCampo, int indice, string valore, string valoreDecodificato)
		{
			System.Diagnostics.Debug.WriteLine("D2ScriptService -> Inizio CampoModificato, id campo: " + idCampo.ToString());

			var threadLock = GetThreadLockObject();

			lock (threadLock)
			{

				var modello = ModelloDinamicoRenderer.DataSourceAttuale;

				if (modello == null)
					return new StatoModello();	// TODO: Gestire caso di errore

				try
				{
					modello.BeginFrame();

					modello.SvuotaCampiModificati();

					CampoDinamicoBase campo = modello.TrovaCampoDaId(Convert.ToInt32(idCampo));
					campo.ListaValori[indice].Valore = valore;
					campo.ListaValori[indice].ValoreDecodificato = valoreDecodificato;

					return GetStatoModello();
				}
				finally
				{
					modello.EndFrame();
					System.Diagnostics.Debug.WriteLine("D2ScriptService -> Fine CampoModificato, id campo: " + idCampo.ToString() + Environment.NewLine);

				}
			}
		}

		private StatoModello GetStatoModello()
		{
			return new StatoModello
			{
				modifiche = VerificaModifiche(),
				errori = VerificaErrori(),
				campiVisibili = GetCampiVisibili(),
				campiNonVisibili = GetCampiNonVisibili(),
				gruppiVisibili = GetGruppiVisibili(),
				gruppiNonVisibili = GetGruppiNonVisibili(),
				messaggiDebug = GetMessaggiDebug()
			};
		}

		private string[] GetMessaggiDebug()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
			{
				return new string[0];
			}

			return ModelloDinamicoRenderer.DataSourceAttuale.DebugMessages.ToArray();
		}

		private int[] GetGruppiNonVisibili()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new int[0];

			var raggruppamenti = ModelloDinamicoRenderer.DataSourceAttuale.Gruppi.GetRaggruppamenti();
			var rVal = new List<int>();


			for (int i = 0; i < raggruppamenti.Count(); i++)
			{
				if (!raggruppamenti.ElementAt(i).IsVisibile())
					rVal.Add(i);
			}

			return rVal.ToArray();
		}

		private int[] GetGruppiVisibili()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new int[0];

			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new int[0];

			var raggruppamenti = ModelloDinamicoRenderer.DataSourceAttuale.Gruppi.GetRaggruppamenti();
			var rVal = new List<int>();

			for (int i = 0; i < raggruppamenti.Count(); i++)
			{
				if (raggruppamenti.ElementAt(i).IsVisibile())
					rVal.Add(i);
			}

			return rVal.ToArray();
		}

		public StatoModello GetStatoModello2()
		{
			return new StatoModello
			{
				modifiche = VerificaModifiche(),
				errori = VerificaErrori(),
				campiVisibili = GetStatoCampiVisibili(),
				campiNonVisibili = GetStatoCampiNonVisibili(),
				gruppiVisibili = GetGruppiVisibili(),
				gruppiNonVisibili = GetGruppiNonVisibili()
			};
		}


		public StatoModelloSalvato SalvaModello()
		{
			var modello = ModelloDinamicoRenderer.DataSourceAttuale;

			if (modello == null)
				return null;

			var threadLock = GetThreadLockObject();

			lock (threadLock)
			{
				try
				{
					modello.BeginFrame();

					var stato = new StatoModelloSalvato(GetStatoModello());

					if (stato.errori.Length > 0)
						return stato;

					modello.Salva();

					if (modello.ErroriScript.Count() > 0)
					{
						return new StatoModelloSalvato(GetStatoModello());
					}

					return new StatoModelloSalvato(GetStatoModello(), true);
				}
				finally
				{
					modello.EndFrame();
				}
			}
		}




		public CampoModificato[] GetValoriCampi()
		{
			List<CampoModificato> campi = new List<CampoModificato>();

			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new CampoModificato[0];

			return ModelloDinamicoRenderer
						.DataSourceAttuale
						.Campi
						.Where(c => c is CampoDinamico)
						.SelectMany(campo =>
						{
							return campo
									.ListaValori
									.Select((valore, idx) =>
									{
										return new CampoModificato
										{
											id = campo.Id,
											indice = idx,
											valore = valore.Valore,
											clientScript = campo.ClientScript
										};
									});
						})
						.ToArray();
		}



		private UidCampo[] GetCampiNonVisibili()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new UidCampo[0];


			return ModelloDinamicoRenderer
					.DataSourceAttuale
					.GetIdCampiNonVisibiliDopoModifiche()
					.Select(idCampo => new UidCampo
					{
						id = idCampo.Id,
						indice = idCampo.IndiceMolteplicita
					})
					.ToArray();
		}

		private UidCampo[] GetCampiVisibili()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new UidCampo[0];

			return ModelloDinamicoRenderer
					.DataSourceAttuale
					.GetIdCampiVisibiliDopoModifiche()
					.Select(idCampo => new UidCampo
					{
						id = idCampo.Id,
						indice = idCampo.IndiceMolteplicita
					})
					.ToArray();
		}

		private UidCampo[] GetStatoCampiNonVisibili()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new UidCampo[0];


			return ModelloDinamicoRenderer
					.DataSourceAttuale
					.GetStatoVisibilitaCampi(false)
					.Select(idCampo => new UidCampo
					{
						id = idCampo.Id,
						indice = idCampo.IndiceMolteplicita
					})
					.ToArray();
		}

		private UidCampo[] GetStatoCampiVisibili()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new UidCampo[0];


			return ModelloDinamicoRenderer
					.DataSourceAttuale
					.GetStatoVisibilitaCampi(true)
					.Select(idCampo => new UidCampo
					{
						id = idCampo.Id,
						indice = idCampo.IndiceMolteplicita
					})
					.ToArray();
		}

		private ErroreCampo[] VerificaErrori()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new ErroreCampo[0];

			var erroriScript = ModelloDinamicoRenderer
								.DataSourceAttuale
								.ErroriScript
								.Select(x => new ErroreCampo { msg = x.Messaggio, id = x.IdCampo, indice = x.Indice });

			var erroriValidazione = Enumerable.Empty<ErroreCampo>();

			try
			{
				ModelloDinamicoRenderer.DataSourceAttuale.ValidaModello();
			}
			catch (ValidazioneModelloDinamicoException ex)
			{
				erroriValidazione = ex.ListaErrori
										.Select(x => new ErroreCampo{ msg = x.Messaggio, id = x.IdCampo, indice = x.Indice });
			}

			return erroriScript.Union(erroriValidazione).ToArray();
		}

		private CampoModificato[] VerificaModifiche()
		{
			if (ModelloDinamicoRenderer.DataSourceAttuale == null)
				return new CampoModificato[0];

			return ModelloDinamicoRenderer
					.DataSourceAttuale
					.CampiModificati
					.SelectMany(campo =>
					{
						return campo
								.ListaValori
								 .Select((valore, idx) =>
								 {
									 return new CampoModificato
									 {
										 id = campo.Id,
										 indice = idx,
										 valore = valore.Valore,										 
										 clientScript = campo.ClientScript
									 };
								 });
					})
					.ToArray();
		}
	}
}
