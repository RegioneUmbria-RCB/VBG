using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti.Configurazione
{
	public class BoxDatiComune
	{
		[Inject]
		public IConfigurazioneContenutiRepository _configurazioneContenutiRepository { get; set; }
		//[Inject]
		//public IConfigurazione<ParametriSezioneContenuti> _configurazione { get; set; }
		[Inject]
		public IInterventiRepository _alberoProcRepository { get; set; }

		public class CodiceAccreditamento
		{
			public string Comune { get; protected set; }
			public string Codice { get; protected set; }

			public CodiceAccreditamento(string comune, string codiceAccreditamento)
			{
				this.Comune = comune;
				this.Codice = codiceAccreditamento;
			}
		}

		public readonly string IntestazioneLogo;
		public readonly string NomeComune;
		public readonly string NomeComune2;
		public readonly string NomeResponsabile;
		public readonly string IndirizzoPec;
		public readonly string Telefono;
		public readonly string IdComune;
		public readonly string LinkServizi;
		public readonly string LinkRegione;
		public readonly string TestoRegione;
		public readonly int? CodiceOggetoLogo;
		public readonly bool PecPresente;
		public readonly IEnumerable<CodiceAccreditamento> CodiciAccreditamento;
		public readonly bool EsistonoInterventiAttivabili;

		internal static BoxDatiComune Load(string idComune, string software, string linkRegione, string testoRegione)
		{
			return new BoxDatiComune(idComune, software, linkRegione, testoRegione);
		}

		protected BoxDatiComune(string idComune, string software, string linkRegione, string testoRegione)
		{
			FoKernelContainer.Inject(this);

			var datiComune = _configurazioneContenutiRepository.GetConfigurazione(idComune, software);

			this.IdComune = idComune;
			this.IndirizzoPec = String.IsNullOrEmpty(datiComune.IndirizzoPec) ?
															datiComune.IndirizzoEmail :
															datiComune.IndirizzoPec;
			this.IntestazioneLogo = datiComune.NomeRegione;
			this.LinkServizi = datiComune.AreaRiservataAttiva ?
														GetUrlAreaRiservata(idComune, software) :
														String.Empty;
			this.NomeComune = datiComune.NomeComune;
			this.NomeComune2 = datiComune.NomeComuneSottotitolo;
			this.NomeResponsabile = datiComune.ResponsabileSportello;
			this.Telefono = datiComune.Telefono;
			this.CodiceOggetoLogo = datiComune.CodiceOggettoLogo;
			this.PecPresente = !String.IsNullOrEmpty(datiComune.IndirizzoPec);
			this.EsistonoInterventiAttivabili = this._alberoProcRepository.EsistonoVociAttivabiliTramiteAreaRiservata(idComune, software);
			this.LinkRegione = linkRegione;
			this.TestoRegione = testoRegione;

			if (datiComune.ListaCodiciAccreditamento == null)
				datiComune.ListaCodiciAccreditamento = new List<CodiceAccreditamentoDto>().ToArray();

			this.CodiciAccreditamento = datiComune
											.ListaCodiciAccreditamento
											.Select(x => new CodiceAccreditamento(x.NomeComune, x.CodiceAccreditamento));
		}

		private static string GetUrlAreaRiservata(string idComune, string software)
		{
			var ctxt = HttpContext.Current;

			var returnTo = ctxt.Server.UrlEncode(ctxt.Request.Url.ToString());

			var url = String.Format("Login.aspx?IdComune={0}&Software={1}&ReturnTo={2}", idComune, software, returnTo);

			return GetUrlAssoluto(url);
		}

		private static string GetUrlAssoluto(string url)
		{
			var req = HttpContext.Current.Request;
			var downloadUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

			if (!String.IsNullOrEmpty(req.ApplicationPath))
				downloadUrl += req.ApplicationPath;

			if (!downloadUrl.EndsWith("/"))
				downloadUrl += "/";

			downloadUrl += url;

			return downloadUrl;
		}
	}
}
