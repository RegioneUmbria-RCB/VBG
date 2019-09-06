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

		string IdComune  => Request.QueryString["IdComune"].ToString();
		int Endo  => Convert.ToInt32(Request.QueryString["Endo"]);
		int Istanza  =>  Convert.ToInt32(Request.QueryString["Istanza"]);
        bool MostraNonValidi => String.IsNullOrEmpty(Request.QueryString["nv"]) ? true : (Request.QueryString["nv"] == "1");

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
                    IEnumerable<IstanzeAllegati> allegati = endo.IstanzeAllegati;

                    if (!this.MostraNonValidi)
                    {
                        allegati = allegati.Where(x => x.CONTROLLOOK == "1");
                    }

					rptListaAllegati.DataSource = allegati;
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
