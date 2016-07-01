using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class AlberoInterventi : BasePage
	{
		[Inject]
		public IInterventiRepository _alberoProcRepository { get; set; }
		[Inject]
		public IAtecoRepository _atecoRepository { get; set; }


		public override string Software
		{
			get
			{
				var sw = Request.QueryString["Software"];

				if (String.IsNullOrEmpty(sw))
					return "SS";

				return sw;
			}
		}

		public bool StartCollapsed
		{
			get { object o = this.ViewState["StartCollapsed"]; return o == null ? true : (bool)o; }
			set { this.ViewState["StartCollapsed"] = value; }
		}

		public Ateco VoceAteco { get; set; }




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

		public int IdAteco
		{
			get {
				var ateco = Request.QueryString["idAteco"];

				if (string.IsNullOrEmpty(ateco))
					return -1;

				return Convert.ToInt32( ateco );
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			DataBind();
		}

		public override void DataBind()
		{
			var url = GetAbsoluteBaseUrl() + "Public/MostraDettagliIntervento.aspx?Id={0}&popup=" + !Popup;

			if (Popup)
			{
				url = "mostraDettagli(this,{0})";
			}
			

			treeRenderer.UrlDettagliIntervento = url;

			if (IdAteco == -1)
			{
				treeRenderer.DataSource = _alberoProcRepository.GetAlberoInterventi(IdComune, Software);
			}
			else
			{
				var l = _atecoRepository.GetAlberoProc(IdComune, IdAteco,AmbitoRicerca.FrontofficePubblico);
				VoceAteco = _atecoRepository.GetDettagli(IdComune, IdAteco);
				StartCollapsed = false;

				if (l.NodiFiglio.Count() == 0)
				{
					StartCollapsed = true;
					l = _alberoProcRepository.GetAlberoInterventi(IdComune, Software);
				}

				treeRenderer.DataSource = l;
			}

			treeRenderer.DataBind();
		}

		protected override void Render(HtmlTextWriter writer)
		{
			if (Popup)
				base.Render(writer);
			else
				pnlAlberoInterventi.RenderControl(writer);
		}

		protected string GetAbsoluteBaseUrl()
		{
			var req = HttpContext.Current.Request;
			var baseUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

			if (!String.IsNullOrEmpty(req.ApplicationPath))
				baseUrl += req.ApplicationPath;

			if (!baseUrl.EndsWith("/"))
				baseUrl += "/";

			return baseUrl;
		}
	}
}
