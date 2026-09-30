using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SIGePro.Net;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;

namespace Sigepro.net.Archivi.DatiDinamici
{
	public partial class PopupInserisciAssegnazione : BasePage
	{
		public static class Constants
		{
			public const string qsIdModello = "idModello";
		}

		public int IdModello
		{
			get { return Convert.ToInt32(Request.QueryString[Constants.qsIdModello]);}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
			this.Master.MostraHelp = false;

			var campi = new Dyn2ModelliDMgr(Database).GetCampiDinamiciModello(IdComune, IdModello);

			rptListaCampi.DataSource = campi.OrderBy(x => x.Posverticale).ThenBy(x => x.Posorizzontale);
			rptListaCampi.DataBind();
		}
	}
}