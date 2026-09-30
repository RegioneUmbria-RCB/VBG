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
using SIGePro.Net;
using Init.SIGePro.Manager.Logic.Ricerche;
using System.Collections.Generic;
using SIGePro.WebControls.Ajax;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;

namespace Sigepro.net.Archivi.Mercati
{
	public partial class MercatiD : BasePage
	{
		int idxRecord = 0;
		public int IndiceRecord
		{
			get { return idxRecord; }
			set { idxRecord = value; }
		}

		public string CodiceUso
		{
			get { return this.rplMercatiUso.Value; }
		}
		protected void Page_Load(object sender, EventArgs e)
		{
			Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;

			if (!IsPostBack)
			{
				this.rplMercatiUso.InitParams["FkCodiceMercato"] = "";
				SetMercatoByQueryString();
				SetMercatoUsoByQueryString();
                BindRepeater();
			}

            this.PreRender += new EventHandler(MercatiD_PreRender);
		}

        void MercatiD_PreRender(object sender, EventArgs e)
        {
            if (IsInPopup)
            {
                this.cmdChiudiDettaglio.Attributes.Add("onClick", "self.close();");
            }
        }

		private void SetMercatoByQueryString()
		{
			if (!String.IsNullOrEmpty(Request.QueryString["codicemercato"]))
			{
				this.rplMercato.Class = new MercatiMgr(Database).GetById(IdComune, Convert.ToInt32(Request.QueryString["codicemercato"]));
				this.rplMercato.ReadOnly = true;

				rplMercato_ValueChanged(this, EventArgs.Empty);
			}
		}
		private void SetMercatoUsoByQueryString()
		{
			if (!String.IsNullOrEmpty(Request.QueryString["codiceuso"]))
			{
				this.rplMercatiUso.Class = new Mercati_UsoMgr(Database).GetById(IdComune, Convert.ToInt32(Request.QueryString["codiceuso"]));
				this.rplMercatiUso.ReadOnly = true;
			}
		}

		protected void rplMercato_ValueChanged(object sender, EventArgs e)
		{
			this.rplMercatiUso.InitParams["FkCodiceMercato"] = this.rplMercato.Value;
			this.rplMercatiUso.Class = null;
		}

		private void BindRepeater()
		{
			this.rptDettaglio.DataSource = GetPosteggi();
			this.rptDettaglio.DataBind();
		}
		private List<Mercati_D> GetPosteggi()
		{
			Init.SIGePro.Data.Mercati_D posteggio = new Init.SIGePro.Data.Mercati_D();
			posteggio.IDCOMUNE = IdComune;
			posteggio.FKCODICEMERCATO = Convert.ToInt32(this.rplMercato.Value);
			posteggio.OrderBy = "CODICEPOSTEGGIO ASC";

			return new Mercati_DMgr(Database).GetList(posteggio);
		}

		#region metodi di ricerca di ricercheplus
		


		[System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
		public static string[] GetCompletionListUsi(string token, 
													string dataClassType,
													string targetPropertyName,
													string descriptionPropertyNames,
													string prefixText,
													int count,
													string software,
													bool ricercaSoftwareTT,
													Dictionary<string, string> initParams, string FkCodiceMercato)
		{
			try
			{
				RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software, ricercaSoftwareTT, initParams);

				// Gestione di una ricerca custom
				sc.Searching += delegate(object sender, RicerchePlusEventArgs e)
				{
					Init.SIGePro.Data.Mercati_Uso mercati_uso = (Init.SIGePro.Data.Mercati_Uso)e.SearchedClass;
					mercati_uso.FkCodiceMercato = Convert.ToInt32(FkCodiceMercato);
				};

				return RicerchePlusCtrl.CreateResultList(sc.Find(true));
			}
			catch (Exception ex)
			{
				return RicerchePlusCtrl.CreateErrorResult(ex);
			}
		}

		#endregion

		protected void rptDettaglio_ItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				Posteggio p = (e.Item.FindControl("Posteggio1") as Posteggio);
			}
		}

		protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
		{
			base.CloseCurrentPage();
		}
	}
}
