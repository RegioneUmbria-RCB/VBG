using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class ListaAllegatiEndo : Ninject.Web.PageBase
	{
		[Inject]
		public IVisuraService _visuraService { get; set; }



		string IdComune
		{
			get
			{
				return Request.QueryString["IdComune"].ToString();
			}
		}

		int Endo
		{
			get
			{
				return Convert.ToInt32(Request.QueryString["Endo"]);
			}
		}

		int Istanza
		{
			get
			{
				return Convert.ToInt32(Request.QueryString["Istanza"]);
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			var istanza = _visuraService.GetById(Istanza, new VisuraIstanzaFlags { LeggiDatiConfigurazione = false });

			for (int i = 0; i < istanza.EndoProcedimenti.Length; i++)
			{
				var endo = istanza.EndoProcedimenti[i];

				if (Convert.ToInt32(endo.CODICEINVENTARIO) == Endo)
				{
					rptListaAllegati.DataSource = endo.IstanzeAllegati;
					rptListaAllegati.DataBind();
				}
			}
		}

		protected override void Render(HtmlTextWriter writer)
		{
			pnlContenuti.RenderControl(writer);
		}

		protected string GeneraUrlDownload(object objDataItem)
		{
			var allegato = (IstanzeAllegati)objDataItem;
			return String.Format("~/MostraOggetto.ashx?IdComune={0}&codiceOggetto={1}", IdComune, allegato.CODICEOGGETTO);
		}
	}
}
