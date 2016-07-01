using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;
using System.Web.UI;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.WebControls.Interventi
{
	
	public class AlberoInterventiJs : WebControl, INamingContainer
	{
		public delegate void OnFogliaSelezionata(object sender, int idIntervento);
		public event OnFogliaSelezionata FogliaSelezionata;

		public string IdComune
		{
			get { return ((IIdComunePage)this.Page).IdComune; }
		}

		public string Software
		{
			get
			{
				var sw = HttpContext.Current.Request.QueryString["Software"];

				if (String.IsNullOrEmpty(sw))
					return "SS";

				return sw;
			}
		}

		public int IdAteco
		{
			get { object o = this.ViewState["IdAteco"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["IdAteco"] = value; }
		}

		public bool AreaRiservata
		{
			get { object o = this.ViewState["AreaRiservata"]; return o == null ? false : (bool)o; }
			set { this.ViewState["AreaRiservata"] = value; }
		}


		public string UrlImgJsLoader
		{
			get { object o = this.ViewState["UrlImgJsLoader"]; return o == null ? "~/Images/ajax-loader.gif" : (string)o; }
			set { this.ViewState["UrlImgJsLoader"] = value; }
		}

		public bool EvidenziaVociAttivabiliDaAreaRiservata
		{
			get { object o = this.ViewState["EvidenziaVociAttivabiliDaAreaRiservata"]; return o == null ? false : (bool)o; }
			set { this.ViewState["EvidenziaVociAttivabiliDaAreaRiservata"] = value; }
		}
		

		public string UrlInterventiService
		{
			get { object o = this.ViewState["UrlInterventiService"]; return o == null ? "~/Public/WebServices/InterventiJsService.asmx" : (string)o; }
			set { this.ViewState["UrlInterventiService"] = value; }
		}

		public string UrlDettagliIntervento
		{
			get { object o = this.ViewState["UrlDettagliIntervento"]; return o == null ? "~/Public/MostraDettagliIntervento.aspx" : (string)o; }
			set { this.ViewState["UrlDettagliIntervento"] = value; }
		}

		public string UrlDettagliEndo
		{
			get { object o = this.ViewState["UrlDettagliEndo"]; return o == null ? "~/Public/MostraDettagliEndo.aspx" : (string)o; }
			set { this.ViewState["UrlDettagliEndo"] = value; }
		}

		public string UrlInfoImage
		{
			get { object o = this.ViewState["UrlInfoImage"]; return o == null ? "~/Images/help_interventi.gif" : (string)o; }
			set { this.ViewState["UrlInfoImage"] = value; }
		}

		public string BlankInfoImage
		{
			get { object o = this.ViewState["BlankInfoImage"]; return o == null ? "~/Images/blank.gif" : (string)o; }
			set { this.ViewState["BlankInfoImage"] = value; }
		}

		public string UrlContenutiBoxRicerca
		{
			get { object o = this.ViewState["UrlContenutiBoxRicerca"]; return o == null ? "~/Public/ContenutiBoxRicercaAteco.htm" : (string)o; }
			set { this.ViewState["UrlContenutiBoxRicerca"] = value; }
		}

		public string Note
		{
			get { object o = this.ViewState["Note"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Note"] = value; }
		}

		public bool UtenteTester
		{
			get { object o = this.ViewState["UtenteTester"]; return o == null ? false : (bool)o; }
			set { this.ViewState["UtenteTester"] = value; }
		}


        public LivelloAutenticazioneEnum LivelloAutenticazione
        {
            get { object o = this.ViewState["LivelloAutenticazione"]; return o == null ? LivelloAutenticazioneEnum.NonIdentificato : (LivelloAutenticazioneEnum)o; }
            set { this.ViewState["LivelloAutenticazione"] = value; }
        }

		public string CookiePrefix
		{
			get { object o = this.ViewState["CookiePrefix"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["CookiePrefix"] = value; }
		}

		public string AutoCompleteCustomRenderer
		{
			get { object o = this.ViewState["AutoCompleteCustomRenderer"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["AutoCompleteCustomRenderer"] = value; }
		}







		public AlberoInterventiJs()
		{
			this.Load += new EventHandler(AlberoInterventiJs_Load);
		}
		protected override bool OnBubbleEvent(object source, EventArgs args)
		{
			return base.OnBubbleEvent(source, args);
		}

		void AlberoInterventiJs_Load(object sender, EventArgs e)
		{
			if (this.Page.IsPostBack && HttpContext.Current.Request["__EVENTTARGET"] == this.UniqueID)
			{
				var eventArg = HttpContext.Current.Request["__EVENTARGUMENT"];
				var idx = eventArg.IndexOf("idSelezionato");
				if (idx >= 0)
				{
					var id = Convert.ToInt32(eventArg.Substring(idx + 14));

					if (FogliaSelezionata != null)
						FogliaSelezionata(this, id);
				}
			}
		}

		//protected override void OnInit(EventArgs e)
		//{
		//    if (!Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "treeview_interventi"))
		//        Page.ClientScript.RegisterClientScriptInclude(this.GetType(), "treeview_interventi", Page.ClientScript.GetWebResourceUrl(this.GetType(), "Init.Sigepro.FrontEnd.WebControls.Interventi.AlberoInterventiJs.js"));

		//    base.OnInit(e);
		//}

		protected override void RenderContents(HtmlTextWriter writer)
		{
			writer.AddAttribute("class", "treeView");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);

			if (!String.IsNullOrEmpty(Note.Trim()))
			{
				writer.AddAttribute("class", "noteAlbero");
				writer.RenderBeginTag(HtmlTextWriterTag.Span);
				writer.Write(Note);
				//<span class="noteAlbero" style="margin-left:32px">Le voci contrassegnate con * sono attivabili tramite i servizi online</span>
				writer.RenderEndTag();
			}

			writer.AddAttribute("class", "filetree");
			writer.AddAttribute("id", "rootNode");
			writer.RenderBeginTag(HtmlTextWriterTag.Ul);
			writer.RenderEndTag();

			writer.RenderEndTag();

			writer.AddAttribute("id", "descrizioneIntervento");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);
			writer.RenderEndTag();

			writer.AddAttribute("id", "descrizioneEndo");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);
			writer.RenderEndTag();

			// Div per la ricerca testuale
			writer.AddAttribute("id", "divRicerca");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);
			writer.RenderEndTag();

			// Elaboro la stringa di postback
			string postbackReference = this.Page.ClientScript.GetPostBackEventReference(this, "SEGNAPOSTO_POSTBACK");
			postbackReference = postbackReference.Replace("'SEGNAPOSTO_POSTBACK'", "'idSelezionato@' + id");

			var startupScript = @"require(['jquery','app/alberointerventi'], function($,albero){
				var options = {
					idComune : '" + IdComune + @"',
					idAteco: '" + IdAteco + @"',
					software: '" + Software + @"',
					rootNode : $('#rootNode'),
					divDescrizioneIntervento: $('#descrizioneIntervento'),
					divDescrizioneEndo: $('#descrizioneEndo'),
					urlImgJsLoader: '" + ResolveClientUrl(UrlImgJsLoader) + @"',
					urlInterventiService: '" + ResolveClientUrl(UrlInterventiService) + @"',
					urlContenutiBoxRicerca: '" + ResolveClientUrl(UrlContenutiBoxRicerca) + @"',
					urlDettagliIntervento: '" + ResolveClientUrl(UrlDettagliIntervento) + "?IdComune=" + IdComune + "&Software=" + Software + "&fromAreaRiservata=" + AreaRiservata + @"',
					urlDettagliEndo: '" + ResolveClientUrl(UrlDettagliEndo) + "?IdComune=" + IdComune + "&Software=" + Software + "&fromAreaRiservata=" + AreaRiservata + @"',
					infoImageString: '<a href=\'#\'><img src=\'" + ResolveClientUrl( UrlInfoImage ) + @"\' /></a>',
					blankInfoString: '<img src=\'" + ResolveClientUrl(BlankInfoImage) + @"\' class=\'blankInfo\' />',
					folderClosedImage: '" + ResolveClientUrl("~/images/folder-closed.gif") + @"',
					folderOpenImage: '" + ResolveClientUrl("~/images/folder.gif") + @"',
					fileImage: '" + ResolveClientUrl("~/images/file.gif") + @"',
					mostraVociAttivabiliDaAreaRiservata: " + (EvidenziaVociAttivabiliDaAreaRiservata ? "true" : "false") + @",
					areaRiservata: " + (AreaRiservata ? "'true'" : "'false'") + @",
					utenteTester: " + (this.UtenteTester ? "true" : "false") + @",
                    livello: '" + this.LivelloAutenticazione.ToString() + @"',
					divRicerca: $('#divRicerca'),
					lnkRicerca: $('#lnkRicerca'),
					cookiePrefix: '" + CookiePrefix + @"',
					fogliaSelezionata: function(id){
						" + postbackReference + @";
					}";

			if (!String.IsNullOrEmpty(AutoCompleteCustomRenderer))
				startupScript += ",\r\nautoCompleteCustomRenderer: " + AutoCompleteCustomRenderer + ",";


			startupScript += "};albero.initialize( options );});";


			Page.ClientScript.RegisterStartupScript(this.GetType(), "startupScript", startupScript, true);

		}

	}
}
