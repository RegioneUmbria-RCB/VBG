using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.SIGePro.DatiDinamici.WebControls;
using System.IO;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	public class GeneratoreHtmlSchedeDinamiche : IGeneratoreHtmlSchedeDinamiche
	{
		private static class Constants
		{
			public const string TitoloSchedaWrapper = "<div class='titoloSchedaDinamica'>{0}</div>";
			public const string SeparatoreSchede = "<br /><hr /><br />";
		}

		ITokenApplicazioneService _tokenApplicazioneService;
		bool _primoSegnapostoTrovato = true;
		IStrutturaModelloReader _strutturaModelloReader;

		public GeneratoreHtmlSchedeDinamiche(ITokenApplicazioneService tokenApplicazioneService, IStrutturaModelloReader strutturaModelloReader)
		{
			this._tokenApplicazioneService = tokenApplicazioneService;
			this._strutturaModelloReader = strutturaModelloReader;
		}

		#region IGeneratoreHtmlSchedeDinamiche Members

		public string GeneraHtml(DomandaOnline domanda, int idScheda, int indiceMolteplicita)
		{
			var modello = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.IdModello == idScheda).FirstOrDefault();

			if (modello == null)
				return String.Empty;

			if (!modello.Compilato)
				return String.Empty;

			return GetStringaCss() + WrapHtml(GenerazioneHtmlDomandaConstants.WrappingDivHtml, () =>
			{
				return WriteHtml(domanda, modello, indiceMolteplicita);
			});
		}


		// Genera l'html di una sola scheda dell'istanza
		public string GeneraHtml(DomandaOnline domanda, int idScheda)
		{
			return GeneraHtml(domanda, idScheda, -1);
		}

		// Genera l'html di tutte le schede dell'istanza
		public string GeneraHtmlDelleSchedeDellaDomanda(DomandaOnline domanda, GenerazioneHtmlSchedeOptions options)
		{
			return GetStringaCss() + WrapHtml(GenerazioneHtmlDomandaConstants.WrappingDivHtml, () =>
			{
				var sb = new StringBuilder();

				var modelli = domanda
								.ReadInterface
								.DatiDinamici
								.Modelli
								.Where(m => m.Compilato)
								.Select(x => x.EstraiOrdine(domanda.ReadInterface.DatiDinamici))
								.OrderBy(m => m.Ordine)
								.Select(m => m.Modello);				
					
				foreach (var modello in modelli)
				{
					if (!modello.Compilato)
						continue;

					// Devo riportare solamente i modelli dinamici che non richiedono la firma
					if (options == GenerazioneHtmlSchedeOptions.SoloSchedeCheNonNecessitanoFirma &&  modello.TipoFirma != 0)
						continue;

					sb.Append(WriteHtml(domanda, modello) );
				}

				return sb.ToString();
			});
		}

		private string WriteHtml(DomandaOnline domanda, ModelloDinamico modello, int indiceMolteplicita = -1)
		{
			var sb = new StringBuilder();
			var idModello = modello.IdModello;

			foreach (var indiceScheda in GetIndiciSchede(domanda, idModello))
			{				
				var scheda = GeneraScheda(domanda, idModello, indiceScheda, indiceMolteplicita);
				var campiNascosti = domanda.ReadInterface.DatiDinamici.GetCampiNonVisibili(idModello);

				sb.Append(GeneraHtmlScheda(scheda, new CampiNonVisibili(campiNascosti)));
				sb.Append(Constants.SeparatoreSchede);
			}

			return sb.ToString();
		}


		public string GeneraHtmlScheda(ModelloDinamicoBase scheda, ICampiNonVisibili campiNonVisibili = null)
		{
			if(campiNonVisibili == null)
				campiNonVisibili = CampiNonVisibili.TuttiICampiVisibili;

			var stringaCss = _primoSegnapostoTrovato ? GenerazioneHtmlDomandaConstants.CssModelliDinamici : String.Empty;

			return WrapHtml(Constants.TitoloSchedaWrapper, () =>
			{
				var renderer = new ModelloDinamicoRenderer
				{
					ID = "renderer",
					ReadOnly = true,
					CompatibilitaOpenOffice = true,
					DataSource = scheda,
					CampiNascosti = campiNonVisibili
				};

				renderer.DataBind();

				var stringWriter = new StringWriter();
				var tw = new HtmlTextWriter(stringWriter);

				renderer.RenderControl(tw);

				return stringWriter.ToString();
			});
		}
		#endregion

		private IEnumerable<int> GetIndiciSchede(DomandaOnline domanda, int idModello)
		{
			return domanda.ReadInterface.DatiDinamici.GetIndiciSchede(this._strutturaModelloReader.Read(idModello));
		}


		private string WrapHtml(string wrapper, Func<string> function)
		{
			return String.Format(wrapper, function());
		}

		private ModelloDinamicoIstanza GeneraScheda(DomandaOnline domanda, int idScheda, int indiceScheda, int indiceMolteplicita)
		{
			var dyn2IstanzeMgr = new Dyn2IstanzeManager(new IstanzaSigeproAdapter(domanda.ReadInterface));

			IDyn2DataAccessProvider dap = null;

			if (indiceMolteplicita == -1)
				dap = new Dyn2DataAccessProvider(domanda, idScheda, dyn2IstanzeMgr, _tokenApplicazioneService);
			else
				dap = new Dyn2DataAccessProviderStampaMolteplicita(domanda, idScheda, indiceMolteplicita, dyn2IstanzeMgr, _tokenApplicazioneService);

			var loader = new ModelloDinamicoLoader(dap, domanda.DataKey.IdComune, true);

			return new ModelloDinamicoIstanza(loader, idScheda, -1, indiceScheda, false);
		}


		private string GetStringaCss()
		{
			var stringaCss = this._primoSegnapostoTrovato ? GenerazioneHtmlDomandaConstants.CssModelliDinamici : String.Empty;

			this._primoSegnapostoTrovato = false;

			return stringaCss;
		}
	}
}
