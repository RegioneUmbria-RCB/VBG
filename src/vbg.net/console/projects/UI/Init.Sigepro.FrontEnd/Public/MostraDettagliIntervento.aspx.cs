using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class MostraDettagliIntervento : BasePage
	{
		[Inject]
		public IInterventiRepository _alberoProcRepository { get; set; }
		[Inject]
		public IEndoprocedimentiService _endoprocedimentiService { get; set; }
		[Inject]
		public ICartRepository _cartRepository { get; set; }


		public bool MostraDefinizioni
		{
			get { object o = this.ViewState["MostraDefinizioni"]; return o == null ? true : (bool)o; }
			set { this.ViewState["MostraDefinizioni"] = value; }
		}


		public int Id
		{
			get { return Convert.ToInt32(Request.QueryString["Id"]); }
		}

		public bool Print
		{
			get
			{
				var qs = Request.QueryString["print"];

				if (String.IsNullOrEmpty(qs))
					return false;

				if (qs.ToUpper() == "TRUE")
					return true;

				return false;
			}
		}

		public bool NoPrintButton
		{
			get
			{
				var qs = Request.QueryString["noprint"];

				if (String.IsNullOrEmpty(qs))
					return false;

				if (qs.ToUpper() == "TRUE")
					return true;

				return false;
			}
		}

		private bool Popup
		{
			get
			{
				var qs = Request.QueryString["popup"];

				if (String.IsNullOrEmpty(qs))
					return false;

				if (qs.ToUpper() == "TRUE")
					return true;

				return false;
			}
		}

		private bool FromAreaRiservata
		{
			get
			{
				var fromAreaRiservata = Request.QueryString["fromAreaRiservata"];

				if (String.IsNullOrEmpty(fromAreaRiservata))
					return true;

				return fromAreaRiservata.ToUpper() == "TRUE";
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			treeRenderer.HideFolders = Print;

			if(!IsPostBack)
				DataBind();
		}


		public class AllegatoInterventoBindingItem
		{
			private static class Constants
			{
				public const string FormatoDoc = "DOC";
				public const string FormatoOO = "ODT";
				public const string FormatoPdf = "PDF";
				public const string FormatoRtf = "RTF";
				public const string UrlDownloadMultiformato = "~/MostraOggettoMultiFormato.ashx?idcomune={0}&id={1}&fmt={2}";
				public const string UrlDownloadGenerico = "~/MostraOggetto.ashx?idcomune={0}&codiceOggetto={1}";
			}


			int? _codiceOggetto;
			string _nomeFile;
			string _formatiSupportati;
			string _idComune;
			readonly string[] FormatiCheSupportanoPrecompilazione = new string[] { ".XSL", ".RTF" };

			public AllegatoInterventoBindingItem(Page page, string idComune,string descrizione, bool obbligatorio, int? codiceOggetto, string nomeFile, string formatiSupportati)
			{
				this._codiceOggetto = codiceOggetto;
				this._nomeFile = nomeFile;
				this._idComune = idComune;
				this._formatiSupportati = formatiSupportati;
				
				this.Obbligatorio = obbligatorio ? "*" : "";
				this.Descrizione = descrizione;				

				this.LinkDoc = page.ResolveUrl( UrlDownload(Constants.FormatoDoc) );
				this.LinkOdt = page.ResolveUrl(UrlDownload(Constants.FormatoOO));
				this.LinkPdf = page.ResolveUrl(UrlDownload(Constants.FormatoPdf));
				this.LinkRtf = page.ResolveUrl(UrlDownload(Constants.FormatoRtf));

				this.LinkGenerico = page.ResolveUrl( UrlDownloadNoPrecompilazione() );
			}

			private string UrlDownloadNoPrecompilazione()
			{
				if (!this._codiceOggetto.HasValue)
					return String.Empty;

				var estensioneFile = Path.GetExtension(this._nomeFile).ToUpperInvariant();

				if (this.FormatiCheSupportanoPrecompilazione.Contains(estensioneFile))
					return String.Empty;

				return String.Format(Constants.UrlDownloadGenerico, this._idComune, this._codiceOggetto.Value);
			}

			private string UrlDownload(string formato)
			{
				if (!this._codiceOggetto.HasValue)
					return String.Empty;

				var estensioneFile = Path.GetExtension(this._nomeFile).ToUpperInvariant();

				if ( !this.FormatiCheSupportanoPrecompilazione.Contains( estensioneFile) )
					return String.Empty;

				if (this._formatiSupportati.ToUpperInvariant().IndexOf(formato.ToUpperInvariant()) == -1)
					return String.Empty;

				return String.Format(Constants.UrlDownloadMultiformato, this._idComune, this._codiceOggetto.Value, formato);
			}

			public string Obbligatorio { get; private set; }
			public string Descrizione { get; private set; }
			public string LinkPdf { get; private set; }
			public string LinkRtf { get; private set; }
			public string LinkDoc { get; private set; }
			public string LinkOdt { get; private set; }
			public string LinkGenerico { get; private set; }

			public bool SupportaDownloadPdf { get { return !String.IsNullOrEmpty(this.LinkPdf); } }
			public bool SupportaDownloadRtf { get { return !String.IsNullOrEmpty(this.LinkRtf); } }
			public bool SupportaDownloadDoc { get { return !String.IsNullOrEmpty(this.LinkDoc); } }
			public bool SupportaDownloadOdt { get { return !String.IsNullOrEmpty(this.LinkOdt); } }
			public bool SupportaDownloadGenerico { get { return !String.IsNullOrEmpty(this.LinkGenerico); } }


		}






		public override void DataBind()
		{
			IAmbitoRicercaIntervento ambitoRicerca = FromAreaRiservata ? (IAmbitoRicercaIntervento)new AmbitoRicercaAreaRiservata(false) : (IAmbitoRicercaIntervento)new AmbitoRicercaFrontofficePubblico();

			var alberoIntervento = _alberoProcRepository.GetAlberaturaNodoDaId( IdComune, Id );
			var dettagliIntervento = _alberoProcRepository.GetDettagliIntervento(IdComune, Id, ambitoRicerca);

			var falsoRoot = new NodoAlberoInterventiDto();

			falsoRoot.NodiFiglio = new ClassTreeOfInterventoDto[1];
			falsoRoot.NodiFiglio[0] = alberoIntervento;

			treeRenderer.DataSource = falsoRoot;
			treeRenderer.DataBind();

			var figlio = falsoRoot.NodiFiglio[0];

			while (figlio.NodiFiglio.Count() > 0)
			{
				figlio = figlio.NodiFiglio[0];
			}

			Page.Title = figlio.Elemento.Descrizione;

			if (!String.IsNullOrEmpty(dettagliIntervento.Note))
			{
				ltrNote.Text = dettagliIntervento.Note.Replace("\n", "<br />"); ;
			}
			else
			{
				MostraDefinizioni = false;
			}

			if (dettagliIntervento.FasiAttuative.Length > 0)
			{
				rptFasiAttuative.DataSource = dettagliIntervento.FasiAttuative;
				rptFasiAttuative.DataBind();
			}

			if (dettagliIntervento.Documenti.Length > 0)
			{
				var dataSourceModulistica = from DocumentoInterventoDto d in dettagliIntervento.Documenti
											where !d.DomandaFo
											select new AllegatoInterventoBindingItem( this.Page,
																					  this.IdComune, 
																					  d.Descrizione, 
																					  d.Obbligatorio, 
																					  d.CodiceOggetto, 
																					  d.NomeFile , 
																					  d.TipoDownload );

				rptModulistica.DataSource = dataSourceModulistica;
				rptModulistica.DataBind();
			}

			if (dettagliIntervento.Normative.Length > 0)
			{
				rptNormativa.DataSource = dettagliIntervento.Normative;
				rptNormativa.DataBind();
			}

			BindEndoprocedimenti();

			if (dettagliIntervento.Oneri.Length > 0)
			{
				rptOneri.DataSource = dettagliIntervento.Oneri;
				rptOneri.DataBind();
			}


			pnlSchedaCart.Visible = false;

			var urlCart = _cartRepository.GetUrlSchedaCARTIntervento( Id );

			if (!String.IsNullOrEmpty(urlCart))
			{
				pnlSchedaCart.Visible = true;
				BindSchedaCart(urlCart);
			}
		}

		private void BindSchedaCart(string urlCart)
		{
			// used to build entire input
			StringBuilder sb = new StringBuilder();

			// used on each read operation
			byte[] buf = new byte[8192];

			// prepare the web page we will be asking for
			HttpWebRequest request = (HttpWebRequest)WebRequest.Create(urlCart);

			// execute the request
			HttpWebResponse response = (HttpWebResponse)
				request.GetResponse();

			var respEncoding = Encoding.GetEncoding(response.CharacterSet);

			// we will read data via the response stream
			var resStream = response.GetResponseStream();

			string tempString = null;
			int count = 0;

			do
			{
				// fill the buffer with data
				count = resStream.Read(buf, 0, buf.Length);

				// make sure we read some data
				if (count != 0)
				{
					// translate from bytes to ASCII text
					tempString = respEncoding.GetString(buf, 0, count);

					// continue building the string
					sb.Append(tempString);
				}
			}
			while (count > 0); // any more data to read?

			string content = sb.ToString();

			var startIdx = content.IndexOf("<body>");

			if (startIdx > 0)
			{
				startIdx += 6;

				var endIdx = content.IndexOf("</body>");

				if (endIdx > 0)
					content = content.Substring(startIdx, endIdx - startIdx);
			}

			content = "<div id='datiCart'>" + content + "</div>";

			ltrSchedaCart.Text = content;
		}


		private void BindEndoprocedimenti()
		{
            var codiceComune = IdComune; // FIXME: andrebbe utilizzato il codice comune per cui si presenta la domanda
            var listaEndoIntervento = _endoprocedimentiService.LeggiEndoprocedimentiDaCodiceIntervento(Id, codiceComune);

			if( listaEndoIntervento.FamiglieEndoprocedimentiPrincipali.Count > 0 ||
				listaEndoIntervento.FamiglieEndoprocedimentiAttivati.Count > 0 )
			{
				var items = new List<FamigliaEndoprocedimentoDto>(listaEndoIntervento.FamiglieEndoprocedimentiPrincipali);
				
				items.AddRange(listaEndoIntervento.FamiglieEndoprocedimentiAttivati);

				var dataSource = new AlberelloEndoControl.AlberelloEndoControlDataSource(IdComune, FromAreaRiservata, Popup, Print, items);

				rptProcedimentiNecessari.DataSource = dataSource;
				rptProcedimentiNecessari.DataBind();
			}


			if (listaEndoIntervento.FamiglieEndoprocedimentiAttivabili.Count > 0)
			{
				var dataSource = new AlberelloEndoControl.AlberelloEndoControlDataSource(IdComune, FromAreaRiservata, Popup, Print, listaEndoIntervento.FamiglieEndoprocedimentiAttivabili);

				rptProcedimentiRicorrenti.DataSource = dataSource;
				rptProcedimentiRicorrenti.DataBind();
			}


			if (listaEndoIntervento.FamiglieEndoprocedimentiFacoltativi.Count() > 0)
			{
				var dataSource = new AlberelloEndoControl.AlberelloEndoControlDataSource(IdComune, FromAreaRiservata, Popup, Print, listaEndoIntervento.FamiglieEndoprocedimentiFacoltativi);

				rptProcedimentiEventuali.DataSource = dataSource;
				rptProcedimentiEventuali.DataBind();
			}
		}



		public string GetUrlStampaPagina()
		{
			var req = HttpContext.Current.Request;
			var downloadUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

			if (!String.IsNullOrEmpty(req.ApplicationPath))
				downloadUrl += req.ApplicationPath;

			if (!downloadUrl.EndsWith("/"))
				downloadUrl += "/";

			downloadUrl += "Public/MostraDettagliIntervento.aspx?idComune=" + IdComune + "&Id=" + Id + "&Print=true";

			return downloadUrl;
		}

		public string GetUrlDownloadPagina()
		{
			var downloadUrl = GetUrlStampaPagina();

			return base.GetBaseUrlAssoluto() + "Public/DownloadPage.ashx" + "?IdComune=" + IdComune + "&url=" + Server.UrlEncode(downloadUrl);
		}



		public string GetLinkModello(object objIdOggetto)
		{
			var idOggetto = (int?)objIdOggetto;

			if (!idOggetto.HasValue)
				return "&nbsp;";

			var baseUrl = base.GetBaseUrlAssoluto();

			string urlLink = baseUrl + "MostraOggettoPdf.ashx";
			urlLink += "?IdComune=" + IdComune + "&id=" + objIdOggetto.ToString();

			string urlImmagine = Page.ResolveUrl("~/Images/download16x16.png");

			var fmtString = "<a href='{0}' target='_blank'><img src='{1}' /></a>";

			return String.Format(fmtString, urlLink, urlImmagine);
		}

		public string GetLinkNormativa(object objNormativa)
		{
			var normativa = (NormativaDto)objNormativa;
			var link = normativa.Link;

			if (String.IsNullOrEmpty(link))
				return String.Empty;

			if (!link.ToUpper().StartsWith("HTTP"))
				link = "http://" + link;

			return "<a href='" + link + "' target='_blank'><img src='" + base.GetBaseUrlAssoluto() + "images/link_icon.png' /></a>";
		}




		protected override void Render(HtmlTextWriter writer)
		{
			if (Popup || Print)
				base.Render(writer);
			else
				pnlDatiIntervento.RenderControl(writer);
		}
	}
}
