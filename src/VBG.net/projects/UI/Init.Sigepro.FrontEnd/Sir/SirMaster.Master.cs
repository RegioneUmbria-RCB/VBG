using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti;

namespace Init.Sigepro.FrontEnd.Sir
{
	public partial class SirMaster : Ninject.Web.MasterPageBase
	{
		[Inject]
		protected ConfigurazioneContenuti _configurazione { get; set; }

		private static class Constants
		{
			public const string HomeUrl = "~/sir/default.aspx?alias={0}&Software={1}";
			public const string SuapeUrl = "~/sir/suape.aspx?alias={0}&Software={1}";
			public const string IstruzioniUrl = "~/sir/istruzioni.aspx?alias={0}&Software={1}";
			public const string FaqUrl = "~/sir/faq.aspx?alias={0}&Software={1}";
			public const string NoteUrl = "~/sir/note.aspx?alias={0}&Software={1}";

			public const string AliasParam = "alias";
			public const string SoftwareParam = "software";
			public const string DefaultSoftware = "TT";
		}

		public class NavMenuLinks
		{
			public string Home { get; set; }
			public string Istruzioni { get; set; }
			public string Suape { get; set; }
			public string Faq { get; set; }
			public string Note { get; set; }
		}

		public class DatiComune
		{
			public string NomeComune;
			public string LogoComune;
		}

		private string Alias
		{
			get
			{
				if (String.IsNullOrEmpty(Request.QueryString[Constants.AliasParam]))
				{
					throw new Exception("Alias non corretto o non impostato");
				}

				return Request.QueryString[Constants.AliasParam];
			}
		}

		private string Software
		{
			get
			{
				if (String.IsNullOrEmpty(Request.QueryString[Constants.SoftwareParam]))
				{
					return Constants.DefaultSoftware;
				}

				return Request.QueryString[Constants.SoftwareParam];
			}
		}

		public NavMenuLinks Links { get; set; }
		public DatiComune Dati { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Links = new NavMenuLinks
			{
				Home = ResolveClientUrl(String.Format(Constants.HomeUrl, Alias, Software)),
				Suape = ResolveClientUrl(String.Format(Constants.SuapeUrl, Alias, Software)),
				Istruzioni = ResolveClientUrl(String.Format(Constants.IstruzioniUrl, Alias, Software)),
				Faq = ResolveClientUrl(String.Format(Constants.FaqUrl, Alias, Software)),
				Note = ResolveClientUrl(String.Format(Constants.NoteUrl, Alias, Software)),
			};



			this.Dati = new DatiComune
			{
				LogoComune =  String.Format("{0}?alias={1}&Software={2}", ResolveClientUrl("~/Contenuti/logoComune.ashx"), Alias, Software),
				NomeComune = this._configurazione.DatiComune.NomeComune
			};
		}
	}
}