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
using Init.SIGePro.Manager;
using Init.SIGePro.Data;

namespace Sigepro.net.Istanze.Statistiche
{
	public partial class PopupAlberoProcedimenti : BasePage
	{
        public bool ReturnExtendedDescription
        {
            get
            {
                object o = Request.QueryString["ReturnExtendedDescription"];
                return o == null ? false : Convert.ToBoolean(o);
            }
        }

		protected void Page_Load(object sender, EventArgs e)
		{
			Response.Cache.SetCacheability(HttpCacheability.NoCache);

			AlberoProcedimenti1.AuthenticationInfo = AuthenticationInfo;
            if (!string.IsNullOrEmpty(Request.QueryString["AllowParentSelection"]))
            {
                AlberoProcedimenti1.AllowParentSelection = Convert.ToBoolean(Request.QueryString["AllowParentSelection"]);
            }

			if (!IsPostBack)
			{
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;

				DataBind();
			}
		}

		public override void DataBind()
		{
			AlberoProcedimenti1.Software = Software;
			AlberoProcedimenti1.DataBind();
		}

		protected void AlberoProcedimenti1_ProcedimentoSelezionato(object sender, Init.SIGePro.Data.AlberoProc procedimento)
		{
            int? sc_id = procedimento.Sc_id;
            string sc_descrizione = procedimento.SC_DESCRIZIONE;

            if (ReturnExtendedDescription)
            {
                VwAlberoProc vap = new VwAlberoProcMgr(Database).GetById(sc_id, IdComune);
                sc_descrizione = vap.ScDescrizione;
            }

            Page.ClientScript.RegisterStartupScript(this.GetType(), "close", String.Format("RitornaValore(\"{0}\",\"{1}\");", sc_id, sc_descrizione), true);
		}
	}
}
