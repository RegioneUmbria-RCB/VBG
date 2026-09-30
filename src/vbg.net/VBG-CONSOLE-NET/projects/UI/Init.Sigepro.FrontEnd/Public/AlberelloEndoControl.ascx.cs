using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class AlberelloEndoControl : System.Web.UI.UserControl
	{
		public class EndoprocedimentoBindingItem
		{
			public string LinkDettagli { get; private set; }
			public string Amministrazione { get; private set; }
			public bool AmministrazionePresente { get { return !String.IsNullOrEmpty(this.Amministrazione); } }

			public EndoprocedimentoBindingItem(bool principale, bool richiesto, string descrizione, string amministrazione, UrlDettaglioEndoprocedimento urlDettaglio)
			{
				this.LinkDettagli = urlDettaglio.ToHtmlString(descrizione);
				this.Amministrazione = amministrazione;
			}
		}

		public class UrlDettaglioEndoprocedimento
		{
			string _idComune;
			int _codice;
			bool _fromAreaRiservata;
			bool _popup;
			bool _print;

			internal UrlDettaglioEndoprocedimento(string idComune, int codice, bool fromAreaRiservata, bool popup, bool print)
			{
				this._idComune = idComune;
				this._codice = codice;
				this._fromAreaRiservata = fromAreaRiservata;
				this._popup = popup;
				this._print = print;
			}

			internal string ToHtmlString(string descrizione)
			{
				var req = HttpContext.Current.Request;
				var urlAssoluto = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

				if (!String.IsNullOrEmpty(req.ApplicationPath))
					urlAssoluto += req.ApplicationPath;

				if (!urlAssoluto.EndsWith("/"))
					urlAssoluto += "/";


				string fmtString = "Public/mostraDettagliEndo.aspx?IdComune={0}&Id={1}&fromAreaRiservata={2}&print={3}";

				var urlEndo = urlAssoluto + String.Format(fmtString, this._idComune, this._codice, this._fromAreaRiservata, this._print);

				if (this._popup)
					urlEndo += "&popup=true";

				if (this._print)
					urlEndo = String.Format("{0}Public/DownloadPage.ashx?IdComune={1}&url={2}", urlAssoluto, this._idComune, HttpContext.Current.Server.UrlEncode(urlEndo));

				var fmtStr = "<a href='{0}' target='_blank' class='linkDettagliendo'>{1}</a>";

				return String.Format(fmtStr, urlEndo, descrizione);
			}
		}

		public class AlberelloEndoControlDataSource
		{
			public readonly string IdComune;
			public readonly bool FromAreaRiservata;
			public readonly bool Popup;
			public readonly bool Print;

			public readonly IEnumerable<FamigliaEndoprocedimentoDto> Items;

			public AlberelloEndoControlDataSource(string idComune,bool fromAreaRiservata,bool popup,bool print,IEnumerable<FamigliaEndoprocedimentoDto> items)
			{
				this.IdComune = idComune;
				this.FromAreaRiservata = fromAreaRiservata;
				this.Popup = popup;
				this.Print = print;
				this.Items = items;
			}
		}

		public AlberelloEndoControlDataSource DataSource { get; set; }



		public string Titolo
		{
			get { return this.ViewState["Titolo"].ToString(); }
			set { this.ViewState["Titolo"] = value; }
		}

		public string IdComune
		{
			get { return this.ViewState["IdComune"].ToString(); }
			set { this.ViewState["IdComune"] = value; }
		}

		public bool FromAreaRiservata
		{
			get { return (bool)this.ViewState["FromAreaRiservata"]; }
			set { this.ViewState["FromAreaRiservata"] = value; }
		}

		public bool Popup
		{
			get { return (bool)this.ViewState["Popup"]; }
			set { this.ViewState["Popup"] = value; }
		}

		public bool Print
		{
			get { return (bool)this.ViewState["Print"]; }
			set { this.ViewState["Print"] = value; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{

		}

		public IEnumerable<EndoprocedimentoBindingItem> GetEndoBindingSource(object objListaEndo)
		{
			var listaEndo = (IEnumerable<EndoprocedimentoDto>)objListaEndo;

			return listaEndo.Select(x => new EndoprocedimentoBindingItem(x.Principale,
																			x.Richiesto,
																			x.Descrizione,
																			x.Amministrazione,
																			new UrlDettaglioEndoprocedimento(this.IdComune,
																											  x.Codice,
																											  this.FromAreaRiservata,
																											  this.Popup, this.Print)));

		}

		public override void DataBind()
		{
			this.IdComune = this.DataSource.IdComune;
			this.Popup = this.DataSource.Popup;
			this.FromAreaRiservata = this.DataSource.FromAreaRiservata;
			this.Print = this.DataSource.Print;

			this.rptFamiglieEndo.DataSource = this.DataSource.Items;
			this.rptFamiglieEndo.DataBind();

		}
	}
}