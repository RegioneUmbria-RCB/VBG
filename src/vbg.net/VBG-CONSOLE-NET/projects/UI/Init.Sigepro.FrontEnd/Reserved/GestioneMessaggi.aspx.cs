using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class GestioneMessaggi : ReservedBasePage
	{
		[Inject]
		public IMessaggiFrontofficeRepository _messaggiFrontofficeRepository { get; set; }


		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			multiView.ActiveViewIndex = 0;

			string filtro = ddlStato.SelectedValue;
			int? filtroStato = null;

			if (!String.IsNullOrEmpty(filtro))
				filtroStato = Convert.ToInt32(filtro);

			gvMessaggi.DataSource = _messaggiFrontofficeRepository.GetMessaggi(IdComune, Software, CodiceUtente, filtroStato);
			gvMessaggi.DataBind();
		}

		protected void ddlStato_SelectedIndexChanged(object sender, EventArgs e)
		{
			DataBind();
		}

		protected void gvMessaggi_SelectedIndexChanged(object sender, EventArgs e)
		{
			int id = Convert.ToInt32(gvMessaggi.DataKeys[gvMessaggi.SelectedIndex]);

			multiView.ActiveViewIndex = 1;

			var msg = _messaggiFrontofficeRepository.GetMessaggio(IdComune, id);

			lblDa.Text = msg.Mittente;
			lblInviatoIl.Text = msg.Data.Value.ToShortDateString();
			lblOggetto.Text = msg.Oggetto;
			lblCorpo.Text = msg.Corpo;
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			DataBind();
		}


	}
}
