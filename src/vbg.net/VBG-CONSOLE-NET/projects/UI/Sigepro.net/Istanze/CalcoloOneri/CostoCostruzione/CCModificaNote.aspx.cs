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

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
	public partial class CCModificaNote : BasePage
	{
		CCICalcoloTContributo TContributo
		{
			get
			{
				int idTContributo = Convert.ToInt32(Request.QueryString["tcontributo"]);
				return new CCICalcoloTContributoMgr(Database).GetById(IdComune, idTContributo);
			}
		}

		public override string Software
		{
			get
			{
                Init.SIGePro.Data.Istanze ist = new IstanzeMgr(Database).GetById(IdComune, TContributo.Codiceistanza.GetValueOrDefault(int.MinValue));
				return ist.SOFTWARE;
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
			if (!IsPostBack)
			{
				DataBind();
			}
		}

		public override void DataBind()
		{
			txtNote.Text = TContributo.Noteriduzione;
		}

		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			CCICalcoloTContributoMgr mgr = new CCICalcoloTContributoMgr(Database);

			int idTContributo = Convert.ToInt32(Request.QueryString["tcontributo"]);
			CCICalcoloTContributo cls = new CCICalcoloTContributoMgr(Database).GetById(IdComune, idTContributo);

			cls.Noteriduzione = txtNote.Text;

			mgr.Update(cls);

			string script = "window.returnValue = true;self.close();";

			this.Page.ClientScript.RegisterStartupScript(this.GetType(), "loadScript", script, true);
			return;
		}
	}
}
