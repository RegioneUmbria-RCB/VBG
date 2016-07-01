using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System.IO;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class VisuraCtrlV2_DettaglioEndo : System.Web.UI.UserControl
	{
		public string IdComune { get { return Request.QueryString["IdComune"]; } }
		public string Software { get { return Request.QueryString["Software"]; } }

		private ProcedimentoType _dataSource;
		public ProcedimentoType DataSource
		{
			get { return this._dataSource; }
			set { this._dataSource = value; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
		}

		protected void rptAllegati_ItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			string downloadAllegatoFmtString = "~/MostraOggetto.ashx?IdComune={0}&CodiceOggetto={1}&STC=true&Software={2}";
			string downloadAllegatoP7mFmtString = "~/MostraOggettoP7M.aspx?IdComune={0}&CodiceOggetto={1}&STC=true&Software={2}";

			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				var hlDownloadAllegato = (HyperLink)e.Item.FindControl("hlDownloadAllegato");

				var doc = (DocumentiType)e.Item.DataItem;

				bool documentoFirmato = (Path.GetExtension(doc.allegati.allegato).ToUpper() == ".P7M");

				hlDownloadAllegato.NavigateUrl = String.Format(documentoFirmato ? downloadAllegatoP7mFmtString : downloadAllegatoFmtString,
																	IdComune,
																	doc.allegati.id,
																	Software
																	);
			}
		}
	}
}