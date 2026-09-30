using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web;


//[assembly: WebResource("Init.Sigepro.FrontEnd.WebControls.Ateco.AlberoAtecoJs.js", "text/javascript")]
namespace Init.Sigepro.FrontEnd.WebControls.Ateco
{
	public class AlberoAtecoJs : WebControl, INamingContainer
	{
		public delegate void OnFogliaSelezionata(object sender, int idAteco);
		public event OnFogliaSelezionata FogliaSelezionata;

		public string IdComune
		{
			get { return ((IIdComunePage)this.Page).IdComune; }
		}

		public string UrlImgJsLoader
		{
			get { object o = this.ViewState["UrlImgJsLoader"]; return o == null ? "~/Images/ajax-loader.gif" : (string)o; }
			set { this.ViewState["UrlImgJsLoader"] = value; }
		}

		public string UrlAtecoService
		{
			get { object o = this.ViewState["UrlAtecoService"];return o == null ? "~/Public/WebServices/AlberoAtecoService.asmx" : (string)o;}
			set { this.ViewState["UrlAtecoService"] = value;}
		}

		public string UrlContenutiBoxRicerca
		{
			get { object o = this.ViewState["UrlContenutiBoxRicerca"];return o == null ? "~/Public/ContenutiBoxRicercaAteco.htm" : (string)o;}
			set { this.ViewState["UrlContenutiBoxRicerca"] = value;}
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
		
		public string ClientIdLinkRicerca
		{
			get { object o = this.ViewState["ClientIdLinkRicerca"]; return o == null ? "lnkRicerca" : (string)o; }
			set { this.ViewState["ClientIdLinkRicerca"] = value; }
		}
	
		

		public AlberoAtecoJs()
		{
			this.Load += new EventHandler(AlberoAtecoJs_Load);
		}

		void AlberoAtecoJs_Load(object sender, EventArgs e)
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

		protected override void OnInit(EventArgs e)
		{
//			if (!Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "treeview_ateco"))
//				Page.ClientScript.RegisterClientScriptInclude(this.GetType(), "treeview_ateco", Page.ClientScript.GetWebResourceUrl(this.GetType(), "Init.Sigepro.FrontEnd.WebControls.Ateco.AlberoAtecoJs.js"));

			base.OnInit(e);
		}

		protected override void RenderContents(HtmlTextWriter writer)
		{
			writer.AddAttribute("class", "treeView");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);

			writer.AddAttribute("class", "filetree");
			writer.AddAttribute("id", "rootNode");
			writer.RenderBeginTag(HtmlTextWriterTag.Ul);
			writer.RenderEndTag();

			writer.RenderEndTag();

			writer.AddAttribute("id", "ricercaNodoAteco");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);
			writer.RenderEndTag();

			writer.AddAttribute("id", "descrizioneAteco");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);
			writer.RenderEndTag();

			// Elaboro la stringa di postback
			string postbackReference = this.Page.ClientScript.GetPostBackEventReference(this, "SEGNAPOSTO_POSTBACK");
			postbackReference = postbackReference.Replace("'SEGNAPOSTO_POSTBACK'", "'idSelezionato@' + id");

			var startupScript = @"
require(['jquery','app/alberoateco'], function($,alberoAteco){

	var options = {
		idComune : '" + IdComune + @"',
		rootNode : $('#rootNode'),
		divRicerca: $('#ricercaNodoAteco'),
		intestazioneAteco : $('#intestazioneAteco'),
		divDescrizioneAteco: $('#descrizioneAteco'),
		lnkRicerca: $('#" + ClientIdLinkRicerca + @"'),
		urlImgJsLoader: '" + ResolveClientUrl(UrlImgJsLoader) + @"',
		urlAtecoService: '" + ResolveClientUrl(UrlAtecoService) + @"',
		urlContenutiBoxRicerca: '" + ResolveClientUrl(UrlContenutiBoxRicerca) + @"',
		infoImageString: '<a href=\'#\'><img src=\'" + ResolveClientUrl(UrlInfoImage) + @"\' /></a>',
		blankInfoString: '<img src=\'" + ResolveClientUrl(BlankInfoImage) + @"\' class=\'blankInfo\' />',
		folderClosedImage: '" + ResolveClientUrl("~/images/folder-closed.gif") + @"',
		folderOpenImage: '" + ResolveClientUrl("~/images/folder.gif") + @"',
		fileImage: '" + ResolveClientUrl("~/images/file.gif") + @"',
		fogliaSelezionata: function(id){
			" + postbackReference + @";
		}
	}

	alberoAteco.initialize(options);
});";

			Page.ClientScript.RegisterStartupScript(this.GetType(), "startupScript", startupScript, true);

		}
	}
}
