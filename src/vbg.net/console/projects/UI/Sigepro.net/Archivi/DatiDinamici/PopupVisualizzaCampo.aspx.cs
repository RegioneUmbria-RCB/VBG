using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SIGePro.Net;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;

namespace Sigepro.net.Archivi.DatiDinamici
{
	public partial class PopupVisualizzaCampo : BasePage
	{
		public static class Constants
		{
			public const string qsIdModello = "idModello";
			public const string qsCampiStatici = "campiStatici";
		}

		public bool CampiStatici
		{
			get 
			{ 
				var qs = Request.QueryString[Constants.qsCampiStatici];
				return !String.IsNullOrEmpty(qs) && qs.ToUpperInvariant() == "TRUE"; }
		}

		public int IdModello
		{
			get { return Convert.ToInt32(Request.QueryString[Constants.qsIdModello]); }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public class BindingItem
		{
			public string Key { get; set; }
			public string Value { get; set; }
			public int PosOrizzontale { get; set; }
			public int PosVerticale { get; set; }
		}

		public override void DataBind()
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
			this.Master.MostraHelp = false;

			IEnumerable<BindingItem> campi;

			if (CampiStatici)
			{
				campi = new Dyn2ModelliDMgr(Database)
												.GetCampiStaticiModello(IdComune,IdModello)
												.Select(x => new BindingItem { 
													Key = x.Id.ToString(), 
													Value = x.CampoTestuale.ToString(),  
													PosOrizzontale = x.Posorizzontale.Value,
													PosVerticale = x.Posverticale.Value
												});
			}
			else
			{
				campi = new Dyn2ModelliDMgr(Database)
												.GetCampiDinamiciModello(IdComune, IdModello)
												.Select(x => new BindingItem { 
													Key = x.CampoDinamico.ToString(),
													Value = x.CampoDinamico.ToString(),
													PosOrizzontale = x.Posorizzontale.Value,
													PosVerticale = x.Posverticale.Value												
												});
			}

			rptListaCampi.DataSource = campi.OrderBy(x => x.PosVerticale).ThenBy(x => x.PosOrizzontale);

			rptListaCampi.DataBind();
		}
	}
}